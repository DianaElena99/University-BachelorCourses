function bubbleSortTable(id, row, dir) {
    var tbody = document.getElementById(id).children[0];
    var rows = tbody.children.length;
    var cols = tbody.children[row].children.length - 1;
    
    var swapped = true;
    do {
        swapped = false;
        
        for (var c = 1; c < cols; c++) {
            var cellCurr, cellNext;
            
            if (rowIsNumeric(id, row)) {
                cellCurr = parseInt(tbody.children[row].children[c].innerHTML);
                cellNext = parseInt(tbody.children[row].children[c + 1].innerHTML);
            } else {
                cellCurr = tbody.children[row].children[c].innerHTML;
                cellNext = tbody.children[row].children[c + 1].innerHTML;
            }
            
            
            if (dir == 'asc' && cellCurr > cellNext) {
                for (var r = 0; r < rows; r++) {
                    var temp = tbody.children[r].children[c].innerHTML;
                    tbody.children[r].children[c].innerHTML = tbody.children[r].children[c + 1].innerHTML;
                    tbody.children[r].children[c + 1].innerHTML = temp;
                }
                
                swapped = true;
            }
            else if (dir == 'desc' && cellNext > cellCurr) {
                for (var r = 0; r < rows; r++) {
                    var temp = tbody.children[r].children[c + 1].innerHTML;
                    tbody.children[r].children[c + 1].innerHTML = tbody.children[r].children[c].innerHTML;
                    tbody.children[r].children[c].innerHTML = temp;
                }
                
                swapped = true;
            }
        }
    } while (swapped);
    
    if (dir == 'asc')
        tbody.children[row].children[0].setAttribute("onclick", "bubbleSortTable('" + id + "', " + row + ", 'desc');");
    else
        tbody.children[row].children[0].setAttribute("onclick", "bubbleSortTable('" + id + "', " + row + ", 'asc');");
}

function rowIsNumeric(id, row) {
    var tbody = document.getElementById(id).children[0];
    var cols = tbody.children[row].children.length - 1;
    
    for (var c = 1; c < cols; c++)
        if (! isNumeric(tbody.children[row].children[c].innerHTML))
            return false;
            
    return true;
}

function isNumeric(n) {
  return ! isNaN (parseFloat(n)) && isFinite (n);
}
