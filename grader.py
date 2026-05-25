#!/usr/bin/env python3
"""
grader.py — HSF302 Lab 02 Auto-Grader
======================================
Cách dùng (local):
    mvn test -fae          # chạy tất cả test, không dừng khi fail
    python grader.py       # xem bảng điểm ở terminal

Cách dùng (CI / GitHub Step Summary):
    python grader.py --markdown >> $GITHUB_STEP_SUMMARY
"""
import argparse
import os
import sys
import xml.etree.ElementTree as ET
from dataclasses import dataclass, field
from typing import List, Optional

# ─────────────────────────────────────────────────────────────────
# Cấu hình điểm số
# ─────────────────────────────────────────────────────────────────
TASK_CONFIG = {
    "Task01_AppConfigTest": {
        "label": "Task 1 — AppConfig",
        "max_pts": 10,
        "test_pts": {
            "t1_hasConfigurationAnnotation":  3,
            "t2_hasComponentScanAnnotation":  4,
            "t3_hasEnableAspectJAutoProxy":   3,
            "t4_contextLoads":                0,   # bonus
        },
    },
    "Task02_StudentPojoTest": {
        "label": "Task 2 — Student POJO",
        "max_pts": 20,
        "test_pts": {
            "t1_hasNameField":   2,
            "t2_hasAgeField":    2,
            "t3_hasConstructor": 4,
            "t4_getName":        3,
            "t5_getAge":         3,
            "t6_setName":        3,
            "t7_setAge":         3,
        },
    },
    "Task03_StudentServiceTest": {
        "label": "Task 3 — StudentServiceImpl",
        "max_pts": 20,
        "test_pts": {
            "t1_hasServiceAnnotation":         5,
            "t2_beanExistsInContext":          5,
            "t3_addStudentPrintsCorrectly":    5,
            "t4_getStudentInfoReturnsNonNull": 5,
        },
    },
    "Task04_LifecycleBeanTest": {
        "label": "Task 4 — LifecycleBean",
        "max_pts": 25,
        "test_pts": {
            "t1_hasInitializedField":            3,
            "t2_hasPostConstructMethod":        10,
            "t3_hasPreDestroyMethod":           10,
            "t4_isInitializedAfterContextStart": 0,  # bonus
            "t5_postConstructPrintsMessage":     0,  # bonus
            "t6_preDestroyPrintsMessage":        2,
        },
    },
    "Task05_AOPTest": {
        "label": "Task 5 — LoggingAspect",
        "max_pts": 25,
        "test_pts": {
            "t1_hasAspectAnnotation":                       2,
            "t2_hasComponentAnnotation":                    3,
            "t3_hasBeforeAdvice":                           7,
            "t4_hasAfterReturningAdvice":                   7,
            "t5_hasAroundAdvice":                           6,
            "t6_beforeAdviceFiresOnAddStudent":             0,  # bonus
            "t7_afterReturningAdviceFiresOnGetStudentInfo": 0,  # bonus
            "t8_aroundAdviceMeasuresTime":                  0,  # bonus
        },
    },
}

TOTAL_MAX = sum(v["max_pts"] for v in TASK_CONFIG.values())


# ─────────────────────────────────────────────────────────────────
# Data classes
# ─────────────────────────────────────────────────────────────────
@dataclass
class TestResult:
    name: str
    passed: bool
    failure_msg: Optional[str] = None


@dataclass
class TaskResult:
    class_name: str
    label: str
    max_pts: int
    earned_pts: int = 0
    tests: List[TestResult] = field(default_factory=list)
    missing_report: bool = False


# ─────────────────────────────────────────────────────────────────
# Helpers
# ─────────────────────────────────────────────────────────────────
def find_report(reports_dir: str, class_name: str) -> Optional[str]:
    for fname in os.listdir(reports_dir):
        if class_name in fname and fname.endswith(".xml"):
            return os.path.join(reports_dir, fname)
    return None


