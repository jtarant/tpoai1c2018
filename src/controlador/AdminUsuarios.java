package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import modelo.Usuario;
import persistencia.AdmPersistenciaUsuarios;

public class AdminUsuarios {
	private static AdminUsuarios instancia;
	private Hashtable<String, Usuario> usuarios;
	
	private AdminUsuarios()
	{
		usuarios = new Hashtable<String, Usuario>();
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
			usuarios.put(idUsuario, nuevoUsr);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public void Modificar(Usuario usuario) throws Exception
	{
		try
		{
			usuario.actualizar();
			usuarios.replace(usuario.getIdUsuario(), usuario);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	
	public Usuario Buscar(String idUsuario) throws Exception
	{
		String id = idUsuario.trim().toLowerCase();
		// Primero lo busco en la coleccion de usuarios, que uso como cache
		Usuario usr = null;
		usr = usuarios.get(id);
		if (usr != null)
			return usr;
		else
			// Si no lo encontre, voy a la base a buscarlo y si estaba, lo agrego a la coleccion para tenerlo cacheado
			usr = AdmPersistenciaUsuarios.getInstancia().Buscar(idUsuario);
			if (usr != null) usuarios.put(idUsuario, usr);
			return usr;
	}
	
	public Usuario AutenticarUsuario(String idUsuario, String password) throws Exception
	{
		Usuario usr = this.Buscar(idUsuario);
		if (usr != null)
		{
			if (usr.getPassword().equals(password))
			{
				return usr;
			}
			else return null;
		}
		else return null;
	}

	public List<UsuarioIDNombreView> ListarUsuarios() throws Exception
	{
		List<Usuario> usuarios = AdmPersistenciaUsuarios.getInstancia().ListarUsuarios();
		
		List<UsuarioIDNombreView> lista = new ArrayList<UsuarioIDNombreView>();
		for (Usuario usr: usuarios)
		{
			lista.add(usr.getIDNombreView());
		}
		return lista;
	}
}
