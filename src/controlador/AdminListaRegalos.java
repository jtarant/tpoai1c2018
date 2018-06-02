package controlador;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import modelo.EstadoListaRegalos;
import modelo.ExceptionDeNegocio;
import modelo.ListaRegalos;
import modelo.Usuario;
import persistencia.AdmPersistenciaListasRegalos;
import persistencia.AdmPersistenciaUsuarios;


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

	public void modificar(int codigo, Date fechaAgasajo, String nombreAgasajado, float monto, Date fechaInicio, Date fechaFin, List<String> idParticipantes) throws Exception
	{
		try
		{
			ListaRegalos lista = buscar(codigo);
			lista.setFechaAgasajo(fechaAgasajo);
			lista.setNombreAgasajado(nombreAgasajado);
			lista.setMontoPorParticipante(monto);
			lista.setFechaInicio(fechaInicio);
			lista.setFechaFin(fechaFin);
			//lista.setEstado(EstadoListaRegalos.);

			
			
			
			
			// ver de pasar la lista de participantes, sin pensar en los pagos. aca solo participan o no. para modificar un pago sera otro metodo
			lista.actualizar();
			listas.replace(lista.getCodigo(), lista);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void eliminar(int codigo) throws Exception 
	{
		try 
		{
			ListaRegalos lista = this.buscar(codigo);
			if (!lista.getAdmin().getIdUsuario().equals(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario()))
			{
				throw new ExceptionDeNegocio("Solo el usuario administrador de esta lista puede eliminarla.");
			}
			lista.eliminar();
			this.listas.remove(codigo);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	private ListaRegalos buscar(int codigo) throws Exception
	{
		// Primero lo busco en la coleccion de listas de regalos, que uso como cache
		ListaRegalos lista = null;
		lista = listas.get(codigo);
		if (lista != null)
			return lista;
		else
		{
			// Si no lo encontre, voy a la base a buscarlo y si estaba, lo agrego a la coleccion para tenerlo cacheado
			lista = AdmPersistenciaListasRegalos.getInstancia().buscar(codigo);
			if (lista != null) listas.put(codigo, lista);
			return lista;
		}
	}
	
	public ListaRegalosView obtener(int codigo) throws Exception
	{
		ListaRegalos lista = this.buscar(codigo);
		if (lista != null)
			return lista.getView();
		else return null;
	}
	
	public List<ListaResumenView> listarMisListas(String idUsuario) throws Exception
	{
		Usuario usr = AdmPersistenciaUsuarios.getInstancia().buscar(idUsuario);
		return AdmPersistenciaListasRegalos.getInstancia().listarMisListas(usr);
	}

	public void dejarDeParticipar(int codigo) throws Exception 
	{
		ListaRegalos lista = this.buscar(codigo);
		if (lista != null)
		{
			lista.quitarParticipante(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario());
		}
	}
}
