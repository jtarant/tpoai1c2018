package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import modelo.EstadoListaRegalos;
import modelo.ExceptionDeNegocio;
import modelo.ListaRegalos;
import modelo.NotificadorEmail;
import modelo.Participante;
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
			agregarCache(lista);
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
			
			if  (!((lista.getAdmin().getIdUsuario().equals(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario())) ||
				    (AdminUsuarios.getInstancia().getUsuarioLogueado().getSysAdmin())))
				{
					throw new ExceptionDeNegocio("Solo el usuario administrador de esta lista o el sysadmin pueden modificarla.");
				}
			lista.setFechaAgasajo(fechaAgasajo);
			lista.setNombreAgasajado(nombreAgasajado);
			lista.setMontoPorParticipante(monto);
			lista.setFechaInicio(fechaInicio);
			lista.setFechaFin(fechaFin);
			
			// Quito los participantes que ya no van a estar
			List<Participante> copiaParticipantes = new ArrayList<Participante>(lista.getParticipantes());
			for (Participante p: copiaParticipantes)
			{
				Boolean sigueEstando = false;
				Iterator<String> i = idParticipantes.iterator();
				while (i.hasNext() && !sigueEstando) 
				{
					String id = (String)i.next();
					if (id.equals(p.getUsuario().getIdUsuario())) sigueEstando = true;
				}
				if (!sigueEstando)
				{
					System.out.println("se quito: " + p.getUsuario().getIdUsuario());
					lista.quitarParticipante(p.getUsuario().getIdUsuario());
				}
			}

			// Agrego los participantes que no esten actualmente
			for	(String idParticipante : idParticipantes)
			{
				if (lista.getParticipante(idParticipante) == null)
				{
					Usuario usr = AdmPersistenciaUsuarios.getInstancia().buscar(idParticipante);
					if (usr != null)
					{
						System.out.println("se agrego: " + usr.getIdUsuario());
						lista.agregarParticipante(new Participante(usr));
					}
				}
			}
			
			lista.actualizar();
			actualizarCache(lista);
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
			if  (!((lista.getAdmin().getIdUsuario().equals(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario())) ||
			    (AdminUsuarios.getInstancia().getUsuarioLogueado().getSysAdmin())))
			{
				throw new ExceptionDeNegocio("Solo el usuario administrador de esta lista o el sysadmin pueden eliminarla.");
			}
			lista.eliminar();
			eliminarCache(codigo);
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
			if (lista != null)
			{
				agregarCache(lista);
				lista.addObserver(NotificadorEmail.getInstancia());
			}
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
		eliminarCache(codigo);	// Quiero asegurarme tener la version mas reciente de la lista para no abandonar una lista cerrada por el demonio
		
		ListaRegalos lista = this.buscar(codigo);
		if (lista != null)
		{
			if (lista.getEstado() == EstadoListaRegalos.CERRADA)
				throw new ExceptionDeNegocio("No podes darte de baja de una lista ya cerrada.");
			
			lista.quitarParticipante(AdminUsuarios.getInstancia().getUsuarioLogueado().getIdUsuario());
			lista.actualizar();
			actualizarCache(lista);
		}
	}
	
	public void registrarPago(int codigoLista, String idUsuario, Date fecha) throws Exception
	{
		eliminarCache(codigoLista);	// Quiero asegurarme tener la version mas reciente de la lista para actualizar los pagos
		
		ListaRegalos lista = this.buscar(codigoLista);
		if (lista != null)
		{
			lista.registrarPago(idUsuario, fecha);
			lista.actualizar();
			actualizarCache(lista);
		}
		else throw new ExceptionDeNegocio("La lista " + Integer.toString(codigoLista) + " no existe.");
	}

	public void procesarProximasCierre() throws Exception 
	{
		int DIAS_ANTES_CIERRE = 3;
		List<ListaRegalos> vencen = AdmPersistenciaListasRegalos.getInstancia().obtenerProximasCierre(DIAS_ANTES_CIERRE);
		
		for (ListaRegalos lista : vencen)
		{
			agregarCache(lista);
			lista.addObserver(NotificadorEmail.getInstancia());
			lista.setEstado(EstadoListaRegalos.PROXIMO_CIERRE);
			lista.actualizar();
			actualizarCache(lista);
		}	
	}
	
	private void agregarCache(ListaRegalos lista)
	{
		this.listas.put(lista.getCodigo(), lista);
	}
	private void actualizarCache(ListaRegalos lista)
	{
		this.listas.replace(lista.getCodigo(), lista);
	}
	private void eliminarCache(int codigoLista)
	{
		if (this.listas.containsKey(codigoLista))
		{
			this.listas.remove(codigoLista);
		}
	}
	public void limpiarCache()
	{
		this.listas.clear();
	}
}
