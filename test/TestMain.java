import CommonUtils.BetterQueue;
import CommonUtils.BetterStack;

public class TestMain {
    public static void main(String[] args) {
//        BetterQueue newq = new BetterQueue();
//        for (int i = 1; i < 9; i++) {
//            newq.add(i);
//        }
//        for (int i = 1; i < 9; i++) {
//            System.out.println(newq.remove());
//        }
//        newq.setCapacity(16);
//    }
        BetterStack newstack = new BetterStack();
        for (int i = 0; i < 10; i++) {
            newstack.push(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(newstack.getStack()[i]);
        }
    }
}