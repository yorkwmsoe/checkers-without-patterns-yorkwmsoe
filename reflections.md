Billy York

SWE 2410-111

1/23/24

# Lab 1 - Checkers without Patterns

## 1: What design aspects in the provided code made your work easier?

  I thought there were several independent functions that made it easy to make one change in a single location and have it carried through everywhere it's required. I would also say the cohesion of the Piece class was high enough such that it was the only class that needed to be changed to implement the "king" functionality.
 
## 2: How do you think the design of this code could be improved to make this process easier?

  There are several instances of "do very similar things" based on an if statement for Types. So a Strategy Pattern for Red and Black pieces implementing a Piece interface is one immediate improvement. There could also be more separation of business logic and rendering logic using an MVC pattern. The Piece class does also have a lot of knowledge of things it doesn't necessarily need to have. 

## 3: What did you learn about design while working on this lab?

  It's easy to take even mediocre design for granted. The if statements for checking if a piece could be captured were very unintuitive. It was also somewhat difficult to track the process of moving a piece through the code, I was essentially fully dependent on IntelliJ's "Go to Implementation" feature to ensure I was looking at the correct functions. 

# Lab 2 Strategy

## How the strategy pattern made the code more maintainable

  By adding an Interface with 3 concrete classes, I was able to abstract the specific behaviors to individual classes and take advantage of polymorphism to reduce the old Piece methods to a single call to the moveBehavior methods. 

## How the strategy pattern made the code less maintainable

  There's now 4 new classes added to the code. 3 of those classes could be considered to be tightly coupled to both the Piece class and BoardController Class, and especially the Type enum within Piece. So the 3 concrete implementations would need to change if the Type enum ever changed to separate normal pieces from kings for example. 

## Things you liked about this lab or suggestions for improving it (required)

  I like how this lab was more open-ended and it was up to us to decide on the exact implementation. I chose an interface with 3 concrete implementations of it. The person next to me did an interface with 4 concrete implementations. The person on the other side of him did an Abstract Clas instead of an interface. All methods are equally viable. The only hangup I really had was not realizing BoardController was a singleton. I do think this series of labs might end up with too many permutations to really be able to keep track as we continue to improve the code with new patterns. I'd be interested in an option so that there's an "official" starting point for each lab that is the instructor's solution. Not that it would rule out anyone else's as "correct" just pruning the possibility tree down the line. 
