package br.com.hamburgueriaRest.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.hamburgueriaRest.model.Chope;

@XmlRootElement
public class Chopes {

	private List<Chope> chopes = new ArrayList<>();

	public Chopes() {
	}

	public Chopes(List<Chope> chopes) {
		this.chopes = chopes;
	}

	@XmlTransient
	public List<Chope> getChopes() {
		return chopes;
	}

	public void setChopes(List<Chope> chopes) {
		this.chopes = chopes;
	}
	
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Chope chope : getChopes()) {
			
			Link link = Link.fromPath("chopes/{nome}")
					.rel("chope")
					.title(chope.getNome())
					.build(chope.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link> links) {
		
	}

}
