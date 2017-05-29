# Supervised Learning - Lesson 1 Video Notes

## Classification

- The process of taking an input (x) and mapping it to a discrete label(s) (true/false)
- For example, trying to map a face to either a "male" or "female" gender
- The key take-away in classification is that for a given input, the output should result in a value that exists in a small, defined, discrete space of possible answer choices


## Regression

- Given a bunch of points on a graph, where does one point "map" to on a linear model line or real number. 
- The key take-away in regression is that for a given input, the output should result in a value that exists in a continuous spectrum or range of possibilities that potentially extends indefinitely


Quiz 1: Define the types of supervised learning in the following examples

1. Input -> Credit History; Output -> Should you lend money?
 Classification (because the answer usually maps to true/false; it's a binary task)

2. Input -> A picture of a kid; Output -> High School, College, or Graduate Student?
 Classification (because there is one and only one single, definitive answer based on the input, in a discrete set of possible values)

3. Input -> A picture of a kid; Output -> Age?
 Regression (because if you quantify age as a fractional value, then the possible age is literally infinite on the numeric scale for all possible real-number values. For example, we could classify two different people as either 3.15003 years old, vs 3.15230 years old)


## Terms:

- **Instances:** vectors of values/attributes (also known as sets of input) that define what the input space is. For example, instances could be pictures, specific credit score examples, air temperatures, etc.

- **Concept:** also known as the "function"; this maps the instances (input) to some type of output. "An idea that describes a set of things" - e.g. if you give me something, I can tell you something about it or what it is

- **Target Concept:** the function (answer) that we are trying to find in a typical machine learning problem. For example, the specific function that determines what the model of a car is given a picture.

- **Hypothesis Class:** the set or range of all possible functions that you are willing to consider that may be the "target concept"

- **Sample:** "A Training Set"; a set of all inputs paired with a CORRECT output label. For example, a training set could contain 100 possible pictures of men of varying heights, with an associated classification label of "Tall" or "Short", which the machine learning algorithm to be developed must assume is correct. In other words, you are explaining what the "target concept" is through examples.

- **Candidate:** A concept that you assert may be the "target concept"; you can think of a candidate as a function that you hypothesize is the target concept.

- **Testing Set:** a set of inputs that the "candidate" concept is applied to. The testing set is used to determine whether or not the candidate concept is in fact the target concept. The testing set SHOULD NOT BE THE SAME as the training set/sample.


## Decision Trees

### Example 1 : Dating

Example: Do I go into a restaurant with my date or not?

- Inputs: Features of the restaurant (Instances)
 - Type of restaurant (Italian/French/Japanese/Thai)
 - Atmosphere (Fancy/Hole-In-The-Wall/Casual)
 - Price (Either discrete input, or numerical price)
 - Hot Date?
 - What day of the week is it?
 - Am I hungry?
 - What is the weather like?
- Output: Yes or No (of whether I go into the restaurant)
  ​	

A decision tree is composed of circular "nodes", which branch off into children nodes, based on the parent node's attributes/values. The lines connecting parent nodes to children nodes represent the "instance" data that influences either the corresponding leaf, or child node.
​	
A decision tree also has "leaves", which are represented by boxes, that represent an answer or "decision".
​	
The decision tree is used to represent a concrete path of attribute/value combinations that lead to a definite conclusion.
​	
For example, given a series of instance data, we can map these instances to their corresponding decision tree node or leaves, to arrive at the destination (class).
​	

The question becomes: How can we make a machine learning problem use a decision tree? We need to somehow map the decision paths to the training set, so that we can pass in a testing set and test our candidate concept.

We need to ask a series of questions that systematically "narrow the list of possibilities". Each question should continually narrow down the scope of the possible solution space until a very small, discrete list of values remain.

For a Decision Tree to learn behavior, the following are important concepts in the algorithm design process:
	1. Pick the best attribute (and define what "best" means in this context)
	2. Ask a question about that attribute
	3. Follow the answer path

![dtree](/Users/kchandra/Lyf/Kode/SCM/Github/K2/MoocX/udacity/free/ml-supervised-learning-ud675/sl1-decision-tree/dtree.png)

### Example 2: 20 Questions

1. Animal ? Yes
2. Person ? Yes
3. Famous ? Yes
4. Know Personally ? NO
5. Living ? NO
6. MUSIC ? Yes
7. 20th Century ? - Yes
8. Rap ? NO
9. Female ? NO
10. Recent Death ? Yes
11. MIchael Jackson ? Yes

to summarize the above example, this is what we did in terms of algorithm:

1. Pick the best Attribute (Best > Splits the Data in 2)
2. Asked Question
3. Follow the Answer path
4. Go to 1 , until you get an answer

## Decision Trees: Expressiveness of Booleans (2 variables)



![dtbool](/Users/kchandra/Lyf/Kode/SCM/Github/K2/MoocX/udacity/free/ml-supervised-learning-ud675/sl1-decision-tree/dtbool.png)

- AND - both variables must result in TRUE to reach a decision
- OR - only one variable must result in TRUE to reach a decision
  ![dtor](/Users/kchandra/Lyf/Kode/SCM/Github/K2/MoocX/udacity/free/ml-supervised-learning-ud675/sl1-decision-tree/dtor.png)
- XOR - either one or the other variable must result in TRUE, but NOT BOTH, to reach a decision
  ![dtxor](/Users/kchandra/Lyf/Kode/SCM/Github/K2/MoocX/udacity/free/ml-supervised-learning-ud675/sl1-decision-tree/dtxor.png)
Exploring the size of decision trees
​	
-  n-OR (Any) - the size of this decision tree with n nodes is "n" or "linear"; for each n, there must be one node
-  n-XOR (Odd Parity) - the size of this decision tree with n nodes is "n^2" or "exponential"

 Parity problems in machine learning present challenges because they require much more data gathering in the form of questions in order to arrive at the correct answer.

 Truth Table of the number of decision trees needed for n attributes (boolean) and output (boolean):
 	- 2^n number of attribute nodes
 	- two choices at the output of each row of the truth table
 	- the output function would be 2^2^n, or a double-exponential function

 ![dtn](/Users/kchandra/Lyf/Kode/SCM/Github/K2/MoocX/udacity/free/ml-supervised-learning-ud675/sl1-decision-tree/dtn.png)
## ID3 Algorithm

Loop:
	1. Assign A <- best attribute (determine what "best" means beforehand!)
	2. Assign A as decision attribute for node
	3. For each value of A, 
		- Create a descendant node
	4. Sort training set examples to leaves
	5. IF examples are perfectly classified
		- STOP
	6. ELSE 
		- Iterate over each leaf and pick the best attribute, and begin loop again
			
	At each loop iteration, we are refining our training set to better and better attributes, until are leaves are perfectly classified.

## Information Gain Equation

About: A mathematical equation that expresses the amount of information "gained" by picking a particular attribute among a list of possible labels. This function deals with entropy (the formula for inherit randomness). Essentially, this equation can be thought of as calculating the "reduction in randomness" over the list of possible labels.

Entropy: The amount of "bits" of randomization. It is helpful to understand entropy in the context of flipping a coin. If we assume a coin being flipped is a fair 1/2 chance flip, the entropy of the flip is maximal, because a flip can maximally results in either of the two values. However, a double-sided coin would have no entropy, because there is no randomness in the result of a coinflip (either side of the coin will be the same side).

So to summarize the information gain equation, it seeks to minimize entropy, because choosing from a set of values with the lowest entropy will result in the most consistent answer.
​	
## Bias

Restriction Bias - the hypothesis set that you are concerned with; in the decision tree example, this hypothesis set is all possible decision tree functions

Preference Bias - explains what sorts of hypotheses we 'prefer' from the restriction bias hypothesis set

Given two decision trees, the preference bias for ID3: 
- functions that provide good data splits at the top.
- functions that are correct over incorrect
- shorter trees

Inductive bias 

## Decision Trees: Other Considerations

	- Are there any continuous attributes? (for example, age, weight, distance, time)
	- If so, add them as a node in the decision tree, which a range specified to determine a more approximate value:
	
		20 <= AGE <= 30

QUIZ: Does it make sense to repeat attributes along a path in a decision tree?
ANSWER: It does not make sense if the attribute is part of a discrete set, but it does make sense if the attribute is a continuous attribute (such as age in the earlier example). A parent node could ask if the age is between 20 and 50, and a few child nodes down the decision path, another node could ask if the age is between 20 and 22, to determine if the person is a college student.

What happens when the ID3 decision tree algorithm runs into noise or a situation when the attributes have "noise"?

We must specify that there should not be any "overfitting" in the results. 

"Overfitting means a statistical model describes random error or noise instead of the underlying relationship. This can occur when the concept is overly complex, such as having too many parameters relative to the number of observations" - Wikipedia

"Cross Validation" is one way to avoid overfitting.

Another way to avoid overfitting is to check the attribute against the validation set before expanding the decision tree.

"Pruning" is another technique for reducing noise in a decision tree algorithm. This is usually concerned with "voting" on attributes to determine what gets pruned and what stays.

Another consideration in decision tree algorithms is the concept of "regression", because measuring information gain on continuous values is impossible. Instead, regression problems are concerned about "splitting" continuous values, moreso than reducing entropy.
​	

Summary of SL1 Video Notes:

- Representation
- ID3: A top down learning algorithm
- Expressiveness of Decision Trees
- Bias of ID3
- What are "best" attributes (Information Gain - GAIN(S, A))
- Dealing with overfitting


(credit - https://github.com/diamonddelt/supervised-learning-SL1/blob/master/SL1_video_notes.txt)
UPDATE_FROM - https://github.com/kinshuk4/MoocX/blob/master/udacity/nd/ml/classes/supervised-learning/decision-trees.md and https://github.com/ritchieng/machine-learning-nanodegree/blob/master/supervised_learning/decision_trees/sklearn_decision_trees.ipynb
