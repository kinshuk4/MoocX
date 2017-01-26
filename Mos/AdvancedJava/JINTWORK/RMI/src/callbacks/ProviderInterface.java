package callbacks;

public interface ProviderInterface extends java.rmi.Remote {
   void register(String number,MobileInterface ref) throws java.rmi.RemoteException;
   void sendMessage(String number,String message) throws java.rmi.RemoteException;
} 
