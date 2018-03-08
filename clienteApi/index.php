<!DOCTYPE html>
<html>
    <head>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="assets/scripts/javascriptApi.js"></script>
        <title>Cobrar facturas</title>
    </head>
    <body>
        <span>Aplicación <b>bancaria</b></span>
        <br>
        <span>La magia de sass</span>
        
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
                    <input type="hidden" id="accion" value="reintegro">
                    <div class="form-group">
                        <span id="errorCuenta"></span>
                        <br>
                        <input type="text" id="numCuenta" placeholder="Nº de cuenta...">
                    </div>
                    <div class="form-group">
                        <span id="errorImporte"></span>
                        <br>
                        <input type="text" id="importe" placeholder="Importe...">
                    </div>
                    <div class="form-group">
                        <br>
                        <input type="text" id="descripcion" placeholder="Descripcion...">
                    </div>
                    <button type="submit">Enviar</button>
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
