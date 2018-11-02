import java.util.*;

public class Simulator {

	//Initializes final variables used for the inputMenu method

	private static final int INITIAL_POPULATION_SIZE = 1,
	MAXIMUM_POPULATION_SIZE = 2,
	MUTATION_INTERVAL = 3,
	REPRODUCTION_INTERVAL = 4,
	DEATH_INTERVAL = 5,
	COMFORT_NORMALIZATION = 6,
	TOTAL_SIMULATION_TIME = 7,
	SIMULATION_MODE = 8,
	START_PROGRAM = 9,
	EXIT_PROGRAM = 0;

	private static final int T_UNITS_MODE = 1,
	N_EVENTS_MODE = 2,
	VERBOSE_MODE = 3,
	SILENT_MODE = 4;

	//Used for developers only. Will print out extra details for debugging.

	private static boolean debug = false;

	//Initialises the default values.

	private static int I_POP_SIZE = 20,
	MAX_POP_SIZE = 200,
	MUT_INTERVAL = 3,
	REP_INTERVAL = 15,
	D_INTERVAL = 30,
	SIM_MODE = 1;

	private static double COM_NOMRAL = 0.001,
	T_SIM_SIZE = 10.0;


	//Initializes scanner which allows the user to interact with the program.

	private static Scanner input = new Scanner(System.in);

	//Initializes EventQueue and Population instances.

	private static EventQueue eventQueue = new EventQueue();
	private static Population population = new Population(0.001);

	private static boolean run = true;

	public static void main(String[] args) {
		defaultSettings();
		do {
			textMenu();
			System.out.println();
			inputMenu();
			if(I_POP_SIZE != 20)
			for(int i = 0; i < I_POP_SIZE; i++){
				individuals(eventQueue, population);
			}
		}while(!run);
			//time mode
			if(SIM_MODE == 1){
				System.out.println("Hej 1");
			}
			//Every n events
			else if(SIM_MODE == 2){
				System.out.println("Hej 2");
			}
			//Verbose mode
			else if(SIM_MODE == 3){
				for(int j = 0; j < I_POP_SIZE; j++){
				System.out.println(eventQueue.next().type());
			}
				System.out.println(population.size());
				printBestPath();
			}
			//Silent mode
			else if(SIM_MODE == 4){
				System.out.println("Hej 3");
			}
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
		System.out.println(INITIAL_POPULATION_SIZE + ". Initial size of the population: " + I_POP_SIZE);
		System.out.println(MAXIMUM_POPULATION_SIZE + ". Maximum population size: " + MAX_POP_SIZE);
		System.out.println(MUTATION_INTERVAL + ". Mutation interval: " + MUT_INTERVAL);
		System.out.println(REPRODUCTION_INTERVAL + ". Reproduction interval: " + REP_INTERVAL);
		System.out.println(DEATH_INTERVAL + ". Death interval: " + D_INTERVAL);
		System.out.println(COMFORT_NORMALIZATION + ". Comfort normalization: " + COM_NOMRAL);
		System.out.println(TOTAL_SIMULATION_TIME + ". Total simulation time: " + T_SIM_SIZE);
		System.out.println(SIMULATION_MODE + ". Simulation mode: " + SIM_MODE);
		System.out.println("_____________________________________________________________");
		System.out.println();
		System.out.println(START_PROGRAM + ". Start program");
		System.out.println(EXIT_PROGRAM + ". Quit program");
		System.out.println("_____________________________________________________________");
	}

	/*
	* Allows the user to interact with the program.
	* The range of the input is 0 - 9.
	*/

