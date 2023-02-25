package example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test8 {
    public static void main(String[] args) {
        List<Integer> intList= Arrays.asList(5, 7,8,9);

        List<Integer> anotherList= intList.stream().filter(i->{
            return i%3==0;
        }).collect(Collectors.toList());



//        System.out.println(anotherList);

        anotherList= intList.stream().filter(i->{
            boolean isPrime=false;
            for(int j=2;j<i;j++){
                isPrime= i%j != 0;
                if(!isPrime){
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        System.out.println(anotherList);


    }
}
