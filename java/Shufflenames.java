
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Shufflenames {

    public void buildShuffledList() {

        ArrayList<String> objList = new ArrayList<>();

        String arrays[] = {
            "Australia",
            "Brazil",
            "China",
            "Denmark",
            "Egypt",
            "Finland",
            "Greece",
            "Hungary",
            "Italy",
            "Japan"};

        int randomize = (int) Math.ceil((double) arrays.length * Math.random());

        String currPlayItem = arrays[randomize];
        objList.addAll(Arrays.asList(arrays));

        int currPlayIndex = objList.indexOf(currPlayItem);
        System.out.println("Currently played item: " + currPlayItem + ".");
//        System.out.println("Index of the item in the original list: " + currPlayIndex);

        System.out.print("List to be shuffled     => \"");
        for (String idx : objList) {
            System.out.print(idx + " ");
        }

        System.out.println("\"");
        Collections.shuffle(objList);

        System.out.print("Auxiliary shuffled list => \"");
        for (String idx : objList) {
            System.out.print(idx + " ");
        }

        System.out.println("\"");

        System.out.println("Index of the item in the auxiliary shuffled list: '" + objList.indexOf(currPlayItem) + "'.");

        int currPlayItemIdxInList = objList.indexOf(currPlayItem);
        
        System.out.println();

        System.out.println("******** BEGIN - Notes ********");
        System.out.println();
        System.out.println("Use the indexes of currently played item '" + currPlayItem + "' in the original list and auxiliary shuffled list.");
        System.out.println("Swap the elements using the indexes to create a new shuffled list, this swapping"
                + " helps to keep the currently played item to be in the same place as the original list.");
        System.out.println("This swapping ensures the currently played item will not be played again"
                + " until other items in the list are played at least once.");
        System.out.println();
        System.out.println("******** END - Notes ********");

        Collections.swap(objList, currPlayItemIdxInList, currPlayIndex);

        System.out.println();
        System.out.println("Resulting shuffled list");
        for (String idx : objList) {
            System.out.print(idx + " ");
        }

        System.out.println("");

    }

    public static void main(String[] args) {

        try {
            
            Shufflenames obj = new Shufflenames();
            obj.buildShuffledList();
            
        } catch (Exception e) {
            
            // if any error occurs
            e.printStackTrace();
            
        }
    }
}
