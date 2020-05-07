$(document).ready(function(){

    var order = {};

    //select all the tables in the page
    var tables = $("table");

    //for each table selected
    tables.each(function (i){

        $(this).attr("id","tab"+i);

        var rows = $(this).children().children();
        rows.each(function (j){

            $(this).attr("id","tab"+i+"row"+j);

            var cols = $(this).children();

            //highlight the header of the table
            cols.eq(0).css("background-color","crimson");
            cols.eq(0).css("color", "white");

            //add on click listener
            cols.eq(0).click(function(){

                var pid = $(this).parent().attr("id");
                var pid2 = $("#"+pid).parent().parent().attr("id");

                SortableTable(pid2, pid);

            });

        });

    });

    

    function SortableTable(tableId,rowId){
        var row = $("#"+rowId);
        var cols = row.children();
        var rows = $("#"+tableId).children().children();
        if(order[rowId] == undefined){
            order[rowId] = false;
        }

        var elems = [];
        cols.each(function (i){
            if(i!=0){
                if(parseFloat($(this).html()) == $(this).html()){
                    elems[elems.length] = parseFloat($(this).html());
                }
                else{
                    elems[elems.length] = $(this).html();
                }
            }

        });

        if(!order[rowId]){
            //sort ascending
            for(var i=0; i<elems.length-1; i++){
                for(var j=i+1; j<elems.length; j++){
                    if(elems[i] > elems[j]){
                        var temp = elems[i];
                        elems[i] = elems[j];
                        elems[j] = temp;
                        rows.each(function(k){
                            var col1 = $(this).children().eq(i+1);
                            var col2 = $(this).children().eq(j+1);
                            temp = col1.html();
                            col1.html(col2.html());
                            col2.html(temp);
                        })
                    }
                }
            }
            order[rowId] = true;
        }

        else{
            //sort descending
            for(var i=0; i<cols.length-1; i++){
                for(var j=i+1; j<cols.length; j++){
                    if(elems[i] < elems[j]){
                        var temp = elems[i];
                        elems[i] = elems[j];
                        elems[j] = temp;
                        rows.each(function(k){
                            var col1 = $(this).children().eq(i+1);
                            var col2 = $(this).children().eq(j+1);
                            temp = col1.html();
                            col1.html(col2.html());
                            col2.html(temp);
                        })
                    }
                }
            }

            order[rowId] = false;
        }
    }
});
