/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import DAO.ListadoDAO;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;

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
}
