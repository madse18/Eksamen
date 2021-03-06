import java.util.*;

public class Simulator {

	//Initialises the numbers for simulation modes.
    private static final int tUnits = 1,
            nEvents = 2,
            verbose = 3,
            silent = 4;

    //Initialises variables for all the inputs and a variable for events simulated.
    private static int iPopSize,
            maxPopSize,
            mutInterval,
            repInterval,
            deathInterval,
            intervalObs,
            timeSimSize,
            simMode,
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
    private static void inputMenu(){
    	System.out.println("Enter the following information: ");
    	System.out.println("Size of the initial population: ");
    	iPopSize = input.nextInt();
    	System.out.println("Maximum size of the population: ");
    	maxPopSize = input.nextInt();
    	System.out.println("Value of the parameter Omega: ");
    	omega = input.nextDouble();
    	System.out.println("Value of the parameter Death: ");
    	deathInterval = input.nextInt();
    	System.out.println("Value of the parameter Mutation: ");
    	mutInterval = input.nextInt();
    	System.out.println("Value of the parameter Reproduction: ");
    	repInterval = input.nextInt();
    	System.out.println("Time for the simulation to run: ");
    	timeSimSize = input.nextInt();
    	System.out.println("Simulation mode: 1, every t units; 2, every n event; 3, verbose; 4, silent");
    	simMode = input.nextInt();
    	if (simMode == 1 || simMode == 2) {
    		System.out.println("Enter the time between observations: ");
    		intervalObs = input.nextInt();
    	}
    }

