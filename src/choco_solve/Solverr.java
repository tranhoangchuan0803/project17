package choco_solve;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import ga_solution.GA;


public class Solverr {
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
		IntVar[][] X = model.intVarMatrix("X",K, N, 0, 1);
		IntVar[][] Xt = new IntVar[N][K];
		for (int i=0; i<N; ++i) {
			for (int j=0; j<K; ++j)
				Xt[i][j] = X[j][i];
		}
		// moi hang chi trong 1 xe
		for (int i=0; i<N; ++i) {
			model.sum(Xt[i],"<=", 1).post();
		}
//		 set lb and ub
		for (int i=0; i<K; ++i) {
			model.scalar(X[i], d, ">=", c1[i]).post();
			model.scalar(X[i], d, "<=", c2[i]).post();
		}
		IntVar sum = model.intVar("sum", 0, sup);
		// duoi ma tran
		IntVar[] arr = new IntVar[N*(K)];
		int[]  w = new int[N*(K)];
		for (int i=0; i<K; ++i) {
			for (int j=0; j<N; ++j) {
				arr[i*N+j] = X[i][j];
				w[i*N+j] = c[j];
			}
		}
		model.scalar(arr, w, "=", sum).post();;
		model.setObjective(Model.MAXIMIZE, sum);
		Solver solver = model.getSolver();
		int[] res = new int[N];
		int iter = 0;
		int maxx = 0;
		long start = System.currentTimeMillis();
		while(solver.solve()) {
			System.out.println(sum.getValue());
			for (int i =0; i<K; ++i) {
				for (int j=0; j<N; ++j) {
//					System.out.print(X[i][j].getValue() + " ");
					if (X[i][j].getValue()==1) res[j] = i;
				}
//				System.out.println();
			}
			for (int i=0; i<N; ++i) System.out.print(res[i] + " ");
			System.out.println();
			maxx = sum.getValue()>maxx?sum.getValue():maxx;
			if (System.currentTimeMillis()>start + runtime*1000) break;
		}
		return maxx;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Solverr s = new Solverr();
//		s.getInput("test_data\\Sample\\test_data.txt");
		s.getInput("test_data\\type1\\10x10.txt");
		System.err.println(s.run());
	}
}
