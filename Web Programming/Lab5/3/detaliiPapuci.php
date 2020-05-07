<?php

    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "web_programming";

    $Connection = new mysqli($servername, $username, $password, $dbname);

    $idPapuc = $_GET["id"];

	if($Connection->connect_error) {
		die("Connection failes: " . $Connection->connect_error);
	}


    $cmd = "SELECT nume, material, pret FROM papuci where id = ?";

    $stmt = $Connection->prepare($cmd);
    $stmt->bind_param("s", $idPapuc);
    $stmt->execute();
    $stmt->bind_result($col1, $col2, $col3);
    
    $stmt->fetch();
    $details = new StdClass();
    $details->nume = $col1;
    $details->material = $col2;
    $details->pret = $col3;
    $details->id = $idPapuc;

    echo json_encode($details);
    
    $stmt->close();
?>