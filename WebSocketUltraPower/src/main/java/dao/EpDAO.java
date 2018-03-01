/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    public final String queryGetMensajes="SELECT m.* FROM mensajes m "
                                       + "LEFT JOIN canales_users cu "
                                       + "ON m.id_canal = cu.id_canal "
                                       + "WHERE m.fecha BETWEEN ? AND ? "
                                       + "AND (m.id_canal = 0 OR cu.user = ?)";
    
    public final String queryGetMisCanales="SELECT c.* FROM canales c JOIN canales_users cu ON c.id = cu.id_canal WHERE cu.user = ?";
    public final String queryGetAdminCanal="SELECT user_admin FROM canales WHERE id = ?";
    public final String queryComprobarSuscripcion="SELECT user FROM canales_users WHERE id_canal = ? AND user = ?";
    public final String queryAddUserCanal="INSERT INTO canales_users (id_canal, user) VALUES (?,?)";
    public final String queryAddCanal="INSERT INTO canales (nombre, user_admin) VALUES (?,?)";
    
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
            mensajes =(ArrayList) jtm.query(queryGetMensajes, new BeanPropertyRowMapper(Mensaje.class), m.getInicio(), m.getFin(), m.getNombre_user());
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
    
    public String getAdminCanal(int idCanal) {
        String resultado = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            resultado = jtm.queryForObject(queryGetAdminCanal, String.class, idCanal);

        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public boolean comprobarSuscripcion(String nombre, int id) {
        String resultado;
        boolean existe = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            resultado = jtm.queryForObject(queryComprobarSuscripcion, String.class, id, nombre);
            if(resultado != null){
                existe = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
    public boolean addUserCanal(int id, String nombre) {
        boolean registrado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            int filas = jtm.update(queryAddUserCanal, id, nombre);
            if (filas > 0) {
                registrado = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            registrado = false;
        }
        return registrado;
    }
    
    public int addCanal(String nombreUser, String nombreCanal){
        Connection con = null;
        int filas = 0;
        try {
            
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement(queryAddCanal, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nombreCanal);
            stmt.setString(2, nombreUser);
            
            filas += stmt.executeUpdate();
            
            int id = 0;
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            stmt = con.prepareStatement(queryAddUserCanal);
            stmt.setInt(1, id);
            stmt.setString(2, nombreUser);

            filas += stmt.executeUpdate();
            con.commit();
            
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con!=null)
                    con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            filas = 0;
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return filas;
    }
}
