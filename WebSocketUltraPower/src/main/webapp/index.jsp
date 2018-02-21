
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/style.css">
        <link rel="stylesheet" href="assets/prueba.css">
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
        <div class="container col-xl-6">
            <div id="conteLogin">
                <h1>Login</h1>
                <form id="login" method="post">
                    <span>Nombre: </span>
                    <input type="text" id="user">
                    <br>
                    <br>
                    <span>Contraseña: </span>
                    <input type="text" id="pass">
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
                
                <div id="textoChat">
                    <div id="output"></div>
                    <form id="chat">
                        <div class="input-group">
                            <input id="myField" type="text" class="form-control" placeholder="Escribe tu mensaje...">
                            <span class="input-group-btn">
                                <input id="enviar" class="btn btn-secondary" type="submit" value="Enviar">
                            </span>
                        </div>
                    </form>
                </div>

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
            <label class="tgl">
                <input type="checkbox" checked/>
                <span class="tgl_body">
                    <span class="tgl_switch"></span>
                    <span class="tgl_track">
                        <span class="tgl_bgd"></span>
                        <span class="tgl_bgd tgl_bgd-negative"></span>
                    </span>
                </span>
            </label>
        </div>
        <script language="javascript" type="text/javascript" src="websocket.js">
        </script>
    </body>
</html>
