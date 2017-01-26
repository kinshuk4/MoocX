package simple.multicast;

// MulticastChatFrame.java

import java.io.IOException;
import java.net.InetAddress;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

// A swing-based user interface to a MulticastChat session

public class MulticastChatFrame extends JFrame implements ActionListener, 
        WindowListener, MulticastChatEventListener {

  // The multicast chat object
  protected MulticastChat chat;

  // Text area used to log chat join, leave and chat messages received
  protected JTextArea textArea;

  // Scroll pane used for the text area (used to auto-scroll)
  protected JScrollPane textAreaScrollPane;

  // The text field used for message entry
  protected JTextField messageField;

  // Button used to transmit messageField data
  protected JButton sendButton;

  // Constructs a new swing multicast chat frame (in unconnected state)
  public MulticastChatFrame() {
    super("MulticastChat (unconnected)");

    // Construct GUI components (before session)
    textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setBorder(BorderFactory.createLoweredBevelBorder());

    textAreaScrollPane = new JScrollPane(textArea);
    getContentPane().add(textAreaScrollPane, BorderLayout.CENTER);

    JPanel messagePanel = new JPanel();
    messagePanel.setLayout(new BorderLayout());

    messagePanel.add(new JLabel("Message:"), BorderLayout.WEST);

    messageField = new JTextField();
    messageField.addActionListener(this);
    messagePanel.add(messageField, BorderLayout.CENTER);

    sendButton = new JButton("Send");
    sendButton.addActionListener(this);
    messagePanel.add(sendButton, BorderLayout.EAST);

    getContentPane().add(messagePanel, BorderLayout.SOUTH);

    // detect window closing and terminate multicast chat session
    addWindowListener(this);
  }

  // Configures the multicast chat session for this interface
  public void join(String username, InetAddress group, int port, 
                   int ttl) throws IOException {
    setTitle("MulticastChat " + username + "@" + group.getHostAddress() 
             + ":" + port + " [TTL=" + ttl + "]");

    // create multicast chat session
    chat = new MulticastChat(username, group, port, ttl, this);
  } 

  protected void log(final String message) {
    java.util.Date date = new java.util.Date();

    /**
     * Must invoke on swing thread since we're invoked from the
     * action listener methods in the context of the MulticastChat
     * receive thread
     */
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        textArea.append(message + "\n");
        textAreaScrollPane.getVerticalScrollBar()
          .setValue(textAreaScrollPane.getVerticalScrollBar().getMaximum());
      } 
    });
  } 

  public void actionPerformed(ActionEvent e) {

    if ((e.getSource().equals(messageField)) 
            || (e.getSource().equals(sendButton))) {

      String message = messageField.getText();
      messageField.setText("");

      try {
        chat.sendMessage(message);
      } catch (Throwable ex) {
        JOptionPane.showMessageDialog(this, 
                                      "Error sending message: " 
                                      + ex.getMessage(), "Chat Error", 
                                      JOptionPane.ERROR_MESSAGE);
      } 
    } 
  } 

  // Invoked the first time a window is made visible.
  public void windowOpened(WindowEvent e) {
    messageField.requestFocus();
  } 

  // On closing, terminate multicast chat
  public void windowClosing(WindowEvent e) {
    try {
      if (chat != null) {
        chat.terminate();
      } 
    } catch (Throwable ex) {
      JOptionPane.showMessageDialog(this, 
                                    "Error leaving chat: " 
                                    + ex.getMessage(), "Chat Error", 
                                    JOptionPane.ERROR_MESSAGE);
    } 
    dispose();
  } 
  public void windowClosed(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}

  // Invoked by the MulticastChat receive thread when a message has arrived
  public void chatMessageReceived(String username, InetAddress address, 
                                  int port, String message) {
    log(username + ": " + message);
  } 

  // Invoked by the MulticastChat receive thread when a user has joined
  public void chatParticipantJoined(String username, InetAddress address, 
                                    int port) {
    log("+++ " + username + " has joined from " + address.getHostName() 
        + ":" + port);
  } 

  // Invoked by the MulticastChat receive thread when a user has left
  public void chatParticipantLeft(String username, InetAddress address, 
                                  int port) {
    log("--- " + username + " has left from " + address.getHostName() + ":" 
        + port);
  } 

  // Command-line invocation expecting three arguments
  public static void main(String[] args) {
    if ((args.length != 3) && (args.length != 4)) {
      System.err.println("Usage: MulticastChatFrame " 
                         + "<username> <group> <port> { <ttl> }");
      System.err.println("       - default time-to-live value is 1");
      System.exit(1);
    } 

    String username = args[0];
    InetAddress group = null;
    int port = -1;
    int ttl = 1;

    try {
      group = InetAddress.getByName(args[1]);
    } catch (Throwable e) {
      System.err.println("Invalid multicast group address: " 
                         + e.getMessage());
      System.exit(1);
    } 

    if (!group.isMulticastAddress()) {
      System.err.println("Group argument '" + args[1] 
                         + "' is not a multicast address");
      System.exit(1);
    } 

    try {
      port = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
      System.err.println("Invalid port number argument: " + args[2]);
      System.exit(1);
    } 

    if (args.length >= 4) {
      try {
        ttl = Integer.parseInt(args[3]);
      } catch (NumberFormatException e) {
        System.err.println("Invalid TTL number argument: " + args[3]);
        System.exit(1);
      } 
    } 

    try {
      MulticastChatFrame frame = new MulticastChatFrame();
      frame.setSize(400, 150);

      frame.addWindowListener(new WindowAdapter() {
        public void windowClosed(WindowEvent e) {
          System.exit(0);
        } 
      });

      frame.show();

      frame.join(username, group, port, ttl);
    } catch (Throwable e) {
      System.err.println("Error starting frame: " + e.getClass().getName() 
                         + ": " + e.getMessage());
      System.exit(1);
    } 
  } 
}
