<%-- 
    Document   : operaciones
    Created on : 26-ene-2018, 13:53:10
    Author     : Miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/style/estilo.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <meta charset="UTF-8">
        <title>Operaciones</title>
        <script src="assets/scripts/javascript.js"></script>
    </head>

    <body>
        <nav class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="abrir.jsp">Abrir cuentas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cerrar.jsp">Cerrar cuentas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#">Ingresar/Retirar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="listado.jsp">Listado de movimientos</a>
                </li>
            </ul>
        </nav>
        <div class="container" id="cuerpo">

            <h1 id="titulo">Ingresos y <b>reintegros</b></h1>

            <div id="formulario">
                <form method="post" id="datos">
                    <span id="errorCuenta" class="errorFormulario">El número de cuenta no es correcto.</span>
                    <br>
                    <lablel for="cuenta">Nº de cuenta: </lablel>
                    <input type="text" id="cuenta" name="cuenta">
                    <br>
                    <br>
                    <lablel for="cuenta">Importe: </lablel>
                    <input type="text" id="numImporte" name="numImporte" value="10" disabled>
                    <input type="range" id="rangoImporte" name="rangoImporte" min="10" max="1000" step="10" value="10" oninput="numImporte.value = rangoImporte.value">
                    <br>
                    <br>
                    <input type="button" id="boton" value="Ingresar" class="btn btn-lg btn-block btn-secondary">
                    <input type="button" id="boton2" value="Retirar" class="btn btn-lg btn-block btn-dark">
                </form>
            </div>

        </div>
    </body>
</html>
