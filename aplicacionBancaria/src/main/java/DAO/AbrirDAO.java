/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private final String queryUpdateTitular = "UPDATE clientes SET cl_ncu = cl_ncu + 1, cl_sal = cl_sal + ? WHERE c.cl_dni = ?";
    private final String queryAddTitular = "INSERT INTO clientes (cl_dni, cl_nom, cl_dir, cl_tel, cl_ema, cl_fna, cl_fcl, cl_ncu, cl_sal) VALUES (?,?,?,?,?,?,?,1,?)";
    private final String queryAddCuenta = "INSERT INTO cuentas (cu_ncu, cu_dn1, cu_sal) VALUES (?,?,?)";
    private final String queryUpdateCuenta = "UPDATE cuentas SET cu_dn2 = ? WHERE cu_ncu = ?";
    private final String queryAddMovimiento = "INSERT INTO movimientos (mo_ncu, mo_fec, mo_hor, mo_des, mo_imp, mo_sal) VALUES(?,?,?,'Alta de cuenta',?,?)";
    
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
    
    public boolean addTitular(String numCuenta, Cliente c, int importe, String tiempo, boolean existe, boolean segundoTitular) {
        Connection con = null;
        boolean guardado = false;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt;
            
            if(existe){
                stmt = con.prepareStatement(queryUpdateTitular);
                stmt.setInt(1, importe);
                stmt.setString(2, c.getCl_dni());
            }else{
                stmt = con.prepareStatement(queryAddTitular);
                stmt.setString(1, c.getCl_dni());
                stmt.setString(2, c.getCl_nom());
                stmt.setString(3, c.getCl_dir());
                stmt.setInt(4, c.getCl_tel());
                stmt.setString(5, c.getCl_ema());
                stmt.setDate(6, new java.sql.Date(c.getCl_fna().getTime()));
                stmt.setDate(7, new java.sql.Date(c.getCl_fcl().getTime()));
                stmt.setInt(8, importe);
            }
            
            stmt.executeUpdate();
            
            if(segundoTitular){
                stmt = con.prepareStatement(queryUpdateCuenta);
                stmt.setString(1, c.getCl_dni());
                stmt.setString(2, numCuenta);
                
                stmt.executeUpdate();
            }else{
                stmt = con.prepareStatement(queryAddCuenta);
                stmt.setString(1, numCuenta);
                stmt.setString(2, c.getCl_dni());
                stmt.setInt(3, importe);

                stmt.executeUpdate();

                stmt = con.prepareStatement(queryAddMovimiento);
                stmt.setString(1, numCuenta);
                stmt.setDate(2, new java.sql.Date(c.getCl_fcl().getTime()));
                stmt.setString(3, tiempo);
                stmt.setInt(4, importe);
                stmt.setInt(5, importe);

                stmt.executeUpdate();
            }
            
            guardado = true;
            con.commit();
        } catch (Exception ex) {
            Logger.getLogger(ListadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con!=null)
                    con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ListadoDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return guardado;
    }
}
