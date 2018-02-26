<%-- 
    Document   : abrir
    Created on : 26-ene-2018, 20:11:28
    Author     : Miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="../assets/style/estilo.css">
        <link rel="stylesheet/less" type="text/css" href="../assets/style/estiloLess.less" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <title>Abrir cuentas</title>
        <script src="../assets/scripts/javascriptAbrir.js"></script>
    </head>

    <body>
        <nav class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
            <div class="nav-item mr-auto">
                <span style="cursor: default" class="navbar-brand">${emailUsuario}</span>
            </div>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Abrir cuentas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cerrar">Cerrar cuentas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="operaciones">Ingresar/Retirar</a>
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

            <h1 id="titulo">Abrir <b>cuentas</b></h1>
            
            <br>
            
            <div id="paneles">
                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <a id="1" class="nav-link active" data-toggle="tab" href="#menu1">Paso 1</a>
                    </li>
                    <li class="nav-item">
                        <a id="2" class="nav-link" data-toggle="tab" href="#menu2">Paso 2</a>
                    </li>
                    <li class="nav-item">
                        <a id="3" class="nav-link" data-toggle="tab" href="#menu3">Paso 3</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="menu1" class="container tab-pane active">
                        <h3>Introduce el número de cuenta</h3>
                        <span id="errorCuenta" class="errorFormulario"></span>
                        <br>
                        <input type="text" id="numCuenta" placeholder="Número de cuenta">
                        <br>
                        <button id="comprobarCuenta" class="btn btn-dark">Comprobar</button>
                    </div>
                    <div id="menu2" class="container tab-pane fade">
                        <h3>Introduce el DNI del titular de la cuenta</h3>
                        <span id="errorDni" class="errorFormulario"></span>
                        <br>
                        <input type="text" id="numDni" placeholder="DNI">
                        <br>
                        <button id="comprobarDni" class="btn btn-dark">Comprobar</button>
                    </div>
                    <div id="menu3" class="container tab-pane fade">
                        <h3>Introduce los datos del titular</h3>
                        <table id="tablaDatos">
                            <tr>
                                <td>
                                    <span>Nombre y apellidos:</span>
                                </td>
                                <td>
                                    <input class="datosTitular" type="text" id="nombre" placeholder="Nombre y apellidos">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span>Dirección:</span>
                                </td>
                                <td>
                                    <input class="datosTitular" type="text" id="direccion" placeholder="Dirección">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span>Teléfono:</span>
                                </td>
                                <td>
                                    <input class="datosTitular" type="text" id="telefono" placeholder="Teléfono">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span>Email:</span>
                                </td>
                                <td>
                                    <input class="datosTitular" type="email" id="email" placeholder="Email">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span>Fecha de nacimiento:</span>
                                </td>
                                <td>
                                    <input class="datosTitular" type="text" id="fechaNacimiento" placeholder="dd/mm/yyyy">
                                </td>
                            </tr>
                        </table>
                        <button id="enviarDatos" class="btn btn-dark">Enviar</button>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <a id="ant" href="#" onclick="anterior(event)">Anterior</a>
            <a id="sig" href="#" onclick="siguiente(event)">Siguiente</a>
            <hr>
        </div>
    </body>
</html>
