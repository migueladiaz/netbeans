/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.ApiKey;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class ApiDAO {

    private final String queryComprobarKey = "SELECT * FROM apikey WHERE apikey = ?";

    public ApiKey comprobarKey(String apikey) {
        ApiKey k;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            k = (ApiKey) jtm.queryForObject(queryComprobarKey, new Object[]{apikey}, new BeanPropertyRowMapper(ApiKey.class));
        } catch (Exception ex) {
            Logger.getLogger(ApiDAO.class.getName()).log(Level.SEVERE, null, ex);
            k = null;
        }
        return k;
    }
}
