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

    public final String queryComprobarPass="SELECT nombre FROM registro WHERE nombre = ? AND pass = ?";
    
    public boolean comprobarPass(String nombre, String pass) {
        boolean existe = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            String resultado = jtm.queryForObject(queryComprobarPass, String.class, nombre, pass);

            if (resultado != null) {
                existe = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(EpDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
}
