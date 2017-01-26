package callbacks;

public interface MobileInterface extends java.rmi.Remote {
   void messageArrived(String msg) throws java.rmi.RemoteException;
}
