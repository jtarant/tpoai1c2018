package modelo;

import java.util.List;

public abstract class Email 
{
	protected List<String> para;
	protected String asunto;
	
	public abstract String getTexto();
}
