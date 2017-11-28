
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Swap the indexes so the current playing item will not be played again until other items
// in the list are played at least once.

// E.g.: An illustration how shuffled list is built.
// An array of indexes is built in sequential order.  0 1 2 3 4 5 6 7 8 9
// Shuffling the indexes list, we name it as "auxiliary list".  9 4 5 8 1 0 2 7 3 6

// Approach:
// Find index of currently playing item in the temporary shuffled list. 
// Let's assume, the current playing item was in "5th" position in the cache. 
// That would mean, as per the temporary shuffled list, the index would be 2. 
// Once can find "5" in index position "2" in the above temporary shuffled list.
// Then we swap the item at the two indexes, which is "2" and "5" to get the below shuffled list.
// This swapping helps to keep the current playing item in the same index.
// Shuffled list => 9 4 0 8 1 5 2 7 3 6 5

// Another illustration to understand the algorithm better using country names in alphabetical order.
// List of items to be shuffled => "australia brazil china denmark egypt finland greece hungary italy japan"
// Currently played item: "denmark".
// Index of the item in the original list: 3
// Create auxiliary shuffled list.
// Auxiliary shuffled list => "china finland egypt hungary italy brazil australia greece japan denmark"
// Index of the item in the auxiliary shuffled list: '9'.
// Swap the elements using the indexes of currently played item 'denmark' in the original list and auxiliary
// shuffled list to create a new shuffled list, this swapping helps to keep the currently played item to be
// in the same place as the original list.
// This swapping ensures the currently played item will not be played again until other items in the list
// are played at least once.
// Final list => china finland egypt denmark italy brazil australia greece japan hungary

public class PlaybarSimulator {

    public List<Integer> buildShuffledList(int currPlayIndex, int totalCount) {

        ArrayList<Integer> objList = new ArrayList<>();

        for (int i = currPlayIndex + 1; i < totalCount; i++) {
            objList.add(i);
        }

        // Below for loop will not executed if shuffle is initiated when first image is displayed.
        // since currPlayIndex will be zero.
        for (int i = 0; i < currPlayIndex; i++) {
            objList.add(i);
        }

        System.out.println("Current list");

        for (int i : objList) {
            System.out.print(i + " ");
        }

        System.out.println("");
        Collections.shuffle(objList);

        System.out.println("After shuffling");
        for (int i : objList) {
            System.out.print(i + " ");
        }

        System.out.println("");

        System.out.println(objList.indexOf(currPlayIndex));

        System.out.println(objList.size());
        // Don't swap     
        // Collections.swap(objList, currPlayItemIdxInList, currPlayIndex);
        objList.add(objList.size(), currPlayIndex);

        System.out.println("max in the list => " + Collections.max(objList));

        for (int i : objList) {
            System.out.print(i + " ");
        }

        System.out.println("");

        return objList;
    }

    public void buildShuffledList() {

        ArrayList<String> objList = new ArrayList<>();

        String arrays[] = {"australia", "brazil", "china", "denmark", "egypt", "finland", "greece", "hungary", "italy", "japan"};

        String currPlayItem = "denmark";

        for (int i = 0; i < arrays.length; i++) {
            objList.add(arrays[i]);
        }

        int currPlayIndex = objList.indexOf(currPlayItem);
        System.out.println("Currently played item: " + currPlayItem + ".");
        System.out.println("Index of the item in the original list: " + currPlayIndex);

        System.out.print("List of items to be shuffled => \"");
        for (String i : objList) {
            System.out.print(i + " ");
        }

        System.out.println("\"");
        Collections.shuffle(objList);

        System.out.print("Auxiliary shuffled list => \"");
        for (String i : objList) {
            System.out.print(i + " ");
        }

        System.out.println("\"");

        System.out.println("Index of the item in the auxiliary shuffled list: '" + objList.indexOf(currPlayItem) + "'.");

        int currPlayItemIdxInList = objList.indexOf(currPlayItem);
        //objList.remove(objList.indexOf(currPlayIndex));

        System.out.println("Use the indexes of currently played item '" + currPlayItem + "' in the original list and auxiliary shuffled list.");
        System.out.println("Swap the elements using the indexes to create a new shuffled list, this swapping helps to keep the currently played item to be in the same place as the original list.");
        System.out.println("This swapping ensures, the currently played item will not be played again until other items in the list are played at least once.");

        Collections.swap(objList, currPlayItemIdxInList, currPlayIndex);

        for (String i : objList) {
            System.out.print(i + " ");
        }

        System.out.println("");

        System.out.println("Item to be played next: " + objList.get(currPlayIndex + 1));

    }

