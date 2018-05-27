package modelo;

public enum EstadoListaRegalos { 
	ABIERTA(0), CERRADA(1), PROXIMO_CIERRE(2); 
	
	private final int valor;
	
	private EstadoListaRegalos(int valor)
	{
		this.valor = valor;
	}
	public int getValor()
	{
		return valor;
	}
}
