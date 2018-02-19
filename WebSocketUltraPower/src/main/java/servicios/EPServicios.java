/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.EpDAO;

/**
 *
 * @author Miguel
 */
public class EPServicios {
    
    public boolean comprobarPass(String nombre, String pass){
        EpDAO dao = new EpDAO();
        return dao.comprobarPass(nombre, pass);
    }
}
