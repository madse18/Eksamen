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

	/*
	* Initializes EventQueue and Population instances.
	* CityGenerator has a static method called generate()
	* and it is therefore not neccesary to initialize CityGenerator as an instance.
	*/
	
	private static EventQueue eventQueue = new EventQueue();
	private static Population population = new Population(0.001);

	private static int userInput;
	private static Individual individual;
	private static int initialPopulationSize = 5000;
	private static boolean debug = false;

	/*
	* Initializes scanner which allows the user to interact with the program.
	*/

	private static Scanner sc = new Scanner(System.in);



	private static boolean run = true;

	public static void main(String[] args) {



		initialPopulation(initialPopulationSize);
		cityGenerator();
		printBestPath();
		do {
			//textMenu();
			//inputMenu();
			//!run will only make the program to run once.
		}while(!run);
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

	private static void initialPopulation(int size){
		int i = 0;
		int j = 0;
		while(i < size) {
			population.add(individual = new Individual(CityGenerator.generate()));
			for(j = 0; j < CityGenerator.generate().length; j++){
			System.out.print(individual.path()[j].name());
		}
			System.out.println(population.size());
			i++;
		}
	}
	private static void maxPopulation(int population){
	}

	/*
	* Prints the name and the coordinates of the cities. 
	*/

	private static void cityGenerator(){
		for(int i = 0; i < CityGenerator.generate().length; i++)
			System.out.println(CityGenerator.generate()[i].name() + " " + CityGenerator.generate()[i].x() + " " + CityGenerator.generate()[i].y());
	}

	/*
	* Prints the best individual's path by city names.
	*/

	private static void printBestPath(){
		double total = 0.0;
		double euclideanDistance = 0.0;

		System.out.print("The best path is: ");
		for(int i = 0; i < CityGenerator.generate().length; i++) {
			System.out.print(population.bestPath()[i].name());
			if(i < (CityGenerator.generate().length-1))
				System.out.print("; ");

			/* 
			* The formula for the euclidean distance is being calculated here.
			* The formula is square root of delta x raised to 2 and delta y raised to 2.
			* The algoritm will stop, if the it does not find the next x and y in the arraylist.
			*/ 

			if(i < (CityGenerator.generate().length-1)) {
				euclideanDistance = Math.sqrt(Math.pow((population.bestPath()[i+1].x() - population.bestPath()[i].x()), 2) + Math.pow((population.bestPath()[i+1].y() - population.bestPath()[i].y()), 2));
				if(debug)
					System.out.println("Distance between " + population.bestPath()[i].name() + " and " + population.bestPath()[i+1].name() + " is: " + euclideanDistance);
				total = total + euclideanDistance;
			}
			else if(debug)
				System.out.println("The system is done calculating.");
	}
	System.out.print(" (cost: "+ total + ")");
}


}
