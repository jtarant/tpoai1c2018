package vista;

import java.util.Date;

public class UsuarioView {
		private String idUsuario;
		private String password;
		private String nombre;
		private String apellido;
		private Date fechaNac;
		private String email;
		private Boolean activo;
		
		public UsuarioView(String id, String pwd, String n, String a, Date fn, String e, Boolean s)
		{
			idUsuario = id;
			password = pwd;
			nombre = n;
			apellido = a;
			fechaNac = fn;
			email = e;
			activo = s;
		}
		
		public String getIdUsuario()
		{
			return idUsuario;
		}
		public String getPassword()
		{
			return password;
		}
		public String getNombre()
		{
			return nombre;
		}
		public String getApellido()
		{
			return apellido;
		}
		public Date getFechaNac()
		{
			return fechaNac;
		}
		public String getEmail()
		{
			return email;
		}
		public Boolean getActivo()
		{
			return activo;
		}
}
