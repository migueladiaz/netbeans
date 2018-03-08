
$(document).ready(function () {
    
    $( ".cambiarFormulario" ).click(function( event ) {
        event.preventDefault();
        $("#mensajeinfo").fadeOut(100);
        $("#errorLogin").fadeOut(100);
    });
    $("#emailregistro").focus(function () {
        $("#errorEmail").fadeOut(100);
    });
    $("#emaillogin").focus(function () {
        $("#errorLogin").fadeOut(100);
        $("#mensajeinfo").fadeOut(100);
    });
    $("#passlogin").focus(function () {
        $("#errorLogin").fadeOut(100);
        $("#mensajeinfo").fadeOut(100);
    });
});

function validarRegistro(){
    
    if ($("#emailDisponible").val() == "ocupado"){
        $("#errorEmail").fadeIn(100);
        return false;
    } else {
        return true;
    }
}

function mostrarRegistro(){
    $("#divlogin").fadeOut(100, function(){
        $("#divregistro").fadeIn(100);
    });
}

function mostrarLogin(){
    $("#divregistro").fadeOut(100, function(){
        $("#divlogin").fadeIn(100);
    });
}

//AJAX comprobar email
$(document).ready(function() {
    $("#emailregistro").focusout(function(){
        $.post("login", {
        email: $("#emailregistro").val(),
        accion: "comprobarEmail"
        },
        function(data, status) {
            $("#emailDisponible").val(data);
        });
    });
});