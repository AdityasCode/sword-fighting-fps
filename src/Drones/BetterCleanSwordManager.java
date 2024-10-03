package Drones;

import CommonUtils.BetterHashTable;
import CommonUtils.BetterQueue;
import CommonUtils.MinHeap;
import Drones.Interfaces.BetterCleanSwordManagerInterface;
import Items.Sword;
import Items.Types.SwordType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.pow;

/**
 * Manages everything regarding the cleaning of swords in our game.  Will be integrated with
 *   the other drone classes.  UPDATED VERSION -- handles sword management with the new rules.
 *   Old version-CleanSwordManager-is deprecated.
 *
 * <bold>251 students: you may use any of the data structures you have previously created, but may not use
 *   any Java library for stacks, queues, min heaps/priority queues, or hash tables (or any similar classes).</bold>
 */
public class BetterCleanSwordManager implements BetterCleanSwordManagerInterface {
    /**
     * Gets the cleaning times per the specifications.
     *
     * @param filename file to read input from
     * @return the list of times requests were filled, times it took to fill them,
     * and sword returned, as per the specifications
     */
    @Override
    public ArrayList<DetailedCleanSwordTime<SwordType>> getCleaningTimes(String filename) {
        ArrayList<DetailedCleanSwordTime<SwordType>> results = new ArrayList<>();
        try {

            // Read the file for n swords and n reqs

            BufferedReader bf = new BufferedReader(new FileReader(filename));
            if (bf == null) {
                throw new IOException();
            }
            String currLine = bf.readLine();
            int n_swords = Integer.parseInt(currLine.split(" ")[0]);
            int n_reqs = Integer.parseInt(currLine.split(" ")[1]);

            // Get swords' information

            MinHeap<Sword> swords_heap = new MinHeap<>();
            BetterHashTable<Integer, Boolean> sword_tracker = new BetterHashTable<>();
            Sword swords_arr[] = new Sword[n_swords];
            int t, th, hl, l, dps, as;
            String n, dsc, com, sty;
            long cleanliness;
            for (int i = 0; i < n_swords; i++) {
                String[] line = bf.readLine().split(", ");
                //technically unnecessary, but I like seeing the variable names before they get containerized
                cleanliness = Long.parseLong(line[0]);
                t = Integer.parseInt(line[1]);
                th = Integer.parseInt(line[2]);
                hl = Integer.parseInt(line[3]);
                l = Integer.parseInt(line[4]);
                dps = Integer.parseInt(line[5]);
                as = Integer.parseInt(line[6]);
                n = line[7].substring(1, line[7].length() - 1);//remove the "" surrounding the stuff
                dsc = line[8].substring(1, line[8].length() - 1);
                com = line[9].substring(1, line[9].length() - 1);
                sty = line[10].substring(1, line[10].length() - 1);
                Sword s = new Sword(t, th, hl, l, dps, as, n, dsc, com, sty);
                s.setCleanliness(cleanliness);
                s.setIndex(-1);
                swords_arr[i] = s;
                if (cleanliness != -1) {
                    s.setIndex(swords_heap.size());
                    swords_heap.add(s);
                    sword_tracker.insert(s.hashCode(), true);
                }
            }

            // Get requests' information

//            int[] requests = new int[n_reqs];
//            int[] requests_swords_in_hash = new int[n_reqs];
            BetterQueue<Integer> outstandingRequests= new BetterQueue<>();
            ArrayList<RequestPair> requestPairArray = new ArrayList<>(n_reqs);
            for (int i = 0; i < n_reqs; i++) {
                String[] line = bf.readLine().split(", ");

//                requests[i] = Integer.parseInt(line[0]);
//                requests_swords_in_hash[i] = Objects.hash(Integer.parseInt(line[0]),Integer.parseInt(line[1]),line[2],Integer.parseInt(line[3]));
                int time = Integer.parseInt(line[0]);
                int hash_code = Objects.hash(Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]), line[4].substring(1, line[4].length() - 1));
                String s = Integer.parseInt(line[1]) + " " + Integer.parseInt(line[2]) + " " + Integer.parseInt(line[3]) + " " + line[4];
                requestPairArray.add(new RequestPair(time, hash_code));
            }

            // Create hashtable for swords.

            BetterHashTable<Integer, Sword> swords_hash_table = new BetterHashTable<>();
            for (Sword sword : swords_arr) {
                swords_hash_table.insert(sword.hashCode(), sword);
            }

            /* Iterate through time. End if queue is empty.
             * At a time t, we first take care of the urgent tasks: checking if
             * there is a request for right now, and if yes, taking its sword and
             * putting it on the queue. Then, move to the necessary task: if
             * there is an outstanding request, and there's a free sword,
             * complete the request, for as many requests as possible.
             */
            // hashtable for swords, minheap for swords, arraylist for requests,
            // queue for requests?

            int time = 0;
            while (results.size() != n_reqs) {

                /* while there are still requests which are for the current time,
                 * add them to the outstanding request pairs queue. add its sword
                 * to the minheap. remove the request pair from the request pair
                 * array.
                 */

                double i = 0;
                while ((!requestPairArray.isEmpty()) && (requestPairArray.get(0).time == time)) {
                    outstandingRequests.add(requestPairArray.get(0).time);
                    Sword swordFromRequest = swords_hash_table.get(requestPairArray.get(0).sword_hash);
                    if (sword_tracker.get(swordFromRequest.hashCode()) == null) {
                        swordFromRequest.setCleanliness(swordFromRequest.getTimeToClean() + time + (i / 10000));
                        swordFromRequest.setIndex(swords_heap.size());
                        swords_heap.add(swordFromRequest);
                        sword_tracker.insert(swordFromRequest.hashCode(), true);
                    }
                    requestPairArray.remove(0);
                    i++;
                }

                // If all requests fulfilled, end program.

                if (results.size() == n_reqs) {
                    return results;
                }

                // While there are outstanding requests and clean swords, remove
                // a request pair from the queue and add it to results.
                // remove the sword used from the heap.

                while ((outstandingRequests.size() != 0) && (((long) swords_heap.peekMin().getCleanliness()) <= time)) {

                    Integer removedElement = outstandingRequests.remove();
//                    System.out.println("removed element is " + removedElement);
                    Sword swordUsed = swords_heap.removeMin();
                    sword_tracker.remove(swordUsed.hashCode());
                    results.add(new DetailedCleanSwordTime<>(time, (time - removedElement), swordUsed));
                }
                time++;
            }
            bf.close();
        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        System.out.println();
        System.out.println();
        for (DetailedCleanSwordTime<SwordType> result : results) {
            System.out.println(result);
        }
        return results;
    }
    private static class RequestPair implements Comparable<RequestPair> {
        int time;
        int sword_hash;
        RequestPair(int time, int sword_hash) {
            this.sword_hash = sword_hash;
            this.time = time;
        }

        @Override
        public int compareTo(RequestPair o) {
            if (this.time > o.time) {
                return -1;
            }
            if (this.time == o.time) {
                return 0;
            }
            return 1;
        }

        @Override
        public String toString() {
            return "RequestPair{" +
                    "time=" + time +
                    ", sword_hash=" + sword_hash +
                    '}';
        }
    }
}
