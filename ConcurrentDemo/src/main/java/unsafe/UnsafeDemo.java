package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by Administrator
 * 2019-06-24
 */

public class UnsafeDemo {

    public static void main(String[] args) throws Exception {
        unsafeAllocateMemory();
        testUnsafeCAS();
        testArray();
        testObject();
        testStringIntern();
    }

    /**
     * 通过java反射机制，跳过了安全检测，拿到了一个unsafe类的实例。
     *
     * @return unsafe类的实例
     * @throws Exception
     */
    public static Unsafe getUnsafeInstance() throws Exception {
        Field unsafeStaticField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeStaticField.setAccessible(true);
        return (Unsafe) unsafeStaticField.get(Unsafe.class);
    }

    /**
     * unsafe 读写数组元素
     *
     * @throws Exception
     */
    public static void testArray() throws Exception {
        Unsafe u = getUnsafeInstance();

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        //返回当前系统数组第一个元素地址相对于数组起始地址的偏移值，数组中还有其他元素占用部分地址，在本例中返回16。
        int b = u.arrayBaseOffset(int[].class);
        //返回数组一个元素占用的字节数,在本例中返回4。
        int s = u.arrayIndexScale(int[].class);
        //获取数组对象obj的起始地址，加上偏移值，得到对应元素的地址，将int val写入内存。
        u.putInt(arr, (long) b + s * 9, 1);

        for (int i = 0; i < 10; i++) {
            //获取数组对象obj的起始地址，加上偏移值，得到对应元素的地址，从而获得元素的值。
            int v = u.getInt(arr, (long) b + s * i);
            System.out.println(v);
        }
    }

    /**
     * 修改静态变量和实例变量的值
     *
     * @throws Exception
     */
    public static void testObject() throws Exception {
        //修改实例变量的值
        Unsafe u = getUnsafeInstance();
        //这里使用allocateInstance方法获取了一个Test类的实例，并且没有打印“constructor called”，说明构造方法没有调用。
        TestObject t = (TestObject) u.allocateInstance(TestObject.class);
        //修改实例变量与修改数组的值类似，同样要获取地址偏移值，然后调用putInt方法。
        //objectFieldOffset: 获取对象某个属性的地址偏移值。
        long b1 = u.objectFieldOffset(TestObject.class.getDeclaredField("intField"));

        u.putInt(t, b1, 2);
        System.out.println("intField:" + t.intField);


        //修改静态变量的值
        //通过Unsafe类修改了方法区中的信息。
        Field staticIntField = TestObject.class.getDeclaredField("staticIntField");
        //staticFieldBase: 获取静态变量所属的类在方法区的首地址。可以看到，返回的对象就是TestObject.class。
        Object o = u.staticFieldBase(staticIntField);

        System.out.println(o == TestObject.class);
        //静态变量与实例变量不同之处在于，静态变量位于于方法区中，它的地址偏移值与TestObject类在方法区的地址相关，与TestObject类的实例无关。
        //获取静态变量地址偏移值。
        Long b4 = u.staticFieldOffset(staticIntField);
        //因为是静态变量，传入的Object参数应为class对象
        u.putInt(o, b4, 10);

        System.out.println("staticIntField:" + u.getInt(TestObject.class, b4));
    }


    public static void testStringIntern() throws Exception {
        String s = "abc";
        //保存s的引用
        s.intern();
        //此时s1==s，地址相同
        String s1 = "abc";

        Unsafe u = getUnsafeInstance();

        //获取s的实例变量value
        Field valueInString = String.class.getDeclaredField("value");
        //获取value的变量偏移值
        long offset = u.objectFieldOffset(valueInString);

        //value本身是一个char[],要修改它元素的值，仍要获取baseOffset和indexScale
        long base = u.arrayBaseOffset(char[].class);
        long scale = u.arrayIndexScale(char[].class);

        //获取value
        char[] values = (char[]) u.getObject(s, offset);
        //为value赋值
        u.putChar(values, base + scale, 'c');
        System.out.println("s:" + s + " s1:" + s1);

        //将s的值改为 abc
        s = "abc";
        String s2 = "abc";
        String s3 = "abc";
        //Unsafe类不安全
        //所有值为“abc”的字符串都变成了“acc”
        System.out.println("s:" + s +" s1:" + s1 +" s2:" + s2 +" s3:" + s3);
    }

    /**
     * 在非Java堆中分配内存
     * 使用new关键字分配的内存会在堆中，并且对象的生命周期内，会被垃圾回收器管理。Unsafe类通过allocateMemory(long)方法分配的内存，
     * 不受Integer.MAX_VALUE的限制，并且分配在非堆内存，使用它时，需要非常谨慎，该部分内存需要手动回收，否则会产生内存泄露；
     * 非法的地址访问时，会导致Java虚拟机崩溃。在需要分配大的连续区域、实时编程时，可以使用该方式，java的nio使用了这一方法。
     * @throws Exception
     */
    public static void unsafeAllocateMemory() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        int BYTE = 1;
        long address = unsafe.allocateMemory(BYTE);
        unsafe.putByte(address, (byte) 52);
        byte num = unsafe.getByte(address);
        System.out.println(num);
        unsafe.freeMemory(address);
        num = unsafe.getByte(address);
        System.out.println(num);
    }

    /**
     * 测试Unsafe实现一个自定义原子类
     * @throws Exception
     */
    public static void testUnsafeCAS() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        MyAutomicInteger myAutomicInteger = new MyAutomicInteger(unsafe);
        myAutomicInteger.increment();
        System.out.println(myAutomicInteger.getValue());
        for (int i = 0; i < 5; i++) {
            System.out.println(myAutomicInteger.getAndIncrement());
        }
        System.out.println(myAutomicInteger.getValue());
    }
}

class TestObject {
    public int intField ;

    public static int staticIntField;

    public static int[] arr;

    private TestObject(){
        System.out.println("constructor called");
    }
}

/**
 * 以Unsafe实现一个自定义原子类
 */
class MyAutomicInteger {
    private volatile int value = 0;
    private Unsafe unsafe;
    private long offset;

    public MyAutomicInteger(Unsafe unsafe) throws Exception {
        this.unsafe = unsafe;
        this.offset = unsafe.objectFieldOffset(MyAutomicInteger.class.getDeclaredField("value"));
    }

    public void increment() {
        int oldValue = value;
        for (;;) {
            if (unsafe.compareAndSwapInt(this, offset, oldValue, oldValue + 1)) {
                break;
            }
            oldValue = value;
        }
    }

    public int getAndIncrement() {
        int oldValue = value;
        for (;;) {
            if (unsafe.compareAndSwapInt(this, offset, oldValue, oldValue + 1)) {
                return oldValue;
            }
            oldValue = value;
        }
    }

    public int getValue() {
        return value;
    }
}

