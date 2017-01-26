package nio.ftpserver;

import java.nio.channels.SelectionKey;

public interface Parser {
	public void parse(SelectionKey key);
}
