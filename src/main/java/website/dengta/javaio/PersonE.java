package website.dengta.javaio;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by Administrator on 2017/9/28.
 */
public class PersonE implements Externalizable{

    private String name ;
    private int age;

    public PersonE() {
    }

    public PersonE(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonE{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // 复写这个方法，根据需要可以保存的属性或者具体内容，在序列化的时候使用
    public void writeExternal(ObjectOutput out) throws IOException{
        out.writeObject(this.name);
        out.writeInt(age);
    }

    // 复写这个方法，根据需要读取内容 反序列话的时候需要
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException{
        this.name = (String) in.readObject();
        this.age = in.readInt();
    }
}
