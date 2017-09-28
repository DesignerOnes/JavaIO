package website.dengta.javaio;

import java.io.*;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ObjectInputStreamDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("d:" + File.separator + "hello.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object obj = ois.readObject();
        ois.close();
        System.out.println(obj);

    }

    //对象序列化的反序列化
    /**
     * 打印代码如下：Person{name='dengta', age=1}
     */
}



