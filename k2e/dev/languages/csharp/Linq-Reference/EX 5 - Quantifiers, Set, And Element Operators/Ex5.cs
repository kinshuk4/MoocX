using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using StackOverflowDumpCodeBuilder;

namespace EX_4
{
    class Ex5
    {
        static void Main(string[] args)
        {
            var users = EntityMapper.LoadUsers();

            #region Quantifiers
            //Take the first ten items; if there are any items in the sequence, the query will return true
            var isAny = users.Take(10).Any();
            Console.WriteLine(isAny);

            //Take first ten items; if any of the first ten items in sequence starts with uppercase A, query will return true
            //Don't actually get the user back, just let's us check 
            isAny = users.Take(10).Any(u => u.DisplayName.StartsWith("A"));
            Console.WriteLine(isAny);

            //Do all the first ten users start with capital A?
            var isAll = users.Take(10).All(u => u.DisplayName.StartsWith("A"));
            Console.WriteLine(isAll);

            //How about if we force the query above using a Where statement?
            isAll = users.Take(10).Where(u => u.DisplayName.StartsWith("A")).All(u => u.DisplayName.StartsWith("A"));
            Console.WriteLine(isAll + "\n");

            //Instead of checking the Count() method against zero, probably a performance advantage to just check it against Any(); Any() is going to stop as soon as it hits any items
            #endregion

            #region Element Operators
            //Single allows us to write a query and assert back that the query is going to return only a single item, 
            //and then unwrap that item out of the enumerable and return it as it's type

            //This actually returns an IEnumerable of User, despite the fact it's a unique query
            var single = users.Where(u => u.Id == 5);

            foreach (User user in single) Console.WriteLine(user.DisplayName);

            //What if we didn't want an IEnumerable of user? Single has an overload that takes a predicate, so we don't have to use a where clause...
            User realSingle = users.Single(u => u.Id == 5);
            Console.WriteLine("Still " + realSingle.DisplayName);

            //What happens if we return a user that doesn't exist? Single throws an exception
            //realSingle = users.Single(u => u.Id == -5); 

            //There's another method called SingleOrDefault; return a single item, or if no items are returned give us a default value (in this case, null)
            realSingle = users.SingleOrDefault(u => u.Id == -5);
            if (realSingle == null) Console.WriteLine("No users returned!!\n");

            //What happens if SingleOrDefault returns multiple values? It throws another InvalidOperation exception...
            //realSingle = users.SingleOrDefault(u => u.Id == 5 || u.Id == 50);

            //What do we do if we just want to match a single item from a list?
            var first = users.First(u => u.Id == 5 || u.Id == 2);
            Console.WriteLine(first.DisplayName);

            //Just like Single, we have a OrDefault method for First....
            first = users.FirstOrDefault(u => u.Id == -5);
            if (first == null) Console.WriteLine("No users found!!\n");

            //What if we wanted a single item from the list, but we wanted the last item, not the first?
            var last = users.LastOrDefault(u => u.Id == 5 || u.Id == 2);
            Console.WriteLine(last.DisplayName);

            //Wouldn't it be useful if we could perform an arbitrary item in the list?
            var element = users.Where(u => u.Id == 5 || u.Id == 2).ElementAt(1);
            Console.WriteLine(element.DisplayName);

            //Also has an OrDefault method...

            //Can also get a null reference back if a query returns no results...
            var empty = users.Take(10).DefaultIfEmpty();

            foreach (User user in empty) Console.WriteLine(user.DisplayName);

            Console.WriteLine();

            //You can also specify a default to avoid a null reference exception...
            empty = users.Take(0).DefaultIfEmpty(new User());

            #endregion 

            #region Set Operations

            //Distinct operator is useful for creating unique lists of properties; say we wanted to see what badges user id 5 has earned....
            var badges = users.Single(u => u.Id == 108).Badges.Select(b => b.Name).OrderBy(bn => bn);

            //But stackoverflow issues duplicate badges... for a unique list we'll need to use Distinct
            foreach (var badge in badges) Console.WriteLine(badge);

            badges = users.Single(u => u.Id == 108).Badges.Select(b => b.Name).Distinct().OrderBy(bn => bn);

            Console.WriteLine();

            foreach (var badge in badges) Console.WriteLine(badge);

            Console.WriteLine();

            //Contains is your basic check if a sequence contains a given value...
            var contains = badges.Contains("Nice Answer");
            Console.WriteLine(contains + "\n");

            //Intersect, Except and Union all work better if we have two sets...

            var setOne = users.Single(u => u.Id == 108).Badges.Select(b => b.Name);
            var setTwo = users.Single(u => u.Id == 2).Badges.Select(b => b.Name);

            //Gives us the unique list of badges in both sets
            var badgeIntersect = setOne.Intersect(setTwo);

            foreach (var badge in badgeIntersect) Console.WriteLine(badge);

            Console.WriteLine();

            //Except method will give you all the values that are in one sequence that are not present in a second.
            //This is useful when you need to remove a set of items from another set. This query shows you all the badges
            //user 108 has earned that user 2 hasn't...
            var setOneExcept = setOne.Except(setTwo);

            foreach (var badge in setOneExcept) Console.WriteLine(badge);

            Console.WriteLine();

            //Union does the opposite. It gives you a unique list of all items in both sequences
            var badgeUnion = setTwo.Union(setOne);

            foreach (var badge in badgeUnion) Console.WriteLine(badge);

            Console.WriteLine();

            //What if you wanted to take all the badges both users have earned, except the ones they've both earned...
            //So, all the badges one person has earned that the other person hasn't. The brag badges, so to speak...
            var bragBadges = setOne.Union(setTwo).Except(setOne.Intersect(setTwo));

            foreach (var badge in bragBadges) Console.WriteLine(badge);

            Console.WriteLine();

            #endregion

            #region SelectMany

            //Basics of select many: take a sequence of items, and project each of those items into its own sequence,
            //and finally concatenate those sequences into a final result sequence

            users = users.Where(u => u.Id == 5 || u.Id == 108);

            //Basically taking each user and returning a list of items based on that user
            var allBadges =  users.SelectMany(u => u.Badges);

            foreach (Badge badge in allBadges) Console.WriteLine(badge.Name);

            Console.WriteLine();

            //Say we wanted to see which users won which badges? There's an overload of SelectMany which lets us
            //pass the original type and what we selected from it into another method, so we can combine bits of them
            var allUsersAndBadges = users.SelectMany(u => u.Badges, (u, b) => u.DisplayName + " " + b.Name);

            foreach (var userBadge in allUsersAndBadges) Console.WriteLine(userBadge);

            Console.WriteLine();

            //Interesting use of SelectMany: say you already have a list that has subsequences in it already, a list of 
            //lists, we could use SelectMany without performing any projection to get a single IEnumerable of all items in all sequences
            var cats = new[] { new[] { "Fluffy", "Mittens", "Lil Bandit" }, new[] { "Mister Kitty", "Catdog", "Chairman Meow" } };

            var allCats = cats.SelectMany(c => c);

            foreach (var cat in allCats) Console.WriteLine(cat);

            //SelectMany becomes powerful when you start chaining them together... say, to go from a list of users, to a list
            //of badges, to a list of badge names...
            var badgeNames = users.SelectMany(u => u.Badges).SelectMany(b => b.Name);

            //Except whoops, we've now enumerated down to a collection of chars - badge.Name is a string, essentially an IEnumerable<char>
            foreach (var badge in badgeNames) Console.WriteLine(badge);

            Console.WriteLine();

            #endregion

            Console.ReadKey();
        }
    }
}
