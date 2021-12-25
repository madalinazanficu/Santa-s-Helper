# Santa-s-Helper
										Project OOP – Stage 1
										Zanficu Madalina 323CA
										
										
Structure:
The simulation was implemented using the following packages:
1. input files contains 7 classes:

* ChildInput - models the main fields of a child given though input:
   	      id, lastName, firstName, age, city, niceScore, and gift preferences.
   	      
* ChildUpdate - implements a list of giftPreferences and other attributes 
		that respersents yearly updates attributes for every child
		given though input.
		
* Gift - contains essential information about a present such as:
         productName, price and category.
  
* InitialData - implements list for the children that potentially receive gifts,
		 list of available Santa's presents and list for the cities that
		 Santa visit.
		
* InputAnnualChange - contains arguments and lists given though input used for
		      an annual update over subjects, such as new santa budget,
		      new gifts available, new children and specific updates
		      for children.
		      
* InputData - encapsulates all the information given though input
* InputLoader - reponsible for reading the data from the input files
	      - used Jackson library in order to read the JSON format
	        and map the fields of the InputData class to the ones from JSON file.


2. output files

* ChildOutput - models the main fields of a child and extra fields added with
		 the flow of the simulation such as: a history of nice scores,
		 the yearly budget assigned and the received gifts
* AnnualChange

* ChildrenOutputFormat - implements a list of children that received gifts
			  in the current year the list is override for every
			  simulation so the AnnualChildrenFormat maintain all
			  the lists for numberOfYear simulations.
		
* AnnualChildrenFormat

* OutputFormat - database created with singleton pattern, responsible 
		 for modeling the output format required 

* Writer - responsible for mapping the OutputFormat database information
	   which contain the results of the entire simulation to a JSON file. 

Separate the input / output files in different packages in order to respect 
the single responsibility concept for the classes inside the package. 
The ChildInput class has some different arguments from ChildOutput
as the read/write implementation of the program is realized with
Jackson library and the arguments for reading should be different
from the arguments for writing in output files.


3. main
* Main

4. observer
* Santa Claus - database created with Singleton pattern and Observer Pattern
	      - implements lists of children, list of available gifts
	        and the attributes santaBudget and budgetUnit
	      - all the fields required yearly updates
	        => Santa Claus is an observator
	      

* Solver - contains fields that are not changing over the simulation:
	   the number of years for the process and the annualChanges
	 -  is responsible for solving the round 0 and the other
	    numberOfYear rounds
	 -  is in charge of appling the updates for the Santa Claus
	    Database for every round before simulation is executed

* Update (interface)

5. strategy
* Strategy Factory - used to instantiate related classes of strategies
		     for computing the average score of a child based on his age.
			
* AverageScoreStrategy (interface)
* Baby Strategy
* Kid Strategy
* Teen Strategy


Design patterns:
In the process of implementing the project I used 4 suitable designs patterns:

1. Singleton Pattern - used to simulate multiple databases for input and output
		       to maintain the information in one place.
			
The classes where I used this pattern favorize the existance of an only instance,
in order to safetly add to/remove from a single place.

2. Factory Pattern - used through StrategyFactory class to create instances of
related classes more exaclty: BabyStrategy, KidStrategy and TeenStrategy.

3. Observer Pattern - used to map the relationship between the SantaClaus database 
	      	       which contains information that received updates yearly
	      	       and the Solver class which maintain the immutable inforamtion
	      	       more exacty the updates that need be applied to SantaClaus.
	            - Before a new round to be solved, the data from Santa need
	              to be update in order to performe the simulation
	              on specific data given.


4. Strategy Pattern - used to encapsulate algorithms in different classes which
		      offer specific interface to use
		    - the advantage of strategy pattern is reprsented by the
		      mechanism of dynamic polimorfism of the program to select
		      the strategy needed depending of the situation at runtime
		    - in this scenario I structured the algorithms stragies in
		      the classes: BabyStrategy, KidStrategy and TeenStrategy.
		

