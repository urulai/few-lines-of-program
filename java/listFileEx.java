
import java.io.File;
import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

                Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+.mp4");

                Matcher matcher = pattern.matcher(path.toString());

                if (matcher != null && matcher.find()) {
                    Console cons = System.console();
                    if (cons != null) {
                        cons.format("I found the text"
                                    + " \"%s\" starting at "
                                    + "index %d and ending at index %d.%n",
                                    matcher.group(),
                                    matcher.start(),
                                    matcher.end());
                    }
                } else {
                    // prints file and directory paths
                    // System.out.println(path);
                }
            }
        } catch (Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }
}
