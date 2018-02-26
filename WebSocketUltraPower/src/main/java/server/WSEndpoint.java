/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import model.Mensaje;
import model.MessageDecoder;
import model.MessageEncoder;
import servicios.EPServicios;
import servicios.IdTokenVerifierAndParser;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
@ServerEndpoint(value = "/websocket/{user}/{pass}",decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class WSEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String nombre, @PathParam("pass") String pass,
            EndpointConfig config) {

        if (nombre.equals("google")) {
            session.getUserProperties().put("login", "NO");
        } else {
            try {
                EPServicios es = new EPServicios();

                if (es.comprobarPass(nombre, pass)) {
                    session.getUserProperties().put("login", "OK");
                    session.getUserProperties().put("user", nombre);
                    Mensaje m = new Mensaje();
                    m.setTipo("info");
                    m.setMensaje(nombre+Constantes.MENSAJE_CONECTADO);
                    for (Session sesionesMandar : session.getOpenSessions()) {
                        if (!session.equals(sesionesMandar)) {
                            sesionesMandar.getBasicRemote().sendObject(m);
                            //solo si no se usan encoder y decoder
                            //sesionesMandar.getBasicRemote().sendText(lo que sea);
                        }
                    }
                } else {
                    session.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnMessage
    public void echoText(Mensaje mensaje, Session sessionQueManda) {
        
        EPServicios es = new EPServicios();
        
        if (sessionQueManda.getUserProperties().get("login").equals("OK")) {
            try {
                if(mensaje.getGuardar()==true){
                    es.guardarMensaje(mensaje);
                }
                switch(mensaje.getTipo()){
                    case "texto":
                        for (Session sesionesMandar : sessionQueManda.getOpenSessions()) {
                            if (!sessionQueManda.equals(sesionesMandar)) {
                                sesionesMandar.getBasicRemote().sendObject(mensaje);
                            }
                        }
                        break;
                        
                    case "canales":
                        ArrayList canales = es.getCanales();
                        ObjectMapper mapper = new ObjectMapper();
                        mensaje.setMensaje(mapper.writeValueAsString(canales));
                        for (Session sesionesMandar : sessionQueManda.getOpenSessions()) {
                            if (sessionQueManda.equals(sesionesMandar)) {
                                sesionesMandar.getBasicRemote().sendObject(mensaje);
                            }
                        }
                        break;
                }
            } catch (Exception ex) {
                Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                String idToken = mensaje.getMensaje();
                GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
                if(!es.comprobarUserGoogle(payLoad.getEmail())){
                    es.addUserGoogle(payLoad.getEmail());
                }
                String name = (String) payLoad.get("name");
                sessionQueManda.getUserProperties().put("user", name);
                sessionQueManda.getUserProperties().put("login", "OK");
                mensaje.setTipo("info");
                mensaje.setMensaje(name+Constantes.MENSAJE_CONECTADO);
                for (Session sesionesMandar : sessionQueManda.getOpenSessions()) {
                    if (!sessionQueManda.equals(sesionesMandar)) {
                        sesionesMandar.getBasicRemote().sendObject(mensaje);
                    }
                }
            } catch (Exception ex) {
                try {
                    sessionQueManda.close();
                } catch (IOException ex1) {
                    Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        if(session.getUserProperties().get("user")!=null){
            Mensaje m = new Mensaje();
            m.setTipo("info");
            m.setMensaje((String)session.getUserProperties().get("user")+Constantes.MENSAJE_DESCONECTADO);
            for (Session s : session.getOpenSessions()) {
                try {
                    s.getBasicRemote().sendObject(m);
                } catch (Exception ex) {
                    Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
