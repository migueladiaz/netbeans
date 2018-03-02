var pestaña = 1;
var titularExiste = false;
var segundoTitular = false;
var datos = {
            nombre: null,
            direccion: null,
            telefono: null,
            email: null,
            fecha: null,
            nombre2: null,
            direccion2: null,
            telefono2: null,
            email2: null,
            fecha2: null
        };

function siguiente(event){
    event.preventDefault();
    $("#ant").fadeIn(100);
    if(pestaña<4){
        pestaña+=1;
        $("#"+pestaña).click();
    } 
    
    $("#sig").fadeOut(100);
    
    if(pestaña==3){
        if(titularExiste==true){
            $("#sig").fadeIn(100);
        }
    }
    
    if(pestaña==4){
        segundoTitular = confirm("¿Quieres introducir un segundo titular?");
    }
}

function anterior(event){
    event.preventDefault();
    $("#sig").fadeIn(100);
    if(pestaña>1){
        pestaña-=1;
        $("#"+pestaña).click();
    } 
    if(pestaña == 1) {
        $("#ant").fadeOut(100);
    }
}

$(document).ready(function () {
    //No se puede hacer click en los enlaces activos
    $(".active").click(function( event ) {
        event.preventDefault();
    });
    
    $("#numCuenta").focus(function(){
        $("#sig").fadeOut(50);
    });
    $("#numDni").focus(function(){
        $("#sig").fadeOut(50);
    });
    
    //Ocultar los errores
    $("#numCuenta").focus(function(){
        $("#errorCuenta").fadeOut(100);
    });
    
    $("#numDni").focus(function(){
        $("#errorDni").fadeOut(100);
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

function validarDni(){
    var numDni = $("#numDni").val();
    var error = false;
    
    if (!/^[0-9]{8}[A-Z]$/.test(numDni)) {
        error = true;
    }
    
    if(error == true){
        $("#errorDni").html("El DNI no es correcto.");
        $("#errorDni").fadeIn(100);
        return false;
    }else{
        return true;
    }
}

//AJAX para comprobar numero de cuenta
$(document).ready(function() {
    $("#comprobarCuenta").click(function(){
        if(validarNumCuenta()){
            $.post("../banco/abrir", {
                accion: "comprobarCuenta",
                cuenta: $("#numCuenta").val()
            },
                function(data, status) {
                    if(data=="disponible"){
                        $("#sig").fadeIn(50);
                    }else{
                        var datos = JSON.parse(data);
                        $("#errorCuenta").html(datos[1]);
                        $("#errorCuenta").fadeIn(100);
                    }
                });
        }
    });
});

//AJAX para comprobar DNI
$(document).ready(function() {
    $("#comprobarDni").click(function(){
        if(validarDni()){
            $.post("../banco/abrir", {
                accion: "comprobarTitular",
                dni: $("#numDni").val()
            },
                function(data, status) {
                    if(data=="pedirDatos"){
                        titularExiste = false;
                        $(".datosTitular").val("");
                        $(".datosTitular").attr("disabled", false);
                        $("#enviarDatos").fadeIn(50);
                        $("#sig").fadeIn(50);
                    }else{
                        var datos = JSON.parse(data);
                        
                        $("#enviarDatos").fadeOut(50);
                        
                        if(datos[0]=="500"){
                            $("#errorDni").html(datos[1]);
                            $("#errorDni").fadeIn(100);
                        }else{
                            var mes = datos.cl_fna.month;
                            if(mes < 10){
                                mes = "0"+mes;
                            }
                            var fecha = datos.cl_fna.day+"/"+mes+"/"+datos.cl_fna.year;
                            
                            $("#nombre").val(datos.cl_nom);
                            $("#direccion").val(datos.cl_dir);
                            $("#telefono").val(datos.cl_tel);
                            $("#email").val(datos.cl_ema);
                            $("#fechaNacimiento").val(fecha);
                            
                            $(".datosTitular").attr("disabled", true);
                            
                            rellenarTitular1();
                            
                            $("#sig").fadeIn(50);
                            titularExiste = true;
                        }
                    }
                });
        }
    });
});

$(document).ready(function () {
    $("#enviarDatos").click(function() {
        if(titularExiste){
            rellenarTitular2();
        }else{
            rellenarTitular1();
        }
    });
});

function rellenarTitular1(){
    datos.nombre = $("#nombre").val();
    datos.direccion = $("#direccion").val();
    datos.telefono = $("#telefono").val();
    datos.email = $("#email").val();
    datos.fecha = $("#fechaNacimiento").val();
}