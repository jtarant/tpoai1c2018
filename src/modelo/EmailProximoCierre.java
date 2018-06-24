package modelo;

import java.text.SimpleDateFormat;

public class EmailProximoCierre extends Email
{
	private ListaRegalos lista;	

	public EmailProximoCierre(String destinatarios, ListaRegalos lista) 
	{
		setDestinatarios(destinatarios);
		setAsunto("AVISO DE CIERRE LISTA - Sistema de Administracion Listas de Regalos");
		this.lista = lista;
	}

	@Override
	public String getTexto() 
	{
		StringBuilder sbBody = new StringBuilder();
		sbBody.append("<h3>Estimado/a</h3>");
		sbBody.append("<br>La lista de regalos para agasajar a <b>");
		sbBody.append(lista.getNombreAgasajado());
		sbBody.append("</b> finaliza el <b>");
		sbBody.append(new SimpleDateFormat("dd/MM/yyyy").format(lista.getFechaFin()));
		sbBody.append("</b> y aun no hiciste tu aporte.<br>Estamos juntando $ <b>");
		sbBody.append(String.format("%.2f", lista.getMontoPorParticipante()));
		sbBody.append("</b> por persona. No te olvides!");
		sbBody.append("<br><br><br>Sistema de Administracion de Listas de Regalos");
		return sbBody.toString();
	}
}
