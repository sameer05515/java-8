package example;

import java.util.StringJoiner;
import java.util.stream.IntStream;

public class EvenSum {
    public static void main(String[] args) {
        int[] arr= new int[]{1,4,2,5,7,8};
        System.out.println(sum(arr));
    }

    private static int sum(int[] arr) {
        int sum=0;
        int[] sumarr=new int[]{0};

//        for(int i=2;i-1<arr.length;i=i+2){
//            for(int j=0;j+i-1<arr.length;j++){
//                int temp=0;
//                StringJoiner sj= new StringJoiner(",","[","]");
//                for(int k=0;k<i;k++){
//                    sj.add(arr[j+k]+"");
//                    temp+=arr[j+k];
//                }
//                System.out.println(sj.toString()+" = "+temp);
//                sum+=temp;
//            }
//        }

        IntStream.iterate(2,i->i+2).limit(arr.length/2)
                .forEach(i->{
                    for(int j=0;j+i-1<arr.length;j++){
                        int temp=0;
                        StringJoiner sj= new StringJoiner(",","[","]");
                        for(int k=0;k<i;k++){
                            sj.add(arr[j+k]+"");
                            temp+=arr[j+k];
                        }
                        System.out.println(sj.toString()+" = "+temp);
                        sumarr[0]+=temp;
                    }
                });

        return sumarr[0];
    }
}
