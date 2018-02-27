/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import config.ServletAwareConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import model.Mensaje;
import utils.MessageDecoder;
import utils.MessageEncoder;
import servicios.EPServicios;
import servicios.IdTokenVerifierAndParser;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
@ServerEndpoint(value = "/websocket/{user}/{pass}",decoders = MessageDecoder.class, encoders = MessageEncoder.class, configurator = ServletAwareConfig.class)
public class WSEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String nombre, @PathParam("pass") String pass, EndpointConfig config) {
        
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpsession");
        httpSession.setAttribute("login", "ok");

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
                    
                    ArrayList<String> usuariosConectados = new ArrayList();
                    
                    for (Session sesionesMandar : session.getOpenSessions()) {
                        if (!session.equals(sesionesMandar)) {
                            sesionesMandar.getBasicRemote().sendObject(m);
                            //solo si no se usan encoder y decoder
                            //sesionesMandar.getBasicRemote().sendText(lo que sea);
                        }
                        usuariosConectados.add((String)sesionesMandar.getUserProperties().get("user"));
                    }
                    
                    httpSession.setAttribute("usuarios", usuariosConectados);
                } else {
                    session.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnMessage
    public void echoText(Mensaje mensaje, Session sessionQueManda, EndpointConfig config) {
        
        EPServicios es = new EPServicios();
        
        if (sessionQueManda.getUserProperties().get("login").equals("OK")) {
            try {
                if(mensaje.getGuardar()==true){
                    es.guardarMensaje(mensaje);
                }
                
                ObjectMapper mapper = new ObjectMapper();
                
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
                        mensaje.setMensaje(mapper.writeValueAsString(canales));
                        for (Session sesionesMandar : sessionQueManda.getOpenSessions()) {
                            if (sessionQueManda.equals(sesionesMandar)) {
                                sesionesMandar.getBasicRemote().sendObject(mensaje);
                            }
                        }
                        break;
                        
                    case "cargar":
                        ArrayList mensajes = es.getMensajes(mensaje);
                        
                        if(mensajes.isEmpty()){
                            mensaje.setMensaje("error");
                        }else{
                            mensaje.setMensaje(mapper.writeValueAsString(mensajes));
                        }
                        
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
                HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpsession");
                ArrayList<String> usuariosConectados = new ArrayList();
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
                    usuariosConectados.add((String)sesionesMandar.getUserProperties().get("user"));
                }
                httpSession.setAttribute("usuarios", usuariosConectados);
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
    public void onClose(Session session, EndpointConfig config) {
        if(session.getUserProperties().get("user")!=null){
            HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpsession");
            Mensaje m = new Mensaje();
            m.setTipo("info");
            m.setMensaje((String)session.getUserProperties().get("user")+Constantes.MENSAJE_DESCONECTADO);
            ArrayList<String> usuariosConectados = new ArrayList();
            for (Session s : session.getOpenSessions()) {
                if (!s.equals(session)) {
                    usuariosConectados.add((String)s.getUserProperties().get("user"));
                }
                
                try {
                    s.getBasicRemote().sendObject(m);
                } catch (Exception ex) {
                    Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            httpSession.setAttribute("usuarios", usuariosConectados);
        }
    }

}
