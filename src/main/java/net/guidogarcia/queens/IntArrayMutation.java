package net.guidogarcia.queens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

public class IntArrayMutation implements EvolutionaryOperator<int[]> {
    private final NumberGenerator<Probability> mutationProbability;

    public IntArrayMutation(Probability mutationProbability) {
        this.mutationProbability = new ConstantGenerator<Probability>(mutationProbability);
    }

    public List<int[]> apply(List<int[]> selectedCandidates, Random rng) {
        List<int[]> mutatedPopulation = new ArrayList<int[]>(selectedCandidates.size());
        for (int[] array : selectedCandidates) {
            mutatedPopulation.add(mutateArray(array, rng));
        }
        return mutatedPopulation;
    }

    private int[] mutateArray(int[] array, Random rng) {
        int[] result = Arrays.copyOf(array, array.length);
        if (mutationProbability.nextValue().nextEvent(rng)) {
            result[rng.nextInt(array.length)] = rng.nextInt(array.length);
        }
        return result;
    }
}
