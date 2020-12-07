package ga_solution;

import java.util.Random;

public class Individual implements Comparable<Individual>{
	public int gene[];
	public int N;
	public int K;
	public double fitness;
	public GA ga;
	public double mask[];
	Random rd;
	public Individual(GA ga, Random rd){
		this.ga = ga;
		N = ga.N;
		K = ga.K;
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
	public void setFitness() {
		// neu tong luong hang hoa trong xe nam ngoai gioi han cua xe coi nhu khong tinh luong hang hoa do
		
		// check xe
		this.fitness = 0;
		for (int i=0; i<N; ++i) {
			mask[gene[i]] += ga.d[i];
		}
		
		for (int i=0; i<K; ++i) {
			if (mask[i]<ga.c1[i] ||mask[i]>ga.c2[i]) mask[i] = 0;
			else mask[i] = 1;
		}
		mask[K] = 0;
		// caculate fitness
		for (int i=0; i<N; ++i){
			fitness += mask[gene[i]]*ga.c[i];
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
	public int compareTo(Individual ind) {
		// TODO Auto-generated method stub
		double res = this.fitness - ind.fitness;
		if (res > 0) return -1;
		if (res < 0) return 1;
		return 0;
	}
	
}
