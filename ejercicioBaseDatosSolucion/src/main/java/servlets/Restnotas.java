/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MensajeHttp;
import model.Nota;
import servicios.AlumnosServicios;
import servicios.AsignaturasServicios;
import servicios.NotasServicios;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Restnotas", urlPatterns = {"/rest/restnotas"})
public class Restnotas extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    AlumnosServicios al = new AlumnosServicios();
    AsignaturasServicios as = new AsignaturasServicios();
    NotasServicios ns = new NotasServicios();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = (String) request.getAttribute("accion");
        Nota n = (Nota) request.getAttribute("nota");

        if (accion != null) {
            if (n != null) {
                Nota nota = ns.getNota(n.getIdAlumno(), n.getIdAsignatura());

                if (nota != null) {
                    request.setAttribute("json", nota);
                } else {
                    response.setStatus(204);
                    MensajeHttp info = new MensajeHttp("No hay notas");
                    request.setAttribute("json", info);
                }
            }
        } else {

            boolean error = false;
            List datos = new ArrayList();

            List alumnos = al.getAllAlumnos();
            List asignaturas = as.getAllAsignaturas();

            if ((alumnos != null) && (asignaturas != null)) {
                datos.add(alumnos);
                datos.add(asignaturas);
            } else {
                response.setStatus(503);
                MensajeHttp info = new MensajeHttp("Ha ocurrido un error");

                request.setAttribute("json", info);
                error = true;
            }

            if (n != null) {
                Nota nota = ns.getNota(n.getIdAlumno(), n.getIdAsignatura());

                if (nota != null) {
                    datos.add(nota);
                } else {
                    response.setStatus(500);
                    MensajeHttp info = new MensajeHttp("No hay notas");

                    datos.add(info);
                }
            }
            if (error == false) {
                request.setAttribute("json", datos);
            }
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

        Nota n = (Nota) request.getAttribute("nota");

        n = ns.guardarNota(n);

        if (n == null) {
            response.setStatus(500);
            MensajeHttp info = new MensajeHttp("Ha ocurrido un error");

            request.setAttribute("json", info);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Nota n = (Nota) request.getAttribute("nota");

        if (ns.delNota(n) == -1) {
            response.setStatus(500);
            MensajeHttp info = new MensajeHttp("Ha ocurrido un error");

            request.setAttribute("json", info);
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
