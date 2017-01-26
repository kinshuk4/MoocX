package threads.pools.pool;


/**
*This is the exception class - it is thrown by the ThreadPool under different circumstances
*Author - Mohit Kumar<kumarmohit@vsnl.net>>
*Created - 9/03/2001.
*Last Modified - December 28th 2001.
*/

public class ClientsUnderServiceException extends Exception{

public ClientsUnderServiceException(){

super("Clients are under service in the ActivePool, this operation cannot be performed now");

}//constructor closing

public ClientsUnderServiceException(String messMessage){

super(messMessage);

}//constructor closing

public String toString(){

String messMessage=super.toString();
return messMessage;

}//toString closing

}//class closing


