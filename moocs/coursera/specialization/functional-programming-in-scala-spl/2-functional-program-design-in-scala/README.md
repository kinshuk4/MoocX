principles-of-reactive-programming
==================================

Earlier - https://class.coursera.org/reactive-001/class



## Functional Program Design in Scala
##### Instructor: **Martin Odersky**
* [Reactive Cheatsheet](https://github.com/sjuvekar/reactive-programming-scala/blob/master/ReactiveCheatSheet.md)
* [Reactive Extension for JavaScript](https://github.com/Reactive-Extensions/RxJS)
* [Reactive Programming Intro](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754)
* [Functional Reactive Programming Study Guide](https://github.com/dnvriend/reactive-programming)

#### Topics
##### [Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/tree/master/coursera-program-design/src/week1) -
*  Partial functions - See [partialfuns.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/partialfuns.sc)

 ```scala
 val f1: PartialFunction[String, String] = { case "ping" => "pong" }

 //result: List(pong, 404, 404)
 List("ping", "abc", "pong").map(a =>
   if(f1.isDefinedAt(a)) f1(a) else "404"
 )
 ```
* For-expressions/for-comprehension - shortcuts for doing a flatMap, filter, then a map. Useful when you need to do nested loops. See [collections.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/collections.sc) for how to use for expressions to implement filter, map, and flatMap, and vice versa. See [json.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/json.sc) and [query.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/query.sc) for how for expressions are useful for database query and json applications. [Sets](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.Set) are typically used for database query or keeping track of a set of explored in graph search applications because Sets don't contain duplicates.
* Random Generators and ScalaCheck - [Generator.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/Generators.scala) has various random generators written using ```scala.util.Random```. [generators.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/generators.sc) shows how it's used.
* Monad - functional programming and reactive programming pattern. Three Monad Laws, Option and Try. See [monad.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/monad.sc). Some good articles on monads: [Demystifying the Monad in Scala](https://medium.com/@sinisalouc/demystifying-the-monad-in-scala-cc716bb6f534#.ebd50ld0z) and [Functors and Applicative](https://medium.com/@sinisalouc/functors-and-applicatives-b9af535b1440#.lx20785nm)

##### [Week 2](https://github.com/xiaoyunyang/coursera-scala-specialization/tree/master/coursera-program-design/src/week2) -
* [Stream](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.Stream) - Streams are similar to [Lists](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List), but their tail is evaluated only on demand. There are three ways to create Streams:

 ```scala
  //Notice the tail is a "?". This means the tail is not yet evaluated
  Stream(1,2,3) //res: Stream(1, ?)
  (1 to 3).toStream //res: Stream(1, ?)
  val xs = Stream.cons(2, Stream.cons(3, Stream.empty)) //res: Stream(2, ?)
  1 #:: xs //res: Stream(1, ?)
 ```

 Unlike the List, only the head of the Stream gets evaluated when it's first instantiated. The tail do not get evaluated until it's first needed.

 ```scala
 def streamRange(lo: Int, hi: Int): Stream[Int] = {
    print(lo+" ") //<- a side effect
    if(lo >= hi) Stream.empty
    else Stream.cons(lo, streamRange(lo + 1, hi))
 }

 /* The following will not print out "1" because the tail of the
  * stream is unevaluated so it will only print out the head (i.e. 1)
  */
 streamRange(1, 10).take(3)  

 /* The following will print out "1 2 3" as side effect because we
  * are forcing all the tails of the Stream to be evaluated by making * it toList
  */
 streamRange(1, 10).take(3).toList
 ```
 See [MyStream.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/MyStream.scala) for an implementation and [Stream.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/stream.sc) for some examples of using Stream.
* Lazy Evaluation - Laziness means do things as late as possible and never do them twice. The later is achieved with memoization, meaning storing the result of the first evaluation and re-using the stored result instead of recomputing. This optimization is sound, since in a purely functional language, an expression produces the same result each time it is evaluated. In the case of by-name and strict evaluation, everything is recomputed. In general, lazy evaluation is a combination of by-name evaluation and memoization. See [laziness.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/laziness.sc) for some examples of lazy evaluations and infinite sequence using Streams. [MyLazyStream.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/MyLazyStream.scala) modified [MyStream.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/MyStream.scala) to be lazy in the tail.

 ```scala
 //If you run the following program, "xzyz" gets printed as a side effect
 def expr = {
    val x = { print("x"); 1} //val gets instantiated when you first define it
    lazy val y = { print("y"); 2 } //lazy val gets instantiated only the first time you use it
    def z = { print("z"); 3 } //def gets instantiated everytime you use it
    z + y + x + z + y + x
 }    
 ```
* Infinite Sequence - using Streams

 ```scala
 // The Sieve of Eratosthenes:
 def sieve(s: Stream[Int]): Stream[Int] =
   s.head #:: sieve(s.tail filter (_ % s.head != 0))

 val primes = sieve(from(2))  //> primes  : Stream[Int] = Stream(2, ?)
 primes.take(10).toList    //> res4: List[Int] = List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
 ```
* The water pouring problem - Program Design. Two optimizations to avoid re-computation: (1) keep a list of explored paths, pass that into the next Path calculation (2) pass the current endState to the constructor of the next Path so the next Path won't have to recompute the last Path's endState all over again using the history. See [Pouring.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/Pouring.scala) and [pouringTest.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/pouringTest.sc) for implementation and result.

##### [Week 3](https://github.com/xiaoyunyang/coursera-scala-specialization/tree/master/coursera-program-design/src/week3) -
* Functions and State - in a reactive program, there will be mutable states. We will broaden our definitions of functions to work with states. We have worked with pure functions have no side effects, but working with mutable states has repurcussions. The concept of time is important for mutable states. In general, objects with mutable states are identifiable through the following two observations:
 * when we perform the same operation twice below, we get back different answers. This is because the history of the operation matters.
 * Whenever you see ```var```, it should be a red flag that you are probably dealing with mutable states. Every form of mutable state is constructed from variables. A ```var``` is like a ```val``` but a ```var``` can be changed later on by assignment.
 * Identity and Change - What it means to be equivalent - x and y are operational equivalence if no possible test can distinguish between them.

 See [BankAccount.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week3/BankAccount.scala) and [accounts.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week3/accounts.sc) for a stateful implementation of the BankAccount.

* Loops - While and Repeat can be translated into higher order functions Do-while loops can be implemented by making WHILE a function inside the DO class. For-loops can be implemented using for-expressions and foreach call on Ranges (1 until n). See [loops.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week3/loops.sc) for the functional implementation of various loops.
* Discrete Event Simulator ([Simulation.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week3/Simulation.scala) and [digitalCircuits.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week3/digitalCircuit.sc)) which maintains a class heirarchy:


	​```scala
	trait Simulation {
		type Action = () => Unit
		case class Event(time: Int, action: Action)
		//...
	}
	abstract class Gates extends Simulation {
		class Wire {
			//...
		}
		def inverter(input: Wire, output: Wire): Unit = {
			//...
		}
		def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
			//...
		}
	 	def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
	 		//...
 		}
 		def probe(name: String, wire: Wire): Unit = {
 			//...
 		}
	}
	abstract class Circuits extends Gates {
  		//...
  	}
  	trait Parameters {
  		def InverterDelay = 2
  		def AndGateDelay = 3
  		def OrGateDelay = 5
	}
	object sim extends Circuits with Parameters
	import sim._
	//...
	
	​```
	
	* An implementation of it is the digital circuit simulator in which each type of gate observes whether any input changes and if so, updates the output signal after some delay (performs action after delay).
	* Discrete Event Simlator API has performs actions specified by the user at a given moment. An ```action``` is a function that doesn't take any parameters and returns Unit.
		
		```scala 
		type Action = () => Unit
		```
		
		Every instance of the Simulation trait keeps an agenda of actions to perform. The agenda is a List of simulated events. Each event consists of an action and the time when it must be produced. 
	* In the end, it's a tradeoff when you use mutable states. On one hand, assignments allow us to formulate certain programs in an elegant way. On the other hand, you lose referential transparency (RT) and the substitution model (tools for you to reason about the program).

##### [Week 4](https://github.com/xiaoyunyang/coursera-scala-specialization/tree/master/coursera-program-design/src/week4) -
* Imperative Event Handling (used a lot for User Interface): The Observer Pattern (e.g., publish/subscribe and model/view/controller). See [BankAccountPublisher.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week4/BankAccountPublisher.scala), [Publisher.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week4/Publisher.scala), and [observers.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week4/observers.sc) for an implementation of BankAccount manager using the observer pattern. This pattern has a few shortcomings.
 * The Good: 
  * Decouples views from state and allows us to have varying number of views of a given state. And it's simple to set up.
 * The Bad: 
  * Forces imperative style (because returns Unit as result).
  * Many moving parts that need to be co-ordinated. Every subscriber has to announce itself to the publisher with subscribe and the publisher has to handle these things in the datastructure.
  * Things will get more complicated when you add concurrency, e.g., when you have a view that observes two different models that get updated concurrently, then the two models can call the handler method of the view, you get race conditions.
  * Another thing is views are still tightly bound to one state. Event handling can be very buggy.
* Functional Reactive Programming (FRP) - reactive programming is about reacting to sequences of events that happen in time. The event sequence is aggregated into a signal. Instad of mutating states to propagate updates, we create new signals in terms of existing ones.
 * Some examples are: [Flapjax](http://www.flapjax-lang.org/), [Elm](http://elm-lang.org/) (javascript), [Bacon.js](https://baconjs.github.io/) (javescript), [React4J](https://bitbucket.org/yann_caron/react4j/wiki/Home) (Java). Rx is a event streaming dataflow programming system, but not really FRP. ```Scala.reactive``` handles FRP.
* ```Signal``` is a value that you can sample at a particular point in time. Signal can be a variable. The crucial difference between a variable signal and mutable variables is that we can define relationship between two signals and when one changes, the other automatically changes. There are no such mechanisms for mutable variables and they must be updated manually. See [BankAccountPublisher.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week4/BankAccountPublisher.scala) and [signal.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week4/signal.sc) for examples of using Signals to maintain states.
 ​	
 ```scala
 //assign constant value to a signal
 val sig = Signal(3) 

 //There are two ways to update a Variable signal
 val sig = Var(3)
 sig.update(5)
 sig() = 5

 /** Key difference between mutable variables and signal variables */
 //mutable varialbes
 var a = 2 
 var b = 2 * a
 a = a + 1
 b		// still 4. Not automatically updated to 6

 //signal variables
 val aSig: Var[Int] = Var(2) // signal variable of 2
 val bSig: Var[Int] = Var(2 * aSig()) //signal variable of 2 * the value of aSig 
 bSig()  // to "dereference" the bSig, we get 4
 aSig.update(3)
 bSig()		// the value of bSig at current time is automatically updated to 6!

 //Constant vs. variable signals
 val x = Var(1)
 val y = Signal(x() * 2)  // when using Signal(), definition cannot be changed
 x()		// 1                         
 y()		// 2

 val x1 = x() + 1
 x() = x1
 x()  	// 2
 y()		// 4		

 /** cyclic signal definition. 
   * s() = s() + 1 makes no sense because you 
   * are trying to define a signal s to be at all points in time larger than itself.
   */
   
 //ERROR
 val x = Var(1)
 x() = x() + 1  // cyclic signal definition

 //CORRECT
 val x = Var(1)
 val x1 = x() + 1
 x() = x1 // updates x() to be Var(2)


 /** Caveats with Signals 
   * moral of the story is don't use var and use s.update(v) instead of s() = v to 
   * minimize chance of mistake.
   */
 val num = Var(1)
 val twice = Signal(num() * 2)
 num() = 2
 twice()	// 4

 var num2 = Var(1)
 val twice2 = Signal(num2() * 2)
 num2 = Var(2)
 twice2()	// 2	
 ```
* Implementation of ```Signal``` and ```Var```, which is a subclass of ```Signal``` in [Signal.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week4/Signal.scala). Each signal maintains its current value, the current expression that defines the signal value, and a set of observers (i.e., the other signals that depend on its value. 
 * The first implementation of Signal relies on a global variable ```caller```, which is a stack. Global variables in concurrency are bad news (results in race conditions). One way to deal with that is to use synchronization.
 * For synchronization, replace global variable by thread local state, i.e., use ```scala.util.DynamicVariable```. Thread-local state means each thread accesses a separate copy of a variable. Thread-local state is an improvement to the unprotected global variable, but it has its own problems (fundamentally imperative, use of threads could create deadlock, its implementation on JDK involves a slow global hash table lookup).
 * Another solution involves implicit parameters. Instead of maintaining a thread-local variable, pass its current value into a signal expression as an implicit parameter. 
* Latency as Effect - Computation can take time. Futures.
* **Future Topics:**
 * Threads are how you do concurrency, but threads are really low levels and dangerous. Some good abstractions of Threads a Futures, Reactive Streams, and Actors using [Akka](http://akka.io/).
 * Using Parallel data structures and parallel algorithms to use multi-threadedness to gain efficiency.
 * If we want to scale parallelism beyond what a single computer can do, then we arrive at distributed programs. Important topics are data analysis and big data using Apache [Spark](http://spark.apache.org/). Spark can be seen as a framework for distributed scala collections. 

#### Programming Assignments
1. Bloxorz - get practice on lazy evaluation, for-expression, Stream, and DFS/BFS graph search algorithms. You are solving a
2. Quickcheck - demonstrates functions and state
3. Calculator - demonstrates timely effects and implement UI using ```Signal```. Hint: dependent signals must be fully wrap the signal upon which it depends (including all calculations). Do not assign signal values to intermediate values ```val```. To store intermediary calculations using signal, assign intermediaries to ```def```. Wrap every atomic computation involing signal values in parentheses.
 * To run compile and run the webUI page with output in console: ```sbt > webUI/fastOptJS``` 

#### Solutions links
1. [ncolomer](https://github.com/ncolomer/coursera/tree/master/reactive)
2. [ehsanmok](https://github.com/ehsanmok/Scala-Parallel-Programming)
3. [Quickcheck Gist](https://gist.github.com/wh5a/7394082)
4. [alias1](https://github.com/alias1/coursera-reactive)
5. [ysihaoy](https://github.com/ysihaoy/coursera-reactive-scala)

https://github.com/iirvine/principles-of-reactive-programming

https://github.com/xiaoyunyang/coursera-scala-specialization/edit/master/README.md