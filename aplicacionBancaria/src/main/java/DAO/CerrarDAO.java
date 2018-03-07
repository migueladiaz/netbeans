/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Cuenta;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class CerrarDAO {
    
    private final String queryGetCuenta = "SELECT * from cuentas WHERE cu_ncu = ?";
    private final String queryGetTitulares = "SELECT * from clientes WHERE cl_ncu = ?";
    
    public Cuenta getCuenta(String numCuenta) {
        Cuenta c;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            c =(Cuenta) jtm.queryForObject(queryGetCuenta, new Object[]{numCuenta}, new BeanPropertyRowMapper(Cuenta.class));
        } catch (Exception ex) {
            Logger.getLogger(CerrarDAO.class.getName()).log(Level.SEVERE, null, ex);
            c = null;
        }
        return c;
    }
    
    public List<Cliente> getTitulares(String numCuenta) {
        List<Cliente> clientes;
        try{
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            clientes = jtm.query(queryGetTitulares, new BeanPropertyRowMapper(Cliente.class));
        }catch(Exception ex){
            Logger.getLogger(CerrarDAO.class.getName()).log(Level.SEVERE, null, ex);
            clientes = null;
        }
        return clientes;
    }
}
