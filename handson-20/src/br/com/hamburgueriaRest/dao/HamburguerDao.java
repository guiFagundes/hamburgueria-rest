package br.com.hamburgueriaRest.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.hamburgueriaRest.model.Hamburguer;

public class HamburguerDao extends JpaDaoBase<Hamburguer> implements IDao<Hamburguer>{

	public Hamburguer buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Hamburguer.buscaPorNome").setParameter("nome", nome);
		List<Hamburguer> hamburguers = query.getResultList();
		if (!hamburguers.isEmpty())
			return hamburguers.get(0);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Hamburguer c WHERE c.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
	
}
