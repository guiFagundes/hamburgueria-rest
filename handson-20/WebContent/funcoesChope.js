function listarChopes() {
	$.ajax({
		url : host + 'chopes',
		type : 'GET',
		headers : {
			Accept : 'application/json'
		},
		success : function(data) {

			$('#grid tr:gt(0)').remove();
			if ($.isArray(data.chopes.link)) {
				for (var i = 0; i < data.chopes.link.length; i++) {
					var link = data.chopes.link[i]['@href'];
					segueLinkChope(link);
				}
			} else {
				var link = data.chopes.link['@href'];
				segueLinkChope(link);
			}

		},
		error : function(data) {
			alert("Erro na invocaÃ§Ã£o");
		}
	});
}

function segueLinkChope(link) {
	$.ajax({
		url : host + link,
		type : 'GET',
		success : function(data) {
			adicionaChopeNovaAoGrid(data.chope);
		},
		error : function(data) {
		}
	});
}

function adicionaChopeNovaAoGrid(chope) {

	var data = "<tr>" + "<td>" + chope.nome + "<td>" + chope.quantidade
			+ "</td>" + "<td>" + chope.tipo + "</td>"
			+ "<td><input type=\"button\" value=\"Apagar\" "
			+ "class='btn btn-primary' " + "onclick=\"apagaChope('"
			+ chope.nome + "');\" /></td>" + "</tr>";

	$("#grid").append(data);
}

function adicionaChope() {

	var data = $("#criarChopeForm").serializeJSON();
	data = "{\"chope\":" + JSON.stringify(data) + "}";

	$.ajax({
		url : host + 'chopes',
		type : 'POST',
		contentType : 'application/json',
		data : data,
		success : function(data) {
			alert("IncluÃ­do com sucesso!");
			$("#criarChopeForm")[0].reset();
			listarChopes();
		},
		error : function(data) {

			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);

		}
	});
}

function apagaChope(id) {
	$.ajax({
		url : host + 'chopes/' + id,
		type : 'DELETE',
		success : function(data) {
			listarChopes();
		},
		error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);
		}
	});

}
