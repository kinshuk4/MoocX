package nio.ftpserver;

import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

class DataIO
{
	private static Logger logger=Logger.getLogger("nio.ftpserver");
	
	
   public static String readFixedString(int size, CharBuffer in) 
   {  
      StringBuilder b = new StringBuilder(size);
      int i = 0;
      boolean more = true;
      while (more && i < size)
      {  
        char ch = in.get();
         i++;
         if (ch == 0) more = false;
         else b.append(ch);
      }
      //in.(2 * (size - i));
      
      return b.toString();
   }

   public static String writeFixedString(String s,int size) 
   {
      int i;
      StringBuilder sb=new StringBuilder();
      for (i = 0; i < size; i++)
      {  
         char ch = 0;
         if (i < s.length()) ch = s.charAt(i);
         sb.append(ch);
         
      }
      logger.info("String length :: "+ sb.toString().length());
      logger.info("String Byte length :: "+ sb.toString().getBytes().length);
      logger.info("String :: "+ sb.toString());
      return sb.toString();
   }
   static{
		logger.setLevel(Level.CONFIG);
	}
}

