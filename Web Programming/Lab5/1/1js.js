var xmlhttp;

window.addEventListener('load', getPlecari);

function getPlecari() {

	xmlhttp = getXmlHttpObject();

	var url = "showPlecari.php";
	xmlhttp.onreadystatechange = AddToContainerPlecari;
	xmlhttp.open("GET", url, true);
	xmlhttp.send(null);
}

function getDestinatii() {

	var plecare = document.getElementById("selectPlecare").value;
	xmlhttp = getXmlHttpObject();

	var url = "showDestinatii.php";
	url +="?plecare=";
	url +=plecare;
	xmlhttp.onreadystatechange = AddToContainerDestinatii;

	xmlhttp.open("GET", url, true);
	xmlhttp.send(null);

}


function getXmlHttpObject() {

	if(window.XMLHttpRequest) {
	  return new XMLHttpRequest();
	}
	return null;
}

function AddToContainerPlecari() {

	if(xmlhttp.readyState == 4 && xmlhttp.status ==200) {
		document.getElementById("ContainerPlecare").innerHTML = xmlhttp.responseText;
		var select = document.getElementById("selectPlecare");
		select.addEventListener("click", getDestinatii);
	}

}

function AddToContainerDestinatii() {

	if(xmlhttp.readyState == 4 && xmlhttp.status ==200) {
		document.getElementById("ContainerDestinatii").innerHTML = xmlhttp.responseText;
	}
}