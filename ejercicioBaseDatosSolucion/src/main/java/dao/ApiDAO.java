/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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

    private final String queryComprobarKey = "SELECT * FROM APIKEY WHERE APIKEY = ?";
    private final String queryUpdatePeticiones = "UPDATE APIKEY SET NUM_PETICIONES = ? WHERE APIKEY = ?";
    private final String queryUpdateHora = "UPDATE APIKEY SET FECHA_ULTIMA_PETICION = ?, NUM_PETICIONES = 1 WHERE APIKEY = ?";

    public ApiKey comprobarKey(String apikey) {
        ApiKey k;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            k = (ApiKey) jtm.queryForObject(queryComprobarKey, new Object[]{apikey}, new BeanPropertyRowMapper(ApiKey.class));
        } catch (Exception ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            k = null;
        }
        return k;
    }

    public int updatePeticiones(ApiKey k) {
        int valido = -1;
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        if (jtm.update(queryUpdatePeticiones, k.getNUM_PETICIONES(), k.getAPIKEY()) > 0) {
            valido = 1;
        }

        return valido;
    }
    
    public int updateHora(ApiKey k) {
        int valido = -1;
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        if (jtm.update(queryUpdateHora,k.getFECHA_ULTIMA_PETICION(), k.getAPIKEY()) > 0) {
            valido = 1;
        }

        return valido;
    }
}
