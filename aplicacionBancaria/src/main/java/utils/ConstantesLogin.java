/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Miguel
 */
public class ConstantesLogin {

    public static final String CUERPO_EMAIL_1 = "<html>"
            + "<body>"
            + "<h1>Registro <strong>completado</strong></h1>"
            + "<p>Muchas gracias por registrarte.</p>"
            + "<p>Haz click en el siguiente enlace para activar tu cuenta.</p>"
            + "<a href='http://localhost:8080/aplicacionBancaria/login?accion=activar&codigo=";
    public static final String CUERPO_EMAIL_2 = "'>Activar</a>"
            + "</body>"
            + "</html>";
    public static final String ASUNTO_EMAIL = "Registro aplicación bancaria";

    public static final String OCUPADO = "ocupado";
    public static final String DISPONIBLE = "disponible";

    public static final String REGISTRO_COMPLETO = "Usuario registrado con éxito. Te hemos enviado un email para activar tu cuenta.";
    public static final String CUENTA_ACTIVADA = "Tu cuenta ha sido activada.";

    public static final String ERROR_USER_PASS = "Usuario y contraseña incorrectos";
    public static final String ERROR_CUENTA_ACTIVADA = "Tu cuenta no está activada.";

    public static final String PARAMETRO_EMAIL = "email";
    public static final String PARAMETRO_EMAIL_LOGIN = "emaillogin";
    public static final String PARAMETRO_EMAIL_REGISTRO = "emailregistro";
    public static final String PARAMETRO_PASS_LOGIN = "passlogin";
    public static final String PARAMETRO_PASS_REGISTRO = "passregistro";
    public static final String PARAMETRO_CODIGO = "codigo";

    public static final String ATRIBUTO_MENSAJE = "mensaje";
    public static final String ATRIBUTO_MENSAJE2 = "mensaje2";
    public static final String ATRIBUTO_EMAIL_USUARIO = "emailUsuario";

    public static final String CASE_COMPROBAR_EMAIL = "comprobarEmail";
    public static final String CASE_LOGIN = "login";
    public static final String CASE_REGISTRO = "registro";
    public static final String CASE_ACTIVAR = "activar";
    public static final String CASE_LOGOUT = "logout";
}
