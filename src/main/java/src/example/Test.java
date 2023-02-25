package example;

import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {


        System.out.println(solution("AaBgggggggb"));
        System.out.println(solutionWithStream("AaBgggggggb"));
        System.out.println(solutionWithStreamParallel("AaBgggggggb"));
        int i = 'b' - 'B';
//        System.out.println(i);

//        IntStream.rangeClosed('A','z').forEach(ch->{
//            System.out.println((char)ch+" = "+ch);
//        });
    }

    public static String solution(String S) {
        long start = System.currentTimeMillis();
        String result = "NO";

        char[] charArr = S.toCharArray();
        char res = 'A';
        boolean found = false;
        for (char ch : charArr) {
            for (char ch1 : charArr) {
                if ((ch != ch1) && (ch - ch1) % 32 == 0) {
//                    System.out.println(ch+" "+ch1);
                    found = true;
                    if (res < ((ch > ch1 ? ch1 : ch))) {
                        res = (ch > ch1 ? ch1 : ch);
                    }
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("normal for loop in MilliSeconds: " + (end-start));


        return found ? "" + res : result;
    }

    public static String solutionWithStream(String S) {
        long start = System.currentTimeMillis();
        String result = "NO";
        char[] res = {'A'};
        boolean[] found = {false};

        S.chars().forEach(ch -> {
            S.chars().filter(ch1 -> ((ch != ch1) && (ch - ch1) % 32 == 0)).forEach(ch1 -> {
//                System.out.println((char) ch + " " + (char) ch1);
                found[0] = true;
                if (res[0] < (ch > ch1 ? ch1 : ch)) {
                    res[0] = (char) (ch > ch1 ? ch1 : ch);
                }
            });
        });

        long end = System.currentTimeMillis();
        System.out.println("sequentialDuration in MilliSeconds: " + (end-start));


        return found[0] ? "" + res[0] : result;
    }

    public static String solutionWithStreamParallel(String S) {
        long start = System.currentTimeMillis();
        String result = "NO";
        char[] res = {'A'};
        boolean[] found = {false};

        S.chars().parallel().forEach(ch -> {
            S.chars().filter(ch1 -> ((ch != ch1) && (ch - ch1) % 32 == 0)).forEach(ch1 -> {
//                System.out.println((char) ch + " " + (char) ch1);
                found[0] = true;
                if (res[0] < (ch > ch1 ? ch1 : ch)) {
                    res[0] = (char) (ch > ch1 ? ch1 : ch);
                }
            });
        });

        long end = System.currentTimeMillis();
        System.out.println("parallelDuration in MilliSeconds: " + (end-start));


        return found[0] ? "" + res[0] : result;
    }
}
