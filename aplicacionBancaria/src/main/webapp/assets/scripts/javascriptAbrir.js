var pestaña = 1;
var titularExiste;
var titularGuardado = false;
var dni = 0;
var datos = {
    existe: null,
    cuenta: null,
    importe: null,
    dni: null,
    nombre: null,
    direccion: null,
    telefono: null,
    email: null,
    fecha: null
};

function siguiente(event){
    event.preventDefault();
    
    pestaña+=1;
    $("#"+pestaña).click();
    
    $("#sig").fadeOut(100);
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
    
    $(".datosTitular").focus(function(){
        $("#errorDatos").fadeOut(100);
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
        $("#errorDni").html("El DNI no es correcto.");
        error = true;
    }
    
    if(numDni==dni){
        $("#errorDni").html("No puedes introducir el mismo DNI otra vez.");
        error = true;
    }
    
    if(error == true){
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
                        $("#sig").fadeIn(50);
                    }else{
                        var datos = JSON.parse(data);
                        
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
                            titularExiste = true;
                            
                            $("#sig").fadeIn(50);
                        }
                    }
                });
        }
    });
});

//AJAX para guardar cliente
$(document).ready(function () {
    $("#guardarDatos").click(function() {
        $("#errorDatos").fadeOut(100);
        if(validarDatosTitular()){
            var continuar = confirm("Se va a proceder a guardar los datos. ¿Quieres continuar?");
            if(continuar){
                datos.existe = titularExiste;
                datos.cuenta = $("#numCuenta").val();
                datos.importe = $("#importe").val();
                datos.dni = $("#numDni").val();
                datos.nombre = $("#nombre").val();
                datos.direccion = $("#direccion").val();
                datos.telefono = $("#telefono").val();
                datos.email = $("#email").val();
                datos.fecha = $("#fechaNacimiento").val();
                if(titularGuardado==false){
                    titularGuardado=true;
                    dni = $("#numDni").val();
                    pestaña+=1;
                    $("#"+pestaña).click();
                    $("#introducirSegundo").fadeIn(50);
                }else{
                    pestaña=6;
                    $("#"+pestaña).click();
                }
            }
        }else{
            $("#errorDatos").html("Rellena todos los datos");
            $("#errorDatos").fadeIn(100);
        }
        
    });
});

$(document).ready(function () {
    //Devuelve a la pestaña para introducir un nuevo titular
    $("#si").click(function() {
        $("#numDni").val("");
        pestaña=3;
        $("#"+pestaña).click();
    });
    
    //Avanza a la siguiente pestaña si no se quiere introducir un nuevo titular
    $("#no").click(function() {
        pestaña+=1;
        $("#"+pestaña).click();
    });
    
    //Resetea todos los parametros para poder crear otra cuenta nueva
    $("#finalizar").click(function() {
        $("#numCuenta").val("");
        $("#numDni").val("");
        $("#importe").val("");
        titularGuardado=false;
        dni = 0;
        pestaña=1;
        $("#"+pestaña).click();
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

function validarDatosTitular(){
    if($("#nombre").val()!="" && $("#direccion").val()!="" && $("#telefono").val()!="" && $("#email").val()!="" && $("#fechaNacimiento").val()!=""){
        return true;
    }else{
        $("#errorDatos").html("Rellena todos los campos");
        $("#errorDatos").fadeIn(100);
        return false;
    }
}