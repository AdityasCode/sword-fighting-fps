package Drones;

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
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            if (bf == null) {
                throw new IOException();
            }
            int i = 0;
            int nmt[] = new int[3];
            String curr = bf.readLine();
            String strs[] = curr.split(" ");
            for (String str : strs) {
                nmt[i] = Integer.parseInt(str);
                i++;
            }
            long clean_times[] = new long[nmt[0]];
            BetterQueue<Long> requests = new BetterQueue<>();
            String thisline;
            for (i = 0; (i < nmt[0]) && ((thisline = bf.readLine()) != null); i++) {
                clean_times[i] = Long.parseLong(thisline);
            }
            for (i = 0; (i < nmt[1]) && ((thisline = bf.readLine()) != null); i++) {
                requests.add(Long.parseLong(thisline));
            }
            int currsword = 0;
            bf.close();
            biggestLoop: for (long t = 0; ((t < Long.MAX_VALUE) && (!requests.isEmpty())); t++) {
                int iter = 0;
                while (((long) ((Object []) requests.getQueue())[requests.getFront()]) <= t) {
                    System.out.println("iter is " + iter + " t is " + t);
                    iter++;
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
                if (clean_times[currsword] <= 0) {
                    for (int j = 0; (currsword + j) < nmt[0]; j++) {
                        Long nextsword = clean_times[currsword + j];
                        if (nextsword > 0) {
                            clean_times[currsword + j] -= 1;
                            break;
                        }
                    }
                }
                else {
                    clean_times[currsword] -= 1;
                }
            }

            //todo

        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        return returns;
    }
}
