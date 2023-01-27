package compareTwoObjectGetMax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<SchoolObj> listOne = new ArrayList<SchoolObj>();
        // TODO: Add sample data to listOne.
        listOne.add(new SchoolObj("nameA", "schoolA", 101, 34));
        listOne.add(new SchoolObj("nameB", "schoolB", 102, 43));
        listOne.add(new SchoolObj("nameC", "schoolC", 103, 42));
        listOne.add(new SchoolObj("nameD", "schoolD", 104, 45));

        List<SchoolObj> listTwo = new ArrayList<SchoolObj>();
        // TODO: Add sample data to listTwo.
        listTwo.add(new SchoolObj("nameA", "schoolA", 101, 32));
        listTwo.add(new SchoolObj("nameB", "schoolB", 102, 41));
        listTwo.add(new SchoolObj("nameC", "schoolC", 103, 45));
        listTwo.add(new SchoolObj("nameD", "schoolD", 104, 48));
        listTwo.add(new SchoolObj("nameE", "schoolE", 105, 47));

        List<SchoolObj> listOneList = listOne.stream().filter(one -> listTwo.stream()
                        .anyMatch(two -> two.getName().equals(one.getName())))
                .collect(Collectors.toList());

        List<List<Integer>> list = listOne.stream().map(one -> listTwo.stream().map(two -> two.getName().equals(one.getName()) ? Math.max(one.getRent(), two.getRent()) : 0).collect(Collectors.toList())).collect(Collectors.toList());
        System.out.println("listOneList:" + list);

        List<Integer> list2 = listOne.stream().map(one -> listTwo.stream().filter(two -> two.getName().equals(one.getName())).map(two -> Math.max(two.getRent(), one.getRent())).collect(Collectors.toList()).get(0)).collect(Collectors.toList());
        System.out.println("list2:" + list2);

    }
}

class SchoolObj
{
    private String name;
    private String school;

    private int id;

    private int rent;

    public SchoolObj(String name, String school, int id, int rent) {
        this.name = name;
        this.school = school;
        this.id = id;
        this.rent = rent;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public int getId() {
        return id;
    }

    public int getRent() {
        return rent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "SchoolObj{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", id=" + id +
                ", rent=" + rent +
                '}';
    }
}
