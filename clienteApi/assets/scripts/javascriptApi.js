
var importeCorrecto = false;
var opcion;

$(document).ready(function(){
    $("#devolver").click(function(){
        if(validarNumCuenta()){
            opcion = "devolver";
            addMovimiento();
        }
    });
    
    $("#cobrar").click(function(){
        if(validarNumCuenta()){
            opcion = "cobrar";
            addMovimiento();
        }
    });
    
    //Comprueba que el importe es mayor que 0 cada vez que se cambia el valor del campo
    $("#importe").on("input",function(){
        if($("#importe").val()>0){
            $("#importe").removeClass("bordeError");
            $("#errorImporte").fadeOut(100);
            importeCorrecto = true;
        }else{
            $("#importe").addClass("bordeError");
            $("#errorImporte").html("El importe introducido no es correcto.");
            $("#errorImporte").fadeIn(100);
            importeCorrecto = false;
        }
    });
    
    $("#importe").focus(function(){
        $("#errorImporte").fadeOut(100);
        $("#importe").removeClass("bordeError");
    });
    
    $("#numCuenta").focus(function(){
        $("#errorCuenta").fadeOut(100);
        $("#numCuenta").removeClass("bordeError");
    });
});

function addMovimiento(){
    if(importeCorrecto){
        var importe = $("#importe").val();
        if(opcion=="cobrar"){
            importe = importe * -1;
        }
        $.post("cliente.php", {
            numCuenta: $("#numCuenta").val(),
            importe: importe,
            descripcion: $("#descripcion").val(),
            accion: "addMovimiento"
        },
            function(data, status) {
                $("#numCuenta").val("");
                $("#descripcion").val("");
                $("#importe").val("");
                $("#info").html(data);
                $("#info").fadeIn(100, function(){
                    setTimeout(function(){$("#info").fadeOut(100)}, 3000);
                });
            });
    }else{
        $("#importe").addClass("bordeError");
        $("#errorImporte").html("El importe introducido no es correcto.");
        $("#errorImporte").fadeIn(100);
    }
}

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
        $("#numCuenta").addClass("bordeError");
        return false;
    }else{
        return true;
    }
}
