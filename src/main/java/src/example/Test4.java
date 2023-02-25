package example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test4 {
    /**
     * Find the second largest pair sum in an unsorted array
     * [8, 5, 7, 3, 6, 9]
     * output: 9,7
     * */

    public static void main(String[] args) {
        int[] arr=new int[]{8, 5,5 , 7, 3, 6, 9,9};

//        int[] pair= getLargestPair(arr);
//        System.out.println(pair[0]+" "+pair[1]);

        //printAllSums(arr);
        int[] pairResult= getNthLargestSumPair(arr,2);
        System.out.println(pairResult[0]+" "+pairResult[1]);

//        List<Integer> input= Arrays.asList(8, 5, 7, 3, 6, 9);

    }

    private static int[] getNthLargestSumPair(int[] arr, int N) {
        int[] result=new int[N];
        if(N<arr.length){
            List<Integer> list=Arrays.stream(arr).boxed().sorted((a,b)->b.compareTo(a)).collect(Collectors.toList());
            System.out.println(list);
            result[0]=list.get(0);
            result[1]=list.get(N);
        }
        return result;
    }

    private static void printAllSums(int[] arr) {
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                if(i!=j){
                    System.out.println(arr[i]+"+"+arr[j]+"="+(arr[i]+arr[j]));
                }
            }
        }
    }


    private static int[] getLargestPair(int[] arr) {
        int[] result=new int[]{0,0};
        int[] result1=new int[]{0,0};
        int sum=0;
        int sum2=0;


        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                if(i!=j && sum<arr[i]+arr[j]){
                    sum2=sum;
                    result1[0]=result[0];
                    result1[1]=result[1];
                    sum=arr[i]+arr[j];
                    result[0]=arr[i];
                    result[1]=arr[j];
                    System.out.println(result[0]+","+result[1]+","+sum+","+result1[0]+","+result1[1]+","+sum2);
                }
            }
        }
        return result1;
    }
}
