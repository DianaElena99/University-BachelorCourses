<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "web_programming";

$Connection = new mysqli($servername, $username, $password, $dbname);

if($Connection->connect_error) {
    die("Connection failes: " . $Connection->connect_error);
}

    $_SERVER['CONTENT_TYPE'] = "application/x-www-form-urlencoded";
    
    $id = $_POST["id"];
    $nume = $_POST["nume"];
    $material = $_POST["material"];
    $pret = $_POST["pret"];
    

    $cmd = "UPDATE papuci 
    SET nume = ? , material = ? , pret = ? 
    WHERE id = ?";

    $stmt = $Connection->prepare($cmd);
    $stmt->bind_param("ssdi", $nume, $material, $pret, $id);
    $stmt->execute();

    $stmt->close();


?>
