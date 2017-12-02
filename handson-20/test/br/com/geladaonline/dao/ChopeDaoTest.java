package br.com.geladaonline.dao;

import org.junit.Assert;

import org.junit.Test;

import br.com.hamburgueriaRest.dao.ChopeDao;
import br.com.hamburgueriaRest.dao.JpaDaoFactory;
import br.com.hamburgueriaRest.model.Chope;

public class ChopeDaoTest {

	private ChopeDao chopeDao = JpaDaoFactory.getInstance().getChopeDao();
	
	@Test
	public void testSave(){
		Chope primeiraChope = new Chope("xxx", "yyy",
				Chope.Tipo.WEIZEN);
		Chope segundaChope = new Chope("Erdinger Weissbier", "Chope de trigo alemã",
				Chope.Tipo.WEIZEN); 
		chopeDao.salva(primeiraChope);
		chopeDao.salva(segundaChope);
		
		Assert.assertNotNull(primeiraChope.getId());
		Assert.assertNotNull(segundaChope.getId());

	}
}
