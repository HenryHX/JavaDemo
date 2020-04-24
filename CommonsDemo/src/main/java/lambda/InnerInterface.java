package lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Administrator
 * 2020-04-24
 */

public class InnerInterface {
    /**
     * 打印user信息
     */
    public void print(User user, Consumer<User> userConsumer) {
        userConsumer.accept(user);
    }

    /**
     * 打印user信息
     */
    public void printAndThen(User user, Consumer<User> userConsumerA, Consumer<User> userConsumerB) {
        Consumer<User> userConsumerC = userConsumerA.andThen(userConsumerB);
        userConsumerC.accept(user);
    }

    /**
     * 返回一个user
     */
    public User getUser(Supplier<User> userSupplier) {
        return userSupplier.get();
    }

    /**
     * 转换一个user
     */
    public User transformUser(User user, Function<User, User> function) {
        return function.apply(user);
    }

    /**
     * 检验User是否合法
     */
    public boolean checkUser(User user, Predicate<User> predicate) {
        return predicate.test(user);
    }

    public static void main(String[] args) {

        User userObj = new User();
        userObj.setUsername("西门吹雪");
        userObj.setAge(22);

        // 测试Consumer
        InnerInterface mainInst = new InnerInterface();
        mainInst.print(userObj, user -> System.out.println(user));
        mainInst.printAndThen(userObj, user -> System.out.println("1:" + user), user -> System.out.println("2:" + user));

        // 测试Supplier
        final User user1 = mainInst.getUser(() -> {
            User user = new User();
            user.setUsername("叶孤城");
            user.setAge(22);
            return user;
        });
        System.out.println(user1);

        // 将西门吹雪的年龄改为25
        final User user2 = mainInst.transformUser(userObj, (user -> {
            user.setAge(25);
            return user;
        }));
        System.out.println(user2);

        // 判断User是否是西门吹雪
        final boolean checkUser = mainInst.checkUser(userObj, (user -> user.getUsername().equals("西门吹雪")));
        System.out.println(checkUser);
    }
}
