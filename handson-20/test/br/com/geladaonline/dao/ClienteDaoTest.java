package br.com.geladaonline.dao;

import org.junit.Assert;

import org.junit.Test;

import br.com.hamburgueriaRest.dao.ClienteDao;
import br.com.hamburgueriaRest.dao.JpaDaoFactory;
import br.com.hamburgueriaRest.model.Cliente;

public class ClienteDaoTest {

	private ClienteDao clienteDao = JpaDaoFactory.getInstance().getClienteDao();
	
	@Test
	public void testSave(){
		Cliente primeiraCliente = new Cliente("Fulano de Tal", "3372-3042");
		Cliente segundaCliente = new Cliente("Ciclano de Tal", "3376-3849"); 
		clienteDao.salva(segundaCliente);
		clienteDao.salva(primeiraCliente);
		clienteDao.salva(segundaCliente);
		
		Assert.assertNotNull(primeiraCliente.getId());
		Assert.assertNotNull(segundaCliente.getId());

	}
}
