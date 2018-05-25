package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Usuario;
import persistencia.AdmPersistenciaUsuarios;

public class AdminUsuarios {
	private static AdminUsuarios instancia;
	private List<Usuario> usuarios;
	
	private AdminUsuarios()
	{
		usuarios = new ArrayList<Usuario>();
	}
	
	public static AdminUsuarios getInstancia()
	{
		if (instancia == null)
			instancia = new AdminUsuarios();
		return instancia;
	}
	
	public void Crear(String idUsuario, String password, String nombre, String apellido, Date fechaNac, String email) throws Exception
	{
		try
		{
			Usuario nuevoUsr = new Usuario(idUsuario, password, nombre, apellido, fechaNac, email);
			usuarios.add(nuevoUsr);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public Usuario Buscar(String idUsuario) throws Exception
	{
		for (Usuario usr: usuarios)
		{
			if (usr.getIdUsuario()==idUsuario.trim().toLowerCase())
				return usr;
		}
		return AdmPersistenciaUsuarios.getInstancia().Buscar(idUsuario);
	}
	
	public Usuario AutenticarUsuario(String idUsuario, String password) throws Exception
	{
		Usuario usr = this.Buscar(idUsuario);
		if (usr != null)
		{
			if (usr.getPassword()==password)
			{
				return usr;
			}
			else return null;
		}
		else return null;
	}
}
