package nju.simulation;

import nju.agent.FirmAgent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  this model is used to:
 *     1. read data from file
 *     2. according to the file format, transform file context into the data model the simulation requires
 *     3. this model will used by simulation class to generate the simulation process
 */

public class FileModel {

    private double[][] graphs;
    private FirmAgent[] agents;
    private HashMap<String, Integer> table;
    private int count = 0;

    private static FileModel filemodel;

    private double u = 0.3;// 破产阈值，u >0 ===>>> u * asset,
    private double aerfa = 0.5;//破产传染逆向影响系数，  0<=aerfa<=1
    private double e = 2;// 周期回复最小值
    private double k = 10;// 周期回复速率指标。k越大，回复越慢(周期回复的值越小）。
    // test
//    public static void main(String[] args) throws IOException{
//        FileModel m = new FileModel("agentsFile.txt","relationFile.txt");
//
//        System.out.println("company name list: ");
//        for (int i = 0; i < m.agents.length; ++i) {
//            System.out.print(m.agents[i].getID()+" ");
//        }
//        System.out.println();
//        System.out.println("relation graphs: ");
//        for (int i = 0; i < m.count; ++i) {
//            for (int j = 0; j < m.count; ++j) {
//                System.out.print(m.graphs[i][j]);
//                System.out.print("  ");
//            }
//            System.out.println();
//        }
//    }

    //
    public static FileModel getInstance() {
    	if (filemodel == null)
    		filemodel = new FileModel();
    	return filemodel;
    }
    //
    public FileModel() {
        this.count = 50;
    	this.graphs = new double[count][count];  
    	this.table = new HashMap<String, Integer>();
    	this.agents = new FirmAgent[count];
    }
    
    public FileModel(String agentsFile, String relationFile) throws IOException{
        readFirmAgents(agentsFile);
        readRelations(relationFile);
    }
    //
    public FileModel(String agentsFile, String relationFile, double u, double aerfa, double e, double k) throws IOException {
        this.u = u;
        this.aerfa = aerfa;
        this.e = e;
        this.k = k;
        readFirmAgents(agentsFile);
        readRelations(relationFile);
    }

    public FileModel(String agentsFile, String relationFile, int targetIndex, double asset) throws IOException {
        readFirmAgents(agentsFile, targetIndex, asset);
        readRelations(relationFile);
    }

    public FileModel(double u2, double aerfa2, double e2, double k2) {
		// TODO Auto-generated constructor stub
        this.u = u2;
        this.aerfa = aerfa2;
        this.e = e2;
        this.k = k2;
        
    	List<FirmAgent> templist = new ArrayList<FirmAgent>();
        
        double [] c = 
        	{100,15,30,45,35,20,30,28,25,20,
			25,28,20,5,5,10,15,5,20,45,
			35,5,30,50,60,25,40,25,35,5,
			10,25,20,10,19,25,35,2,10,15,
			35,50,15,20,25,100,50,25,18,50};
        this.count = c.length;
        table = new HashMap<String, Integer>();
        for (int i = 0; i < c.length; ++i) {
        	templist.add(new FirmAgent(String.valueOf(i), c[i]*u, aerfa, c[i], e, k));
            table.put(String.valueOf(i), i);
        }
        agents = new FirmAgent[templist.size()];
        templist.toArray(agents);
        
        double[][] relations = new double[count][count];
        
        relations[0][7] = 50;relations[0][8] = 20;relations[0][9] = 30;relations[1][0] = 20;relations[1][5] = 15;
		relations[2][0] = 40;relations[3][0] = 50;relations[4][0] = 30;relations[4][8] = 10;relations[5][10] = 25;
		relations[7][12] = 30;relations[7][14] = 5;relations[8][14] = 5;relations[8][16] = 7;relations[8][17] = 6;
		relations[9][10] = 5;relations[9][12] = 5;relations[9][13] = 10;relations[10][30] = 15;relations[11][5] = 30;
		relations[12][15] = 18;relations[12][18] = 4;relations[13][27] = 4;relations[13][31] = 4;relations[14][12] = 3;
		relations[14][33] = 2;relations[15][35] = 10;relations[16][33] = 15;relations[18][35] = 20;relations[19][3] = 25;
		relations[19][4] = 25;relations[20][4] = 25;relations[20][16] = 13;relations[21][3] = 10;relations[22][3] = 30;
		relations[23][1] = 5;relations[23][2] = 50;relations[24][1] = 30;relations[24][11] = 10;relations[24][43] = 20;
		relations[25][6] = 5;relations[25][23] = 25;relations[26][10] = 10;relations[26][11] = 22;relations[27][31] = 30;
		relations[28][11] = 5;relations[28][26] = 35;relations[29][26] = 10;relations[32][27] = 20;relations[33][36] = 10;
		relations[33][37] = 5;relations[34][18] = 20;relations[35][36] = 30;relations[37][38] = 2;relations[39][20] = 10;
		relations[39][38] = 10;relations[40][19] = 30;relations[40][20] = 10;relations[41][19] = 30;relations[41][20] = 20;
		relations[41][22] = 20;relations[42][22] = 20;relations[44][29] = 10;relations[44][32] = 25;relations[45][6] = 30;
		relations[45][21] = 20;relations[45][42] = 40;relations[46][23] = 35;relations[46][24] = 25;relations[47][24] = 30;
		relations[48][24] = 20;relations[49][28] = 50;relations[49][29] = 50;relations[49][43] = 5;
		
		this.graphs = relations;
	}
    
