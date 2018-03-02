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
import utils.ConstantesLogin;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Login", urlPatterns = {Constantes.URL_LOGIN})
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

        String accion = request.getParameter(Constantes.PARAMETRO_ACCION);

        if (accion != null) {
            LoginServicios ls = new LoginServicios();
            String email;
            String pass;

            switch (accion) {
                case ConstantesLogin.CASE_COMPROBAR_EMAIL:
                    email = request.getParameter(ConstantesLogin.PARAMETRO_EMAIL);

                    if (ls.comprobarEmail(email)) {
                        response.getWriter().print(ConstantesLogin.OCUPADO);
                    } else {
                        response.getWriter().print(ConstantesLogin.DISPONIBLE);
                    }
                    break;

                case ConstantesLogin.CASE_LOGIN:
                    email = request.getParameter(ConstantesLogin.PARAMETRO_EMAIL_LOGIN);
                    pass = request.getParameter(ConstantesLogin.PARAMETRO_PASS_LOGIN);

                    switch(ls.login(email, pass)){
                        case -1:
                            request.setAttribute(ConstantesLogin.ATRIBUTO_MENSAJE2, Constantes.ERROR);
                            break;
                            
                        case 0:
                            request.setAttribute(ConstantesLogin.ATRIBUTO_MENSAJE2, ConstantesLogin.ERROR_USER_PASS);
                            break;
                            
                        case 1:
                            request.getSession().setAttribute(ConstantesLogin.ATRIBUTO_EMAIL_USUARIO, email);
                            response.sendRedirect(Constantes.REDIRIGIR_LISTADO);
                            return;
                            
                        case 2:
                            request.setAttribute(ConstantesLogin.ATRIBUTO_MENSAJE2, ConstantesLogin.ERROR_CUENTA_ACTIVADA);
                            break;
                    }
                    break;

                case ConstantesLogin.CASE_REGISTRO:
                    email = request.getParameter(ConstantesLogin.PARAMETRO_EMAIL_REGISTRO);
                    pass = request.getParameter(ConstantesLogin.PARAMETRO_PASS_REGISTRO);
                    
                    if (ls.registro(email, pass)) {
                        request.setAttribute(ConstantesLogin.ATRIBUTO_MENSAJE, ConstantesLogin.REGISTRO_COMPLETO);
                    } else {
                        request.setAttribute(ConstantesLogin.ATRIBUTO_MENSAJE, Constantes.ERROR);
                    }
                    
                    break;
                    
                case ConstantesLogin.CASE_ACTIVAR:
                    String codigo = request.getParameter(ConstantesLogin.PARAMETRO_CODIGO);
                    
                    if(ls.activar(codigo)){
                        request.setAttribute(ConstantesLogin.ATRIBUTO_MENSAJE, ConstantesLogin.CUENTA_ACTIVADA);
                    }else{
                        request.setAttribute(ConstantesLogin.ATRIBUTO_MENSAJE, Constantes.ERROR);
                    }
                    break;
                
                case ConstantesLogin.CASE_LOGOUT: 
                    request.getSession().invalidate();
                    break;
            }
        } else {
            request.getRequestDispatcher(Constantes.PINTAR_LOGIN).forward(request, response);
        }
        if(!accion.equals(ConstantesLogin.CASE_COMPROBAR_EMAIL)){
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
