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
