$(document).ready(function(){
    var order = {};
    //select all the tables from the page
    var tables = $("table");
    tables.each(function (i){
        $(this).attr("id","t"+i);
        var rows = $(this).children().children();
        rows.each(function (j){
            $(this).attr("id","tab"+i+"row"+j);
        });

        var cols = rows.eq(0).children();
        cols.each(function(j){

            $(this).attr("id","tab"+i+"row0"+"col"+j);

            $(this).css("background-color","crimson");
            $(this).css("color", "white")

            $(this).click(function (){

                var cid = $(this).attr("id");
                var cid2 = tables.eq(i).attr("id");
                SortTable(cid2, cid);

            });

        })

    });

    

    function SortTable(tab,coloana){
        var rows = $("#"+tab).children().children();
        if(order[coloana] == undefined){
            order[coloana] = false;
        }
        var elems = [];
        var cols = rows.eq(0).children();
        var nrCols = cols.length;
        var indexCol = 0;

        cols.each(function (i){
            if($(this).attr("id") == coloana){
                indexCol = i;
            }

        });

        rows.each(function (i){
            if(i!=0){
                var comp = $(this).children().eq(indexCol); 
                if(parseFloat(comp.html()) == comp.html()){
                    elems[elems.length] = parseFloat(comp.html());
                }
                else{
                    elems[elems.length] = comp.html();
                }
            }

        });



        if(!order[coloana]){
            //ascending
            for(var i=0; i<rows.length-1; i++){

                for(var j=i+1; j<rows.length; j++){

                    if(elems[i] > elems[j]){

                        var temp = elems[i];
                        elems[i] = elems[j];
                        elems[j] = temp;

                        for(var c = 0; c<nrCols; c++){
                            var c1 = rows.eq(i+1).children().eq(c);
                            var c2 = rows.eq(j+1).children().eq(c);
                            temp = c1.html();
                            c1.html(c2.html());
                            c2.html(temp);
                        }
                    }
                }
            }
            order[coloana] = true;

        }

        else{
            //descending
            for(var i=0; i<rows.length-1; i++){
                for(var j=i+1; j<rows.length; j++){
                    if(elems[i] < elems[j]){

                        var temp = elems[i];
                        elems[i] = elems[j];
                        elems[j] = temp;

                        for(var c = 0; c<nrCols; c++){
                            var c1 = rows.eq(i+1).children().eq(c);
                            var c2 = rows.eq(j+1).children().eq(c);
                            temp = c1.html();
                            c1.html(c2.html());
                            c2.html(temp);
                        }
                    }
                }
            }

            order[coloana] = false;

        }    
    }
});
