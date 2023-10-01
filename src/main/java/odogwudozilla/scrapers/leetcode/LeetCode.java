package odogwudozilla.scrapers.leetcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.databind.JsonNode;
import com.gargoylesoftware.htmlunit.WebClient;
import odogwudozilla.scrapers.helperClasses.CommonUtils;

import static odogwudozilla.scrapers.helperClasses.CommonUtils.createFileOrDirectoryIfNotExists;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.fileExists;
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


public class LeetCode {
    static final String ALGORITHMS_BASE_URL = "https://leetcode.com/problems/";
    static final String API_BASE_URL = "https://leetcode.com/api/problems/algorithms/";
    static final String PROBLEMS_DIR = "leetcode/problems/";
    static final String API_FILE_DIR = PROBLEMS_DIR + "algorithms/";
    static final String API_FILE_NAME = "algorithms.json";
    static final String PROBLEM_DIR = "problem";
    private static final String PROBLEMS_COUNTER_FILE_NAME = "problemsCounter.txt";
    //private static WebClient webClientele;
    private static Integer problemsCounter;
    private static final Logger log = Logger.getLogger(LeetCode.class.getName());

    //private static WebDriver chromeDriver = new ChromeDriver();

    public static void main(String[] args) throws IOException {
        retrieveApiJson();
    }

    private static void retrieveApiJson() throws IOException {
        String problemsCounterPath = API_FILE_DIR + PROBLEMS_COUNTER_FILE_NAME;
        createFileOrDirectoryIfNotExists(problemsCounterPath);
        problemsCounter = Integer.valueOf(readTextOrJsonFile(problemsCounterPath));

        // Create the algorithm file if it does not exist
        String apiFilePath = API_FILE_DIR + API_FILE_NAME;
        CommonUtils.createFileOrDirectoryIfNotExists(apiFilePath);

        JsonNode algorithmsNode = readJsonData(apiFilePath, true);


        if (problemsCounter < algorithmsNode.get(PROBLEMS_TOTAL.code).asInt()) {
            // replace the existing file if the leetcode api size is greater than the local
            String pageUrl = API_BASE_URL;
            saveJsonData(pageUrl, apiFilePath, true);
        }

        int total_problems = Math.max(problemsCounter, algorithmsNode.get(PROBLEMS_TOTAL.code).asInt());

        JsonNode problemsList = algorithmsNode.get(PROBLEMS_LIST.code);

        for (int probs = (total_problems - 1); probs > 0; probs--) {
            JsonNode currentProblemNode = problemsList.get(probs);

            if (currentProblemNode.get(PROBLEM_IS_PAID.code).asBoolean()) {
                continue;
            }
            JsonNode problem = currentProblemNode.get(PROBLEM.code);
            int question_id = problem.get(PROBLEM_QUESTION_ID.code).asInt();
            int frontend_question_id = problem.get(PROBLEM_QUESTION_ID_FRONTEND.code).asInt();
            String question__title = problem.get(PROBLEM_QUESTION_TITLE.code).asText();
            String question__title_slug = problem.get(PROBLEM_QUESTION_TITLE_SLUG.code).asText();
            int difficultyLevel = currentProblemNode.get(PROBLEM_DIFFICULTY.code).get(PROBLEM_DIFFICULTY_LEVEL.code).asInt();
        }





        //String pageUrl = ALGORITHMS_BASE_URL + "two-sum/";

        //chromeDriver.get(pageUrl);
        // Get the JSON data as an InputStream
        // byte[] jsonStream = chromeDriver.getPageSource().getBytes();
        //saveJsonFile(jsonStream, apiFile);
        log.info("Page retrieved");
        //chromeDriver.quit();
    }
}
