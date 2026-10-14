-- 1. Si la base de datos no existe, la creamos primero
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'DB_RECLUTAMIENTO')
BEGIN
    CREATE DATABASE DB_RECLUTAMIENTO;
END
GO

-- 2. Nos cambiamos obligatoriamente a tu base de datos
USE DB_RECLUTAMIENTO;
GO

-- 3. Creamos la tabla de Usuarios para el Login
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[USUARIOS]') AND type in (N'U'))
BEGIN
    CREATE TABLE USUARIOS(
        USUARIO VARCHAR(50) PRIMARY KEY, 
        PASSWORD VARCHAR(50) NOT NULL
    );
END
GO

-- 4. Creamos la tabla de Candidatos (Cambiado TEXT por VARCHAR(MAX))
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CANDIDATOS]') AND type in (N'U'))
BEGIN
    CREATE TABLE CANDIDATOS(
        CORREO VARCHAR(100) PRIMARY KEY, 
        NOMBRE VARCHAR(100) NOT NULL, 
        TELEFONO VARCHAR(20), 
        DIRECCION VARCHAR(200),
        FOTO VARCHAR(255),
        PERFIL VARCHAR(MAX)
    );
END
GO

-- 5. Creamos la tabla de Experiencia
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EXPERIENCIA]') AND type in (N'U'))
BEGIN
    CREATE TABLE EXPERIENCIA(
        ID_EXPERIENCIA INT IDENTITY(1,1) PRIMARY KEY, 
        CORREO_CANDIDATO VARCHAR(100), 
        EMPRESA VARCHAR(100), 
        PUESTO VARCHAR(100), 
        FUNCIONES VARCHAR(MAX), 
        FECHA_INICIO VARCHAR(50) NOT NULL, -- La cambiamos a VARCHAR para que en Java y la Web no te dé errores de conversión de fechas
        FECHA_FINAL VARCHAR(50) NULL,
        FOREIGN KEY(CORREO_CANDIDATO) REFERENCES CANDIDATOS(CORREO) ON DELETE CASCADE
    );
END
GO

-- 6. Creamos la tabla de Estudios
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ESTUDIOS]') AND type in (N'U'))
BEGIN
    CREATE TABLE ESTUDIOS(
        ID_ESTUDIO INT IDENTITY(1,1) PRIMARY KEY, 
        CORREO_CANDIDATO VARCHAR(100),
        INSTITUCION VARCHAR(100),
        TITULO VARCHAR(100), 
        DESCRIPCION VARCHAR(MAX), 
        FECHA_INICIO VARCHAR(50) NOT NULL, -- También en VARCHAR para manejo simple en las cajas de texto
        FECHA_FINAL VARCHAR(50) NULL,
        FOREIGN KEY(CORREO_CANDIDATO) REFERENCES CANDIDATOS(CORREO) ON DELETE CASCADE
    );
END
GO

-- 7. Limpiamos e Insertamos el administrador de pruebas asegurándonos de que no se duplique
DELETE FROM USUARIOS WHERE USUARIO = 'admin';
GO
INSERT INTO USUARIOS(USUARIO, PASSWORD) VALUES ('admin', '1234');
GO

-- Verificaciones finales
SELECT * FROM USUARIOS;
SELECT * FROM CANDIDATOS;

UPDATE EXPERIENCIA 
SET EMPRESA = ?, 
    PUESTO = ?, 
    FUNCIONES = ?, 
    FECHA_INICIO = ?, 
    FECHA_FINAL = ?
WHERE ID_EXPERIENCIA = ?