package modelo;

import java.util.List;

import controlador.AdminListaRegalos;
import controlador.AdminUsuarios;
import controlador.ListaResumenView;

public class FachadaMobile 
{
	public Boolean autenticar(String idUsuario, String password) throws Exception
	{
		return AdminUsuarios.getInstancia().autenticar(idUsuario, password);
	}
	
	public List<ListaResumenView> listarMisListas(String idUsuario) throws Exception
	{
		return AdminListaRegalos.getInstancia().listarMisListas(idUsuario);
	}
}
