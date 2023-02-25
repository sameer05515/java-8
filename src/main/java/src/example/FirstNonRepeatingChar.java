package example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FirstNonRepeatingChar {
    public static void main(String[] args) {
        String s="abcdabcxyy";
        Set<Character> set=new HashSet<>();
//        s.chars().filter(ch->!set.add(ch)).collect(Collectors.toSet());

        //Arrays.asList(s.toCharArray()).stream().filter(ch->!set.add((char)ch)).collect(Collectors.toSet());

        char[] characters= s.toCharArray();

        for(int i=0;i<characters.length;i++){
            boolean duplicate=false;
            for(int j=0;j<characters.length;j++) {
                if(i!=j && characters[i]==characters[j]){
                    duplicate=true;
                    break;
                }
            }
            if(!duplicate){
                System.out.println(characters[i]);
                break;
            }
        }
    }
}
