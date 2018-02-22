/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import freemarker.template.TemplateExceptionHandler;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Miguel
 */
public class Configuration {

    private static Configuration config;

    private Configuration() {

    }

    public static Configuration getInstance() {

        return config;
    }

    public static Configuration getInstance(InputStream file, ServletContext sc) {
        if (config == null) {
            Yaml yaml = new Yaml();
            config = (Configuration) yaml.loadAs(file, Configuration.class);
            config.setFreeMarker(new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23));
            config.getFreeMarker().setServletContextForTemplateLoading(sc, "WEB-INF/templates");
            config.getFreeMarker().setDefaultEncoding("UTF-8");
            config.getFreeMarker().setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
            config.getFreeMarker().setLogTemplateExceptions(false);
        }
        return config;
    }

    private String urlDB;
    private String driverDB;
    private String userDB;
    private String passDB;
    private String mailFrom;
    private String smtpServer;
    private String smtpPort;
    private String mailPass;
    private int longitudCodigo;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getMailPass() {
        return mailPass;
    }

    public void setMailPass(String mailPass) {
        this.mailPass = mailPass;
    }
    
    public int getLongitudCodigo() {
        return longitudCodigo;
    }

    public void setLongitudCodigo(int longitudCodigo) {
        this.longitudCodigo = longitudCodigo;
    }

    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    public String getDriverDB() {
        return driverDB;
    }

    public void setDriverDB(String driverDB) {
        this.driverDB = driverDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPassDB() {
        return passDB;
    }

    public void setPassDB(String passDB) {
        this.passDB = passDB;
    }

    private freemarker.template.Configuration freeMarker;

    public freemarker.template.Configuration getFreeMarker() {
        return freeMarker;
    }

    public void setFreeMarker(freemarker.template.Configuration freeMarker) {
        this.freeMarker = freeMarker;
    }
}
