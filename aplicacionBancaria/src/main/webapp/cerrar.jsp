<%-- 
    Document   : cerrar
    Created on : 26-ene-2018, 20:11:37
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <title>Cerrar cuentas</title>
        <script src="../assets/scripts/javascriptCerrar.js"></script>
    </head>

    <body>
        <nav class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
            <div class="nav-item mr-auto">
                <span style="cursor: default" class="navbar-brand">${emailUsuario}</span>
            </div>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="abrir">Abrir cuentas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#">Cerrar cuentas</a>
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

            <h1 id="titulo">Cerrar <b>cuentas</b></h1>
            
            <div>
                <h3>Introduce el número de cuenta</h3>
                <span id="errorCuenta" class="errorFormulario"></span>
                <br>
                <input type="text" id="numCuenta" placeholder="Número de cuenta">
                <button id="comprobarCuenta" class="btn btn-dark">Comprobar</button>
            </div>
            <div class="row tablas">
                <div class="col-xl-12">
                    <table id="datosCuenta" class="table table-striped"></table>
                </div>
                <div id="titular1" class="titular col-md-6 col-12">
                    <h2>Titular 1</h2>
                    <table id="tablaDatos1">
                        <tr>
                            <td>
                                <span>Nombre y apellidos:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="nombre">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Dirección:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="direccion">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Teléfono:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="telefono">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Email:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="email" id="email">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Fecha de nacimiento:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="fechaNacimiento">
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="titular2" class="titular col-md-6 col-12">
                    <h2>Titular 2</h2>
                    <table id="tablaDatos2">
                        <tr>
                            <td>
                                <span>Nombre y apellidos:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="nombre2">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Dirección:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="direccion2">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Teléfono:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="telefono2">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Email:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="email" id="email2">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span>Fecha de nacimiento:</span>
                            </td>
                            <td>
                                <input class="datosTitular" type="text" id="fechaNacimiento2">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <h3 id="mensaje">La cuenta se ha borrado correctamente</h3>
            <br>
            <br>
            <button id="cerrar" class="btn btn-dark">Cerrar cuenta</button>
        </div>
    </body>
</html>
