package website.dengta.javaio;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/9/26.
 */
public class TestJavaIO {

    /**
     * 制定目录创建文件
     */
    @Test
    public void TestCreateFile() {
        File file = new File("D:" + File.separator + "java-io.txt");
        try {
            if (!file.exists()) {
                System.out.println("文件不存在，创建文件");
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定目录创建文件夹
     */
    @Test
    public void TestCreateDir() {
        File file = new File("D:" + File.separator + "TestJava");

        if (!file.exists()) {
            System.out.println("文件夹不存在，创建");
            file.mkdirs();
        }

    }

    /**
     * 删除文件或者文件夹
     */
    @Test
    public void TestDeleteFileOrDir() {
        String fileName = "D:" + File.separator + "TestJava";
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {//文件存在且为文件
            file.delete();
        } else if (!file.exists() && file.isFile()) {
            System.out.println("文件不存在");
        } else if (file.exists() && file.isDirectory()) {
            file.delete();
            System.out.println("删除文件夹成功");
        } else {
            System.out.println("文件夹不存在");
        }
    }

    /**
     * 打印出目录下的一级目录的文件和文件夹
     * <p>
     * File下的
     * list()方法是不带路径
     * listFiles()是带路径的
     */
    @Test
    public void TestFilesList() {
        String fileName = "D:" + File.separator;

        File file = new File(fileName);
        //File[] files = file.listFiles();
        String[] files = file.list();

        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i]);
        }
    }

    /**
     * 使用递归调用的方式来列出目录下的所有文件和文件夹
     */
    @Test
    public void TestListAllFileAndDir() {
        String fileName = "D:" + File.separator + "TestJavaIO";
        File file = new File(fileName);
        System.out.println("列出文件路径-->" + file);

        PrintFileAndDir(file);
    }

