package oio;

/**
   @version 1.10 17 Aug 1998
   @author Cay Horstmann
*/


import java.io.*;
import java.util.*;

class ObjectRefTest
{  
   public static void main(String[] args)
   {  
      Employee1 harry = new Employee1("Harry Hacker", 50000, 1989, 10, 1);
      Manager1 boss = new Manager1("Carl Cracker", 80000, 1987, 12, 15);
      boss.setSecretary(harry);

      Employee1[] staff = new Employee1[3];

      staff[0] = boss;
      staff[1] = harry;
      staff[2] = new Employee1("Tony Tester", 40000, 1990, 3, 15);
      
      try
      {  
         // save all employee records to the file employee.dat
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee1.dat"));
         out.writeObject(staff);
         out.close();

         // retrieve all records into a new array
         ObjectInputStream in =  new ObjectInputStream(new FileInputStream("employee1.dat"));
         Employee1[] newStaff = (Employee1[]) in.readObject();
         in.close();

         // raise secretary's salary
         newStaff[1].raiseSalary(10); 

         // print the newly read employee records
         for (Employee1 e : newStaff)
            System.out.println(e);
      }
      catch (Exception e)
      {  
         e.printStackTrace(); 
      }
   }
}

class Employee1 implements Serializable
{
   public Employee1() {}

   public Employee1(String n, double s, int year, int month, int day)
   {  
      name = n;
      salary = s;
      GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
      hireDay = calendar.getTime();
   }

   public String getName()
   {  
      return name;
   }

   public double getSalary()
   {  
      return salary;
   }

   public Date getHireDay()
   {  
      return hireDay;
   }

   public void raiseSalary(double byPercent)
   {  
      double raise = salary * byPercent / 100;
      salary += raise;
   }

   public String toString()
   {  
      return getClass().getName()
         + "[name=" + name
         + ",salary=" + salary
         + ",hireDay=" + hireDay
         + "]";
   }

   private String name;
   private double salary;
   private Date hireDay;
}

class Manager1 extends Employee1
{  
   /**
      Constructs a Manager without a secretary
      @param n the employee's name
      @param s the salary
      @param year the hire year
      @param month the hire month
      @param day the hire day
   */
   public Manager1(String n, double s, int year, int month, int day)
   {  
      super(n, s, year, month, day);
      secretary = null;
   }

   /**
      Assigns a secretary to the manager.
      @param s the secretary
   */
   public void setSecretary(Employee1 s)
   {  
      secretary = s;
   }

   public String toString()
   {
      return super.toString()
        + "[secretary=" + secretary
        + "]";
   }

   private Employee1 secretary;
}

