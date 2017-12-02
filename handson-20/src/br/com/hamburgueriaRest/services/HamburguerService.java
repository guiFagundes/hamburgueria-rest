package br.com.hamburgueriaRest.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.hamburgueriaRest.dao.HamburguerDao;
import br.com.hamburgueriaRest.dao.JpaDaoFactory;
import br.com.hamburgueriaRest.model.Hamburguer;
import br.com.hamburgueriaRest.model.rest.Hamburguers;

import javax.ws.rs.core.Response.Status;



@Path("/hamburguers")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class HamburguerService {

	private HamburguerDao hamburguerDao = JpaDaoFactory.getInstance().getHamburguerDao();
	
	private static final int TAMANHO_PAGINA = 10;

	@GET
	@Path("{nome}")
	public Hamburguer encontreHamburguer(@PathParam("nome") String nomeDaHamburguer) {
		Hamburguer hamburguer = hamburguerDao.buscaPorNome(nomeDaHamburguer);
		if (hamburguer != null)
			return hamburguer;

		throw new WebApplicationException(Status.NOT_FOUND);

	}

	@GET
	public Hamburguers listeTodasAsHamburguers(@QueryParam("pagina") int pagina) {
		List<Hamburguer> hamburguers = hamburguerDao.listaPaginada(pagina, TAMANHO_PAGINA);
		return new Hamburguers(hamburguers);
	}

	@POST
	public Response criarHamburguer(Hamburguer hamburguer) {
		
		try {
			hamburguerDao.salva(hamburguer);
		} catch (Exception e) {
			throw new WebApplicationException(Status.CONFLICT);
		}

		URI uri = UriBuilder.fromPath("hamburguers/{nome}").build(
				hamburguer.getNome());

		return Response.created(uri).entity(hamburguer).build();
	}

	@PUT
	@Path("{nome}")
	public void atualizarHamburguer(@PathParam("nome") String nome, Hamburguer hamburguer) {
		encontreHamburguer(nome);
		hamburguer.setNome(nome);
		hamburguerDao.atualiza(hamburguer);
	}

	@DELETE
	@Path("{nome}")
	public void apagarHamburguer(@PathParam("nome") String nome) {
		hamburguerDao.remove(nome);
	}

	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperaImagem(@PathParam("nome") String nomeDaHamburguer)
			throws IOException {
		InputStream is = HamburguerService.class.getResourceAsStream("/"
				+ nomeDaHamburguer + ".jpg");

		if (is == null)
			throw new WebApplicationException(Status.NOT_FOUND);

		byte[] dados = new byte[is.available()];
		is.read(dados);
		is.close();

		return Response.ok(dados).type("image/jpg").build();
	}

	private static Map<String, String> EXTENSOES;

	static {
		EXTENSOES = new HashMap<>();
		EXTENSOES.put("image/jpg", ".jpg");
	}

	@POST
	@Path("{nome}")
	@Consumes("image/*")
	public Response criaImagem(@PathParam("nome") String nomeDaImagem,
			@Context HttpServletRequest req, byte[] dados) throws IOException,
			InterruptedException {

		String userHome = System.getProperty("user.home");
		String mimeType = req.getContentType();
		FileOutputStream fos = new FileOutputStream(userHome
				+ java.io.File.separator + nomeDaImagem
				+ EXTENSOES.get(mimeType));

		fos.write(dados);
		fos.flush();
		fos.close();

		return Response.ok().build();
	}
	
}
