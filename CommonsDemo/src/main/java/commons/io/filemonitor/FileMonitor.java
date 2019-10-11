package commons.io.filemonitor;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator
 * 2019-10-11
 */

public class FileMonitor {
    @PostConstruct
    public void initFileMonitor() {
        // 监控目录
        String rootDir = "D:\\data";
        // 轮询间隔 5 秒
        long interval = TimeUnit.SECONDS.toMillis(5L);
        // 创建一个文件观察器用于处理文件的格式
        // 创建过滤器
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files       = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".txt"));
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        // 使用过滤器
        FileAlterationObserver observer = new FileAlterationObserver(rootDir, filter, IOCase.INSENSITIVE);
        // 不使用过滤器
        //FileAlterationObserver observer = new FileAlterationObserver(rootDir);

        observer.addListener(new FileListener()); //设置文件变化监听器
        //创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileMonitor fileMonitor = new FileMonitor();
        fileMonitor.initFileMonitor();
    }
}
