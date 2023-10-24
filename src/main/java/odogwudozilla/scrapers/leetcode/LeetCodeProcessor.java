package odogwudozilla.scrapers.leetcode;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.JsonNode;

import static odogwudozilla.scrapers.helperClasses.CommonUtils.createFileOrDirectoryIfNotExists;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.fileExists;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.readJsonData;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.readTextOrJsonFile;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.saveJsonData;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.setUseResourcePath;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.writeToFile;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEMS_LIST;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEMS_TOTAL;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM_DIFFICULTY;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM_DIFFICULTY_LEVEL;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM_IS_PAID;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM_QUESTION_ID;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM_QUESTION_ID_FRONTEND;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM_QUESTION_TITLE;
import static odogwudozilla.scrapers.leetcode.LeetCodeEnums.PROBLEM_QUESTION_TITLE_SLUG;


public class LeetCodeProcessor {
    static final String PROBLEMS_BASE_URL = "https://leetcode.com/problems/";
    static final String API_BASE_URL = "https://leetcode.com/api/problems/algorithms/";
    static final String PROBLEMS_DIR = "leetcode/problems/";
    static final String API_FILE_DIR = "leetcode/algorithms/";
    static final String API_FILE_NAME = "algorithms.json";
    private static final String PROBLEMS_COUNTER_FILE_NAME = "problemsCounter.txt";
    private Integer problemsCounter;
    private static final String problemsCounterPath = API_FILE_DIR + PROBLEMS_COUNTER_FILE_NAME;
    private static Integer totalProblemsForDownload;
    private JsonNode problemsList;
    private LeetCodeProblem problem;
    private static final Logger log = Logger.getLogger(LeetCodeProcessor.class.getName());

    public LeetCodeProcessor() {
        // empty for now
    }

    public static void main(String[] args) throws IOException {
        LeetCodeProcessor processor = new LeetCodeProcessor();
        processor.retrieveApiJson();
        processor.fillAndProcessProblem();
    }

    private void retrieveApiJson() throws IOException {
        createFileOrDirectoryIfNotExists(problemsCounterPath);
        problemsCounter = Integer.valueOf(readTextOrJsonFile(problemsCounterPath));

        // Create the algorithm file if it does not exist
        String apiFilePath = API_FILE_DIR + API_FILE_NAME;
        createFileOrDirectoryIfNotExists(apiFilePath);

        JsonNode algorithmsNode = readJsonData(apiFilePath, true);
        if (!algorithmsNode.fields().hasNext() || 2000 > algorithmsNode.get(PROBLEMS_TOTAL.code).asInt()) {
            // replace the existing algorithms file only if the local version is empty or less than 2000 .
            String pageUrl = API_BASE_URL;
            saveJsonData(pageUrl, apiFilePath, true);
            // Reload the file
            algorithmsNode = readJsonData(apiFilePath, true);
        }

        totalProblemsForDownload = Math.max(problemsCounter, algorithmsNode.get(PROBLEMS_TOTAL.code).asInt());

        problemsList = algorithmsNode.get(PROBLEMS_LIST.code);

    }

    private void fillAndProcessProblem() {
        int counter = 0;
        // Since the list of problems is in descending order, Start downloading from the last problem on the list
        for (int probs = (totalProblemsForDownload - 1); probs > 0; probs--) {

            JsonNode currentProblemNode = problemsList.get(probs);
            if (counter > 5) break;
            if (currentProblemNode.get(PROBLEM_IS_PAID.code).asBoolean()) {
                counter++;
                continue;
            }
            JsonNode problemNode = currentProblemNode.get(PROBLEM.code);
            int questionId = problemNode.get(PROBLEM_QUESTION_ID.code).asInt();

            problem = new LeetCodeProblem(questionId);
            problem.setFrontEndId(problemNode.get(PROBLEM_QUESTION_ID_FRONTEND.code).asInt());
            problem.setTitle(problemNode.get(PROBLEM_QUESTION_TITLE.code).asText());
            problem.setTitleSlug(problemNode.get(PROBLEM_QUESTION_TITLE_SLUG.code).asText());
            problem.setDifficultyLevel(currentProblemNode.get(PROBLEM_DIFFICULTY.code).get(PROBLEM_DIFFICULTY_LEVEL.code).asInt());
            problem.setDifficultyLevelName(problem.translateDifficultyIdToText(problem.getDifficultyLevel()));
            problem.setClassName(problem.getTitle().replace(" ", ""));
            problem.setDirectUrl(PROBLEMS_BASE_URL + problem.getTitleSlug());
            problem.setTextFileLocation(PROBLEMS_DIR + problem.getDifficultyLevelName().toLowerCase() + "/" + problem.getClassName() + ".txt");

//            if (fileExists(problem.getTextFileLocation())) {
//                // This problem is already processed. Skip to the next.
//                log.info("File '" + problem.getTextFileLocation() + "' already exists. Skipping to the next");
//                counter++;
//                continue;
//            }

            downloadProblem();

            String classContent = constructClassContent();

            writeProblemToClassAndFile(classContent);

            counter++;
        }
        // Update the problems counter
        if (counter > problemsCounter) {
            problemsCounter = counter;
        }
        setUseResourcePath(true);
        writeToFile(problemsCounterPath, problemsCounter.toString());
    }

