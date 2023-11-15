package odogwudozilla.helperClasses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    // Get the project's base directory (the location of the pom.xml file in a maven project, for instance)
    public static final String PROJECT_BASE_DIRECTORY = System.getProperty("user.dir")  + File.separator
            + "src" + File.separator
            + "main" + File.separator;

    private static boolean useResourcePath = true;

    // The 'resource' directory path
    public static final String RESOURCE_DIRECTORY_PATH = PROJECT_BASE_DIRECTORY + File.separator
            + "resources" + File.separator;

    // The 'main' directory path
    public static final String MAIN_DIR_PATH = PROJECT_BASE_DIRECTORY + File.separator
            + "java" + File.separator;

    public static void createFileOrDirectoryIfNotExists(String path) {
        File fileOrDir = new File(appendResourceOrMainPath(path));

        if (!fileExists(path)) {
            try {
                if (path.endsWith(File.separator)) {
                    // If the path ends with a separator, it's a directory
                    fileOrDir.mkdirs();
                } else {
                    // If it doesn't end with a separator, it's a file
                    fileOrDir.getParentFile().mkdirs(); // Create parent directories if needed
                    fileOrDir.createNewFile();
                }
                log.info("File or directory created at: {}", path);
            } catch (IOException e) {
                log.error("Failed to create file or directory: {}", e.getMessage());
            }
        } else {
            log.info("File or directory already exists at: {}", path);
        }
    }

    public static void saveJsonData(String sourceFile, String fileDirectory, boolean fromUrl) throws IOException {
        String jsonResponse;
        try (WebClient webClient = new WebClient()) {
            fileDirectory = appendResourceOrMainPath(fileDirectory);

            if (fromUrl) {
                jsonResponse = getJsonData(webClient, sourceFile);
            } else {
                jsonResponse = sourceFile;
            }
        }
        try (FileWriter fileWriter = new FileWriter(fileDirectory)) {
            fileWriter.write(jsonResponse);
        }
    }

    public static String readTextOrJsonFile(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of(appendResourceOrMainPath(filePath)));
        return new String(bytes);
    }

    public static String getJsonData(WebClient webClient, String apiUrl) throws IOException {
        WebRequest request = new WebRequest(new java.net.URL(apiUrl));
        request.setHttpMethod(HttpMethod.GET);
        WebResponse response  = webClient.loadWebResponse(request);
        return response.getContentAsString();
    }


    public static JsonNode readJsonData(String source, boolean isFilePath) {
        JsonNode fileRootNode = null;
        try {
            // Create an ObjectMapper object to read JSON data from the file
            ObjectMapper objectMapper = new ObjectMapper();

            if (isFilePath) {
                // Read JSON data from the file and parse it into a JsonNode object
                try (InputStream inputStream = CommonUtils.class.getClassLoader().getResourceAsStream(source)) {
                    fileRootNode = objectMapper.readTree(inputStream);
                }
            } else {
                // Read JSON data from the source string and parse it into a JsonNode object
                fileRootNode = objectMapper.readTree(source);

            }

        } catch (IOException e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }

        return fileRootNode;
    }

    public static void writeToFile(String path, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appendResourceOrMainPath(path)))) {
            writer.write(content);
            log.info("Object written to File {}", path);
        } catch (IOException e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static String appendResourceOrMainPath(String filePath) {
        return useResourcePath ? RESOURCE_DIRECTORY_PATH + filePath :
        MAIN_DIR_PATH + filePath;
    }

    public static String formatTitleForClassName(String title) {
        String cleanedTitle = stripCharsAfterFirstSpecialChar(title);

        // The regex pattern to match all beginning numeric digits
        Pattern pattern = Pattern.compile("^\\d+(?=\\D)");
        return checkForLeadingDigits(cleanedTitle, pattern);
    }

    public static String stripCharsAfterFirstSpecialChar(String input) {
        // Regex to match all no
        Pattern pattern = Pattern.compile("^(.*?)[^a-zA-Z0-9]+.*$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher.group(1);
        }

        return input;
    }

    /**
     * Processes a string by applying the regex pattern to match the digits at the beginning, appends a text prefix
     * and returns the corresponding concatenation.
     * @param input   The input string.
     * @param pattern The regex pattern.
     * @return The concatenated string pattern or original string if no match is found.
     */
    private static String checkForLeadingDigits(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return "The" + matcher.group() + input.substring(matcher.end());
        }

        return input;
    }


    public static boolean fileExists(String filePath) {
        return new File(appendResourceOrMainPath(filePath)).exists();
    }

    public static void setUseResourcePath(boolean useResourcePath) {
        CommonUtils.useResourcePath = useResourcePath;
    }
}
