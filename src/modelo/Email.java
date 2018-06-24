package modelo;

public abstract class Email 
{
	protected String para;
	protected String asunto;
	
	protected void setDestinatarios(String cvnombres)
	{
		this.para = cvnombres;
	}

	protected void setAsunto(String asunto)
	{
		this.asunto = asunto;
	}
	
	public String getDestinatarios()
	{
		if (para.endsWith(",")) para = para.substring(0, para.length()-1);
		return para;
	}
	
	public String getAsunto()
	{
		return asunto;
	}

	public abstract String getTexto();
}
