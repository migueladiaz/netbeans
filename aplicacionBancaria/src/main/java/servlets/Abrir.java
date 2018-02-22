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
import servicios.Servicios;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Abrir", urlPatterns = {"/banco/abrir"})
public class Abrir extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        
        if(accion != null){
            Servicios s = new Servicios();
            String numCuenta = request.getParameter("cuenta");
            String dni = request.getParameter("dni");
            
            switch(accion){
                case "comprobarCuenta":
                    if(s.comprobarNumCuenta(numCuenta)){
                        if(!s.validarCuenta(numCuenta)){
                            response.getWriter().write(Constantes.DISPONIBLE);
                        } else {
                            response.getWriter().write(s.error(Constantes.ERROR_CUENTA_YA_EXISTE));
                        }
                    } else {
                        response.getWriter().write(s.error(Constantes.ERROR));
                    }
                    break;
                    
                case "ComprobarTitular":
                    if (s.comprobarDni(dni)) {
                        if (s.comprobarTitularAlta(dni)) {
                            response.getWriter().write(s.getCliente(dni));
                        } else {
                            response.getWriter().write(Constantes.PEDIR_DATOS);
                        }
                    } else {
                        response.getWriter().write(s.error(Constantes.ERROR));
                    }
                    break;
            }
            
        }else{
             request.getRequestDispatcher(Constantes.PINTAR_ABRIR).forward(request, response);
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