    private void PrintFileAndDir(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        //递归调用
                        System.out.println(files[i]);
                        PrintFileAndDir(files[i]);
                    }
                }
            }
        }
    }

    /**
     * RandomAccessFile 方式写入文件
     * 乱码：asdsad    A?αH@(>ωΫ"Πε
     * 打开文件是一个乱码。原因：
     * String.getBytes()会按系统默认编码获取字符串的字节码，
     * 而RandomAccessFile.write(byte b[])正好也是按照系统默认编码进行写入的，
     * 这样两处编码正好一致，使用其它方法无法保证编码一致。
     *
     * @throws Exception
     */
    @Test
    public void TestRandomAccessFile() throws Exception {
        String fileName = "D:" + File.separator + "RandomAccessFile.txt";
        File f = new File(fileName);
        RandomAccessFile demo = new RandomAccessFile(f, "rw");
        demo.writeBytes("asdsad");
        demo.writeInt(12);
        demo.writeBoolean(true);
        demo.writeChar('A');
        demo.writeFloat(1.21f);
        demo.writeDouble(12.123);
        // 文件长度，字节数
        long fileLength = demo.length() - 2;
        // 将写文件指针移到文件尾。
        demo.seek(fileLength);
        String content = "\"乱码解决的方式是使用WriteBytes的方式来写入文件\");";
        demo.write(("\n" + content + "\n").getBytes());
        demo.close();
    }

    /**
     * 使用字节流的方式--写入文件
     */
    @Test
    public void TestReaderToWriteFile() throws Exception {
        String fileName = "D:" + File.separator + "TestJavaIO" + File.separator + "hello.txt";
        File f = new File(fileName);
        OutputStream out = new FileOutputStream(f);
        String str = "你好";
        byte[] b = str.getBytes();
        out.write(b);
        out.close();
    }

    /**
     * 使用字节流的方式一个一个字节的写入到文件中
     *
     * @throws Exception
     */
    @Test
    public void TestStreamToWriteFile() throws Exception {
        String fileName = "D:" + File.separator + "Stream.txt";
        File file = new File(fileName);
        OutputStream os = new FileOutputStream(file, true); //写入追加模式
        byte[] bytes = "TestStreamToWriteFile".getBytes();

        for (int i = 0; i < bytes.length; i++) {
            os.write(bytes[i]);
        }
        os.close();
    }


    /**
     * 文件读取方式
     *
     * @throws Exception
     */
    @Test
    public void TestReadFile() throws Exception {
        String fileName = "D:" + File.separator + "Stream.txt";
        File file = new File(fileName);
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[1024];

        int len = is.read(bytes);  //读取到的字节流
        is.close();
        System.out.println(new String(bytes));


        System.out.println("读入长度为：" + len);
        System.out.println(new String(bytes, 0, len));
    }

    /**
     * 字节流
     * 读文件内容,节省空间
     */
    @Test
    public void TestSaveSplaceToReadFile() throws Exception {
        String fileName = "D:" + File.separator + "Stream.txt";
        File f = new File(fileName);
        InputStream in = new FileInputStream(f);
        byte[] b = new byte[(int) f.length()];
        in.read(b);
        System.out.println("文件长度为：" + f.length());
        in.close();
        System.out.println(new String(b));
    }

    /**
     * 一个一个字节读取文件
     */
    @Test
    public void TestByteToByteToReadFile() throws Exception {
        String fileName = "D:" + File.separator + "Stream.txt";
        File f = new File(fileName);
        InputStream in = new FileInputStream(f);
        byte[] b = new byte[(int) f.length()];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) in.read();
        }
        System.out.println(new String(b));
    }

    /**
     * 上面的几个例子都是在知道文件的内容多大，然后才展开的，
     * 有时候我们不知道文件有多大，这种情况下，我们需要判断是否独到文件的末尾。
     */

    @Test
    public void TestUnknowFileLengthToRead() throws Exception {
        String fileName = "D:" + File.separator + "hello.txt";
        File f = new File(fileName);
        InputStream in = new FileInputStream(f);
        byte[] b = new byte[1024];
        int count = 0;
        int temp = 0;
        while ((temp = in.read()) != (-1)) {//当读到文件末尾的时候会返回-1.正常情况下是不会返回-1的
            b[count++] = (byte) temp;
        }
        in.close();
        System.out.println(new String(b));
    }

    /**
     * 字符流
     * 写入数据
     */
    @Test
    public void TestWriteToWriteData() throws Exception {
        String fileName = "D:" + File.separator + "WriteToWriteData.txt";
        File file = new File(fileName);
        Writer writer = new FileWriter(file, true);
        writer.write("字符流的方式写入数据");
        writer.close();
    }

    /**
     * 字符流
     * 读取数据
     */

    @Test
    public void TestReadFileByWrite() throws Exception {
        String fileName = "D:" + File.separator + "WriteToWriteData.txt";
        File file = new File(fileName);
        Reader reader = new FileReader(file);
        char[] chars = new char[100];
        int count = reader.read(chars);
        reader.close();
        System.out.println("内容如下：\n\t" + new String(chars, 0, count));
    }

    /**
     * 最好采用循环读取的方式，因为我们有时候不知道文件到底有多大
     */
    @Test
    public void TestCycleToRead() throws Exception {
        String fileName = "D:" + File.separator + "WriteToWriteData.txt";
        File file = new File(fileName);
        Reader read = new FileReader(file);
        char[] chars = new char[100];
        int temp = 0;
        int count = 0;
        while ((temp = read.read()) != (-1)) {
            chars[count++] = (char) temp;
        }
        read.close();
        System.out.println("内容为:\n\t" + new String(chars, 0, count));
    }

    /***
     * 小结：
     *
     *  关于字节流和字符流的区别

     实际上字节流在操作的时候本身是不会用到缓冲区的，是文件本身的直接操作的，但是字符流在操作的 时候下后是会用到缓冲区的，是通过缓冲区来操作文件的。

     读者可以试着将上面的字节流和字符流的程序的最后一行关闭文件的代码注释掉，然后运行程序看看。你就会发现使用字节流的话，文件中已经存在内容，但是使用字符流的时候，文件中还是没有内容的，这个时候就要刷新缓冲区。

     使用字节流好还是字符流好呢？

     答案是字节流。首先因为硬盘上的所有文件都是以字节的形式进行传输或者保存的，包括图片等内容。但是字符只是在内存中才会形成的，所以在开发中，字节流使用广泛。
     */


    /**
     * ******************************************
     *
     *          2017.09.26  待续.....
     *
     * ******************************************
     */


    /**
     * 文件复制
     */
    @Test
    public void TestCopyFile() throws Exception {
        String srcFile = "D:" + File.separator + "SrcFile.txt";
        String desFile = "D:" + File.separator + "desFile.txt";

        File src = new File(srcFile);
        File des = new File(desFile);

        if (!src.exists()) {
            System.out.println("源文件不存在，请检查~");
            //System.exit(1);
            //没有就创建一个文件
            System.out.println("创建一个文件");
            src.createNewFile();

            //使用字节流的方式写入数据
            OutputStream outputStream = new FileOutputStream(srcFile, true);
            byte[] bytes = "复制文件操作".getBytes();
            outputStream.write(bytes);

        }

        System.out.println("复制文件操作");
        InputStream in = new FileInputStream(src);

        OutputStream out = new FileOutputStream(des);

        if (in != null && out != null) {
            int temp = 0;
            while ((temp = in.read()) != -1) {
                out.write(temp);
            }
        }
        in.close();
        out.close();
    }

    /**
     * OutputStreramWriter 和InputStreamReader类
     *
     * 整个IO类中除了字节流和字符流还包括字节和字符转换流。
     *
     *      OutputStreramWriter将输出的字符流转化为字节流
     *      InputStreamReader将输入的字节流转换为字符流
     */

    /**
     * 将字节输出流转化为字符输出流
     *
     * @throws Exception
     */
    @Test
    public void TestStreamTranlation() throws Exception {
        String fileName = "d:" + File.separator + "hello.txt";
        File file = new File(fileName);

        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
        outputStreamWriter.write("hello java io");
        outputStreamWriter.close();
    }

    /**
     * 将字节输入流变为字符输入流
     *
     * @throws Exception
     */
    @Test
    public void TestFileTranslation() throws Exception {
        String fileName = "d:" + File.separator + "hello.txt";
        File file = new File(fileName);

        Reader reader = new InputStreamReader(new FileInputStream(file));
        char[] chars = new char[1024];
        int len = reader.read(chars);
        System.out.println(new String(chars, 0, len));
        reader.close();

    }

    /**
     * ByteArrayInputStream 主要将内容写入内存中
     * ByteArrayOutputStream 主要将内容从内存输出
     */

    /**
     * 使用内存操作流将一个大写字母转化为小写字母
     */
    @Test
    public void TestMemoryOperation() throws Exception {
        String str = "Memory Operation";
        ByteArrayInputStream input = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int temp = 0;
        while ((temp = input.read()) != -1) {
            char ch = (char) temp;
            output.write(Character.toLowerCase(ch));
        }
        String outStr = output.toString();
        input.close();
        output.close();
        System.out.println(outStr);

    }

    /**
     * 管道流
     * 管道流主要可以进行两个线程之间的通信。
     *      PipedOutputStream 管道输出流
     *      PipedInputStream 管道输入流
     */

    /**
     * 消息发送类
     */
    class Send implements Runnable {
        private PipedOutputStream out = null;

        public Send() {
            out = new PipedOutputStream();
        }

        public PipedOutputStream getOut() {
            return this.out;
        }

        public void run() {
            String message = "Hello,Pipe~";
            try {
                out.write(message.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 接受消息类
     */
    class Recive implements Runnable {
        private PipedInputStream input = null;

        public Recive() {
            this.input = new PipedInputStream();
        }

        public PipedInputStream getInput() {
            return this.input;
        }

        public void run() {
            byte[] b = new byte[1000];
            int len = 0;
            try {
                len = this.input.read(b);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("接受的内容为 " + (new String(b, 0, len)));
        }
    }

    /**
     * 验证管道流
     *
     * @throws IOException
     */
    @Test
    public void TestPipedSteam() throws IOException {
        Send send = new Send();
        Recive recive = new Recive();

        try {
            //管道连接
            send.getOut().connect(recive.getInput());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(send).start();
        new Thread(recive).start();
    }

    /**
     * 使用PrintStream输出
     *
     * @throws IOException
     */
    @Test
    public void TestPrintStream() throws IOException {
        PrintStream print = new PrintStream(new FileOutputStream(new File("d:"
                + File.separator + "hello.txt")));
        print.println(true);
        print.println("Rollen");
        print.close();
    }

    /**
     * OutputStream 在屏幕上输出内容
     */
    @Test
    public void TestPrintScreen() {
        OutputStream out = System.out;
        try {
            out.write("hello".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入输出重定向
     */
    @Test
    public void TestRedirect() {
        // 此刻直接输出到屏幕
        System.out.println("hello");
        File file = new File("d:" + File.separator + "hello.txt");
        try {
            System.setOut(new PrintStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("这些内容在文件中才能看到哦！");
    }

    @Test
    public void TestErrorRedirect() {
        // 此刻直接输出到屏幕
        File file = new File("d:" + File.separator + "hello.txt");
        System.err.println("这些在控制台输出");
        try {
            System.setErr(new PrintStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.err.println("这些在文件中才能看到哦！");
    }


    @Test
    public void TestSystemInRedirect() {
        File file = new File("d:" + File.separator + "hello.txt");
        if (!file.exists()) {
            return;
        } else {
            try {
                System.setIn(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[1024];
            int len = 0;
            try {
                len = System.in.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("读入的内容为：" + new String(bytes, 0, len));
        }
    }


    /**
     * BufferedReader只能接受字符流的缓冲区，
     * 因为每一个中文需要占据两个字节，所以需要将System.in这个字节输入流变为字符输入流，采用：
     * <p>
     * BufferedReader buf = new BufferedReader(
     * new InputStreamReader(System.in));
     */

    @Test
    public void TestBufferedReader() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        System.out.println("请输入内容：");

        try {
            bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("你输入的内容为-->" + str);
    }

    /**
     * Scanner类处理输入数据
     */
    @Test
    public void TestUserScannerHanddleData() {
        Scanner scanner = new Scanner(System.in);
        int temp = scanner.nextInt();
        System.out.println(temp);
    }

    /**
     * 从Scanner类中读取文件数据
     */

    @Test
    public void TestScannerReadFile() {
        File file = new File("d:" + File.separator + "hello.txt");
        Scanner sca = null;
        try {
            sca = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str = sca.next();
        System.out.println("从文件中读取的内容是：" + str);

    }

    /**
     * 数据操作流DataOutputStream、DataInputStream类
     */

    @Test
    public void TestDataOutputStream() throws IOException {
        File file = new File("D:" + File.separator + "hello.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        char[] chars = {'A', 'B', 'C', 'D'};
        for (char temp : chars) {
            dataOutputStream.writeChar(temp);
        }

        dataOutputStream.close();
    }

    @Test
    public void TestDataInputStream() throws Exception {
        File file = new File("D:" + File.separator + "hello.txt");
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        char[] chars = new char[1024];
        int count = 0;
        char temp;
        while ((temp = dataInputStream.readChar()) != 'D') {
            chars[count++] = temp;
        }

        System.out.println(chars);
    }

    /**
     * 合并流
     */
    @Test
    public void TestSequenceInputStream() throws Exception {
        File file1 = new File("d:" + File.separator + "hello1.txt");
        File file2 = new File("d:" + File.separator + "hello2.txt");
        File file3 = new File("d:" + File.separator + "hello.txt");
        InputStream input1 = new FileInputStream(file1);
        InputStream input2 = new FileInputStream(file2);
        OutputStream output = new FileOutputStream(file3);
        // 合并流
        SequenceInputStream sis = new SequenceInputStream(input1, input2);
        int temp = 0;
        while ((temp = sis.read()) != -1) {
            output.write(temp);
        }
        input1.close();
        input2.close();
        output.close();
        sis.close();
    }

    /**
     * 文件压缩 ZipOutputStream类
     */

    @Test
    public void TestZipOutputStream() throws Exception {
        File file = new File("d:" + File.separator + "hello.txt");
        File zipFile = new File("d:" + File.separator + "hello.zip");
        InputStream input = new FileInputStream(file);
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
                zipFile));
        zipOut.putNextEntry(new ZipEntry(file.getName()));
        // 设置注释
        zipOut.setComment("hello");
        int temp = 0;
        while ((temp = input.read()) != -1) {
            zipOut.write(temp);
        }
        input.close();
        zipOut.close();
    }

    /**
     * 一次压缩多个文件
     */
    public void TestMutZip() throws Exception {
        // 要被压缩的文件夹
        File file = new File("d:" + File.separator + "temp");
        File zipFile = new File("d:" + File.separator + "zipFile.zip");
        InputStream input = null;
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
                zipFile));
        zipOut.setComment("hello");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; ++i) {
                input = new FileInputStream(files[i]);
                zipOut.putNextEntry(new ZipEntry(file.getName()
                        + File.separator + files[i].getName()));
                int temp = 0;
                while ((temp = input.read()) != -1) {
                    zipOut.write(temp);
                }
                input.close();
            }
        }
        zipOut.close();
    }

    /**
     * 解压
     */
    @Test
    public void TestUnZip() throws Exception {
        File file = new File("d:" + File.separator + "hello.zip");
        ZipFile zipFile = new ZipFile(file);
        System.out.println("压缩文件的名称为：" + zipFile.getName());
    }

    /**
     * 解压单个压缩文件（也就是压缩文件中只有一个文件的情况），
     */
    @Test
    public void TestUnzipSingleFile() throws IOException {
        File file = new File("d:" + File.separator + "hello.zip");
        File outFile = new File("d:" + File.separator + "unZipFile.txt");
        ZipFile zipFile = new ZipFile(file);
        ZipEntry entry = zipFile.getEntry("hello.txt");
        InputStream input = zipFile.getInputStream(entry);
        OutputStream output = new FileOutputStream(outFile);
        int temp = 0;
        while ((temp = input.read()) != -1) {
            output.write(temp);
        }
        input.close();
        output.close();

    }

    /**
     * 需要解压缩多个文件的时候，ZipEntry就无法使用了，
     * <p>
     * 如果想操作更加复杂的压缩文件，我们就必须使用ZipInputStream类
     */

    @Test
    public void TestMultipleZipFile() throws IOException {
        File file = new File("d:" + File.separator + "zipFile.zip");
        File outFile = null;
        ZipFile zipFile = new ZipFile(file);
        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
        ZipEntry entry = null;
        InputStream input = null;
        OutputStream output = null;
        while ((entry = zipInput.getNextEntry()) != null) {
            System.out.println("解压缩" + entry.getName() + "文件");
            outFile = new File("d:" + File.separator + entry.getName());
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdir();
            }
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            input = zipFile.getInputStream(entry);
            output = new FileOutputStream(outFile);
            int temp = 0;
            while ((temp = input.read()) != -1) {
                output.write(temp);
            }
            input.close();
            output.close();
        }
    }

    /**
     * 回退流操作
     */
    @Test
    public void TestPushBackInputStream() throws IOException {

        String str = "hello,rollenholt";
        PushbackInputStream push = null;
        ByteArrayInputStream bat = null;
        bat = new ByteArrayInputStream(str.getBytes());
        push = new PushbackInputStream(bat);
        int temp = 0;
        while ((temp = push.read()) != -1) {
            if (temp == ',') {
                push.unread(temp);
                temp = push.read();
                System.out.print("(回退" + (char) temp + ") ");
            } else {
                System.out.print((char) temp);
            }

        }
    }

    /**
     * 获取编码方式
     */
    @Test
    public void getCodeEncode(){
        System.out.println("系统默认编码方式为："+System.getProperty("file.encoding"));
    }




}
