
import java.util.Arrays;

/**
 * 
 * 초기 정렬된 배열의 병합
 * 
 */
public class App {
    public static int[] merge(int[] left, int[] right) {
        // 두 배열을 병합하여 정렬된 하나의 배열로 반환
        int[] merged = new int[left.length + right.length];
        // left의 초기 인덱스. right의 초기 인덱스. 병합될 배열의 초기 인덱스 
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                merged[k++] = left[i++];
            } else{
                merged[k++] = right[j++];
            }
        }

        // 남은 요소들을 병합
        while(i < left.length) {
            merged[k++] = left[i++];
        }
        while(j < right.length) {
            merged[k++] = right[j++];
        }
        
        return merged;
    }
    public static void main(String[] args) throws Exception {
        int[] left  = {};
        int[] right = {2, 4, 6};

        System.out.println(Arrays.toString(merge(left, right)));
    }
}
