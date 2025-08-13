import java.io.BufferedReader;
import java.io.InputStreamReader;

public class p_2018 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int start_p = 1, end_p = 1, sum = 1, count = 1;

        while(end_p != N){
            if(sum == N){
                count++;
                end_p++;
                sum += end_p;
            } else if(sum > N){
                sum -= start_p;
                start_p++;
            } else{
                end_p++;
                sum += end_p;
            }
        }

        System.out.println(count);
    }
}