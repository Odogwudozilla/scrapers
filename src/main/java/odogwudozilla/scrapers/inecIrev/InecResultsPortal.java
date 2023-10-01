package odogwudozilla.scrapers.inecIrev;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;

import static java.nio.file.Files.createDirectories;

public class InecResultsPortal {

    public static final int SLEEP_TIME_IN_SECONDS = 1;
    private static WebClient webClientele;

    public static final String STATE_DATA_API_URL = "https://lv001-g.inecelectionresults.ng/api/v1/elections/";
    public static final String LOGIN_HASH = "63f8f25b594e164f8146a213";
    public static final String APPEND_LGA_TO_URL = "lga";
    public static final String APPEND_STATE_TO_URL = "state";
    public static final String APPEND_PUS_TO_URL = "pus?ward=";
    public static final String APPEND_TO_RESULT = "result_";
    public static final String FWARD_SLASH = "/";
    public static final String BWARD_SLASH = "\\";
    public static final String WARD = "ward";
    public static final String UNDER_SCORE = "_";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String DATA = "data";
    public static final String API_FOLDER = "SAVED_API_DATA";
    public static final String JSON_FILE_FORMAT = ".json";
    public static final String INVALID_CHARACTERS_REGEX = "[./\\\"]";

    private static final Logger log = Logger.getLogger(InecResultsPortal.class.getName());


    public static void main(String[] args) throws IOException, ParseException {
        //generateData();
        copyStatesDataToFile();
    }

    private static void copyStatesDataToFile () throws IOException, ParseException {
        // initialise the web request
        initialise();

        // Create the main directory if it does not exist
        String mainDirectory  = API_FOLDER + BWARD_SLASH;
        File folder = new File(mainDirectory);
        if (!folder.exists()) {
            folder.mkdir();
        }

        for (StatesEnum state : StatesEnum.values()) {

            // set the file and url params
            String currentDirectory  = state.name() + UNDER_SCORE + DATA + BWARD_SLASH;
            String stateFileName = state.name() + UNDER_SCORE + DATA + UNDER_SCORE + WARD + JSON_FILE_FORMAT;
            Path targetFilePath = Path.of(mainDirectory + currentDirectory + BWARD_SLASH + stateFileName);
            log.info("Checking for file: " + stateFileName);

            File directory = new File(mainDirectory + currentDirectory);

            // first check if the data for the particular state has been copied before
            if (Files.exists(targetFilePath)) {
                log.info("File '" + stateFileName + "' already exists. will skip download of this state's data");
                JsonNode loadedStateData = readJsonData(targetFilePath.toString(), true);
                if (loadedStateData != null) {
                    // Fill the data into the PU object
                    fillElectionData(loadedStateData, directory);
                }
                continue;
            }

            String apiUrl = STATE_DATA_API_URL + LOGIN_HASH + FWARD_SLASH + APPEND_LGA_TO_URL + FWARD_SLASH + APPEND_STATE_TO_URL + FWARD_SLASH + state.stateCode;
            log.info("The current API url is '" + apiUrl + "'");

            // Create the directory if it does not exist
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Attempt to copy the file from the api url
            log.info("Starting to pull data for state: " + state.name());
            saveJsonData(apiUrl, targetFilePath.toString(), true);

            log.info(" File for state '" + state.name() + "' successfully saved");
            log.info("%n -------------------------------------------------- %n");

        }

        log.info("%n COPYING COMPLETED SUCCESSFULLY!!!");

    }

