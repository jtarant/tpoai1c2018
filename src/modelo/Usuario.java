package modelo;

import java.util.Date;

import controlador.UsuarioIDNombreView;
import persistencia.AdmPersistenciaUsuarios;

public class Usuario {
	private String idUsuario;
	private String password;
	private String nombre;
	private String apellido;
	private Date fechaNac;
	private String email;
	private Boolean activo;
	
	public Usuario(String idUsuario, String password, String nombre, String apellido, Date fechaNac, String email) throws Exception
	{
		this.setIdUsuario(idUsuario);
		this.setPassword(password);
		this.setNombre(nombre);
		this.setApellido(apellido);
		this.setFechaNac(fechaNac);
		this.setEmail(email);
		this.activo = true;
		AdmPersistenciaUsuarios.getInstancia().Insertar(this);
	}
	
	public Usuario(String idUsuario, String password, String nombre, String apellido, Date fechaNac, String email, Boolean activo)
	{
		this.setIdUsuario(idUsuario);
		this.setPassword(password);
		this.setNombre(nombre);
		this.setApellido(apellido);
		this.setFechaNac(fechaNac);
		this.setEmail(email);
		this.setActivo(activo);
	}
	
	public void setIdUsuario(String u)
	{
		idUsuario = u.trim().toLowerCase();
	}
	public void setPassword(String pwd)
	{
		if (pwd != null) password = pwd.trim();
	}
	public void setNombre(String n)
	{
		nombre = n.toUpperCase();
	}
	public void setApellido(String a)
	{
		apellido = a.toUpperCase();
	}
	public void setFechaNac(Date d)
	{
		fechaNac = d;
	}
	public void setEmail(String e)
	{
		if (e != null) email = e.trim().toLowerCase();
	}
	public void setActivo(Boolean a)
	{
		activo = a;
	}
	public String getIdUsuario()
	{
		return idUsuario.trim().toLowerCase();
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
	public UsuarioIDNombreView getIDNombreView()
	{
		return new UsuarioIDNombreView(getIdUsuario(),getNombre(),getApellido());
	}
	public void actualizar() throws Exception 
	{
		AdmPersistenciaUsuarios.getInstancia().Modificar(this);
	}
}
