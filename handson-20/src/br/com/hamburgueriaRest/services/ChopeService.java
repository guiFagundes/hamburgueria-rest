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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import br.com.hamburgueriaRest.dao.ChopeDao;
import br.com.hamburgueriaRest.dao.JpaDaoFactory;
import br.com.hamburgueriaRest.model.Chope;

import br.com.hamburgueriaRest.model.rest.Chopes;

@Path("/chopes")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class ChopeService {

	private ChopeDao chopeDao = JpaDaoFactory.getInstance().getChopeDao();

	private static final int TAMANHO_PAGINA = 10;

	@GET
	@Path("{nome}")
	public Chope encontreChope(@PathParam("nome") String nomeDaChope) {
		Chope chope = chopeDao.buscaPorNome(nomeDaChope);
		if (chope != null)
			return chope;

		throw new WebApplicationException(Status.NOT_FOUND);

	}

	@GET
	public Chopes listeTodasAsChopes(@QueryParam("pagina") int pagina) {
		List<Chope> chopes = chopeDao.listaPaginada(pagina, TAMANHO_PAGINA);
		return new Chopes(chopes);
	}

	@POST
	public Response criarChope(Chope chope) {
		
		try {
			chopeDao.salva(chope);
		} catch (Exception e) {
			throw new WebApplicationException(Status.CONFLICT);
		}

		URI uri = UriBuilder.fromPath("chopes/{nome}").build(
				chope.getNome());

		return Response.created(uri).entity(chope).build();
	}

	@PUT
	@Path("{nome}")
	public void atualizarChope(@PathParam("nome") String nome, Chope chope) {
		encontreChope(nome);
		chope.setNome(nome);
		chopeDao.atualiza(chope);
	}

	@DELETE
	@Path("{nome}")
	public void apagarChope(@PathParam("nome") String nome) {
		chopeDao.remove(nome);
	}

	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperaImagem(@PathParam("nome") String nomeDaChope)
			throws IOException {
		InputStream is = ChopeService.class.getResourceAsStream("/"
				+ nomeDaChope + ".jpg");

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
