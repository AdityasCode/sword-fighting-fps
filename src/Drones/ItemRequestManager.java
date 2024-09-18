package Drones;

import CommonUtils.BetterStack;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
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
        System.out.println(filename);
        ArrayList<ItemRetrievalTimes> returns = new ArrayList<>();
        try {
            // as all of the inputs are on the same line, it is actually more efficient to use scanner's nextInt since
            // with BufferedReader you would have to read in the entire line (possibly 10m integers long) at once
            Scanner scan = new Scanner(new FileReader(filename));
            int nreqs = scan.nextInt();
            int runtime = scan.nextInt() + 1;
            ArrayList<Long> reqs = new ArrayList<>();
            for (int i = 0; i < nreqs; i++) {
                reqs.add(scan.nextLong());
            }
            ArrayList<Integer> positions = new ArrayList<>();
            for (int i = 0; i < nreqs; i++) {
                positions.add(runtime);
            }

            // track position

            ArrayList<Integer> map = new ArrayList<>();
            for (int i = 1; i <= (runtime); i++) {
                map.add(i);
            }
//            for (int i = runtime; i > 0; i--) {
//                map.add(i * (-1));
//            }
            int position = 1;
            boolean hasItem = false;
            long currReqTime = reqs.get(0);
            long destination = runtime;
            long currIndex = 0;

            // stack of requests

            BetterStack<Long> stack = new BetterStack();
            for (long t = 0; t < Long.MAX_VALUE; t++) {
                if (t < reqs.get(0)) {
                    continue;
                }
                if (position < 1) {
                    position = 1;
                    System.out.println("INCORRECT HANDLING OF POSITION");
                }
                System.out.println("big loop run: time #" + t);
                System.out.printf("time: %d, position: %d, hasItem: %b\n", t, position, hasItem);
                try {
                    System.out.printf("stack top: %d\n", stack.peek());
                } catch (EmptyStackException e) {
                    System.out.println("empty stack");
                }
                System.out.println("all positions:");
                for (long p : positions) {
                    System.out.println(p);
                }
                if (t == 10) {
                    System.out.println("hello");
                }

                // SUCCESSFUL CONDITION: at starting, non empty stack, have item

                if ((position == 1) && (!stack.isEmpty()) && (hasItem)) {
                    Object removed = stack.pop();
//                    int startsAt= 0;
//                    boolean found = false;
//                    for (int i = 0; i < nreqs; i++) {
//                        if (reqs.get(i) == removed && !found) {
//                            found = true;
//                            startsAt = i;
//                        }
//                        if (found && reqs.get(i) != removed) {
//                            break;
//                        }
//                    }
//                    returns.add(new ItemRetrievalTimes(startsAt, t));
                    returns.add(new ItemRetrievalTimes(reqs.indexOf(currReqTime), t));
                    hasItem = false;
                }

                // HAS EVERY REQ BEEN FULFILLED NOW?

                if (returns.size() == nreqs) {
                    break;
                }


                // THIS IMPLIES THAT EITHER I AM NOT AT STARTING AND/OR STACK IS EMPTY AND/OR I DONT HAVE ITEM
                // IT SHOULD MOT BE POSSIBLE TO HAVE AN ITEM WITHOUT ANY ELEMS IN STACK
                // LETS UPDATE STACK WITH CURRENT TIME
                // IF STACK IS EMPTY, THERE IS NOTHING TO DELIVER, AND I SHOULD SIMPLY GO BACK TO THE FRONT
                // if theres a more urgent request, keep it in the stack and push more important ones on

                boolean found = false;
                for (int i = 0; i < nreqs; i++) {
                    if (reqs.get(i) == t) {
                        found = true;
                        hasItem = false;
                        stack.push(reqs.get(i));
                        currIndex = i;
                    }
                    if (found && reqs.get(i) != t) {
                        break;
                    }
                }

                // IS STACK EMPTY/NO REQUESTS: MOVE BACK TO BASE AND MOVE TO THE NEXT ITERATION

                if (stack.isEmpty()) {
                    position -= 1;
                    if (position < 1) {
                        position = 1;
                    }
                    continue;
                }

                // THERE IS A REQUEST IN THE STACK: CHECK WHICH ONE AND MARK IT

                currReqTime = stack.peek();

                // DO I HAVE THE ITEM? IF NOT, MOVE TO STORAGE; OTHERWISE, MOVE TO BASE

                if (!hasItem) {
                    if (position != positions.get(reqs.indexOf(currReqTime))) {
                        position +=1;
                    } else {
                        hasItem = true;
                    }
                }

                // I HAVE THE ITEM, MOVING BACK TO STORAGE. MARKING ITS LOCATION IN CASE I HAVE TO DROP IT

                else {
                    position -= 1;
                    positions.set(reqs.indexOf(currReqTime), position);
                }

                // AM I AT THE POSITION OF THE ITEM (EITHER STORAGE(RUNTIME) OR THE PLACE IT WAS DROPPED AT)? IF SO,
                // MARK THAT I HAVE NOW GOT IT

                if (position == positions.get(reqs.indexOf(currReqTime))) {
                    hasItem = true;
                }

                // ALL POSSIBLE SCENARIOS HAVE BEEN COVERED.
            }

            //todo

        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        for (ItemRetrievalTimes i : returns) {
            System.out.println(i.toString());
        }
        return returns;
    }
}
