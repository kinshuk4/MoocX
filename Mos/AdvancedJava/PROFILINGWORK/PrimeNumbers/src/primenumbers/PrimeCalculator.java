package primenumbers;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;



/**
 *
 * Calculates prime numbers using the sieve of Eratosthenes.
 *
 *  Eratosthenes
 *
 *  
 *
 *  Runs on a separate thread in order to avoid tying up the
 * Event Dispatch Thread.
 */
class PrimeCalculator extends SwingWorker {
    
    private final PrimeNumbers primeNumbers;
    
    private int maxValue_;
    
    private int maxInt_;
    
    private int maxNext_;
    
    private int[] primes_;
    
    int examining_;
    
    int primeCandidate_;

    private int maxInt;
    
    PrimeCalculator(PrimeNumbers primeNumbers, int startTypingTheNewName) {
        this.primeNumbers = primeNumbers;
        this.maxValue_ = startTypingTheNewName;
        ArrayList myList = new ArrayList(10);
    }
    
    public Object construct() {
        Thread me = Thread.currentThread();
        me.setName("Calculate Primes for " + maxValue_);
        int[] candidates_ = new int[maxValue_-1];
        for (int i = 0; i < candidates_.length; i++)
            candidates_[i] = i + 2;
        int startPosition = 0;
        while (true) {
            primeCandidate_ = candidates_[startPosition];
            examining_ = startPosition + 2;
            myMethod();
            if (primeCandidate_ > Math.sqrt(maxValue_))
                break;
            
            // if we've already determined that this entry is not
            // prime then we can skip it
            if (primeCandidate_ == -1) {
                startPosition++;
                continue;
            }
            for (int i = startPosition + primeCandidate_; i < candidates_.length; i+= primeCandidate_)            {
                examining_ = i + 2;
                candidates_[i] = -1;
            }
            startPosition++;
            myMethod();
        }
        int count = 0;
        for (int i = 0; i < candidates_.length; i++)        {
            if (candidates_[i] != -1) {
                count++;
            }
        }
        if (maxValue_ <= 1000000) {
            this.primeNumbers.completeResults_.put(candidates_, candidates_);
        }
        persist(candidates_);        
        primes_ = new int[count];
        for (int resultIndex = 0, i = 0; i < candidates_.length; i++)        {
            if (candidates_[i] != -1) {
                primes_[resultIndex++] = candidates_[i];
            }
        }
        return null;
    }
    
    public void finished() {
        this.primeNumbers.processResult(maxValue_, primes_);
    }
    
    private void myMethod() {
        if (primes_ == null) return;
        for (int i = 0; i < primes_.length; i++) {
            if (primes_ != null) {
                int j = primes_[i];
                System.out.println("j" + j);
            }
        }

    }

    private static File[] files = new File[100];
    private static int count = 0;
    private void persist(int[] candidates_) {
        files[count++] = new File("tmpPrimeFile-"+count);
        for (int i = 0; i < 5000; i++) {
          //  System.out.println(i);
        }
    }

    public int getMaxNext_() {
        return maxNext_;
    }

    /** Sets the max value
     * @param val The new maximum value
     * */
    public void setMaxNext_(int val) {
        this.maxNext_ = val;
    }

    public int getMaxInt() {
        return maxInt;
    }

    public void setMaxInt(int val) {
        this.maxInt = val;
    }
}
