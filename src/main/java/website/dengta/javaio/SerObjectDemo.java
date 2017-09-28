package website.dengta.javaio;

import java.io.*;

/**
 * Created by Administrator on 2017/9/28.
 */
public class SerObjectDemo {
    public static void main(String[] args) throws Exception{
        Student[] stu = { new Student("hello", 20), new Student("world", 30),
                new Student("rollen", 40) };
        ser(stu);
        Object[] obj = dser();
        for(int i = 0; i < obj.length; ++i){
            Student s = (Student) obj[i];
            System.out.println(s);
        }
    }

    // 序列化
    public static void ser(Object[] obj) throws Exception{
        File file = new File("d:" + File.separator + "hello.txt");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
                file));
        out.writeObject(obj);
        out.close();
    }

    // 反序列化
    public static Object[] dser() throws Exception{
        File file = new File("d:" + File.separator + "hello.txt");
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(
                file));
        Object[] obj = (Object[]) input.readObject();
        input.close();
        return obj;
    }
}


class Student implements Serializable{
    public Student(){

    }

    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString(){
        return "姓名： " + name + " 年龄：" + age;
    }

    private String name;
    private int age;
}

