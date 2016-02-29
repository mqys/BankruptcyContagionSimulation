package common;

import java.util.ArrayList;

public class Agent {

    private final double t;    //净利润占总收入的百分比
    private final double vis;    //对供应链网络的可见度度量
    private final double p0;    //基准二次影响概率
    private final double phi;    //二次影响衰减系数
    private final double c;        //初始资产
    private final double u;        //破产阈值比
    private final double y;    //影响感染破产速度的参数
    private final double a;     //可视度对抑制力缓解效果权重
    int id;
    AgentsRelation globalRelations;
    double cash;

    private boolean isBankrupt = false;
    private ArrayList<BankruptEvent> eventsBuffer = new ArrayList<BankruptEvent>();
    private ArrayList<BankruptEvent> currentEvents = new ArrayList<BankruptEvent>();

    public Agent(double c, double t, double vis, double p0, double phi) {
        this.c = c;
        this.cash = c;

        this.u = 0.3; // u is fixed
        this.y = 1.0; // y is fixed
        this.a = 0.5; // a is fixed
        this.t = t;
        this.vis = vis;
        this.p0 = p0;
        this.phi = phi;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void bankrupt() {
        this.isBankrupt = true;
        for (Agent agent : globalRelations.getAliveUpstreamAgents(this)) {
            double value = globalRelations.getValue(agent, this);
            BankruptEvent e = new BankruptEvent(value, false);
            agent.informBankrupt(e);
        }
        for (Agent agent : globalRelations.getAliveDownstreamAgents(this)) {
            double value = globalRelations.getValue(this, agent);
            BankruptEvent e = new BankruptEvent(value, true);
            agent.informBankrupt(e);
        }
    }

    public void informBankrupt(BankruptEvent e) {
        eventsBuffer.add(e);
    }

    public void beforeTurn() {
        currentEvents = eventsBuffer;
        eventsBuffer = new ArrayList<BankruptEvent>();
    }

    public void doTurn() {
//        BankruptCal cal = new BankruptCal();
        for (BankruptEvent e : currentEvents) {
            double f;
            double p;
            if (e.fromUpstream) {
                f = getFFromUpstream(e.value);
                p = getP();

                // 增加对间接下游结点的影响
                // 若当前结点收到上游结点的破产影响, 则有一点概率对其下游造成间接影响
                Agent[] down = globalRelations.getAliveDownstreamAgents(Agent.this);
                int downCount = down.length;
                for (Agent a : down) {
                    if (Math.random() < (1 / downCount)) {
                        double value = globalRelations.getValue(this, a);
                        double force = f * a.getFFromUpstream(value) / t / value;
//                        System.out.println(force);
                        a.cash -= force;
                    }
                }
            } else {
                f = getFFromDownstream(e.value);
                p = getP();
            }
            this.cash -= f;

            // 二次影响
            if (Math.random() < p) {
                this.cash -= f * phi;
            }
        }

        if (this.cash < this.c * this.u) {
            bankrupt();
        }
    }

//    public void afterTurn() {
//        if (!isBankrupt && cash <= c) {
//            double incre = (c - cash) / k;
//            if (incre < e) {
//                incre = e;
//            }
//            this.cash += incre;
//        }
//    }

//    private class BankruptCal {

    double getFFromDownstream(double value) {
        Agent[] arrounds = globalRelations.getAllArroudAgents(Agent.this);
        int b = 0;
        for (Agent agent : arrounds) {
            if (agent.isBankrupt()) {
                b++;
            }
        }

        Agent[] downstreams = globalRelations.getAllDownstreamAgents(Agent.this);
        double vb = 0.0;
        for (Agent agent : downstreams) {
            vb += globalRelations.getValue(Agent.this, agent);
        }
        return value * t * (Math.exp(-y * vis) * a + 1.0 - a) * b * value / arrounds.length / vb;
    }

//        double getPFromDownstream(double value) {
//            Agent[] arrounds = globalRelations.getAllArroudAgents(Agent.this);
//            int b = 0;
//            for (Agent agent : arrounds) {
//                if (agent.isBankrupt()) {
//                    b++;
//                }
//            }
//
////            Agent[] downstreams = globalRelations.getAllDownstreamAgents(Agent.this);
////            double vb = 0.0;
////            for (Agent agent : downstreams) {
////                vb += globalRelations.getValue(Agent.this, agent);
////            }
////            return p0 * b * value / arrounds.length / vb;
//            return p0 * b / arrounds.length;
//        }

    double getFFromUpstream(double value) {
        Agent[] upstreams = globalRelations.getAllUpstreamAgents(Agent.this);
        Agent[] downstreams = globalRelations.getAllDownstreamAgents(Agent.this);
        double in = 0.0;
        for (Agent agent : downstreams) {
            in += globalRelations.getValue(Agent.this, agent);
        }
        double out = 0.0;
        for (Agent agent : upstreams) {
            out += globalRelations.getValue(agent, Agent.this);
        }

        int b = 0;
        Agent[] arrounds = globalRelations.getAllArroudAgents(Agent.this);
        for (Agent agent : arrounds) {
            if (agent.isBankrupt()) {
                b++;
            }
        }

        double vc = 0.0;
        for (Agent agent : upstreams) {
            vc += globalRelations.getValue(agent, Agent.this);
        }

        return value * in * t * (Math.exp(-y * vis) * a + 1.0 - a) * b * value / out / arrounds.length / vc;
    }

    double getP() {
        Agent[] arrounds = globalRelations.getAllArroudAgents(Agent.this);
        int b = 0;
        for (Agent agent : arrounds) {
            if (agent.isBankrupt()) {
                b++;
            }
        }
        return p0 * b / arrounds.length;
    }
//        double getPFromUpstream(double value) {
//            int b = 0;
//            Agent[] arrounds = globalRelations.getAllArroudAgents(Agent.this);
//            for (Agent agent : arrounds) {
//                if (agent.isBankrupt()) {
//                    b++;
//                }
//            }
////            double vc = 0.0;
////            Agent[] upstreams = globalRelations.getAllUpstreamAgents(Agent.this);
////            for (Agent agent : upstreams) {
////                vc += globalRelations.getValue(agent, Agent.this);
////            }
////            return p0 * b * value / arrounds.length / vc;
//
//            return p0 * b / arrounds.length;
//        }

//    }

}
