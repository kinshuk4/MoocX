package references;

import java.lang.ref.*;

class VeryBig {
  private static final int SZ = 1;
  private double[] d = new double[SZ];
  private String ident;
  public VeryBig(String id) { ident = id; }
  public String toString() { return ident; }
  public void finalize() {
    System.out.println("Finalizing " + ident);
  }
}

public class References {
  private static ReferenceQueue rq = new ReferenceQueue();
  public static void checkQueue() {
    Object inq = rq.poll();
    if(inq != null)
      System.out.println("In queue: " +
        (VeryBig)((Reference)inq).get());
  }
  public static void main(String[] args) {
    int size = 10;
    // Or, choose size via the command line:
    if(args.length > 0)
      size = Integer.parseInt(args[0]);
    /*[] sa = new SoftReference[size];
    for(int i = 0; i < sa.length; i++) {
      sa[i] = new SoftReference(
        new VeryBig("Soft " + i), rq);
      System.out.println("Just created SOFT:: " +
        (VeryBig)sa[i].get());
      checkQueue();
    }*/
    WeakReference[] wa = new WeakReference[size];
    for(int i = 0; i < wa.length; i++) {
      wa[i] = new WeakReference(
        new VeryBig("Weak " + i), rq);
      System.out.println("Just created WEAK:: " +
        (VeryBig)wa[i].get());
      checkQueue();
    }
    /*SoftReference s =
      new SoftReference(new VeryBig("Soft"));
    WeakReference w =
      new WeakReference(new VeryBig("Weak"));*/
    //System.gc();
    /*PhantomReference[] pa = new PhantomReference[size];
    for(int i = 0; i < pa.length; i++) {
      pa[i] = new PhantomReference(
        new VeryBig("Phantom " + i), rq);
      System.out.println("Just created PHANTOM:: " +
        (VeryBig)pa[i].get());
      checkQueue();
    }*/
  }
} ///:~



