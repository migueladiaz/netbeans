/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class EpDAO {

    public final String queryComprobarPass="SELECT pass FROM registro WHERE nombre = ?";
    public final String queryAddUser="INSERT INTO registro (nombre, pass) VALUES (?,?)";
    public final String queryAddUserGoogle="INSERT INTO registro (nombre, pass) VALUES (?,'google')";
    
    public String getPass(String nombre) {
        String resultado = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            resultado = jtm.queryForObject(queryComprobarPass, String.class, nombre);

        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public boolean addUser(String nombre, String pass) {
        boolean registrado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            int filas = jtm.update(queryAddUser, nombre, pass);
            if (filas > 0) {
                registrado = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            registrado = false;
        }
        return registrado;
    }
    
    public boolean addUserGoogle(String email) {
        boolean registrado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            int filas = jtm.update(queryAddUserGoogle, email);
            if (filas > 0) {
                registrado = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            registrado = false;
        }
        return registrado;
    }
    
}
