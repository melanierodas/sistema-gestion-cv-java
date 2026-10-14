# Sistema de Gestión y Reclutamiento de Candidatos (Java + SQL Server)

Solución integral de software para la administración, registro y consulta centralizada de currículums vitae y expedientes laborales. El proyecto integra un cliente de escritorio administrativo (Java Swing) y un portal web de consulta para reclutadores (JSP), conectados a una base de datos relacional.

## 🚀 Arquitectura y Módulos del Sistema

### 1. Módulo Administrativo (Desktop - Java Swing)
- **Gestión de Candidatos (CRUD):** Registro, actualización, búsqueda y baja de información personal y de contacto.
- **Historial Laboral:** Registro dinámico de experiencias laborales asociadas por correo de candidato.
- **Historial Académico:** Gestión de grados y certificaciones por candidato.
- **Control de Acceso:** Ventana de autenticación para usuarios administrativos.

### 2. Portal de Consulta (Web - JSP)
- Control de sesión HTTP (`HttpSession`).
- Búsqueda y filtrado en tiempo real de candidatos registrados.
- Renderizado interactivo del perfil consolidado con experiencia y estudios.

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Java (JDK 17+)
- **Desktop UI:** Java Swing / NetBeans GUI Builder
- **Web:** JSP (JavaServer Pages), HTML5, CSS3
- **Base de Datos:** Microsoft SQL Server (Conexión JDBC con `PreparedStatement` para prevenir inyecciones SQL)
- **Arquitectura:** Separación en capas (Frontend / Backend / Modelo de Datos)

## 📂 Estructura del Repositorio
- `src/BACKEND/`: Clases de acceso a datos y lógica de negocio (`clsConexion`, `clsCandidato`, `clsExperiencia`, `clsEstudios`, `clsUsuarios`).
- `src/FRONTEND/`: Vistas de escritorio (`frmLogin`, `fmrCandidato`, `fmrExperiencia`, `frmEstudios`).
- `web/`: Archivo `index.jsp` y recursos para la interfaz web.
- `Reclutamiento.sql`: Script de creación y estructura relacional de la base de datos.
