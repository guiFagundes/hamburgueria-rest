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

import br.com.hamburgueriaRest.dao.ClienteDao;
import br.com.hamburgueriaRest.dao.JpaDaoFactory;
import br.com.hamburgueriaRest.model.Cliente;
import br.com.hamburgueriaRest.model.rest.Clientes;

@Path("/clientes")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class ClienteService {

	private ClienteDao clienteDao = JpaDaoFactory.getInstance().getClienteDao();

	private static final int TAMANHO_PAGINA = 10;

	@GET
	@Path("{nome}")
	public Cliente encontreCliente(@PathParam("nome") String nomeDaCliente) {
		Cliente cliente = clienteDao.buscaPorNome(nomeDaCliente);
		if (cliente != null)
			return cliente;

		throw new WebApplicationException(Status.NOT_FOUND);

	}

	@GET
	public Clientes listeTodasAsClientes(@QueryParam("pagina") int pagina) {
		List<Cliente> clientes = clienteDao.listaPaginada(pagina, TAMANHO_PAGINA);
		return new Clientes(clientes);
	}

	@POST
	public Response criarCliente(Cliente cliente) {
		
		try {
			clienteDao.salva(cliente);
		} catch (Exception e) {
			throw new WebApplicationException(Status.CONFLICT);
		}

		URI uri = UriBuilder.fromPath("clientes/{nome}").build(
				cliente.getNome());

		return Response.created(uri).entity(cliente).build();
	}

	@PUT
	@Path("{nome}")
	public void atualizarCliente(@PathParam("nome") String nome, Cliente cliente) {
		encontreCliente(nome);
		cliente.setNome(nome);
		clienteDao.atualiza(cliente);
	}

	@DELETE
	@Path("{nome}")
	public void apagarCliente(@PathParam("nome") String nome) {
		clienteDao.remove(nome);
	}

	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperaImagem(@PathParam("nome") String nomeDaCliente)
			throws IOException {
		InputStream is = ClienteService.class.getResourceAsStream("/"
				+ nomeDaCliente + ".jpg");

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
