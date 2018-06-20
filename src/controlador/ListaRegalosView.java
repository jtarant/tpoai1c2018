package controlador;

import java.util.Date;
import java.util.List;

public class ListaRegalosView 
{
	private int codigo;
	private String idUsuarioAdmin;
	private List<ParticipanteView> participantes;
	private Date fechaAgasajo;
	private String nombreAgasajado;
	private float montoPorParticipante;
	private Date fechaInicio;
	private Date fechaFin;
	private int estado;
	private float montoRecaudado;
	
	public ListaRegalosView(int codigo, String idUsuarioAdmin, Date fechaAgasajo, String nombreAgasajado, float monto, Date fechaInicio, Date fechaFin, float montoRecaudado, List<ParticipanteView> participantes)
	{
		setCodigo(codigo);
		setAdmin(idUsuarioAdmin);
		setFechaAgasajo(fechaAgasajo);
		setNombreAgasajado(nombreAgasajado);
		setMontoPorParticipante(monto);
		setFechaInicio(fechaInicio);
		setFechaFin(fechaFin);
		setEstado(estado);
		setMontoRecaudado(montoRecaudado);
		this.participantes = participantes;
	}
	
	public void setAdmin(String idUsuarioAdmin)
	{
		this.idUsuarioAdmin = idUsuarioAdmin;
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
		nombreAgasajado = nombre;
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
	public void setEstado(int estado)
	{
		this.estado = estado;
	}
	public void setMontoRecaudado(float monto)
	{
		this.montoRecaudado = monto;
	}
	public int getCodigo()
	{
		return codigo;
	}
	public String getidUsuarioAdmin()
	{
		return idUsuarioAdmin;
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
	public int getEstado()
	{
		return estado;
	}
	public float getMontoRecaudado()
	{
		return montoRecaudado;
	}
	public List<ParticipanteView> getParticipantes()
	{
		return participantes;
	}
}
