/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.AsignaturasDAO;
import model.Asignatura;
import java.util.List;

/**
 *
 * @author rafa
 */
public class AsignaturasServicios {

    public List<Asignatura> getAllAsignaturas() {
        AsignaturasDAO dao = new AsignaturasDAO();
        return dao.getAllAsignaturas();
    }

    public int updateAsignatura(Asignatura a) {
        AsignaturasDAO dao = new AsignaturasDAO();
        return dao.updateAsignatura(a);
    }
    
    public Asignatura addAsignatura(Asignatura a){
        AsignaturasDAO dao = new AsignaturasDAO();
        return dao.addAsignatura(a);
    }
    
    public int delAsignatura(Asignatura a){
        AsignaturasDAO dao = new AsignaturasDAO();
        return dao.delAsignatura(a);
    }
}
