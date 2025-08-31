
import java.util.Arrays;

public class App2 {

    public static void mergeSort(int[] array) {
        int n = array.length;
        int[] temp = new int[n];
        
        for(int step=1; step < n; step *= 2) {
            for(int start=0; start < n; start += 2 * step) {
                // 두 개의 부분 배열을 병합
                int mid = Math.min(start + step, n);
                int end = Math.min(start + 2 * step, n);

                merge(array, temp, start, mid, end);
            }
        }
    }

    public static void merge(int[] array, int[] temp, int start, int mid, int end) {
        int i=start, j=mid, k=start;

        while(i< mid && j < end){
            if(array[i] <= array[j]){
                temp[k++] = array[i++];
            }else{
                temp[k++] = array[j++];
            }
        }

        while(i < mid) {
            temp[k++] = array[i++];
        }
        while(j < end) {
            temp[k++] = array[j++];
        }

        // 배열 복사
        for(i=start; i<end; i++) {
            array[i] = temp[i];
        }
        
    }
    public static void main(String[] args) {
        int[] arr = {5, 2, 4, 1, 3, 7, 6};
        System.out.println("정렬 전: " + Arrays.toString(arr));
        mergeSort(arr);
        System.out.println("정렬 후: " + Arrays.toString(arr));
    }
}
