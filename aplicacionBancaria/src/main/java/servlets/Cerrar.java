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
import utils.ConstantesCerrar;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Cerrar", urlPatterns = {Constantes.URL_CERRAR})
public class Cerrar extends HttpServlet {

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
        
        String accion = request.getParameter(Constantes.PARAMETRO_ACCION);
        
        if(accion != null){
            Servicios s = new Servicios();
            String numCuenta = request.getParameter(ConstantesCerrar.PARAMETRO_NUMERO_CUENTA);
            
            switch(accion){
                case ConstantesCerrar.CASE_COMPROBAR_CUENTA:
                    if(s.comprobarNumCuenta(numCuenta)){
                        if(s.validarCuenta(numCuenta)){
                            response.getWriter().write(s.getDatosCuenta(numCuenta));
                        } else {
                            response.getWriter().write(s.error(ConstantesCerrar.ERROR_CUENTA_NO_EXISTE));
                        }
                    } else {
                        response.getWriter().write(s.error(Constantes.ERROR));
                    }
                    break;
                    
                case ConstantesCerrar.CASE_COMPROBAR_SALDO:
                    if(s.getSaldo(numCuenta)==0){
                        response.getWriter().write(Constantes.CODIGO_OK);
                    }else{
                        response.getWriter().write(s.error(ConstantesCerrar.ERROR_SALDO));
                    }
                    break;
                    
                case ConstantesCerrar.CASE_CERRAR_CUENTA:
                    if(s.delCuenta(numCuenta)){
                        response.getWriter().write(Constantes.CODIGO_OK);
                    }else{
                        response.getWriter().write(s.error(Constantes.ERROR));
                    }
                    break;
            }
        }else{
             request.getRequestDispatcher(Constantes.PINTAR_CERRAR).forward(request, response);
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
