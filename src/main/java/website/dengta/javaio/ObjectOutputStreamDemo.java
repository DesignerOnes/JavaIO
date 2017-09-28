package website.dengta.javaio;

import java.io.*;

/**
 * Created by Administrator on 2017/9/28.
 */
public class ObjectOutputStreamDemo {

    public static void main(String[] args) throws IOException{
        File file = new File("d:"+File.separator+"hello.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(new Person("dengta",1));
        oos.close();
    }

    /**
     * 二进制文件不能查看，可使用ObjectInputStream类来查看
     */


}



