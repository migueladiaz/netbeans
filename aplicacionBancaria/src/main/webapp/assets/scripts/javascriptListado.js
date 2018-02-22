function validarListado() {
    var numCuenta = $("#cuenta").val();
    var inicio = new Date($("#inicio").val());
    var fin = new Date($("#fin").val());
    var total = 0;
    var error = false;
    
    for (var i = 0; i < numCuenta.length - 1; i++) {
        total += parseInt(numCuenta.charAt(i));
    }

    if (!/^[0-9]{10}$/.test(numCuenta)) {
        $("#errorCuenta").fadeIn(100);
        error = true;
    }
    if (!((total % 9) == (numCuenta.charAt(numCuenta.length - 1)))) {
        $("#errorCuenta").fadeIn(100);
        error = true;
    }
    if ((isNaN(inicio.getTime())) || (isNaN(fin.getTime()))) {
        $("#errorFecha").fadeIn(100);
        error = true;
    }
    if (inicio > fin) {
        $("#errorFecha").fadeIn(100);
        error = true;
    }

    if (error == true) {
        return false;
    } else {
        return true;
    }
}

//AJAX para mostrar listado
$(document).ready(function() {
    $("#botonListado").click(function() {
        if (validarListado()) {
            $.post("../banco/listado", $("#datos").serialize(),
                function(data, status, xhr) {
                    
                    var datos = JSON.parse(data);
                    $("#tabla").empty();
                    $("#error").fadeOut(100, function (){
                        if(datos[0]=="500"){
                            $("#error").html(datos[1]);
                            $("#error").fadeIn(100);
                        }else {
                            $("#tabla").append("<tr><th>Fecha</th><th>Descripción</th><th>Importe</th></tr>");

                            for (var i = 0; i < datos.length; i++) {
                                var fecha = formatoFecha(datos[i].mo_fec);
                                $("#tabla").append('<tr><td>' + fecha + '</td><td>' + datos[i].mo_des + '</td><td>' + datos[i].mo_imp + '€</td></tr>');
                            }
                        }
                    });
                });
        }
    });
});

//Oculta los errores mostrados
$(document).ready(function () {
    
    $("#cuenta").focus(function () {
        $("#errorCuenta").fadeOut(100);
    });
    $("#inicio").focus(function () {
        $("#errorFecha").fadeOut(100);
    });
    $("#fin").focus(function () {
        $("#errorFecha").fadeOut(100);
    });
    $(".active").click(function( event ) {
        event.preventDefault();
    });
    $("#putaso").click(function(){
    document.getElementById("puta").innerHTML="á tus muertos € $ ááááé";
    alert(document.getElementById("puta").innerHTML);
    });
});

//Escribe la fecha en el formato deseado
function formatoFecha(cadena){
    var fecha = new Date(cadena);
    var fechaFinal = "";
    fechaFinal = fecha.getDate()+"/"+(fecha.getMonth()+1)+"/"+fecha.getFullYear();
    return fechaFinal;
}