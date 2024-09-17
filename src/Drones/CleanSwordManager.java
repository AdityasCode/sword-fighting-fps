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
        System.out.println(filename);
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
            i = 0;
            int clean_times[] = new int[nmt[0]];
            BetterQueue<Integer> requests = new BetterQueue<>();
            String thisline;
            for (i = 0; (i < nmt[0]) && ((thisline = bf.readLine()) != null); i++) {
                clean_times[i] = Integer.parseInt(thisline);
            }
            for (i = 0; (i < nmt[1]) && ((thisline = bf.readLine()) != null); i++) {
                requests.add(Integer.parseInt(thisline));
            }
//            while (bf != null) {
//                for (i = 0; i < nmt[0]; i++) {
//                    clean_times[i] = Integer.parseInt(thisline);
//                }
//                for (i = 0; i < nmt[1]; i++) {
//                    requests.add(Integer.parseInt(bf.readLine()));
//                }
//            }
            int max_time = nmt[1] * nmt[2];
            int currsword = 0;
            bf.close();
            for (int t = 0; ((t <= Integer.MAX_VALUE) && (!requests.isEmpty())); t++) {
                while (( ((Object []) requests.getQueue())[requests.getFront()] != null) && (((int) ((Object []) requests.getQueue())[requests.getFront()]) <= t)) {
                    if (clean_times[currsword] <= 0) {
                        Integer lastreq = requests.remove();
                        returns.add(new CleanSwordTimes(t, t - lastreq));
                        clean_times[currsword] = nmt[2];
                        currsword = (currsword + 1) % nmt[0];
                    }
                    else {
                        break;
                    }
                }
                if (clean_times[currsword] <= 0) {
                    for (int j = 0; (currsword + j) < nmt[0]; j++) {
                        int nextsword = clean_times[currsword + j];
                        if (nextsword > 0) {
                            clean_times[currsword + j] -= 1;
                            break;
                        }
                    }
                }
                clean_times[currsword] -= 1;
//                while (clean_times[currsword] <= 0) {
//                    while (requests.getQueue()[requests.getFront()] <= t) {
//                        Integer lastreq = requests.remove();
//                       returns.add(new CleanSwordTimes(t, t - lastreq));
//                        clean_times[currsword] = nmt[3];
//                    }
//                    currsword = (currsword + 1) % nmt[0];
//                }

//                for (int s = 0; s < nmt[0]; s++) {
//                    --clean_times[s];
//                    if (clean_times[s] <= 0) {
//                        if (requests.getQueue()[requests.getFront()] <= t) {
//                            requests.remove();
//                            returns.add(new CleanSwordTimes());
//                        }
//                    }
//                }
            }

            //todo

        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
//        for (CleanSwordTimes c : returns) {
//            c.timeFilled++;
//            c.timeToFulfill++;
//        }
        return returns;
    }
}
