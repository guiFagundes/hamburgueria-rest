package br.com.hamburgueriaRest.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.hamburgueriaRest.model.Cerveja;

public class CervejaDao extends JpaDaoBase<Cerveja> implements IDao<Cerveja> {

	public Cerveja buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Cerveja.buscaPorNome").setParameter("nome", nome);
		List<Cerveja> cervejas = query.getResultList();
		if (!cervejas.isEmpty())
			return cervejas.get(0);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Cerveja c WHERE c.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
	
	
}
