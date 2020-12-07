package choco_solve;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class Constraint {
	public int N;
	public int K;
	public int d[]; // weight of n-th pack
	public int c[];
	public int c1[], c2[]; // lower and upper of n-th car
	public int sup = 0;
	public int maxIter = 10000;
	int runtime = 60;
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
		d = new int[N];
		c = new int[N];
		c1 = new int[K];
		c2 = new int[K];
		
		for (int i = 0; i <N; i++) {
			d[i] = sc.nextInt(); // weight of pack
			c[i] = sc.nextInt(); // cost of paack
		}
		for (int i=0; i<K; ++i) {
			c1[i] = sc.nextInt(); // lower and upper bound
			c2[i] = sc.nextInt();
		}
		sup  = 0;
		for (int i=0; i<N; ++i) {
			sup+= c[i];
		}
	}
	
	public int run() {
		// Init var
		Model model = new Model("hihi");
		IntVar[] X = model.intVarArray("X",N, 0, K);
		IntVar[][] Sum = model.intVarMatrix("Sum", K, N+1, 0, sup);
		for (int i=0; i<K; ++i) {
			model.arithm(Sum[i][0], "=", 0).post();
		}
//		set lb and ub
		for (int i=0; i<K; ++i) {
			for (int j=0; j<N; ++j) {
				model.ifThenElse(
					model.arithm(X[j], "=", i),
					model.arithm(Sum[i][j+1], "-", Sum[i][j], "=", d[j]),
					model.arithm(Sum[i][j], "=", Sum[i][j+1]));			
			}
		}
		IntVar[] object = model.intVarArray("object", N+1, 0, sup);
		model.arithm(object[0], "=", 0).post();
		for (int i=0; i<N; ++i) {
			model.ifThenElse(
				model.arithm(X[i], "=", K), 
				model.arithm(object[i+1], "-", object[i], "=", c[i]), 
				model.arithm(object[i+1], "=", object[i]));
		}
		model.setObjective(Model.MINIMIZE, object[N]);
		Solver solver = model.getSolver();
		int[] res = new int[N];
		int iter = 0;
		int maxx = 0;
		long start = System.currentTimeMillis();
		while(solver.solve()) {
			System.out.println(object[N].getValue());
				
			for (int i=0; i<N; ++i) System.out.print(X[i].getValue() + " ");
			System.out.println();
			if (System.currentTimeMillis()>start + runtime*1000) break;
		}
		return maxx;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Solverr s = new Solverr();
			s.getInput("test_data\\Sample\\test_data.txt");
//		s.getInput("test_data\\type1\\10x10.txt");
		System.err.println(s.run());
	}
}
