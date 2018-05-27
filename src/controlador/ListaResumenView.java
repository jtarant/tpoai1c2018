package controlador;

import java.util.Date;

public class ListaResumenView {
	private int codigo;
	private String idUsuarioAdmin;
	private Date fechaAgasajo;
	private String nombreAgasajado;
	private float montoPorParticipante;
	private Date fechaInicio;
	private Date fechaFin;
	private int estado;
	
	public ListaResumenView(int codigo, String idUsuarioAdmin, Date fechaAgasajo, String nombreAgasajado, float monto, Date fechaInicio, Date fechaFin, int estado)
	{
		this.codigo = codigo;
		this.idUsuarioAdmin = idUsuarioAdmin;
		this.fechaAgasajo = fechaAgasajo;
		this.nombreAgasajado = nombreAgasajado;
		this.montoPorParticipante = monto;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
	}
	public int getCodigo()
	{
		return codigo;
	}
	public String getAdmin()
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
}
