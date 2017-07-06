#6. Principles of Data Manipulation and Management
## Data Models, Terminology
- how do we store data?
  - HDDs and SSDs - non-volatile storage
- What is the data model
  - how do we organize the data
    - tree like
    - rows and cols
    - spreadsheets - unstructured
- What is a data model?
  1. structures
  2. constraints
  3. operations
- Structures
  - rows/cols
  - nodes/edges
  - key-values
  - sequence of bytes - files
- Constraints
  - all rows have same number of cols
  - all vals in a col must be the same type
  - child cannot have two parents
- Operations
  - find the value x
  - find the rows where col x has val y
  - get the next N bytes, open/close
- What is a Database
  - collection of info organized to afford efficient retrieval
  - Another view by Jim Gray
    - data should be self-describing and it should have a schema

## From data models to databases
- Why would I want a database?
  - Sharing Data - support and interface for many users
  - Data Model Enforcement
    - ensure all application see clean, organized data
  - Scale
    - databases too large to fit into main memory
  - Flexibility
    - Use the data in new, unanticipated ways
- Questions to consider
  - how is the data physically organized on disk?
  - what kinds of queries are efficiently supported
    - reads vs writes - is the DB organized in a way to support the latency requirements
  - how to update or add new data
  - What happens if I encounter new queries that I didn't anticipate?

## Pre-Relational Databases
- Motivation of Relational Databases
- Historical Example: Network Database
    - file oriented 
    - allows for drill-down queries but not aggregated queries
      - adding fields requires reorganization of DB
    - different queries might need a duplicated but reorganized copy of the structure
- Hierarchical Database
  - IBM IMS system
  - Data organized as segments
  - you can change one segment without re-writing everything but the developer still needs to understand the Hierarchy
  - must anticipate the queries that will be asked

## Motivation Relational Databases
- build to use one set of data in multiple ways, including ways that we unforeseen at the time of creation
  - Everything is a table
  - Every row in the table has the same columns
  - relationships are implicit: no pointers
    - using shared ID
    - lookups of sharedID has worse performance over direct pointers but the time is consistent in either direction
  - everything is stored only once - not achieved with network DBs

## Relational Databases: key Ideas
- pre-relational: reorganization meant rebuilding application
- RDBMS were buggy and slow but required only 5% of the application code
- User/applications remain unaffected when the internal representation of the data is changed
- **Key Idea** Programs that manipulate tabular data exhibit and algebraic structure allowing reasoning and manipulation independently of the physical data representation
- Physical Data independence
  - we now manipulate the logical "table" as apposed to chasing pointers around
  - programs are more robust than without the rational model
- Algebra of Tables
  - Select, Project, join
  - queries written in terms of table operations
  - DB designers need only focus on implementing these operations

#7. Relational Algebra
## Algebraic Optimization Overview
- Algebraic laws (identity, distributive, commutative) can be use to simply expressions turing many operations into several.
- the same sort of optimization can be applied to tables increasing the query efficiency
- It is critical to not do things in the wrong order or un-needed work
- every query is rewritten using algebraic re-write rules
- attempt to minimize the cost of the expression
- conversely, optimizing map reduce is left up to the developer
- Algebraic Closure: every operation that applies to a table also returns a table
    - operations can be chained together

### Operators
- union ∪   (222a)
- intersection ∩ (2229)
- difference - 
- Selection σ (3c3)
- Projection ∏ (220f)
- Join ⋈ (22c8)
  - left outer join ⟕  (27d5)
  - right outer join ⟖  (27d6)
  - full outer join ⟗  (27d7) 
- Extended RA
  - Duplicate elimination d
  - grouping and aggregation g
  - sorting t
- Mainly: set operations + selection, projection, join

# RA Operators
- Sets vs Bags
  - Sets - no duplicates
  - bags - duplicates allowed
- RA has 2 semantics
  - set semantics = standard RA
  - bag semantics = extended RA
- Rule of thumb
  - every paper will assume set semantics
  - every implementation will assume bag semantics

###Union
- R1 ∪ R2

```
SELECT * FROM R1
UNION
SELECT * FROM R2
```
- returns all data from R1 and R2 with duplicates between the 2 tables removed
- duplicates can be included by using ```UNION ALL```

###Difference
- R1 - R2

```
SELECT * FROM R1
EXCEPT
SELECT * FROM R2
```
- returns R1 with R2 values removed
- Intersection can be defined as difference
  - R1 ∩ R2 = R1 - (R1 - R2)
- Or as Join
  - R1 ∩ R2 = R1 ⋈ R2

###Selection
- Returns all tuples which satisfy a condition (c)
  - σ<sub>c</sub>(R)
- i.e.
  - σ<sub>Salary</sub> > 40000 (Employee)
  - σ<sub>Name</sub> = "Smith"(Employee)
- the condition c can be any equal/grater/less than condition, any boolean function, any arbitrary function that returns a boolean

### Project
- eliminates columns
  - Removes all columns that aren't explicitly listed AND removes all duplicates that might remain
- ∏<sub>A1,...,An</sub>(R)
- i.e. project on to SSN and Name out of Employee
  - ∏<sub>SSN, Name</sub>(Employee)
  - result: (SSN, Name)
  - set semantics will remove any duplicate tuples
  - bag semantics will include all duplicate tuples
    - more efficient than set semantics

### Cross Product
- for every combination of tuples in R1 an R2 produce a tuple in the output
- |R1| x |R2| = |R1 x R2|
- Find all pairs of similar image/tweets/songs,
  - compute the cross product, then compute a similarity function f(x1,x2) for every pair
 
### Join
- most common form is Equi-join
- for every record in R1 find corresponding record in R2 that satisfies some condition
- R1 ⋈<sub>A=B</sub> R2 = σ<sub>A=B</sub>(R1 x R2)
- 2 notations in SQL

```
SELECT *
FROM R1, R2
WHERE R1.A = R2.B
```

```
SELECT *
FROM R1 JOIN R2
ON R1.A = R2.B
```

### Outer Join
- Include tuples with no matches in the output
- use NULL values for mission attributes
  - pad out missing values with NULL
- R1 ⟕  R2 (left outer)
  - set of all combinations of tuples in R1 and R2 that are equal on their common attribute names, in addition to tuples in R1 that have no matching tuples in R2 (missing values are NULL padded)
- SQLite not able to do full outer joins

### Theta Join
- A join that involves a predicate
- R1 ⋈<sub>θ</sub> R2 = σ<sub>θ</sub>(R1 x R2)
- θ (3b8) can be any condition
- i.e. 1
  - Find all hospitals within 5 miles of a school
  - ∏<sub>name</sub>(Hospitals ⋈<sub>distance(location, location) < 5 </sub>Schools)
  - assumes user defined function "distance"

```
SELECT DISTINCT h.names
FROM Hospitals h, Schools s
WHERE distance(h.location, s.location) < 5
```

- i.e. 2
  - find all user clicks made within 5 seconds of a page load
  - Clicks ⋈<sub>abs(click_time - load_time) < 5</sub>PageLoads

```
SELECT *
FROM Clicks c, PageLoads p
WHERE abs(c.click_time - p.load_time) < 5
```
- Band join and range joins
  - find tuples from one table within an interval or range defined by another table