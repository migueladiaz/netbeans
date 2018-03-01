
var wsUri = "ws://localhost:8080/WebSocketUltraPower/websocket";
console.log("Connecting to " + wsUri);

var websocket;
var idToken;
var nombre;
var output = document.getElementById("output");

function conectar() {
    websocket = new WebSocket(wsUri + "/" + user.value + "/" + pass.value, []);

    websocket.onopen = function (evt) {
        onOpen(evt);
    };
    websocket.onmessage = function (evt) {
        onMessage(evt);
    };
    websocket.onerror = function (evt) {
        onError(evt);
    };
    websocket.onclose = function (evt) {
        onClose(evt);
    };
}

$(document).ready(function () {
    $("#chat").submit(function (event) {
        event.preventDefault();
        /*Solo si no se usan encoder y decoder
         websocket.send(myField.value);*/
        var fechaActual = new Date();
        var objeto = {
            tipo: "texto",
            mensaje: myField.value,
            fecha: fechaActual,
            id_canal: $("#misCanales").val(),
            nombre_canal: $("#misCanales option:selected" ).text(),
            nombre_user: nombre,
            guardar: guardar.checked
        };
        websocket.send(JSON.stringify(objeto));
        if($("#misCanales").val()>0){
            writeToScreen("<span class='privado'>"+$("#misCanales option:selected" ).text()+"</span> - Tu: " + myField.value);
        }else{
            writeToScreen("<span class='general'>General</span> - Tu: " + myField.value);
        }
        myField.value = "";
    });
});

function onOpen() {
    if (user.value == "google") {
        var objeto = {
            mensaje: idToken
        };
        websocket.send(JSON.stringify(objeto));
    } else {
        nombre = user.value;
    }
    writeToScreen("<span class='info'>Bienvenido " + nombre + "</span>");
    mostrarChat();
}
function onClose() {
    writeToScreen("<span class='info'>DISCONNECTED</span>");
    setTimeout(mostrarLogin, 1000);
}

function onMessage(evt) {
    /*Solo si no se usan encoder y decoder
     writeToScreen(evt.data);*/
    var mensaje = JSON.parse(evt.data);
    switch (mensaje.tipo) {
        case "texto":
            writeToScreen("<span class='general'>General</span> - "+mensaje.nombre_user + ": " + mensaje.mensaje);
            break;

        case "info":
            writeToScreen("<span class='info'>"+mensaje.mensaje+"</span>");
            getUsuarios();
            break;

        case "canales":
            var canales = JSON.parse(mensaje.mensaje);
            $("#listaCanales").append("<option value='default' disabled selected>Selecciona un canal</option><option disabled>-------------------------</option>");
            for (var canal in canales) {
                $("#listaCanales").append(new Option(canales[canal].nombre, canales[canal].id));
            }
            getUsuarios();
            break;
            
        case "cargar":
            if(mensaje.mensaje == "error"){
                writeToScreen("<span class='info'>No hay mensajes</span>");
            }else{
                var mensajes = JSON.parse(mensaje.mensaje);
                for (var mensaje in mensajes) {
                    var canal = $("#misCanales option[value='"+mensajes[mensaje].id_canal+"']");
                    writeToScreen("<span class='info'>"+formatoFecha(mensajes[mensaje].fecha)+" "+$(canal).text()+"</span><span class='textoCargado'> - "+mensajes[mensaje].nombre_user+": "+mensajes[mensaje].mensaje+"</span>");
                }
            }
            break;
            
        case "misCanales":
            var canales = JSON.parse(mensaje.mensaje);
            $("#misCanales").append(new Option("General", "0"));
            if(canales != null){
                for (var canal in canales) {
                    $("#misCanales").append(new Option(canales[canal].nombre, canales[canal].id));
                }   
            }
            
            break;
            
        case "peticion":
            var aceptado = confirm("El usuario "+mensaje.nombre_user+" ha solicitado permiso para unirse a "+mensaje.mensaje);
            if(aceptado == true){
                var objeto = {
                    tipo: "aceptarPeticion",
                    nombre_user: mensaje.nombre_user,
                    id_canal: mensaje.id_canal
                };
                websocket.send(JSON.stringify(objeto));
            }else{
                var objeto2 = {
                    tipo: "rechazarPeticion",
                    nombre_user: mensaje.nombre_user
                };
                websocket.send(JSON.stringify(objeto2));
            }
            break;
            
        case "errorPeticion":
            alert("No se ha podido enviar la solicitud. El administrador no está conectado");
            break;
            
        case "errorSuscripcion":
            alert("No se ha podido enviar la solicitud. Ya estás suscrito a ese canal");
            break;
            
        case "aceptado":
            if(mensaje.mensaje == "ok"){
                alert("El administrador ha aceptado tu solicitud");
                getMisCanales();
            }else{
                alert("Ha ocurrido un error");
            }
            break;
            
        case "rechazado":
            alert("El administrador ha rechazado tu solicitud");
            break;
            
        case "crearCanal":
            if(mensaje.mensaje == "ok"){
                alert("Se ha creado el canal "+mensaje.nombre_canal);
                getMisCanales();
                getCanales();
            }else{
                alert("Ha ocurrido un error");
            }
            break;
            
        case "privado":
            writeToScreen("<span class='privado'>"+mensaje.nombre_canal+"</span> - "+mensaje.nombre_user + ": " + mensaje.mensaje);
            break;
    }
    
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
    var box = document.getElementById('output');
    box.scrollTop = box.scrollHeight;
}

