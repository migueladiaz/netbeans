<%-- 
    Document   : login
    Created on : 26-ene-2018, 20:50:27
    Author     : Miguel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/style/estilo.css">
        <link rel="stylesheet/less" type="text/css" href="assets/style/estiloLess.less" />
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="assets/scripts/javascriptLogin.js"></script>
        <title>Iniciar sesión</title>
    </head>

    <body>
        <nav class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="#">La magia de la aplicación bancaria</a>
                </li>
            </ul>
        </nav>
        <div class="container cuerpologin" id="cuerpo">

            <h1 id="mensajeinfo"><c:out value="${mensaje}"></c:out></h1>
            
            <div id="divlogin" class="col-xl-4">
                <h1 id="titulo">Iniciar <b>sesión</b></h1>
                <br>
                <span id="errorLogin"><c:out value="${mensaje2}"></c:out></span>
                <br>
                <br>
                <form action="login?accion=login" method="post" onsubmit="return validarLogin()">
                    <table>
                        <tr>
                            <td><label for="emaillogin">Email </label></td>
                            <td><input type="email" id="emaillogin" name="emaillogin" placeholder=" name@example.com" required></td>
                        </tr>
                        <tr>
                            <td><label for="passlogin">Contraseña </label></td>
                            <td><input type="password" id="passlogin" name="passlogin" required></td>
                        </tr>
                    </table>
                    <input type="submit" id="botonLogin" value="Iniciar" class="btn btn-lg btn-block btn-dark">
                    <br>
                    <span><b>Regístrate </b>haciendo click <a class="cambiarFormulario" href="#" onclick="mostrarRegistro()">aqui</a></span>
                </form>
            </div>

            <div id="divregistro" class="col-xl-4">
                <h1 id="titulo">Registrar <b>cuenta</b></h1>
                
                <form action="login?accion=registro" method="post" onsubmit="return validarRegistro()">
                    <input type="hidden" id="emailDisponible">
                    <span id="errorDni" class="errorFormulario">El DNI introducido no es correcto</span>
                    <br>
                    <span id="errorEmail" class="errorFormulario">El email no está disponible</span>
                    <br>
                    <table>
                        <tr>
                            <td><label for="emailregistro">Email </label></td>
                            <td><input type="email" id="emailregistro" name="emailregistro" placeholder=" name@example.com" required></td>
                        </tr>
                        <tr>
                            <td><label for="passregistro">Contraseña </label></td>
                            <td><input type="password" id="passregistro" name="passregistro" required></td>
                        </tr>
                    </table>
                    <input type="submit" id="botonRegistro" value="Registrarse" class="btn btn-lg btn-block btn-dark">
                    <br>
                    <span><b>Inicia </b>sesión haciendo click <a class="cambiarFormulario" href="#" onclick="mostrarLogin()">aqui</a></span>
                </form>
            </div>
        </div>
    </body>
</html>
