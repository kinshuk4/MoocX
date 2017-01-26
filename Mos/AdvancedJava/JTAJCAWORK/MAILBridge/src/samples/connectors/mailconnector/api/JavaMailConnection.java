
package samples.connectors.mailconnector.api;

import java.util.List;


import javax.mail.MessagingException;
import javax.resource.ResourceException;

public interface JavaMailConnection
{
    public List<String> getToaddresslist();
    public void addToAddress(String toaddress);
    public void setToAddress(String toaddress);
    public void removeFromToAddress(String toaddress);
    public void removeAllToAddresses();
    public String getFromaddress();
    public void setFromaddress(String fromaddress);
    public String sendMail(String messageSubject,Object message,String contentType) throws MessagingException,ResourceException;
    public String sendMail(String messageSubject,Object message) throws MessagingException,ResourceException;
    public String sendMail(String messageSubject,Object message,String messageId,String replyTo,String inReplyTo) throws MessagingException,ResourceException;
    public String sendMail(String messageSubject,Object message,String contentType,String messageId,String replyTo,String inReplyTo) throws MessagingException,ResourceException;
    public void close()throws ResourceException;
}