    public static void main(String[] args) {

        try {

            PlaybarSimulator obj = new PlaybarSimulator();

            final int NEXT = 1;
            final int PREV = 2;
            final int SHUFFLE = 3;
            final int REPEAT = 4;
            final int PRINT = 5;

            int currPlayIndex = 0;
            int totalCount = 3;
            boolean repeatStatus = false;
            boolean shuffleStatus = false;
            boolean playStatus = true;

            int historyKey = -1;
            List<Integer> shuffledList = null;

            int tempIterator = 0;

            do {
                Scanner sc = new Scanner(System.in);
                System.out.println("1 -> NEXT, 2 -> PREV, 3 -> SHUFFLE, 4 -> REPEAT, 5 -> PRINT");
                System.out.print("? ");
                int input = sc.nextInt();

                switch (input) {
                    case SHUFFLE:

                        shuffleStatus = !shuffleStatus;

                        // Build a new shuffle list.
                        if (shuffleStatus) {
                            if (shuffledList == null || shuffledList.isEmpty()) {
                                System.out.println("Build a new shuffle list.");
                                shuffledList = obj.buildShuffledList(currPlayIndex, totalCount);
                                tempIterator = 0;
                            }

                        } else {
                            System.out.println("Shuffle toggle off, clear shuffle list");
                            shuffledList.clear();
                        }

                        break;

                    case REPEAT:
                        repeatStatus = !repeatStatus;

                        String status;
                        status = repeatStatus ? "On" : "Off";

                        System.out.println("repeat toggle " + status);
                        break;

                    case PREV:
                        historyKey = PREV;
                        if (shuffleStatus && repeatStatus && shuffledList != null) {
                            --tempIterator;

                            if (tempIterator < 0) {
                                tempIterator = totalCount - 1;
                            }

                            System.out.println("Shuffled, repeat On. iter: " + tempIterator + ", Play index : " + shuffledList.get(tempIterator));
                            currPlayIndex = shuffledList.get(tempIterator);

                        } else if (shuffleStatus && shuffledList != null) {
                            --tempIterator;

                            if (tempIterator < 0) {
                                System.out.print("played all items in shuffledlist, exit playbar");
                                playStatus = false;
                            } else {
                                System.out.println("iter: " + tempIterator + ", play index : " + shuffledList.get(tempIterator));
                                currPlayIndex = shuffledList.get(tempIterator);

                                playStatus = true;
                            }
                        } else if (repeatStatus) {
                            --currPlayIndex;

                            if (currPlayIndex < 0) {
                                currPlayIndex = totalCount - 1;
                                playStatus = true;
                            }
                            System.out.println("repeat is on, shuffle is off. play index : " + currPlayIndex);
                        } else {
                            --currPlayIndex;

                            if (currPlayIndex < 0) {
                                playStatus = false;

                                break;
                            }

                            System.out.println("repeat is off, shuffle is off. play index : " + currPlayIndex);
                        }
                        break;

                    case NEXT:
                        historyKey = NEXT;
                        if (shuffleStatus && repeatStatus && shuffledList != null) {

                            ++tempIterator;
                            if (tempIterator >= totalCount) {
                                tempIterator = 0;
                            }

                            System.out.println("iter: " + tempIterator + ", play index : " + shuffledList.get(tempIterator));
                            currPlayIndex = shuffledList.get(tempIterator);
                        } else if (shuffleStatus && shuffledList != null) {

                            ++tempIterator;
                            if (tempIterator >= totalCount) {
                                System.out.println("played all items in shuffledlist, exit playbar");
                                playStatus = false;
                            } else {
                                System.out.println("iter: " + tempIterator + ", play index : " + shuffledList.get(tempIterator));
                                currPlayIndex = shuffledList.get(tempIterator);
                                playStatus = true;
                            }
                        } else if (repeatStatus) {
                            currPlayIndex++;
                            if (currPlayIndex >= totalCount) {
                                currPlayIndex = 0;
                                playStatus = true;
                            }
                            System.out.println("repeat is on, shuffle is off. play index : " + currPlayIndex);
                        } else {
                            currPlayIndex++;

                            if (currPlayIndex >= totalCount) {
                                playStatus = false;

                                break;
                            }

                            System.out.println("repeat is off, shuffle is off. play index : " + currPlayIndex);
                        }
                        break;

                    case PRINT:
                        if (shuffledList != null && shuffledList.size() > 0) {

                            System.out.println("list all items in shuffle list: historyKey = " + historyKey);

                            switch (historyKey) {
                                case NEXT:
                                    for (int i = tempIterator; i < shuffledList.size(); i++) {
                                        System.out.print(shuffledList.get(i) + " ");
                                    }
                                    break;
                                case PREV:
                                    for (int i = tempIterator; i >= 0; i--) {
                                        System.out.print(shuffledList.get(i) + " ");
                                    }
                                    for (int i = shuffledList.size() - 1; i > tempIterator; i--) {
                                        System.out.print(shuffledList.get(i) + " ");
                                    }
                                    break;
                                default:
                                    for (int i = tempIterator; i < shuffledList.size(); i++) {
                                        System.out.print(shuffledList.get(i) + " ");
                                    }
                                    break;
                            }

                            System.out.println();
                        } else {
                            System.out.println("No shuffle list, currPlayIndex: " + currPlayIndex);
                            System.out.println("remaining items: ");

                            for (int i = currPlayIndex; i < totalCount; i++) {
                                System.out.print(i + " ");
                            }

                            System.out.println();

                        }
                        break;
                }

                if (!playStatus) {

                    System.out.println("Dismiss playbar");
                    break;
                }

            } while (true);

        } catch (Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }
}
