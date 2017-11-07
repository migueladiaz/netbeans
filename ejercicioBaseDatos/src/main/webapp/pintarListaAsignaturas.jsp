<%-- 
    Document   : pintarListaAsignaturas
    Created on : 07-nov-2017, 9:44:08
    Author     : Miguel Angel Diaz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="utils.Constantes" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function cargarAsignatura(id, nombre, ciclo, curso) {
                document.getElementById("idasignatura").value = id;
                document.getElementById("nombre").value = nombre;
                document.getElementById("ciclo").value = ciclo;
                document.getElementById("curso").value = curso;
            }
            function accionActualizar() {
                document.getElementById("accion").value = "actualizar";
            }
            function accionInsertar() {
                document.getElementById("accion").value = "insertar";
            }
            function accionBorrar() {
                document.getElementById("accion").value = "borrar";
            }
        </script>
    </head>
    <body>
        <h1>Asignaturas</h1>
        <h3><c:out value="${mensaje}"></c:out></h3>
            <table border="1">
            <c:forEach items="${asignaturas}" var="asignatura">
                <tr>
                    <td><input type="button" value="Cargar ${asignatura.id}" style="width:100px" onclick="cargarAsignatura('${asignatura.id}',
                                    '${asignatura.nombre}', '${asignatura.ciclo}', '${asignatura.curso}')"></td>
                    <td>${asignatura.nombre}</td>
                    <td>${asignatura.ciclo}</td>
                    <td>${asignatura.curso}</td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <form>
            <input type="hidden" id="idasignatura" name="idasignatura">
            <input type="text" id="nombre" name="nombre">
            <input type="text" id="ciclo" name="ciclo">
            <input type="text" id="curso" name="curso">
            <input type="hidden" id="accion" name="accion">
            <br>
            <br>
            <button id="actualizar" onclick="accionActualizar()">Actualizar</button>
            <button id="insertar" onclick="accionInsertar()">Insertar</button>
            <button id="borrar" onclick="accionBorrar()">Borrar</button>
        </form>
    </body>
</html>
