<%-- 
    Document   : index
    Created on : 27/05/2026
    Author     : famro
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="BACKEND.clsUsuarios"%>
<%@page import="BACKEND.clsCandidato"%>
<%@page import="BACKEND.clsExperiencia"%>
<%@page import="BACKEND.clsEstudios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    if (request.getParameter("logout") != null) {
        session.removeAttribute("usuarioLogueado");
        response.sendRedirect("index.jsp");
        return;
    }
    boolean logueado = (session.getAttribute("usuarioLogueado") != null);
    
    if (request.getParameter("accionLogin") != null) {
        String u = request.getParameter("txtUsuario");
        String p = request.getParameter("txtPassword");
        clsUsuarios userBackend = new clsUsuarios();
        
        if (userBackend.login(u, p)) {
            session.setAttribute("usuarioLogueado", u);
            logueado = true;
        } else {
            out.println("<script>alert('Usuario o Contraseña incorrectos');</script>");
        }
    }

    String correoVer = request.getParameter("verCorreo");
    String buscar = request.getParameter("txtBuscar");
    
    clsCandidato backendCand = new clsCandidato();
    clsCandidato candidatoSeleccionado = null;
    
    java.util.ArrayList<clsCandidato> listaFiltrada = new java.util.ArrayList<>();
    
    if (logueado) {
        if (buscar != null && !buscar.trim().isEmpty()) {
            clsCandidato filtrado = backendCand.fncBuscar(buscar);
            if (filtrado != null) {
                listaFiltrada.add(filtrado); 
            }
        } else {
            listaFiltrada = backendCand.fncListarTodos();
        }
        
        if (correoVer != null) {
            candidatoSeleccionado = backendCand.fncBuscar(correoVer);
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Portal de Reclutamiento - Consulta de CVs</title>
        <style>
            body { 
                font-family: Arial, sans-serif; 
                background-color: #f4f6f9; 
                margin: 40px; 
                color: #333333; 
            }
            
            .cuadro-login { 
                background: white; 
                padding: 35px; 
                width: 380px; 
                margin: 50px auto; 
                border: 1px solid #ccd6dd; 
                box-shadow: 2px 2px 12px #aaa; 
                text-align: left; 
                border-radius: 8px; 
            }
            .cuadro-login h2 { 
                text-align: center; 
                color: #111e2e; 
                margin-top: 0; 
            }
            .container { 
                max-width: 850px; 
                margin: 0 auto; 
                background: white; 
                padding: 30px; 
                border-radius: 8px; 
                box-shadow: 0 4px 6px rgba(0,0,0,0.1); 
                text-align: left; 
            }
            .header-bar { 
                background: #111e2e; 
                color: white; 
                padding: 15px; 
                margin: -30px -30px 30px -30px; 
                border-radius: 8px 8px 0 0; 
                display: flex; 
                justify-content: space-between; 
                align-items: center; 
            }
            .header-bar h2 { 
                margin: 0; 
                padding-left: 15px; 
                font-size: 20px; 
            }
            .header-bar a { 
                color: white; 
                text-decoration: none; 
                font-weight: bold; 
                background: #b91c1c; 
                padding: 6px 12px; 
                border-radius: 4px; 
            }
            .fila-form { 
                margin-bottom: 15px; 
            }
            .fila-form label { 
                display: block; 
                font-weight: bold; 
                margin-bottom: 5px; 
                color: #111e2e;
            }
            .fila-form input { 
                width: 100%; 
                padding: 10px; 
                border: 1px solid #ccc; 
                border-radius: 4px; 
                box-sizing: border-box; 
            }
            .btn-ingresar { 
                background-color: #111e2e; 
                color: white; 
                border: none; 
                padding: 12px; 
                width: 100%; 
                font-weight: bold; 
                cursor: pointer; 
                border-radius: 4px; 
                font-size: 16px; 
            }
            .btn { 
                background: #111e2e; 
                color: white; 
                border: none; 
                padding: 10px 20px; 
                cursor: pointer; 
                border-radius: 4px; 
                font-weight: bold; 
                text-decoration: none; 
                display: inline-block; 
            }
            .btn-azul { 
                background: #111e2e; 
            }
            table { 
                width: 100%; 
                border-collapse: collapse; 
                margin-top: 20px; 
                background: white; 
            }
            th, td { 
                border: 1px solid #e5e7eb; 
                padding: 12px; 
                text-align: center; 
            }
            th { 
                background-color: #111e2e; 
                color: white; 
            }
            tr:hover { 
                background-color: #f8fafc; 
            }
            .cv-section { 
                margin-bottom: 30px; 
                padding-bottom: 15px; 
                border-bottom: 1px solid #e2e8f0; 
            }
            .cv-section h3 { 
                color: #111e2e; 
                border-left: 4px solid #111e2e; 
                padding-left: 10px; 
                margin-bottom: 15px; 
                font-size: 20px;
            }
            .avatar { 
                width: 140px; 
                height: 140px; 
                border-radius: 6px; 
                object-fit: cover; 
                border: 2px solid #ccc; 
                float: right; 
            }
            .item-cv { 
                background-color: #ffffff; 
                padding: 20px; 
                margin-bottom: 20px; 
                border: 1px solid #e2e8f0; 
                border-radius: 8px; 
                text-align: left; 
                box-shadow: 0 2px 4px rgba(0,0,0,0.02); 
            }
            .item-titulo { 
                font-size: 18px; 
                color: #111e2e; 
                font-weight: bold; 
                margin: 0 0 5px 0; 
            }
            .item-subtitulo { 
                font-size: 14px; 
                color: #64748b; 
                margin: 0 0 12px 0; 
            }
            .item-descripcion {
                font-size: 15px;
                color: #334155;
                line-height: 1.5;
                margin: 0;
            }
        </style>
    </head>
    <body>
        <% if (!logueado) { %>
            <div class="cuadro-login">
                <h2>BIENVENIDO</h2>
                <p style="color: #7f8c8d; text-align:center; font-size:14px;">Reclutamiento Web</p>
                <br>
                <form action="index.jsp" method="POST">
                    <input type="hidden" name="accionLogin" value="true">
                    
                    <div class="fila-form">
                        <label>Usuario:</label>
                        <input type="text" name="txtUsuario" placeholder="Ingrese su usuario" required>
                    </div>
                    
                    <div class="fila-form">
                        <label>Contraseña:</label>
                        <input type="password" name="txtPassword" placeholder="Ingrese su contraseña" required>
                    </div>
                    
                    <br>
                    <input type="submit" value="INGRESAR" class="btn-ingresar">
                </form>
            </div>
        <% } %>

        <% if (logueado) { %>
            <div class="container">
                <div class="header-bar">
                    <h2>Reclutamiento - Consulta de CVs</h2>
                    <a href="index.jsp?logout=true">Cerrar Sesión</a>
                </div>

                <% if (candidatoSeleccionado != null) { %>
                    <a href="index.jsp" class="btn" style="background:#7f8c8d; margin-bottom:20px;">◀ Volver al Listado</a>
                    
                    <div class="cv-section" style="min-height: 150px;">
                        <img src="<%= (candidatoSeleccionado.getFoto() != null && !candidatoSeleccionado.getFoto().trim().isEmpty()) ? candidatoSeleccionado.getFoto() : "https://via.placeholder.com/150" %>" class="avatar" alt="Foto">
                        <h2 style="color: #111e2e; margin-top: 0;"><%= candidatoSeleccionado.getNombre() %></h2>
                        <p><strong>Correo:</strong> <%= candidatoSeleccionado.getCorreo() %></p>
                        <p><strong>Teléfono:</strong> <%= candidatoSeleccionado.getTelefono() %></p>
                        <p><strong>Dirección:</strong> <%= candidatoSeleccionado.getDireccion() %></p>
                    </div>

                    <div class="cv-section">
                        <h3>Perfil Profesional</h3>
                        <p style="line-height: 1.5; color: #334155;"><%= (candidatoSeleccionado.getPerfil() != null && !candidatoSeleccionado.getPerfil().isEmpty()) ? candidatoSeleccionado.getPerfil() : "No especificado." %></p>
                    </div>

                    <div class="cv-section">
                        <h3>Experiencia Laboral</h3>
                        <% 
                            java.util.ArrayList<clsExperiencia> listaExp = new clsExperiencia().fncListarPorCandidato(candidatoSeleccionado.getCorreo());
                            if(listaExp.isEmpty()) { 
                        %>
                            <p style="color: #64748b;">No se registró experiencia laboral</p>
                        <% } else { 
                            for(clsExperiencia exp : listaExp) { 
                        %>
                                <div class="item-cv">
                                    <div class="item-titulo"><%= exp.getPuesto() %></div>
                                    <div class="item-subtitulo">
                                        💼 <%= exp.getEmpresa() %> &nbsp;|&nbsp; 📅 <%= exp.getFechaInicio() %> a <%= (exp.getFechaFinal() != null && !exp.getFechaFinal().isEmpty()) ? exp.getFechaFinal() : "Presente" %>
                                    </div>
                                    <p class="item-descripcion"><strong>Funciones:</strong> <%= exp.getFunciones() %></p>
                                </div>
                        <% 
                            } 
                        } 
                        %>
                    </div>

                    <div class="cv-section">
                        <h3>Estudios Realizados</h3>
                        <% 
                            java.util.ArrayList<clsEstudios> listaEst = new clsEstudios().fncListarPorCandidato(candidatoSeleccionado.getCorreo());
                            if(listaEst.isEmpty()) { 
                        %>
                            <p style="color: #64748b;">No se registraron estudios académicos</p>
                        <% } else { 
                            for(clsEstudios est : listaEst) { 
                        %>
                                <div class="item-cv">
                                    <div class="item-titulo"><%= est.getTitulo() %></div>
                                    <div class="item-subtitulo">
                                        🏢 <%= est.getInstitucion() %> &nbsp;|&nbsp; 📅 <%= est.getFechaInicio() %> a <%= (est.getFechaFinal() != null && !est.getFechaFinal().isEmpty()) ? est.getFechaFinal() : "Presente" %>
                                    </div>
                                    <p class="item-descripcion"><%= est.getDescripcion() %></p>
                                </div>
                        <% 
                            } 
                        } 
                        %>
                    </div>
                <% } %>

                <% if (candidatoSeleccionado == null) { %>
                    <form action="index.jsp" method="GET" style="display:flex; gap:10px; margin-bottom:20px;">
                        <input type="text" name="txtBuscar" placeholder="Ingrese el correo electrónico para buscar al candidato" style="padding:10px; width:75%; border:1px solid #ccc; border-radius:4px;">
                        <input type="submit" value="Buscar" class="btn btn-azul" style="width:25%;">
                    </form>

                    <table>
                        <thead>
                            <tr>
                                <th>Nombre Completo</th>
                                <th>Correo Electrónico</th>
                                <th>Teléfono</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                            if (listaFiltrada.isEmpty()) {
                            %>
                                <tr>
                                    <td colspan="4" style="color: #64748b; padding: 20px;">No se encontraron candidatos con ese correo</td>
                                </tr>
                            <% 
                            } else {
                                for (clsCandidato cand : listaFiltrada) { 
                            %>
                                    <tr>
                                        <td><%= cand.getNombre() %></td>
                                        <td><%= cand.getCorreo() %></td>
                                        <td><%= cand.getTelefono() %></td>
                                        <td>
                                            <a class="btn" style="padding: 5px 12px; font-size: 13px;" href="index.jsp?verCorreo=<%= cand.getCorreo() %>">Ver CV</a>
                                        </td>
                                    </tr>
                            <% 
                                }
                            } 
                            %>
                        </tbody>
                    </table>
                <% } %>

            </div>
        <% } %>

    </body>
</html>