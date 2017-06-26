# Planning

These notes come from the book [Artificial Intelligence - A Modern Approach 3rd edition](http://aima.cs.berkeley.edu/) by Russel and Norvig (except where otherwise noted).

## PDDL

- Planning Domain Defintion Language
- Derived from the STRIPS planning language
- States
  - A state of the world is represented by a collection of variables
  - PDDL state examples
    - Poor ∧ Unknown
    - At(Truck1, Melbourne) ∧ At(Truck2, Sydney)
  - Closed-world assumption
    - Any fluents not mentioned are false
  - Unique names assumption
    - Truck1 and Truck2 are distinct
  - Disallowed in states
    - Non-ground fluents
    - Negations
    - Function symbols
  - "States can be treated either as a conjunction of fluents, which can be manipulated by logical inference, or as a set of fluents, which can be manipulated with set operations."

## GRAPHPLAN

Algorithm:

```
function GRAPHPLAN(problem) returns solution or failure
  graph = INITIAL-PLANNING-GRAPH(problem)
  goals = CONJUCTS(problem.GOAL)
  nogoods = empty hash table
  for tl = 0 to inf do:
    if goals all non-mutex in St of graph then:
      solution = EXTRACT-SOLUTION(graph, goals, NUMLEVELS(graph), nogoods)
      if solution != failure then return solution
   if graph and nogoods have both leveled off then return failure
   graph = EXPAND-GRAPH(graph, problem)
```





https://github.com/jdsutton/AIOverview/tree/master/Planning