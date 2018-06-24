package modelo;

public class EmailInicioLista extends Email
{
	private ListaRegalos lista;	

	public EmailInicioLista(String destinatarios, ListaRegalos lista) 
	{
		this.lista = lista;
		setDestinatarios(destinatarios);
		setAsunto("NUEVA INVITACION - Sistema de Administracion Listas de Regalos");
	}

	@Override
	public String getTexto() 
	{
		StringBuilder sbBody = new StringBuilder();
		sbBody.append("<h3>Estimado/a</h3>");
		sbBody.append("<br>A partir de hoy estas participando de la lista de regalos creada por ");
		sbBody.append(lista.getAdmin().getApellido());
		sbBody.append(", ");
		sbBody.append(lista.getAdmin().getNombre());
		sbBody.append(" para agasajar a <b>");
		sbBody.append(lista.getNombreAgasajado());
		sbBody.append("</b><br><br>Estamos juntando $: <b>");
		sbBody.append(String.format("%.2f", lista.getMontoPorParticipante()));
		sbBody.append("</b> por persona. Gracias!");
		sbBody.append("<br><br><br>Sistema de Administracion de Listas de Regalos");
		return sbBody.toString();
	}
}
