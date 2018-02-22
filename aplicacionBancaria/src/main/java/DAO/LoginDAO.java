/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class LoginDAO {

    private final String queryComprobarEmail = "SELECT email FROM users WHERE email = ?";
    private final String queryRegistro = "INSERT INTO users (email,pass,codigo,activado) VALUES (?,?,?,0)";
    private final String queryUserByCodigo = "SELECT * FROM users WHERE codigo = ?";
    private final String queryActivar = "UPDATE users SET activado = TRUE WHERE codigo = ?";
    private final String queryUserByEmail = "SELECT * FROM users WHERE email = ?";

    public boolean comprobarEmail(String email) {
        boolean existe = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            String resultado = jtm.queryForObject(queryComprobarEmail, String.class, email);

            if (resultado != null) {
                existe = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            existe = false;
        }
        return existe;
    }

    public boolean registro(User u) {
        boolean registrado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            int filas = jtm.update(queryRegistro, u.getEmail(), u.getPass(), u.getCodigo());
            if (filas > 0) {
                registrado = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            registrado = false;
        }
        return registrado;
    }

    public User getUserByCodigo(String codigo) {
        User u;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            u = (User) jtm.queryForObject(queryUserByCodigo, new Object[]{codigo}, new BeanPropertyRowMapper(User.class));
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            u = null;
        }
        return u;
    }

    public boolean activarUserByCodigo(String codigo) {
        boolean activado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryActivar, codigo) > 0) {
                activado = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            activado = false;
        }
        return activado;
    }
    
    public User getUserByEmail(String email) {
        User u;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            u = (User) jtm.queryForObject(queryUserByEmail, new Object[]{email}, new BeanPropertyRowMapper(User.class));
        } catch (Exception ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            u = null;
        }
        return u;
    }
}
