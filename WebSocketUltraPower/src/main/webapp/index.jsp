
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/style.css">
        <link rel="stylesheet" href="assets/switch.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="125084952103-ggvd68hk194h03db20sgit6epeaf0m1d.apps.googleusercontent.com">
        <title>Chat</title>

    </head>
    <body>
        <div class="jumbotron">
            <h1>Jumbotron</h1>
            <h5>La magia de websocket</h5>
        </div>
        <div class="container">
            <div id="conteLogin">
                <h1>Login</h1>
                <form id="login" method="post">
                    <span>Nombre: </span>
                    <input type="text" id="user">
                    <br>
                    <br>
                    <span>Contraseña: </span>
                    <input type="password" id="pass">
                    <br>
                    <br>
                    <input class="btn btn-secondary" type="button" onclick="conectar()" value="Conectar">
                </form>

                <br>
                <br>
                <p>Inicia sesión con Google</p>
                <div id="botonGoogle" class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
            </div>
            <div id="conteChat">
                <div class="row">
                    <div id="conteUsuarios" class="col">
                        <div id="listaUsuarios"></div>
                    </div>
                    <div id="textoChat" class="col-xl-9">
                        <div id="output"></div>
                        <form id="chat">
                            <div class="input-group">
                                <select id="misCanales"></select>
                                <input id="myField" type="text" class="form-control" placeholder="Escribe tu mensaje...">
                                <span class="input-group-btn">
                                    <input id="enviar" class="btn btn-secondary" type="submit" value="Enviar">
                                </span>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="panel">
                    <table>
                        <tr>
                            <th>
                                <span class="texto">Guardar mensajes</span>
                            </th>
                            <th>
                                <span class="texto">Suscribirse a un canal</span>
                            </th>
                            <th>
                                <span class="texto">Crear un canal</span>
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <label class="tgl">
                                    <input id="guardar" type="checkbox"/>
                                    <span class="tgl_body">
                                        <span class="tgl_switch"></span>
                                        <span class="tgl_track">
                                            <span class="tgl_bgd"></span>
                                            <span class="tgl_bgd tgl_bgd-negative"></span>
                                        </span>
                                    </span>
                                </label>
                            </td>
                            <td>
                                <select id="listaCanales" onchange="suscripcion()"></select>
                            </td>
                            <td>
                                <button id="crearCanal" class="btn btn-secondary" onclick="crearCanal()">Crear</button>
                            </td>
                        </tr>
                        <tr>
                            <th colspan="3">
                                <span class="texto">Mostrar mensajes</span>
                            </th>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <span>Desde</span>
                                <input type="date" id="inicio">
                                <span>Hasta</span>
                                <input type="date" id="fin">
                                <button id="cargarMensajes" class="btn btn-secondary" onclick="cargar()">Cargar</button>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <button class="btn btn-secondary" onclick="signOut()">Sign out</button>

                    <script>
                        function signOut() {
                            var auth2 = gapi.auth2.getAuthInstance();
                            auth2.signOut().then(function () {
                                console.log('User signed out.');
                            });
                            websocket.close();
                        }
                    </script>
                </div>
            </div>
        </div>
        <script language="javascript" type="text/javascript" src="websocket.js">
        </script>
    </body>
</html>
