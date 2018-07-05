import java.io.File;
import java.util.ArrayList;

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

                if (path.toString().endsWith("mp4") || path.toString().endsWith("MP4")) {

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