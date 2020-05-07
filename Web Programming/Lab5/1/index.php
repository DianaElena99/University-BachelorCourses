<?php
include 'dbh.php';
?>

<!DOCTYPE html>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
/*
$(document).ready(function(){
    $("option").click(function(){
        var start = $(this).value();
        $("#sosiri").load("showSosiri.php", {
            plecare:start
         });
    });
});

*/


function loadSosiri(start){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function(){
        if (this.readyState == 4 && this.status == 200){
            document.getElementById("sosiri").innerHTML = this.responseText;
        }
    }
    xmlhttp.open("GET", "showSosiri.php?q=" + start , true);
    xmlhttp.send();
}


  </script>

<body>
  <select size=10 id="selectPlecare">
  <?php
      $sql = "SELECT distinct plecare FROM trenuri";
      $result = mysqli_query($conn, $sql);
      if(mysqli_num_rows($result) > 0){
          while ($row = mysqli_fetch_assoc($result)) {
            echo "<option onclick=loadSosiri(\"" .$row["plecare"] . "\")>" . $row["plecare"] . "</option>"; 
          }
      }
      else{
        echo "no trains so far";
      }
      ?>
  </select>

  <ul id="sosiri" style="list-style-type:none;">
  </ul>

</body>
</html>