/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Miguel
 */
public class ApiKey {
    private int ID;
    private String APIKEY;
    private int NUM_PETICIONES;
    private LocalDateTime FECHA_ULTIMA_PETICION;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAPIKEY() {
        return APIKEY;
    }

    public void setAPIKEY(String APIKEY) {
        this.APIKEY = APIKEY;
    }

    public int getNUM_PETICIONES() {
        return NUM_PETICIONES;
    }

    public void setNUM_PETICIONES(int NUM_PETICIONES) {
        this.NUM_PETICIONES = NUM_PETICIONES;
    }

    public LocalDateTime getFECHA_ULTIMA_PETICION() {
        return FECHA_ULTIMA_PETICION;
    }

    public void setFECHA_ULTIMA_PETICION(LocalDateTime FECHA_ULTIMA_PETICION) {
        this.FECHA_ULTIMA_PETICION = FECHA_ULTIMA_PETICION;
    }
    
}
