<%-- 
    Document   : operaciones
    Created on : 26-ene-2018, 13:53:10
    Author     : Miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet/less" type="text/css" href="../assets/style/estiloLess.less" />
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <title>Operaciones</title>
        <script src="../assets/scripts/javascriptOperaciones.js"></script>
    </head>

    <body>
        <div class="jumbotron">
            <h1>Aplicación bancaria</h1>
            <h4>La magia de Less</h4>
        </div>
        <nav class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
            <div class="nav-item mr-auto">
                <span style="cursor: default" class="navbar-brand">${emailUsuario}</span>
            </div>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="abrir">Abrir cuentas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cerrar">Cerrar cuentas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#">Ingresar/Retirar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="listado">Listado de movimientos</a>
                </li>
            </ul>
            <div class="nav-item ml-auto">
                <a class="nav-link" href="../login?accion=logout"><i class="fa fa-sign-out"></i> Cerrar sesión</a>
            </div>
        </nav>
        <div class="container" id="cuerpo">

            <h1 id="titulo">Ingresos y <b>reintegros</b></h1>

            <div id="formulario">
                <form method="post" id="datos">
                    <span id="errorCuenta" class="errorFormulario"></span>
                    <br>
                    <lablel for="numCuenta">Nº de cuenta: </lablel>
                    <input type="text" id="numCuenta" name="numCuenta">
                    <br>
                    <br>
                    <span id="errorImporte" class="errorFormulario"></span>
                    <br>
                    <lablel for="numImporte">Importe: </lablel>
                    <input type="text" id="numImporte" class="imp" name="numImporte" value="0" oninput="rangoImporte.value = numImporte.value">
                    <input type="range" id="rangoImporte" class="imp" name="rangoImporte" min="10" max="1000" step="10" value="0" oninput="numImporte.value = rangoImporte.value">
                    <br>
                    <br>
                    <lablel for="descripcion">Descripción: </lablel>
                    <input type="text" id="descripcion" name="descripcion" maxlength="25">
                    <span id="contador" class="saldo">25</span>
                    <span class="saldo">/ 25</span>
                    <br>
                    <br>
                    <input type="button" id="ingreso" value="Ingresar" class="btn btn-lg btn-secondary enviar">
                    <input type="button" id="reintegro" value="Retirar" class="btn btn-lg btn-dark enviar">
                </form>
            </div>
            <br>
            <h3 id="info" class="oculto"></h3>
        </div>
    </body>
</html>
