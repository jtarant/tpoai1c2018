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
	
	public void crear(String idUsuario, String password, String nombre, String apellido, Date fechaNac, String email) throws Exception
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

	public void modificar(String idUsuario, String password, String nombre, String apellido, Date fechaNac, String email) throws Exception
	{
		try
		{
			Usuario usr = buscar(idUsuario);
			usr.setPassword(password);
			usr.setNombre(nombre);
			usr.setApellido(apellido);
			usr.setFechaNac(fechaNac);
			usr.setEmail(email);
			usr.actualizar();
			usuarios.replace(usr.getIdUsuario(), usr);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void eliminar(String idUsuario) throws Exception 
	{
		try 
		{
			Usuario usr = buscar(idUsuario);
			usr.eliminar();
			usuarios.remove(idUsuario);
		}
		catch (Exception e)
		{
			throw e;
		}		
	}
	
	private Usuario buscar(String idUsuario) throws Exception
	{
		String id = idUsuario.trim().toLowerCase();
		// Primero lo busco en la coleccion de usuarios, que uso como cache
		Usuario usr = null;
		usr = usuarios.get(id);
		if (usr != null)
			return usr;
		else
			// Si no lo encontre, voy a la base a buscarlo y si estaba, lo agrego a la coleccion para tenerlo cacheado
			usr = AdmPersistenciaUsuarios.getInstancia().buscar(idUsuario);
			if (usr != null) usuarios.put(idUsuario, usr);
			return usr;
	}
	
	public UsuarioView obtener(String idUsuario) throws Exception
	{
		Usuario usr = this.buscar(idUsuario);
		if (usr != null)
			return usr.getView();
		else return null;
	}
	
	public Boolean autenticar(String idUsuario, String password) throws Exception
	{
		Usuario usr = this.buscar(idUsuario);
		if (usr != null)
		{
			return usr.getPassword().equals(password); 
		}
		else return null;
	}

	public List<UsuarioIdNombreView> listarIdNombre() throws Exception
	{
		List<Usuario> usuarios = AdmPersistenciaUsuarios.getInstancia().listarIdNombre();
		
		List<UsuarioIdNombreView> lista = new ArrayList<UsuarioIdNombreView>();
		for (Usuario usr: usuarios)
		{
			lista.add(usr.getIDNombreView());
		}
		return lista;
	}
}
