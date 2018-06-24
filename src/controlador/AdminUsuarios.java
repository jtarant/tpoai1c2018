package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import modelo.ExceptionDeNegocio;
import modelo.Usuario;
import persistencia.AdmPersistenciaUsuarios;

public class AdminUsuarios {
	private static AdminUsuarios instancia;
	private Hashtable<String, Usuario> usuarios;
	private Usuario usuarioLogueado;
	
	private AdminUsuarios()
	{
		usuarios = new Hashtable<String, Usuario>();
		usuarioLogueado = null;
	}
	
	public static AdminUsuarios getInstancia()
	{
		if (instancia == null)
			instancia = new AdminUsuarios();
		return instancia;
	}
	
	public void crear(String idUsuario, String password, String nombre, String apellido, Date fechaNac, String email, Boolean sysAdmin) throws Exception
	{
		try
		{
			Usuario nuevoUsr = new Usuario(idUsuario, password, nombre, apellido, fechaNac, email, sysAdmin);
			usuarios.put(idUsuario, nuevoUsr);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public void modificar(String idUsuario, String password, String nombre, String apellido, Date fechaNac, String email, Boolean sysAdmin) throws Exception
	{
		try
		{
			if ((!getUsuarioLogueado().getIdUsuario().equals(idUsuario)) && 
			    (!getUsuarioLogueado().getSysAdmin()))
			{
				throw new ExceptionDeNegocio("Necesita permisos de sysadmin para modificar usuarios.");
			}  
			Usuario usr = buscar(idUsuario);
			usr.setPassword(password);
			usr.setNombre(nombre);
			usr.setApellido(apellido);
			usr.setFechaNac(fechaNac);
			usr.setEmail(email);
			usr.setSysAdmin(sysAdmin);
			usr.actualizar();
			usuarios.replace(usr.getIdUsuario(), usr);
			// Si modifique el usuario logueado, actualizo sus datos
			if (getUsuarioLogueado().getIdUsuario().equals(idUsuario))
			{
				this.usuarioLogueado = usr;
			}
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
			if (getUsuarioLogueado().getIdUsuario().equals(idUsuario))
			{
				throw new ExceptionDeNegocio("No se puede eliminar el usuario que se encuentra logueado actualmente.");
			}
			else
			{
				if (!getUsuarioLogueado().getSysAdmin()) throw new ExceptionDeNegocio("Necesita permisos de sysadmin para eliminar usuarios.");
			}			
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
			if (usr.getPassword().equals(password))
			{
				this.usuarioLogueado = usr;
				return true;
			}
			else 
			{
				this.usuarioLogueado = null;
				return false;
			}
		}
		else return false;
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
	
	public UsuarioView getUsuarioLogueado()
	{
		if (usuarioLogueado != null) return usuarioLogueado.getView();
		else return null;
	}
	
	public int getCantidadUsuarios() throws Exception
	{
		return AdmPersistenciaUsuarios.getInstancia().getCantidadUsuarios();
	}
}
