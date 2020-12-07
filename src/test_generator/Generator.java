package test_generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Generator {
	int seed = 0;
	Random rd = new Random(seed);
	int maxD = 100;
	int maxC = 200;
	int maxC2 = 1000;
	public void genTestAndWriteToFile(int N, int K, FileWriter fw) throws IOException {
		fw.write(N + " " + K + "\n");
		// sinh khoi luong va gia tri
		for (int i=0; i<N; ++i) {
			int d = rd.nextInt(maxD);
			int c = rd.nextInt(maxC);
			fw.write(d + " " + c + "\n");
		}
		
		// sinh can
		for (int i=0; i<K; ++i) {
			int c2 = rd.nextInt(maxC2);
			while (c2==0) c2 = rd.nextInt(maxC2);
			int c1 = rd.nextInt(c2);
			fw.write(c1 + " "+ c2+"\n");
		}
		fw.close();
	}
	public void genFile(String testType, int[][] size) throws IOException {
		String filePath = "test_data" + "\\" + testType+ "\\";
		for (int i=0; i< size.length; ++i) {
			// creat dir
			File f = new File(filePath);
			f.mkdir();
			String file = filePath + (size[i][0] + "x" + size[i][1]) + ".txt";
			FileWriter fw = new FileWriter(new File(file));
			genTestAndWriteToFile(size[i][0], size[i][1], fw);
		}
	}
	public static void main(String[] args) throws IOException {
		Generator gen = new Generator();
		// test nho
//		int[][] size= {{10,10}, {15,10}, {20,10}, {30,10}, {30,30}};
//		gen.genFile("type1", size);
//		
//		// volumn xe lon
//		int[][] size1= {{100,15}, {200,10}, {300,20}, {400, 30}};
//		gen.genFile("type2", size1);
//		
//		// volumn xe lon
//		int[][] size2= {{500,15}, {200,15}, {300,15}, {500, 30}};
//		gen.maxC2 = 500;
//		gen.genFile("type3", size2);
		
		int[][] size3= {{5, 6}, {8, 3}, {6, 3}, {4, 4}, {10, 4}};
		gen.maxC2 = 200;
		gen.genFile("very_small", size3);
	}
}
