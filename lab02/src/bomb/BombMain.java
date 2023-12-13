package bomb;

import common.IntList;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        final int repeatTimes = 1337;
        final int totalTimes = 100000;

        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }

        Bomb b = new Bomb();

        if (phase >= 0) {
            b.phase0("39291226");
        }

        if (phase >= 1) {
            IntList password = new IntList();
            b.phase1(password.of(0, 9, 3, 0, 8));
        }

        if (phase >= 2) {
            b.phase2("1 ".repeat(repeatTimes) + "-81201430" + " 1".repeat(totalTimes - repeatTimes));
        }
    }
}
