/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 *
 * @author oscar
 */
public class AsignaturasDAO {

    public List<Asignatura> getAllAsignaturas() {
        List<Asignatura> lista = null;
        DBConnection db = new DBConnection();
        Connection con = null;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Asignatura>> h = new BeanListHandler<Asignatura>(Asignatura.class);
            lista = qr.query(con, "select * FROM ASIGNATURAS", h);

        } catch (Exception ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return lista;
    }

    public int updateAsignatura(Asignatura a) {
        DBConnection db = new DBConnection();
        Connection con = null;
        int filas = 0;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            filas = qr.update(con, "UPDATE ASIGNATURAS SET NOMBRE = ?, CICLO = ?, CURSO = ? WHERE ID = ?", a.getNombre(), a.getCiclo(), a.getCurso(), a.getId());

        } catch (Exception ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return filas;
    }

    public Asignatura addAsignatura(Asignatura a) {
        DBConnection db = new DBConnection();
        Connection con = null;

        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();

            BigInteger id = qr.insert(con,
                    "INSERT INTO ASIGNATURAS (NOMBRE,CICLO,CURSO) VALUES(?,?,?)",
                    new ScalarHandler<BigInteger>(), a.getNombre(), a.getCiclo(), a.getCurso());

            a.setId(id.longValue());

        } catch (Exception ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return a;
    }

    public int delAsignatura(Asignatura a) {
        DBConnection db = new DBConnection();
        Connection con = null;
        int filas = 0;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            filas = qr.update(con, "DELETE FROM ASIGNATURAS WHERE ID = ?", a.getId());

        } catch (Exception ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return filas;
    }
}
