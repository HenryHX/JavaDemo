package selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
    public static void main(String[] args)  throws Exception {
        /** 实例化一个选择器对象 **/
        Selector selector = Selector.open();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8888));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        boolean is_Run = true;
        while (is_Run) {
            /** 阻塞等待事件到达**/
            selector.select();

            /** 获取到达事件SelectionKey集合**/
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            /** 遍历SelectionKey**/
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isConnectable()) {
                    socketChannel = (SocketChannel) key.channel();
                    while (!socketChannel.finishConnect()){
                    }
                }
            }
        }
    }
}
