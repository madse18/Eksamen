
import java.util.*;

public class Simulator {

    //Initializes final variables used for the inputMenu method.
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
            SIM_MODE = 1,
            INTERVAL_OBS = 3;

    private static double COM_NORMAL = 0.001,
            T_SIM_SIZE = 10.0;

    private static int eventsSimulated = 0;

    //Initializes scanner which allows the user to interact with the program.
    private static Scanner input = new Scanner(System.in);

    //Initializes EventQueue, Population and City[] instances.
    private static EventQueue eventQueue = new EventQueue();
    private static Population population = new Population(COM_NORMAL);
    private static City[] cities;
    private static boolean run = true;
    private static Event event;

    public static void main(String[] args) {
        textMenu();
        inputMenu();
        listOfCities();
        for (int i = 0; i < I_POP_SIZE; i++) {
        	individuals(eventQueue, population);
        }
        if (SIM_MODE == 1) {
        	timeMode();
		}
        else if (SIM_MODE == 2) {
        	nEventsMode();
		}
        else if (SIM_MODE == 3) {
        	verboseMode();
        }
        else if (SIM_MODE == 4) {
        	silentMode();
		}
    }

    /*
    * Simulates the population.
    * Prints out current time, events simulated, population size, best city path per interval in time.
    */
    private static void timeMode() {
		int newInterval = INTERVAL_OBS;
		event = eventQueue.next();
		while (event.time() <= T_SIM_SIZE) {
			if (eventQueue.hasNext() && population.contains(event.individual())) {
		        if (event.type() == 'm') 
                        mutationEvent();

                else if (event.type() == 'r') 
                        reproductionEvent();

                else if (event.type() == 'd')
                        deathEvent();
                
                if (population.size() > MAX_POP_SIZE) {
                	eventsSimulated++;
                    population.epidemic();
                }

                if (event.time() >= newInterval) {
                    newInterval = INTERVAL_OBS + newInterval;
                    System.out.println("Current time: " + event.time());
                    System.out.println("Events simulated: " + eventsSimulated);
                    System.out.println("Population size: " + population.size());
                    printBestPath();
                }
            }
            event = eventQueue.next();
        }
        printBestPath();
    }

    /*
    * Simulates the population.
    * Prints out current time, events simulated, population size, best city path per interval in number of events simulated.
    */
	private static void nEventsMode() {
		int newInterval = INTERVAL_OBS;
  		event = eventQueue.next();
		while(event.time() <= T_SIM_SIZE) {
    		if (eventQueue.hasNext() && population.contains(event.individual())) {
    			if (event.type() == 'm') 
        				mutationEvent();
        			
      			 else if (event.type() == 'r') 
          				reproductionEvent();
          		
      			 else if (event.type() == 'd') 
          				deathEvent();
      			 
      			if (population.size() > MAX_POP_SIZE) {
      				eventsSimulated++;
        			population.epidemic();
      			}

    			if (eventsSimulated >= newInterval) {
					newInterval = INTERVAL_OBS + newInterval;
        			System.out.println("Current time: " + event.time());
        			System.out.println("Events simulated: " + eventsSimulated);
        			System.out.println("Population size: " + population.size());
        			printBestPath();
        		}
			}
			event = eventQueue.next();
		}	
		printBestPath();
	}

    /*
    * Simulates the population.
    * Prints out every simulated events and in the end the best city path.
    */
    private static void verboseMode() {
        event = eventQueue.next();

        while (event.time() <= T_SIM_SIZE) {
            if (eventQueue.hasNext() && population.contains(event.individual())) {
                if (event.type() == 'm') {
                        System.out.println(event);
                        mutationEvent();
                } else if (event.type() == 'r') {
                        System.out.println(event);
                        reproductionEvent();
                } else if (event.type() == 'd') {
                        System.out.println(event);
                        deathEvent();
                }
            }
            event = eventQueue.next();

            if (population.size() > MAX_POP_SIZE) {
            	eventsSimulated++;
                population.epidemic();
            }
        }
        printBestPath();
    }

