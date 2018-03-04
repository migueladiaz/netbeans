/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import config.Configuration;
import dao.ApiDAO;
import java.time.LocalDateTime;
import model.ApiKey;

/**
 *
 * @author Miguel
 */
public class FiltroServicios {

    public ApiKey comprobarKey(String k) {
        ApiDAO ad = new ApiDAO();
        return ad.comprobarKey(k);
    }

    public boolean comprobarDatosKey(ApiKey k) {
        ApiDAO ad = new ApiDAO();
        LocalDateTime fechaActual = LocalDateTime.now().minusMinutes(Configuration.getInstance().getMinutosValidarKey());
        LocalDateTime ultimaPeticion = k.getFECHA_ULTIMA_PETICION();
        boolean datosCorrectos;

        if (ultimaPeticion.isAfter(fechaActual)) {
            if(k.getNUM_PETICIONES() == Configuration.getInstance().getTotalConsultas()){
                datosCorrectos = false;
            }else{
                k.setNUM_PETICIONES(k.getNUM_PETICIONES()+1);
                ad.updatePeticiones(k);
                datosCorrectos = true;
            }
        } else {
            k.setFECHA_ULTIMA_PETICION(LocalDateTime.now());
            ad.updateHora(k);
            datosCorrectos = true;
        }
        return datosCorrectos;
    }
}
