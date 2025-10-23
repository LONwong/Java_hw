public class HW3 {
    public static void main(String[] args) {
        Human h = new Human("Alice", 20);
        Student s1 = new Student("Bob", 22, "CS");
        Student s2 = new Student("Bob", 22, "CS");

        System.out.println(h);
        System.out.println(s1);
        System.out.println("s1==s2?"+s1.equals(s2));

        Human h2 = new Student("Carol", 22, "Math");
        System.out.println(h2);

        Student s3 = (Student) h2; 
        System.out.println(s3.major);
    }
}

class Human{
    String name;
    int age;

    Human(String name,int age){
        this.name=name;
        this.age=age;
    }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Human))
            return false;
        Human h = (Human) obj;
        return age == h.age && name.equals(h.name);
    }

}

class Student extends Human{
    String major;
    Student(String name,int age,String major){
        super(name, age);
        this.major=major;
    }

    @Override
    public String toString() {
        return super.toString() + " major:" + major;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Student))
            return false;
        Student s = (Student) obj;
        return super.equals(s) && major.equals(s.major);
    }

    @Override
    public int hashCode (){
        return super.hashCode() + major.hashCode();
    }
}