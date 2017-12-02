package br.com.hamburgueriaRest.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.hamburgueriaRest.model.Hamburguer;

@XmlRootElement
public class Hamburguers {

	private List<Hamburguer> hamburguers = new ArrayList<>();

	public Hamburguers() {
	}

	public Hamburguers(List<Hamburguer> hamburguers) {
		this.hamburguers = hamburguers;
	}

	@XmlTransient
	public List<Hamburguer> getHamburguers() {
		return hamburguers;
	}

	public void setHamburguers(List<Hamburguer> hamburguers) {
		this.hamburguers = hamburguers;
	}
	
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Hamburguer hamburguer : getHamburguers()) {
			
			Link link = Link.fromPath("hamburguers/{nome}")
					.rel("hamburguer")
					.title(hamburguer.getNome())
					.build(hamburguer.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link> links) {
		
	}

}
