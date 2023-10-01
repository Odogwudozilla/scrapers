package odogwudozilla.scrapers.leetcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.gargoylesoftware.htmlunit.WebClient;
import odogwudozilla.scrapers.helperClasses.CommonUtils;

import static odogwudozilla.scrapers.helperClasses.CommonUtils.saveJsonData;
import static odogwudozilla.scrapers.helperClasses.CommonUtils.saveJsonFile;


public class LeetCode {
    static final String ALGORITHMS_BASE_URL = "https://leetcode.com/problems/";
    static final String API_BASE_URL = "https://leetcode.com/api/problems/algorithms/";
    static final String PROBLEMS_DIR = "leetcode/problems/";
    static final String API_FILE_DIR = PROBLEMS_DIR + "algorithms/";
    static final String API_FILE_NAME = "algorithms.json";
    static final String PROBLEM_DIR = "problem";
    private static WebClient webClientele;
    private static final Logger log = Logger.getLogger(LeetCode.class.getName());

    private static WebDriver chromeDriver = new ChromeDriver();

    public static void main(String[] args) throws IOException {
        retrieveApiJson();
    }

    private static void retrieveApiJson() throws IOException {
        // Create the algorithm file if it does not exist
        String apiFile = API_FILE_DIR + API_FILE_NAME;
        CommonUtils.createFileOrDirectoryIfNotExists(apiFile);


        //String pageUrl = ALGORITHMS_BASE_URL + "two-sum/";
        String pageUrl = API_BASE_URL;
        saveJsonData(pageUrl, apiFile, true);

        //chromeDriver.get(pageUrl);
        // Get the JSON data as an InputStream
        // byte[] jsonStream = chromeDriver.getPageSource().getBytes();
        //saveJsonFile(jsonStream, apiFile);
        log.info("Page retrieved");
        //chromeDriver.quit();
    }
}
