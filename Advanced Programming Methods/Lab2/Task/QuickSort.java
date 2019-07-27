package Task;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort extends AbstractSorter {

    public ArrayList<Integer> sort(ArrayList<Integer> arr){
        arr = quickSort(0, arr.size()-1, arr);
        return arr;
    }

    private ArrayList<Integer> quickSort(int left, int right, ArrayList<Integer> arr){
        if (right <= left){
            return arr;
        }
        int mid = (left+right)/2;
        int pivot = arr.get(mid);
        int i = left; int j = right;

        while (i<j){
            while (arr.get(i).compareTo(pivot)<0)
                i++;
            while (arr.get(j).compareTo(pivot)>0)
                j--;
            if (i<j)
                Collections.swap(arr, i, j);
        }

        quickSort(left, j-1, arr);
        quickSort(j+1, right, arr);
        return arr;
    }
}
