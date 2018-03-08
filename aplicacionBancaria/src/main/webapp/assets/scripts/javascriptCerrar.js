
//AJAX para comprobar numero de cuenta
$(document).ready(function() {
    $("#comprobarCuenta").click(function(){
        $(".tablas").fadeOut(100);
        if(validarNumCuenta()){
            $.post("../banco/cerrar", {
                accion: "comprobarCuenta",
                numCuenta: $("#numCuenta").val()
            },
                function(data, status) {
                    
                    var datos = JSON.parse(data);
                    $("#datosCuenta").empty();
                    $("#errorCuenta").fadeOut(100);
                    
                    if(datos[0]=="500"){
                        $("#errorCuenta").html(datos[1]);
                        $("#errorCuenta").fadeIn(100);
                        $(".tablas").fadeOut(100);
                        $("#cerrar").fadeOut(100);
                    }else{
                        var dni2 = datos[0].cu_dn2;
                        if(typeof dni2 == "undefined"){
                            dni2 = "---";
                        }
                        $("#datosCuenta").append("<tr><th>Nº Cuenta</th><th>Titular 1</th><th>Titular 2</th><th>Saldo</th></tr>");
                        $("#datosCuenta").append("<tr><td>"+datos[0].cu_ncu+"</td><td>"+datos[0].cu_dn1+"</td><td>"+dni2+"</td><td>"+datos[0].cu_sal+"</td></tr>");
                       
                        $(".datosTitular").val("");
                        
                        var clientes = datos[1];
                        var fechaNacimiento;  
                        
                        for(var i = 0; i < clientes.length; i++){
                            if(clientes[i].cl_dni == datos[0].cu_dn1){
                                fechaNacimiento = formatoFecha(new Date(clientes[i].cl_fna));

                                $("#nombre").val(clientes[i].cl_nom);
                                $("#direccion").val(clientes[i].cl_dir);
                                $("#telefono").val(clientes[i].cl_tel);
                                $("#email").val(clientes[i].cl_ema);
                                $("#fechaNacimiento").val(fechaNacimiento);
                            }else{
                                fechaNacimiento = formatoFecha(new Date(clientes[i].cl_fna));

                                $("#nombre2").val(clientes[i].cl_nom);
                                $("#direccion2").val(clientes[i].cl_dir);
                                $("#telefono2").val(clientes[i].cl_tel);
                                $("#email2").val(clientes[i].cl_ema);
                                $("#fechaNacimiento2").val(fechaNacimiento);
                            }
                        }

                        $(".datosTitular").attr("disabled", true);
                        
                        $("#cerrar").fadeIn(100);
                        $(".tablas").fadeIn(100);
                        $(".tablas").css("display","flex");
                    }
                });
        }
    });
});

//AJAX para comprobar el saldo
$(document).ready(function(){
    $("#cerrar").click(function(){
        $.post("../banco/cerrar", {
            accion: "comprobarSaldo",
            numCuenta: $("#numCuenta").val()
        },
            function(data, status) {
                if(data!="200"){
                    var datos = JSON.parse(data);
                    $("#errorCuenta").html(datos[1]);
                    $("#errorCuenta").fadeIn(100);
                    $("#cerrar").fadeOut(100);
                }else{
                    cerrarCuenta();
                }
            });
    });
});


$(document).ready(function () {
    //Ocultar los errores
    $("#numCuenta").focus(function(){
        $("#errorCuenta").fadeOut(100);
        $("#cerrar").fadeOut(100);
    });
});

function cerrarCuenta(){
    var confirmar = confirm("Se va a borrar la cuenta. También se borrarán los clientes que solo estén como titulares en esta cuenta. ¿Quieres continuar?");
    if(confirmar){
        $.post("../banco/cerrar", {
            accion: "cerrarCuenta",
            numCuenta: $("#numCuenta").val()
        },
            function(data, status) {
                if(data=="200"){
                    $("#datosCuenta").empty();
                    $(".datosTitular").val("");
                    $("#cerrar").fadeOut(100);
                    $(".tablas").fadeOut(100, function(){
                        $("#mensaje").fadeIn(100, function(){
                            setTimeout(function(){$("#mensaje").fadeOut(100)}, 1500);
                        });
                    });
                }else{
                    var datos = JSON.parse(data);
                    $("#errorCuenta").html(datos[1]);
                    $("#errorCuenta").fadeIn(100);
                }
            });
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
        return false;
    }else{
        return true;
    }
}

function formatoFecha(fecha){
    var dia = fecha.getDate();
    if(dia < 10){
        dia = "0"+dia;
    }
    
    var mes = fecha.getMonth()+1;
    if(mes < 10){
        mes = "0"+mes;
    }
    return dia+"/"+mes+"/"+fecha.getFullYear();
}

