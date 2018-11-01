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
	private static Random random = new Random();
	public static int n = 42;
	public static int M = 20;
	public static int nMax = 50;
	public static int R = 10;
	public static double D = 0.002;
	/*
	* Initializes scanner which allows the user to interact with the program.
	*/
	private static Scanner sc = new Scanner(System.in);



	public static void main(String[] args) {

		for(int i = 0; i < n; i++){
			individuals(eventQueue, population);
		}
		System.out.println(population.size());
		System.out.println(mut);
		System.out.println(rep);
		System.out.println(dead);
	}

	public static void individuals(EventQueue eventQueue,Population population){
		//Creates a new individual and places him in population
		City[] city = CityGenerator.generate();
		Individual individual = new Individual(city);
		population.add(individual);

		//Defines the probability of events
		final double fit = 1 - Math.log(population.fitness(individual));
		final double mut = RandomUtils.getRandomTime(fit * M);
		final double rep = RandomUtils.getRandomTime(fit * (n / nMax) * R);
		final double dead = RandomUtils.getRandomTime(fit * D);

		//Makes an object type event.
		//Event(char type, double time, Individual individual) - To remember parameters
		Event eventMutation = new Event('m', mut, individual);
		Event eventReproduction = new Event('R', rep, individual);
		Event eventDeath = new Event('D', dead, individual);


		//adds events to eventQueue
		eventQueue.add(eventMutation);
		eventQueue.add(eventReproduction);
		eventQueue.add(eventDeath);
		System.out.println(mut + rep + dead);
	}
}
