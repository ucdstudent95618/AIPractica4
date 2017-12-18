
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.Individual;


public class ExamDemo {
	
	public static int turnosNecesarios; // este valor se tiene que importar
	public  static List<Profesor> profesores = new ArrayList<Profesor>(); 
	public  static int numProfesores;
	public static final int tableSize = 16; 
	public  static List<String> files = Arrays.asList("archivosConfP4/configuracionConvocatoria1.txt", 
													  "archivosConfP4/configuracionConvocatoria2.txt", 
													  "archivosConfP4/configuracionConvocatoria3.txt");
	

	public static void main(String[] args) {
		
		for (int i = 0; i< 3; i++) {
			String file = new String();
			
			file = files.get(i);
			System.out.println("Estamos ejecutando el fichero: "+file);
			
			try {
				newNExamDemo(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				
			}
		}
		
	}

	private static void newNExamDemo(String file) throws FileNotFoundException {

		System.out.println("Ok I'm reading the file now..");
		
		File fileInReading = new File(file);
		
		if (fileInReading.exists()) {
			
			String line1; // numero de turnos necesarios;
			try {
				
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				line1 = br.readLine();
				turnosNecesarios = Integer.parseInt(line1);
				br.close(); // Guigdeygfiuwfgyuekr jgcfkervgyekgyci l
				System.out.println("Just makin' sure it should be some number <= than 16: " +turnosNecesarios);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String line2; // numero de turnos necesarios;
			try {
				
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				br.readLine(); // to skip the first one we already read
				line2 = br.readLine();
				List<String> namesOnLine2 = Arrays.asList(line2.split(","));
				
				System.out.println("Just makin' sure it should be a bunch of names:\n\n"+ namesOnLine2);
				
				numProfesores = namesOnLine2.size();
				
				for (int i = 0; i< numProfesores; i++) {
					String profesorName = namesOnLine2.get(i);
					Profesor profesor = new Profesor();
					profesor.nombre = profesorName;
					profesores.add(profesor);
				}
				
				
				br.close(); // Guigdeygfiuwfgyuekr jgcfkervgyekgyci l
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			String line3; // numero de turnos necesarios;
			try {
				
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				br.readLine(); // to skip the first one we already read
				br.readLine(); // to skip the second one we already read
				
				line3 = br.readLine(); 
				System.out.println("Now we are doing the restrictions maaaan!");
				
				int lineCount = 0; // goes from 0 to numProfesores -1
				while (line3 != null && lineCount < numProfesores) {
					
					List<String> temp = new ArrayList<String>(); // will contain useless professor name
					
					Profesor profesor = profesores.get(lineCount);
					
					temp = Arrays.asList(line3.split(": "));
					
					if (temp.size() > 1) { // if there are actually some restrictions
						List<String> splitRestricciones = Arrays.asList(temp.get(1).split(","));
						profesor.restricciones = convertStringToInteger(splitRestricciones);
						  
					}
					
					System.out.println("Here are your restrictions Mr. "+profesor.nombre + " maaaaan: "+ profesor.restricciones);
					
					line3 = br.readLine(); // goes to the new line
					lineCount++;
				}
				
				
				
				System.out.println("Now we are doing the preferencias maaaan!");
				
				lineCount = 0; // goes from 0 to numProfesores -1
				while (line3 != null && lineCount < numProfesores) {
					
					List<String> temp = new ArrayList<String>(); // will contain useless professor name
					
					Profesor profesor = profesores.get(lineCount);
					
					temp = Arrays.asList(line3.split(": "));
					
					if (temp.size() > 1) { // if there are actually some restrictions
						List<String> splitPreferencias = Arrays.asList(temp.get(1).split(","));
						profesor.preferencias = convertStringToInteger(splitPreferencias);
					}
					
					System.out.println("Here are your preferencias Mr. "+profesor.nombre + " maaaaan: "+ profesor.preferencias);
					
					line3 = br.readLine(); // goes to the new line
					lineCount++;	
				}
				
				br.close(); // Guigdeygfiuwfgyuekr jgcfkervgyekgyci l
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		ExamGeneticAlgorithmSearch();
	}

	

	private static List<Integer> convertStringToInteger(List<String> splitRestricciones) {
		
		List<Integer> tempList = new ArrayList<Integer>();
		for(String temp : splitRestricciones) {
			Integer number = Integer.parseInt(temp);
			tempList.add(number);
		}
		
		return tempList;
	}

	public static void ExamGeneticAlgorithmSearch() {
		System.out.println("\nExamDemo GeneticAlgorithm  -->");
		try {
			FitnessFunction<Integer> fitnessFunction = ExamGenAlgoUtil.getFitnessFunction();
			GoalTest goalTest = ExamGenAlgoUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(ExamGenAlgoUtil.generateRandomIndividual(numProfesores)); // numero de profesores
			}
			
			
			
			
			

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ExecuteAlgorithm() {
		
		for (int i = 0; i < 100; i++) {
		
			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(tableSize,
				ExamGenAlgoUtil.getFiniteAlphabetForProfesors(numProfesores), 0.15);
			
			// Run for a set amount of time
				Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

		}
		
			
			System.out.println("Max Time (1 second) Best Individual=\n"
					+ ExamGenAlgoUtil.getBoardForIndividual(bestIndividual));
			//System.out.println("Board Size      = " + turnosNecesarios);
			//System.out.println("# Board Layouts = " + (new BigDecimal(turnosNecesarios)).pow(turnosNecesarios));
			System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
			
			System.out.println("Is Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");
	
			
			System.out.println("");
			System.out
					.println("Goal Test Best Individual=\n" + ExamGenAlgoUtil.getBoardForIndividual(bestIndividual));
			//System.out.println("Board Size      = " + turnosNecesarios);
			//System.out.println("# Board Layouts = " + (new BigDecimal(turnosNecesarios)).pow(turnosNecesarios));
			System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("Is Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");
		
		
	}

}
