import java.util.*;

public class Simulator {

	/*
	* Initializes final variables for the user to choose
	*/
	private static final int INITIAL_POPULATION_SIZE = 1;
	private static final int MAXIMUM_POPULATION_SIZE = 2;
	private static final int MUTATION_INTERVAL = 3;
	private static final int REPRODUCTION_INTERVAL = 4;
	private static final int DEATH_INTERVAL = 5;
	private static final int COMFORT_NORMALIZATION = 6;
	private static final int TOTAL_SIMULATION_TIME = 7;
	private static final int START_PROGRAM = 8;
	private static final int EXIT_PROGRAM = 0;

	/*
	* Initializes scanner which allows the user to interact with the program
	*/
	private static Scanner sc = new Scanner(System.in);
	private static int userInput;

	private static boolean run = true;

	public static void main(String[] args) {

		/*
		* Initializes EventQueue and Population instances.
		* CityGenerator has a static method called generate()
		* and it is therefore not neccesary to initialize CityGenerator as an instance
		*/
		EventQueue eventQueue = new EventQueue();
		Population population = new Population(0.003);
		

		/*
		* Prints out the name of the cities (test)
		*/
		// for(int i = 0; i < CityGenerator.generate().length; i++){
		// 	System.out.println(CityGenerator.generate()[i].name());
		// }

		//population.add(individual = new Individual());
		//System.out.println(population.size());





		do {
			textMenu();
			inputMenu();
			
			//!run will only make the program to run once
		}while(!run);
	}


	/*
	* Prints out a text menu for the user
	*/
	private static void textMenu(){
		System.out.println("__________________________________");
		System.out.println("");
		System.out.println("1. Initial size of the population");
		System.out.println("2. Maximum population size");
		System.out.println("3. Mutation interval");
		System.out.println("4. Reproduction interval");
		System.out.println("5. Death interval");
		System.out.println("6. Comfort normalization");
		System.out.println("7. Total simulation time");
		System.out.println("__________________________________");
		System.out.println("");
		System.out.println("8. Start program");
		System.out.println("0. Quit program");
		System.out.println("__________________________________");
	}

	/*
	* Allows the user to interact with the program.
	* The range of the input is 1 - 8 and 0.
	*/
	private static void inputMenu(){
		System.out.println("");
		System.out.println("Enter here: ");
		userInput = sc.nextInt();

	}

}