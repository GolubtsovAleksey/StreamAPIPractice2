import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }
}
public class Main {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        Map<String, Double> averageGrades = students.parallelStream()                 //  Открываю многопоточность
                .flatMap(student -> student.getGrades().entrySet().stream()) // flatMap - сглаживаем в поток пары Предмет=Оценка которые получит от.stream, entrySet() из Map достанет объекты-пары вернёт Set и далее стрим пускает пары по конвейеру
                .collect(Collectors.groupingBy(                                          // (entrySet().stream() - поток пар
                        Map.Entry::getKey,                                     // Группирую по имени предмета (ключ)
                        Collectors.averagingDouble(Map.Entry::getValue)       //  далее в сгруппированных коробках считаю среднюю оценку (значение)
                ));
        System.out.println("Средние оценки по предметам");
        System.out.println(averageGrades);
    }
}