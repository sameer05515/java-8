package example;// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

import java.util.HashSet;
import java.util.Set;

class Test2 {
    public int solution(int[][] A) {

        int count=0;

        Set<Integer> set= new HashSet<>();
        // Implement your solution here
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A[i].length;j++){
                for(int k=0;k<A.length;k++){
                    for(int l=1;l<A[i].length;l++){
                        if(i!=k){
                            set.add(A[i][j]);
                        }
                    }
                }
            }
        }
        return set.size();
    }
}
