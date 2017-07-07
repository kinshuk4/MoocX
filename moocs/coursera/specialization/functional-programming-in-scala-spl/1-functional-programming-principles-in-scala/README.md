functional-programming-in-scala
===============================

https://class.coursera.org/progfun-003/class/index


#### Topics
##### [Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1) -
* Evaluation strategy and recursion: [fixedpoint.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1/fixedpoint.sc) demonstrates writing a basic function in scala.
   [eval_strategy.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1/eval_strategy.sc) illustrates call-by-vale (imperative, C) versus call-by-name (not to be confused with lazy evaluation).

   ```scala
   def constOne(x: Int, y: => Int) = 1   //y has a type '=> Int' which means y is passed as a by-name parameter
   constOne(1+2, loop) //reduces to 1
   constOne(loop, 1+2) //infinite cycle
   ```

##### [Week 2](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2) -
* higher order functions - basically functions that has functions as arguments. See [HOF.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2/HOF.sc) for some examples.
* Currying and Partial applications - currying is the process of transforming a function that takes multiple arguments into a function that takes a single argument and returns another function that returns another function that takes remaining arguments. This way, we can transform any function of multiple arguments into chain of multiple functions with single argument (e.g., a partially applied function). Why would we want to break up a big complicated function that takes a dozen arguments into many smaller functions that take a few arguments each?

 ```scala
 // without curry
 def fun(z: Int, x: Int): Int = z + x  //fun is a function that takes two arguments
 List(1,2,3) map (x => fun(5, x))          
 List(1,2,3) map (fun(5, _))               

 // with curry
 def fun2(z: Int)(x: Int): Int = z + x  
 List(1,2,3) map fun2(5) //fun2(5), the argument of map, is a partially applied function of type Int => Int               
 ```

* Scala class and datastructure in scala example: [Rational.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2/Rational.scala), which gets called in [datastructure.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2/datastruct.sc)

##### [Week 3](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3) -
* To run a Scala Application, just add a main function:

 ```scala
 object ScalaProgram {
    def main(args: Array[String]) = {
       println("hello world!")
    }
 }
 ```
* class hierarchy and organization. See example: [List.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/List.scala) and [IntSet.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/IntSet.scala), which is called by [class_hierarchy.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/class_hierarchy.sc).

 ```scala
  trait Tree[+T]
  case class Leaf[T](value: T) extends Tree[T]
  case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]
  case object Empty extends Tree[Nothing]
  object Tree { //companion object
    //functions in here can pattern match on case Leaf, case Branch, or case Empty.
  }
 ```

