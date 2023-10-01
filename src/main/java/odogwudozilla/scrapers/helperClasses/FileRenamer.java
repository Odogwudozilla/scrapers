package odogwudozilla.scrapers.helperClasses;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

public class FileRenamer {

    private final String parentDirectory;
    private int numFilesCHANGED = 0;
    private int numFilesSKIPPED = 0;

    public FileRenamer(String parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public static void main(String[] args) {
        String parentDirectory = "SAVED_API_DATA";
        FileRenamer fileRenamer = new FileRenamer(parentDirectory);
        fileRenamer.renameFiles();
    }

    public void renameFiles() {
        Queue<File> directories = new ArrayDeque<>();
        directories.add(new File(parentDirectory));

        while (!directories.isEmpty()) {
            File directory = directories.poll();
            File[] files = directory.listFiles();

            if (files == null) {
                continue;
            }

            for (File file : files) {
                if (file.isDirectory()) {
                    directories.add(file);
                } else {
                    String fileName = file.getName();
                    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    // We only care about image files and PDFs
                    boolean isEligibleFile = fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png") || fileExtension.equals("pdf");

                    if (fileName.startsWith("result_") || !isEligibleFile) {
                        numFilesSKIPPED++;
                        continue; // skip files that have already been renamed or are ineligible.
                    }

                    String newName = "result_" + fileName;
                    File newFile = new File(file.getParent(), newName);
                    if (file.renameTo(newFile)) {
                        numFilesCHANGED++;
                        System.out.println("Renamed " + fileName + " to " + newName);
                    } else {
                        System.out.println(fileName + " could not be renamed ");
                    }

                }
            }
        }

        System.out.println("Total number of files skipped: " + numFilesSKIPPED);
        System.out.println("Total number of files changed: " + numFilesCHANGED);
    }
}
