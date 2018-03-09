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
import model.Movimiento;
import servicios.Servicios;
import utils.Constantes;
import utils.ConstantesOperaciones;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Operaciones", urlPatterns = {Constantes.URL_OPERACIONES})
public class Operaciones extends HttpServlet {

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

        if (accion != null) {
            Servicios s = new Servicios();
            Movimiento m = new Movimiento();
            String numCuenta = request.getParameter(ConstantesOperaciones.PARAMETRO_CUENTA);
            String asdf = request.getParameter(ConstantesOperaciones.PARAMETRO_IMPORTE);

            switch (accion){
                case ConstantesOperaciones.CASE_COMPROBAR_CUENTA:
                    if(s.comprobarNumCuenta(numCuenta)){
                        if(s.validarCuenta(numCuenta)){
                            response.getWriter().write(Constantes.CODIGO_OK);
                        } else {
                            response.getWriter().write(s.error(ConstantesOperaciones.ERROR_CUENTA_NO_EXISTE));
                        }
                    }
                    break;
                
                case ConstantesOperaciones.CASE_MOVIMIENTO:
                    boolean errorSaldo = false;
                    m.setMo_des(request.getParameter(ConstantesOperaciones.PARAMETRO_DESCRIPCION));
                    m.setMo_imp(Integer.parseInt(request.getParameter(ConstantesOperaciones.PARAMETRO_IMPORTE)));
                    m.setMo_ncu(numCuenta);
                    if(m.getMo_imp()<0){
                        if((m.getMo_imp()*-1)>s.getSaldo(numCuenta)){
                            errorSaldo = true;
                        }
                    }
                    if(!errorSaldo){
                        if(s.addMovimiento(m)){
                            response.getWriter().write(ConstantesOperaciones.MENSAJE_MOVIMIENTO);
                        }else{
                            response.getWriter().write(Constantes.ERROR);
                        }
                    }else{
                        response.getWriter().write(ConstantesOperaciones.ERROR_SALDO);
                    }
                    break;
            }
        } else {
            request.getRequestDispatcher(Constantes.PINTAR_OPERACIONES).forward(request, response);
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
