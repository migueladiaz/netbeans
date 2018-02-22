/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Miguel
 */
public class Movimiento {
    private String mo_fec;
    private String mo_des;
    private int mo_imp;

    public String getMo_fec() {
        return mo_fec;
    }

    public void setMo_fec(String mo_fec) {
        this.mo_fec = mo_fec;
    }

    public String getMo_des() {
        return mo_des;
    }

    public void setMo_des(String mo_des) {
        this.mo_des = mo_des;
    }

    public int getMo_imp() {
        return mo_imp;
    }

    public void setMo_imp(int mo_imp) {
        this.mo_imp = mo_imp;
    }
}
