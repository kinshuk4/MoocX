using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;
using StackOverflowDumpCodeBuilder;


namespace SelectStatement
{
    static class Ex2
    {
        #region QueryResult

        internal class QueryResult
        {
            public string DisplayName { get; set; }
            public int Age { get; set; }

            public QueryResult(string DisplayName, int Age)
            {
                this.DisplayName = DisplayName;
                this.Age = Age;
            }
        }

        internal class QueryResultSansConstructor
        {
            public string DisplayName { get; set; }
            public int Age { get; set; }
        }

        #endregion

        #region Tuple

        internal class Tuple<T1, T2>
        {
            public T1 Item1 { get; private set; }
            public T2 Item2 { get; private set; }

            public Tuple(T1 item1, T2 item2)
            {
                Item1 = item1;
                Item2 = item2;
            }
        }

        internal class Tuple
        {
            public static Tuple<T1> Create<T1>(T1 item1)
            {
                return new Tuple<T1>(item1);
            }

            public static Tuple<T1, T2> Create<T1, T2>(T1 item1, T2 item2)
            {
                return new Tuple<T1, T2>(item1, item2);
            }
        }

        #endregion

        static void Main(string[] args)
        {
            //How would we select multiple properties, from multiple proporties, from a single user, or multiple properties from multiple objects?
            var users = EntityMapper.LoadUsers();

            //Could just concatenate the properties we're after...
            var Concatenate = users.Where(u => u.Age < 20).Select(u => u.Age + " " + u.DisplayName);

            foreach (var item in Concatenate)
                Console.WriteLine(item);
          
            //But what we really want is to select out a single object that has multiple properties that could be an aggregation of 
            //a single class's properties or multiple classes' properties... we could create a new class to hold our results... 
            var UseQueryResult = users.Where(u => u.Age < 20).Select(u => new QueryResult(u.DisplayName, u.Age));

            foreach (var item in UseQueryResult)
                Console.WriteLine(item);

            //But this is a pain in the butt. Imagine if you had to create a new QueryResult type every time you wanted to query out some different combination of properties.
            //Object initializers make things a little easier, letting us instantiate new objects without a constructor and directly intialize properties.
            var UseConstructerlessQueryResult = users.Where(u => u.Age < 20).Select(u => new QueryResultSansConstructor { Age = u.Age, DisplayName = u.DisplayName });

            foreach (var item in UseConstructerlessQueryResult)
                Console.WriteLine(item);
            
            //Unfortunately, this still isn't something we want to do for every query. We're still creating a class that has properties that our query expects to return.
            //The overhead for doing this for every query in your application is too big; you need some sort of adhoc, generic way to do this. One way may be to use tuples...
            var UseTuples = users.Where(u => u.Age < 20).Select(u => new Tuple<string, int>(u.DisplayName, u.Age));

            foreach (var item in UseTuples)
                Console.WriteLine(item.Item1 + item.Item2);

            //Tuples are just generic data containers; you probably don't want to pass them around your application too much and look at bunches of meaningless Item1, Item2 properties.
            //But if you're querying a result and writing it to the console, or if there's some intermediate step you need to perform, a tuple can come in handy. 
            
            //Really, what we're looking for is a class, with named properties that are meaningful in your application. We have yet another option for implementing this...
            //This is where the var keyword comes in handy. It lets us represent types that don't have a name - anonymous types. 
            var UseVar = users.Where(u => u.Age < 20).Select(u => new { u.DisplayName, u.Age });

            foreach (var item in UseVar)
                Console.WriteLine(item.Age + " " + item.DisplayName);

            //The var keyword lets us create a type out of thin air.
            var myVar = new { SomeValue = 1 };


            Console.ReadLine();
        }

        #region Method Implementations
        static void Concatenate(IEnumerable<User> users)
        {
            var result = users.Where(u => u.Age < 20).Select(u => u.DisplayName + "  " + u.Age);

            foreach (var user in result)
            {
                Console.WriteLine(user);
            }
        }

        static void UseQueryResult(IEnumerable<User> users)
        {
            var result = users.Where(u => u.Age < 20).Select(u => new QueryResult(u.DisplayName, u.Age));
            foreach (var user in result)
            {
                Console.WriteLine(user.Age + " " + user.DisplayName);
            }
        }

        static void UseConstructorLessQueryResult(IEnumerable<User> users)
        {
            var result = users.Where(u => u.Age < 20).Select(u => new QueryResultSansConstructor { Age = u.Age, DisplayName = u.DisplayName });
            foreach (var user in result)
            {
                Console.WriteLine(user.DisplayName + " " + user.Age);
            }
        }

        static void UseTuples(IEnumerable<User> users)
        {
            var result = users.Where(u => u.Age < 20).Select(u => new Tuple<string,int>(u.DisplayName,u.Age));
            foreach (var user in result)
            {
                Console.WriteLine(user.Item1 + " " + user.Item2);
            }
        }

        static void UseVar(IEnumerable<User> users)
        {
            var result = users.Where(u => u.Age < 20).Select(u => new { u.DisplayName, u.Age });
            foreach (var user in result)
            {
                Console.WriteLine(user.Age + " " + user.DisplayName);
            }
        }

        #endregion

    }
}
