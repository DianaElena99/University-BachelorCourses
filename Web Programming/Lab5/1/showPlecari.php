<?php
	
	include "dbh.php";

	//Create Connection
	$Connection = new mysqli($servername, $username, $password, $dbname);

	//Check Connection
	if($Connection->connect_error) {
		die("Connection failes: " . $Connection->connect_error);
	}

	//SQL command
	$cmd = "SELECT distinct plecare
			FROM trenuri";

	//Execute stmt
	$stmt = $Connection->prepare($cmd);
	$stmt->execute();
	$stmt->bind_result($plecare);

	//Build select
	echo "<select size=10 name=Plecare id=\"selectPlecare\">";

	//Build options
	while ($stmt->fetch()) {
		echo "<option " . "value=" . $plecare . ">" . $plecare . "</option>";
	}

	echo "</select>";
	
	//Close conn and stmt
	$stmt->close();
	//$Connection.close();
?>