    private void writeProblemToClassAndFile(String classContent) {

        // Construct paths and write to a class file
        String problemDirectoryForClass = "odogwudozilla/scrapers/" + PROBLEMS_DIR + problem.getDifficultyLevelName().toLowerCase() + "/";
        problem.setClassFileLocation(problemDirectoryForClass + problem.getClassName() + ".java");
        setUseResourcePath(false);
        createFileOrDirectoryIfNotExists(problem.getClassFileLocation());
        // Write the content to the specified output file
        writeToFile(problem.getClassFileLocation(), classContent);
        log.info("Problem text file written to " + problem.getClassFileLocation());

        // Construct paths and write to a text file
        setUseResourcePath(true);
        createFileOrDirectoryIfNotExists(problem.getTextFileLocation());
        // Write the content to the specified output file
        writeToFile(problem.getTextFileLocation(), classContent);
        log.info("Problem text file written to " + problem.getTextFileLocation());

    }

    private String constructClassContent() {
        String packageStatement = "package " + LeetCodeProcessor.class.getPackage().getName() + ".problems." + problem.getDifficultyLevelName().toLowerCase() + ";";

        String titleSection = " *<h2>" + "No. " + problem.getFrontEndId() + ": " + problem.getTitle() + "</h2>\n" +
                              " *" + "Link: <em><a href=\"" + problem.getDirectUrl() + "\">" + problem.getTitle() + "</a></em>\n" +
                              " *" + "Difficulty: <strong>" + problem.getDifficultyLevelName() + "</strong>" +
                              "<hr>\n\n";

        // Put the title and htmlString in comments
        StringBuilder commentLines = new StringBuilder("/**\n").append(titleSection);
        for (String line : problem.getHtmlString().toString().split("\r?\n")) {
            commentLines.append(" *").append(line).append("\n");
        }
        commentLines.append(" */");

        // Build the class declaration
        String classDeclaration = "public class " +
                                  problem.getClassName() +
                                  " {";

        String mainMethod = """
                        public static void main(String[] args) {
                            System.out.println("Hello, World!");
                        }""";

        return packageStatement + "\n\n" +
               commentLines + "\n\n" +
               classDeclaration + "\n\n" +
               mainMethod + "\n\n" +
               problem.getClassBody() + "\n}";
    }


    private void downloadProblem() {
        WebDriver chromeDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(5));
        //Open the page in Chrome
        chromeDriver.get(problem.getDirectUrl());

        // Create an HTML string to store the contents
        StringBuilder htmlString = new StringBuilder("<html><body>");

        /// Explicitly wait for the "div.xFUwe" element to be present
        WebElement divElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.xFUwe")));

        // Find all the p, pre, ul elements within the div
        List<WebElement> paragraphAndPreElements = divElement.findElements(By.cssSelector("p, pre, ul"));
        // Iterate through the list of paragraph elements and save their text outerHTML
        for (WebElement element : paragraphAndPreElements) {
            String elementHtml = element.getAttribute("outerHTML");
            htmlString.append(elementHtml);
        }
        // Close the html string
        htmlString.append("</body></html>");

        // Locate and click the button to open the dropdown for the programming language list
        WebElement dropdownClass = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.relative.notranslate")));
        WebElement dropdownButton = dropdownClass.findElement(By.tagName("button"));
        dropdownButton.click();

        // Locate the <div> element with the text "Java" using XPath
        WebElement javaOption = chromeDriver.findElement(By.xpath("//div[@class='whitespace-nowrap' and text()='Java']"));
        // Click the Java option to select it
        javaOption.click();
        // Explicitly wait for 10 seconds to ensure the effect of the click is loaded (i.e, the Java workspace)
        waitUntil(wait, Duration.ofSeconds(5));

        StringBuilder classBody = new StringBuilder(16);

        // Locate the top-level <div class="view-lines">
        WebElement viewLinesElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.view-lines")));

        // Locate all <div class="view-line"> elements within the top-level div
        List<WebElement> viewLineElements = viewLinesElement.findElements(By.cssSelector("div.view-line"));

        for (WebElement secondViewLineElement : viewLineElements) {
            // Locate all <span> elements with class names starting with 'mtk' within the second "div.view-line"(s)
            List<WebElement> spanElements = secondViewLineElement.findElements(By.cssSelector("span[class^='mtk']"));
            StringBuilder concatSpanText = new StringBuilder(16);
            for (WebElement span : spanElements) {
                String spanText = span.getText();
                // Concatenate each span text value
                concatSpanText.append(spanText);
            }
            // Skip class name definitions because we will use a custom one
            if (concatSpanText.toString().contains("class")) continue;
            // Build the class body from the span text values
            classBody.append("//").append(concatSpanText);
            classBody.append("\n");
        }


        System.out.println(classBody);
        problem.setClassBody(classBody.toString());
        problem.setHtmlString(htmlString);

        log.info("Problem page '" + problem.getTitleSlug() + "' retrieved");
        // We are done with this page. Close it.
        chromeDriver.quit();
    }

    private void waitUntil(WebDriverWait wait, Duration duration) {
        // Wait for a custom condition (e.g., wait for 5 seconds)
        wait.until(driver1 -> {
            try {
                Thread.sleep(duration.toMillis()); // Sleep for the duration
                return true; // Return true after waiting for the duration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });
    }
}
