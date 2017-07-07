## Parallel Programming
##### Instructors: Viktor Kuncak and Aleksander Prokopec
* [Code from Lectures](https://github.com/axel22/parprog-snippets)

#### Topics
##### [Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-parallel/src/week1) -
* Deadlock
* Consider a multi-threaded program with two threads A and B, in which these two threads perform calculations using global mutable values that gets passed around and mutated by both threads. Because we can’t don’t know for sure when thread B is going to be called, we are dealing with uncertainty about the value of the object we are working on. While it is possible to bring order by imposing strict rules regarding resource access and mutation, this workaround (commonly referred to as Synchrnous Programming) comes at a high cost in performance and additional layers of complexity in the software and hardware to have to manage the policy layer. Good reading on this topic: [Benjamin Erb’s Diploma Thesis](http://berb.github.io/diploma-thesis/original/)
* Parallelism on the JVM
* First Class Tasks

##### [Week 2](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-parallel/src/week2) -
* Task parallelism (big idea: create reduction tree):
  > a form of parallelizaton that distributes execution processes across computing nodes.
  
* Parallel map - implement using a Tree:

  ```scala
  def parMap[A: Manifest, B: Manifest](t: Tree[A], f: A => B): Tree[B] = t match {
    //match on shape of the tree
	 case Leaf(a) => {      
      //...A sequential map of array producing an array using a loop.
    }
	 case Node(l, r) => { //do parallel for the large cases, i.e., Node
	   val (lb, rb) = parallel(parMap(l, f), parMap(r, f))
		Node(lb, rb)
	 }
  }         
  ```

* Parallel fold and reduce - implement using a Tree.

  ```scala
  def parReduce[A](t: FoldTree[A], f: (A,A) => A): A = t match {
      case Leaf(v) => v
      case Node(l,r) => {
        val (lb, rb) = parallel(parReduce(l, f), parReduce(r,f))
        f(lb, rb)
      }
  }
  ```
  Associative property is important here.

* Parallel scan - requires upsweep and downsweep and a Tree consisting of a ```Leaf```, ```Node``` and a ```NodeRes```, which allows the Trees storing intermediate value in Node.

  ```scala
  sealed abstract class ScanTreeRes[A] {
  	val res: A
  }
  case class LeafRes[A](override val res: A) extends TreeRes[A]
  case class NodeRes[A](l: TreeRes[A], override val res: A, r: TreeRes[A]) extends TreeRes[A]
  case class Node[A](l: TreeRes[A],	override val res: A, r: TreeRes[A]) extends TreeRes[A]
  
  def upsweep[A](inp: Array[A], from: Int, to: Int, f: (A,A) => A): ScanTreeRes[A] = {
      if(to - from < threshold)
	     Leaf(from, to, reduceSeg(inp, from + 1, to, inp(from), f)) //imperative solution using loop
	   else {
		  val mid = from + (to - from) / 2
		  val (tL, tR) = parallel(upsweep(inp, from, mid, f), upsweep(inp, mid, to, f))
		  Node(tL, f(tL.res, tR.res), tR)
	   }
    }
  
  def downsweep[A](inp: Array[A], a0: A, f: (A, A) => A, t: TreeRes[A], out: Array[A]): Unit = t match {
      case Leaf(from, to, res) => scanLeftSeg(inp, from, to, a0, f, out) //imperative solution using loop
      case Node(l, _, r) => {
	     val (_, _) = parallel(downsweep(inp, a0, f, l, out), downsweep(inp, f(a0, l.res), f, r, out))
	  }
  }
  ```

##### [Week 3](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-parallel/src/week3) -

* Data parallelism
  > a form of parallelization that distributes data across computing nodes.
  
  Data parallelism provides significant speedup over Task parallelism when the work is not constant.
* parallel for-loop - Scala collections can be converted to parallel collections by invoking the ```.par``` method. Subsequent data parallel operations are executed in parallel:

	 ```scala
	 //a program that checks for palindrome
	 (1 until 1000).par
	 		.filter(n => n % 3 == 0)
			.count(n => n.toString == n.toString.reverse)
	 ```	 
	Data Parallelism does not work for ```foldLeft```, ```foldRight``` ```scanLeft```, ```scanRight```, ```reduceLeft```, and ```reduceRight``` operations because these processes process elements sequentially. In order to use data-parallelism, we have to use ```fold```, ```scan```, and ```reduce```.
	
	 ```scala
	 def foldLeft(z: A)(f: (B, A) => B): B //can't have data parallelism
	 def fold(z: A)(f: A, A) => A): A //can have data parallelism
	
	 ```

* ```foldLeft``` is more expressive than ```fold```, however ```fold``` allows for data parallelization, with the condition that the neutral element ```z``` and the binary operator ```f``` must form a monoid. ```z``` must be the same type as the members of the collection and the ```f``` operator must be associative for the program to work correctly. 
	 
   ```scala
   def fold(z: A)(f: A, A) => A): A
   def foldLeft(z: A)(f: (B, A) => B): B
   def fold(z: A)(op: (A,A) => A): A = foldLeft[A](z)(op) 

   /** Example showing the difference between fold and foldLeft */
   //The following program does not compile -- z is 0 and not a Char
   Array('E', 'P', 'F', 'L').par.fold(0)((count, c) => 
      if(isVowel(c)) count+1 else count)

   //However, foldLeft compiles because for foldLeft, z element does not
   //have to be the same type as the elements in the array
   Array('E', 'P', 'F', 'L').foldLeft(0)((count, c) =>
		if(isVowel(c)) count+1 else count)

	def isVowel(c: Char): Boolean =
		List('a','e','i','o','u') exists (a => a == Character.toLowerCase(c))
   ```
	
* ```aggregate``` addresses the shortcomings of both the fold (not expressive) and foldLeft (no data parallelization). The ```aggregate``` operation is defined as: 

	```scala
	def aggregate[B](z: B)(f: (B,A) => B, g: (B, B) => B): B
	```
	
	how it works is it divides the data into many smaller parts for mutiple processors to compute via foldLeft. Then it combines back into a form similar to fold. The parallel reduction operator and the neutral element form a monoid.
    
     ```scala
  	 // Use aggregate to count the number of vowels in an array
    Array('E', 'P', 'F', 'L').par.aggregate(0)(
   			(count, c) => if(isVowel(c)) count + 1 else count,
  			_ + _
  	 )  
  	 ```
    
* Scala Parallel Collection: 
	![Scala Parallel Collection](http://docs.scala-lang.org/resources/images/parallel-collections-hierarchy.png)
	* Sequential traits
		*  ```Traversable[T]``` - collection of elements with type T, with operations implemented using ```foreach```.
		* ```Iterable[T]``` - collection of elements with type T, with operations implemented using ```iterator```.
		* ```Seq[T]``` - an ordered sequence of elements with type T.
		* ```Set[T]``` - a set of elements with type T (no duplicates).
		* ```Map[K,V]``` - a map of keys with type K associated with values of type V (no duplicate keys). 
	* Parallel Counterpart traits - ```ParIterable[T]```, ```ParSeq[T]```, ```ParSet[T]```, and ```ParMap[K,V]```.
	* For code that is agnostic about parallelism, there exists a separate hierarchy of generic collection traits ```GenIterable[T]```, ```GenSeq[T]```, ```GenSet[T]``` and ```GenMap[K,V]```. A generic collection type, such as GenSeq or GenMap, can be implemented either with a parallel or a sequential collection. This means that the classify method will either run sequentially or in parallel, depending on the type of the collection that is passed to it, and returns a sequential or a parallel map, respectively. The method is oblivious to whether the algorithm is parallel or not.
	* Not thread-safe: ```mutable.Map[K,V]```.
	* Thread-safe: ```TrieMap[K,V]``` - the ```snapshot``` method can be used to efficiently grab the current state by creating a copy of the current state. 
	* Rules:
		* Never write to a collection that is concurrently traversed.
		* Never read from a collection that is concurrently modified.
		* Don't mutate collection states without synchronization.
		* Look for ways to use the correct combinator to solve the problem via pure functions - No side effect gives deterministic and correct data-parallel operation (deterministic programs that don't crash).
* Data Parallel Abstractions - iterator, splitter, builders, combiners.

##### [Week 4](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-parallel/src/week2) -	
* Implementation of Combiners - picking datastructures that are more amenable to parallelization. 
	* Transformer operation is a collection operation that creates another collection, instead of adjusting a single value. Methods such as ```filter```, ```map```, ```flatMap```, and  ```groupBy``` are examples of transformer operations. Methods such as ```fold```, ```sum```, and ```aggregate``` are not transformer operations.
			
		```scala
		//Builders can only implement sequential transformer operations
		trait MyBuilder[A, Repr] {
		 //The `+=` adds the items into the collection sequentially
		 def +=(elem: A): MyBuilder[A, Repr] 
		 def result: Repr //obtains the resulting sequence
		}
			
		//Combiners implement parallel transformer operations
		//When Repr is a Set or a Map, combine represents a Union
		//When Repr is a sequence, combine represents concatenation
		trait MyCombiner[A, Repr] extends MyBuilder[A, Repr] {
		 def combine(that: MyCombiner[A, Repr]): MyCombiner[A, Repr]
		}
		```
	 
	* Constructing datastructures in parallel requires an efficient ```combine``` operation. Why? Because we are copying things multiple times to create the sub-problems for the multicore computer to process in parallel and merging things back into the final result. The act of copying takes time. 
	* We cannot implement an efficient ```combine``` operation for Arrays.
	* Worst case lookup time:
		* Hash tables - O(1)
		* balanced tree - O(log n)
		* Linked list - O(n)
	* However, most data structures can be construted in parallel using two-phase constructure, where the intermediary datastructures are ArrayBuffers, Hash tables, balanced trees, or quad trees (for partitioning the data based on their spatial coordinates). 
* Conc-tree Data Structure - 

#### Programming Assignments
1. Box Blur Filter - demonstrates Tasks
  * boxBlurKernel - this method computes the blurred RGBA value of a single pixel of the input image
  * HorizontalBoxBlur - singleton which implements methods blur and parBlur to blur horizontal pixels of the image in parallel
  * VerticalBoxBlur - singleton which implements methods blur and parBlur to blur vertical pixels of the image in parallel
  * To run the ScalaShop program, in the console:
    ```
      sbt
      > runMain scalashop.ScalaShop
    ```
2. Reductions and Prefix Sums - demonstrates basic task parallel algorithms
3. K-Means - demonstrates Data-Parallelism
4. Barnes-Hut Simulation - demonstrates data structures for Parallel Computing

#### Solutions links
1. [hugcruz](https://github.com/hugcruz/parprog1)
2. [ehsanmok](https://github.com/ehsanmok/Scala-Parallel-Programming)
3. [TomLous](https://github.com/TomLous/coursera-parallel-programming-scala/tree/master/src/main/scala)
4. [reinno](https://github.com/reinno/coursera-parprog)





https://github.com/xiaoyunyang/coursera-scala-specialization/edit/master/README.md