    public void setModel(double u2, double aerfa2, double e2, double k2) {
    	this.u = u2;
        this.aerfa = aerfa2;
        this.e = e2;
        this.k = k2;
        
    	List<FirmAgent> templist = new ArrayList<FirmAgent>();
        
        double [] c = 
        	{100,15,30,45,35,20,30,28,25,20,
			25,28,20,5,5,10,15,5,20,45,
			35,5,30,50,60,25,40,25,35,5,
			10,25,20,10,19,25,35,2,10,15,
			35,50,15,20,25,100,50,25,18,50};
//        this.count = c.length;
//        table = new HashMap<String, Integer>();
        for (int i = 0; i < c.length; ++i) {
        	templist.add(new FirmAgent(String.valueOf(i), c[i]*u, aerfa, c[i], e, k));
            table.put(String.valueOf(i), i);
        }
//        agents = new FirmAgent[templist.size()];
        templist.toArray(agents);
        
        double[][] relations = this.graphs;
        
        relations[0][7] = 50;relations[0][8] = 20;relations[0][9] = 30;relations[1][0] = 20;relations[1][5] = 15;
		relations[2][0] = 40;relations[3][0] = 50;relations[4][0] = 30;relations[4][8] = 10;relations[5][10] = 25;
		relations[7][12] = 30;relations[7][14] = 5;relations[8][14] = 5;relations[8][16] = 7;relations[8][17] = 6;
		relations[9][10] = 5;relations[9][12] = 5;relations[9][13] = 10;relations[10][30] = 15;relations[11][5] = 30;
		relations[12][15] = 18;relations[12][18] = 4;relations[13][27] = 4;relations[13][31] = 4;relations[14][12] = 3;
		relations[14][33] = 2;relations[15][35] = 10;relations[16][33] = 15;relations[18][35] = 20;relations[19][3] = 25;
		relations[19][4] = 25;relations[20][4] = 25;relations[20][16] = 13;relations[21][3] = 10;relations[22][3] = 30;
		relations[23][1] = 5;relations[23][2] = 50;relations[24][1] = 30;relations[24][11] = 10;relations[24][43] = 20;
		relations[25][6] = 5;relations[25][23] = 25;relations[26][10] = 10;relations[26][11] = 22;relations[27][31] = 30;
		relations[28][11] = 5;relations[28][26] = 35;relations[29][26] = 10;relations[32][27] = 20;relations[33][36] = 10;
		relations[33][37] = 5;relations[34][18] = 20;relations[35][36] = 30;relations[37][38] = 2;relations[39][20] = 10;
		relations[39][38] = 10;relations[40][19] = 30;relations[40][20] = 10;relations[41][19] = 30;relations[41][20] = 20;
		relations[41][22] = 20;relations[42][22] = 20;relations[44][29] = 10;relations[44][32] = 25;relations[45][6] = 30;
		relations[45][21] = 20;relations[45][42] = 40;relations[46][23] = 35;relations[46][24] = 25;relations[47][24] = 30;
		relations[48][24] = 20;relations[49][28] = 50;relations[49][29] = 50;relations[49][43] = 5;
		
		this.graphs = relations;
    }
    
	// read firm information form file and build the id to index map
    public void readFirmAgents(String filename) throws IOException{
        File file = new File(filename);
        if (!file.exists())
            throw new IOException("file not found");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String firm_id;
        double asset;
        List<FirmAgent> templist = new ArrayList<FirmAgent>();
        table = new HashMap<String, Integer>();

        while (br.ready()) {
            String line = br.readLine();
            String[] data = line.split("\t");

            firm_id = data[0];
            asset = Double.parseDouble(data[1]);
            templist.add(new FirmAgent(firm_id, asset * u, aerfa, asset, e, k));
            table.put(firm_id, count);
            ++count;
        }
        agents = new FirmAgent[templist.size()];
        templist.toArray(agents);

        br.close();
        fr.close();
    }

    public void readFirmAgents(String filename, int targetIndex, double asset) throws IOException {
        File file = new File(filename);
        if (!file.exists())
            throw new IOException("file not found");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String firm_id;
        double file_asset;
        List<FirmAgent> templist = new ArrayList<FirmAgent>();
        table = new HashMap<String, Integer>();

        while (br.ready()) {
            String line = br.readLine();
            String[] data = line.split("\t");

            firm_id = data[0];
            file_asset = Double.parseDouble(data[1]);
            // change targetAgent asset
            if (count == targetIndex)
                file_asset = asset;

            templist.add(new FirmAgent(firm_id, file_asset * u, aerfa, file_asset, e, k));
            table.put(firm_id, count);
            ++count;
        }
        agents = new FirmAgent[templist.size()];
        templist.toArray(agents);

        br.close();
        fr.close();
    }

    // read relations between agents
    public void readRelations(String filename) throws IOException{
        File file = new File(filename);
        if (!file.exists())
            throw new IOException("file not found");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        graphs = new double[count][count];
        
        int id1, id2;
        double fund;
        while (br.ready()) {
            String line = br.readLine();
            String[] data = line.split("\t");

            id1 = Integer.parseInt(data[0]);
            id2 = Integer.parseInt(data[1]);
            fund = Double.parseDouble(data[2]);

            // need to minus 1 due to the mistake of input data
            graphs[id1-1][id2-1] = fund;
        }

        br.close();
        fr.close();
    }


    public double[][] getGraphs() {
        return graphs;
    }

    public FirmAgent[] getAgents() {
        return agents;
    }

    public HashMap<String, Integer> getTable() {
        return table;
    }

    public int getCount() {
        return count;
    }
}
