package odogwudozilla.leetcode;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.gargoylesoftware.htmlunit.WebClient;
import odogwudozilla.helperClasses.CommonUtils;


public class LeetCodeProcessor {
    static final String PROBLEMS_BASE_URL = "https://leetcode.com/problems/";
    static final String API_BASE_URL = "https://leetcode.com/api/problems/algorithms/";
    static final String PROBLEMS_DIR = "leetcode/problems/";
    static final String API_FILE_DIR = "leetcode/algorithms/";
    static final String API_FILE_NAME = "algorithms.json";
    private static final String PROBLEMS_COUNTER_FILE_NAME = "problemsCounter.txt";
    private Integer nrOfProcessedProblems;
    private static final String PROBLEMS_COUNTER_PATH = API_FILE_DIR + PROBLEMS_COUNTER_FILE_NAME;
    private Integer totalProblemsForDownload;
    private JsonNode problemsList;
    private LeetCodeProblem problem;
    private static final Logger log = LoggerFactory.getLogger(LeetCodeProcessor.class);

    public LeetCodeProcessor() {
        // empty for now
    }

    public static void main(String[] args) throws IOException {
        LeetCodeProcessor processor = new LeetCodeProcessor();
        log.info("retrieveApiJson() - Starting retrieval of the API JSON... \n");
        processor.retrieveApiJson();
        log.info("retrieveApiJson()- Retrieval of the API JSON complete... \n\n");

        log.info("fillAndProcessProblem() - Starting fill and process... \n");
        processor.fillAndProcessProblem();
        log.info("fillAndProcessProblem() - Fill and process complete... \n");
    }

    private void retrieveApiJson() throws IOException {
        CommonUtils.createFileOrDirectoryIfNotExists(PROBLEMS_COUNTER_PATH);
        nrOfProcessedProblems = Integer.valueOf(CommonUtils.readTextOrJsonFile(PROBLEMS_COUNTER_PATH));
        log.info("Number of previously processed items: {}", nrOfProcessedProblems);

        // Create the algorithm file if it does not exist
        String apiFilePath = API_FILE_DIR + API_FILE_NAME;
        CommonUtils.createFileOrDirectoryIfNotExists(apiFilePath);
        String pageUrl = API_BASE_URL;

        JsonNode algorithmsNodeFromFile = CommonUtils.readJsonData(apiFilePath, true);
        JsonNode algorithmsNodeFromUrl = CommonUtils.readJsonData(CommonUtils.getJsonData(new WebClient(), pageUrl), false);

        // replace the existing algorithms file only if the local version is empty, or it's problem size is less than the online version.
        if (!algorithmsNodeFromFile.fields().hasNext()
                || algorithmsNodeFromFile.get(LeetCodeEnums.PROBLEMS_TOTAL.code).asInt() < algorithmsNodeFromUrl.get(LeetCodeEnums.PROBLEMS_TOTAL.code).asInt()) {
            // (re)Download the online version
            CommonUtils.saveJsonData(pageUrl, apiFilePath, true);
            // Reload the file
            algorithmsNodeFromFile = CommonUtils.readJsonData(apiFilePath, true);
        }
        // Set other instance variables needed for further processing
        totalProblemsForDownload = algorithmsNodeFromFile.get(LeetCodeEnums.PROBLEMS_TOTAL.code).asInt();
        problemsList = algorithmsNodeFromFile.get(LeetCodeEnums.PROBLEMS_LIST.code);

    }

