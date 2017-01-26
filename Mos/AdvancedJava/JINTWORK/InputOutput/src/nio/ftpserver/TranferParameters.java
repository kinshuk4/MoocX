package nio.ftpserver;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class TranferParameters {
	 
	private String mode=null;
	private List<String> files=new LinkedList<String>();
	private static TranferParameters tranferparameters=null;
	private boolean linger=false;
	
	/*
	 * A static factory method.There is really no need to have another
	 * instance of this object.
	 */
	public static TranferParameters getInstance(){
		if (tranferparameters==null){
			tranferparameters=new TranferParameters();
		}
		return tranferparameters;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<String> getFiles() {
		return files;
	}

	public void addFiles(String filename) {
		File f=new File(filename);
		if (f.exists()){
			files.add(filename);
		}
	}
	private TranferParameters(){}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mode+files;
	}

	public boolean isLinger() {
		return linger;
	}

	public void setLinger(boolean linger) {
		this.linger = linger;
	}
	public void reset(){
		mode=null;
		files.clear();
		linger=false;
	}
	
}
