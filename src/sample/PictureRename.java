package sample;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

public class PictureRename extends Window {

    public static void pictureRename(File dir) throws IOException {

        System.out.println(dir);
        // List the images in current directory
        File[] images = getPictures(dir);

        if (images.length==0) //TODO tagasta info konsooliaknasse

        // Rename images
        renameImage(images);
        images = getPictures(dir);

        // Delete duplicates
        deleteDuplicatePictures(images);

        //Delete duplicate pictures in Bad Files folder
        deleteDuplicatesInBadFiles();
    }

    private static void deleteDuplicatesInBadFiles() {
        String directoryName = "Bad files";
        File theDir = new File(directoryName);
        // Create a directory for the pictures without metadata, if the director does not already exist
        if (!theDir.exists()) {
            //System.exit(0);
        } else {
            File[] badImages = getPictures(theDir);
            if (badImages.length==0) // System.exit(0);
            deleteDuplicatePictures(badImages);
        }
    }

    private static void renameImage(File[] images) throws IOException {
        for (int i = 0; i < images.length; i++) {
            File image = images[i];

            // Current name
            String originalName = image.getName();

            // Current formatting
            String[] originalSplitted = originalName.split("\\.");
            String imageType = originalSplitted[originalSplitted.length-1];

            // Time taken
            String takenTime = readPictureTakenTime(image);
            if (takenTime != null){
                // Format new name
                String newname = formatNewName(takenTime);
                String newnameWithExtentsion = addExtentsion(newname, imageType);

                // Rename picture
                File imageRenamed = new File(newnameWithExtentsion);

                // Change file name if file name already exists
                int counter = 0;
                if (!checkIfNamesMatch(originalName, newnameWithExtentsion)) {
                    if (imageRenamed.exists()) {
                        imageRenamed = renameIfNameExists(originalName, newnameWithExtentsion, newname, imageType, counter);
                    };
                    // Rename file
                    boolean success = image.renameTo(imageRenamed);
                    if (!success) {
                        // Put the file in a separate folder
                        throwFileInFolder(image);
                    }
                }
            }
        }
    }

    private static void throwFileInFolder(File image) {
        String directoryName = "Bad files";
        File theDir = new File(directoryName);
        // Create a directory for the pictures without metadata, iff the directory does not already exist
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
                //handle it
            }
        }

        File source = new File (theDir + "/" + image.getName());
        if (!source.exists()) {
            image.renameTo(source);
        } else if (source.exists()) {
            try {
                if (FileUtils.contentEquals(image, source)) {
                    image.delete();
                } else {
                    int counter = 1;
                    while (source.exists()) {
                        String[] originalSplitted = image.getName().split("\\.");
                        String imageType = originalSplitted[originalSplitted.length-1];
                        System.out.println(imageType);
                        StringBuilder originalNameBuilder = new StringBuilder();
                        for (int i = 0; i < originalSplitted.length-1; i++) {
                            originalNameBuilder.append(originalSplitted[i]);
                        }
                        String originalName = originalNameBuilder.toString();
                        source = new File (theDir + "/" + originalName + "_" + counter + "."+imageType);
                        counter++;
                    }
                    image.renameTo(source);
                    counter = 1;
                }
            } catch (IOException e) {
                System.out.println("ah?");
                e.printStackTrace();
            }
        }
    }

    private static void deleteDuplicatePictures(File[] images) {
        for (int x = 0; x < images.length; x++) {
            for (int y = x + 1; y < images.length; y++) {
                if (CompareFiles(images[x], images[y])) images[y].delete();
            }
        }
    }

    private static String addExtentsion(String newname, String imageType) {
        return newname + "." + imageType;
    }

    private static boolean checkIfNamesMatch(String originalName, String newnameWithExtentsion) {
        return originalName.equals(newnameWithExtentsion);
    }

    private static File renameIfNameExists(String originalName, String newnameWithExtentsion, String newname, String imageType, int counter) {
        File image = new File(newnameWithExtentsion);

        while (image.exists()) {
            if (counter == 0) {
                newnameWithExtentsion = newname + "_0." + imageType;
                if (checkIfNamesMatch(originalName, newnameWithExtentsion)) {
                    System.out.println("Faili " + originalName + " nime ei muudetud");
                    return new File (originalName);
                }
                image = new File(newnameWithExtentsion);
                counter++;
            }
            else {

                newnameWithExtentsion=newnameWithExtentsion.replaceAll("_" + Integer.toString(counter - 1), "_" + Integer.toString(counter));
                if (checkIfNamesMatch(originalName, newnameWithExtentsion)) {
                    System.out.println("Faili " + originalName + " nime ei muudetud");
                    return new File (originalName);
                }
                image = new File (newnameWithExtentsion);
                counter++;
            }
        }
        System.out.println("Faili " + originalName + " nimi muudeti. Uus nimi on: " + newnameWithExtentsion);
        return image;
    }

    private static File[] getPictures(File curDir) {
        File[] images = curDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") ||
                        name.toLowerCase().endsWith(".jpeg") ||
                        name.toLowerCase().endsWith(".png");
                //TODO Add acceptable filetypes
            }
        });
        return images;
    }

    private static boolean CompareFiles(File x, File y) {
        boolean isTwoEqual = false;
        try {
            isTwoEqual = FileUtils.contentEquals(x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isTwoEqual;
    }

    private static String getMonthNumber(String s) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] monthsInNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int index = -1;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(s)){
                index = i;
            }
        }
        return monthsInNumbers[index];
    }

    private static String readPictureTakenTime(File image) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(image);
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, TimeZone.getDefault());
            return date.toString();
        } catch (ImageProcessingException | IOException | NullPointerException e) {
            throwFileInFolder(image);
        }
        return null;
    }

    private static String formatNewName(String takenTime) {
        StringBuilder newName = new StringBuilder();
        String[] splited = takenTime.split("\\s+");
        String[] splitedTime = splited[3].split(":");
        String month = getMonthNumber(splited[1]);
        newName.append(splited[splited.length-1])
                .append("-")
                .append(month)
                .append("-")
                .append(splited[2])
                .append(" ")
                .append(splitedTime[0])
                .append(".")
                .append(splitedTime[1])
                .append(".")
                .append(splitedTime[2]);
        return newName.toString();
    }

}
