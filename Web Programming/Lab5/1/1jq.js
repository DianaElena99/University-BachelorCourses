$(document).ready(Initialize);

function Initialize() {

	getPlecari();
	$("#table").on('click', 'tr', setInputs);
}

function getPlecari() {

	$.ajax({
		type: "GET",
		url: "showPlecari.php",
		success: function(data,status) {
			$("#ContainerPlecare").html(data);
			$("#ContainerPlecare").on("click","select",getDestinatii);
		}
	})
}

function getDestinatii() {

	var plecare = $(this).val();
	var url = "showDestinatii.php" + "?plecare=" + plecare;

	$.ajax({
		type: "GET",
		url: url,
		success: function(data,status) {
			$("#ContainerDestinatii").html(data);
		}
	})
}

function setInputs() {
	alert(this);
}