* Scala traits and abstract class. See [classes_org.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/classes_org.sc) for examples of extending traits as well as when you use Nothing and AnyVal.

 ![class hierarchy](https://cdn.intellipaat.com/wp-content/uploads/2015/11/class-hierarchy-of-scala.png)


##### [Week 4](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4) -
* Subtyping and typing rules for functions: see  [subtyping.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/subtyping.sc). Functions are contravariant in their argument types and covariant in their result type. For example, the scala Function1 trait:

   ```scala
    /* Variance
     * the '-' and '+' in front of the type A and R denotes the
     * variance. '+R' and '-A' means A <: R or 'A is a subtype of R' or * 'R is a supertype of A'
     */
    trait Function1[-A, +R] {
      def apply(x: A): R
    }
   ```

* polymorphism, illustrated in [MyList.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/MyList.scala) and the scala worksheet. [collectionlist.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/collectionlist.sc). As another example, [Boolean2.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Boolean2.scala) implements a boolean using an abstract class and two companion objects. In the object oriented approach with a trait and class implementations, we can make a singleton for things that only has one implementation. See the Scala implementation of the Peano number in [Nat.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Nat.scala).

* Pattern Matching, or a fancy if statements (a generalization of switch from C/Java to class hierarchies) is a good solution for Functional Decomposition. As the purpose of decomposition is to reverse the construction process, pattern matching offers a great way to automate this deconstruction process. The comparison of pattern matching decomposition approach (using scala trait and case class) with the object oriented approach (trait and class implementations) is highlighted in  [Expr.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Expr.scala), [Expr2.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Expr2.scala), and [Expr3.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Expr3.scala).

##### [Week 5](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week5) -
* List operations - functions on list processing
* Pairs and Tuples - can help with program composition and decomposition. Pair is a 2-tuple. Pair can only take two things. Tuples can take as long of a sequence as you want.

 ```scala
 //pair
 val pair = ("answer", 42)

 //tuple
 //value assign using pattern matching - preferred because it's shorter and clearer
 val (label, value1, value2) = (pair._1, pair._2, 80) pair._1 //equivalent to label: "answer"
 pair._2 //equivalent to value1: 42           
 ```

* Implicit Parameters and ```scala.math.Ordering```
* Higher Order List Functions (HOFs)
  * HOFs that take unary operators: ```filter```, ```filterNot``` and ```partition``` (combination filter and filterNot. ```takeWhile```, ```dropWhile``` and ```span``` (combibnation takeWhile and dropWhile)
  * HOFs that take binary operators: ```reduce``` and ```fold```

  ```scala
  //Scan is like recording the intermediate values of computing a fold

  //fold
  List("a","b","c").fold("!")((x,y) => x+y) // !abc
  List("a","b","c").scan("!")((x,y) => x+y) // List(!, !a, !ab, !abc)

  //foldLeft - scans the original list and constructs the new list from left to right
  List("a","b","c").foldLeft("!")((x,y) => x+y) // !abc
  List("a","b","c").scanLeft("!")((x,y) => x+y)  // List(!, !a, !ab, !abc)
  List("a","b","c").foldLeft("!")((x,y) => y+x)   // cba!
  List("a","b","c").scanLeft("!")((x,y) => y+x)  // List(!, a!, ba!, cba!)

  //foldRight - scans the original list and constructs the new list from right to left
  List("a","b","c").foldRight("!")((x,y) => x+y)  // abc!
  List("a","b","c").scanRight("!")((x,y) => y+x)  // List(!cba, !cb, !c, !)
  List("a","b","c").foldRight("!")((x,y) => y+x)  // !cba
  List("a","b","c").scanRight("!")((x,y) => y+x)  // List(!cba, !cb, !c, !)

  /** Calculate max of an array using fold is super easy */
  //we used the par method on 
  //the collection to turn it into a parallel data collection 
  def max(xs: Array[Int]): Int = {
     //instead of math.max, you can use (x, y) => if(x > y) x else y
     xs.par.fold(Int.MinValue)(math.max) 
  }                                                  
  ```

* Natural induction and structural induction can be performed on functional programs because functional programming allows for referential transparency. Concat on list can be proved to be associative using structural induction. The fold-unfold method for equational proof of functional programs.

##### [Week 6](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week6) -

![http://docs.scala-lang.org/resources/images/collections.mutable.png](http://docs.scala-lang.org/resources/images/collections.mutable.png)

* Other Collections of Sequence. List, Vector, and Range are implementation of  Sequence, i.e., Sequence is a base class of List and Vector. Array and String are sequence like structures and "subclasses" of Sequence, but both came from the Java universe. Set and Map all have the base class Iterable.
  * List - O(N). Good for operations with access patterns that requires you to access head and tail sequentially. For Cons, you  use ```::```. For concat, you use ```++```. The fundamental operation for List is head and tail.
  * Vector - O(Log_32(N)). Good for bulk operations, such as map or a fold or filter (highly parallelizable operations). You can do it in chunks of 32. For Cons, you use ```+:``` to add element to the left of the Vector, or use ```:+``` to add element to the right of the Vector. Note that ```:``` always points to the sequence. For Vectors, the fundamental operation is index.

  ```scala
  0 +: nums //Vector(0, 1, 2, 3, -88)
  nums :+ 0 //Vector(1, 2, 3, -88, 0)

  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
      (xs zip ys).map{case (x,y) => x*y }.sum
  }		
  ```

*  String - a sequence from the java universe [java.lang.String](http://docs.oracle.com/javase/8/docs/api/java/lang/String.html)

 ```scala
 val s = "Hello World"
 s filter (c => c.isUpper) //HW
 s exists (c => c.isUpper)  //true
 s forall (c => c.isUpper)  //false
 s flatMap (c => List('.', c)) //.H.e.l.l.o. .W.o.r.l.d
 ```

*  Range

    ```scala
    val r: Range = 1 until 5 //Range(1, 2, 3, 4)
    val t: Range = 1 to 5 //Range(1, 2, 3, 4, 5)
    1 to 10 by 3 //Range(1, 4, 7, 10)
    6 to 1 by -2 //Range(6, 4, 2)

    def rangePairs(m: Int, n: Int) = {
    	 (1 to m) flatMap (x => (1 to n) map (y => (x, y)))
     }    
     rangePairs(3,2) //Vector((1,1), (1,2), (2,1), (2,2), (3,1), (3,2))

    def isPrime(n: Int): Boolean = {
    	(2 until n) forall (d => n % d != 0)
     }
    ```

* Combinatorial Search and For-Expressions - for expressions handle nested sequences in combinatorial problems. Higher order functions on sequences often replaces loops. For nested loops, we can use for expressions. The general form is ```for ( s ) yield e ``` where s is a sequence of generators and filters, and e is an expression whose value is returned by an iterator. A generator is in the form ```p <- e``` instead of ( s ), braces { s } can also be used, and then the sequence of generators and filters can be written on multiple lines without requiring semicolons.

   ```scala
    def scalarProduct(xs: List[Double], ys: List[Double]): Double = {
      (for((x,y) <- xs zip ys) yield(x*y)).sum
    }
   ```

* Combinatorial Search examples - combine Sets and for expressions in a classical combinatorial search problem. Sets are unordered, do not have duplicate elements, and the fundamental operation for Set is contains. Checkout nqueens for an example of solution generation using Vector functions (```forall```, ```fill```, ```updated```), and for expressions.
* Map and Option - Maps provide efficient lookup of all the values mapped to a certain key. Any collection of pairs can be transformed into a `Map` using the ```toMap``` method. Similarly, any ```Map``` can be transformed into a ```List``` of pairs using the ```toList``` method.

   ```scala
    val romanNumerals = Map('I' -> 1, 'V' -> 5, 'X' -> 10)
    val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")
    capitalOfCountry("US")   //Washington
    capitalOfCountry("Andorra")  //NoSuchElementException
    capitalOfCountry get "Andorra"                  //> None
    capitalOfCountry get "US"                       //> Some(Washington)

    //With default function catches the exception and returns a default value
    val cap1 = capitalOfCountry withDefaultValue "<unknown>"
    cap1("Andorra")  //<unknown>

    //what withDefaultValue does behind the scene
    def showCapitalWithOption(country: String) = capitalOfCountry.get(country) match {
   		case Some(capital) => capital
   		case None => "missing data"
   	}
   	showCapitalWithOption("US")               //> Washington
   	showCapitalWithOption("Andorra")          //> missing data

    //To subtract something from a Map
    val m = Map("1" -> "a", "2" -> "b")		// Map(1 -> a, 2 -> b)
    m - "2"                 // Map(1 -> a)

   ```

    ```groupBy``` and ```orderBy``` operations in SQL queries have analogues in Scala, namely ```sortWith``` and ```sorted```. The ```groupBy``` method takes a function mapping an element of a collection to a key of some other type, and produces a `Map` of keys and collections of elements which mapped to the same key.

    ```scala
    val fruit = List("apple", "pear", "orange", "pineapple")
   						//List(apple, pear, orange, pineapple)
    fruit sortWith (_.length < _.length)      //List(pear, apple, orange, pineapple)
    fruit sorted                              //List(apple, orange, pear, pineapple)

    //groupBy partitions a collection into a map of collections according to a discriminator function f
    fruit groupBy (_.head)  //Map(p -> List(pear, pineapple), a -> List(apple), o -> List(orange))
    ```
* In general scala collections have certain accessor combinators like ```sum```, ```fold```, ```count```, and transformer combinators like ```map```, ```flatMap```, and ```groupBy```.

#### Programming Assignments
1. Recursive Functions - demonstrates recursion
2. Functional Sets - demonstrates Higher Order Functions
3. Object Oriented Sets - demonstrates data and abstraction
4. Huffman Encoding - demonstrates Pattern Matching and the scala List Collection
5. Anagrams - demonstrates Collections and for expressions by solving the combinatorial problem of finding all the anagrams of a sentence.

#### Solutions links
1. [ailyenko](https://github.com/ailyenko/Functional-Scala/tree/master/src/main/scala)
2. [frankh](https://github.com/frankh/coursera-scala)
3. [tonyskn](https://github.com/tonyskn/coursera-scala)
4. [drolando](https://github.com/drolando/scala-coursera)
5. [coderwall](https://coderwall.com/p/_akojq/scala-week-3)


https://github.com/iirvine/functional-programming-in-scala

https://github.com/xiaoyunyang/coursera-scala-specialization/edit/master/README.md