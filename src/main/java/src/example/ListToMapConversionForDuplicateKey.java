package example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListToMapConversionForDuplicateKey {

    /**
     * @see <a href="https://mkyong.com/java8/java-8-convert-list-to-map/">Java 8 – Convert List to Map</a>
     */
    //Java 8 – Convert List to Map
//    Exception in thread "main" java.lang.IllegalStateException: Duplicate key 1
//    at java.util.stream.Collectors.lambda$throwingMerger$0(Collectors.java:133)
//    at java.util.HashMap.merge(HashMap.java:1255)
//    at java.util.stream.Collectors.lambda$toMap$58(Collectors.java:1320)
//    at java.util.stream.ReduceOps$3ReducingSink.accept(ReduceOps.java:169)
//    at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
//    at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
//    at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
//    at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:708)
//    at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
//    at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:566)
//    at example.Test8.main(Test8.java:10)
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 1, 1, 2, 3, 3, 4, 4, 4, 4, 5, 6, 7, 7, 8, 8, 9);

//        list.stream().collect(Collectors.toMap(
//                x->x,
//                x->x))
//                .forEach((k, v) -> System.out.println(k + " " + v));

        list.stream().collect(Collectors.toMap(x -> x,
                        x -> x,
                        (oldValue, newValue) -> oldValue)
                )
                .forEach((k, v) -> System.out.println(k + " " + v));
    }
}
