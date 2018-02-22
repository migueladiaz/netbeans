/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import config.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
public class MailServicios {

    public void mandarMail(String to, String codigo, String subject) {
        try {

            Email email = new SimpleEmail();

            email.setHostName(Configuration.getInstance().getSmtpServer());
            email.setSmtpPort(Integer.parseInt(Configuration.getInstance().getSmtpPort()));
            email.setAuthentication(Configuration.getInstance().getMailFrom(), Configuration.getInstance().getMailPass());
            email.setStartTLSEnabled(true);
            email.setFrom(Configuration.getInstance().getMailFrom());
            email.setSubject(subject);
            email.setContent(Constantes.CUERPO_EMAIL_1+codigo+Constantes.CUERPO_EMAIL_2, "text/html");
            email.addTo(to);

            email.send();
        } catch (EmailException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MailServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
