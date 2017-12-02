function listarClientes() {
	$.ajax({
		url : host + 'clientes',
		type : 'GET',
		headers : {
			Accept : 'application/json'
		},
		success : function(data) {

			$('#grid tr:gt(0)').remove();
			if ($.isArray(data.clientes.link)) {
				for (var i = 0; i < data.clientes.link.length; i++) {
					var link = data.clientes.link[i]['@href'];
					segueLinkCliente(link);
				}
			} else {
				var link = data.clientes.link['@href'];
				segueLinkCliente(link);
			}

		},
		error : function(data) {
			alert("Erro na invocaÃ§Ã£o");
		}
	});
}

function segueLinkCliente(link) {
	$.ajax({
		url : host + link,
		type : 'GET',
		success : function(data) {
			adicionaClienteNovaAoGrid(data.cliente);
		},
		error : function(data) {
			alert("Ocorreu um erro");
		}
	});
}

function adicionaClienteNovaAoGrid(cliente) {

	var data = "<tr>" + "<td>" + cliente.nome + "</td>" + "<td>"
			+ cliente.telefone + "</td>"
			+ "<td><input type=\"button\" value=\"Apagar\" "
			+ "onclick=\"apagaCliente('" + cliente.nome + "');\" /></td>"
			+ "</tr>";

	$("#grid").append(data);
}

function adicionaCliente() {

	var data = $("#criarClienteForm").serializeJSON();
	data = "{\"cliente\":" + JSON.stringify(data) + "}";

	$.ajax({
		url : host + 'clientes',
		type : 'POST',
		contentType : 'application/json',
		data : data,
		success : function(data) {
			alert("IncluÃ­do com sucesso!");
			$("#criarClienteForm")[0].reset();
			listarClientes();
		},
		error : function(data) {

			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);

		}
	});
}

function apagaCliente(id) {
	$.ajax({
		url : host + 'clientes/' + id,
		type : 'DELETE',
		success : function(data) {
			listarClientes();
		},
		error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);
		}
	});

}