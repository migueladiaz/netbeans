<!DOCTYPE html>
<html>
    <head>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link href="https://fonts.googleapis.com/css?family=Poiret+One" rel="stylesheet">
        <link rel="stylesheet/less" type="text/css" href="assets/style/estiloLess.less" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js"></script>
        <script src="assets/scripts/javascriptApi.js"></script>
        <title>Cobrar facturas</title>
    </head>
    <body>
        <header id="cabecera">
            <div id="titulo">
                <span>Aplicación <b>bancaria</b></span>
            </div>
            <br>
            <div id="subtitulo">
                <span><b>La magia</b> de Less</span>
            </div>
        </header>
        <div id="form-cont">
            <div class="form-top">
                <div class="form-top-left">
                    <h3>Cobrar facturas</h3>
                    <p>Introduce los datos del cliente:</p>
                </div>
                <div class="form-top-right">
                    <i class="fa fa-pencil"></i>
                </div>
            </div>
            
            <div class="form-bot">
                <form action="cliente.php" method="post" id="formulario">
                    <input type="hidden" name="accion" value="addMovimiento">
                    <div class="form-group">
                        <span id="errorCuenta"></span>
                        <br>
                        <input type="text" id="numCuenta" name="numCuenta" placeholder="Nº de cuenta...">
                    </div>
                    <div class="form-group">
                        <span id="errorImporte"></span>
                        <br>
                        <input type="text" id="importe" name="importe" placeholder="Importe...">
                    </div>
                    <div class="form-group">
                        <br>
                        <input type="text" id="descripcion" name="descripcion" placeholder="Descripcion...">
                    </div>
                    <button id="ingreso">Devolver</button>
                    <button id="reintegro">Cobrar</button>
                </form>
            </div>
            <?php
            if(isset($info)){
                echo $info;
            }
            ?>
        </div>
    </body>
</html>
