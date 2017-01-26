/*
 * Performance.java
 *
 * Created on June 26, 2005, 10:13 AM
 */

package demo;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import demo.primenumbers.PrimeNumbers;

/** A demo main servlet.
 *
 * @author Ian Formanek, edited by Ruth Kusterer
 * @version 1.1
 */
public class Performance extends HttpServlet {

    PrimeNumbers calculator;
    
    private int number = -1;
    private int results = -1;
    private String fileName = null;
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        long timeMs = -1;
        boolean isOptimzed=false;
        if( request.getParameter("isOptimzed") != null )
            isOptimzed = ( request.getParameter("isOptimzed").equals("on") ? true : false );
            
        String num = request.getParameter("number");
        
        if (num != null) {
            number = Integer.parseInt ((String)num);
            timeMs = System.currentTimeMillis();
            results = doCountPrimeNumbers (number, isOptimzed);
            timeMs = System.currentTimeMillis() - timeMs;
        } else {
            fileName = request.getParameter("file");
            if (fileName != null) {
                timeMs = System.currentTimeMillis();
                results = doCountFiles(fileName);
                timeMs = System.currentTimeMillis() - timeMs;
            }
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Profiler Demo -- Performance</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>Servlet MainServlet at " + request.getContextPath ());
        out.println("<h1>Welcome to We-have-performance-problems.com!</h1>");
        out.println("<h2>Calculate preceding primenumber</h2>");

        out.println("<blockquote>");
        out.print("<form action=\"");
        out.print("Performance\" ");
        out.println("method=POST>");
        
        out.println("Enter a number");
        out.println("<input type=text size=6 value=" + (number == -1? 123456 : number) +" name=number>");
        out.println("<input type=checkbox name=isOptimzed> optimized");
        out.println("<input type=submit>");
        out.println("<br>");  
        out.println("</form>");
        if (results != -1) {
            out.println("<p>Result: The prime preceding "+number+" is <strong>" +results+"</strong>.<br />");
            String time = (timeMs/1000) + " seconds, " + (timeMs % 1000) + " ms";                
            out.println("Processing time " + time );
            if(isOptimzed) out.println(" (optimized)"); else out.println(" (standard)");
            out.println("</p>");
            results = -1;
        }

        /*
        out.print("<form action=\"");
        out.print("Performance\" ");
        out.println("method=POST>");
        out.println("File");
        out.println("<input type=text size=30 value=\"" + (fileName != null? fileName : "") +"\" name=file>");
        out.println("<input type=submit>");
        out.println("<br>");
        out.println("</form>");
        */
        
        out.println("</blockquote>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    
    private int doCountPrimeNumbers (int max, boolean isOptimized ) {
        if(!isOptimized || calculator == null)  {
          calculator = new PrimeNumbers ();
        }
        
        if( isOptimized == true )
        return calculator.calculate2 (max); 
        else
        return calculator.calculate (max); 
    }

    private int addFiles (File folder, int count) {
        File[] files = null;
        files = folder.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                count ++;
                if (files[i].isDirectory()) {
                    count = addFiles(files[i], count);
                }
            }
        }
        return count;
    }
    
    private int doCountFiles (String fileName) {
        File f = new File (fileName);
        return addFiles (f, 0);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
