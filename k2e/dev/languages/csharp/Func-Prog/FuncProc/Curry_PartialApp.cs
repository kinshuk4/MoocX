using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FuncProc
{
    public class Book
    {
        public string Name { get; set; }
        public string Author { get; set; }
        public double Price { get; set; }
        public string Category { get; set; }

        public Book(string name, string author, double price, string category)
        {
            Name = name;
            Author = author;
            Price = price;
            Category = category;
        }
    }

    public class Curry_PartialApp
    {
        public static void RunSample()
        {
            var bk = new Book("Expert F#", "Don Syme", 55.99, "Computer Science");

            const double discountRate = 0.1;
            const double taxRate = 0.055;
            const double promotionCode = 0.05;

            //our base multiplication function
            Func<double, double, double> multiply = (x, y) => x * y;

            //so now we've got a basic multiplication function. we can call it with two arguments, like this:
            var number = multiply(2, 3);

            //we could use this to calculate our discount/promotion rates if we wanted...
            var priceWithDiscount = multiply(bk.Price, 1 - discountRate);
            var priceWithDiscountAndPromotion = multiply(priceWithDiscount, 1 - promotionCode);
            var priceWithDiscountAndPromotionAndTax = multiply(priceWithDiscountAndPromotion, 1 + taxRate);

            //we probably wouldn't do it like that of course. we might do something more like this:
            var finalPrice = multiply(1 + taxRate, multiply(1-promotionCode, multiply(1 - discountRate,bk.Price)));

            //bleh. in OO, this is the type of logic we'd encapsulate behind a class. with functional constructs, we can encapsulate/compose
            //behaviors with our functions! what we need are functions for each step in this process, and we can get them with partial application

            //All the generics make the signatures confusing... but take a look at the Apply operator. We call Apply on a Func<T1, T2, TResult>
            //We pass in an argument of T1 - kind of like this: someFunc<T1,T2,TResult>.Apply(T1 someArg). This will return another
            //function, that will look sort of like this: newFunc<someArg,T2,TResult>; so someArg is now "stuck" to newFunc, and newFunc is now just expecting 
            //a single argument, which it will apply to the argument we've fixed to the function

            var calcDiscountedRate = multiply.Apply(1 - discountRate);
            var calcPromotionalRate = multiply.Apply(1 - promotionCode);
            var calcTax = multiply.Apply(1 + taxRate);

            /*so now we've got a set of functions representing the full set of operations we want to apply to get a book's final price.
             *We've made them by "sticking" their first arguments to our constants. Neato! now we can call each of them like this, with a single argument: */
            priceWithDiscount = calcDiscountedRate(bk.Price);

            //and we can also chain them together like this:
            calcTax(calcPromotionalRate(calcDiscountedRate(bk.Price)));

            //that's all well and good, but what would be SUPER SWEET is if we could COMPOSE all these into one function, chain them all 
            //into a single pipeline we could use to get our net price... oh wait we can:

            var calcNetPrice = calcDiscountedRate
                .ForwardCompose(calcPromotionalRate)
                .ForwardCompose(calcTax);

            var netPrice = calcNetPrice(bk.Price);

            //we call ForwardCompose on Func, kind of like Apply... it'll bascially call functions on the results of other functions, like our standard chain above
            //we can easily add more functions into this pipeline if we have to

            //we can accomplish the same thing with Curry. First we "curry" our multiply function...
            var curriedMult = multiply.Curry();

            //this gives us a function we can call with one argument at a time:
            calcDiscountedRate = curriedMult(1 - discountRate);
            calcPromotionalRate = curriedMult(1 - promotionCode);
            calcTax = curriedMult(1 + taxRate);

            calcNetPrice = calcDiscountedRate
                .ForwardCompose(calcPromotionalRate)
                .ForwardCompose(calcTax);
        }
    }

    public static class FuncExtensions
    {
        public static Func<TArg1, Func<TArg2, TResult>> Curry<TArg1, TArg2, TResult>(
            this Func<TArg1, TArg2, TResult> func)
        {
            return arg1 => arg2 => func(arg1, arg2);
        }

        public static Func<TArg2, TResult> Apply<TArg1, TArg2, TResult>(
            this Func<TArg1, TArg2, TResult> func, TArg1 arg1)
        {
            return arg2 => func(arg1, arg2);
        }

        public static Func<TSource, TResult> ForwardCompose<TSource, TIntermediate, TResult>(
            this Func<TSource, TIntermediate> func1, Func<TIntermediate, TResult> func2)
        {
            return source => func2(func1(source));
        }
    }
}
