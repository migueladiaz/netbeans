/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
    private final String queryGetTitulares = "SELECT * from clientes WHERE "
            + "cl_dni = (SELECT cu_dn1 FROM cuentas WHERE cu_ncu = ?) "
            + "OR cl_dni = (SELECT cu_dn2 FROM cuentas WHERE cu_ncu = ?)";
    
    private final String queryDelCuenta = "DELETE FROM cuentas WHERE cu_ncu = ?";
    private final String queryDelTitular = "DELETE FROM clientes WHERE cl_dni = ?";
    private final String queryUpdateTitular = "UPDATE clientes SET cl_ncu = cl_ncu - 1 WHERE cl_dni = ?";
    private final String queryDelMovimientos = "DELETE FROM movimientos WHERE mo_ncu = ?";
    
    
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
            clientes = jtm.query(queryGetTitulares, new Object[]{numCuenta, numCuenta}, new BeanPropertyRowMapper(Cliente.class));
        }catch(Exception ex){
            Logger.getLogger(CerrarDAO.class.getName()).log(Level.SEVERE, null, ex);
            clientes = null;
        }
        return clientes;
    }
    
    public boolean delCuenta(String numCuenta, List<Cliente> titulares) {
        Connection con = null;
        boolean borrado;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt;
            
            
            stmt = con.prepareStatement(queryDelCuenta);
            stmt.setString(1, numCuenta);
            
            stmt.executeUpdate();
            
            for(int i = 0; i < titulares.size(); i++){
                if(titulares.get(i).getCl_ncu() == 1){
                    stmt = con.prepareStatement(queryDelTitular);
                    stmt.setString(1, titulares.get(i).getCl_dni());
                }else{
                    stmt = con.prepareStatement(queryUpdateTitular);
                    stmt.setString(1, titulares.get(i).getCl_dni());
                }

                stmt.executeUpdate();
            }

            
            stmt = con.prepareStatement(queryDelMovimientos);
            stmt.setString(1, numCuenta);

            stmt.executeUpdate();
            
            
            borrado = true;
            con.commit();
        } catch (Exception ex) {
            Logger.getLogger(ListadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            borrado = false;
            try {
                if (con!=null)
                    con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ListadoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return borrado;
    }
}
