package dynamiccompilation;



public class DynamicCompilationMain
{
    public static void main (String [] args)
    {
        // Create an ITimer using the Factory class:
        //final ITimer timer = TimerFactory.newTimer ();
        
        for (int repeat = 0; repeat < 100; ++ repeat)
        {
            //timer.start ();
        	Long start=System.nanoTime();
            sum (100);
            Long end=System.nanoTime();
            
            System.out.println (repeat + ": " + (end-start));
            //timer.reset ();
        }
    }
    
    synchronized public static int sum (int n)
    {
        if (n <= 1)
            return 1;
        else
            return n + sum (n - 1);
    }

} // End of class