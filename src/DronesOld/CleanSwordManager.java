package DronesOld;

import CommonUtils.BetterQueue;

import java.io.*;
import java.util.ArrayList;

/**
 * Manages everything regarding the cleaning of swords in our game.
 * Will be integrated with the other drone classes.
 *
 * You may only use java.util.List, java.util.ArrayList, and java.io.* from
 * the standard library.  Any other containers used must be ones you created.
 */
public class CleanSwordManager implements CleanSwordManagerInterface {
    /**
     * Gets the cleaning times per the specifications.
     *
     * @param filename file to read input from
     * @return the list of times requests were filled and times it took to fill them, as per the specifications
     */
    @Override
    public ArrayList<CleanSwordTimes> getCleaningTimes(String filename) {
        ArrayList<CleanSwordTimes> returns = new ArrayList<>();
        try {

            // force exit any loop!

            long myNuke = 0;
            long myNuke1 = 0;
            long myNuke2 = 0;

            BufferedReader bf = new BufferedReader(new FileReader(filename));
            if (bf == null) {
                throw new IOException();
            }
            int i = 0;
            int nmt[] = new int[3];
            String curr = bf.readLine();
            String strs[] = curr.split(" ");
            for (String str : strs) {
                if (myNuke == Long.MAX_VALUE) {
                    break;
                }
                myNuke++;
                nmt[i] = Integer.parseInt(str);
                i++;
            }
            myNuke = 0;
            long clean_times[] = new long[nmt[0]];
            BetterQueue<Long> requests = new BetterQueue<>();
            String thisline;
            for (i = 0; (i < nmt[0]) && ((thisline = bf.readLine()) != null); i++) {
                if (myNuke == Long.MAX_VALUE) {
                    break;
                }
                myNuke++;
                clean_times[i] = Long.parseLong(thisline);
            }
            myNuke = 0;
            for (i = 0; (i < nmt[1]) && ((thisline = bf.readLine()) != null); i++) {
                if (myNuke == Long.MAX_VALUE) {
                    break;
                }
                myNuke++;
                requests.add(Long.parseLong(thisline));
            }
            myNuke = 0;
            int currsword = 0;
            bf.close();
            biggestLoop: for (long t = 0; ((t < Long.MAX_VALUE) && (!requests.isEmpty())); t++) {
                if (myNuke2 == Long.MAX_VALUE) {
                    break;
                }
                myNuke2++;
                while (((long) ((Object []) requests.getQueue())[requests.getFront()]) <= t) {
                    if (myNuke == Long.MAX_VALUE) {
                        break;
                    }
                    myNuke++;
                    if (clean_times[currsword] <= 0) {
                        Long lastreq = requests.remove();
                        returns.add(new CleanSwordTimes(t, t - lastreq));
                        clean_times[currsword] = nmt[2];
                        currsword = (currsword + 1) % nmt[0];
                    }
                    else {
                        break;
                    }
                    if (returns.size() == nmt[1]) {
                        break biggestLoop;
                    }
                }
                myNuke = 0;
                if (clean_times[currsword] <= 0) {
                    for (int j = 0; (currsword + j) < nmt[0]; j++) {
                        if (myNuke1 == Long.MAX_VALUE) {
                            break;
                        }
                        myNuke1++;
                        Long nextsword = clean_times[currsword + j];
                        if (nextsword > 0) {
                            clean_times[currsword + j] -= 1;
                            break;
                        }
                    }
                    myNuke1 = 0;
                }
                else {
                    clean_times[currsword] -= 1;
                }
            }
            myNuke2 = 0;


        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        return returns;
    }
}
