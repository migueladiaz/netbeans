/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Miguel
 */
public class Cliente {
    private String cl_dni;
    private String cl_nom;
    private String cl_dir;
    private int cl_tel;
    private String cl_ema;
    private LocalDate cl_fna;
    private LocalDate cl_fcl;
    private int cl_ncu;
    private int cl_sal;

    public String getCl_dni() {
        return cl_dni;
    }

    public void setCl_dni(String cl_dni) {
        this.cl_dni = cl_dni;
    }

    public String getCl_nom() {
        return cl_nom;
    }

    public void setCl_nom(String cl_nom) {
        this.cl_nom = cl_nom;
    }

    public String getCl_dir() {
        return cl_dir;
    }

    public void setCl_dir(String cl_dir) {
        this.cl_dir = cl_dir;
    }

    public int getCl_tel() {
        return cl_tel;
    }

    public void setCl_tel(int cl_tel) {
        this.cl_tel = cl_tel;
    }

    public String getCl_ema() {
        return cl_ema;
    }

    public void setCl_ema(String cl_ema) {
        this.cl_ema = cl_ema;
    }

    public LocalDate getCl_fna() {
        return cl_fna;
    }

    public void setCl_fna(LocalDate cl_fna) {
        this.cl_fna = cl_fna;
    }

    public LocalDate getCl_fcl() {
        return cl_fcl;
    }

    public void setCl_fcl(LocalDate cl_fcl) {
        this.cl_fcl = cl_fcl;
    }

    public int getCl_ncu() {
        return cl_ncu;
    }

    public void setCl_ncu(int cl_ncu) {
        this.cl_ncu = cl_ncu;
    }

    public int getCl_sal() {
        return cl_sal;
    }

    public void setCl_sal(int cl_sal) {
        this.cl_sal = cl_sal;
    }
}
