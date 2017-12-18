import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;



public class ExamGenAlgoUtil {

	public static FitnessFunction<Integer> getFitnessFunction() {
		return new ExamFitnessFunction();
	}

	public static GoalTest getGoalTest() {
		return new ExamGenAlgoGoalTest();
	}



	public static Individual<Integer> generateRandomIndividual(int nProfesors) {
		List<Integer> individualRepresentation = new ArrayList<Integer>();
		for (int i = 0; i < ExamDemo.tableSize; i++) { // table size should be 16 always
			individualRepresentation.add(new Random().nextInt(nProfesors+1));

		}
		Individual<Integer> individual = new Individual<Integer>(individualRepresentation);

		return individual;
	}


	public static Collection<Integer> getFiniteAlphabetForProfesors(int nProfesores) { 
		Collection<Integer> finiteAlphabet = new ArrayList<Integer>();

		for (int i = 0; i <= nProfesores; i++) {
			finiteAlphabet.add(i);
		}

		return finiteAlphabet;
	}



	// Modify to inluce balance between shifts
	public static class ExamFitnessFunction implements FitnessFunction<Integer> {
		
		public double apply(Individual<Integer> individual) {

			double fitness = ExamDemo.tableSize; // nSlots to fill

			ExamBoard board = getBoardForIndividual(individual);
			fitness -=board.getNumberOfViolatedRestrictions();

			// only apply preferences if we have a valid solution
			if(fitness == ExamDemo.tableSize) {
			
				float preferences = board.getNumberOfPreferencesApplied(); 
				
				float giniIndex = 1 -getGiniIndex(individual); // 1 is best 0 is worst
				
				fitness += (preferences + giniIndex)/2;
			
			}
			
			return fitness;
		}
		
		public float getGiniIndex(Individual<Integer> individual) {
			
			ExamBoard board = getBoardForIndividual(individual);
			
			float giniIndex; 

			float sum = 0;
			for(int i = 1; i<=ExamDemo.numProfesores; i++) {
				float workDaysForProfesor = (float)(board.workDays(i))/(float)(ExamDemo.turnosNecesarios);
				workDaysForProfesor = workDaysForProfesor*workDaysForProfesor;

				sum += workDaysForProfesor;
			}
			float I = 1 - sum;
			float k = (float) ExamDemo.numProfesores;
			giniIndex = (k*I)/(k-1);
			
			return giniIndex;
		}
		
	

		
	}

	
	public static class ExamGenAlgoGoalTest implements GoalTest {
		private final ExamGoalTest goalTest = new ExamGoalTest();

		@SuppressWarnings("unchecked")
		public boolean isGoalState(Object state) {
			return goalTest.isGoalState(getBoardForIndividual((Individual<Integer>) state));
		}
	}


	// hecho
	public static ExamBoard getBoardForIndividual(Individual<Integer> individual) {

		int tableSize = ExamDemo.tableSize;

		ExamBoard board = new ExamBoard(tableSize);
		for (int i = 0; i < tableSize; i++) {
			int prof = individual.getRepresentation().get(i);

			board.addProfessorAt(Integer.valueOf(prof), new Integer(i));
		}

		return board;
	}
	
	
}
