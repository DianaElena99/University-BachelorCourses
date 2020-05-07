<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "web_programming";

//Create Connection
$conn = new mysqli($servername, $username, $password, $dbname);

$q = $_GET["plecare"];

$cmd = 'SELECT sosire from trenuri where plecare = ?';
$stmt = $conn->prepare($cmd);

$stmt->bind_param("s",$q);
$stmt->execute();
$stmt->bind_result($destinatie);

while ($stmt->fetch()) {
    echo "<li>" . $destinatie . "</li>";
}

$stmt->close();
?>

