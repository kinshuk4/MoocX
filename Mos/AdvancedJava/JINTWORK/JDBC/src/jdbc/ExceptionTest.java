package jdbc;

public class ExceptionTest
{
	public static void main(String[] args) 
	{
		try{
		ConnectionClass c=new ConnectionClass();
		c.initialize();
		}catch (Exception e){e.printStackTrace();}
	}
};  
