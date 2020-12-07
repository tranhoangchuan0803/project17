package local_search;

import java.util.Random;

public class Indi implements Comparable<Indi> {
	public int gene[];
	public int N;
	public int K;
	public double fitness;
	public LocalSearch lc;
	public double mask[];
	public Random rd;
	public int preMove;
	// gene[i] = k if good ith in the kth truck
	public Indi(LocalSearch lc, Random rd){
		this.lc = lc;
		N = lc.N;
		K = lc.K;
		preMove = 0;
		gene = new int[N];
		this.fitness = 0;
		mask = new double[K+1];
		mask[K] = 0;
		this.rd = rd;
	}
	public void init() {
		// Khoi tao random kieu gene
		for (int i=0; i<N; ++i) {
			gene[i] = rd.nextInt(K+1);
		}
		this.setFitness();
	}
	public Indi getNeighbor(int n, int k) {
		Indi res = new Indi(lc, rd);
		for (int i = 0; i<N; ++i) {
			res.gene = this.gene;
		}
		res.gene[n] = k;
		res.preMove = n;
		res.setFitness();
		return res;
	}
	public void setFitness() {
		// neu tong luong hang hoa trong xe nam ngoai gioi han cua xe coi nhu khong tinh luong hang hoa do
		
		// check xe
		this.fitness = 0;
		for (int i=0; i<N; ++i) {
			mask[gene[i]] += lc.d[i];
		}
		
		for (int i=0; i<K; ++i) {
			if (mask[i]<lc.c1[i] ||mask[i]>lc.c2[i]) mask[i] = 0;
			else mask[i] = 1;
		}
		mask[K] = 0;
		// caculate fitness
		for (int i=0; i<N; ++i){
			fitness += mask[gene[i]]*lc.c[i];
		}
	}
	public double getFitness() {
		return this.fitness;
	}
	
	public void show(boolean showMask) {
		for (int i=0; i<N; ++i) {
			System.out.print(gene[i] + "  ");
		}
		System.out.println();
		if (showMask) {
			for (int i=0; i<=K; ++i) System.out.print(mask[i] + "  ");
			System.out.println();
		}
	}
	@Override
	public int compareTo(Indi ind) {
		// TODO Auto-generated method stub
		double res = this.fitness - ind.fitness;
		if (res > 0) return -1;
		if (res < 0) return 1;
		return 0;
	}
}
