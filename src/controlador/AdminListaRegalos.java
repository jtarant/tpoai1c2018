package controlador;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import modelo.ListaRegalos;


public class AdminListaRegalos {
	private static AdminListaRegalos instancia;
	private Hashtable<Integer, ListaRegalos> listas;
	
	private AdminListaRegalos()
	{
		listas = new Hashtable<Integer, ListaRegalos>();
	}
	
	public static AdminListaRegalos getInstancia()
	{
		if (instancia == null)
			instancia = new AdminListaRegalos();
		return instancia;
	}
	
	public void crear(String idUsuarioAdmin, Date fechaAgasajo, String nombreAgasajado, float monto, Date fechaInicio, Date fechaFin, List<String> idParticipantes) throws Exception
	{
		try
		{
			ListaRegalos lista = new ListaRegalos(idUsuarioAdmin, fechaAgasajo, nombreAgasajado, monto, fechaInicio, fechaFin, idParticipantes);
			listas.put(lista.getCodigo(), lista);
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
/*			Usuario usr = buscar(idUsuario);
			usr.setPassword(password);
			usr.setNombre(nombre);
			usr.setApellido(apellido);
			usr.setFechaNac(fechaNac);
			usr.setEmail(email);
			usr.actualizar();
			usuarios.replace(usr.getIdUsuario(), usr);
*/		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void eliminar(String idUsuario) throws Exception 
	{
		try 
		{
/*			Usuario usr = buscar(idUsuario);
			usr.eliminar();
			usuarios.remove(idUsuario);
*/		}
		catch (Exception e)
		{
			throw e;
		}		
	}
	
/*	private ListaRegalos buscar(int codigo) throws Exception
	{
		// Primero lo busco en la coleccion de listas de regalos, que uso como cache
		ListaRegalos lista = null;
		lista = listas.get(codigo);
		if (lista != null)
			return lista;
		else
			// Si no lo encontre, voy a la base a buscarlo y si estaba, lo agrego a la coleccion para tenerlo cacheado
			//usr = AdmPersistenciaUsuarios.getInstancia().buscar(idUsuario);
			if (lista != null) listas.put(codigo, lista);
			return lista;
	}
*/	
/*	public UsuarioView obtener(String idUsuario) throws Exception
	{
		Usuario usr = this.buscar(idUsuario);
		if (usr != null)
			return usr.getView();
		else return null;
	}
*/	
/*	public List<UsuarioIdNombreView> listarIdNombre() throws Exception
	{
		List<Usuario> usuarios = AdmPersistenciaUsuarios.getInstancia().listarIdNombre();
		
		List<UsuarioIdNombreView> lista = new ArrayList<UsuarioIdNombreView>();
		for (Usuario usr: usuarios)
		{
			lista.add(usr.getIDNombreView());
		}
		return lista;
	}
*/
}
