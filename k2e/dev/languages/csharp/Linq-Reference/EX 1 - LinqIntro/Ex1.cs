using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LinqIntro1
{
    class Ex1
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Running test one:\n");
            LinqTestOne.Start();

            Console.WriteLine("Running test two:\n");
            LinqTestTwo.Start();

            Console.WriteLine("Running test three:\n");
            LinqTestThree.Start();

            Console.WriteLine("Running test four:\n");
            LinqTestFour.Start();

            LinqTestEight.Start();
        }
    }

    static class LinqTestOne
    {
        public static void Start()
        {
            var nums = new[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            var result = new List<int>();
            foreach (int i in nums)
            {
                if (Predicate(i))
                {
                    result.Add(i);
                }
            }

            foreach (var i in result)
            {
                Console.WriteLine(i);
            }
        }

        public static bool Predicate(int item)
        {
            return item % 2 != 0;
        }
    }

    class LinqTestTwo
    {
        public static void Start()
        {
            var nums = new[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            var result = FilterIntegers(nums);

            foreach (var i in result)
            {
                Console.WriteLine(i);
            }
        }

        public static IEnumerable<int> FilterIntegers(IEnumerable<int> list)
        {
            var result = new List<int>();
            foreach (int i in list)
            {
                //Here's the problem with this: our predicate method is hardcoded. If we want to change our filtering method, we'd 
                //need to go in and change the method we're pointing at. This is where that method pointer comes in handy...
                if (Predicate(i))
                {
                    result.Add(i);
                }
            }
            return result;
        }

        public static bool Predicate(int item)
        {
            return item % 2 != 0;
        }
    }

    class LinqTestThree
    {
        //We can declare a delegate type of int to bool...
        public delegate bool PredicateDelegate(int value);

        public static void Start()
        {
            var nums = new[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            //And then pass a pointer to a method (that matches our delegate signature) to use to filter our list...
            var result = FilterIntegers(nums, Predicate);

            //We can give it different methods to filter the list differently...
            var evenResult = FilterIntegers(nums, IsEven);

            //Problem now is that we've created a delegate that's locked into the exact types we expect. We're taking an int, and returning
            //a boolean value; couldn't we use generics to rewrite the delegate to not explicitly use integers?
        }

        public static IEnumerable<int> FilterIntegers(IEnumerable<int> list, PredicateDelegate predicateDelegate)
        {
            var result = new List<int>();
            foreach (int i in list)
            {
                if (predicateDelegate(i))
                {
                    result.Add(i);
                }
            }
            return result;
        }

        public static bool Predicate(int item)
        {
            return item % 2 != 0;
        }

        public static bool IsEven(int item)
        {
            return item % 2 == 0;
        }
    }

    class LinqTestFour
    {
        //We can declare a generic version of our Predicate delegate...
        public delegate bool PredicateDelegate<T1>(T1 value);

        public static void Start()
        {
            var nums = new[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            
            //Now that we're using a generic delegate type we'll have to specify that we're passing it an int...
            PredicateDelegate<int> predicateDelegate = Predicate;

            var result = Filter(nums, Predicate);
        }

        //We're not really doing anything integer specific - just creating a list, looping through it, passing each int into our
        //generic predicate delegate, and adding and returning the results. We can remove the references to int and make this method
        //generic as well. Can't really call it filter integers any more, since we can filter way more than ints...
        public static IEnumerable<T1> Filter<T1>(IEnumerable<T1> list, PredicateDelegate<T1> predicateDelegate)
        {
            var result = new List<T1>();
            foreach (T1 i in list)
            {
                if (predicateDelegate(i))
                {
                    result.Add(i);
                }
            }
            return result;
        }

        public static bool Predicate(int item)
        {
            return item % 2 != 0;
        }
    }

    class LinqTestFive
    {
        public static void Start()
        {
            var nums = new[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

            //With the generic delegate types in .NET, we don't actually need to declare our own custom delegate. We can just use our Func<>
            Func<int, bool> predicateDelegate = Predicate;

            var result = Filter(nums, Predicate);
        }

        //Just need to tell our filter method that we're using our Func, instead of our old custom PredicateDelegate - still using our generic parameter,
        //but we still know we'll always want to return a bool, so we put it in the parameter list. Now we've created a generic filter method - something
        //that takes an arbitrary list of items and some delegate and returns back a list of items based on what that delegate returned. But it's kind of a
        //pain in the butt to keep on speccing out these little methods to do such simple filtering... but that's where anonymous methods come in.
        public static IEnumerable<T1> Filter<T1>(IEnumerable<T1> list, Func<T1, bool> predicateDelegate)
        {
            var result = new List<T1>();
            foreach (T1 i in list)
            {
                if (predicateDelegate(i))
                {
                    result.Add(i);
                }
            }
            return result;
        }

        public static bool Predicate(int item)
        {
            return item % 2 != 0;
        }
    }

    class LinqTestSix
    {
        public static void Start()
        {
            var nums = new[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            //Lambdas provide us a succint syntax for a delegate; now instead of pointing to a method or typing out an anonymous method, 
            //we just spec out our predicate inline with our function call and pass it into our filter method
            var result = Filter(nums, i => i % 2 != 0);
        }

        public static IEnumerable<T1> Filter<T1>(IEnumerable<T1> list, Func<T1, bool> predicateDelegate)
        {
            var result = new List<T1>();
            foreach (T1 i in list)
            {
                if (predicateDelegate(i))
                {
                    result.Add(i);
                }
            }
            return result;
        }
    }

    public static class LinqTestSeven
    {
        public static void Start()
        {
            var nums = new[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            var filteredNums = nums.FilterCollection(i => i % 2 != 0);
            foreach (var num in filteredNums)
            {
                Console.WriteLine(num);
            }
        }

        //Here we've basically recreated the Where extension method in LINQ. Putting it in an extension lets us call it on an instance of a collection,
        //and using yield return lets us return each item that satisfies the predicate as we get to it, instead of building the entire list in place.
        public static IEnumerable<T1> FilterCollection<T1>(this IEnumerable<T1> list, Func<T1,bool> predicateDelegate)
        {
            //Yield return lets us iterate over a list and return items back as we get them; instead of adding each integer into the list,
            //once we hit the yield return statement, the i is returned back from the method and the method stops executing at that point until
            //the next number is requested by the iterator. In the background, yield return is creating an iterator for us. 
            foreach (T1 i in list)
            {
                if (predicateDelegate(i))
                {
                    yield return i;
                }
            }
        }
    }

    class LinqTestEight
    {
        public static void Start()
        {
            var nums = Enumerable.Range(1, Int32.MaxValue);
            //Delayed execution means LINQ operations are chainable; 
            var result = nums.Where(i => i % 2 != 0)
                             .Where(i => i % 3 != 0);

            foreach (var item in result)
            {
                Console.WriteLine(item);
            }
        }
    }
}
