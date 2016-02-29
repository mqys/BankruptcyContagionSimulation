package common;

import java.util.ArrayList;

public class AgentsRelation {

    private final Agent[] agents;
    private final double[][] relations;

    AgentsRelation(Agent[] agents, double[][] relations) {
        this.agents = agents;
        this.relations = relations;
    }

    public Agent[] getAliveDownstreamAgents(Agent me) {
        ArrayList<Agent> results = new ArrayList<Agent>();
        for (int i = 0; i < agents.length; i++) {
            Agent agent = agents[i];
            if (!agent.isBankrupt() && relations[me.id][i] > 0) {
                results.add(agent);
            }
        }

        return results.toArray(new Agent[results.size()]);
    }

    public Agent[] getAllDownstreamAgents(Agent me) {
        ArrayList<Agent> results = new ArrayList<Agent>();
        for (int i = 0; i < agents.length; i++) {
            Agent agent = agents[i];
            if (relations[me.id][i] > 0) {
                results.add(agent);
            }
        }

        return results.toArray(new Agent[results.size()]);
    }

    public Agent[] getAliveUpstreamAgents(Agent me) {
        ArrayList<Agent> results = new ArrayList<Agent>();
        for (int i = 0; i < agents.length; i++) {
            Agent agent = agents[i];
            if (!agent.isBankrupt() && relations[i][me.id] > 0) {
                results.add(agent);
            }
        }
        return results.toArray(new Agent[results.size()]);
    }

    public Agent[] getAllUpstreamAgents(Agent me) {
        ArrayList<Agent> results = new ArrayList<Agent>();
        for (int i = 0; i < agents.length; i++) {
            Agent agent = agents[i];
            if (relations[i][me.id] > 0) {
                results.add(agent);
            }
        }
        return results.toArray(new Agent[results.size()]);
    }

    public Agent[] getAllArroudAgents(Agent me) {
        ArrayList<Agent> results = new ArrayList<Agent>();
        for (int i = 0; i < agents.length; i++) {
            Agent agent = agents[i];
            if (relations[i][me.id] > 0 || relations[me.id][i] > 0) {
                results.add(agent);
            }
        }
        return results.toArray(new Agent[results.size()]);
    }

    public double getValue(Agent from, Agent to) {
        return relations[from.id][to.id];
    }

}
