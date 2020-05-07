var selectedPapuc;
var hasChanges;
$(document).ready(init);

function init(){
    selectedPapuc = false;
    getPapuc();
    $("#saveButton").on('click', updatePapuc);
    $("#pretPapuc").change(enableSave);
    $("#numePapuc").change(enableSave);
}

function enableSave(){
    if (!selectedPapuc){
        $("#saveButton").attr("disabled", true);
    }else{
        $("#saveButton").attr("disabled", false);
        hasChanges = true;
    }
}

function getPapuc(){
    $.ajax({
        type: "GET",
        url : "listaPapuci.php",
        success : function(data, status){
            $('#listContainer').html(data);
            $('#papucList').on("click", "li", getDetalii);
        }
    })
}

function getDetalii(){
    if (hasChanges){
        if (confirm("Ati modificat o pereche de papuci, doriti sa o si salvati?"))
            updatePapuc();
    }

    selectedPapuc = true;
    hasChanges = false;
    $("#saveButton").attr("disabled", true);
    var papucID = $(this).html();
    console.log(papucID);
    var url = "detaliiPapuci.php?id=";
        url += papucID;

    $.ajax({
        type: "GET",
        url : url,
        success : function(data, status){
            console.log(data);
            var obj = JSON.parse(data);
            $("#numePapuc").val(obj.nume);
            $("#idPapuc").val(papucID);
            $("#materialPapuc").val(obj.material);
            $("#pretPapuc").val(obj.pret);
        }
    })
}

function validateInput(){
    var err = "";
    if ($("#numePapuc").val()==""){
        err+="Nume invalid -- nu poate fi vid\n";
    }
    if(isNaN($("#pretPapuc").val())){
        err+="Pretul trebuie sa fie un numar\n";
    }
    if($("#materialPapuc").val()==""){
        err +="Materialul nu poate fi string vid\n";
    }
    return err;
}

function updatePapuc(){
    var err = validateInput();
    if (err!=""){
        alert(err);
        return;
    }

    var url = "updatePapuci.php";
    var id = $("#idPapuc").val();
    var nume = $("#numePapuc").val();
    var pret = $("#pretPapuc").val();
    var material = $("#materialPapuc").val();
    var data = "id="+ id+ "&nume=" + nume + "&material=" + material + "&pret=" + pret;
    console.log(data);
    $.ajax({
        type : "POST",
        url  : url,
        data : {
            nume: nume,
            material : material,
            pret : pret,
            id : id
        },
        success : function(response){
            hasChanges = false;
            alert(response);
        }
    })
}