	private static void inputMenu(){
		int userInput;
		System.out.println();
		do {
			System.out.println("Select the options here: ");
			userInput = input.nextInt();
			switch(userInput) {
			   	case INITIAL_POPULATION_SIZE:
			   		System.out.println("Population size has been chosen");
			   		I_POP_SIZE = input.nextInt();
			   		System.out.println("Population size is " + I_POP_SIZE);
			   	break;
				case MAXIMUM_POPULATION_SIZE:
			   		System.out.println("Maximum population size has been chosen");
					MAX_POP_SIZE = input.nextInt();
					System.out.println("Maximum population size is " + MAX_POP_SIZE);
				break;
				case MUTATION_INTERVAL:
			   		System.out.println("Mutation interval has been chosen");
					MUT_INTERVAL = input.nextInt();
					System.out.println("Mutation interval is " + MUT_INTERVAL);
					break;
				case REPRODUCTION_INTERVAL:
			   		System.out.println("Reproduction interval has been chosen");
					REP_INTERVAL = input.nextInt();
					System.out.println("Reproduction interval " + REP_INTERVAL);
					break;
				case DEATH_INTERVAL:
			   		System.out.println("Death interval has been chosen");
					D_INTERVAL = input.nextInt();
					System.out.println("Death interval is " + D_INTERVAL);
					break;
				case COMFORT_NORMALIZATION:
			   		System.out.println("Comfort normalization has been chosen");
					COM_NOMRAL = input.nextDouble();
					System.out.println("Comfort normalization is " + COM_NOMRAL);
					break;
				case TOTAL_SIMULATION_TIME:
			   		System.out.println("Total simulation time has been chosen");
					T_SIM_SIZE = input.nextDouble();
					System.out.println("Total simulation time is " + T_SIM_SIZE);
					break;
				case SIMULATION_MODE:
					System.out.print("Simulation mode (1: every t units; 2: every n events; 3: verbose; 4: silent): ");
					SIM_MODE = input.nextInt();
					System.out.println("Simulation mode is " + SIM_MODE);
				case START_PROGRAM:
					if(debug)
			    		System.out.println("Starting program");
					break;
				case EXIT_PROGRAM:
					if(debug)
						System.out.println("Exiting program");
					System.exit(0);
				default:
					System.out.println("Invalid option. Please try again.");
					System.out.println();
				break;
			}
		}while(userInput != START_PROGRAM);
	}

	/*
	* Prints out the default settings.
	*/

	private static void defaultSettings(){
		System.out.println("*************************************************************");
		System.out.println("* These are the default settings                            *");
		System.out.println("*************************************************************");
		System.out.println("Initial size of the population: " + I_POP_SIZE);
		System.out.println("Maximum population size: " + MAX_POP_SIZE);
		System.out.println("Mutation interval: " + MUT_INTERVAL);
		System.out.println("Reproduction interval: " + REP_INTERVAL);
		System.out.println("Death interval: " + D_INTERVAL);
		System.out.println("Comfort normalization: " + COM_NOMRAL);
		System.out.println("Total simulation time: " + T_SIM_SIZE);
		System.out.println("Simulation mode: " + SIM_MODE);
		System.out.println();

	}

	/*
	* Prints the best individual's path by city names.
	*/

	private static void printBestPath(){
		//Defines total cost and the euclidean distance between two cities.
		double total = 0.0;
		double euclideanDistance = 0.0;

		System.out.print("The best path is: ");

		//Prints out all the city names by the best path.
		for(int i = 0; i < CityGenerator.generate().length; i++) {
			System.out.print(population.bestPath()[i].name());
			if(i < (CityGenerator.generate().length-1))
				System.out.print("; ");

			/*
			* The formula for the euclidean distance is being calculated here.
			* The formula is square root of delta x raised to 2 and delta y raised to 2.
			*/

			if(i < (CityGenerator.generate().length-1)) {

				//Defines delta x and delta y.
				double deltaX = (population.bestPath()[i+1].x() - population.bestPath()[i].x());
				double deltaY = (population.bestPath()[i+1].y() - population.bestPath()[i].y());

				//Formula for the euclidean distance is executed here.
				euclideanDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

				if(debug)
					System.out.println("Distance between " + population.bestPath()[i].name() + " and " + population.bestPath()[i+1].name() + " is: " + euclideanDistance);

				total = total + euclideanDistance;
			}
			else if(debug)
				System.out.println("The system is done calculating.");
		}
		System.out.print(" (cost: "+ total + ")");
	}

	public static void individuals(EventQueue eventQueue, Population population){
		//Creates a new individual and places him in population.
		City[] city = CityGenerator.generate();
		Individual individual = new Individual(city);
		population.add(individual);

		//Defines the probability of events.
		final double fit = 1 - Math.log(population.fitness(individual));
		final double mut = RandomUtils.getRandomTime(fit * MUT_INTERVAL);
		final double rep = RandomUtils.getRandomTime(fit * I_POP_SIZE/MAX_POP_SIZE * REP_INTERVAL);
		final double dead = RandomUtils.getRandomTime(fit * D_INTERVAL);

		//Makes an object type event.
		//Event(char type, double time, Individual individual) - To remember parameters
		Event eventMutation = new Event('M', mut, individual);
		Event eventReproduction = new Event('R', rep, individual);
		Event eventDeath = new Event('D', dead, individual);


		//adds events to eventQueue.
		eventQueue.add(eventMutation);
		eventQueue.add(eventReproduction);
		eventQueue.add(eventDeath);

		System.out.println("Dette er mutation probability " + mut);
		System.out.println(fit);
		System.out.println(I_POP_SIZE);
		System.out.println(REP_INTERVAL);
		System.out.println(MAX_POP_SIZE);
		System.out.println("Dette er Reproduction probability " + rep);
		System.out.println("Dette er death probability " + dead + '\n');
	}
}
