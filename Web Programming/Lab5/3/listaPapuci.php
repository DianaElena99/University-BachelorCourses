<?php

    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "web_programming";

    $Connection = new mysqli($servername, $username, $password, $dbname);

	if($Connection->connect_error) {
		die("Connection failes: " . $Connection->connect_error);
	}


    $cmd = "SELECT id from papuci";

    $stmt = $Connection->prepare($cmd);
	$stmt->execute();
    $stmt->bind_result($idPapuc);
    
    echo '<ol style=\'list-style-type:none;\' id =\'papucList\'>';

    while ($stmt->fetch()) {
        echo "id papuc : <li>" . $idPapuc . "</li>";
        echo "<hr>";
	}

    echo "</ol>";
    
    $stmt->close();
?>