    private static void fillElectionData(JsonNode loadedStateData, File directory) throws IOException, ParseException {

        ExecutorService executor = Executors.newFixedThreadPool(2); // use 10 threads

        // Extract the lga and ward data
        for (JsonNode lgasNode : loadedStateData.path("data")) {

            executor.submit(() -> {
                try {
                    processWardsData(lgasNode, directory, loadedStateData);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    private static void processWardsData(JsonNode lgasNode, File directory, JsonNode loadedStateData) throws IOException, ParseException {
        JsonNode lgaData = lgasNode.get("lga");
        // Create a folder for lga
        String lgaDirectoryName = APPEND_LGA_TO_URL + UNDER_SCORE + normalizeFileName(lgaData.get("name").asText().replaceAll(INVALID_CHARACTERS_REGEX, UNDER_SCORE));

        JsonNode wardsData = lgasNode.get("wards");

        for (JsonNode wardData : wardsData) {

            String wardDirectoryName = WARD + UNDER_SCORE + normalizeFileName(wardData.get("name").asText().replaceAll(INVALID_CHARACTERS_REGEX, UNDER_SCORE));
            String wardFileName = String.format("%s%s%s%s%s%s",
                    wardDirectoryName,
                    UNDER_SCORE,
                    normalizeFileName(lgaData.get("name").asText().replaceAll(INVALID_CHARACTERS_REGEX, UNDER_SCORE)),
                    UNDER_SCORE,
                    Arrays.stream(StatesEnum.values()).filter(sId -> sId.stateCode.equals(lgaData.get("state_id").asText())).findFirst().get(),
                    JSON_FILE_FORMAT);
            String filePathToWardData = directory.toString() + BWARD_SLASH + lgaDirectoryName + BWARD_SLASH + wardDirectoryName;
            String wardHash = wardData.findValue("_id").asText();
            String apiUrlForPUs = STATE_DATA_API_URL + LOGIN_HASH + FWARD_SLASH + APPEND_PUS_TO_URL + wardHash;
            String fileToSave = filePathToWardData + BWARD_SLASH + wardFileName;

            // Create the directories if they do not exist
            createDirectories(Path.of(filePathToWardData));

            // pull the ward data only if they do not already exist
            String pusJsonResponse = null;
            if (!Files.exists(Path.of(fileToSave))) {
                pusJsonResponse = getJsonData(apiUrlForPUs);
                log.info("PUs data for '" + wardDirectoryName + "' pulled from API successfully");
                // only save files not already saved
                saveJsonData(pusJsonResponse, fileToSave, false);
                log.info(wardFileName + " saved successfully");
            } else {
                // Get the file from disk
                pusJsonResponse = readJsonData(fileToSave, true).toString();
            }


            // Create an ObjectMapper object to read JSON data from the response
            ObjectMapper objectMapper = new ObjectMapper();
            // Read JSON data from the file and parse it into a JsonNode object
            JsonNode loadedPuData = objectMapper.readTree(pusJsonResponse);

            // Create a thread pool with a fixed number of threads
            int numThreads = 2;
            ExecutorService pool = new ThreadPoolExecutor(numThreads, numThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(numThreads));

            // Define a task to process each polling unit
            Runnable task = () -> {
                for (JsonNode eachPuData : loadedPuData.path("data")) {
                    PollingUnit currentPollingUnit = new PollingUnit();

                    try {
                        currentPollingUnit.fillPollingUnitData(eachPuData);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentPollingUnit.fillElectionWardData(wardData);
                    currentPollingUnit.fillLocalGovtData(lgaData);
                    currentPollingUnit.fillStateData(loadedStateData);

                    String pollingUnitDirectoryName = normalizeFileName(eachPuData.get("polling_unit").get("name").asText().replaceAll(INVALID_CHARACTERS_REGEX, UNDER_SCORE))
                            + " "
                            + OPEN_BRACKET
                            + normalizeFileName(eachPuData.get("polling_unit").get("pu_code").asText().replace(FWARD_SLASH, UNDER_SCORE))
                            + CLOSE_BRACKET;

                    String pollingUnitFileName = pollingUnitDirectoryName + JSON_FILE_FORMAT;
                    String filePathToSavePollingUnitData = filePathToWardData + BWARD_SLASH + pollingUnitDirectoryName;
                    // Create the directories if they do not exist
                    try {
                        createDirectories(Path.of(filePathToSavePollingUnitData));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Attempt to save each polling unit document in the document list
                    for (Pair<String, String> document : currentPollingUnit.puDocumentList) {
                        String directLink = document.getValue();

                        if (!directLink.isEmpty()) {
                            // Save documents that are not empty
                            String linkSubString = directLink.substring(directLink.lastIndexOf("/") + 1).replace(FWARD_SLASH, UNDER_SCORE);
                            String destination = filePathToSavePollingUnitData + BWARD_SLASH + APPEND_TO_RESULT + pollingUnitDirectoryName + UNDER_SCORE + linkSubString;
                            if (!Files.exists(Path.of(destination))) {
                                // only save files not already saved
                                saveFileViaDirectLink(directLink, destination);
                            } else {
                                log.info("File '" + linkSubString + "' already downloaded. Skipping to the next");
                            }

                        }

                    }

                    // Save the polling unit
                    try {
                        String whereToSave = filePathToSavePollingUnitData + BWARD_SLASH + pollingUnitFileName;
                        if (!Files.exists(Path.of(whereToSave))) {
                            // only save files not already saved
                            objectMapper.writeValue(new File(whereToSave), currentPollingUnit);
                            log.info("Polling unit '" + pollingUnitDirectoryName + "' saved successfully");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            // Submit the task to the thread pool
            for (int i = 0; i < numThreads; i++) {
                pool.submit(task);
            }

            // Shutdown the thread pool after all tasks are completed
            pool.shutdown();
            while (!pool.isTerminated()) {
                // Wait for all tasks to complete
            }


        }
    }


    private static void saveFileViaDirectLink(String directLink, String destination) {

        try {
            URL url = new URL(directLink);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();

            try (FileOutputStream outputStream = new FileOutputStream(destination)) {

                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

            }
            inputStream.close();

            log.info("File  from '" + directLink + "' downloaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void saveJsonData(String sourceFile, String fileDirectory, boolean fromUrl) throws IOException {
        String jsonResponse;
        if (fromUrl) {
            jsonResponse = getJsonData(sourceFile);
        } else {
            jsonResponse = sourceFile;
        }
        try (FileWriter fileWriter = new FileWriter(fileDirectory)) {
            fileWriter.write(jsonResponse);
        }
    }

    static String getJsonData(String apiUrl) throws IOException {
        initialise();
        WebRequest request = new WebRequest(new java.net.URL(apiUrl));
        request.setHttpMethod(HttpMethod.GET);
        WebResponse response  = webClientele.loadWebResponse(request);
        return response.getContentAsString();

    }

    static JsonNode readJsonData (String source, boolean readFromFile) {
        JsonNode fileRootNode = null;
        try {

            // Create an ObjectMapper object to read JSON data from the file
            ObjectMapper objectMapper = new ObjectMapper();
            // Read JSON data from the file and parse it into a JsonNode object
           if (readFromFile) {
               fileRootNode = objectMapper.readTree(new File(source));
           } else {
               fileRootNode = objectMapper.readTree(source);
           }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileRootNode;
    }

    public static String normalizeFileName(String fileName) {
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(fileName);
        Path normalizedPath = path.normalize();
        return normalizedPath.toString();
    }


    /**
     * sleeps the program for the stated number of seconds
     *
     * @param inSeconds number of seconds the program sleeps for
     */
    public static void iSleep(int inSeconds) {
        try {
            TimeUnit.SECONDS.sleep(inSeconds);
        } catch (InterruptedException ex) {
            log.severe("The sleep was interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private static void initialise() {
        WebClient webClient = new WebClient();
        // disable javascript
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // disable css support
        webClient.getOptions().setCssEnabled(false);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());

        webClientele = webClient;

    }

}
