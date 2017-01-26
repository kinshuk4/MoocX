/**
 * PrimeNumbersMgmtMBean.java
 *
 * Created on Mon Jun 13 13:17:12 CDT 2005
 */
package primenumbers.mbeans;

import java.awt.Color;

/**
 * Interface PrimeNumbersMgmtMBean
 * View/Control attributes
 * @author gs145266
 */
public interface PrimeNumbersMgmtMBean
{
   /**
    * Get Milliseconds between status updates
    */
    public int getRefreshInterval();

   /**
    * Set Milliseconds between status updates
    */
    public void setRefreshInterval(int value);
    
    public String getResultColor();
    
    public void setResultColor(String resultColor);

}
