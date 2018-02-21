/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.IOException;
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
import servicios.EPServicios;
import servicios.IdTokenVerifierAndParser;

/**
 *
 * @author Miguel
 */
@ServerEndpoint(value = "/websocket/{user}/{pass}")
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
                    for (Session sesionesMandar : session.getOpenSessions()) {
                        if (!session.equals(sesionesMandar)) {
                            sesionesMandar.getBasicRemote().sendText(session.getUserProperties().get("user") + " se ha conectado");
                        }
                    }
                } else {
                    session.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnMessage
    public void echoText(String mensaje, Session sessionQueManda) {
        
        EPServicios es = new EPServicios();
        
        if (sessionQueManda.getUserProperties().get("login").equals("OK")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Mensaje m = mapper.readValue(mensaje, new TypeReference<Mensaje>() {});
                if(m.getGuardar()==true){
                    es.guardarMensaje(m);
                }
                for (Session sesionesMandar : sessionQueManda.getOpenSessions()) {

                    if (!sessionQueManda.equals(sesionesMandar)) {
                        sesionesMandar.getBasicRemote().sendText(sessionQueManda.getUserProperties().get("user") + ": " + m.getMensaje());
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                String idToken = mensaje;
                GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
                es.addUserGoogle(payLoad.getEmail());
                String name = (String) payLoad.get("name");
                sessionQueManda.getUserProperties().put("user", name);
                sessionQueManda.getUserProperties().put("login", "OK");
                for (Session sesionesMandar : sessionQueManda.getOpenSessions()) {
                    if (!sessionQueManda.equals(sesionesMandar)) {
                        sesionesMandar.getBasicRemote().sendText(sessionQueManda.getUserProperties().get("user") + " se ha conectado");
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
        for (Session s : session.getOpenSessions()) {
            try {
                s.getBasicRemote().sendText(session.getUserProperties().get("user").toString() + " se ha desconectado");
            } catch (IOException ex) {
                Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
