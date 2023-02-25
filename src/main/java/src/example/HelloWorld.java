package example;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HelloWorld {
    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 2, 2, 3, 4, 5};
        Set<Integer> dup = new HashSet<>();
        Map<Integer,Integer> mapDup= new HashMap<>();


        Arrays.asList(1, 1, 2, 2, 2, 3, 4, 5).stream().filter((i) -> !dup.add(i)).distinct().collect(Collectors.toList()).forEach(i -> System.out.println(i));


        //Arrays.asList(1, 1, 2, 2, 2, 3, 4, 5).stream().collect(Collectors.toMap(i->i,i->i,(old,new)->new))

        for(int i:arr){
            if(!mapDup.containsKey(i)){
                mapDup.put(i,i);
            }else {
                dup.add(i);
            }
        }
        System.out.println(dup);
    }
}
