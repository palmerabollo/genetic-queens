package net.guidogarcia.queens;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.operators.IntArrayCrossover;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

public class Queens {
    public static final int BOARD_SIZE = 8;

    public static void main(String[] args) {
        CandidateFactory<int[]> candidateFactory = new AbstractCandidateFactory<int[]>() {
            public int[] generateRandomCandidate(Random rng) {
                int[] positions = new int[BOARD_SIZE];
                for (int i = 0; i < BOARD_SIZE; i++) {
                    positions[i] = rng.nextInt(BOARD_SIZE);
                }
                return positions;
            }
        };

        List<EvolutionaryOperator<int[]>> operators = new LinkedList<EvolutionaryOperator<int[]>>();
        operators.add(new IntArrayCrossover());
        operators.add(new IntArrayMutation(new Probability(0.05d)));
        EvolutionaryOperator<int[]> evolutionScheme = new EvolutionPipeline<int[]>(operators);

        FitnessEvaluator<int[]> fitnessEvaluator = new BoardEvaluator();

        EvolutionEngine<int[]> engine = new GenerationalEvolutionEngine<int[]>(
                candidateFactory,
                evolutionScheme,
                fitnessEvaluator,
                new RouletteWheelSelection(), // selection strategy
                new MersenneTwisterRNG()); // random generator

        engine.addEvolutionObserver(new EvolutionObserver<int[]>() {
            public void populationUpdate(PopulationData<? extends int[]> data) {
                System.out.printf("Generation %d: %s => %.2f, avg %.2f\n",
                        data.getGenerationNumber(),
                        Arrays.toString(data.getBestCandidate()),
                        data.getBestCandidateFitness(),
                        data.getMeanFitness());
            }
        });

        int[] result = engine.evolve(500, // population size
                                     1,  // elitism
                                     new TargetFitness(0, false));
        print(result);
    }

    public static void print(int[] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[col] == BOARD_SIZE - row - 1) {
                    System.out.print("* ");
                } else {
                    System.out.print("· ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

class BoardEvaluator implements FitnessEvaluator<int[]> {
    public double getFitness(int[] candidate, List<? extends int[]> population) {
        int errors = 0;
        for (int i = 0; i < candidate.length; i++) {
            for (int j=i+1; j < candidate.length; j++) {
                if (candidate[i] == candidate[j]) {
                    errors++;
                }
                if (candidate[i] - candidate[j] == j - i) {
                    errors++;
                }
                if (candidate[j] - candidate[i] == j - i) {
                    errors++;
                }
            }
        }
        return errors;
    }

    public boolean isNatural() {
        return false;
    }
}

