/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Canal;
import model.Mensaje;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class EpDAO {

    public final String queryComprobarPass="SELECT pass FROM registro WHERE nombre = ?";
    public final String queryAddUser="INSERT INTO registro (nombre, pass) VALUES (?,?)";
    public final String queryAddUserGoogle="INSERT INTO registro (nombre, pass) VALUES (?,'google')";
    public final String queryGuardarMensaje="INSERT INTO mensajes (mensaje, fecha, id_canal, nombre_user) VALUES (?,?,?,?)";
    public final String queryGetCanales="SELECT * FROM canales";
    public final String queryGetMensajes="SELECT * FROM mensajes WHERE fecha BETWEEN ? AND ?";
    public final String queryGetMisCanales="SELECT c.* FROM canales c JOIN canales_users cu ON c.id = cu.id_canal WHERE cu.user = ?";
    
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
    
    public boolean guardarMensaje(Mensaje m) {
        boolean guardado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            int filas = jtm.update(queryGuardarMensaje, m.getMensaje(), m.getFecha(), m.getId_canal(), m.getNombre_user());
            if (filas > 0) {
                guardado = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            guardado = false;
        }
        return guardado;
    }
    
    public ArrayList getCanales() {
        ArrayList<Canal> canales;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            canales =(ArrayList) jtm.query(queryGetCanales, new BeanPropertyRowMapper(Canal.class));
            if(canales.isEmpty()){
                canales = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            canales = null;
        }
        return canales;
    }
    
    public ArrayList getMensajes(Mensaje m) {
        ArrayList<Mensaje> mensajes;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            mensajes =(ArrayList) jtm.query(queryGetMensajes, new BeanPropertyRowMapper(Mensaje.class), m.getInicio(), m.getFin());
            if(mensajes.isEmpty()){
                mensajes = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensajes = null;
        }
        return mensajes;
    }
    
    public ArrayList getMisCanales(String nombre) {
        ArrayList<Canal> canales;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            canales =(ArrayList) jtm.query(queryGetMisCanales, new BeanPropertyRowMapper(Canal.class), nombre);
            if(canales.isEmpty()){
                canales = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            canales = null;
        }
        return canales;
    }
}
