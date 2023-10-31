package org.example.interview.KMS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import lombok.Builder;

@Builder
class Student {
    private String name;
    private int score;

    public Student() {

    }

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Score: %s", name, score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}

public class KMS {

    public static void main(String[] args) {

        List<Student> students = new ArrayList<Student>(5);

        students.add(new Student("Nguyen A", 6));

        students.add(new Student("Tran B", 4));

        students.add(new Student("Nguyen F", 5));

        students.add(new Student("Le C", 7));

        students.add(new Student("Ho D", 9));

        students.add(new Student("Nguyen F", 8));

        students.add(new Student("Nguyen A", 10));

        // Print students that have score > 5, in ascending order, if duplicated, print
        // higher score only.

        HashMap<String, Student> store = new HashMap<>();

        HashSet<Integer> set = new HashSet<>();
        set.add(null);

        Integer a = null; // heap, class Object
        int b; // stack, primary type

        for (Student student : students) {
            Student temp = store.get(student.getName());
            if (temp == null) {
                store.put(student.getName(), student);
                continue;
            }

            if (temp.getScore() < student.getScore()) {
                store.put(student.getName(), student);
            }
        }

        List<Student> list = new ArrayList<>(store.values());

        Collections.sort(list, (st1, st2) -> {
            return Integer.compare(st1.getScore(), st2.getScore());
        });

        list.stream().filter(t -> t.getScore() > 5).forEach(System.out::println);
    }

}

// quan ly doc gia thue sach
// table: book, user, user_book
// book: id, price, name, year,...
// user: id, name, dob, address,...
// user_book: PK(user_id, book_id), number_of_book,...

// select sum() from user_book group by user_id having user_id = 'id'

// cross join, left join, right join, full join.
// token: 1h
// refresh_token: 3 ngay
