package nju.simulation;

import nju.agent.FirmAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * simple simulation use matrix to store the relationship between companies
 * list to store the companies
 */
public class SimulationDemo {
    // connection between companies
    public static int connectionGraph[][] = {{0,1,0}, {0,0,1}, {0,0,0}};
    // companies list for iterating and updating status
    public static List<FirmAgent> agentList = new ArrayList<FirmAgent>();

    public static void main(String[] args) {

        // init the simulation demo figures, simple number, just for a little demo
        double u = 5.0, aerfa = 0.5, c = 7.0, e = 5, k = 0.5;
        // company 0 bankrupted at the begin
        agentList.add(new FirmAgent(String.valueOf(0), u, aerfa, 4.0, e, k));
        for (int i = 1; i < 3; ++i)
            agentList.add(new FirmAgent(String.valueOf(i), u, aerfa, c + i, e, k));

        // this set not work, the thinking() will still use money to judge isBankrupted
        //agentList.get(0).setBankruptcy();

        int numOfRuptcy, newNum = 1;
        int numOfCom = connectionGraph.length;

        do {
            numOfRuptcy = newNum;
            for (FirmAgent fa : agentList) {
                System.out.println("company "+fa.getID()+" : is thinking");
                fa.thinking();
                if (fa.isBankruptcy()) {
                    System.out.println("--- company "+fa.getID()+" : bankrupted here");
                    ++newNum;
                }
            }
        } while (numOfRuptcy != newNum && newNum < numOfCom);
    }
}
