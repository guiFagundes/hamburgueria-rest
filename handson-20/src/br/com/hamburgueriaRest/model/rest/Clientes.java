package br.com.hamburgueriaRest.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.hamburgueriaRest.model.Cliente;

@XmlRootElement
public class Clientes {

	private List<Cliente> clientes = new ArrayList<>();

	public Clientes() {
	}

	public Clientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@XmlTransient
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Cliente cliente : getClientes()) {
			
			Link link = Link.fromPath("clientes/{nome}")
					.rel("cliente")
					.title(cliente.getNome())
					.build(cliente.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link> links) {
		
	}
	
}
