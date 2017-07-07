using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;

namespace FuncProc
{
    class Program
    {
        static void Main(string[] args)
        {
            DoStuff();
            Curry_PartialApp.RunSample();
        }

        public static List<MemberInfo> GetAllMembers()
        {
            Assembly[] assemblies = AppDomain.CurrentDomain.GetAssemblies();
            List<Type> types = new List<Type>();

            foreach (Assembly assembly in assemblies)
                types.AddRange(assembly.GetTypes());

            List<MemberInfo> members = new List<MemberInfo>();

            foreach (Type type in types)
                members.AddRange(type.GetMembers());

            return members;
        }

        public static IEnumerable<MemberInfo> GetAllMembersFunctional()
        {
            return AppDomain.CurrentDomain.GetAssemblies()
                .SelectMany(assembly => assembly.GetTypes().SelectMany(type => type.GetMembers()));
        }

        static Func<int, int> Closures()
        {
            int baseVal = 10;

            Func<int, int> add = val => baseVal + val;
            Console.WriteLine(add(10));

            baseVal = 50;
            Console.WriteLine(add(10));

            return add;
        }

        static IEnumerable<TResult> Map<TSource, TResult>(Converter<TSource, TResult> function, IEnumerable<TSource> list)
        {
            foreach (TSource sourceVal in list)
                yield return function(sourceVal);
        }

        static IEnumerable<T> Filter<T>(Predicate<T> predicate, IEnumerable<T> list)
        {
            foreach (T val in list)
                if (predicate(val))
                    yield return val;
        }

        static TResult Reduce<TSource, TResult>(Func<TResult, TSource, TResult> accumulator, TResult startVal, IEnumerable<TSource> list)
        {
            TResult result = startVal;
            foreach (TSource sourceVal in list)
                result = accumulator(result, sourceVal);
            return result;
        }

        static void Currying()
        {
            Func<int, int, int> add =
                delegate(int x, int y)
                {
                    return x + y;
                };

            Console.WriteLine(add(10, 20));

            Func<int, Func<int, int>> curriedAdd =
                delegate(int x)
                {
                    return delegate(int y)
                    {
                        return add(x, y);
                    };
                };

            Func<int, int> add5 = curriedAdd(5);
            Console.WriteLine(add5(27));

            Func<int, int> add7 = curriedAdd(7);

            Func<int, int, int> autoAdd = (x, y) => x + y;

            var addCurriedAuto = Curry(add);

            var someNum = addCurriedAuto(5)(10);

            var add10 = addCurriedAuto(10);

            Func<int, string, int> wierdCalc =
                (val, text) => val * text.Length;

            var wierdCalcCurried = Curry(wierdCalc);

            var wierdCalc3 = wierdCalcCurried(3);
        }

        static void Composition()
        {
            Func<int, int> add5 = val => val + 5;
            Func<int, int> triple = val => val * 3;
            var add5AndTriple = Compose(triple, add5);
        }

        static Func<TSource, TEndResult> Compose<TSource, TIntermediateResult, TEndResult>(Func<TSource, TIntermediateResult> func1, Func<TIntermediateResult, TEndResult> func2)
        {
            return sourceParam => func2(func1(sourceParam));
        }

        static void DoStuff()
        {
            //foreach(int item in Sequence<int>(x=> x + 1, 0, val => val >= 10))
            //    Console.WriteLine(item);
            //Console.ReadLine();

            //var sum = Reduce((result, newval) => result + newval, 0, Sequence(cur => cur + 2, 1, val => val + 2 > 10));
            //Console.WriteLine(sum);

            var reduce = TypedReduce<int>();
            var curriedReduce = Curry(reduce);
            var sequence = TypedSequence<int>();
            var curriedSequence = Curry(sequence);

            var sumCalculator =
                curriedReduce((result, newval) => result + newval)(0);

            Func<int, int> nextValueGenerator = cur => cur + 2;

            var oddNumbersSequence = curriedSequence(nextValueGenerator)(1);
        }

        static IEnumerable<T> Sequence<T>(Func<T, T> getNext, T start, Func<T, bool> endChecker)
        {
            if (getNext == null)
                yield break;

            yield return start;

            T val = start;

            while (endChecker == null || !endChecker(val))
            {
                val = getNext(val);
                yield return val;
            }
        }

        static Func<Func<T, T, T>, T, IEnumerable<T>, T> TypedReduce<T>()
        {
            return Reduce<T, T>;
        }

        static Func<Func<T,T>, T, Func<T,bool>, IEnumerable<T>> TypedSequence<T>()
        {
            return Sequence<T>;
        }

        static Func<T1, Func<T2, T3>> Curry<T1, T2, T3>(Func<T1, T2, T3> func)
        {
            return par1 => par2 => func(par1, par2);
        }

        static Func<T1, Func<T2, Func<T3, T4>>> Curry<T1, T2, T3, T4>(Func<T1, T2, T3, T4> func)
        {
            return par1 => par2 => par3 => func(par1, par2, par3);
        }
    }
}
