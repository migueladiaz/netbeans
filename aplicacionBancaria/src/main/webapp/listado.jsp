<%-- 
    Document   : listado
    Created on : 26-ene-2018, 13:19:10
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
        <title>Movimientos</title>
        <script src="../assets/scripts/javascriptListado.js"></script>
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
                    <a class="nav-link" href="operaciones">Ingresar/Retirar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#">Listado de movimientos</a>
                </li>
            </ul>
            <div class="nav-item ml-auto">
                <a class="nav-link" href="../login?accion=logout"><i class="fa fa-sign-out"></i> Cerrar sesión</a>
            </div>
        </nav>
        
        <div class="container" id="cuerpo">

            <h1 id="titulo">Listado de <b>movimientos</b></h1>

            <div id="formularioListado">
                <form method="post" id="datos">
                    <span id="errorCuenta" class="errorFormulario">El número de cuenta no es correcto.</span>
                    <br>
                    <lablel for="cuenta">Nº de cuenta: </lablel>
                    <input type="text" id="cuenta" name="cuenta">
                    <br>
                    <br>
                    <span id="errorFecha" class="errorFormulario">Las fechas seleccionadas no son correctas.</span>
                    <br>
                    <lablel for="inicio">Desde: </lablel>
                    <input type="date" id="inicio" name="inicio" class="fecha">
                    <lablel for="fin">Hasta: </lablel>
                    <input type="date" id="fin" name="fin" class="fecha">
                    <br>
                    <br>
                    <input type="button" id="botonListado" value="Buscar" class="btn btn-lg btn-block btn-dark">
                </form>
            </div>
            <div id="contTabla" class="col-xl-9">
                <table class="table table-striped" id="tabla"></table>
            </div>
            <h1 id="error" class="oculto"></h1>
        </div>
    </body>
</html>
