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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.JsonNode;
import odogwudozilla.scrapers.helperClasses.CommonUtils;

import static odogwudozilla.scrapers.helperClasses.CommonUtils.createFileOrDirectoryIfNotExists;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.readJsonData;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.readTextOrJsonFile;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.saveJsonData;
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
    static final String ALGORITHMS_BASE_URL = "https://leetcode.com/problems/";
    static final String API_BASE_URL = "https://leetcode.com/api/problems/algorithms/";
    static final String PROBLEMS_DIR = "leetcode/problems/";
    static final String API_FILE_DIR = PROBLEMS_DIR + "algorithms/";
    static final String API_FILE_NAME = "algorithms.json";
    static final String PROBLEM_DIR = "problem";
    private static final String PROBLEMS_COUNTER_FILE_NAME = "problemsCounter.txt";
    private Integer problemsCounter;
    private static Integer totalProblemsForDownload;
    private JsonNode problemsList;
    private LeetCodeProblem problem;
    private static final Logger log = Logger.getLogger(LeetCodeProcessor.class.getName());

    public LeetCodeProcessor() {
    }

    public static void main(String[] args) throws IOException {
        LeetCodeProcessor processor = new LeetCodeProcessor();
        processor.problem = null;
        processor.retrieveApiJson();
        processor.fillAndProcessProblem();
    }

    private void retrieveApiJson() throws IOException {
        String problemsCounterPath = API_FILE_DIR + PROBLEMS_COUNTER_FILE_NAME;
        createFileOrDirectoryIfNotExists(problemsCounterPath);
        problemsCounter = Integer.valueOf(readTextOrJsonFile(problemsCounterPath));

        // Create the algorithm file if it does not exist
        String apiFilePath = API_FILE_DIR + API_FILE_NAME;
        CommonUtils.createFileOrDirectoryIfNotExists(apiFilePath);

        JsonNode algorithmsNode = readJsonData(apiFilePath, true);
        if (problemsCounter < algorithmsNode.get(PROBLEMS_TOTAL.code).asInt()) {
            // replace the existing algorithms file only if the leetcode api size is greater than the local  version.
            String pageUrl = API_BASE_URL;
            saveJsonData(pageUrl, apiFilePath, true);
        }

        totalProblemsForDownload = Math.max(problemsCounter, algorithmsNode.get(PROBLEMS_TOTAL.code).asInt());

        problemsList = algorithmsNode.get(PROBLEMS_LIST.code);

    }

    private boolean fillAndProcessProblem() {
        int counter = 0;
        // Since the list of problems is in descending order, Start downloading from the last problem on the list
        for (int probs = (totalProblemsForDownload - 1); probs > 0; probs--) {

            JsonNode currentProblemNode = problemsList.get(probs);
            if (counter > 1) break;
            if (currentProblemNode.get(PROBLEM_IS_PAID.code).asBoolean()) {
                continue;
            }
            JsonNode problemNode = currentProblemNode.get(PROBLEM.code);
            int questionId = problemNode.get(PROBLEM_QUESTION_ID.code).asInt();

            problem = new LeetCodeProblem(questionId);
            problem.setFrontEndProblemId(problemNode.get(PROBLEM_QUESTION_ID_FRONTEND.code).asInt());
            problem.setProblemTitle(problemNode.get(PROBLEM_QUESTION_TITLE.code).asText());
            problem.setProblemTitleSlug(problemNode.get(PROBLEM_QUESTION_TITLE_SLUG.code).asText());
            problem.setProblemDifficultyLevel(currentProblemNode.get(PROBLEM_DIFFICULTY.code).get(PROBLEM_DIFFICULTY_LEVEL.code).asInt());
            problem.setProblemDifficulty(problem.translateDifficultyIdToText(problem.getProblemDifficultyLevel()));

            if (!downloadProblem(problem.getProblemTitleSlug())) {
                // handle error
                return false;
            }

            writeProblemToClass();

            counter++;
        }
        return true;
    }

    private void writeProblemToClass() {

    }


    private boolean downloadProblem(String questionTitleSlug) {
        WebDriver chromeDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        String pageUrl = ALGORITHMS_BASE_URL + questionTitleSlug;
        chromeDriver.get(pageUrl);

        // Create an HTML string to store the contents
        StringBuilder htmlString = new StringBuilder("<html><body>");

        /// Explicitly wait for the div element to be present
        WebElement divElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.xFUwe")));

        // Find all the p, pre, ul elements within the div
        List<WebElement> paragraphAndPreElements = divElement.findElements(By.cssSelector("p, pre, ul"));
        // Iterate through the list of paragraph elements and save their text outerHTML
        for (WebElement element : paragraphAndPreElements) {
            String elementHtml = element.getAttribute("outerHTML");
            htmlString.append(elementHtml);
        }


        // Locate and click the button to open the dropdown
        WebElement dropdownButton = chromeDriver.findElement(By.id("headlessui-listbox-button-:r2e:"));
        dropdownButton.click();

        // Locate the dropdown list (ul) by its ID
        //WebElement dropdownList = chromeDriver.findElement(By.id("headlessui-listbox-options-:r30:"));

        // Locate the desired option (li) by its ID "headlessui-listbox-option-:r32:"
        WebElement javaOption = chromeDriver.findElement(By.id("headlessui-listbox-option-:r32:"));

        // Click the Java option to select it
        javaOption.click();

        /// Explicitly wait for the div element to be present
        WebElement divElement1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.view-lines")));
        StringBuilder solutionMethod = new StringBuilder(16);

        // Locate the top-level <div class="view-lines">
        WebElement viewLinesElement = chromeDriver.findElement(By.cssSelector("div.view-lines"));

        // Locate all <div class="view-line"> elements within the top-level div
        List<WebElement> viewLineElements = viewLinesElement.findElements(By.cssSelector("div.view-line"));

        // Check if there is a second <div class="view-line"> element
        if (viewLineElements.size() >= 2) {
            // Get the second <div class="view-line"> element (index 1 in the list)
            WebElement secondViewLineElement = viewLineElements.get(1);

            // Locate all <span> elements with class names starting with 'mtk' within the second <div>
            List<WebElement> spanElements = secondViewLineElement.findElements(By.cssSelector("span[class^='mtk']"));


            // Iterate through the list of span elements and print their text
            for (WebElement span : spanElements) {
                String spanText = span.getText();
                solutionMethod.append(spanText);
            }
            // Close the curly brackets
            solutionMethod.append("\n }");
        }

        System.out.println(solutionMethod);
        problem.setProblemSolutionMethod(solutionMethod.toString());

        htmlString.append("</body></html>");
        problem.setHtmlString(htmlString);

        log.info("Problem page '" + questionTitleSlug + "' retrieved");

        chromeDriver.quit();
        return true;
    }
}
