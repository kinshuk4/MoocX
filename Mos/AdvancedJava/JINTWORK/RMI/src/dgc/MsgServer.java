package dgc;

import java.io.Serializable;
import java.rmi.server.*;
import java.rmi.*;

public class MsgServer extends UnicastRemoteObject
             implements MsgInterface, Serializable, Unreferenced{

   // Set a counter for the number of instances of this class that are created.
   private static int counter;
   // Hold an id for the object instance
   private int id;

   public MsgServer() throws RemoteException {
      super();
      System.out.println("Created Msg:" + counter);
      counter++;
      setId(counter);
   }

   public void finalize() throws Throwable {
      super.finalize();
      System.out.println( "Finalizer called for Msg: " + id );
   }

   public void unreferenced(){
      System.out.println("The unreferenced()method called for Msg: " + id );
      // If we need we can call unexportObject here since no one is using it
      // unexportObject(this, true);
   }

   private void setId(int id){
      this.id=id;
   }
}
