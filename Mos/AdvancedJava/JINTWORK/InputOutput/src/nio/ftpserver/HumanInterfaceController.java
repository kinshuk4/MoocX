package nio.ftpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HumanInterfaceController {
	private static Logger logger=Logger.getLogger("nio.ftpserver");
	private static HumanInterfaceController hic=null;
	BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
	
	public void queryTransferParameters(TranferParameters tp){
		tp.reset();
		System.out.println("PLease provide the parameters of file(s) transfer");
		this.queryTransferMode(tp);
		this.queryFileNames(tp);		
	}
	protected void queryTransferMode(TranferParameters tp){
		System.out.println("Enter the mode?");
		System.out.println("u				::Uploads a file to server");
		System.out.println("d				::Downloads a file from server");
		try {
			tp.setMode(in.readLine());
			logger.info(tp.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void queryFileNames(TranferParameters tp){
		System.out.println("Enter the filename with extension and press ENTER");
		System.out.println("if there are more than one file then repeat the above process.");
		System.out.println("When you are done then type 'quit' on the console [q]");
		System.out.println("empty ENTER");
		String filename=null;
		try {
			while ((filename=in.readLine())!=null) {
				if (filename.equalsIgnoreCase("quit"))
					break;
				tp.addFiles(filename);
				logger.info(tp.toString());
				System.out.println("Any other file? or type quit if you are done");
			}
			System.out.println("Would you want to transfer more files later?");
			System.out.println("y			:: The program will not exit");
			System.out.println("n			:: The program will exit");
			String linger=in.readLine();
			if (linger.equalsIgnoreCase("y")) {
				tp.setLinger(true);
			}
			else if (linger.equalsIgnoreCase("n")) {
				tp.setLinger(false);
			}
			else{
				System.out.println("You cant be helped..");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private HumanInterfaceController(){}
	/*
	 * A static factory method.There is really no need to have another
	 * instance of this object.
	 */
	public static HumanInterfaceController getInstance() {
		if (hic==null) {
			hic=new HumanInterfaceController();
		}
		return hic;
	}
	static{
		logger.setLevel(Level.CONFIG);
	}
}
