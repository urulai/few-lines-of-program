import java.util.Calendar;
import java.util.ArrayList;
import java.io.File;

class Main {

    public static void main(String[] args) {

        String currentPath = System.getProperty("user.dir");
        String filepath = null;

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();

        MP4FileList mp4Files = new MP4FileList(args.length > 0 ? args[0] : null);
        ArrayList<File> fileList = mp4Files.getList();
        ArrayList<String> lst360Videos = null;

        lst360Videos = VideoFinder.parse(fileList);

        cal = Calendar.getInstance();
        long endTime = cal.getTimeInMillis();

        System.out.println("Millis took to parse ("  + fileList.size() +  ") files --> " + (endTime - startTime) + " msecs");

        if (lst360Videos != null) {
            for (int idx = 0; idx < lst360Videos.size(); idx++) {
                if (idx == 0)
                    System.out.println("List of 360 videos");

                System.out.println(lst360Videos.get(idx));
            }
        }
        else {
            //System.out.println("No 360 videos found");
        }
    }
}