<?php

require 'vendor/autoload.php';

use GuzzleHttp\Client;

$client = new Client();
$movimiento = new \stdClass;
$uri = 'http://localhost:8080/ejercicioBaseDatos/rest/restOperaciones';

if (isset($_REQUEST["accion"])) {
    
    $accion = $_REQUEST["accion"];
    $numCuenta = $_REQUEST["numCuenta"];
    $importe = $_REQUEST["importe"];
    $descripcion = $_REQUEST["descripcion"];
    

    $movimiento->mo_ncu = $numCuenta;
    $movimiento->mo_imp = "-".$importe;
    $movimiento->mo_des = $descripcion;
    
    
    switch ($accion){
        case "reintegro":
            try {
                $response = $client->put($uri, [
                    'headers' => ["API_KEY" => "85RGF64UTVVFM9GZYY1H8U69N77IHSAKV83SEP9XWYB4R7GYM2"],
                    'query' => [
                        'movimiento' => json_encode($movimiento)
                    ]
                ]);
                $info = "Se ha realizado el cargo en la cuenta";
                
            } catch (Exception $exception) {
                if ($exception->getCode() == 403) {
                    $info = "No tienes permiso para realizar esta acción";
                }else if ($exception->getCode() == 409) {
                    $info = "El número de cuenta no existe";
                }else{
                    $info = "Ha ocurrido un error";
                }
            }
            break;
    }
    
}else{
    include 'index.php';
}
