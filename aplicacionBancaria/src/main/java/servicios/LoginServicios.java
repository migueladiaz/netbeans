package servicios;

import DAO.LoginDAO;
import config.Configuration;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import utils.Constantes;
import utils.ConstantesLogin;
import utils.PasswordHash;
import utils.Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Miguel
 */
public class LoginServicios {

    public boolean comprobarEmail(String email) {
        LoginDAO ld = new LoginDAO();
        return ld.comprobarEmail(email);
    }

    public boolean registro(String email, String pass) {
        LoginDAO ld = new LoginDAO();
        boolean registrado = false;
        try {
            String hash = PasswordHash.getInstance().createHash(pass);
            String codigo = Utils.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo());
            User u = new User();
            u.setEmail(email);
            u.setPass(hash);
            u.setCodigo(codigo);
            if (ld.registro(u)) {
                MailServicios mail = new MailServicios();
                mail.mandarMail(email, codigo, ConstantesLogin.ASUNTO_EMAIL);
                registrado = true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginServicios.class.getName()).log(Level.SEVERE, null, ex);
            registrado = false;
        }
        return registrado;
    }

    public boolean activar(String codigo) {
        LoginDAO ld = new LoginDAO();
        User userDB = ld.getUserByCodigo(codigo);

        boolean activado;

        if (userDB == null) {
            activado = false;
        } else if (!userDB.getActivado()) {
            if (ld.activarUserByCodigo(codigo)) {
                activado = true;
            } else {
                activado = false;
            }
        } else {
            activado = false;
        }
        return activado;
    }

    public int login(String email, String pass) {
        int valido;

        try {
            LoginDAO ld = new LoginDAO();
            User userDB = ld.getUserByEmail(email);
            if (userDB != null) {
                boolean passCorrecta = PasswordHash.getInstance().validatePassword(pass, userDB.getPass());
                
                if (passCorrecta == true) {
                    if (userDB.getActivado() == true) {
                        valido = 1;
                    } else {
                        valido = 2;
                    }
                } else {
                    valido = 0;
                }
            } else {
                valido = 0;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginServicios.class.getName()).log(Level.SEVERE, null, ex);
            valido = -1;
        }
        return valido;
    }
}
