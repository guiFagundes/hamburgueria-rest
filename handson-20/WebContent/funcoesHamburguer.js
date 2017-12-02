function listarHamburgues() {
	$.ajax({
		url : host + 'hamburguers',
		type : 'GET',
		headers : {
			Accept : 'application/json'
		},
		success : function(data) {

			$('#grid tr:gt(0)').remove();
			if ($.isArray(data.hamburguers.link)) {
				for (var i = 0; i < data.hamburguers.link.length; i++) {
					var link = data.hamburguers.link[i]['@href'];
					segueLinkHamburguer(link);
				}
			} else {
				var link = data.hamburguers.link['@href'];
				segueLinkHamburguer(link);
			}

		},
		error : function(data) {
			alert("Erro na invocaÃ§Ã£o");
		}
	});
}

function segueLinkHamburguer(link) {
	$.ajax({
		url : host + link,
		type : 'GET',
		success : function(data) {
			adicionaHamburguerrNovaAoGrid(data.hamburguer);
		},
		error : function(data) {
			alert("Ocorreu um erro");
		}
	});
}

function adicionaHamburguerrNovaAoGrid(hamburguer) {

	var data = "<tr>" + "<td>" + hamburguer.nome + "</td>" + "<td>"
			+ hamburguer.descricao + "</td>" + "<td>" + hamburguer.preco
			+ "</td>" + "<td><input type=\"button\" value=\"Apagar\" "
			+ "class='btn btn-primary' " + "onclick=\"apagaHamburguer('"
			+ hamburguer.nome + "');\" /></td>" + "</tr>";

	$("#grid").append(data);
}

function adicionaHamburguer() {

	var data = $("#criarHamburguerForm").serializeJSON();
	data = "{\"hamburguer\":" + JSON.stringify(data) + "}";

	$.ajax({
		url : host + 'hamburguers',
		type : 'POST',
		contentType : 'application/json',
		data : data,
		success : function(data) {
			alert("IncluÃ­do com sucesso!");
			$("#criarHamburguerForm")[0].reset();
			listarHamburgues();
		},
		error : function(data) {

			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);

		}
	});
}

function apagaHamburguer(id) {
	$.ajax({
		url : host + 'hamburguers/' + id,
		type : 'DELETE',
		success : function(data) {
			listarHamburgues();
		},
		error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);
		}
	});

}




