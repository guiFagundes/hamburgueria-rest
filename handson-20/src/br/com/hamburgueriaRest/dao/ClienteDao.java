package br.com.hamburgueriaRest.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.hamburgueriaRest.model.Cliente;

public class ClienteDao extends JpaDaoBase<Cliente> implements IDao<Cliente> {

	public Cliente buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Cliente.buscaPorNome").setParameter("nome", nome);
		List<Cliente> clientes = query.getResultList();
		if (!clientes.isEmpty())
			return clientes.get(0);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Cliente c WHERE c.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
	
	
}
