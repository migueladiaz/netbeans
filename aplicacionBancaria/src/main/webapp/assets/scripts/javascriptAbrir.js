var pestaña = 1;

function siguiente(event){
    event.preventDefault();
    $("#ant").fadeIn(100);
    if(pestaña<3){
        pestaña+=1;
        $("#"+pestaña).click();
    } 
    if(pestaña == 3) {
        $("#sig").fadeOut(100);
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
});
