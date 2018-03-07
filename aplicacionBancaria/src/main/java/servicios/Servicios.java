/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import DAO.AbrirDAO;
import DAO.CerrarDAO;
import DAO.ListadoDAO;
import DAO.OperacionesDAO;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Cuenta;
import model.Movimiento;

/**
 *
 * @author Miguel
 */
public class Servicios {

    public boolean comprobarNumCuenta(String numCuenta) {
        boolean error = false;
        int total = 0;

        for (int i = 0; i < numCuenta.length() - 1; i++) {
            total += (int) numCuenta.charAt(i);
        }

        if (!((total % 9) == (int) (numCuenta.charAt(numCuenta.length() - 1)))) {
            error = true;
        }

        if (!numCuenta.matches("^[0-9]{10}$")) {
            error = true;
        }

        return error;
    }
    
    public boolean comprobarDni(String dni){
        return dni.matches("^[0-9]{8}[A-Z]$");
    }
    
    public boolean comprobarFechas(String inicio, String fin){
        boolean fechasValidas;
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(inicio, dtf);
        LocalDate fechaFin = LocalDate.parse(fin, dtf);

        if(fechaInicio.isEqual(fechaFin)){
            fechasValidas = true;
        }else{
            fechasValidas = !fechaInicio.isAfter(fechaFin);
        }
        return fechasValidas;
    }
    
    public boolean validarCuenta(String cuenta){
        ListadoDAO dao = new ListadoDAO();
        return dao.validarCuenta(cuenta);
    }
    
    public String getMovimientos(String cuenta, String inicio, String fin){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(inicio, dtf);
        LocalDate fechaFin = LocalDate.parse(fin, dtf);
        ListadoDAO dao = new ListadoDAO();
        List lista = dao.getMovimientos(cuenta, fechaInicio, fechaFin);
        String movimientos;
        
        if(lista == null){
            movimientos = null;
        }else{
            movimientos = new Gson().toJson(lista);
        }
        return movimientos;
    }
    
    public String error(String error){
        ArrayList respuesta = new ArrayList();
        respuesta.add("500");
        respuesta.add(error);
        return new Gson().toJson(respuesta);
    }
    
    public boolean comprobarTitularAlta(String dni){
        AbrirDAO dao = new AbrirDAO();
        boolean existe;
        Cliente c = dao.getCliente(dni);
        if(c == null){
            existe = false;
        } else {
            existe = true;
        }
        return existe;
    }
    
    public String getCliente(String dni){
        AbrirDAO dao = new AbrirDAO();
        return new Gson().toJson(dao.getCliente(dni));
    }
    
    public String parseTiempo(LocalDateTime fechaActual){
        String tiempo = "";
        
        if(fechaActual.getHour()<10){
            tiempo = "0"+fechaActual.getHour();
        }else{
            tiempo = Integer.toString(fechaActual.getHour());
        }
        
        if(fechaActual.getMinute()<10){
            tiempo = tiempo + "0" + fechaActual.getMinute();
        }else{
            tiempo = tiempo + Integer.toString(fechaActual.getMinute());
        }
        
        if(fechaActual.getSecond()<10){
            tiempo = tiempo + "0" + fechaActual.getSecond();
        }else{
            tiempo = tiempo + Integer.toString(fechaActual.getSecond());
        }
        return tiempo;
    }
    
    public boolean addTitular(Cliente c, int importe, boolean existe, boolean segundoTitular){
        AbrirDAO dao = new AbrirDAO();
        LocalDateTime fechaActual = LocalDateTime.now();
        String tiempo = parseTiempo(fechaActual);
        return dao.addTitular(c, importe, tiempo, existe, segundoTitular);
    }
    
    public boolean addMovimiento(Movimiento m){
        OperacionesDAO dao = new OperacionesDAO();
        LocalDateTime fechaActual = LocalDateTime.now();
        String hora = parseTiempo(fechaActual);
        m.setMo_hor(hora);
        
        return dao.addMovimiento(m);
    }
    
    public String getDatosCuenta (String numCuenta){
        CerrarDAO dao = new CerrarDAO();
        ArrayList datos = new ArrayList();
        
        Cuenta datosCuenta = dao.getCuenta(numCuenta);
        List<Cliente> datosTitulares = dao.getTitulares(numCuenta);
        
        datos.add(datosCuenta);
        datos.add(datosTitulares);
        
        return new Gson().toJson(datos);
    }
}
