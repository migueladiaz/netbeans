
var wsUri = "ws://localhost:8080/WebSocketUltraPower/websocket";
console.log("Connecting to " + wsUri);

var websocket;
var idToken;
var nombre;

function conectar(){
    websocket = new WebSocket(wsUri+"/"+user.value+"/"+pass.value,[]);
    
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

var output = document.getElementById("output");

$(document).ready(function(){
    $("#chat").submit(function(event){
        event.preventDefault();
        var objeto = {
            mensaje: myField.value,
            fecha: new Date(),
            id_canal: 1,
            nombre_user: nombre,
            guardar: guardar.checked
        };
        websocket.send(JSON.stringify(objeto));
        writeToScreen("Tu: " + myField.value);
        myField.value="";
    });
});

function onOpen() {
    if(user.value == "google"){
        websocket.send(idToken);
    }else{
        nombre = user.value;
    }
    writeToScreen("Bienvenido "+nombre);
    mostrarChat();
}
function onClose() {
    writeToScreen("DISCONNECTED");
    setTimeout(mostrarLogin, 1000);
}

function onMessage(evt) {
    writeToScreen(evt.data);
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
    idToken =  googleUser.getAuthResponse().id_token;
    var profile = googleUser.getBasicProfile();
    nombre = profile.getName();
    user.value="google";
    pass.value="google";
    conectar();
}

function mostrarChat(){
    $("#conteLogin").fadeOut(100, function(){
        $("#conteChat").fadeIn(100);
    });
}

function mostrarLogin(){
    $("#user").val("");
    $("#pass").val("");
    $("#conteChat").fadeOut(100, function(){
        $("#conteLogin").fadeIn(100);
    });
    $("#output").empty();
}
