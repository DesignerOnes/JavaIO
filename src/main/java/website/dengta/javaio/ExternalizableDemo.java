package website.dengta.javaio;

import java.io.*;

/**
 * Created by Administrator on 2017/9/28.
 * <p>
 * 被Serializable接口声明的类的对象的属性都将被序列化，但是如果想自定义序列化的内容的时候，就需要实现Externalizable接口。
 * <p>
 * 当一个类要使用Externalizable这个接口的时候，这个类中必须要有一个无参的构造函数，如果没有的话，在构造的时候会产生异常，这是因为在反序列话的时候会默认调用无参的构造函数。
 */
public class ExternalizableDemo {
    public static void main(String[] args) throws Exception {
        ser(); // 序列化
        dser(); // 反序列话
    }

    public static void ser() throws Exception{
        File file = new File("d:" + File.separator + "hello.txt");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
                file));
        out.writeObject(new PersonE("rollen", 20));
        out.close();
    }

    public static void dser() throws Exception{
        File file = new File("d:" + File.separator + "hello.txt");
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(
                file));
        Object obj = input.readObject();
        input.close();
        System.out.println(obj);
    }


    /**
     * 当我们使用Serializable接口实现序列化操作的时候，如果一个对象的某一个属性不想被序列化保存下来，那么我们可以使用transient关键字进行说明：
     */
}
