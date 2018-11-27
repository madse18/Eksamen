import java.util.*;

public class Simulator {

	//Initialises the numbers for simulation modes.
	private static final int TIMEPERUNITMODE = 1,
		NUMBEROFEVENTSMODE = 2,
		VERBOSEMODE = 3,
		SILENTMODE = 4;

	//Initialises variables for all the inputs and a variable for events simulated.
	private static int initialPopulationSize,
		maximumPopulationSize,
		mutationInterval,
		reproductionInterval,
		deathInterval,
		intervalObservation,
		timeSimulationSize,
		simulationMode,
		eventsSimulated = 0;

	private static double omega;

	//Initializes scanner which allows the user to interact with the program.
	private static Scanner input = new Scanner(System.in);

	//Initializes EventQueue, Population, City[] and event instances.
	private static EventQueue eventQueue = new EventQueue();
	private static Population population = new Population(omega);
	private static City[] cities;
	private static Event event;

	/*
     * Runs a input menu for the user, and stores the input in the class variables 
     * for the other methods to use.
	 */
	private static void inputMenu() {
		System.out.println("Enter the following information: ");
		System.out.println("Size of the initial population: ");
		initialPopulationSize = input.nextInt();
		System.out.println("Maximum size of the population: ");
		maximumPopulationSize = input.nextInt();
		System.out.println("Value of the parameter Omega: ");
		omega = input.nextDouble();
		System.out.println("Value of the parameter Death: ");
		deathInterval = input.nextInt();
		System.out.println("Value of the parameter Mutation: ");
		mutationInterval = input.nextInt();
		System.out.println("Value of the parameter Reproduction: ");
		reproductionInterval = input.nextInt();
		System.out.println("Time for the simulation to run: ");
		timeSimulationSize = input.nextInt();
		System.out.println("Simulation mode: 1, every t units; 2, every n event; 3, verbose; 4, silent");
		simulationMode = input.nextInt();
		while (simulationMode <= -0 || simulationMode >= 5) {
			System.out.println("You accidently typed " + simulationMode + ". Please try again.");
			simulationMode = input.nextInt();
		}
		if (simulationMode == 1 || simulationMode == 2) {
			System.out.println("Enter the time between observations: ");
			intervalObservation = input.nextInt();
		}
	}

	/*
     * Add individuals to the population and adds mutation, reproduction and death events affected by the new individual to the event queue.
	 */
	public static void addIndividuals(EventQueue eventQueue, Population population) {
		Individual individual = new Individual(cities);
		population.add(individual);

		double minusOneLogIndividualFitness = (1 - (Math.log(population.fitness(individual))));
		double mutationTime = RandomUtils.getRandomTime(minusOneLogIndividualFitness * mutationInterval);

		double reproductionTime = RandomUtils.getRandomTime(minusOneLogIndividualFitness * initialPopulationSize / maximumPopulationSize * reproductionInterval);
		double deathTime = RandomUtils.getRandomTime(1 - Math.log(1 - population.fitness(individual))) * deathInterval;

		Event eventMutation = new Event(event.MUTATION, mutationTime, individual);
		Event eventReproduction = new Event(event.REPRODUCTION, reproductionTime, individual);
		Event eventDeath = new Event(event.DEATH, deathTime, individual);

		eventQueue.add(eventMutation);
		eventQueue.add(eventReproduction);
		eventQueue.add(eventDeath);
	}

	/*
	 * Runs the mutation event. If the individual mutates, add the event to the event queue.
	 * Otherwise add an event of which average time is divided by ten.
	 */
	private static void mutationEvent() {
		eventsSimulated++;
		boolean hasMutated = false;
		double mutationTime = event.time() + RandomUtils.getRandomTime((1 - Math.log(population.fitness(event.individual())) * mutationInterval));
		double mutationTimeDivided10 = event.time() + RandomUtils.getRandomTime((1 - Math.log(population.fitness(event.individual())) * mutationInterval / 10.0));

		Event eventMutation = new Event(event.MUTATION, mutationTime, event.individual());
		Event eventMutation2 = new Event(event.MUTATION, mutationTimeDivided10, event.individual());

		if (RandomUtils.getRandomEvent(Math.pow(1 - population.fitness(event.individual()), 2))) {
			event.individual().mutate();

			hasMutated = true;

			if (RandomUtils.getRandomEvent(0.3)) {
				event.individual().mutate();

				if (RandomUtils.getRandomEvent(0.15)) {
					event.individual().mutate();
				}
			}
		}

		if (hasMutated) {
			eventQueue.add(eventMutation);

		} else {
			eventQueue.add(eventMutation2);

		}
	}

