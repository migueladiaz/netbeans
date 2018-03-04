/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Alumno;
import model.MensajeHttp;
import servicios.AlumnosServicios;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Restalumnos", urlPatterns = {"/rest/restalumnos"})
public class Restalumnos extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    AlumnosServicios as = new AlumnosServicios();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List lista = as.getAllAlumnos();

        if (lista == null) {
            response.setStatus(500);
            MensajeHttp info = new MensajeHttp("Ha ocurrido un error");

            request.setAttribute("json", info);
        } else {
            request.setAttribute("json", lista);
        }
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

        Alumno a = (Alumno) request.getAttribute("alumno");

        if (as.updateAlumno(a) <= 0) {
            response.setStatus(500);
            MensajeHttp info = new MensajeHttp("Ha ocurrido un error");

            request.setAttribute("json", info);
        }else{
            request.setAttribute("json", a);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Alumno a = (Alumno) request.getAttribute("alumno");
        
        a = as.addAlumno(a);
        
        if (a == null) {
            response.setStatus(500);
            MensajeHttp info = new MensajeHttp("Ha ocurrido un error");

            request.setAttribute("json", info);
        }else{
            request.setAttribute("json", a);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Alumno a = (Alumno) request.getAttribute("alumno");
        String accion = (String) request.getAttribute("accion");

        if (accion.equals("borrar2")) {
            if (as.delAlumno2(a) <= 0) {
                response.setStatus(500);
                MensajeHttp info = new MensajeHttp("Ha ocurrido un error");

                request.setAttribute("json", info);
            }else{
                request.setAttribute("json", a);
            }
        } else {
            if (as.delAlumno(a) == -1) {
                response.setStatus(503);
                request.setAttribute("json", a);
            }else{
                request.setAttribute("json", a);
            }
        }
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
