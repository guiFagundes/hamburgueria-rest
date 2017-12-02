package br.com.hamburgueriaRest.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.hamburgueriaRest.model.Chope;

public class ChopeDao extends JpaDaoBase<Chope> implements IDao<Chope> {

	public Chope buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Chope.buscaPorNome").setParameter("nome", nome);
		List<Chope> chopes = query.getResultList();
		if (!chopes.isEmpty())
			return chopes.get(0);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Chope c WHERE c.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
	
	
}
