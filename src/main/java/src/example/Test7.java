package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test7 {
    public static void main(String[] args) {
        String techStack="Spring Boot, Spring MVC, Spring Security, MyBatis, MySQL, Tomcat";
        List<String> techList= getTechList(techStack);
        techList.forEach(l-> System.out.println(l));
    }

    private static List<String> getTechList(String techStack) {
        List<String> techList = new ArrayList<>();
        if(techStack!=null && !techStack.trim().isEmpty()){
            techList=Arrays.stream(techStack.split(",")).map(String :: trim).collect(Collectors.toList());
        }
        return techList;
    }
}
