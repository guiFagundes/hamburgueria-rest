function listarCervejas() {
	$.ajax({
		url : host + 'cervejas',
		type : 'GET',
		headers : {
			Accept : 'application/json'
		},
		success : function(data) {

			$('#grid tr:gt(0)').remove();
			if ($.isArray(data.cervejas.link)) {
				for (var i = 0; i < data.cervejas.link.length; i++) {
					var link = data.cervejas.link[i]['@href'];
					segueLinkCerveja(link);
				}
			} else {
				var link = data.cervejas.link['@href'];
				segueLinkCerveja(link);
			}

		},
		error : function(data) {
			alert("Erro na invocaÃ§Ã£o");
		}
	});
}

function segueLinkCerveja(link) {
	$.ajax({
		url : host + link,
		type : 'GET',
		success : function(data) {
			adicionaCervejaNovaAoGrid(data.cerveja);
		},
		error : function(data) {
		}
	});
}

function adicionaCervejaNovaAoGrid(cerveja) {

	var data = "<tr>" + "<td>" + cerveja.nome + "</td>" + "<td>"
			+ cerveja.cervejaria + "</td>" + "<td>" + cerveja.descricao
			+ "</td>" + "<td>" + cerveja.tipo + "</td>"
			+ "<td><input type=\"button\" value=\"Apagar\" "
			+ "class='btn btn-primary' " + "onclick=\"apagaCerveja('"
			+ cerveja.nome + "');\" /></td>" + "</tr>";

	$("#grid").append(data);
}

function adicionaCerveja() {

	var data = $("#criarCervejaForm").serializeJSON();
	data = "{\"cerveja\":" + JSON.stringify(data) + "}";

	$.ajax({
		url : host + 'cervejas',
		type : 'POST',
		contentType : 'application/json',
		data : data,
		success : function(data) {
			alert("IncluÃ­do com sucesso!");
			$("#criarCervejaForm")[0].reset();
			listarCervejas();
		},
		error : function(data) {

			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);

		}
	});
}

function apagaCerveja(id) {
	$.ajax({
		url : host + 'cervejas/' + id,
		type : 'DELETE',
		success : function(data) {
			listarCervejas();
		},
		error : function(data) {
			console.log(data);
			alert("Ocorreu um erro: " + data.status + " " + data.statusText);
		}
	});

}

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
