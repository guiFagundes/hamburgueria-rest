package br.com.geladaonline.dao;

import org.junit.Assert;
import org.junit.Test;

import br.com.hamburgueriaRest.dao.HamburguerDao;
import br.com.hamburgueriaRest.dao.JpaDaoFactory;
import br.com.hamburgueriaRest.model.Hamburguer;

public class HamburguerDaoTest {
	private HamburguerDao hamburguerDao = JpaDaoFactory.getInstance().getHamburguerDao();
	
	@Test
	public void testSave(){
		Hamburguer aa = new Hamburguer();
		aa.setDescricao("x-Piranha");
		aa.setPreco(21.0);
		aa.setDescricao("bom");
		Hamburguer bb = new Hamburguer();
		bb.setDescricao("x-tudo");
		bb.setPreco(11.0);
		bb.setDescricao("barato");
		hamburguerDao.salva(aa);
		hamburguerDao.salva(bb);
		
		Assert.assertNotNull(aa.getId());
		Assert.assertNotNull(bb.getId());

	}
}
