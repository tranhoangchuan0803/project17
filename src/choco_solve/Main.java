package choco_solve;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
			String filePath = "Result\\choco_result\\"+type + ".txt";
			File kk = new File("test_data\\" + type);
			File file = new File(filePath);
			FileWriter fw = new FileWriter(file);
			for (String name: kk.list()) {
				System.out.println(name);
				String real = name.split("\\.")[0];
				
				// Get data
				String truePath = "test_data\\" + type +"\\" +name;
				long t1 = System.currentTimeMillis();
				Solverr s = new Solverr();
				s.getInput(truePath);
				int max = 0;
				for (int i=0; i<numRun; ++i) {
					int res = s.run();
					max = res>max?res:max;
				}
				long now = System.currentTimeMillis();
				fw.write("Type: "+ real  + " Time: "+ (now-t1) + " Cost: "+ max + "\n");
			}
			fw.close();
		}
	}
	public static void main(String[] args) throws IOException {
		Main m = new Main();
		m.run();
	}
}
