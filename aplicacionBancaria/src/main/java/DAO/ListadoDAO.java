/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movimiento;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class ListadoDAO {
    
    private final String queryValidarCuenta = "SELECT cu_ncu FROM cuentas WHERE cu_ncu = ?";
    private final String queryGetMovimientos ="SELECT m.*, c.cu_sal FROM movimientos m "
                                            + "JOIN cuentas c ON m.mo_ncu = c.cu_ncu "
                                            + "WHERE m.mo_ncu = ? AND m.mo_fec BETWEEN ? AND ? "
                                            + "ORDER BY m.mo_fec DESC";
    
    public boolean validarCuenta(String cuenta) {
        boolean existe = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            String resultado = jtm.queryForObject(queryValidarCuenta, String.class, cuenta);

            if (resultado != null) {
                existe = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ListadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
    public List getMovimientos(String cuenta, LocalDate inicio, LocalDate fin) {
        List<Movimiento> movimientos;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            movimientos = jtm.query(queryGetMovimientos, new BeanPropertyRowMapper(Movimiento.class), cuenta, inicio, fin);
            if(movimientos.size()==0){
                movimientos = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ListadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            movimientos = null;
        }
        return movimientos;
    }
}
