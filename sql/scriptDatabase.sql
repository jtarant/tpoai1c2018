USE [master]
GO
/****** Object:  Database [TPO_AI_TARANTINO_CALISI]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'TPO_AI_TARANTINO_CALISI')
BEGIN
CREATE DATABASE [TPO_AI_TARANTINO_CALISI]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'TPO_AI_TARANTINO_CALISI', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\TPO_AI_TARANTINO_CALISI.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'TPO_AI_TARANTINO_CALISI_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\TPO_AI_TARANTINO_CALISI_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
END

GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [TPO_AI_TARANTINO_CALISI].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET ARITHABORT OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET  DISABLE_BROKER 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET  MULTI_USER 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET DB_CHAINING OFF 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [usrconsultorio]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'usrconsultorio')
CREATE LOGIN [usrconsultorio] WITH PASSWORD=N'xAZ0KINzO8UXI2hBBHJBDAOIR62hcsp8OOTncIXI/NU=', DEFAULT_DATABASE=[CONSULTORIO_PROD], DEFAULT_LANGUAGE=[Español], CHECK_EXPIRATION=OFF, CHECK_POLICY=ON
GO
ALTER LOGIN [usrconsultorio] DISABLE
GO
/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [tpoai]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'tpoai')
CREATE LOGIN [tpoai] WITH PASSWORD=N'aW+gkSWwnIJJflB2NRDX3RtAD7W5eFF+A7tVppyWrXU=', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
ALTER LOGIN [tpoai] DISABLE
GO
/****** Object:  Login [NT SERVICE\Winmgmt]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'NT SERVICE\Winmgmt')
CREATE LOGIN [NT SERVICE\Winmgmt] FROM WINDOWS WITH DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español]
GO
/****** Object:  Login [NT SERVICE\SQLWriter]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'NT SERVICE\SQLWriter')
CREATE LOGIN [NT SERVICE\SQLWriter] FROM WINDOWS WITH DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español]
GO
/****** Object:  Login [NT SERVICE\ReportServer]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'NT SERVICE\ReportServer')
CREATE LOGIN [NT SERVICE\ReportServer] FROM WINDOWS WITH DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español]
GO
/****** Object:  Login [NT Service\MSSQLSERVER]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'NT Service\MSSQLSERVER')
CREATE LOGIN [NT Service\MSSQLSERVER] FROM WINDOWS WITH DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español]
GO
/****** Object:  Login [NT AUTHORITY\SYSTEM]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'NT AUTHORITY\SYSTEM')
CREATE LOGIN [NT AUTHORITY\SYSTEM] FROM WINDOWS WITH DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español]
GO
/****** Object:  Login [Jose-Desktop\Jose]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'Jose-Desktop\Jose')
CREATE LOGIN [Jose-Desktop\Jose] FROM WINDOWS WITH DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español]
GO
/****** Object:  Login [BUILTIN\Usuarios]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'BUILTIN\Usuarios')
CREATE LOGIN [BUILTIN\Usuarios] FROM WINDOWS WITH DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español]
GO
/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [##MS_PolicyTsqlExecutionLogin##]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'##MS_PolicyTsqlExecutionLogin##')
CREATE LOGIN [##MS_PolicyTsqlExecutionLogin##] WITH PASSWORD=N'/GYgLClIAvv2PBvpGGCbQ9JBAy12wVDIjvi01+ndYqU=', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=ON
GO
ALTER LOGIN [##MS_PolicyTsqlExecutionLogin##] DISABLE
GO
/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [##MS_PolicyEventProcessingLogin##]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'##MS_PolicyEventProcessingLogin##')
CREATE LOGIN [##MS_PolicyEventProcessingLogin##] WITH PASSWORD=N'C6/FP1zdXdErlp8f6g8ne/8ldLbQmQ5MDrzySIw34v8=', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[Español], CHECK_EXPIRATION=OFF, CHECK_POLICY=ON
GO
ALTER LOGIN [##MS_PolicyEventProcessingLogin##] DISABLE
GO
ALTER AUTHORIZATION ON DATABASE::[TPO_AI_TARANTINO_CALISI] TO [Jose-Desktop\Jose]
GO
ALTER SERVER ROLE [sysadmin] ADD MEMBER [usrconsultorio]
GO
ALTER SERVER ROLE [dbcreator] ADD MEMBER [usrconsultorio]
GO
ALTER SERVER ROLE [sysadmin] ADD MEMBER [NT SERVICE\Winmgmt]
GO
ALTER SERVER ROLE [sysadmin] ADD MEMBER [NT SERVICE\SQLWriter]
GO
ALTER SERVER ROLE [sysadmin] ADD MEMBER [NT Service\MSSQLSERVER]
GO
ALTER SERVER ROLE [sysadmin] ADD MEMBER [Jose-Desktop\Jose]
GO
USE [TPO_AI_TARANTINO_CALISI]
GO
/****** Object:  User [tpoai]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'tpoai')
CREATE USER [tpoai] FOR LOGIN [tpoai] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [tpoai]
GO
/****** Object:  Table [dbo].[ListasRegalos]    Script Date: 26/05/2018 18:36:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ListasRegalos]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ListasRegalos](
	[CodigoLista] [int] IDENTITY(1,1) NOT NULL,
	[IdUsuarioAdmin] [varchar](15) NOT NULL,
	[FechaAgasajo] [date] NOT NULL,
	[NombreAgasajado] [varchar](50) NOT NULL,
	[MontoPorParticipante] [float] NOT NULL,
	[FechaInicio] [date] NOT NULL,
	[FechaFin] [date] NOT NULL,
	[Estado] [int] NOT NULL,
 CONSTRAINT [PK_ListasRegalos] PRIMARY KEY CLUSTERED 
(
	[CodigoLista] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
ALTER AUTHORIZATION ON [dbo].[ListasRegalos] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[Participantes]    Script Date: 26/05/2018 18:36:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Participantes]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Participantes](
	[CodigoLista] [int] NOT NULL,
	[IdUsuario] [varchar](15) NOT NULL,
	[FechaPago] [date] NULL,
 CONSTRAINT [PK_Participantes] PRIMARY KEY CLUSTERED 
(
	[CodigoLista] ASC,
	[IdUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
ALTER AUTHORIZATION ON [dbo].[Participantes] TO  SCHEMA OWNER 
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 26/05/2018 18:36:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuarios]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Usuarios](
	[IdUsuario] [varchar](15) NOT NULL,
	[Password] [varchar](15) NOT NULL,
	[Nombre] [varchar](50) NOT NULL,
	[Apellido] [varchar](50) NOT NULL,
	[FechaNac] [date] NOT NULL,
	[Email] [varchar](128) NOT NULL,
	[Activo] [bit] NOT NULL,
 CONSTRAINT [PK_Usuarios] PRIMARY KEY CLUSTERED 
(
	[IdUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
ALTER AUTHORIZATION ON [dbo].[Usuarios] TO  SCHEMA OWNER 
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_ListasRegalos]    Script Date: 26/05/2018 18:36:33 ******/
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[ListasRegalos]') AND name = N'IX_ListasRegalos')
CREATE UNIQUE NONCLUSTERED INDEX [IX_ListasRegalos] ON [dbo].[ListasRegalos]
(
	[IdUsuarioAdmin] ASC,
	[FechaAgasajo] ASC,
	[NombreAgasajado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ListasRegalos_Usuarios]') AND parent_object_id = OBJECT_ID(N'[dbo].[ListasRegalos]'))
ALTER TABLE [dbo].[ListasRegalos]  WITH CHECK ADD  CONSTRAINT [FK_ListasRegalos_Usuarios] FOREIGN KEY([IdUsuarioAdmin])
REFERENCES [dbo].[Usuarios] ([IdUsuario])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ListasRegalos_Usuarios]') AND parent_object_id = OBJECT_ID(N'[dbo].[ListasRegalos]'))
ALTER TABLE [dbo].[ListasRegalos] CHECK CONSTRAINT [FK_ListasRegalos_Usuarios]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Participantes_ListasRegalos]') AND parent_object_id = OBJECT_ID(N'[dbo].[Participantes]'))
ALTER TABLE [dbo].[Participantes]  WITH CHECK ADD  CONSTRAINT [FK_Participantes_ListasRegalos] FOREIGN KEY([CodigoLista])
REFERENCES [dbo].[ListasRegalos] ([CodigoLista])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Participantes_ListasRegalos]') AND parent_object_id = OBJECT_ID(N'[dbo].[Participantes]'))
ALTER TABLE [dbo].[Participantes] CHECK CONSTRAINT [FK_Participantes_ListasRegalos]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Participantes_Usuarios]') AND parent_object_id = OBJECT_ID(N'[dbo].[Participantes]'))
ALTER TABLE [dbo].[Participantes]  WITH CHECK ADD  CONSTRAINT [FK_Participantes_Usuarios] FOREIGN KEY([IdUsuario])
REFERENCES [dbo].[Usuarios] ([IdUsuario])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Participantes_Usuarios]') AND parent_object_id = OBJECT_ID(N'[dbo].[Participantes]'))
ALTER TABLE [dbo].[Participantes] CHECK CONSTRAINT [FK_Participantes_Usuarios]
GO
USE [master]
GO
ALTER DATABASE [TPO_AI_TARANTINO_CALISI] SET  READ_WRITE 
GO
