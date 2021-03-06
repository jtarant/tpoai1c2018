USE [TPO_AI_TARANTINO_CALISI]
GO
INSERT [dbo].[Usuarios] ([IdUsuario], [Password], [Nombre], [Apellido], [FechaNac], [Email], [Activo], [sysadmin]) VALUES (N'jtarant', N'1234', N'JOSE LUIS', N'TARANTINO', CAST(N'2018-06-09' AS Date), N'joseluistarantino@gmail.com', 1, 1)
INSERT [dbo].[Usuarios] ([IdUsuario], [Password], [Nombre], [Apellido], [FechaNac], [Email], [Activo], [sysadmin]) VALUES (N'lcalisi', N'1234', N'LUCAS', N'CALISI', CAST(N'2018-06-09' AS Date), N'joseluistarantino@gmail.com', 1, 0)
INSERT [dbo].[Usuarios] ([IdUsuario], [Password], [Nombre], [Apellido], [FechaNac], [Email], [Activo], [sysadmin]) VALUES (N'lmessi', N'1234', N'LIONEL', N'MESSI', CAST(N'1985-01-01' AS Date), N'joseluistarantino@gmail.com', 1, 0)
INSERT [dbo].[Usuarios] ([IdUsuario], [Password], [Nombre], [Apellido], [FechaNac], [Email], [Activo], [sysadmin]) VALUES (N'masche', N'1234', N'JAVIER', N'MASCHERANO', CAST(N'2018-06-24' AS Date), N'joseluistarantino@gmail.com', 0, 0)
INSERT [dbo].[Usuarios] ([IdUsuario], [Password], [Nombre], [Apellido], [FechaNac], [Email], [Activo], [sysadmin]) VALUES (N'mmeza', N'1234', N'MAXI', N'MEZA', CAST(N'1998-06-25' AS Date), N'joseluistarantino@gmail.com', 1, 0)
INSERT [dbo].[Usuarios] ([IdUsuario], [Password], [Nombre], [Apellido], [FechaNac], [Email], [Activo], [sysadmin]) VALUES (N'sole', N'1234', N'SOLE', N'RAMOS', CAST(N'1978-08-13' AS Date), N'joseluistarantino@gmail.com', 1, 0)
SET IDENTITY_INSERT [dbo].[ListasRegalos] ON 

INSERT [dbo].[ListasRegalos] ([CodigoLista], [IdUsuarioAdmin], [FechaAgasajo], [NombreAgasajado], [MontoPorParticipante], [FechaInicio], [FechaFin], [Estado], [Activo]) VALUES (1, N'jtarant', CAST(N'2018-06-24' AS Date), N'SAMPAOLI', 3, CAST(N'2018-06-24' AS Date), CAST(N'2018-07-09' AS Date), 1, 0)
INSERT [dbo].[ListasRegalos] ([CodigoLista], [IdUsuarioAdmin], [FechaAgasajo], [NombreAgasajado], [MontoPorParticipante], [FechaInicio], [FechaFin], [Estado], [Activo]) VALUES (2, N'jtarant', CAST(N'2018-06-24' AS Date), N'JUAN PEREZ', 30, CAST(N'2018-06-24' AS Date), CAST(N'2018-06-24' AS Date), 1, 1)
INSERT [dbo].[ListasRegalos] ([CodigoLista], [IdUsuarioAdmin], [FechaAgasajo], [NombreAgasajado], [MontoPorParticipante], [FechaInicio], [FechaFin], [Estado], [Activo]) VALUES (7, N'jtarant', CAST(N'2018-06-25' AS Date), N'JOHN DOE', 75, CAST(N'2018-06-01' AS Date), CAST(N'2018-06-10' AS Date), 1, 0)
INSERT [dbo].[ListasRegalos] ([CodigoLista], [IdUsuarioAdmin], [FechaAgasajo], [NombreAgasajado], [MontoPorParticipante], [FechaInicio], [FechaFin], [Estado], [Activo]) VALUES (8, N'jtarant', CAST(N'2018-06-30' AS Date), N'HOMERO SIMPSON', 90, CAST(N'2018-06-01' AS Date), CAST(N'2018-06-24' AS Date), 1, 1)
INSERT [dbo].[ListasRegalos] ([CodigoLista], [IdUsuarioAdmin], [FechaAgasajo], [NombreAgasajado], [MontoPorParticipante], [FechaInicio], [FechaFin], [Estado], [Activo]) VALUES (9, N'jtarant', CAST(N'2018-08-04' AS Date), N'JOSE', 300, CAST(N'2018-06-20' AS Date), CAST(N'2018-06-25' AS Date), 1, 0)
INSERT [dbo].[ListasRegalos] ([CodigoLista], [IdUsuarioAdmin], [FechaAgasajo], [NombreAgasajado], [MontoPorParticipante], [FechaInicio], [FechaFin], [Estado], [Activo]) VALUES (10, N'jtarant', CAST(N'2018-08-03' AS Date), N'JOSE LUIS', 450, CAST(N'2018-06-23' AS Date), CAST(N'2018-06-24' AS Date), 1, 1)
SET IDENTITY_INSERT [dbo].[ListasRegalos] OFF
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (1, N'lmessi', CAST(N'2018-06-20' AS Date), 3)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (1, N'masche', NULL, 0)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (2, N'lcalisi', CAST(N'2018-06-20' AS Date), 30)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (7, N'lcalisi', NULL, 0)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (7, N'sole', NULL, 0)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (8, N'lcalisi', CAST(N'2018-06-02' AS Date), 90)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (8, N'lmessi', NULL, 0)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (8, N'mmeza', CAST(N'2018-06-16' AS Date), 90)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (8, N'sole', CAST(N'2018-06-25' AS Date), 90)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (9, N'sole', NULL, 0)
INSERT [dbo].[Participantes] ([CodigoLista], [IdUsuario], [FechaPago], [MontoPagado]) VALUES (10, N'sole', NULL, 0)
