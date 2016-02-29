package common;

public class AgentsWorldBase extends AgentsWorld {


    public AgentsWorldBase(double t, double vis, double p0, double phi) {
        super(t, vis, p0, phi);
    }

    public static final int AGENTS_N = 50;

    public static double[] agents_c = new double[]{
            20, 30, 30, 30, 2,
            10, 5, 12, 5, 4,
            30, 5, 8, 13, 10,
            20, 15, 15, 2, 20,
            8, 30, 8, 80, 10,
            20, 10, 5, 4, 25,
            8, 14, 5, 8, 18,
            15, 8, 3, 5, 1,
            7, 8, 7, 14, 8,
            15, 17, 19, 20, 7
    };

    @Override
    protected Agent[] initAgents() {
        Agent[] agents = new Agent[agents_c.length];
        initAgentsEx(agents, agents_c);
        return agents;
    }

    protected void initAgentsEx(Agent[] agents, double[] agents_c) {
        for (int i = 0; i < agents.length; i++) {
            double c = agents_c[i];
//            double u = 0.2;
//            double t = 0.4;    //净利润占总收入的百分比
//            double vis = 1;    //对供应链网络的可见度度量
//            double p0 = 0.2;    //基准二次影响概率
//            double phi = 0.5;    //二次影响衰减系数

            agents[i] = new Agent(c, this.t, this.vis, this.p0, this.phi);
        }
    }

    public int run() {
        callRandomBankrupt();
        once();
        return getBankruptCount();
    }

    public int run(int num) {
        callRandomBankrupt(num);
        once();
        return getBankruptCount();
    }

    public boolean run(int target, double asset, int count) {
        callRandomBankrupt(target, count);
        this.agents[target].cash = asset;
        once();
        return this.agents[target].isBankrupt();
    }

    @Override
    protected void initRelations(double[][] r) {
        double[][] relations = new double[agents_c.length][agents_c.length];
        relations[0][1] = 30;
        relations[1][2] = 40;
        relations[2][3] = 40;
        relations[3][4] = 5;
        relations[4][5] = 3;
        relations[5][6] = 10;
        relations[6][7] = 8;
        relations[7][8] = 10;
        relations[8][9] = 8;
        relations[10][1] = 20;
        relations[10][11] = 10;
        relations[10][12] = 15;
        relations[11][13] = 8;
        relations[12][13] = 10;
        relations[13][2] = 10;
        relations[13][18] = 5;
        relations[18][3] = 3;
        relations[15][16] = 25;
        relations[14][17] = 15;
        relations[16][17] = 20;
        relations[17][18] = 5;
        relations[17][20] = 15;
        relations[20][19] = 10;
        relations[3][19] = 30;
        relations[19][5] = 15;
        relations[19][21] = 15;
        relations[24][22] = 15;
        relations[22][21] = 10;
        relations[23][21] = 30;
        relations[5][29] = 5;
        relations[21][29] = 40;
        relations[29][7] = 10;
        relations[29][30] = 10;
        relations[29][31] = 15;
        relations[8][37] = 5;
        relations[37][8] = 2;
        relations[37][39] = 2;
        relations[23][25] = 50;
        relations[25][26] = 15;
        relations[25][27] = 10;
        relations[25][28] = 10;
        relations[26][31] = 7;
        relations[26][32] = 7;
        relations[27][32] = 2;
        relations[27][33] = 5;
        relations[28][33] = 7;
        relations[31][38] = 10;
        relations[31][36] = 8;
        relations[33][36] = 6;
        relations[33][43] = 4;
        relations[38][41] = 7;
        relations[40][41] = 5;
        relations[40][42] = 5;
        relations[36][42] = 12;
        relations[42][43] = 13;
        relations[23][34] = 30;
        relations[34][35] = 25;
        relations[35][44] = 10;
        relations[35][48] = 10;
        relations[45][47] = 20;
        relations[46][47] = 25;
        relations[47][48] = 15;
        relations[47][49] = 15;
        relations[49][48] = 10;


        for (int i = 0; i < agents_c.length; i++) {
            for (int j = 0; j < agents_c.length; j++) {
                r[i][j] = relations[j][i];
            }
        }
    }

}
