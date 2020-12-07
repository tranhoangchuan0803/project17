package ga_solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class GA {
	public int N;
	public int K;
	public double d[]; // weight of n-th pack
	public double c[];
	public double c1[], c2[]; // lower and upper of n-th car
	public double sup = 0;
	// GA param
	public int convergence = 4000;
	public int popSize = 1000;
	public double pc = 0.9;
	public double pm = 0.1;
	public int seed = 0;
	public int numMutation = 50;
	public double prop = 0.5;
	public void getInput(String filePath) throws FileNotFoundException {
		/*
		 * N,K
		 * c[i]
		 * d[i]
		 * c1[i]
		 * c2[i]
		 * 
		 */
		Scanner sc = new Scanner(new File(filePath));
		N = sc.nextInt();
		K = sc.nextInt();
		d = new double[N];
		c = new double[N];
		c1 = new double[K];
		c2 = new double[K];
		
		for (int i = 0; i <N; i++) {
			d[i] = sc.nextDouble(); // weight of pack
			c[i] = sc.nextDouble(); // cost of paack
		}
		for (int i=0; i<K; ++i) {
			c1[i] = sc.nextDouble(); // lower and upper bound
			c2[i] = sc.nextDouble();
		}
		sup  = 0;
		for (int i=0; i<N; ++i) {
			sup+= c[i];
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		GA ga = new GA();
//		ga.getInput("test_data\\Sample\\test_data.txt");
		ga.getInput("test_data\\type3\\500x30");
		System.out.println("Get input success!");
		Population p = new Population(ga);
		Individual m = p.run();
		System.out.println(m.getFitness());
//		ga.getTestList();		
	}
}
