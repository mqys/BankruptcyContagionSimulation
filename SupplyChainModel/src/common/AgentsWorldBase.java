package common;

public class AgentsWorldBase extends AgentsWorld {


    public static final int AGENTS_N = 50;
//    private static final double[] agents_c = new double[]{
//            100, 90, 90, 80, 20,
//            30, 60, 80, 20, 50,
//            30, 70, 60, 70, 70,
//            50, 40, 90, 40, 70,
//            10, 20, 80, 30, 20,
//            90, 10, 90, 30, 30,
//            30, 40, 50, 50, 20,
//            28, 20, 20, 10, 50,
//            60, 40, 20, 20, 10,
//            10, 20, 30, 10, 10
//    };

//     my data
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

    public AgentsWorldBase(double t, double vis, double p0, double phi) {
        super(t, vis, p0, phi);
    }

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

//    protected void initRelations(double[][] r) {
//        double[][] relations = new double[agents_c.length][agents_c.length];
//        relations[0][5] = 120;
//        relations[0][6] = 140;
//        relations[1][7] = 100;
//        relations[1][8] = 20;
//        relations[1][9] = 60;
//        relations[2][10] = 100;
//        relations[2][11] = 90;
//        relations[3][12] = 50;
//        relations[3][14] = 90;
//        relations[3][15] = 70;
//        relations[3][17] = 100;
//        relations[4][19] = 130;
//        relations[4][21] = 40;
//        relations[5][22] = 100;
//        relations[6][22] = 60;
//        relations[6][23] = 60;
//        relations[6][8] = 10;
//        relations[7][8] = 90;
//        relations[8][23] = 120;
//        relations[9][8] = 20;
//        relations[9][24] = 30;
//        relations[10][12] = 20;
//        relations[10][13] = 70;
//        relations[11][13] = 20;
//        relations[11][25] = 60;
//        relations[12][16] = 50;
//        relations[13][25] = 50;
//        relations[13][26] = 30;
//        relations[14][16] = 30;
//        relations[15][16] = 20;
//        relations[16][28] = 60;
//        relations[17][18] = 70;
//        relations[17][20] = 20;
//        relations[18][32] = 60;
//        relations[19][20] = 100;
//        relations[20][32] = 10;
//        relations[20][34] = 90;
//        relations[21][20] = 10;
//        relations[22][23] = 20;
//        relations[22][24] = 10;
//        relations[22][36] = 70;
//        relations[23][24] = 90;
//        relations[24][38] = 60;
//        relations[25][24] = 10;
//        relations[25][27] = 90;
//        relations[26][27] = 20;
//        relations[27][40] = 70;
//        relations[27][42] = 30;
//        relations[28][29] = 50;
//        relations[29][31] = 40;
//        relations[30][31] = 30;
//        relations[31][44] = 20;
//        relations[31][46] = 40;
//        relations[32][30] = 40;
//        relations[32][33] = 20;
//        relations[33][48] = 15;
//        relations[34][33] = 50;
//        relations[34][35] = 30;
//        relations[35][48] = 5;
//        relations[35][49] = 20;
//        relations[36][37] = 50;
//        relations[37][39] = 30;
//        relations[38][39] = 40;
//        //39
//        relations[40][41] = 50;
//        relations[41][43] = 20;
//        relations[42][43] = 10;
//        //43
//        relations[44][45] = 15;
//        relations[45][47] = 5;
//        relations[46][47] = 30;
//        //47
//        relations[48][49] = 5;
//        //49
//
//        for (int i = 0; i < agents_c.length; i++) {
//            for (int j = 0; j < agents_c.length; j++) {
//                r[i][j] = relations[j][i];
//            }
//        }
//    }
}
