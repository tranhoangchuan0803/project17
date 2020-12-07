package ga_solution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
	int numRun = 1;
	public void writeResult(String filePath, long runtime, double fittness) throws IOException {
		File f = new File(filePath);
		FileWriter fw = new FileWriter(f);
		fw.write("Runtime: " + runtime + "\n" + "Cost: "+ fittness);
		fw.close();
	}
	public void run() throws IOException {
		File f = new File("test_data");
		for (String type: f.list()) {
			// Creat file
			String filePath = "Result\\ga_result\\"+type + ".txt";
			File kk = new File("test_data\\" + type);
			File file = new File(filePath);
			FileWriter fw = new FileWriter(file);
			for (String name: kk.list()) {
				System.out.print(name);
				String real = name.split("\\.")[0];
				
				// Get data
				String truePath = "test_data\\" + type +"\\" +name;
				long t1 = System.currentTimeMillis();
				GA ga = new GA();
				ga.getInput(truePath);
				Individual max = new Individual(ga, new Random());
				max.setFitness();
				for (int i=0; i<numRun; ++i) {
					ga.seed = i;
					Population p = new Population(ga);
					Individual ind = p.run();
					if (ind.fitness > max.fitness) max = ind;
				}
				long now = System.currentTimeMillis();
				fw.write("Type: "+ real  + " Time: "+ (now-t1) + " Cost: "+ max.getFitness() + "\n");
			}
			fw.close();
		}
	}
	public static void main(String[] args) throws IOException {
		File f= new File("test_data\\Sample");
		Main m = new Main();
		m.run();
	}
}
