package misc;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class TestProperties {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		URL o=new TestProperties().getClass().getResource("Test.java");
		
		System.out.println(o);
		System.out.println(o.getFile());
		System.out.println(o.getContent());
		System.out.println(o.getProtocol());
		//System.out.println(o.);
		URLConnection c=o.openConnection();
		System.out.println(c.getHeaderFields());
	}

}
