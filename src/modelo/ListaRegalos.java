package modelo;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import persistencia.AdmPersistenciaListasRegalos;
import persistencia.AdmPersistenciaUsuarios;

public class ListaRegalos 
{
	private Usuario admin;
	private Hashtable<String,Participante> participantes;
	private int codigo;
	private Date fechaAgasajo;
	private String nombreAgasajado;
	private float montoPorParticipante;
	private Date fechaInicio;
	private Date fechaFin;
	private EstadoListaRegalos estado;
	
	public ListaRegalos(String idUsuarioAdmin, Date fechaAgasajo, String nombreAgasajado, float monto, Date fechaInicio, Date fechaFin, List<String> idParticipantes) throws Exception
	{
		Usuario admin = AdmPersistenciaUsuarios.getInstancia().buscar(idUsuarioAdmin);
		setAdmin(admin);
		setFechaAgasajo(fechaAgasajo);
		setNombreAgasajado(nombreAgasajado);
		setMontoPorParticipante(monto);
		setFechaInicio(fechaInicio);
		setFechaFin(fechaFin);
		setEstado(EstadoListaRegalos.ABIERTA);
		
		participantes = new Hashtable<String,Participante>();
		for (String idParticipante : idParticipantes)
		{
			Usuario usr = AdmPersistenciaUsuarios.getInstancia().buscar(idParticipante);
			agregarParticipante(usr);
		}
		AdmPersistenciaListasRegalos.getInstancia().insertar(this);
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
	public void agregarParticipante(Usuario usr)
	{
		// El admin no puede ser participante, asi que si lo agregan, lo ignoro
		if (!usr.getIdUsuario().equals(getAdmin().getIdUsuario()))
		{
			Participante participante = new Participante(usr);
			participantes.put(usr.getIdUsuario(), participante);
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
	public Collection<Participante> getParticipantes()
	{
		return participantes.values();
	}
}
