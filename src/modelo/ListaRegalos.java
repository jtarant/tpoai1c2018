package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Observable;

import controlador.ListaRegalosView;
import controlador.ParticipanteView;
import persistencia.AdmPersistenciaListasRegalos;
import persistencia.AdmPersistenciaUsuarios;

public class ListaRegalos extends Observable
{
	private Usuario admin;
	private Hashtable<String,Participante> participantes;
	private List<Participante> nuevos;
	private List<Participante> modificados;
	private List<Participante> eliminados;
	private int codigo;
	private Date fechaAgasajo;
	private String nombreAgasajado;
	private float montoPorParticipante;
	private Date fechaInicio;
	private Date fechaFin;
	private EstadoListaRegalos estado;
	
	public ListaRegalos(String idUsuarioAdmin, Date fechaAgasajo, String nombreAgasajado, float monto, Date fechaInicio, Date fechaFin, List<String> idParticipantes) throws Exception
	{
		participantes = new Hashtable<String,Participante>();
		resetearCambiosParticipantes();
		Usuario admin = AdmPersistenciaUsuarios.getInstancia().buscar(idUsuarioAdmin);
		setAdmin(admin);
		setFechaAgasajo(fechaAgasajo);
		setNombreAgasajado(nombreAgasajado);
		setMontoPorParticipante(monto);
		setFechaInicio(fechaInicio);
		setFechaFin(fechaFin);
		setEstado(EstadoListaRegalos.ABIERTA);
		
		for (String idParticipante : idParticipantes)
		{
			Usuario usr = AdmPersistenciaUsuarios.getInstancia().buscar(idParticipante);
			agregarParticipante(new Participante(usr));
		}
		AdmPersistenciaListasRegalos.getInstancia().insertar(this);
		addObserver(NotificadorEmail.getInstancia());
		setChanged();
		notifyObservers(nuevos);
		resetearCambiosParticipantes();
	}

	public ListaRegalos(int codigo, Usuario admin, Date fechaAgasajo, String nombreAgasajado, float monto, Date fechaInicio, Date fechaFin, EstadoListaRegalos estado) throws Exception
	{
		participantes = new Hashtable<String,Participante>();
		resetearCambiosParticipantes();
		setCodigo(codigo);
		setAdmin(admin);
		setFechaAgasajo(fechaAgasajo);
		setNombreAgasajado(nombreAgasajado);
		setMontoPorParticipante(monto);
		setFechaInicio(fechaInicio);
		setFechaFin(fechaFin);
		setEstado(estado);
	}
	
	public void setAdmin(Usuario usr)
	{
		admin = usr;
	}
	public void setCodigo(int codigo)
	{
		this.codigo = codigo;
	}
	public void setFechaAgasajo(Date fecha)
	{
		fechaAgasajo = fecha;
	}
	public void setNombreAgasajado(String nombre)
	{
		nombreAgasajado = nombre.toUpperCase();
	}
	public void setMontoPorParticipante(float valor)
	{
		montoPorParticipante = valor;
	}
	public void setFechaInicio(Date fecha)
	{
		fechaInicio = fecha;
	}
	public void setFechaFin(Date fecha)
	{
		fechaFin = fecha;
	}
	public void setEstado(EstadoListaRegalos estado)
	{
		this.estado = estado;
	}
	
	public void agregarParticipante(Participante p)
	{
		// El admin no puede ser participante, asi que si lo agregan, lo ignoro
		if (!p.getUsuario().getIdUsuario().equals(getAdmin().getIdUsuario()))
		{
			// Y solo lo agrego si no lo tenia ya en la lista
			if (participantes.get(p.getUsuario().getIdUsuario()) == null)
			{
				p.setLista(this);
				participantes.put(p.getUsuario().getIdUsuario(), p);
				this.nuevos.add(p);
			}
		}
	}
	
	public void quitarParticipante(String idUsuario) throws ExceptionDeNegocio 
	{
		Participante p = participantes.get(idUsuario);
		if (p == null)
		{
			throw new ExceptionDeNegocio("El usuario " + idUsuario + " no participa de esta lista");
		}
		else
		{
			this.participantes.remove(idUsuario);
			this.eliminados.add(p);
		}
	}
	
	public int getCodigo()
	{
		return codigo;
	}
	public Usuario getAdmin()
	{
		return admin;
	}
	public Date getFechaAgasajo()
	{
		return fechaAgasajo;
	}
	public String getNombreAgasajado()
	{
		return nombreAgasajado;
	}
	public float getMontoPorParticipante()
	{
		return montoPorParticipante;
	}
	public Date getFechaInicio()
	{
		return fechaInicio;
	}
	public Date getFechaFin()
	{
		return fechaFin;
	}
	public EstadoListaRegalos getEstado()
	{
		return estado;
	}
	public Participante getParticipante(String idUsuario)
	{
		return participantes.get(idUsuario);
	}
	public Collection<Participante> getParticipantes()
	{
		return participantes.values();
	}
	
	public ListaRegalosView getView()
	{
		List<ParticipanteView> partv = new ArrayList<ParticipanteView>();
		ParticipanteView pv;
		
		for (Participante p: getParticipantes())
		{
			pv = new ParticipanteView(p.getUsuario().getIdUsuario(), p.getFechaPago(), p.getMontoPagado());
			partv.add(pv);
		}
		return new ListaRegalosView(getCodigo(), getAdmin().getIdUsuario(), getFechaAgasajo(), getNombreAgasajado(), getMontoPorParticipante(), getFechaInicio(), getFechaFin(), getMontoRecaudado(), partv);
	}

	public void eliminar() throws Exception 
	{
		AdmPersistenciaListasRegalos.getInstancia().eliminar(this);
	}
	
	public void actualizar() throws Exception
	{
		AdmPersistenciaListasRegalos.getInstancia().actualizar(this);
		setChanged();
		notifyObservers(nuevos);
		resetearCambiosParticipantes();
	}
	
	public List<Participante> getParticipantesNuevos()
	{
		return nuevos;
	}
	
	public List<Participante> getParticipantesModificados()
	{
		return modificados;
	}
	
	public List<Participante> getParticipantesEliminados()
	{
		return eliminados;
	}
	
	public void registrarPago(String idUsuario, Date fechaPago) throws ExceptionDeNegocio
	{
		Participante p = getParticipante(idUsuario);
		if (p == null)
		{
			throw new ExceptionDeNegocio("El usuario " + idUsuario + " no participa de esta lista.");
		}
		else
		{
			p.setFechaPago(fechaPago);
			p.setMontoPagado(getMontoPorParticipante());	// Asumo que pagan el total, dado que el monto no viene en el archivo
		}
		if (!nuevos.contains(p))
		{
			modificados.add(p);
		}
		Boolean pagaronTodos = true;
		for	(Participante part: getParticipantes())
		{
			if (!part.getPagoRealizado())
			{
				pagaronTodos = false;
				break;
			}
		}
		if (pagaronTodos)
		{
			setEstado(EstadoListaRegalos.CERRADA);
		}		
	}
	
	public float getMontoRecaudado()
	{
		float monto = 0;
		for (Participante p: getParticipantes())
		{
			monto = monto + p.getMontoPagado();
		}
		return monto;
	}
	
	public void resetearCambiosParticipantes()
	{
		if (nuevos == null)
		{
			nuevos = new ArrayList<Participante>();
		} 
		else nuevos.clear();
		if (modificados == null)
		{
			modificados = new ArrayList<Participante>();
		} 
		else modificados.clear();
		if (eliminados == null)
		{
			eliminados = new ArrayList<Participante>();
		}
		else eliminados.clear();
	}	
}
