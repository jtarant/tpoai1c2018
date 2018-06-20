package modelo;

import java.util.Date;

public class PagoExterno 
{
	private int codigoLista;
	private String idUsuario;
	private Date fechaPago;
	
	public PagoExterno(int codigoLista, String idUsuario, Date fechaPago)
	{
		this.codigoLista = codigoLista;
		this.idUsuario = idUsuario;
		this.fechaPago = fechaPago;
	}
	
	public int getCodigoLista()
	{
		return this.codigoLista;
	}
	public String getIdUsuario()
	{
		return this.idUsuario;
	}
	public Date getFechaPago()
	{
		return this.fechaPago;
	}
}
