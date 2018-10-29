import java.util.*;

public class Simulator {

	/*
	* Initializes final variables for the user to choose, and the userInput variable.
	*/

	private static final int INITIAL_POPULATION_SIZE = 1,
	MAXIMUM_POPULATION_SIZE = 2,
	MUTATION_INTERVAL = 3,
	REPRODUCTION_INTERVAL = 4,
	DEATH_INTERVAL = 5,
	COMFORT_NORMALIZATION = 6,
	TOTAL_SIMULATION_TIME = 7,
	START_PROGRAM = 8,
	EXIT_PROGRAM = 0;
	public static EventQueue eventQueue = new EventQueue();
	public static Population population = new Population(0.003);
	private static int userInput;

	/*
	* Initializes scanner which allows the user to interact with the program.
	*/

	private static Scanner sc = new Scanner(System.in);



	private static boolean run = true;

	public static void main(String[] args) {

		/*
		* Initializes EventQueue and Population instances.
		* CityGenerator has a static method called generate()
		* and it is therefore not neccesary to initialize CityGenerator as an instance.
		*/



		/*
		* Prints out the name of the cities (test).
		*/

		//for(int i = 0; i < CityGenerator.generate().length; i++){
		//System.out.println(CityGenerator.generate()[i].name());
		// }

		//population.add(individual = new Individual());
		//System.out.println(population.size());
		int population1 = 20;
		int i = 0;
		do {
			//textMenu();
			//inputMenu();
			System.out.println(population1);
			population1 = population1 + 1;
			initialPopulation(population1, i);

			if(i == 30)
			run = false;
			//!run will only make the program to run once.
		}while(run);
	}

	/*
	* Prints out a text menu for the user.
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

	private static void initialPopulation(int population1, int i){
		while(i < population1){
			population.add(new Individual(CityGenerator.generate()));
			i ++;
		}
	}
	private static void maxPopulation(int population){
	}
}
