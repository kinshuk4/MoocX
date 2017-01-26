package nio.ftpserver;



import java.io.Serializable;

public abstract class TransferModes implements Serializable {
	private final transient String name;
	protected TransferModes(String name) { 
		this.name = name;
	}
//	 Perform arithmetic operation represented by this constant 
	
	public static final TransferModes CHANNELTOCHANNEL = new TransferModes("CHANNELTOCHANNEL") {
	};
	public static final TransferModes MEMORYMAPPED = new TransferModes("MEMORYMAPPED") {
	};
	public static final TransferModes SCATTERGATHER = new TransferModes("SCATTERGATHER") {
	};
	
}