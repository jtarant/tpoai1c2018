package modelo;

public class EmailAvisoRegalo extends Email
{
	private ListaRegalos lista;

	public EmailAvisoRegalo(String destinatario, ListaRegalos lista) 
	{
		this.lista = lista;
		setAsunto("AVISO DE REGALO - Sistema de Administracion Listas de Regalos");
		setDestinatarios(destinatario);
	}

	@Override
	public String getTexto() 
	{
		StringBuilder sbBody = new StringBuilder();
		sbBody.append("<h3>Estimado/a ");
		sbBody.append(lista.getAdmin().getNombre());
		sbBody.append(":</h3><br>El dinero para agasajar a <b>");
		sbBody.append(lista.getNombreAgasajado());
		sbBody.append("</b> esta listo.<br><br>Se recaudo un total de $ <b>");
		sbBody.append(String.format("%.2f", lista.getMontoRecaudado()));
		sbBody.append("</b>, esperamos que lo disfrute.<br><br>Participaron del regalo:<br>");
		for (Participante p: lista.getParticipantes())
		{
			if (p.getPagoRealizado())
			{
				sbBody.append(p.getUsuario().getApellido());
				sbBody.append(", ");
				sbBody.append(p.getUsuario().getNombre());
				sbBody.append("<br>");
			}
		}
		sbBody.append("<br><br><br>Sistema de Administracion de Listas de Regalos");
		return sbBody.toString();
	}
}
