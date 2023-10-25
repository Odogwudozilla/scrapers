package odogwudozilla.leetcode;

public enum LeetCodeEnums {
    PROBLEMS_TOTAL("num_total"),
    PROBLEMS_LIST("stat_status_pairs"),
    PROBLEM("stat"),
    PROBLEM_QUESTION_ID("question_id"),
    PROBLEM_QUESTION_TITLE("question__title"),
    PROBLEM_QUESTION_TITLE_SLUG("question__title_slug"),
    PROBLEM_QUESTION_ID_FRONTEND("frontend_question_id"),
    PROBLEM_DIFFICULTY("difficulty"),
    PROBLEM_DIFFICULTY_LEVEL("level"),
    PROBLEM_IS_PAID("paid_only");

    public final String code;

    LeetCodeEnums(String code) {
        this.code = code;
    }
}
