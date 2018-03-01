/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.EpDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mensaje;
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
        } catch (Exception ex) {
            Logger.getLogger(EPServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return comprobarPass;
    }
    
    public boolean addUserGoogle(String email){
        EpDAO dao = new EpDAO();
        boolean userCreado = false;
        if(dao.getPass(email)==null){
            userCreado = dao.addUserGoogle(email);
        }
        return userCreado;
    }
    
    public boolean comprobarUserGoogle(String email){
        EpDAO dao = new EpDAO();
        boolean existe;
        
        String pass = dao.getPass(email);
        
        if(pass != null){
            existe = true;
        } else {
            existe = false;
        }
        return existe;
    }
    
    public boolean guardarMensaje(Mensaje m){
        EpDAO dao = new EpDAO();
        return dao.guardarMensaje(m);
    }
    
    public ArrayList getCanales(){
        EpDAO dao = new EpDAO();
        return dao.getCanales();
    }
    
    public ArrayList getMensajes(Mensaje m){
        EpDAO dao = new EpDAO();
        return dao.getMensajes(m);
    }
    
    public ArrayList getMisCanales(String nombre){
        EpDAO dao = new EpDAO();
        return dao.getMisCanales(nombre);
    }
    
    public boolean comprobarSuscripcion(String nombre, int id){
        EpDAO dao = new EpDAO();
        return dao.comprobarSuscripcion(nombre, id);
    }
    
    public String getAdminCanal(int idCanal){
        EpDAO dao = new EpDAO();
        return dao.getAdminCanal(idCanal);
    }
    
    public boolean addUserCanal(int id, String nombre){
        EpDAO dao = new EpDAO();
        return dao.addUserCanal(id, nombre);
    }
    
    public boolean addCanal(String nombreUser, String nombreCanal){
        EpDAO dao = new EpDAO();
        return dao.addCanal(nombreUser, nombreCanal) > 0;
    }
}
