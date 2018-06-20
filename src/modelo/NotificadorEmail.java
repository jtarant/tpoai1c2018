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
			// El primer argumento es la lista que estoy observando, el segundo son los participantes a notificar
			ListaRegalos lista = (ListaRegalos)arg0;
			if (arg1 != null)
			{
				@SuppressWarnings("unchecked")
				List<Participante> participantesANotificar = ((List<Participante>)arg1);
				participantesANotificar.forEach(p -> destinatarios.add(p.getUsuario().getEmail()));
			}
			switch (lista.getEstado()) 
			{
				case ABIERTA:
				{
					if (!destinatarios.isEmpty())
						email = new EmailInicioLista(destinatarios, lista.getNombreAgasajado(), lista.getMontoPorParticipante());
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
