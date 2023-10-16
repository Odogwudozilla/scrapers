package odogwudozilla.scrapers.helperClasses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;

public class CommonUtils {
    // Get the project's base directory (the location of the pom.xml file in a maven project, for instance)
    public static final String PROJECT_BASE_DIRECTORY = System.getProperty("user.dir")  + File.separator
            + "src" + File.separator
            + "main" + File.separator;

    private static boolean useResourcePath = true;

    // The resource directory path
    public static final String RESOURCE_DIRECTORY_PATH = PROJECT_BASE_DIRECTORY + File.separator
            + "resources" + File.separator;

    // The resource directory path
    public static final String MAIN_DIR_PATH = PROJECT_BASE_DIRECTORY + File.separator
            + "java" + File.separator;

    public static void createFileOrDirectoryIfNotExists(String path) {
        File fileOrDir = new File(appendResourcePath(path));

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
                System.out.println("File or directory created at: " + path);
            } catch (IOException e) {
                System.err.println("Failed to create file or directory: " + e.getMessage());
            }
        } else {
            System.out.println("File or directory already exists at: " + path);
        }
    }

    public static void saveJsonData(String sourceFile, String fileDirectory, boolean fromUrl) throws IOException {
        String jsonResponse;
        try (WebClient webClient = new WebClient()) {
            fileDirectory = appendResourcePath(fileDirectory);

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
        byte[] bytes = Files.readAllBytes(Path.of(appendResourcePath(filePath)));
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
            e.printStackTrace();
        }

        return fileRootNode;
    }


    private static WebClient initialise(WebClient webClient) {
        // disable javascript
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // disable css support
        webClient.getOptions().setCssEnabled(true);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());

        return webClient;
    }

    public static String appendResourcePath(String filePath) {
        return useResourcePath ? RESOURCE_DIRECTORY_PATH + filePath :
        MAIN_DIR_PATH + filePath;
    }

    public static boolean fileExists(String filePath) {
        return new File(appendResourcePath(filePath)).exists();
    }

    public static boolean isUseResourcePath() {
        return useResourcePath;
    }

    public static void setUseResourcePath(boolean useResourcePath) {
        CommonUtils.useResourcePath = useResourcePath;
    }
}
