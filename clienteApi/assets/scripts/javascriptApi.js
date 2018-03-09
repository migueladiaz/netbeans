var importeCorrecto;

$(document).ready(function(){
    $("#ingreso").click(function(event){
        event.preventDefault();
        if(validarNumCuenta()){
            $("#formulario").submit();
        }
    });
    
    $("#reintegro").click(function(event){
        event.preventDefault();
        if(validarNumCuenta()){
            var importe = -1 * parseInt($("#importe").val());
            $("#importe").val(importe);
            $("#formulario").submit();
        }
    });
    
    //Comprueba que el importe es mayor que 0 cada vez que se cambia el valor del campo
    $("#importe").on("input",function(){
        if($("#importe").val()>0){
            $("#sig").fadeIn(100);
            $("#errorImporte").fadeOut(100);
        }else{
            $("#errorImporte").html("El importe introducido no es correcto");
            $("#errorImporte").fadeIn(100);
            $("#sig").fadeOut(100);
        }
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
