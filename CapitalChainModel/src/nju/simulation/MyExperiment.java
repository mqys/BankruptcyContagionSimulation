package nju.simulation;

import nju.agent.AgentRelations;
import nju.agent.FirmAgent;

import java.io.IOException;
import java.util.ArrayList;


public class MyExperiment extends AgentsWorld {
    public static long INTERVAL = 0;

    // some data need to be used besides simulationExp
    private int timeCount [];


    @Override
    protected void initRelations() {
        // everything has been done in initAgents()
    }

    @Override
    protected void initAgents() {
        // data
        // use FileModel to init state
        try {
            FileModel fileModel = new FileModel("agentsFile.txt", "relationFile.txt");
            // set relations
            AgentRelations.getInstance().initRelations(fileModel);
            // get agents this file
            this.agents = fileModel.getAgents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initAgents(double u, double aerfa, double e, double k) {

        // use FileModel to init state
        try {
            FileModel fileModel = new FileModel("agentsFile.txt", "relationFile.txt", u, aerfa, e, k);
            // set relations
            AgentRelations.getInstance().initRelations(fileModel);
            // get agents this file
            this.agents = fileModel.getAgents();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
    
    protected void initAgentsMap2(double u, double aerfa, double e, double k) {
    	FileModel fileModel =  FileModel.getInstance();
    	fileModel.setModel(u, aerfa, e, k);
		
		AgentRelations.getInstance().initRelations(fileModel);
		this.agents = fileModel.getAgents();
    }
    
    protected void initAgents(int targetIndex, double asset) {
        // use FileModel to init state
        try {
            FileModel fileModel = new FileModel("agentsFile.txt", "relationFile.txt", targetIndex, asset);
            // set relations
            AgentRelations.getInstance().initRelations(fileModel);
            // get agents this file
            this.agents = fileModel.getAgents();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

    public void init(double u, double aerfa, double e, double k){
        bankruptNum = 0;
        initAgents(u, aerfa, e, k);
//        initAgentsMap2(u, aerfa, e, k);
        initRelations();
    }

    public void init(int targetIndex, double asset) {
        bankruptNum = 0;
        initAgents(targetIndex, asset);
        initRelations();
    }

    // one round simulation
    @Override
    public void startSimulation() {

        int turnbefore_bankruptNums = 0;
        this.timestep = 0;
        while(turnbefore_bankruptNums != AgentsWorld.bankruptNum){
            turnbefore_bankruptNums = AgentsWorld.bankruptNum;
            // the next needed by exp2 to explore the time and num
//            this.timeCount[this.timestep] += turnbefore_bankruptNums;

            this.timestep++;
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //运行模拟
            for(int i = 0 ; i < agents.length ; i++){
                FirmAgent agent = agents[i];
                if(!agent.isBankruptcy())
                    agent.thinking();
            }

        }

        // for Exp2 replace the 0s in the array
//        for (int i = this.timestep; i < this.timeCount.length; ++i)
//            this.timeCount[i] += turnbefore_bankruptNums;

//        System.out.println("一次模拟结束");
    }

    //===========================Experiments==============================

    // Exp6
    public void simulationExp6() {
        ExperimentData.clean();
//        try {
//            Logger.start();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        int init_B_num =  1;
        //
        double u = 0.3;
        double aerfa = 0.5;
        double e = 2;
//        double k = 10;
        for(double k = 1.0 ; k <= 250; k += 0.1){

            // exp data init and collect
            final int repeatTimes = 500;
            double[] finRatio = new double[repeatTimes];
            double init_R_ratio = 0;

            // do the exp here
            for(int i = 0 ; i < repeatTimes ; i++){
                // construct the model
//                init();
                init(u, aerfa, e, k);

                if(init_B_num > agents.length)
                    break;
//                Logger.log_startSimulation();
                initBankruptcySource(init_B_num);
//                init_R_ratio = this.calBankruptRatio();
                // one round simulation
                startSimulation();
//                Logger.log_endSimulation();

                double final_R_ratio = this.calBankruptRatio();
                finRatio[i] = final_R_ratio;
            }

            double finRatio_avg = this.calAverage(finRatio);
//            finRatio_avg = ((double)((int)(finRatio_avg * 10000))) / 10000;
            SimulationResult line = new SimulationResult(k, finRatio_avg);
            ExperimentData.addData(line);

        }

//        Logger.stop();
        //用图显示结果。
        ExperimentData.showData();
    }

    // Exp5
    public void simulationExp5() {
        ExperimentData.clean();
//        try {
//            Logger.start();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        int init_B_num =  1;
        //
        double u = 0.3;
        double aerfa = 0.5;
//        double e = 2;
        double k = 10;
        for(double e = 1.0 ; e <= 20; e += 0.1){

            // exp data init and collect
            final int repeatTimes = 500;
            double[] finRatio = new double[repeatTimes];
            double init_R_ratio = 0;

            // do the exp here
            for(int i = 0 ; i < repeatTimes ; i++){
                // construct the model
//                init();
                init(u, aerfa, e, k);

                if(init_B_num > agents.length)
                    break;
//                Logger.log_startSimulation();
                initBankruptcySource(init_B_num);
//                init_R_ratio = this.calBankruptRatio();
                // one round simulation
                startSimulation();
//                Logger.log_endSimulation();

                double final_R_ratio = this.calBankruptRatio();
                finRatio[i] = final_R_ratio;
            }

            double finRatio_avg = this.calAverage(finRatio);
//            finRatio_avg = ((double)((int)(finRatio_avg * 10000))) / 10000;
            SimulationResult line = new SimulationResult(e, finRatio_avg);
            ExperimentData.addData(line);

        }

//        Logger.stop();
        //用图显示结果。
        ExperimentData.showData();
    }

    // Exp4 explores the asset of A and the possibility of its bankruptcy
    public void simulationExp4() {
        ExperimentData.clean();
//        try {
//            Logger.start();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        int init_B_num =  1;

        // data for this exp
        int targetIndex = 19;
        double asset = 0.0;

        for(; asset < 50; asset += 0.5){

            // exp data init and collect
            final int repeatTimes = 80;
            int targetRuptcyTimes = 0;
            double init_R_ratio = 0;

            // do the exp here
            for(int i = 0 ; i < repeatTimes ; i++){
                // construct the model
//                init();
//                init(u, aerfa, e, k);
                init(targetIndex, asset);

                if(init_B_num > agents.length)
                    break;
//                Logger.log_startSimulation();
//                initBankruptcySource(init_B_num);
                initBankruptcySource(init_B_num, targetIndex);
//                init_R_ratio = this.calBankruptRatio();

                // one round simulation
                startSimulation();
//                Logger.log_endSimulation();

                if (agents[targetIndex].isBankruptcy())
                    ++targetRuptcyTimes;

            }

            double bankruptcyPossibility = (double)targetRuptcyTimes / (double)repeatTimes;
            SimulationResult line = new SimulationResult(asset, bankruptcyPossibility);
            ExperimentData.addData(line);

        }

//        Logger.stop();
        //用图显示结果。
        ExperimentData.showData();
    }


    // Exp3 explore the aerfa and the BN/SN
    public void simulationExp3() {
        ExperimentData.clean();
//        try {
//            Logger.start();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        int init_B_num =  1;
        //
        double u = 0.3;
        double e = 2;
        double k = 10;
        for(double aerfa = 0.0 ; aerfa <= 1.0; aerfa += 0.01){

            // exp data init and collect
            final int repeatTimes = 300;
            double[] finRatio = new double[repeatTimes];
            double init_R_ratio = 0;

            // do the exp here
            for(int i = 0 ; i < repeatTimes ; i++){
                // construct the model
//                init();
                init(u, aerfa, e, k);
                if(init_B_num > agents.length)
                    break;
//                Logger.log_startSimulation();
                initBankruptcySource(init_B_num);
                init_R_ratio = this.calBankruptRatio();
                // one round simulation
                startSimulation();
//                Logger.log_endSimulation();

                double final_R_ratio = this.calBankruptRatio();
                finRatio[i] = final_R_ratio;
            }

            double finRatio_avg = this.calAverage(finRatio);
            SimulationResult line = new SimulationResult(aerfa, finRatio_avg);
            ExperimentData.addData(line);

        }

//        Logger.stop();
        //用图显示结果。
        ExperimentData.showData();
    }

    // Exp2 explores how the structure of the asset flow affects the results
    // need to add time record in function startSimulation()
    public void simulationExp2() {
        // start state mode: 1 company bankrupt
        int init_B_num = 1;
        ExperimentData.clean();
//        try {
//            Logger.start();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        // do the whole exp 5 times here
//        for(int k = 0 ; k < 5 ; k++){
            // exp data init and collect
            final int counts = 1000;
//            double[] incres = new double[counts];
            double init_R_ratio = 0;
            this.timeCount = new int[6];

            // do the exp here
            for(int i = 0 ; i < counts ; i  ++){
                init();
                if(init_B_num > agents.length)
                    break;
//                Logger.log_startSimulation();
                initBankruptcySource(init_B_num);
                init_R_ratio = this.calBankruptRatio();
                // one round simulation
                startSimulation();
//                Logger.log_endSimulation();

            }
//            for (int i = 0; i < this.timeCount.length; ++i)
//                System.out.println(i+"--->"+this.timeCount[i]);
            // deal with the data
            for (int i = 0; i < this.timeCount.length; ++i) {
                if (this.timeCount[i] != 0) {
//                    System.out.println("-=-=-="+this.timeCount[i]);
                    ExperimentData.addData(new SimulationResult(i, (double)this.timeCount[i] / counts / agents.length));
                }
            }

//            double incre_avg = this.calAverage(incres);
//            SimulationResult line = new SimulationResult(init_R_ratio, incre_avg);
//            ExperimentData.addData(line);

            //初始破产agent数增加；
//            init_B_num++;
//        }

//        Logger.stop();
        //用图显示结果。
        ExperimentData.showData();
    }

    // Exp1 explores how the init_B_num affects the final
    public void simulationExp1() {

        ExperimentData.clean();
//        try {
//            Logger.start();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        int init_B_num =  1;
        //
        for(int k = 0 ; k < 50; k++){

            // exp data init and collect
            final int counts = 500;
            double[] incres = new double[counts];
            double init_R_ratio = 0;

            // do the exp here
            for(int i = 0 ; i < counts ; i  ++){
                // construct the model
                init();
                if(init_B_num > agents.length)
                    break;
//                Logger.log_startSimulation();
                initBankruptcySource(init_B_num);
                init_R_ratio = this.calBankruptRatio();
                // one round simulation
                startSimulation();
//                Logger.log_endSimulation();

                double final_R_ratio = this.calBankruptRatio();
                double incre_R_ratio = final_R_ratio - init_R_ratio;

                incres[i] = incre_R_ratio;
            }

                double incre_avg = this.calAverage(incres);
                SimulationResult line = new SimulationResult(init_R_ratio, incre_avg);
                ExperimentData.addData(line);

            //初始破产agent数增加；
            init_B_num++;
        }

//        Logger.stop();
        //用图显示结果。
        ExperimentData.showData();
    }


    // -----------------------------------------
    //  helpers
    private double calBankruptRatio(){
        int len = agents.length;
        double ratio = ((double) AgentsWorld.bankruptNum ) / len;
        return ratio;
    }

    private double calAverage(double[] data){
        double temp = 0 ;
        int len = data.length;
        for(int i = 0 ; i < len ; i++){
            temp += data[i];
        }

        return temp/len;
    }
    private ArrayList<Integer> selectDistinctNums(final int n, final int len){
        if(n == 0)
            return new ArrayList<Integer>();
        if(n == len){
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for(int i = 0 ; i < len; i ++){
                temp.add(i);
            }

            return temp;
        }

        int count = n;
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(count > 0){
            //初始化破产节点
            int d = (int) Math.floor( Math.random() * len );
            if(d == len)
                d = len-1;

            if(!list.contains(d)){
                list.add(d);
                count--;
            }
        }


        return list;
    }
    private ArrayList<Integer> selectDistinctNums(final int n, final int len, int targetIndex){
        if(n == 0)
            return new ArrayList<Integer>();
        if(n == len){
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for(int i = 0 ; i < len; i ++){
                temp.add(i);
            }

            return temp;
        }

        int count = n;
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(count > 0){
            //初始化破产节点
            int d = (int) Math.floor( Math.random() * len );
            if(d == len)
                d = len-1;
            // ignore the target index
            if (d == targetIndex)
                continue;

            if(!list.contains(d)){
                list.add(d);
                count--;
            }
        }


        return list;
    }
    public void initBankruptcySource(final int n){
        int len = agents.length;
        if(n > len){
            throw new OutboundException();
        }

        if(n < len/2){
            ArrayList<Integer> indexes = this.selectDistinctNums(n, len);

            for(int i = 0; i < indexes.size() ;  i++){
                int index = indexes.get(i);
                agents[index].setBankruptcy();
            }
        }else{
            int exclude_nums = len - n;
            ArrayList<Integer> list = this.selectDistinctNums(exclude_nums, len);
            for(int i = 0 ; i < len ; i++){
                if(!list.contains(i)){
                    agents[i].setBankruptcy();
                }
            }
        }

        AgentsWorld.bankruptNum = n;//与设置多少个破产传染源有关
    }

    public void initBankruptcySource(final int n, int targetIndex) {
        int len = agents.length;
        if (n > len) {
            throw new OutboundException();
        }
        ArrayList<Integer> indexes = this.selectDistinctNums(n, len, targetIndex);
        for(int i = 0; i < indexes.size() ;  i++){
            int index = indexes.get(i);
            agents[index].setBankruptcy();
        }
        AgentsWorld.bankruptNum = n;//与设置多少个破产传染源有关

    }
}
