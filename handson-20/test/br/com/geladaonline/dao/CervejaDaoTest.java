package br.com.geladaonline.dao;

import org.junit.Assert;

import org.junit.Test;

import br.com.hamburgueriaRest.dao.CervejaDao;
import br.com.hamburgueriaRest.dao.JpaDaoFactory;
import br.com.hamburgueriaRest.model.Cerveja;

public class CervejaDaoTest {

	private CervejaDao cervejaDao = JpaDaoFactory.getInstance().getCervejaDao();
	
	@Test
	public void testSave(){
		Cerveja primeiraCerveja = new Cerveja("Stella Artois",
				"A cerveja belga mais francesa do mundo :)", "Artois",
				Cerveja.Tipo.LAGER);
		Cerveja segundaCerveja = new Cerveja("Erdinger Weissbier", "Cerveja de trigo alem„", "Erdinger Weissbr√§u",
				Cerveja.Tipo.WEIZEN); 
		cervejaDao.salva(primeiraCerveja);
		cervejaDao.salva(segundaCerveja);
		
		Assert.assertNotNull(primeiraCerveja.getId());
		Assert.assertNotNull(segundaCerveja.getId());

	}
}
