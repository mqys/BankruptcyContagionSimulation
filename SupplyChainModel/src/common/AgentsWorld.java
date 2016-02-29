package common;

import java.util.ArrayList;
import java.util.Random;

public abstract class AgentsWorld {

    protected  Agent[] agents;
    protected double c;
    protected double t;
    protected double vis;
    protected double p0;
    protected double phi;

    protected Boolean countOnTurn = false;
    protected double[] dataArray;

    protected AgentsWorld(double t, double vis, double p0, double phi) {
        this.t = t;
        this.vis = vis;
        this.p0 = p0;
        this.phi = phi;

        Agent[] agents = initAgents();

        double[][] relationsValue = new double[agents.length][agents.length];
        initRelations(relationsValue);

        AgentsRelation relations = new AgentsRelation(agents, relationsValue);
        for (int i = 0; i < agents.length; i++) {
            agents[i].id = i;
            agents[i].globalRelations = relations;
        }

        this.agents = agents;

    }
//    protected AgentsWorld() {
//        Agent[] agents = initAgents();
//
//        double[][] relationsValue = new double[agents.length][agents.length];
//        initRelations(relationsValue);
//
//        AgentsRelation relations = new AgentsRelation(agents, relationsValue);
//        for (int i = 0; i < agents.length; i++) {
//            agents[i].id = i;
//            agents[i].globalRelations = relations;
//        }
//
//        this.agents = agents;
//    }

    protected abstract Agent[] initAgents();

    protected abstract void initRelations(double[][] relations);

    protected void onTurn(int step) {
//        System.out.println(step);
        dataArray[step] += getBankruptCount();
    }

    protected void finCount(int step) {
        for (int i = step; i < this.dataArray.length; ++i)
            this.dataArray[i] += getBankruptCount();
    }
    public final void setDataArray(double[] data) {
        countOnTurn = true;
        this.dataArray = data;
    }

    public final void callBankrupt(int id) {
        agents[id].bankrupt();
    }

    public final void callRandomBankrupt(int count) {
        Random random = new Random();
        ArrayList<Integer> n = new ArrayList<Integer>();
        int i = 0;
        while (i != count) {
            int t = random.nextInt(agents.length);
            if (!n.contains(t)) {
                n.add(t);
                i++;
            }
        }
        for (int g : n) {
            agents[g].bankrupt();
        }
    }

    public final void callRandomBankrupt(int target, int count) {
        Random random = new Random();
        ArrayList<Integer> n = new ArrayList<Integer>();
        int i = 0;
        while (i != count) {
            int t = random.nextInt(agents.length);
            if (t == target)
                continue;
            if (!n.contains(t)) {
                n.add(t);
                i++;
            }
        }
        for (int g : n) {
            agents[g].bankrupt();
        }
    }

    public final void callRandomBankrupt() {
        callRandomBankrupt(1);
    }

    public final int getBankruptCount() {
        int result = 0;
        for (Agent agent : agents) {
            if (agent.isBankrupt()) {
                result++;
            }
        }
        return result;
    }

    public final void once() {
        int last_bankrupt = 0;
        int step = 0;
        if (countOnTurn)
            onTurn(step);
        while (true) {
            for (Agent agent : agents) {
                agent.beforeTurn();
            }
            for (Agent agent : agents) {
                agent.doTurn();
            }
//            for (Agent agent : agents) {
//                agent.afterTurn();
//            }
            if (countOnTurn)
                onTurn(++step);
            int this_bankrupt = getBankruptCount();
            if (last_bankrupt == this_bankrupt) {
                if (countOnTurn)
                    finCount(++step);
                break;
            }
            last_bankrupt = this_bankrupt;
        }
    }

}
