import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MP4FileList {
    private ArrayList<File> listFiles = null;

    MP4FileList(String directory) {

        String currentPath = System.getProperty("user.dir") + File.separator;

        if (directory != null)
            currentPath += directory + File.separator;

        File f = new File(currentPath);

        if (f != null && f.exists()) {
            File[] paths = null;

            paths = f.listFiles();

            for (File path : paths) {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+.[mp4|MP4]");
                Matcher matcher = pattern.matcher(path.toString());

                if (matcher != null && matcher.find()) {
                    if (listFiles == null)
                        listFiles =  new ArrayList<File>();

                    listFiles.add(path);
                }
            }
        }
    }

    public ArrayList<File> getList() {
        return listFiles;
    }
}