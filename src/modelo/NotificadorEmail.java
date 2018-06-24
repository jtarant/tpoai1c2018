package modelo;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificadorEmail implements Observer
{
	private static NotificadorEmail instancia;
	private String de;
	private String smtpUsuario;
	private String smtpPassword;
	private Email email;
	private Properties props;
	private Session session;

	private NotificadorEmail()
	{
		try
		{
			props = new Properties();
			props.load(getClass().getClassLoader().getResourceAsStream("ConfigEmail.txt"));
			props.put("mail.smtp.ssl.trust", props.getProperty("mail.smtp.host"));
			smtpUsuario = props.getProperty("smtpUsuario");
			smtpPassword = props.getProperty("smtpPassword");
			de = props.getProperty("de");
			session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(smtpUsuario, smtpPassword);
						}
					  });
		}
		catch (Exception e)
		{
			System.out.println("Error al inicializar la configuracion de emails: " + e.getMessage());
			session = null;
		}
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
		// El primer argumento es la lista que estoy observando
		ListaRegalos lista = (ListaRegalos)arg0;
		email = null;
		StringBuilder destinatarios = new StringBuilder();
		
		try
		{
			switch (lista.getEstado()) 
			{
				case ABIERTA:
				{
					// En el parametro arg1, tengo solo los nuevos participantes que se agregaron, notifico solo a ellos
					if (arg1 != null)
					{
						@SuppressWarnings("unchecked")
						List<Participante> notificarA = ((List<Participante>)arg1);
						if (!notificarA.isEmpty())
						{
							notificarA.forEach(participante -> destinatarios.append(participante.getUsuario().getEmail()+","));
							email = new EmailInicioLista(destinatarios.toString(), lista);
						}
					}		
					break;				
				}
				case PROXIMO_CIERRE:
				{
					// Obtengo de la lista solo los participantes que no pagaron, y notifico solo a ellos
					Boolean faltaPagar = false;
					for (Participante p : lista.getParticipantes())
					{
						if (!p.getPagoRealizado())
						{
							destinatarios.append(p.getUsuario().getEmail()+",");
							faltaPagar = true;
						}
					}
					if (faltaPagar) email = new EmailProximoCierre(destinatarios.toString(), lista);
					break;
				}
				case CERRADA:
				{
					email = new EmailAvisoRegalo(lista.getAdmin().getEmail(), lista);
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
			System.out.println("Error al enviar email: " + e.getMessage());
		}
	}
	
	private void enviar() throws Exception
	{
		String texto = email.getTexto();
		String destinatarios = email.getDestinatarios();
		
		System.out.println("Enviando mail a: " + destinatarios);
		System.out.println(texto);

		if (session != null)
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(de));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarios));
			message.setSubject(email.getAsunto());
			message.setContent(texto, "text/html; charset=utf-8");
			Transport.send(message);
			System.out.println("enviado correctamente.");
		}
	}
}