    /*
     * Simulates the population.
     * Prints out current time, events simulated, population size, best city path per interval in time.
     */
    private static void timeMode() {
		int newInterval = intervalObs;
		event = eventQueue.next();
		while (event.time() <= timeSimSize) {
			if (eventQueue.hasNext() && population.contains(event.individual())) {
		        if (event.type() == 'm') {
                        mutationEvent();
                }
                else if (event.type() == 'r') {
                        reproductionEvent();
                }
                else if (event.type() == 'd') {
                        deathEvent();
                }
                if (population.size() > maxPopSize) {
                	eventsSimulated++;
                    population.epidemic();
                }

                if (event.time() >= newInterval) {
                    newInterval = intervalObs + newInterval;
                    System.out.println("Current time: " + event.time());
                    System.out.println("Events simulated: " + eventsSimulated);
                    System.out.println("Population size: " + population.size());
                    printBestPath();
                    System.out.println();
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
		int newInterval = intervalObs;
  		event = eventQueue.next();
		while(event.time() <= timeSimSize) {
    		if (eventQueue.hasNext() && population.contains(event.individual())) {
    			if (event.type() == 'm') { 
        				mutationEvent();
        		}	
      			else if (event.type() == 'r') {
          				reproductionEvent();
          		}
      			else if (event.type() == 'd') {
          				deathEvent();
      			}
      			if (population.size() > maxPopSize) {
      				eventsSimulated++;
        			population.epidemic();
      			}

    			if (eventsSimulated >= newInterval) {
					newInterval = intervalObs + newInterval;
        			System.out.println("Current time: " + event.time());
        			System.out.println("Events simulated: " + eventsSimulated);
        			System.out.println("Population size: " + population.size());
        			printBestPath();
        			System.out.println();
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
        while (event.time() <= timeSimSize) {
            if (eventQueue.hasNext() && population.contains(event.individual())) {
                if (event.type() == 'm') {
                        System.out.println(event);
                        mutationEvent();
                } 
                else if (event.type() == 'r') {
                        System.out.println(event);
                        reproductionEvent();
                } 
                else if (event.type() == 'd') {
                        System.out.println(event);
                        deathEvent();
                }
            }
            event = eventQueue.next();

            if (population.size() > maxPopSize) {
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

        while (event.time() <= timeSimSize) {
            if (eventQueue.hasNext() && population.contains(event.individual())) {
                if (event.type() == 'm'){
                    mutationEvent();
                }
                else if (event.type() == 'r') {
                    reproductionEvent();
                }
                else if (event.type() == 'd') {
                    deathEvent();
                }
                event = eventQueue.next();

                if (population.size() > maxPopSize) {
                	eventsSimulated++;
                    population.epidemic();
                }
            }
        }
        printBestPath();
    }

    /*
     * Returns the list of cities.
     */
    private static City[] listOfCities() {
        cities = CityGenerator.generate();

        return cities;
    }

    /*
     * Add individuals to the population and adds mutation, reproduction and death events affected by the new individual to the event queue.
     */
    public static void addIndividuals(EventQueue eventQueue, Population population) {
        Individual individual = new Individual(cities);
        population.add(individual);

        double fitCal = (1 - (Math.log(population.fitness(individual))));
        double mut = RandomUtils.getRandomTime(fitCal * mutInterval);

        double rep = RandomUtils.getRandomTime(fitCal * iPopSize / maxPopSize * repInterval);
        double dead = RandomUtils.getRandomTime(1 - Math.log(1 - population.fitness(individual))) * deathInterval;

        Event eventMutation = new Event('m', mut, individual);
        Event eventReproduction = new Event('r', rep, individual);
        Event eventDeath = new Event('d', dead, individual);


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
        double mut = event.time() + RandomUtils.getRandomTime((1 - Math.log(population.fitness(event.individual())) * mutInterval));
        double mut2 = event.time() + RandomUtils.getRandomTime((1 - Math.log(population.fitness(event.individual())) * mutInterval / 10.0));

        Event eventMutation = new Event('m', mut, event.individual());
        Event eventMutation2 = new Event('m', mut2, event.individual());

        if (RandomUtils.getRandomEvent(Math.pow(1 - population.fitness(event.individual()), 2))) {
            event.individual().mutate();

            hasMutated = true;

            if (RandomUtils.getRandomEvent(0.3)) {
				eventsSimulated++;
                event.individual().mutate();

                if (RandomUtils.getRandomEvent(0.15)) {
                	eventsSimulated++;
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

        double fitCal = 1 - Math.log(population.fitness(event.individual()));
        double rep = event.time() + RandomUtils.getRandomTime(fitCal * iPopSize / maxPopSize * repInterval);

        Event eventReproduction = new Event('r', rep, event.individual());

        fitCal = 1 - Math.log(population.fitness(reproducedIndividual));
        double mut = event.time() + RandomUtils.getRandomTime(fitCal * mutInterval);
        rep = event.time() + RandomUtils.getRandomTime(fitCal * iPopSize / maxPopSize * repInterval);
        double dead = event.time() + RandomUtils.getRandomTime(fitCal * deathInterval);

        Event newEventMutation = new Event('m', mut, reproducedIndividual);
        Event newEventReproduction = new Event('r', rep, reproducedIndividual);
        Event newEventDeath = new Event('d', dead, reproducedIndividual);

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
        } 
        else {

            Event newEventDeath = new Event('d', event.time() + (1 - Math.log(1 - population.fitness(event.individual())) * deathInterval), event.individual());
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

                double deltaX = (population.bestPath()[i + 1].x() - population.bestPath()[i].x());
                double deltaY = (population.bestPath()[i + 1].y() - population.bestPath()[i].y());

                euclideanDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

                total = total + euclideanDistance;
            }	else if (i < (population.bestPath().length)) {

                double deltaX = (population.bestPath()[0].x() - population.bestPath()[i].x());
                double deltaY = (population.bestPath()[0].y() - population.bestPath()[i].y());

                euclideanDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

                total = total + euclideanDistance;
            } 
        }
        System.out.println(" (cost: " + total + ")");
    }


    public static void main(String[] args) {
        inputMenu();
        listOfCities();
        for (int i = 0; i < iPopSize; i++) {
            addIndividuals(eventQueue, population);
        }
        switch(simMode){
            case tUnits: timeMode(); break;

            case nEvents: nEventsMode(); break;

            case verbose: verboseMode(); break;

            case silent: silentMode(); break;

            default: System.out.println("Input to sim mode is invalid.");
        }
    }
}