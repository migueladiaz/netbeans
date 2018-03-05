/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movimiento;

/**
 *
 * @author Miguel
 */
public class OperacionesDAO {
    
    private final String queryUpdateSaldoCuenta = "UPDATE cuentas c JOIN cuentas cu ON c.cu_ncu = cu.cu_ncu "
            + "SET c.cu_sal = cu.cu_sal + ? WHERE c.cu_ncu = ?";
    
    private final String queryUpdateSaldoCliente = "UPDATE clientes c JOIN clientes cl ON c.cl_dni = cl.cl_dni "
            + "SET c.cl_sal = cl.cl_sal + ? WHERE c.cl_dni = (SELECT cu_dn1 FROM cuentas WHERE cu_ncu = ?) "
            + "OR c.cl_dni = (SELECT cu_dn2 FROM cuentas WHERE cu_ncu = ?)";
    
    private final String queryAddMovimiento = "INSERT INTO movimientos (mo_ncu, mo_fec, mo_hor, mo_des, mo_imp, mo_sal)"
            + " VALUES (?,?,?,?,?,(SELECT cu_sal FROM cuentas WHERE cu_ncu = ?) + ?)";
    
    
    public boolean addMovimiento(Movimiento m){
        Connection con = null;
        boolean guardado = false;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt;
            
            stmt = con.prepareStatement(queryUpdateSaldoCuenta);
            stmt.setInt(1, m.getMo_imp());
            stmt.setString(2, m.getMo_ncu());
            
            stmt.executeUpdate();
            
            
            stmt = con.prepareStatement(queryUpdateSaldoCliente);
            stmt.setInt(1, m.getMo_imp());
            stmt.setString(2, m.getMo_ncu());
            stmt.setString(3, m.getMo_ncu());

            stmt.executeUpdate();
            
            
            stmt = con.prepareStatement(queryAddMovimiento);
            stmt.setString(1, m.getMo_ncu());
            stmt.setDate(2, (java.sql.Date) new Date());
            stmt.setString(3, m.getMo_hor());
            stmt.setString(4, m.getMo_des());
            stmt.setInt(5, m.getMo_imp());
            stmt.setString(6, m.getMo_ncu());
            stmt.setInt(7, m.getMo_imp());

            stmt.executeUpdate();
            
            
            guardado = true;
            con.commit();
        } catch (Exception ex) {
            Logger.getLogger(AbrirDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con!=null)
                    con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AbrirDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return guardado;
    }
}
    