    private void fillAndProcessProblem() {
        int processedItemsCounter = 0;
        int batchCount = nrOfProcessedProblems + 10; // We want to process only this number of items per run of this processor.

        // Since the list of problems is in descending order, Start processing from the last problem on the list
        int probs = totalProblemsForDownload - 1;
        for (; probs > 0; probs--) {

            JsonNode currentProblemNode = problemsList.get(probs);

            if (batchCount == 0) break;

            if (currentProblemNode.get(LeetCodeEnums.PROBLEM_IS_PAID.code).asBoolean()) {
                // Skip problems that require paid access.
                processedItemsCounter++;
                batchCount--;
                log.info("Problem '{}' is paid version only. Skipping...", currentProblemNode.get(LeetCodeEnums.PROBLEM.code).get(LeetCodeEnums.PROBLEM_QUESTION_TITLE.code).asText());
                continue;
            }

            JsonNode problemNode = currentProblemNode.get(LeetCodeEnums.PROBLEM.code);
            int questionId = problemNode.get(LeetCodeEnums.PROBLEM_QUESTION_ID.code).asInt();

            problem = new LeetCodeProblem(questionId);
            problem.setFrontEndId(problemNode.get(LeetCodeEnums.PROBLEM_QUESTION_ID_FRONTEND.code).asInt());
            problem.setTitle(problemNode.get(LeetCodeEnums.PROBLEM_QUESTION_TITLE.code).asText());
            problem.setTitleSlug(problemNode.get(LeetCodeEnums.PROBLEM_QUESTION_TITLE_SLUG.code).asText());
            problem.setDifficultyLevel(currentProblemNode.get(LeetCodeEnums.PROBLEM_DIFFICULTY.code).get(LeetCodeEnums.PROBLEM_DIFFICULTY_LEVEL.code).asInt());
            problem.setDifficultyLevelName(problem.translateDifficultyIdToText(problem.getDifficultyLevel()));
            problem.setClassName(CommonUtils.stripCharsAfterFirstSpecialChar(problem.getTitle().replace(" ", "")));
            problem.setDirectUrl(PROBLEMS_BASE_URL + problem.getTitleSlug());
            problem.setTextFileLocation(PROBLEMS_DIR + problem.getDifficultyLevelName().toLowerCase() + "/" + problem.getClassName() + ".txt");

            if (CommonUtils.fileExists(problem.getTextFileLocation())) {
                // This problem is already processed. Skip to the next.
                log.info("File '{}' already exists. Skipping to the next", problem.getTextFileLocation());
                processedItemsCounter++;
                batchCount--;
                continue;
            }
            // Download the problem from its specific page.
            downloadProblem();
            // Construct the content for the particular problem files
            problem.setClassContent(constructClassContent());
            // Save both class file and txt file (other file formats can be added later)
            writeProblemToClassAndFile();

            processedItemsCounter++;
            batchCount--;

        }

        // Update the problems counter
        if (processedItemsCounter > nrOfProcessedProblems) {
            nrOfProcessedProblems = processedItemsCounter;
        }
        CommonUtils.setUseResourcePath(true);
        CommonUtils.writeToFile(PROBLEMS_COUNTER_PATH, nrOfProcessedProblems.toString());

    }

    private void writeProblemToClassAndFile() {

        // Construct paths and write to a class file
        String problemDirectoryForClass = "odogwudozilla/" + PROBLEMS_DIR + problem.getDifficultyLevelName().toLowerCase() + "/";
        problem.setClassFileLocation(problemDirectoryForClass + problem.getClassName() + ".java");
        CommonUtils.setUseResourcePath(false);
        CommonUtils.createFileOrDirectoryIfNotExists(problem.getClassFileLocation());
        // Write the content to the specified output file
        CommonUtils.writeToFile(problem.getClassFileLocation(), problem.getClassContent());
        log.info("Problem text file written to {}", problem.getClassFileLocation());

        // Construct paths and write to a text file
        CommonUtils.setUseResourcePath(true);
        CommonUtils.createFileOrDirectoryIfNotExists(problem.getTextFileLocation());
        // Write the content to the specified output file
        CommonUtils.writeToFile(problem.getTextFileLocation(), problem.getClassContent());
        log.info("Problem text file written to {}", problem.getTextFileLocation());

    }

    private String constructClassContent() {
        String packageStatement = "package " + LeetCodeProcessor.class.getPackage().getName() + ".problems." + problem.getDifficultyLevelName().toLowerCase() + ";";

        String titleSection = " *<h2>" + "No. " + problem.getFrontEndId() + ": " + problem.getTitle() + "</h2>\n" +
                              " *" + "Link: <em><a href=\"" + problem.getDirectUrl() + "\">" + problem.getTitle() + "</a></em>\n" +
                              " *" + "Difficulty: <strong>" + problem.getDifficultyLevelName() + "</strong>" +
                              "<hr>\n\n"; // Horizontal line

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

        // Find all the p, pre, ul elements within the div.xFUwe
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


        log.info("Contents of the class file\n: {}", classBody);
        problem.setClassBody(classBody.toString());
        problem.setHtmlString(htmlString);

        log.info("Problem page '{}' retrieved", problem.getTitleSlug());
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