def parse_report(xml_path: str) -> List[TestResult]:
    results = []
    tree = ET.parse(xml_path)
    root = tree.getroot()
    for tc in root.findall("testcase"):
        name = tc.get("name", "")
        failure = tc.find("failure")
        error   = tc.find("error")
        skipped = tc.find("skipped")
        if failure is not None:
            results.append(TestResult(name=name, passed=False,
                                      failure_msg=failure.text or failure.get("message")))
        elif error is not None:
            results.append(TestResult(name=name, passed=False,
                                      failure_msg=error.text or error.get("message")))
        elif skipped is not None:
            results.append(TestResult(name=name, passed=False,
                                      failure_msg="Test bị skip"))
        else:
            results.append(TestResult(name=name, passed=True))
    return results


def score_task(task_result: TaskResult, cfg: dict) -> None:
    test_pts = cfg["test_pts"]
    earned = 0
    for tr in task_result.tests:
        if tr.passed and tr.name in test_pts:
            earned += test_pts[tr.name]
    task_result.earned_pts = min(earned, task_result.max_pts)


def ascii_bar(earned: int, max_pts: int, width: int = 20) -> str:
    filled = int(round(width * earned / max_pts)) if max_pts > 0 else 0
    return "█" * filled + "░" * (width - filled)


def grade_letter(score: int, total: int) -> str:
    pct = score / total * 100 if total > 0 else 0
    if pct >= 90: return "A"
    if pct >= 75: return "B"
    if pct >= 60: return "C"
    if pct >= 50: return "D"
    return "F"


# ─────────────────────────────────────────────────────────────────
# Output: plain text (terminal)
# ─────────────────────────────────────────────────────────────────
def print_text(task_results: List[TaskResult], total_earned: int) -> None:
    print()
    print("=" * 62)
    print("  HSF302 Lab 02 — Grade Report")
    print("=" * 62)
    print()

    for tr in task_results:
        if tr.missing_report:
            status = "NO REPORT"
            pts_str = f"[ ??/{tr.max_pts:2d} ]"
            bar_str = "░" * 20
        else:
            pts_str = f"[ {tr.earned_pts:2d}/{tr.max_pts:2d} ]"
            bar_str = ascii_bar(tr.earned_pts, tr.max_pts)
            status = ("PASSED"  if tr.earned_pts >= tr.max_pts else
                      "PARTIAL" if tr.earned_pts  > 0          else
                      "FAILED")

        label = tr.label.ljust(30)
        print(f"  {label} {pts_str} {bar_str}  {status}")

        for test in tr.tests:
            pts = TASK_CONFIG[tr.class_name]["test_pts"].get(test.name, 0)
            if not test.passed and pts > 0:
                short = test.name[:50]
                print(f"      ✗ {short} (-{pts}pts)")
                if test.failure_msg:
                    line = test.failure_msg.strip().split("\n")[0][:80]
                    print(f"        → {line}")

    print()
    print("-" * 62)
    letter = grade_letter(total_earned, TOTAL_MAX)
    total_bar = ascii_bar(total_earned, TOTAL_MAX, width=30)
    print(f"  {'TOTAL'.ljust(30)} [{total_earned:3d}/{TOTAL_MAX}]  {letter}")
    print(f"  {total_bar}")
    print()

    if total_earned < TOTAL_MAX:
        print("  Task chưa hoàn thành:")
        for tr in task_results:
            if not tr.missing_report and tr.earned_pts < tr.max_pts:
                print(f"    • {tr.label}: còn thiếu {tr.max_pts - tr.earned_pts} điểm")
        print()


