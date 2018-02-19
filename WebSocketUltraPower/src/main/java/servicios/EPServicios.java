/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.EpDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PasswordHash;

/**
 *
 * @author Miguel
 */
public class EPServicios {
    
    public boolean comprobarPass(String nombre, String pass){
            EpDAO dao = new EpDAO();
            boolean comprobarPass = false;
        try {
            String passDB = dao.getPass(nombre);
            
            if(passDB != null){
                boolean passCorrecta = PasswordHash.getInstance().validatePassword(pass, passDB);
            
                if(passCorrecta){
                    comprobarPass = true;
                }
            }else{
                String hash = PasswordHash.getInstance().createHash(pass);
                if(dao.addUser(nombre, hash)){
                    comprobarPass = true;
                }
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(EPServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return comprobarPass;
    }
}