    /*
    * Simulates the population. Prints best city path when simulation ends.
    */
    private static void silentMode() {
        event = eventQueue.next();

        while (event.time() <= T_SIM_SIZE) {
            if (eventQueue.hasNext() && population.contains(event.individual())) {
                if (event.type() == 'm') 
                        mutationEvent();
                else if (event.type() == 'r') 
                        reproductionEvent();
                else if (event.type() == 'd') 
                        deathEvent();
                
                event = eventQueue.next();
                if (population.size() > MAX_POP_SIZE) {
                	eventsSimulated++;
                    population.epidemic();
                }
                
            }
        }
        printBestPath();
    }

    /*
	 * Runs the mutation event.
     */
    private static void mutationEvent() {
        eventsSimulated++;
        boolean hasMutated = false;
        double mut = event.time() + RandomUtils.getRandomTime((1 - Math.log(population.fitness(event.individual())) * MUT_INTERVAL));
        double mut2 = event.time() + RandomUtils.getRandomTime((1 - Math.log(population.fitness(event.individual())) * MUT_INTERVAL / 10.0));

        Event eventMutation = new Event('m', mut, event.individual());
        Event eventMutation2 = new Event('m', mut2, event.individual());

        if (debug) {
            System.out.println("Individual fitness: " + population.fitness(event.individual()));

            System.out.println(Math.pow(1 - population.fitness(event.individual()), 2));
        }
        if (RandomUtils.getRandomEvent(Math.pow(1 - population.fitness(event.individual()), 2))) {
            event.individual().mutate();
            if (debug) {
                System.out.println("Mutation 1");
                System.out.println(event.individual().cost());
            }
            hasMutated = true;

            if (RandomUtils.getRandomEvent(0.3)) {
				eventsSimulated++;
                event.individual().mutate();
                if (debug) {
                    System.out.println("Mutation 2");
                    System.out.println(event.individual().cost());
                }

                if (RandomUtils.getRandomEvent(0.15)) {
                	eventsSimulated++;
                    event.individual().mutate();
                    if (debug) {
                        System.out.println("Mutation 3");
                        System.out.println(event.individual().cost());
                    }
                }
            }
        } else if (debug) {
            System.out.println("Individual did not mutate");
        }

        if (hasMutated) {
            eventQueue.add(eventMutation);
            
        } else {
            eventQueue.add(eventMutation2);
            
        }
    }

    /*
	 * Runs the reproduction event.
     */
    private static void reproductionEvent() {
        eventsSimulated++;
        Individual reproducedIndividual = event.individual().reproduce();

        double fitCal = 1 - Math.log(population.fitness(event.individual()));

        double rep = event.time() + RandomUtils.getRandomTime(fitCal * I_POP_SIZE / MAX_POP_SIZE * REP_INTERVAL);

        Event eventReproduction = new Event('r', rep, event.individual());

        fitCal = 1 - Math.log(population.fitness(reproducedIndividual));
        double mut = event.time() + RandomUtils.getRandomTime(fitCal * MUT_INTERVAL);
        rep = event.time() + RandomUtils.getRandomTime(fitCal * I_POP_SIZE / MAX_POP_SIZE * REP_INTERVAL);
        double dead = event.time() + RandomUtils.getRandomTime(fitCal * D_INTERVAL);

        Event newEventMutation = new Event('m', mut, reproducedIndividual);
        Event newEventReproduction = new Event('r', rep, reproducedIndividual);
        Event newEventDeath = new Event('d', dead, reproducedIndividual);

        eventQueue.add(eventReproduction);
        eventQueue.add(newEventDeath);
        eventQueue.add(newEventReproduction);
        eventQueue.add(newEventMutation);


        population.add(reproducedIndividual);
        if (debug) {
            System.out.println("Individual reproduced fitness: " + fitCal);
            System.out.println("New individual: " + reproducedIndividual);
        }
    }

