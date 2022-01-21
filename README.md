# Santa-s-Helper
# Zanficu Madalina 323CA
# Project OOP – Stage 1 && 2

## Structure:
The simulation was implemented using the following packages:
1. **input files** contains 7 classes:

* ChildInput - models the main fields of a child given though input:
             id, lastName, firstName, age, city, niceScore, gift preferences,
             niceScoreBonus and elf.
   	      
* ChildUpdate - implements a list of giftPreferences and other attributes 
        that represents yearly updates attributes for every child
        given though input.
		
* Gift - contains essential information about a present such as:
         productName, price, category and quantity.
  
* InitialData - implements list for the children that potentially receive gifts,
                 and list of available Santa's presents.
		
* InputAnnualChange - contains arguments and lists given though input
                     used for an annual update over subjects, such as:
                     new santa budget, new gifts available, new children,
                     specific updates for children and the strategy meant
                     to implement in order to distribute gifts.
		      
* InputData - encapsulates all the information given though input
* InputLoader - responsible for reading the data from the input files
          - used Jackson library in order to read the JSON format
            and map the fields of the InputData class to the ones from JSON file.


2. **output files**

* ChildOutput - models the main fields of a child and extra fields added with
		 the flow of the simulation such as: a history of nice scores,
		 the yearly budget assigned and the received gifts
* AnnualChange

* ChildrenOutputFormat - implements a list of children that received gifts
			  in the current year, the list is override for every
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


3. **main**
* Main - responsible for creating the link between:
         input files -> tests -> apply actions -> output files and checker call

4. **observer**
* Santa Claus - database created with Singleton pattern and Observer Pattern
	      - implements lists of children, list of available gifts
	        and the attributes santaBudget, budgetUnit and strategy
	      - all the fields required yearly updates
	        => Santa Claus is an observer
	      

* Solver - contains fields that are not changing over the simulation:
           the number of years for the process and the annualChanges
         -  is responsible for solving the round 0 and the other
            numberOfYear rounds
         -  is in charge of applying the updates for the Santa Claus
           Database for every round before simulation is executed

* Update (interface)

5. **strategy**

A. average score
* Strategy Factory - used to instantiate related classes of strategies
             for computing the average score of a child based on his age.
			
* AverageScoreStrategy (interface)
* Baby Strategy
* Kid Strategy
* Teen Strategy

B. sorting
* Sorting Factory - used to instantiate related classes of strategies
                    for sorting the list of children, in order to distribute
                    gifts after multiple criteria

* Sorting Strategy (interface)
* IdStrategy - the sorting criteria is the id of the children

* NiceScoreStrategy - the sorting criteria is the averageScore
                      and the second one is the id.

* NiceScoreCityStrategy - sorting criteria: the averageScore of the city
                        - the children from the city with the biggest averageScore
                          are the one who receive gifts first place.

## Design patterns:
In the process of implementing the project I used 4 suitable designs patterns:

1. Singleton Pattern - used to simulate multiple databases for input and output
		       to maintain the information in one place.
			
The classes where I used this pattern favorite the existence of an only instance,
in order to safety add to/remove from a single place.

2. Factory Pattern - used through StrategyFactory class to create instances of
related classes more exactly: BabyStrategy, KidStrategy and TeenStrategy
from averagescore package and also the IdStrategy, NiceScoreStrategy
and NiceScoreCityStrategy.

3. Observer Pattern - used to map the relationship between the SantaClaus database 
                      which contains information that received updates yearly
                      and the Solver class which maintain the immutable information
                      more exactly the updates that need be applied to SantaClaus.
                    - Before a new round to be solved, the data from Santa need
                      to be updated in order to perform the simulation
                      on specific data given.


4. Strategy Pattern - used to encapsulate algorithms in different classes which
                      offer specific interface to use
                     - the advantage of strategy pattern is represented by the
                      mechanism of dynamic polymorphism of the program to select
                      the strategy needed depending on the situation at runtime
                    - in this scenario I structured the algorithms strategies in
                      the classes: BabyStrategy, KidStrategy and TeenStrategy for
                      computing the averageScore depending on the age pf the children.
                    - As well IdStrategy, NiceScoreStrategy and NiceScoreCityStrategy
                      in order to sort the list of children after the requested criteria
                      given through input.
		
## The flow of the simulation
The main idea is to distribute gifts to eligible children in different
rounds of simulation. Every round of simulation represent a new Christmas
season => the age of every child increase with 1.
An eligible child is a child with the age smaller or equal to 18.
Yearly, the preferences of the children, the children and multiple data
is changing, so before distributing gifts the data need to be updated
(The observer Santa Claus is responsible).

### The first stage of the project:
The distribution of the gifts is fully based on the id.
For every child (taking into consideration the age) the averageScore and budget
are computed. The children are taken in increasing order after the id field,
and the presents are distributed after the preferred category and the budget
of every child.

### The second stage of the project:
The distribution of the gifts could be realized after the id field,
the niceScore of the children and the niceScore of every city.
Before distributing the presents, the list of children need to be sorted
in order to maintain the requested strategy of sharing gifts.
The difference between the first stage consists in the limited quantity
of the gifts, the bonus score (optional) that is added to the averageScore
formula, and also the presents of the elves.
There are 4 types of elves: two of them (PINK and BLACK) could modify
the budget of a child and the YELLOW elf is responsible for sharing gifts
to the children that did not receive gifts before.


