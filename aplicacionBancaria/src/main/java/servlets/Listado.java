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
import utils.ConstantesListado;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "listado", urlPatterns = {Constantes.URL_LISTADO})
public class Listado extends HttpServlet {

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

        String numCuenta = request.getParameter(ConstantesListado.PARAMETRO_CUENTA);
        String cadenaInicio = request.getParameter(ConstantesListado.PARAMETRO_INICIO);
        String cadenaFin = request.getParameter(ConstantesListado.PARAMETRO_FIN);

        if (numCuenta != null && cadenaInicio != null && cadenaFin != null) {
            Servicios s = new Servicios();

            if (s.comprobarNumCuenta(numCuenta) && s.comprobarFechas(cadenaInicio, cadenaFin)) {
                if (s.validarCuenta(numCuenta)) {
                    String movimientos = s.getMovimientos(numCuenta, cadenaInicio, cadenaFin);

                    if (movimientos != null) {
                        response.getWriter().write(movimientos);
                    } else {
                        //response.setStatus(500);
                        //response.getWriter().write(ConstantesListado.ERROR_NO_MOVIMIENTOS);
                        response.getWriter().write(s.error(ConstantesListado.ERROR_NO_MOVIMIENTOS));
                    }
                } else {
                    response.getWriter().write(s.error(ConstantesListado.ERROR_CUENTA_NO_EXISTE));
                }
            } else {
                response.getWriter().write(s.error(Constantes.ERROR));
            }
        } else {
            request.getRequestDispatcher(Constantes.PINTAR_LISTADO).forward(request, response);
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
