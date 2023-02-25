package example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaDuplicated1 {

    public static void main(String[] args) {

        // 3, 4, 9
//        List<Integer> list = Arrays.asList(5, 3, 4, 1, 3, 7, 2, 9, 9, 4);
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 1, 6, 7, 2);

        Set<Integer> result = findDuplicateBySetAdd(list);
        result.forEach(System.out::println);

        Set<Integer> res1 = findDuplicate(list);
        res1.forEach(System.out::println);

        Set<Integer> res2 = findDuplicateByGrouping(list);
        res2.forEach(System.out::println);

    }

    public static <T> Set<T> findDuplicate(List<T> list) {
        return list.stream().filter(item -> Collections.frequency(list, item) > 1).collect(Collectors.toSet());
    }

    public static <T> Set<T> findDuplicateBySetAdd(List<T> list) {

        Set<T> items = new HashSet<>();
        return list.stream()
                .filter(n -> !items.add(n)) // Set.add() returns false if the element was already in the set.
                .collect(Collectors.toSet());

    }

    public static <T> Set<T> findDuplicateByGrouping(List<T> list) {

        return list.stream()
                .collect(Collectors.groupingBy(Function.identity()
                        , Collectors.counting()))    // create a map {1=1, 2=1, 3=2, 4=2, 5=1, 7=1, 9=2}
                .entrySet().stream()                 // Map -> Stream
                .filter(m -> m.getValue() > 1)       // if map value > 1, duplicate element
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

    }

}