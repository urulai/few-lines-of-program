
import java.io.File;

public class listFileEx {

    public static void main(String[] args) {
        File f = null;
        File[] paths;

        try {

            // create new file
            String strFilePath = System.getProperty("user.dir") + File.separator;
            f = new File(strFilePath);
            
            // returns pathnames for files and directory
            paths = (f != null) ? f.listFiles() : null;

            if (paths == null) {
                System.out.println("paths is null");
                return;
            }
            // for each pathname in pathname array
            for (File path : paths) {

                // prints file and directory paths
                System.out.println(path);
            }

        } catch (Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }
}
