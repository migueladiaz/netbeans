/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import servicios.Servicios;
import utils.Constantes;
import utils.ConstantesAbrir;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "Abrir", urlPatterns = {Constantes.URL_ABRIR})
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
        
        String accion = request.getParameter(Constantes.PARAMETRO_ACCION);
        
        if(accion != null){
            Servicios s = new Servicios();
            String numCuenta = request.getParameter(ConstantesAbrir.PARAMETRO_NUMERO_CUENTA);
            String dni = request.getParameter(ConstantesAbrir.PARAMETRO_DNI);
            
            switch(accion){
                case ConstantesAbrir.CASE_COMPROBAR_CUENTA:
                    if(s.comprobarNumCuenta(numCuenta)){
                        if(!s.validarCuenta(numCuenta)){
                            response.getWriter().write(Constantes.DISPONIBLE);
                        } else {
                            response.getWriter().write(s.error(ConstantesAbrir.ERROR_CUENTA_YA_EXISTE));
                        }
                    } else {
                        response.getWriter().write(s.error(Constantes.ERROR));
                    }
                    break;
                    
                case ConstantesAbrir.CASE_COMPROBAR_TITULAR:
                    if (s.comprobarDni(dni)) {
                        if (s.comprobarTitularAlta(dni)) {
                            response.getWriter().write(s.getCliente(dni));
                        } else {
                            response.getWriter().write(ConstantesAbrir.PEDIR_DATOS);
                        }
                    } else {
                        response.getWriter().write(s.error(Constantes.ERROR));
                    }
                    break;
                    
                case ConstantesAbrir.CASE_GUARDAR_TITULAR:
                    LocalDate fechaCliente = LocalDate.now();
                    Cliente c = new Cliente();
                    c.setCl_dni(dni);
                    c.setCl_fcl(Date.from(fechaCliente.atStartOfDay().toInstant(ZoneOffset.UTC)));
                    
                    int importe = Integer.parseInt(request.getParameter(ConstantesAbrir.PARAMETRO_IMPORTE));
                    boolean existe = Boolean.parseBoolean(request.getParameter(ConstantesAbrir.PARAMETRO_EXISTE));
                    boolean segundoTitular = Boolean.parseBoolean(request.getParameter(ConstantesAbrir.PARAMETRO_SEGUNDO_TITULAR));
                    
                    if(!existe){
                        try {
                            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                            String fecha = request.getParameter(ConstantesAbrir.PARAMETRO_FECHA_NACIMIENTO);
                            Date fechaNacimiento = fmt.parse(fecha);

                            c.setCl_nom(request.getParameter(ConstantesAbrir.PARAMETRO_NOMBRE));
                            c.setCl_dir(request.getParameter(ConstantesAbrir.PARAMETRO_DIRECCION));
                            c.setCl_tel(Integer.parseInt(request.getParameter(ConstantesAbrir.PARAMETRO_TELEFONO)));
                            c.setCl_ema(request.getParameter(ConstantesAbrir.PARAMETRO_EMAIL));
                            c.setCl_fna(fechaNacimiento);
                        } catch (ParseException ex) {
                            Logger.getLogger(Abrir.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    if(s.addTitular(numCuenta, c, importe, existe, segundoTitular)){
                        response.getWriter().write(Constantes.CODIGO_OK);
                    }else{
                        response.getWriter().write(s.error(ConstantesAbrir.ERROR_GUARDAR_DATOS));
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
