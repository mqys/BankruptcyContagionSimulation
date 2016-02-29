package simulation;

import common.Agent;
import common.AgentsWorldBase;
import common.Data;

public class Simulation {

    private static double t = 0.4;
    private static double vis = 1;
    private static double p0 = 0.2;
    private static double phi = 0.5;

    public static void test() {
        Data data = new Data();
        for (int i = 1; i <= 500; i++) {
            AgentsWorldBase world = new AgentsWorldBase(t, vis, p0, phi);
            int count = world.run(1);

            data.addData(i, count);
        }
        data.plot();
    }

    public static void exp1() {
        Data data = new Data();
        for (int i = 1; i <= 50; i++) {
            int count = 0;
            // repeat 500 times
            for (int j = 0; j < 500; j++) {
                AgentsWorldBase world = new AgentsWorldBase(t, vis, p0, phi);
                count += world.run(i);
            }
            double bn1 = i / 50.0;
            double bn2 = count / 500.0 / 50.0;
            data.addData(bn1, bn2 - bn1);
        }
        data.plot();
    }

    public static void exp2() {
        Data data = new Data();
        double[] numOnturn = new double[10];

        for (int times = 0; times < 1000; ++times) {
            AgentsWorldBase world = new AgentsWorldBase(t, vis, p0, phi);
            world.setDataArray(numOnturn);
            world.run();
        }

        double last = 0.0;
        for (int step = 0; step < numOnturn.length; ++step) {
//            System.out.println(numOnturn[step]);
            double nowcount = numOnturn[step] > 0 ? numOnturn[step]: last;
            last = nowcount;
            data.addData((double) step, nowcount / 1000.0 / 50.0);
        }
        data.plot();
    }

    public static void exp3() {
        Data data = new Data();
        for (double newT = 0.0; newT < 1.0; newT += 0.01) {
            int bcount = 0;
            // repeat
            for (int times = 0; times < 500; ++times) {
                AgentsWorldBase world = new AgentsWorldBase(newT, vis, p0, phi);
                bcount += world.run();
            }
            double res = bcount / 500.0 / 50.0;
            data.addData(newT, res);
        }
        data.plot();
    }

    public static void exp4() {
        Data data = new Data();
        for (double asset = 0.0; asset < 20.0; asset += 0.05) {
            int targetAgent = 19;
            int bcount = 0;
            // repeat
            for (int times = 0; times < 1000; ++times) {
                AgentsWorldBase world = new AgentsWorldBase(t, vis, p0, phi);
                if (world.run(targetAgent, asset, 1))
                    ++bcount;
            }
            double res = bcount / 1000.0;
            data.addData(asset, res);
        }
        data.plot();
    }

    public static void exp5() {
        Data data = new Data();
        for (double newVis = 0.0; newVis < 100.0; newVis += 0.1) {
            int bcount = 0;
            // repeat
            for (int times = 0; times < 500; ++times) {
                AgentsWorldBase world = new AgentsWorldBase(t, newVis, p0, phi);
                bcount += world.run();
            }
            double res = bcount / 500.0 / 50.0;
            data.addData(newVis, res);
        }
        data.plot();
    }


    public static void exp6() {
        Data data = new Data();
        for (double newP0 = 0.0; newP0 < 1.0; newP0 += 0.002) {
            int bcount = 0;
            // repeat
            for (int times = 0; times < 500; ++times) {
                AgentsWorldBase world = new AgentsWorldBase(t, vis, newP0, phi);
                bcount += world.run();
            }
            double res = bcount / 500.0 / 50.0;
            data.addData(newP0, res);
        }
        data.plot();
    }

    public static void exp7() {
        Data data = new Data();
        for (double newPhi = 0.0; newPhi < 1.0; newPhi += 0.002) {
            int bcount = 0;
            // repeat
            for (int times = 0; times < 500; ++times) {
                AgentsWorldBase world = new AgentsWorldBase(t, vis, p0, newPhi);
                bcount += world.run();
            }
            double res = bcount / 500.0 / 50.0;
            data.addData(newPhi, res);
        }
        data.plot();
    }

    public static void main(String[] args) {
//        test();
//        exp1();
//        exp2();
//        exp3();
//        exp4();
//        exp5();
//        exp6();
//        exp7();

    }

}
