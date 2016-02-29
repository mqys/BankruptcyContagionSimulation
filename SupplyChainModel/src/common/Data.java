package common;

import MyProject.DotGraph;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import java.util.ArrayList;

public class Data {

	private final ArrayList<Double> x = new ArrayList<Double>();
	private final ArrayList<Double> y = new ArrayList<Double>();
	
	
	public void addData(double x, double y){
		this.x.add(x);
		this.y.add(y);
	}
	
	public void plot(){
		final int length = x.size();
		double[] dx = new double[length];
		for(int i=0; i<length; i++){
			dx[i] = this.x.get(i);
		}
		MWNumericArray mx = new MWNumericArray(dx, MWClassID.DOUBLE);
		
		double[] dy = new double[length];
		for(int i=0; i<length; i++){
			dy[i] = this.y.get(i);
		}
		MWNumericArray my = new MWNumericArray(dy, MWClassID.DOUBLE);
		
		
		try {

			DotGraph figure = new DotGraph();
			figure.Plot(mx, my);
			figure.waitForFigures();
			figure.dispose();

		} catch (MWException e) {
			e.printStackTrace();
		}
		
	}
	
}
