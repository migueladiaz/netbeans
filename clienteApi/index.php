<!DOCTYPE html>
<html>
    <head>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet"> 
        <link rel="stylesheet/less" type="text/css" href="assets/style/estiloLess.less" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js"></script>
        <script src="assets/scripts/javascriptApi.js"></script>
        <title>Facturas</title>
    </head>
    <body>
        <header id="cabecera">
            <div id="titulo">
                <span>Aplicación bancaria</span>
            </div>
            <div id="subtitulo">
                <span>La magia de <b>Less</b></span>
            </div>
        </header>
        <div id="form-cont">
            <div class="form-top">
                <div class="form-top-left">
                    <p>FACTURAS</p>
                    <p>Rellena los datos:</p>
                </div>
                <div class="form-top-right">
                    <i class="fa fa-pencil"></i>
                </div>
            </div>
            <div class="form-bot">
                <br>
                <hr>
                <div class="form-group">
                    <span class="error" id="errorCuenta"></span>
                    <br>
                    <input type="text" id="numCuenta" name="numCuenta" placeholder="Nº de cuenta...">
                </div>
                <div class="form-group">
                    <span class="error" id="errorImporte"></span>
                    <br>
                    <input type="text" id="importe" name="importe" placeholder="Importe...">
                </div>
                <div class="form-group">
                    <br>
                    <input type="text" id="descripcion" name="descripcion" placeholder="Descripción...">
                </div>
                <button id="devolver">Devolver</button>
                <button id="cobrar">Cobrar</button>
            </div>
            <h2 id="info"></h2>
        </div>
    </body>
</html>
