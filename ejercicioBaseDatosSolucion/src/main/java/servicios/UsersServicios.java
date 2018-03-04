/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import config.Configuration;
import dao.UsersDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import utils.Constantes;
import utils.PasswordHash;
import utils.Utils;

/**
 *
 * @author Miguel Angel Diaz
 */
public class UsersServicios {

    public int registrar(User user) {
        UsersDAO dao = new UsersDAO();

        boolean existe = this.comprobarNombres(user.getNombre());

        int alta = -1;
        if (!existe) {
            try {
                user.setPassword(PasswordHash.getInstance().createHash(user.getPassword()));
                user.setCodigo_activacion(Utils.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));
                LocalDateTime fechaActual = LocalDateTime.now();
                user.setFecha_activacion(fechaActual);
                user = dao.registrar(user);
                if (user != null) {
                    MailServicios mail = new MailServicios();
                    mail.mandarMail(user.getEmail(), Constantes.LINK_EMAIL + user.getCodigo_activacion(), Constantes.ASUNTO_EMAIL);
                    alta = 1;
                }

            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UsersServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alta = 2;
        }
        return alta;
    }

    public boolean comprobarNombres(String nombreUser) {
        UsersDAO dao = new UsersDAO();
        return dao.comprobarNombres(nombreUser);
    }

    public int login(User user) {
        int valido = -1;
        try {

            UsersDAO dao = new UsersDAO();
            User userDB = dao.getUserByNombre(user.getNombre());
            boolean passCorrecta = PasswordHash.getInstance().validatePassword(user.getPassword(), userDB.getPassword());
            if (passCorrecta == true) {
                if(userDB.getActivo() == true){
                    valido = 1;
                }else{
                    valido = 2;
                }
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UsersServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valido;
    }

    public int activar(String codigo) {
        UsersDAO dao = new UsersDAO();
        User userDB = dao.getUserByCodigoActivacion(codigo);

        int activar;
        
        if(userDB==null){
            activar = -1;
        }else if (!userDB.getActivo()) {
            LocalDateTime fechaActivacion = userDB.getFecha_activacion();
            LocalDateTime fechaActual = LocalDateTime.now().minusMinutes(Configuration.getInstance().getMinutosParaValidar());
            if (fechaActivacion.isAfter(fechaActual)) {
                activar = dao.updateValido(userDB);
            } else {
                activar = dao.borrarUserByCodigoActivacion(userDB);
            }
        } else {
            activar = 2;
        }
        return activar;
    }

}
