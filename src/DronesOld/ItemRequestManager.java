package DronesOld;

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
        ArrayList<ItemRetrievalTimes> returns = new ArrayList<>();
        try {

            // force exit out of any loop!

            long myNuke = 0;
            long myNuke1 = 0;


            // as all of the inputs are on the same line, it is actually more efficient to use scanner's nextInt since
            // with BufferedReader you would have to read in the entire line (possibly 10m integers long) at once

            Scanner scan = new Scanner(new FileReader(filename));
            int nreqs = scan.nextInt();
            int runtime = scan.nextInt() + 1;
            ArrayList<Long> reqs = new ArrayList<>();
            for (int i = 0; i < nreqs; i++) {
                if (myNuke == Long.MAX_VALUE) {
                    break;
                }
                myNuke++;
                reqs.add(scan.nextLong());
            }
            myNuke = 0;
            ArrayList<Integer> positions = new ArrayList<>();
            for (int i = 0; i < nreqs; i++) {
                if (myNuke == Long.MAX_VALUE) {
                    break;
                }
                myNuke++;
                positions.add(runtime);
            }
            myNuke = 0;

            // track position

            ArrayList<Integer> map = new ArrayList<>();
            for (int i = 1; i <= (runtime); i++) {
                if (myNuke == Long.MAX_VALUE) {
                    break;
                }
                myNuke++;
                map.add(i);
            }
            myNuke = 0;
//            for (int i = runtime; i > 0; i--) {
//                map.add(i * (-1));
//            }
            int position = 1;
            boolean hasItem = false;
            long currReqTime = reqs.get(0);
            long destination = runtime;
            int currIndex = 0;

            // stack of requests

            BetterStack<ItemRetrievalTimes> stack = new BetterStack();
            for (long t = 0; t < Long.MAX_VALUE; t++) {
                if (myNuke == Long.MAX_VALUE) {
                    break;
                }
                myNuke++;
                if (t < reqs.get(0)) {
                    continue;
                }
                if (position < 1) {
                    position = 1;
                }

                // SUCCESSFUL CONDITION: at starting, non empty stack, have item

                if ((position == 1) && (!stack.isEmpty()) && (hasItem)) {
                    ItemRetrievalTimes removed = stack.pop();
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
                    returns.add(new ItemRetrievalTimes(removed.getIndex(), t));
                    hasItem = false;
                    if (!stack.isEmpty()) {
                        currIndex = stack.peek().getIndex();
                    }
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
                for (int i = currIndex; i < nreqs; i++) {
                    if (myNuke1 == Long.MAX_VALUE) {
                        break;
                    }
                    myNuke1++;
                    if (i == -1) {
                        break;
                    }
                    if (reqs.get(i) == t) {
                        found = true;
                        hasItem = false;
                        stack.push(new ItemRetrievalTimes(i, reqs.get(i)));
//                        stack.push(reqs.get(i));
                        currIndex = i;
                    }
                    if (found && reqs.get(i) != t) {
                        break;
                    }
                }
                myNuke1 = 0;

                // IS STACK EMPTY/NO REQUESTS: MOVE BACK TO BASE AND MOVE TO THE NEXT ITERATION

                if (stack.isEmpty()) {
                    position -= 1;
                    if (position < 1) {
                        position = 1;
                    }
                    continue;
                }

                // THERE IS A REQUEST IN THE STACK: CHECK WHICH ONE AND MARK IT

                currReqTime = stack.peek().getTimeFilled();

                // DO I HAVE THE ITEM? IF NOT, MOVE TO STORAGE; OTHERWISE, MOVE TO BASE

                if (!hasItem) {
                    if (position != positions.get(currIndex)) {
                        position +=1;
                        if (position == positions.get((int) currIndex)) {
                            hasItem = true;
                        }
                    } else {
                        hasItem = true;
                        position -= 1;
                    }
                }

                // I HAVE THE ITEM, MOVING BACK TO STORAGE. MARKING ITS LOCATION IN CASE I HAVE TO DROP IT

                else {
                    position -= 1;
                    positions.set(currIndex, position);
                }

                // AM I AT THE POSITION OF THE ITEM (EITHER STORAGE(RUNTIME) OR THE PLACE IT WAS DROPPED AT)? IF SO,
                // MARK THAT I HAVE NOW GOT IT


                // ALL POSSIBLE SCENARIOS HAVE BEEN COVERED.
            }
            myNuke = 0;

            //todo

        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        return returns;
    }
}
