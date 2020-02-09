package selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws Exception {
        /** 实例化一个选择器对象 **/
        Selector selector = Selector.open();
        Selector selector2 = Selector.open();

        /** 创建服务器套接字通道 ServerSocketChannel **/
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        /** 绑定监听 InetSocketAddress **/
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        /** 设置为非阻塞IO模型 **/
        serverSocketChannel.configureBlocking(false);

        Object object = new Object();
        /** 将serverSocketChannel通道注册到selector选择器中，并设置感兴趣的事件OP_ACCEPT**/
        SelectionKey register1 = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,object);
        SelectionKey register2 = serverSocketChannel.register(selector2, SelectionKey.OP_ACCEPT,object);

        /** 获取通道SelectionKey对象中的通道 **/
        System.out.println(register1.channel().equals(serverSocketChannel));
        /** 获取通道SelectionKey对象绑定到选择器中的附件对象**/
        System.out.println(register1.attachment().equals(object));
        /** 获取通道SelectionKey对象中选择器 **/
        System.out.println(register1.selector().equals(selector));
        /** 获取通道SelectionKey对象中感兴趣的事件 **/
        System.out.println((register1.interestOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT);

        boolean is_Run = true;
        while (is_Run) {

            /** 阻塞等待事件到达**/
            selector.select();
            selector2.select();


            /** 获取到达事件SelectionKey集合**/
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Set<SelectionKey> selectionKeys2 = selector2.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            Iterator<SelectionKey> iterator2 = selectionKeys2.iterator();

            /** 遍历SelectionKey**/
            if (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    /** 从SelectionKey获取对应通道ServerSocketChannel**/
                    serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel accept = serverSocketChannel.accept();
                    /** 获取通道SelectionKey对象中就绪的事件 **/
                    System.out.println((register1.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT);
                }
            }

            if (iterator2.hasNext()) {
                SelectionKey key = iterator2.next();
                iterator2.remove();
                if (key.isAcceptable()) {
                    /** 从SelectionKey获取对应通道ServerSocketChannel**/
                    serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel accept = serverSocketChannel.accept();
                    /** 获取通道SelectionKey对象中就绪的事件 **/
                    System.out.println((register2.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT);
                }
            }
        }
    }
}
