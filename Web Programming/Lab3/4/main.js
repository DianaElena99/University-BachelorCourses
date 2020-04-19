/*const getCellValue = (tr, idx) => tr.children[idx].innerText || tr.children[idx].textContent;

const comparer = (idx, asc) => (a, b) => ((v1, v2) => 
    v1 !== '' && v2 !== '' && !isNaN(v1) && !isNaN(v2) ? v1 - v2 : v1.toString().localeCompare(v2)
    )(getCellValue(asc ? a : b, idx), getCellValue(asc ? b : a, idx));

document.querySelectorAll('th').forEach(th => th.addEventListener('click', (() => {
    const table = th.closest('table');
    Array.from(table.querySelectorAll('tr:nth-child(n+2)'))
        .sort(comparer(Array.from(th.parentNode.children).indexOf(th), this.asc = !this.asc))
        .forEach(tr => table.appendChild(tr) );
        })
    )
);*/

function bubbleSortTable(id, row, dir) {
    var tbody = document.getElementById(id).children[0];
    var cols = document.getElementById(id).rows[0].cells.length;
    var rows = document.getElementById(id).getElementsByTagName("tr").length-1;
    
    var swapped = true;
    do {
        swapped = false;
        
        for (var c = 1; c < rows; c++) {
            var cellCurr, cellNext;
            
            if (rowIsNumeric(id, row)) {
                cellCurr = parseInt(tbody.children[c].children[row].innerHTML);
                cellNext = parseInt(tbody.children[c+1].children[row].innerHTML);
            } else {
                cellCurr = tbody.children[c].children[row].innerHTML;
                cellNext = tbody.children[c+1].children[row].innerHTML;
            }
            
            
            if (dir == 'asc' && cellCurr > cellNext) {
                for (var r = 0; r < cols; r++) {
                    var temp = tbody.children[c].children[r].innerHTML;
                    tbody.children[c].children[r].innerHTML = tbody.children[c+1].children[r].innerHTML;
                    tbody.children[c+1].children[r].innerHTML = temp;
                }
                
                swapped = true;
            }
            else if (dir == 'desc' && cellNext > cellCurr) {
                for (var r = 0; r < cols; r++) {
                    var temp = tbody.children[c].children[r].innerHTML;
                    tbody.children[c].children[r].innerHTML = tbody.children[c+1].children[r].innerHTML;
                    tbody.children[c+1].children[r].innerHTML = temp;
                }
                
                swapped = true;
            }
        }
    } while (swapped);
    
    if (dir == 'asc')
        tbody.children[0].children[row].setAttribute("onclick", "bubbleSortTable('" + id + "', " + row + ", 'desc');");
    else
        tbody.children[0].children[row].setAttribute("onclick", "bubbleSortTable('" + id + "', " + row + ", 'asc');");
}

function rowIsNumeric(id, row) {
    var tbody = document.getElementById(id).children[0];
    var cols = document.getElementById(id).rows[0].cells.length;
    
    for (var c = 0; c < cols; c++)
        if (! isNumeric(tbody.children[row].children[c].innerHTML))
            return false;
            
    return true;
}

function isNumeric(n) {
  return ! isNaN (parseFloat(n)) && isFinite (n);
}
