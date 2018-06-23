package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NotificadorEmail implements Observer
{
	private static NotificadorEmail instancia;
	private String de;
	private String smtpServer;
	private String smtpUsuario;
	private String smtpPassword;
	private Email email;

	private NotificadorEmail()
	{
	}
	
	public static NotificadorEmail getInstancia()
	{
		if (instancia == null)
			instancia = new NotificadorEmail();
		return instancia;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		email = null;
		ArrayList<String> destinatarios = new ArrayList<String>();
		
		try
		{
			// El primer argumento es la lista que estoy observando
			ListaRegalos lista = (ListaRegalos)arg0;

			switch (lista.getEstado()) 
			{
				case ABIERTA:
				{
					// En el parametro arg1, tengo solo los nuevos participantes que se agregaron
					if (arg1 != null)
					{
						@SuppressWarnings("unchecked")
						List<Participante> participantesANotificar = ((List<Participante>)arg1);
						participantesANotificar.forEach(p -> destinatarios.add(p.getUsuario().getEmail()));
						
						if (!destinatarios.isEmpty())
							email = new EmailInicioLista(destinatarios, lista.getNombreAgasajado(), lista.getMontoPorParticipante());						
					}					
					break;				
				}
				case PROXIMO_CIERRE:
				{
					// Obtengo de la lista solo los participantes que no pagaron
					for (Participante p : lista.getParticipantes())
					{
						if (!p.getPagoRealizado()) destinatarios.add(p.getUsuario().getEmail());
					}
					if (!destinatarios.isEmpty())
						email = new EmailProximoCierre(destinatarios, lista.getNombreAgasajado(), lista.getMontoPorParticipante());
					break;
				}
				case CERRADA:
				{
					email = new EmailAvisoRegalo(lista.getAdmin().getEmail(), lista.getNombreAgasajado(), lista.getMontoRecaudado());
					break;
				}				
				default:
					break;
			}
			
			if (email != null)
			{
				this.enviar();
			}
		}
		catch (Exception e)
		{
			// Si falla la notificacion, no tiene que fallar la aplicacion, asi que no re-lanzo la excepcion
			System.out.println("Error al notificar: " + e.getMessage());
		}
	}
	
	private void enviar()
	{
		System.out.println(email.getTexto());
	}
}
