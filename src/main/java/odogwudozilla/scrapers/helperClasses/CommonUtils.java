package odogwudozilla.scrapers.helperClasses;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;

public class CommonUtils {
    // Get the project's base directory (the location of the pom.xml file in a maven project, for instance)
    public static  final String PROJECT_BASE_DIRECTORY = System.getProperty("user.dir");
    // Get the resource directory path
    public static final String RESOURCE_DIRECTORY_PATH = PROJECT_BASE_DIRECTORY + File.separator
            + "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator;
    public static void createFileOrDirectoryIfNotExists(String path) {
        File fileOrDir = new File(RESOURCE_DIRECTORY_PATH + path);

        if (!fileOrDir.exists()) {
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

    public static void saveJsonFile(byte[] jsonData, String filePath) {
        try {
            // Copy the JSON data from the byte array to the file, replacing it if it exists
            Path destination = Path.of(RESOURCE_DIRECTORY_PATH + filePath);
            Files.copy(new ByteArrayInputStream(jsonData), destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("JSON data saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveJsonData(String sourceFile, String fileDirectory, boolean fromUrl) throws IOException {
        WebClient webClient = new WebClient();
        String jsonResponse;
        fileDirectory = RESOURCE_DIRECTORY_PATH + fileDirectory;
        if (fromUrl) {
            jsonResponse = getJsonData(webClient, sourceFile);
        } else {
            jsonResponse = sourceFile;
        }
        try (FileWriter fileWriter = new FileWriter(fileDirectory)) {
            fileWriter.write(jsonResponse);
        }
    }
    public static String getJsonData(WebClient webClient, String apiUrl) throws IOException {
        webClient = initialise(webClient);
        WebRequest request = new WebRequest(new java.net.URL(apiUrl));
        request.setHttpMethod(HttpMethod.GET);
        WebResponse response  = webClient.loadWebResponse(request);
        return response.getContentAsString();
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
}
