package odogwudozilla.scrapers.leetcode;


public class LeetCodeProblem {
    private Integer problemId;
    private Integer frontEndProblemId;
    private Integer problemDifficultyLevel;
    private String problemDifficulty;
    private String problemTitle;
    private String problemTitleSlug;

    private String problemSolutionMethod;

    // Create an HTML string to store the contents
    private  StringBuilder htmlString;

    public LeetCodeProblem(Integer problemId) {
        this.problemId = problemId;
    }

    public LeetCodeProblem() {
    }

    public String translateDifficultyIdToText(Integer difficultyLevel) {
        switch (difficultyLevel) {
            case 1 -> {
                return "Easy";
            }
            case 2 -> {
                return "Medium";
            }
            case 3 -> {
                return "Hard";
            }
            default -> {
                return "Unknown";
            }
        }

    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getFrontEndProblemId() {
        return frontEndProblemId;
    }

    public void setFrontEndProblemId(Integer frontEndProblemId) {
        this.frontEndProblemId = frontEndProblemId;
    }

    public Integer getProblemDifficultyLevel() {
        return problemDifficultyLevel;
    }

    public void setProblemDifficultyLevel(Integer problemDifficultyLevel) {
        this.problemDifficultyLevel = problemDifficultyLevel;
    }

    public String getProblemDifficulty() {
        return problemDifficulty;
    }

    public void setProblemDifficulty(String problemDifficulty) {
        this.problemDifficulty = problemDifficulty;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemTitleSlug() {
        return problemTitleSlug;
    }

    public void setProblemTitleSlug(String problemTitleSlug) {
        this.problemTitleSlug = problemTitleSlug;
    }

    public StringBuilder getHtmlString() {
        return htmlString;
    }

    public void setHtmlString(StringBuilder htmlString) {
        this.htmlString = htmlString;
    }

    public String getProblemSolutionMethod() {
        return problemSolutionMethod;
    }

    public void setProblemSolutionMethod(String problemSolutionMethod) {
        this.problemSolutionMethod = problemSolutionMethod;
    }

}
