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


	private static int 
	MUT_INTERVAL,
	REP_INTERVAL,
	D_INTERVAL;
	
	private static double COM_NOMRAL,
	T_SIM_SIZE,
	I_POP_SIZE, 
	MAX_POP_SIZE;

	/*
	* Initializes scanner which allows the user to interact with the program.
	*/

	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {




		int userInput;


		initialPopulation(initialPopulationSize);
		cityGenerator();
		printBestPath();
		do {
			defaultSettings();
			textMenu();
			System.out.println();
			System.out.println("Enter your decided option: ");
			userInput = input.nextInt();
			if(userInput == START_PROGRAM){
				//start program
			}
			else if(userInput == EXIT_PROGRAM){
				
			}
			
			else if(1 <= userInput && userInput < 8){
				inputMenu(userInput);
			}
			// will only make the program to run once.
			else
				System.out.println("Invalid option!");
			
		}while(userInput != EXIT_PROGRAM);
	}

	/*
	* Prints out a text menu for the user.
	*/

	private static void textMenu(){
		System.out.println("*************************************************************");
		System.out.println("* AVAILABLE OPTIONS                                         *");
		System.out.println("* Would you like to change som of it press enter the number *");
		System.out.println("*************************************************************");
		System.out.println();
		System.out.println(INITIAL_POPULATION_SIZE + ". Initial size of the population");
		System.out.println(MAXIMUM_POPULATION_SIZE + ". Maximum population size");
		System.out.println(MUTATION_INTERVAL + ". Mutation interval");
		System.out.println(REPRODUCTION_INTERVAL + ". Reproduction interval");
		System.out.println(DEATH_INTERVAL + ". Death interval");
		System.out.println(TOTAL_SIMULATION_TIME + ". Comfort normalization");
		System.out.println(TOTAL_SIMULATION_TIME + ". Total simulation time");
		System.out.println("_____________________________________________________________");
		System.out.println("");
		System.out.println(START_PROGRAM + ". Start program");
		System.out.println(EXIT_PROGRAM + ". Quit program");
		System.out.println("_____________________________________________________________");
	}

	private static void inputMenu(int userInput){
		while(userInput != START_PROGRAM){
			System.out.println();
			System.out.println("Enter here what you would like to change it to: ");
			userInput = input.nextInt();
			
			switch(userInput) {
	    		case INITIAL_POPULATION_SIZE: I_POP_SIZE = input.nextDouble(); break;
				case MAXIMUM_POPULATION_SIZE: MAX_POP_SIZE = input.nextDouble(); break;
				case MUTATION_INTERVAL: MUT_INTERVAL = input.nextInt(); break;
				case REPRODUCTION_INTERVAL: REP_INTERVAL = input.nextInt(); break;
				case DEATH_INTERVAL: D_INTERVAL = input.nextInt(); break;
				case COMFORT_NORMALIZATION: COM_NOMRAL = input.nextDouble(); break;
				case TOTAL_SIMULATION_TIME: T_SIM_SIZE = input.nextDouble(); break;
				case START_PROGRAM: break;

				default: System.out.println("Invalid option. Please try again.");
				System.out.println();
				break;
			}

		}		
	}

	/*
	* Allows the user to interact with the program.
	* The range of the input is 1 - 8 and 0.
	*/

	private static void defaultSettings(){
		I_POP_SIZE = 20;
		MAX_POP_SIZE = 200; 
		MUT_INTERVAL = 3;
		REP_INTERVAL = 15;
		D_INTERVAL = 30;
		COM_NOMRAL = 0.001;
		T_SIM_SIZE = 10.0;

		System.out.println("*************************************************************");
		System.out.println("*The are the default settings                               *");
		System.out.println("*************************************************************");
		System.out.println("Initial size of the population: " + I_POP_SIZE);
		System.out.println("Maximum population size: " + MAX_POP_SIZE);
		System.out.println("Mutation interval: " + MUT_INTERVAL);
		System.out.println("Reproduction interval: " + REP_INTERVAL);
		System.out.println("Death interval: " + D_INTERVAL);
		System.out.println("Comfort normalization: " + COM_NOMRAL);
		System.out.println("Total simulation time: " + T_SIM_SIZE);
		System.out.println();

	}

	public static void test(){
		/*
		* Initializes EventQueue and Population instances.
		* CityGenerator has a static method called generate()
		* and it is therefore not neccesary to initialize CityGenerator as an instance.
		*/
		//EventQueue eventQueue = new EventQueue();
		//Population population = new Population(0.003);
		

		/*
		* Prints out the name of the cities (test).
		*/

		// for(int i = 0; i < CityGenerator.generate().length; i++){
		// 	System.out.println(CityGenerator.generate()[i].name());
		// }

		//population.add(individual = new Individual());
		//System.out.println(population.size());
		/*switch(userInput) {
	    	case INITIAL_POPULATION_SIZE: break();
			case MAXIMUM_POPULATION_SIZE: break();
			case MUTATION_INTERVAL: break();
			case REPRODUCTION_INTERVAL: break();
			case DEATH_INTERVAL: break();
			case COMFORT_NORMALIZATION: break ();
			case TOTAL_SIMULATION_TIME: break();
			case START_PROGRAM: break();
			case EXIT_PROGRAM: break ();

	    	default: System.out.println("Invalid option. Please try again.");
			System.out.println();
			break;
			}
			*/
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
