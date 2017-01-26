/*
 * LeakThread.java
 *
 * Created on June 27, 2005, 9:05 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package demo.memoryleak;

public class LeakThread extends Thread {
    private java.util.HashMap<float[], double[]> map = 
                new java.util.HashMap<float[], double[]> ();
    public boolean stop = false;
    public void run () {
        stop = false;
        
        //////////////////////////////////////////////
        while (!stop) {
            try {
                map.put (new float[1], new double [1]);
                Thread.sleep (100);
                System.gc ();
            } catch (InterruptedException e) {
                return;
            }
        }
        //////////////////////////////////////////////
    }
}
