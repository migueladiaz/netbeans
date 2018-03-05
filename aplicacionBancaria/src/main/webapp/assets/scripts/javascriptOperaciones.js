
//AJAX para comprobar numero de cuenta
$(document).ready(function() {
    $("#numCuenta").blur(function(){
        if(validarNumCuenta()){
            $.post("../banco/operaciones", {
                accion: "comprobarCuenta",
                numCuenta: $("#numCuenta").val()
            },
                function(data, status) {
                    if(data=="200"){
                        $(".enviar").fadeIn(100);
                    }else{
                        var datos = JSON.parse(data);
                        $("#errorCuenta").html(datos[1]);
                        $("#errorCuenta").fadeIn(100);
                    }
                });
        }
    });
});

//AJAX para mostrar listado
$(document).ready(function() {
    $(".enviar").click(function() {
        if (validarListado()) {
            $.post("../banco/operaciones", $("#datos").serialize(),
                function(data, status) {
                    $("#info").html(data);
                });
        }
    });
});

//Oculta los botones y los errores si se va a cambiar el número de cuenta
$(document).ready(function(){
   $(".enviar").focus(function(){
       $(".enviar").fadeOut(100);
       $("#errorCuenta").fadeOut(100);
   }); 
});

function validarNumCuenta() {
    var numCuenta = $("#numCuenta").val();
    var total = 0;
    var error = false;
    
    for (var i = 0; i < numCuenta.length - 1; i++) {
        total += parseInt(numCuenta.charAt(i));
    }
    
    if (!/^[0-9]{10}$/.test(numCuenta)) {
        error = true;
    }
    if (!((total % 9) == (numCuenta.charAt(numCuenta.length - 1)))) {
        error = true;
    }
    
    if(error == true){
        $("#errorCuenta").html("El número de cuenta no es correcto.");
        $("#errorCuenta").fadeIn(100);
        return false;
    }else{
        return true;
    }
}