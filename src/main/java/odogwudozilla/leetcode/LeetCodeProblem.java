package odogwudozilla.leetcode;


public class LeetCodeProblem {
    private Integer problemId;
    private Integer frontEndId;
    private Integer difficultyLevel;
    private String difficultyLevelName;
    private String title;
    private String titleSlug;
    private String classBody;
    private String className;
    private String classFileLocation;
    private String textFileLocation;
    private String directUrl;
    private String classContent;

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

    public Integer getFrontEndId() {
        return frontEndId;
    }

    public void setFrontEndId(Integer frontEndId) {
        this.frontEndId = frontEndId;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDifficultyLevelName() {
        return difficultyLevelName;
    }

    public void setDifficultyLevelName(String difficultyLevelName) {
        this.difficultyLevelName = difficultyLevelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleSlug() {
        return titleSlug;
    }

    public void setTitleSlug(String titleSlug) {
        this.titleSlug = titleSlug;
    }

    public StringBuilder getHtmlString() {
        return htmlString;
    }

    public void setHtmlString(StringBuilder htmlString) {
        this.htmlString = htmlString;
    }

    public String getClassBody() {
        return classBody;
    }

    public void setClassBody(String classBody) {
        this.classBody = classBody;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassFileLocation() {
        return classFileLocation;
    }

    public void setClassFileLocation(String classFileLocation) {
        this.classFileLocation = classFileLocation;
    }

    public String getTextFileLocation() {
        return textFileLocation;
    }

    public void setTextFileLocation(String textFileLocation) {
        this.textFileLocation = textFileLocation;
    }

    public String getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }

    public String getClassContent() {
        return classContent;
    }

    public void setClassContent(String classContent) {
        this.classContent = classContent;
    }

}
