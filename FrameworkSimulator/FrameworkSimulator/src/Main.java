import Operator.*;
import Task.*;
import Thing.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {
	
	/**
     * Round to certain number of decimals
     * 
     * @param decimal
     * @param decimalPlace
     * @return decimalRounded
     */
	
	public static float round(float decimal, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(decimal));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
	
	/**
     * Computation of random values.
     * 
     * Simulation of operators, theirs contexts, and tasks for the framework proposed in 
     * <i>Modelling trust dynamics in the Internet of Things</i> by Fernandez-Gago et al. 
     * 
     * @return Operators, OperatorContexts, Tasks
     * 
     * @author Anna Guinet
     * 
     */
	
	public static void main(String[] args) {
		
		//Output in a text file
        String filename = "SimulationIoT.txt";
        File file = new File("C:\\Users\\Anna\\Documents\\Framework\\" + filename);
        try {
            PrintStream printStream = new PrintStream(file);
            System.setOut(printStream);
            System.out.println("Simulation: Modelling trust dynamics in the Internet of Things");
            System.out.println("\n");
       	
            /**
             * Generation of random operators
             * 
             */
            
            Operator operatorRandom;
		
            //Number of operators generated: 10
            for (int i = 0 ; i < 10 ; i++){
            	
            	//To print operators and operatorContexts
    			StringBuilder printOperators = new StringBuilder();
    			StringBuilder printContexts = new StringBuilder();
	    	
            	//OperatorID: 0 to 80
            	SecureRandom randomOperatorID = new SecureRandom();
            	int operatorID = randomOperatorID.nextInt(801);  	
	       	
            	//Number of worked hours: 0 to 35
            	SecureRandom randomWork = new SecureRandom();
            	int nbWorkedHours = randomWork.nextInt(36);
	    	
            	//Number of tasks completed: 0 to 15
            	SecureRandom randomTask = new SecureRandom();
            	int nbTasksCompleted = randomTask.nextInt(16);
	    	
            	//Global Reputation: 0.000 to 1.000
            	SecureRandom randomGlobalReputation = new SecureRandom();
            	float globalReputation = randomGlobalReputation.nextFloat();
            	globalReputation = round(globalReputation, 3);
	    	
            	//Location[x,y]
            	SecureRandom randomLocation = new SecureRandom();
            	float latitude = randomLocation.nextFloat();
            	float longitude = randomLocation.nextFloat();
            	float[] locationOperator = new float[2];
            	locationOperator[0] = latitude;
            	locationOperator[1] = longitude;
	    	
            	//Years of experience: 0 to 40
            	SecureRandom randomYear = new SecureRandom();
	    		int yearsExperience = randomYear.nextInt(41);
	    	
	    		//Task Assignation: True or False
	    		SecureRandom randomAssign = new SecureRandom();
	    		boolean taskAssignation = randomAssign.nextBoolean();
	    	
	     		    			
	    		/**
	    		 * Generation of random contexts for an operator.
	    		 * 
	    		 * First, generation of the things owned.
	    		 * Second, generation of a context by computing a set of things.
	    		 * Third, comparison with previous contexts.
	    		 * 
	    		 */
	    		
	    		List<Thing> operatorThings = new ArrayList<Thing>();
	    		Thing thingRandom;
	    		
	    		/**
		         * Generation of things owned by an operator
		         * without the petrol level for a car
		         * 
		         */
			
	    		//Number of things generated: 4
	    		//Car, PDA, Smartglass, Smartwatch
	    		for (int j = 0 ; j < 4 ; j++){
			    	
	    			//thingID: 0 to 3200
				 	SecureRandom randomThingID = new SecureRandom();
			       	int thingID = randomThingID.nextInt(32001);  	
			       	
			       	//LastSupervision: 01/01/2017 to 31/12/2017 
			       	long minDayThing = LocalDate.of(2017, 1, 1).toEpochDay();
			        long maxDayThing = LocalDate.of(2017, 12, 31).toEpochDay();
			        long randomDayThing = ThreadLocalRandom.current().nextLong(minDayThing, maxDayThing);
			        LocalDate lastSupervision = LocalDate.ofEpochDay(randomDayThing);	
			        
			        //NbPastDefaillances: 0 to 30
				 	SecureRandom randomDefaillances = new SecureRandom();
			       	int nbPastDefaillances = randomDefaillances.nextInt(31);
			       	
			       	//SecurityCertification: True or False
				 	SecureRandom randomCertification = new SecureRandom();
			       	boolean securityCertification = randomCertification.nextBoolean();
			    	
			       	thingRandom = new Thing(thingID, lastSupervision, nbPastDefaillances, securityCertification);
			    	operatorThings.add(thingRandom);
			    	
			    	
	    		}
			
	    		/**
	    		 * Generation of operator contexts for an operator
	    		 * 
	    		 */
			 
	    		List<OperatorContext> operatorContexts = new ArrayList<OperatorContext>();
	    		List<Thing> setOfThings = new ArrayList<Thing>();
	    		OperatorContext operatorContextRandom;
				
	    		// Number of contexts generated: 1 to 15
	    		// 4 things chosen, at least 1 carried by the operator
	    		// 15 = (4 choose 1) + (4 choose 2) + (4 choose 3) + (4 choose 4)
	    		SecureRandom randomNbContexts = new SecureRandom();
	    		int maxNbContexts = randomNbContexts.nextInt(15) + 1;
			
	    		//Parameters for an operator context
	    		int contextID = 0;
	    		LocalDate timeContext = null;
	    		
	    		/** 
				 * Generation of a set of things for each context, 
				 * depending on the previous 4 things generated 
				 * (owned by the operator).
				 * 
				 */
			 
	    		for (contextID = 0 ; contextID < maxNbContexts ; contextID++){ 
			        
			        //Setting setOfThings with null for one context
			        for (int k = 0 ; k < 4 ; k++){
			        	setOfThings.add(null);
			        }
			        
			        //Random value: 0 to 3
			        //Choose the first thing
			        SecureRandom random = new SecureRandom();
					int firstThing = random.nextInt(4);
					
					//Add the first thing in setOfThings
					//setOfThings has at least one thing
					setOfThings.set((firstThing + 4*contextID), operatorThings.get(firstThing));
					
					//Boolean for stopping the computation
					boolean stop = false;
					
					while (stop == false){
					
						//Random value: 0 to 4
						//Next possibilities: add a new thing (0 to 3) or stop (4)
						SecureRandom random2 = new SecureRandom();
						int nextPossibility = random2.nextInt(5);
						
						//Don't choose the first thing again
						if (firstThing != nextPossibility){
							
							if (nextPossibility == 4){
								stop = true;
							}
							else{
								
								//Don't choose the same thing outside the first thing
								//in the subList from 4*j to 4*j+3
								if (setOfThings.subList((4*contextID), (4*contextID + 4)).get(nextPossibility) == null ){									
										setOfThings.set((nextPossibility + 4*contextID), operatorThings.get(nextPossibility));
										
									//Don't add another time if all the things are already chosen
									//in the subList from 4*j to 4*j+3
									if(setOfThings.subList((4*contextID), (4*contextID + 4)).containsAll(operatorThings)){
										stop = true;
									}
								}
							}
						}
					}
						
					/** 
					 * Comparison of the context generated with the previous ones.
					 * 
					 */
						
					if(contextID > 0) { //Comparison needs at least 2 setOfThings
							
						//Creation of a sublist of 4 size: selection of the last set of things
						List<Thing> lastThingsList = new ArrayList<Thing>();
						lastThingsList = setOfThings.subList(4*contextID, 4*contextID + 4);
							
						//Creation of a set with unique values from the previous list
						Set<Thing> lastThingsSet = new HashSet<Thing>(lastThingsList);
						 
						//Creation of a new ArrayList from the previous set
						List<Thing> lastThingsListUnique = new ArrayList<Thing>(lastThingsSet);
							
						//Comparison with the previous sets of things
						for(int l = 0 ; l < contextID ; l++){
										
							//Creation of a sublist of 4 size: selection of a previous set of things
							List<Thing> previousThingsList = new ArrayList<Thing>();
							previousThingsList = setOfThings.subList(4*l, 4*l + 4);
										
							//Creation of a set with unique values from the previous list
							Set<Thing> previousThingsSet = new HashSet<Thing>(previousThingsList);
									 
							//Creation of a new ArrayList from the previous set
							List<Thing> previousThingsListUnique = new ArrayList<Thing>(previousThingsSet);
									
							//Compare if the selected set of things equals to another in the list
							if(lastThingsListUnique.equals(previousThingsListUnique)){
								
								//Delete the duplicated set of things
								//Regeneration of a set of things for the same contextID
								setOfThings.remove(4*contextID + 3);
								setOfThings.remove(4*contextID + 2);
								setOfThings.remove(4*contextID + 1);
								setOfThings.remove(4*contextID);
								contextID -= 1;
								break;
							}
							else{
								
								//Generation timeContext if unique set of things
						       	//TimeContext: 2017-01-01 to 2017-12-01 
						       	long minDayContext = LocalDate.of(2017, 1, 1).toEpochDay();
						        long maxDayContext = LocalDate.of(2017, 12, 31).toEpochDay();
						        long randomDayContext = ThreadLocalRandom.current().nextLong(minDayContext, maxDayContext);
						        timeContext = LocalDate.ofEpochDay(randomDayContext);	
							}
						}		
					} 
					else{
						
						//Generation timeContext if contextID = 0
					   	//TimeContext: 2017-01-01 to 2017-12-01 
				       	long minDayContext = LocalDate.of(2017, 1, 1).toEpochDay();
				        long maxDayContext = LocalDate.of(2017, 12, 31).toEpochDay();
				        long randomDayContext = ThreadLocalRandom.current().nextLong(minDayContext, maxDayContext);
				        timeContext = LocalDate.ofEpochDay(randomDayContext);	
					}
					
					//Add the operatorContext to the list of contexts
			        operatorContextRandom = new OperatorContext(contextID, timeContext, setOfThings);
					operatorContexts.add(operatorContextRandom);
					
					//Delete if new context equals to another in the list
					//Because no incrementation of contextID
					if(operatorContexts.size() > (contextID + 1)){
						operatorContexts.remove(operatorContexts.size() - 1);
					} 
					else{ 
						
				        //To print the contexts of an operator
			        	printContexts.append("Context ID: " + operatorContextRandom.getContextID());
			        	printContexts.append(", time: " + operatorContextRandom.getTimeContext());
			        	printContexts.append(", set of things: " + operatorContextRandom.getSetOfThings().subList((4*contextID), (4*contextID+4)));
			        	printContexts.append(System.getProperty("line.separator")); 
			        	
					}
	    		}
					
	    		//Creation of an operator with his/her contexts
		    	operatorRandom = new Operator(operatorID, "John", "Doe", "Technician", nbWorkedHours, nbTasksCompleted, globalReputation,
		    			locationOperator, yearsExperience, taskAssignation, maxNbContexts, operatorContexts);
		    	
				//Information about the operator
				printOperators.append("Operator ID: " + operatorRandom.getOperatorID());
				printOperators.append(", worked hours: " + operatorRandom.getNbWorkedHours());
				printOperators.append(", tasks completed: " + operatorRandom.getNbTasksCompleted());
				printOperators.append(", years of experience: " + operatorRandom.getYearsExperience());
				printOperators.append(", global reputation: " + operatorRandom.getGlobalReputation());
				printOperators.append(", location: " + operatorRandom.getLocationOperator());
				printOperators.append(", task assigned: " + operatorRandom.isTaskAssignation()); 
				
				//Print the operator and his/her contexts
				System.out.println(printOperators);
				System.out.println(printContexts + "\n");
            }
	    	    
            /**
             * Generation of random tasks
             * 
             */
            Task taskRandom;
		
            //Number of tasks generated: 10
            for (int taskID = 0 ; taskID < 10 ; taskID++){
            	
            	//To print tasks 
    			StringBuilder printTasks = new StringBuilder();
		    	
		    	//Duration (hours): 0 to 7
			 	SecureRandom randomDuration = new SecureRandom();
		       	float estimatedDuration = randomDuration.nextInt(8);  	
		       	
		       	//Complexity: 0.00 to 1.00
		       	SecureRandom randomComplexity = new SecureRandom();
		    	float estimatedComplexity = randomComplexity.nextFloat();
		    	estimatedComplexity = round(estimatedComplexity, 2);
		    	
		    	//Criticality: 1.0 to 5.0
		    	SecureRandom randomCriticality = new SecureRandom();
		    	int criticality = randomCriticality.nextInt(5) + 1;
	    	
		    	//Location[x,y]
		    	SecureRandom randomLocation = new SecureRandom();
		    	float latitude = randomLocation.nextFloat();
		    	float longitude = randomLocation.nextFloat();
		    	float[] locationTask = new float[2];
		    	locationTask[0] = latitude;
		    	locationTask[1] = longitude;
		    	
			   	//TimeTask: 2017-01-01 to 2017-12-01 
		       	long minDayTask = LocalDate.of(2017, 1, 1).toEpochDay();
		        long maxDayTask = LocalDate.of(2017, 12, 31).toEpochDay();
		        long randomDayTask = ThreadLocalRandom.current().nextLong(minDayTask, maxDayTask);
		        LocalDate timeTask = LocalDate.ofEpochDay(randomDayTask);	
		    	
		    	//Creation of task without preferredProfile
		    	taskRandom = new Task(estimatedDuration, estimatedComplexity, criticality, "none", locationTask, timeTask);
		    	
		    	//Information about the task
		    	printTasks.append("Task: " + taskID);
		    	printTasks.append(", estimated duration: " + taskRandom.getEstimatedDuration());
		    	printTasks.append(", estimated complexity: " + taskRandom.getEstimatedComplexity());
		    	printTasks.append(", criticality: " + taskRandom.getCriticality());
		    	printTasks.append(", timeTask: " + taskRandom.getTimeTask());
		    	printTasks.append(", location: " + taskRandom.getLocationTask());
		    	
		    	//Print the tasks
				System.out.println(printTasks);
		    	
		    }
	    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
