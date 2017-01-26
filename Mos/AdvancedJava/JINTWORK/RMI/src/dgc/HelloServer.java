package dgc;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Date;

public class HelloServer extends UnicastRemoteObject implements HelloInterface {

   public HelloServer() throws RemoteException{
      super();
   }

   public MsgInterface getMsg() throws RemoteException, Exception {
      return (MsgInterface)new MsgServer();
   }
}

