package com.mycompany.servelettable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;
 
@WebServlet(name = "ShowClientInState", urlPatterns = {"/ShowClientInState"})
public class ShowClient extends HttpServlet {
 
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("table, th, td {\n"+"border: 1px solid black;\n"+"}");
            out.println("</style>");
            out.println("<title>Servlet ShowClient</title>");
            out.println("</head>");
            out.println("<body>");
            try {   // Trouver la valeur du paramètre HTTP customerID
                String val = request.getParameter("state");
                if (val == null) {
                    throw new Exception("Pas de paramètres transmis");
                }
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                List<CustomerEntity> customer = dao.customersInState(val);
                if (customer == null) {
                    throw new Exception("Client inconnu");
                }        
                out.println("<table style=\"width:20%\">");
                out.println("<tr><th>ID</th>"+"<th>Nom</th>"+"<th>Adresse</th>");
                for(int i=0;i<customer.size();i++)
                {
                    out.printf("<tr><td> %d </td>"+"<td> %s </td>"+"<td> %s </td></tr>",customer.get(i).getCustomerId(),customer.get(i).getName(),customer.get(i).getAddressLine1());
                }
                out.println("</tr>");
            } catch (Exception e) {
                out.printf("Erreur : %s", e.getMessage());
            }
            out.printf("<hr><a href='%s'>Retour au menu</a>", request.getContextPath());
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            Logger.getLogger("servlet").log(Level.SEVERE, "Erreur de traitement", ex);
        }
    }
 
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }
 
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }
 
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
 
}