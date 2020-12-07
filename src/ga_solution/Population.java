package ga_solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
	public Random rd;
	public GA ga;
	public int popSize;
	public int N;
	public int K;
	
	public ArrayList<Individual> pop;
	public Population(GA ga) {
		this.ga = ga;
		rd = new Random(ga.seed);
		popSize = ga.popSize;
		pop = new ArrayList<>();
		this.N = ga.N;
		this.K = ga.K;
	}
	public void init() {
		for (int i =0; i<popSize; ++i) {
			Individual ind = new Individual(ga, rd);
			ind.init();
			ind.setFitness();
			pop.add(ind);
		}
	}
	public ArrayList<Individual> crossover(Individual p1, Individual p2){
		// lai ghep 2 diem cat
		ArrayList<Individual> offspring = new ArrayList<>();
		int s1;
		int s2;
		s1 = rd.nextInt(N);
		s2 = s1 + rd.nextInt(N-s1);
		Individual child1 = new Individual(ga, rd);
		Individual child2 = new Individual(ga, rd);
		for (int i=0; i<s1; ++i) {
			child1.gene[i] = p1.gene[i];
			child2.gene[i] = p2.gene[i];
		}
		for (int i=s2+1; i<N; ++i) {
			child1.gene[i] = p1.gene[i];
			child2.gene[i] = p2.gene[i];
		}
		for (int i=s1; i<=s2; ++i) {
			child2.gene[i] = p1.gene[i];
			child1.gene[i] = p2.gene[i];
		}
		offspring.add(child1);
		offspring.add(child2);
		return offspring;
	}
	public void mutate(Individual child) {
		for (int i=0; i<ga.numMutation; ++i) {
			child.gene[rd.nextInt(N)] = rd.nextInt(K+1);
		}
	}
	public Individual run() {
		init();
		for (int i=0; i<ga.convergence; ++i) {
			double pm,pc;
			ArrayList<Individual> newPop = new ArrayList<>(pop.subList(0, (int)(popSize*ga.prop)));
			while (newPop.size() < 2*popSize) {
				int i1, i2;
				i1 = rd.nextInt(popSize);
				i2 = rd.nextInt(popSize);
				pm = rd.nextDouble();
				if (pm>ga.pm) continue;
				ArrayList<Individual> offspring = crossover(pop.get(i1), pop.get(i2));
				pc = rd.nextDouble();
				if (pc<ga.pc) {
					mutate(offspring.get(0));
					mutate(offspring.get(1));
				}
				offspring.get(0).setFitness();
				offspring.get(1).setFitness();
				newPop.addAll(offspring);
			}
			Collections.sort(newPop);
			pop = newPop;
			System.out.println("Generation " + i + ": " + pop.get(0).getFitness());
			pop.get(0).show(true);
			if (ga.sup==pop.get(0).getFitness()) break;
		}
		return pop.get(0);
	}
}