	/*
	 * Runs the reproduction event. 
	 * Adds a reproduction event in the event queue affecting the reproduced individual.
	 * Adds mutation, reproduction and death events in the event queue
	 * affecting the new individual in the population.
	 */
	private static void reproductionEvent() {
		eventsSimulated++;
		Individual reproducedIndividual = event.individual().reproduce();

		double minusOneLogIndividualFitness = 1 - Math.log(population.fitness(event.individual()));
		double reproductionTime = event.time() + RandomUtils.getRandomTime(minusOneLogIndividualFitness * initialPopulationSize / maximumPopulationSize * reproductionInterval);

		Event eventReproduction = new Event(event.REPRODUCTION, reproductionTime, event.individual());

		minusOneLogIndividualFitness = 1 - Math.log(population.fitness(reproducedIndividual));
		double mutationTime = event.time() + RandomUtils.getRandomTime(minusOneLogIndividualFitness * mutationInterval);
		reproductionTime = event.time() + RandomUtils.getRandomTime(minusOneLogIndividualFitness * initialPopulationSize / maximumPopulationSize * reproductionInterval);
		double deathTime = event.time() + RandomUtils.getRandomTime(minusOneLogIndividualFitness * deathInterval);

		Event newEventMutation = new Event(event.MUTATION, mutationTime, reproducedIndividual);
		Event newEventReproduction = new Event(event.REPRODUCTION, reproductionTime, reproducedIndividual);
		Event newEventDeath = new Event(event.DEATH, deathTime, reproducedIndividual);

		eventQueue.add(eventReproduction);
		eventQueue.add(newEventDeath);
		eventQueue.add(newEventReproduction);
		eventQueue.add(newEventMutation);

		population.add(reproducedIndividual);
	}

	/*
	 * Runs the death event. If the individual survives, another death event affecting
	 * the individual will be added to the event queue. Otherwise remove the individual
	 * from the population.
	 */
	private static void deathEvent() {
		eventsSimulated++;
		if (RandomUtils.getRandomEvent(1 - Math.pow(population.fitness(event.individual()), 2))) {
			population.remove(event.individual());
		} else {

			Event newEventDeath = new Event(event.DEATH, event.time() + (1 - Math.log(1 - population.fitness(event.individual())) * deathInterval), event.individual());
			eventQueue.add(newEventDeath);

		}
	}

	/*
	 * Prints the best individual's path by city names and total cost in euclidean distance.
	 */
	private static void printBestPath() {
		double total = 0.0;
		double euclideanDistance = 0.0;

		System.out.print("The best path is: ");

		for (int i = 0; i < population.bestPath().length; i++) {
			System.out.print(population.bestPath()[i].name());

			if (i < (population.bestPath().length - 1)) {
				System.out.print("; ");
			}

			if (i < (population.bestPath().length - 1)) {

				euclideanDistance = population.bestPath()[i].distanceTo(population.bestPath()[i + 1]);

				total = total + euclideanDistance;

			} else {

				euclideanDistance = population.bestPath()[i].distanceTo(population.bestPath()[0]);

				total = total + euclideanDistance;
			}
		}
		System.out.println(" (cost: " + total + ")");
	}

	public static void main(String[] args) {
		inputMenu();
		cities = CityGenerator.generate();
		for (int i = 0; i < initialPopulationSize; i++) {
			addIndividuals(eventQueue, population);
		}

		int newInterval = intervalObservation;
		event = eventQueue.next();
		while (event.time() <= timeSimulationSize) {
			if (eventQueue.hasNext() && population.contains(event.individual())) {
				if (simulationMode == TIMEPERUNITMODE) {
					if (event.type() == event.MUTATION) {
						mutationEvent();
					} else if (event.type() == event.REPRODUCTION) {
						reproductionEvent();
					} else if (event.type() == event.DEATH) {
						deathEvent();
					}
					if (population.size() > maximumPopulationSize) {
						eventsSimulated++;
						population.epidemic();
					}

					if (event.time() >= newInterval) {
						newInterval = intervalObservation + newInterval;
						System.out.println("Current time: " + event.time());
						System.out.println("Events simulated: " + eventsSimulated);
						System.out.println("Population size: " + population.size());
						printBestPath();
						System.out.println();
					}
					
				} else if (simulationMode == NUMBEROFEVENTSMODE) {
					if (event.type() == event.MUTATION) {
						mutationEvent();
					} else if (event.type() == event.REPRODUCTION) {
						reproductionEvent();
					} else if (event.type() == event.DEATH) {
						deathEvent();
					}
					if (population.size() > maximumPopulationSize) {
						eventsSimulated++;
						population.epidemic();
					}

					if (eventsSimulated >= newInterval) {
						newInterval = intervalObservation + newInterval;
						System.out.println("Current time: " + event.time());
						System.out.println("Events simulated: " + eventsSimulated);
						System.out.println("Population size: " + population.size());
						printBestPath();
						System.out.println();
					}
				} else if (simulationMode == VERBOSEMODE) {
					if (event.type() == event.MUTATION) {
						System.out.println(event);
						mutationEvent();
					} else if (event.type() == event.REPRODUCTION) {
						System.out.println(event);
						reproductionEvent();
					} else if (event.type() == event.DEATH) {
						System.out.println(event);
						deathEvent();
					}

					if (population.size() > maximumPopulationSize) {
						eventsSimulated++;
						population.epidemic();
					}
				} else if (simulationMode == SILENTMODE) {
					if (event.type() == event.MUTATION) {
						mutationEvent();
					} else if (event.type() == event.REPRODUCTION) {
						reproductionEvent();
					} else if (event.type() == event.DEATH) {
						deathEvent();
					}

					if (population.size() > maximumPopulationSize) {
						eventsSimulated++;
						population.epidemic();
					}
				}
			}
			event = eventQueue.next();
		}
		printBestPath();
	}
}