function onSignIn(googleUser) {
    idToken = googleUser.getAuthResponse().id_token;
    var profile = googleUser.getBasicProfile();
    nombre = profile.getName();
    user.value = "google";
    pass.value = "google";
    conectar();
}

function mostrarChat() {
    $("#conteLogin").fadeOut(100, function () {
        $("#conteChat").fadeIn(100);
    });
    getCanales();
    getMisCanales();
}

function mostrarLogin() {
    $("#user").val("");
    $("#pass").val("");
    $("#conteChat").fadeOut(100, function () {
        $("#conteLogin").fadeIn(100);
    });
    $("#output").empty();
}

function getCanales() {
    $("#listaCanales").empty();
    var objeto = {
        tipo: "canales"
    };
    websocket.send(JSON.stringify(objeto));
}

function getMisCanales() {
    $("#misCanales").empty();
    var objeto = {
        tipo: "misCanales",
        nombre_user: nombre
    };
    websocket.send(JSON.stringify(objeto));
}

function getUsuarios() {
    $.post("lista",
    function (data, status) {
        var datos = JSON.parse(data);
        $("#listaUsuarios").empty();
        $("#listaUsuarios").html("<h3 class='bg-secondary'>Usuarios</h3>");
        for (var usuario in datos) {
            $("#listaUsuarios").append("<p>"+datos[usuario]+"</p>");
        }
    });
}

function cargar(){
    var objeto = {
        tipo: "cargar",
        nombre_user: nombre,
        inicio: inicio.value,
        fin: fin.value
    };
    websocket.send(JSON.stringify(objeto));
}

function suscripcion(){
    var objeto = {
        tipo: "suscripcion",
        mensaje: $("#listaCanales option:selected" ).text(),
        nombre_user: nombre,
        id_canal: $("#listaCanales").val()
    };
    websocket.send(JSON.stringify(objeto));
    alert("Se ha enviado una solicitud al administrador del canal");
    $("#listaCanales").val("default");
}

function crearCanal(){
    var nombreCanal=prompt("Introduce el nombre del canal");
    var objeto = {
        tipo: "crearCanal",
        nombre_user: nombre,
        nombre_canal: nombreCanal
    };
    websocket.send(JSON.stringify(objeto));
}

function formatoFecha(cadena){
    var fecha = new Date(cadena);
    var dia = fecha.getDate();
    var mes = fecha.getMonth()+1;
    var hora = fecha.getHours();
    var minutos = fecha.getMinutes();
    var segundos = fecha.getSeconds();
    if(mes < 10){
        mes = "0"+mes;
    }
    if(dia < 10){
        dia = "0"+dia;
    }
    if(hora < 10){
        hora = "0"+hora;
    }
    if(minutos < 10){
        minutos = "0"+minutos;
    }
    if(segundos < 10){
        segundos = "0"+segundos;
    }
    return dia+"/"+mes+"/"+fecha.getFullYear()+" "+hora+":"+minutos+":"+segundos;
}
