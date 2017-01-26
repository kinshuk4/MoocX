package helloWorld;



public interface HelloInterface extends java.rmi.Remote {

   // This method is called by remote clients and is implemented by the 
   // remote object
   public String sayHello() throws java.rmi.RemoteException;
}
