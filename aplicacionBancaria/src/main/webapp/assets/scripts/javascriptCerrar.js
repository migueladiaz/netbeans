
//AJAX para comprobar numero de cuenta
$(document).ready(function() {
    $("#comprobarCuenta").click(function(){
        if(validarNumCuenta()){
            $.post("../banco/abrir", {
                accion: "comprobarCuenta",
                numCuenta: $("#numCuenta").val()
            },
                function(data, status) {
                    var datos = JSON.parse(data);
                    
                    if(datos[0]=="500"){
                        $("#errorCuenta").html(datos[1]);
                        $("#errorCuenta").fadeIn(100);
                    }else{
                        
                    }
                });
        }
    });
});


