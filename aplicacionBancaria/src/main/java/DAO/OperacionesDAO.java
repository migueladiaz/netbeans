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
    
    private final String queryUpdateSaldoCuenta = "UPDATE cuentas SET cu_sal = cu_sal + ? WHERE cu_ncu = ?";
    
    private final String queryUpdateSaldoCliente = "UPDATE clientes SET cl_sal = cl_sal + ? "
            + "WHERE cl_dni = (SELECT cu_dn1 FROM cuentas WHERE cu_ncu = ?) "
            + "OR cl_dni = (SELECT cu_dn2 FROM cuentas WHERE cu_ncu = ?)";
    
    private final String queryAddMovimiento = "INSERT INTO movimientos (mo_ncu, mo_fec, mo_hor, mo_des, mo_imp, mo_sal)"
            + " VALUES (?,?,?,?,?,(SELECT cu_sal FROM cuentas WHERE cu_ncu = ?))";
    
    
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
            stmt.setDate(2, new java.sql.Date(new Date().getTime()));
            stmt.setString(3, m.getMo_hor());
            stmt.setString(4, m.getMo_des());
            stmt.setInt(5, m.getMo_imp());
            stmt.setString(6, m.getMo_ncu());

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
    