    /*
	 * Runs the death event.
     */
    private static void deathEvent() {
        eventsSimulated++;
        if (RandomUtils.getRandomEvent(1 - Math.pow(population.fitness(event.individual()), 2))) {
            if (debug) {
                System.out.println("Individual dies");
            }

            population.remove(event.individual());
        } else {
            if (debug) {
                System.out.println("Individual did not die");
            }

            Event newEventDeath = new Event('d', event.time() + (1 - Math.log(1 - population.fitness(event.individual())) * D_INTERVAL), event.individual());
            eventQueue.add(newEventDeath);
            
        }
    }

    /*
	 * Prints out a text menu for the user.
     */
    private static void textMenu() {
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
        System.out.println(COMFORT_NORMALIZATION + ". Comfort normalization: " + COM_NORMAL);
        System.out.println(TOTAL_SIMULATION_TIME + ". Total simulation time: " + T_SIM_SIZE);
        System.out.println(SIMULATION_MODE + ". Simulation mode: " + SIM_MODE);
        System.out.println("_____________________________________________________________");
        System.out.println();
        System.out.println(START_PROGRAM + ". Start program");
        System.out.println(EXIT_PROGRAM + ". Quit program");
        System.out.println("_____________________________________________________________");
        System.out.println();
    }

    /*
	 * Allows the user to interact with the program.
	 * The range of the input is 0 - 9.
     */
    private static void inputMenu() {
        int userInput;
        System.out.println();
        do {
            System.out.println("Select the options here: ");
            userInput = input.nextInt();
            switch (userInput) {
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
                    COM_NORMAL = input.nextDouble();
                    System.out.println("Comfort normalization is " + COM_NORMAL);
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
                    if (debug) {
                        System.out.println("Starting program");
                    }
                    break;
                case EXIT_PROGRAM:
                    if (debug) {
                        System.out.println("Exiting program");
                    }
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
                    System.out.println();
                    break;
            }
        } while (userInput != START_PROGRAM);
    }

    /*
	* Returns the list of cities.
    */
    private static City[] listOfCities() {
        cities = CityGenerator.generate();

        return cities;
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

                double deltaX = (population.bestPath()[i + 1].x() - population.bestPath()[i].x());
                double deltaY = (population.bestPath()[i + 1].y() - population.bestPath()[i].y());

                euclideanDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

                if (debug) {
                    System.out.println();
                    System.out.println("Distance between " + population.bestPath()[i].name() + " and " + population.bestPath()[i + 1].name() + " is: " + euclideanDistance);
                }

                total = total + euclideanDistance;
            }	else if (i < (population.bestPath().length)) {

                double deltaX = (population.bestPath()[0].x() - population.bestPath()[i].x());
                double deltaY = (population.bestPath()[0].y() - population.bestPath()[i].y());

                euclideanDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

                if (debug) {
                    System.out.println();
                    System.out.println("Distance between " + population.bestPath()[i].name() + " and " + population.bestPath()[0].name() + " is: " + euclideanDistance);
                }

                total = total + euclideanDistance;
            } else if (debug) {
                System.out.println("The system is done calculating.");
            }
        }
        System.out.println(" (cost: " + total + ")");
    }

    /*
     * Creates individuals to the population and adds mutation, reproduction and death events affected by the new individual to the event queue.
     */
    public static void individuals(EventQueue eventQueue, Population population) {
        Individual individual = new Individual(cities);
        population.add(individual);

        double fit = (1 - (Math.log(population.fitness(individual))));
        double mut = RandomUtils.getRandomTime(fit * MUT_INTERVAL);

        double rep = RandomUtils.getRandomTime(fit * I_POP_SIZE / MAX_POP_SIZE * REP_INTERVAL);
        double dead = RandomUtils.getRandomTime(1 - Math.log(1 - population.fitness(individual))) * D_INTERVAL;

        Event eventMutation = new Event('m', mut, individual);
        Event eventReproduction = new Event('r', rep, individual);
        Event eventDeath = new Event('d', dead, individual);


        eventQueue.add(eventMutation);
        eventQueue.add(eventReproduction);
        eventQueue.add(eventDeath);
    }
}