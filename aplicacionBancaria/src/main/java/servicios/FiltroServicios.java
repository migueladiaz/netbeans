/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import DAO.ApiDAO;
import model.ApiKey;

/**
 *
 * @author Miguel
 */
public class FiltroServicios {

    public ApiKey comprobarKey(String k) {
        ApiDAO ad = new ApiDAO();
        return ad.comprobarKey(k);
    }
}
