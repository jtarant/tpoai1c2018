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
	public static EstadoListaRegalos fromInt(int valor)
	{
		for (EstadoListaRegalos tipo: values())
		{
			if (tipo.getValor() == valor) return tipo;
		}
		return null;
	}
}
