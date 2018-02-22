/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class AbrirDAO {
    
    private final String queryGetCliente = "SELECT * from clientes WHERE cl_dni = ?";
    
    public Cliente getCliente(String dni) {
        Cliente c;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            c =(Cliente) jtm.queryForObject(queryGetCliente, new Object[]{dni}, new BeanPropertyRowMapper(Cliente.class));
        } catch (Exception ex) {
            Logger.getLogger(ListadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            c = null;
        }
        return c;
    }
}
