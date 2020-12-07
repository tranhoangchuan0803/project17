package local_search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
public class LocalSearch {
	public int N;
	public int K;
	public int d[]; // weight of n-th pack
	public int c[];
	public int c1[], c2[]; // lower and upper of n-th car
	public int sup = 0;
	public int maxIter = 10000;
	int runtime = 60;
	
	// local search parameter
	public int maxTabuSize = 10;
	public int seed = 0;
	Random rd = new Random(seed);
	public int tabuMove = 3;
	public int maxTabuListSize = 3;
	public int iterLoop = 5000;
	
	// param ngu
	private Queue<Indi> tabuList = new LinkedList<>();
	private int[] tabuNum;
	
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
	
	private boolean compare(Indi ind1, Indi ind2) {
		for (int i=0; i<N; ++i) {
			if (ind1.gene[i] != ind2.gene[i]) return false;
		}
		return true;
	}
	private boolean checkInTabuList(Indi ind) {
		 if (tabuList.size()==0) return false;
		 for (Indi member: tabuList) {
			 if (compare(ind, member)) return true;
		 }
		 return false;
	}
	private ArrayList<Indi> getNeighbors(Indi ind){
		ArrayList<Indi> sNeighbors = new ArrayList<>();
		for (int i = 0; i<N; ++i) {
			for (int j =0; j<=K; ++j) {
				Indi neighbor = ind.getNeighbor(i, j);
				sNeighbors.add(neighbor);
			}
		}
		// set fitness all
		return sNeighbors;
	}
	private double countTruck(Indi ind) {
		double res = 0;
		for (int i=0; i<K; ++i) {
			res += ind.mask[i]; 
		}
		return res;
	}
	public Indi run() {
		Indi sBest = new Indi(this, rd);
		Indi bestCandidate = sBest;
		sBest.init();
		sBest.gene[3] = 0;
		sBest.gene[4] = 0;
		System.out.println(sBest.fitness);
		sBest.show(true);
		// init
		tabuNum = new int[N];
		
		tabuList.add(sBest);
		while (iterLoop > 0) {
			ArrayList<Indi> sNeighbors = getNeighbors(bestCandidate);
			for (Indi sCandicate: sNeighbors) {
				if (countTruck(sCandicate) >= countTruck(bestCandidate) && !checkInTabuList(sCandicate)) {
					if (tabuNum[sCandicate.preMove] == 0)
						bestCandidate = sCandicate;
				}
			}
			if (bestCandidate.getFitness() > sBest.getFitness()) sBest = bestCandidate;
			// update tabu list
			tabuList.add(bestCandidate);
			tabuNum[bestCandidate.preMove] += maxTabuSize;
			for (int i =0; i<N; ++i) --tabuNum[i];
			if (tabuList.size()>maxTabuListSize) tabuList.remove();
		}
		iterLoop --;
		return sBest;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		LocalSearch s = new LocalSearch();
		s.getInput("test_data\\Sample\\test_data.txt");
//		s.getInput("test_data\\type1\\10x10.txt");
//		System.err.println(s.run());
		Indi ind = s.run();
		
		
		// test ham check in tabu list
//		Indi ind = new Indi(s, new Random());
//		Indi ind1 = new Indi(s, new Random());
//		ind1.init();
//		ind1.show(false);
//		ind.show(false);
//		s.tabuList.add(ind1);
//		s.tabuList.add(ind);
//		System.out.println(s.checkInTabuList(ind));
		
		
	}
}
