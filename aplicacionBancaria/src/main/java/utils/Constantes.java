/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Miguel Angel Diaz
 */
public class Constantes {
    public static final String CUERPO_EMAIL_1 ="<html>"
                                            + "<body>"
                                            + "<h1>Registro <strong>completado</strong></h1>"
                                            + "<p>Muchas gracias por registrarte.</p>"
                                            + "<p>Haz click en el siguiente enlace para activar tu cuenta.</p>"
                                            + "<a href='http://localhost:8080/aplicacionBancaria/login?accion=activar&codigo=";
    public static final String CUERPO_EMAIL_2 = "'>Activar</a>"
                                            + "</body>"
                                            + "</html>";
    public static final String ASUNTO_EMAIL = "Registro aplicación bancaria";
    public static final String PINTAR_LOGIN = "login.jsp";
    public static final String OCUPADO = "ocupado";
    public static final String DISPONIBLE = "disponible";
    public static final String ERROR = "Ha ocurrido un error.";
    public static final String REGISTRO_COMPLETO = "Usuario registrado con éxito. Te hemos enviado un email para activar tu cuenta.";
    public static final String CUENTA_ACTIVADA = "Tu cuenta ha sido activada.";
    public static final String ERROR_USER_PASS = "Usuario y contraseña incorrectos";
    public static final String ERROR_CUENTA_ACTIVADA = "Tu cuenta no está activada.";
    public static final String PINTAR_LISTADO = "/listado.jsp";
    public static final String PINTAR_ABRIR = "/abrir.jsp";
    public static final String ERROR_CUENTA_NO_EXISTE = "El número de cuenta no existe";
    public static final String ERROR_CUENTA_YA_EXISTE = "El número de cuenta ya existe";
    public static final String ERROR_NO_MOVIMIENTOS = "No hay movimientos";
    public static final String PEDIR_DATOS = "pedirDatos";
}
