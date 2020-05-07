$(document).ready(init);

function init(){
    getSelects();
    getNotebooks();
}

function getSelects(){
    $.ajax({
        type:"GET",
        url : "showSelects.php",
        success : function(data, status){
            $("#ContainerSelects").html(data);
        }
    });
}


function getNotebooks(){
    $.ajax({
        type: "GET",
        url : "showNotebooks.php",
        success : function(data, status){
            $("#ContainerNotebooks").html(data);
        }
    });
}

function filterNotebooks(){
    var producator = $("#producator").val();
    console.log(producator);
    var procesor = $("#procesor").val();
    console.log(procesor);
    var placaVideo = $("#placaVideo").val();
    console.log(placaVideo);
    var hdd = $("#hdd").val();
    console.log(hdd);

    $.ajax({
        type: "GET",
        url : "showDetails.php",
        data : {
            producator : producator,
            procesor : procesor,
            placaVideo : placaVideo,
            hdd : hdd
        },
        success : function(data, status){
            $("#ContainerNotebooks").html(data);
        }
    });
}