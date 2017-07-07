using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using StackOverflowDumpCodeBuilder;
using System.Collections;

namespace EX_6___Casts__Joins__Groups_and_Zip
{
    class Program
    {
        static void Main(string[] args)
        {
            var users = EntityMapper.LoadUsers();
            var badges = EntityMapper.LoadBadges();

            #region ToList, ToDictionary, ToArray, ToLookup

            //In LINQ, the results of our queries are always going to be an IEnumerable of some type
            //LINQ operates in a delayed manner, so the following select won't do anything until we interate over it
            IEnumerable<User> result = users.Select(u => u);

            //Certain operations that need to iterate over the entire enumerable can be expensive, so LINQ provides 
            //extensions to put the result of a query straight into a collection. This forces the entire result set 
            //to be put into a list; all the work is done in the following line, rather than when we iterate over it
            IEnumerable<User> listResult = users.Select(u => u).ToList();

            //Can also put the results of a query into a dictionary by supplying a key; the following gives us a dictionary
            //of UserID to User. Of course, you need to be sure that the item used as a key is a unique value
            Dictionary<int, User> dict = users.Select(u => u).ToDictionary(u => u.Id);

            //A useful overload to ToDictionary takes a keySelector and an elementSelector, which lets us specify what part
            //of the user we want to be the values in the dictionary
            Dictionary<int, string> newDict = users.Select(u => u).ToDictionary(u => u.Id, u => u.DisplayName);

            //Can also cast into an ILookup, a generic interface that can apply to any item that maps a key to a value.
            //Unlike a dictionary, ILookup is mapping directly to the Lookup type, which was introduced in 3.5 and acts
            //similar to a dictionary, except that you can have repeated keys, and can match a single key to multiple values
            ILookup<int, string> ageToName = users.Select(u => u).ToLookup(u => u.Age, u => u.DisplayName);

            //Here we've created an IEnumerable<IGrouping>, of age to user display name
            foreach (IGrouping<int, string> grouping in ageToName)
            {
                Console.WriteLine(grouping.Key);
                foreach (string displayName in grouping)
                    Console.WriteLine(displayName);
            }

            #endregion

            #region GroupBy

            //We can do a similar thing as ToLookup using the grouping operators, with the added bonus that 
            //grouping operations benefit from deferred execution, just like the rest of LINQ
            var ageGrouping = users.GroupBy(u => u.Age);

            //This gives us the same result as the ToLookup example above
            foreach (var group in ageGrouping)
            {
                Console.WriteLine(group.Key);
                foreach (var user in group)
                    Console.WriteLine(user);
            }

            //Again, we get a useful overload that lets us specify an elementSelector. Here we're getting back a grouping
            //of age to displayname, instead of age to user type
            var elementSelect = users.GroupBy(u => u.Age, u => u.DisplayName);

            //Another overload takes a resultsSelector, a lambda which takes in the key and the values from the grouping and do something with them
            var resultSelect = users.GroupBy(u => u.Age, u => u.DisplayName, (key, value) => new { Age = key, Count = value.Count() }).OrderBy(u => u.Count);

            //The above projects the grouping into our anonymous type, instead of an IGrouping
            foreach (var group in resultSelect)
                Console.WriteLine(group.Age + " " + group.Count);

            #endregion

            #region Join and GroupJoin

            //Join operator works just like we'd expect, same as it does in SQL. Basically, we call it on a collection
            //and pass in another collection to join to, spec out selector methods for the outer and inner collections,
            //and then pass in a result selector describing what we'd like to get back from the join
            var joinResult = users.Join(badges, 
                                        u => u.Id, 
                                        b => b.UserId, 
                                        (u, b) => new { User = u, Badge = b });

            foreach (var join in joinResult) Console.WriteLine(join.User.DisplayName + " " + join.Badge.Name);

            Console .WriteLine();

            //We are performing an "inner" join, ie, we aren't getting any results back unless both the left and right
            //side have matching values. There are times we might want a "left outer" join - we want a complete list of an entire
            //side, whether it has matches on the right side or not. You'll have to use the groupjoin operator...
            var groupJoin = users
                            .GroupJoin(badges,
                                 u => u.Id,
                                 b => b.UserId,
                                (u, b) => new { User = u, Badges = b.DefaultIfEmpty() })
                            .SelectMany(a => a.Badges.Select(b => new { User = a.User, Badge = b }));

            foreach (var join in groupJoin)
            {
                Console.WriteLine(join.User.DisplayName + " " + 
                    (join.Badge == null ? "" : join.Badge.Name));
            }

            Console.WriteLine();

            #endregion

            #region Zip

            //What zip does is let you take two sequences and combine them item by item. Generally the use case for 
            //zip is when you have two sequences and you need to perform two different queries on them, and you need
            //to combine those sequences, sort of like a zipper; first item in list1 goes with first item in list2, etc...

            //Result of the following zip operation is just a concatenated string of doubled up user names
            var zipTest = users.Take(10).Zip(users.Take(10), (u1, u2) => u1.DisplayName + " " + u2.DisplayName);

            foreach (var res in zipTest)
                Console.WriteLine(res);

            //Of course that's kind of silly. But imagine if the two different values were coming from two different queries

            Console.WriteLine();

            #endregion

            #region Cast and OfType

            //Cast lets us take a sequence of types and cast them to another type; really useful with old libraries
            //that might use ArrayLists or something

            var list = new ArrayList();
            list.Add(1);
            list.Add(2);
            list.Add(3);

            //We want to perform some filtering across this list, but in order to perform the predicate, we need some types.
            //You can get a strongly typed IEnumerable<int> using cast
            var castList = list.Cast<int>().Where(i => i > 1);

            Console.WriteLine();

            //But you have to be careful with cast - it will throw an exception if all the items in the list are not
            //of the specified type. If you don't know for certain that the list is singly typed, you can use OfType
            var multiList = new ArrayList();
            list.Add(1);
            list.Add(2.3);
            list.Add(3);

            var onlyInts = list.OfType<int>();

            foreach (var num in onlyInts) Console.WriteLine(num);

            #endregion

            #region Histogram Example
            var listOLists = new List<List<string>>() {
                new List<string>{"CATS", "DERP", "SUPERDERP"},
                new List<string>{"WELP", "DERP", "CATS", "CATS"},
                new List<string>{"WELP", "HNNNGGG", "CATS"}
                };

            var histO = listOLists.SelectMany(x => x).GroupBy(s => s);//.ToDictionary(g => g.Key, v => v.Count());
                
                //.Select(g => new { Key = g.Count(), Word = g.Key });

            foreach (var group in histO)
            {
                Console.WriteLine(group.Key);
                foreach (var Word in group)
                    Console.WriteLine("\t" + Word);
            }

            //(l, s) => l.Count(x => x == s));
            #endregion

            Console.ReadLine();

        }
    }
}
