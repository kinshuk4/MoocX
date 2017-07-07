using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using StackOverflowDumpCodeBuilder;


namespace EX_4___OrderBy
{
    class Ex4
    {
        static void Main(string[] args)
        {
            var users = EntityMapper.LoadUsers();
            var results = users.Where(u => u.Age > 0).OrderByDescending(u => u.DisplayName.Length);

            foreach (var user in results)
            {
                Console.WriteLine(user.DisplayName + " " + user.Age);
            }
       
            Console.ReadKey();
        }
    }
}
