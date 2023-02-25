package example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetTotalMonthsExperienceTechWise {
    public static void main(String[] args) {
        ProjectRepo.getAllProjects().stream().flatMap(p->p.getTechList().stream()).distinct().sorted().forEach(System.out::println);
        long c=ProjectRepo.getAllProjects().stream().flatMap(p->p.getTechList().stream()).distinct().sorted().count();
        System.out.println(c);
    }
}

class ProjectRepo{

    private static final String[][] raw={{"Spring Boot, Spring MVC, Spring Security, MyBatis, MySQL, Tomcat","Dec 2022 to Feb 2023"},
            {"Java/Jee, GWT, Angular, Spring Boot, Spring Rest","Oct 2021 to Dec 2022"},
            {"Java/Jee, ReactJs, Nodejs, Spring Boot, Spring Rest","Sep 2019 to Oct 2021"},
            {"Java/Jee, Spring Boot, Spring Rest, Spring Scheduler","Sep 2019 to Oct 2021"},
            {"Java/Jee, Spring Boot, JasperReports","Sep 2019 to Oct 2021"},
            {"Spring Boot , Spring REST, Spring Data for SQL Server. Front end designed using Angular 8 and Bootstrap","Jan 2019 to Sep 2019"},
            {"Spring Boot , Spring REST, Spring Data for SQL Server, Hibernate and Struts2 Front end designed using Struts2","Nov 2017 to Sep 2019"},
            {"Spring Boot , Spring REST, Spring Data for SQL Server, Hibernate and Struts2 Front end designed using Struts2","Jan 2019 to Sep 2019"},
            {"Spring Batch , Spring Data, Hibernate and Talend","Apr 2017 to Jul 2017"},
            {"Restful web service project with AngularJS 2. Worked for AWS DevOps to create instances, configure and deploy application","Jun 2015 to Mar 2017"},
            {"Spring Boot , Spring REST, Spring Data for SQL Server, Hibernate and Struts2 Front end designed using Struts2","Jan 2014 to May 2015"},
            {"Spring Boot , Spring REST, Spring Data for SQL Server, Hibernate and Struts2 Front end designed using Struts2","Jan 2014 to May 2015"},
            {"Spring Boot , Spring REST, Spring Data for SQL Server, Hibernate and Struts2 Front end designed using Struts2","Jan 2014 to May 2015"},
            {"Java, J2ee, JDBC, Hibernate for backend service development and Flex4, CSS, JavaScript to develop views","May 2012 to Dec 2013"},
            {"Java, J2ee, JDBC, Hibernate for backend service development and Flex4, CSS, JavaScript to develop views","May 2012 to Dec 2013"},
            {"Java, J2ee, JDBC, Apache Solr, Hibernate for backend service development and Flex4, CSS, JavaScript to develop views","May 2012 to Dec 2013"},
            {"Core Java, Swing, JDBC, JasperReports","Mar 2011 to Apr 2012"},
            {"Java/J2ee , JavaScript, Ajax , CSS","Sep 2009 to Feb 2011"}};
    public static List<Project> getAllProjects(){
        return Arrays.stream(raw).map(r-> new Project(r[0],r[1])).collect(Collectors.toList());
//        List<Project> projects=new ArrayList<>();
//        for(String[] r:raw){
//            System.out.println(r.length);
////            projects.add(new Project(r[0],r[1]));
//        }
//        return projects;
    }
}

class Project {
    private String techStack;

    public List<String> getTechList() {
        return techList;
    }

    private List<String> techList = new ArrayList<>();
    private String tenure;

    public long getDurationMonths() {
        return durationMonths;
    }

    private long durationMonths;

    public Project(String techStack, String tenure) {
        this.techStack = techStack;
        this.tenure = tenure;
        if(techStack!=null && !techStack.trim().isEmpty()){
            this.techList= Arrays.stream(techStack.split(",")).map(String :: trim).collect(Collectors.toList());
        }
        if(tenure!=null && !tenure.trim().isEmpty()){
            String[] ss=tenure.split("to");
            try {
                this.durationMonths= calculateDifference(ss[0],ss[1]);
            } catch (ParseException e) {
            }
        }
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    private long calculateDifference(String start, String end) throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("MMM yyyy");
        LocalDate startDate=formatter.parse(start).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endDate=formatter.parse(end).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int months = Period.between(startDate, endDate).getYears() * 12+Period.between(startDate, endDate).getMonths();
//        System.out.println(startDate);
//        System.out.println(endDate);

        return months;
    }
}