# ─────────────────────────────────────────────────────────────────
# Output: Markdown (GitHub Step Summary / PR comment)
# ─────────────────────────────────────────────────────────────────
def print_markdown(task_results: List[TaskResult], total_earned: int) -> None:
    lines = []
    letter = grade_letter(total_earned, TOTAL_MAX)
    emoji  = {"A": "🏆", "B": "✅", "C": "⚠️", "D": "⚠️", "F": "❌"}.get(letter, "")

    lines.append(f"## {emoji} HSF302 Lab 02 — Grade: **{total_earned}/{TOTAL_MAX}** ({letter})")
    lines.append("")

    # Progress bar (Unicode blocks)
    pct = int(total_earned / TOTAL_MAX * 20) if TOTAL_MAX > 0 else 0
    prog = "🟩" * pct + "⬜" * (20 - pct)
    lines.append(f"{prog} `{total_earned}/{TOTAL_MAX} pts`")
    lines.append("")

    # Per-task table
    lines.append("| Task | Điểm | Trạng thái |")
    lines.append("|------|------|------------|")

    for tr in task_results:
        if tr.missing_report:
            row_pts   = f"??/{tr.max_pts}"
            row_status = "⬜ No report"
        else:
            row_pts = f"{tr.earned_pts}/{tr.max_pts}"
            if tr.earned_pts >= tr.max_pts:
                row_status = "✅ Passed"
            elif tr.earned_pts > 0:
                row_status = f"🟡 Partial (+{tr.earned_pts})"
            else:
                row_status = "❌ Failed"
        lines.append(f"| {tr.label} | {row_pts} | {row_status} |")

    lines.append("")

    # Failed tests detail
    failed_details = []
    for tr in task_results:
        if tr.missing_report:
            continue
        for test in tr.tests:
            pts = TASK_CONFIG[tr.class_name]["test_pts"].get(test.name, 0)
            if not test.passed and pts > 0:
                msg = ""
                if test.failure_msg:
                    msg = test.failure_msg.strip().split("\n")[0][:120]
                failed_details.append((tr.label, test.name, pts, msg))

    if failed_details:
        lines.append("<details>")
        lines.append("<summary>❌ Chi tiết các test chưa qua</summary>")
        lines.append("")
        lines.append("| Task | Test | Điểm bị trừ | Lý do |")
        lines.append("|------|------|-------------|-------|")
        for task_label, tname, pts, msg in failed_details:
            msg_escaped = msg.replace("|", "\\|")[:80]
            lines.append(f"| {task_label} | `{tname}` | -{pts}pts | {msg_escaped} |")
        lines.append("")
        lines.append("</details>")
        lines.append("")

    # Hướng dẫn tiếp theo
    if total_earned < TOTAL_MAX:
        lines.append("> 💡 **Hướng dẫn:** Xem README.md để biết cách implement từng TODO.")
        lines.append("> Sau khi sửa, commit & push để CI chạy lại tự động.")
    else:
        lines.append("> 🎉 **Tuyệt vời!** Bạn đã hoàn thành tất cả các task!")

    print("\n".join(lines))


# ─────────────────────────────────────────────────────────────────
# Main
# ─────────────────────────────────────────────────────────────────
def main() -> int:
    parser = argparse.ArgumentParser(description="HSF302 Lab 02 Auto-Grader")
    parser.add_argument(
        "--markdown", action="store_true",
        help="Output Markdown (for GitHub Step Summary / PR comment)"
    )
    args = parser.parse_args()

    reports_dir = os.path.join("target", "surefire-reports")
    if not os.path.isdir(reports_dir):
        msg = (
            "\n[ERROR] Không tìm thấy target/surefire-reports/\n"
            "        Hãy chạy trước:  mvn test -fae\n"
        )
        if args.markdown:
            print("## ❌ Grade Report\n\nChưa có surefire report. Chạy `mvn test -fae` trước.")
        else:
            print(msg)
        return 1

    task_results: List[TaskResult] = []
    for class_name, cfg in TASK_CONFIG.items():
        tr = TaskResult(class_name=class_name, label=cfg["label"], max_pts=cfg["max_pts"])
        xml_path = find_report(reports_dir, class_name)
        if xml_path is None:
            tr.missing_report = True
        else:
            tr.tests = parse_report(xml_path)
            score_task(tr, cfg)
        task_results.append(tr)

    total_earned = sum(tr.earned_pts for tr in task_results if not tr.missing_report)

    if args.markdown:
        print_markdown(task_results, total_earned)
    else:
        print_text(task_results, total_earned)

    return 0 if total_earned >= TOTAL_MAX else 1


if __name__ == "__main__":
    sys.exit(main())
