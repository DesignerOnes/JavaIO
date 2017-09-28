package website.dengta.javaio;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/28.
 */
public class Person implements Serializable{

        private transient String name ;
        private int age;
        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

}
