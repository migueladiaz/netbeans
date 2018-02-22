/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.LoginServicios;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion != null) {
            LoginServicios ls = new LoginServicios();
            String email;
            String pass;

            switch (accion) {
                case "comprobarEmail":
                    email = request.getParameter("email");

                    if (ls.comprobarEmail(email)) {
                        response.getWriter().print(Constantes.OCUPADO);
                    } else {
                        response.getWriter().print(Constantes.DISPONIBLE);
                    }
                    break;

                case "login":
                    email = request.getParameter("emaillogin");
                    pass = request.getParameter("passlogin");

                    switch(ls.login(email, pass)){
                        case -1:
                            request.setAttribute("mensaje2", Constantes.ERROR);
                            break;
                            
                        case 0:
                            request.setAttribute("mensaje2", Constantes.ERROR_USER_PASS);
                            break;
                            
                        case 1:
                            request.getSession().setAttribute("emailUsuario", email);
                            response.sendRedirect("banco/listado");
                            return;
                            //break;
                            
                        case 2:
                            request.setAttribute("mensaje2", Constantes.ERROR_CUENTA_ACTIVADA);
                            break;
                    }
                    break;

                case "registro":
                    email = request.getParameter("emailregistro");
                    pass = request.getParameter("passregistro");
                    
                    if (ls.registro(email, pass)) {
                        request.setAttribute("mensaje", Constantes.REGISTRO_COMPLETO);
                    } else {
                        request.setAttribute("mensaje", Constantes.ERROR);
                    }
                    
                    break;
                    
                case "activar":
                    String codigo = request.getParameter("codigo");
                    
                    if(ls.activar(codigo)){
                        request.setAttribute("mensaje", Constantes.CUENTA_ACTIVADA);
                    }else{
                        request.setAttribute("mensaje", Constantes.ERROR);
                    }
                    break;
                
                case "logout": 
                    request.getSession().invalidate();
                    break;
            }
        } else {
            request.getRequestDispatcher(Constantes.PINTAR_LOGIN).forward(request, response);
        }
        if(!accion.equals("comprobarEmail")){
            request.getRequestDispatcher(Constantes.PINTAR_LOGIN).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    }// </editor-fold>

}
