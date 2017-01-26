package dgc;

import java.rmi.*;

public interface HelloInterface extends java.rmi.Remote {
   public MsgInterface getMsg() throws RemoteException, Exception;
}

