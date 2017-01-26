/*
 * MemoryLeak.java
 *
 * Created on June 27, 2005, 9:01 AM
 */

package demo;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Ian Formanek
 * @version
 */
public class MemoryLeak extends HttpServlet {
    private demo.memoryleak.LeakThread leak = new demo.memoryleak.LeakThread ();
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (request.getParameter("start") != null) {
            System.out.println("Starting leak...");
            leak.start();
        }
        else {
            System.out.println("Stopping leak...");
            if (request.getParameter("stop") != null) leak.stop = true;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet MemoryLeak</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p><h1>Welcome to We-leak-objects.com!</h1></p>");
        out.print("<form action=\"");
        out.print("MemoryLeak\" ");
        out.println("method=POST>");
        out.println("<input type=submit value=\"Start Leaking\" name=start>");
        out.println("<input type=submit value=\"Stop Leaking\" name=stop>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        out.close();
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
