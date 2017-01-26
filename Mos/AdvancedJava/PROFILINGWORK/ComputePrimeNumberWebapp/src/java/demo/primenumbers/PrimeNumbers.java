/*
 * PrimeNumbers.java
 *
 * Created on June 10, 2005, 2:57 PM
 */

package demo.primenumbers;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * Calculates prime numbers using the sieve of Eratosthenes.
 *
 */
public class PrimeNumbers {
    
    private Map<int[], int[]> cache_ = new HashMap<int[], int[]>(100);
    private int maxValue_;
    private int[] primes_;
    int primeCandidate_;
    
    public PrimeNumbers() {
    }
    
    public int calculate(int maxValue) {
        // trivial un-optimzed algorithm
        
        this.maxValue_ = maxValue;
                
        // allocate the array and fill with all candidate numbers 
        int[] candidates_ = new int[maxValue_-1];
        for (int i = 0; i < candidates_.length; i++)
            candidates_[i] = i + 2;
        
        // inefficient way to eliminate all non-prime numbers:
        // starting from the candidate, test whether 
        // it can be divided without rest by a smaller number.
        for (int i = 0; i < candidates_.length; i++) {
            primeCandidate_ = candidates_[i];
            
            for (int j = 2; j <= i/2; j++) {
                if ( primeCandidate_ % j == 0  )
                {
                    candidates_[i] = -1;
                    break;
                }
            }
        }
        
        // store results.
        // count the number of prime numbers
        int count = 0;
        for (int i = 0; i < candidates_.length; i++) {
            if (candidates_[i] != -1) {
                count++;
            }
        }
        
        // copy the prime numbers to the result array
        primes_ = new int[count];
        for (int resultIndex = 0, i = 0; i < candidates_.length; i++) {
            if (candidates_[i] != -1) {
                primes_[resultIndex++] = candidates_[i];
            }
        }
                
        // update the user
        return primes_[primes_.length-1];
    }
    
    public int calculate2(int maxValue) {
        // efficient algorithm
        this.maxValue_ = maxValue;
        
        // previous results are cashed
        int[] primes = (int[]) cache_.get(maxValue_);
        if (primes != null) {
            // we've found a match - no need to recalc.
            return primes[primes.length-1];
        }
        
        // else allocate the array and fill with all candidate numbers up to max
        int[] candidates_ = new int[maxValue_-1];
        for (int i = 0; i < candidates_.length; i++)
            candidates_[i] = i + 2;
        
        // efficiently eliminate all non-prime numbers
        int startPosition = 0;
        while (true) {
            primeCandidate_ = candidates_[startPosition];
            
            // if we've made it up to a candidate that is
            // greater than the square root of the max. value, then
            // by Eratosthenes' definition, we're done.
            if (primeCandidate_ > Math.sqrt(maxValue_))
                break;
            
            // if we've already determined that this entry is not
            // prime then we can skip it
            if (primeCandidate_ == -1) {
                startPosition++;
                continue;
            }
            
            // starting from the first multiple of the current candidate,
            // eliminate all multiples of this candidate.
            for (int i = startPosition + primeCandidate_;
            i < candidates_.length;
            i+= primeCandidate_) {
                candidates_[i] = -1;
            }
            startPosition++;
        }
        
        // store results.
        // count the number of prime numbers
        int count = 0;
        for (int i = 0; i < candidates_.length; i++) {
            if (candidates_[i] != -1) {
                count++;
            }
        }
        
        // copy the prime numbers to the result array
        primes_ = new int[count];
        for (int resultIndex = 0, i = 0; i < candidates_.length; i++) {
            if (candidates_[i] != -1) {
                primes_[resultIndex++] = candidates_[i];
            }
        }
        
        // store this off for possible later use (caching)
        cache_.put(primes_, primes_);
        
        // update the user
        return primes_[primes_.length-1];
    }
}
