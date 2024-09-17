package Drones;

import CommonUtils.BetterStack;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages everything regarding the requesting of items in our game.
 * Will be integrated with the other drone classes.
 *
 * You may only use java.util.List, java.util.ArrayList, java.io.* and java.util.Scanner
 * from the standard library.  Any other containers used must be ones you created.
 */
public class ItemRequestManager implements ItemRequestManagerInterface {
    /**
     * Get the retrieval times as per the specifications
     *
     * @param filename file to read input from
     * @return the list of times requests were filled and index of the original request, per the specifications
     */
    @Override
    public ArrayList<ItemRetrievalTimes> getRetrievalTimes(String filename) {
        try {
            // as all of the inputs are on the same line, it is actually more efficient to use scanner's nextInt since
            // with BufferedReader you would have to read in the entire line (possibly 10m integers long) at once
            Scanner scan = new Scanner(new FileReader(filename));
            int nreqs = scan.nextInt();
            int runtime = scan.nextInt();
            ArrayList<Integer> reqs = new ArrayList<>();
            for (int i = 0; i < nreqs; i++) {
                reqs.add(scan.nextInt());
            }
            int positions[] = new int[nreqs];
            BetterStack stack = new BetterStack();
            for (int t = 0; t < Integer.MAX_VALUE; t++) {
                if (stack.size() != nreqs) {
                    while (reqs.get(0) <= t) {
                        stack.push(reqs.get(0));
                        reqs.remove(0);
                    }
                }
                if ()
            }

            //todo

        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }

        return null;
    }
}
