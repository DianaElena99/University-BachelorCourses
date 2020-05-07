var xmlhttp;
var selectedPapuc;
var hasChanges;

function init(){
    selectedPapuc = false;
    getPapuc();
    $("#saveButton").on("click", updatePapuc);
    $("#pretPapuc").change(enableSave);
    $("#materialPapuc").change(enableSave);
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

function detaliiPapuc(){
    
}

function getPapuc(){
    xmlhttp = getXmlHttpObject();
    var url = "listaPapuci.php";
    xmlhttp.onreadystatechange = AddToPapucList;
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);
}

function getXmlHttpObject(){
    if (window.XMLHttpRequest){
        return new XMLHttpRequest;
    }
    return null;
}

function updatePapuc(){

}