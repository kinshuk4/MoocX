package threads.pools.pool;

/**
*This is the exception class - it is thrown by the ThreadPool if the Class provided in the constructor
*is not an instanceof Runnable
*Author - Mohit Kumar<kumarmohit@vsnl.net>
*Last Modified - 10/03/2001
*/

public class IllegalClassException extends Exception{

public IllegalClassException(){

super("Class provided in the constructor is not an instanceof java.lang.Runnable");

}//constructor closing

public IllegalClassException(String messMessage){

super(messMessage);

}//constructor closing

public String toString(){

String messMessage=super.toString();
return messMessage;

}//toString closing

}//class closing
