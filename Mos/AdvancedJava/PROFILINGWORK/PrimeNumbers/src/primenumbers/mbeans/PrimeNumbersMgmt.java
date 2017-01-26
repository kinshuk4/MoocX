/**
 * PrimeNumbersMgmt.java
 *
 * Created on Mon Jun 13 13:17:12 CDT 2005
 */
package primenumbers.mbeans;
import java.awt.Color;
import javax.swing.Timer;
import primenumbers.PrimeNumbers;

/**
 * Class PrimeNumbersMgmt
 * View/Control attributes
 * @author gs145266
 */
public class PrimeNumbersMgmt implements PrimeNumbersMgmtMBean
{
    /** Attribute : the managed object */
    private PrimeNumbers managed_ = null;
    
   /* Creates a new instance of PrimeNumbersMgmt */
    public PrimeNumbersMgmt()
    {
    }

   /**
    * Get Milliseconds between status updates
    */
    public int getRefreshInterval()
    {
        return managed_.getTimer().getDelay();
    }
    
    public String getResultColor() 
    {
        return managed_.getResultColor();
    }
    
    public void setManaged(PrimeNumbers managed)
    {
        managed_ = managed;
    }

   /**
    * Set Milliseconds between status updates
    */
    public void setRefreshInterval(int value)
    {
        managed_.getTimer().setDelay(value);
    }
    
    public void setResultColor(String resultColor)
    {
        managed_.setResultColor(resultColor);
    }
    
 

}
