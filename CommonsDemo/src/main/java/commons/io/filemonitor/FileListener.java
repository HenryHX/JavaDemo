package commons.io.filemonitor;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.Date;

/**
 * Created by Administrator
 * 2019-10-11
 */

/**
 * 文件变化监听器
 *
 * 在Apache的Commons-IO中有关于文件的监控功能的代码. 文件监控的原理如下：
 * 由文件监控类FileAlterationMonitor中的线程不停的扫描文件观察器FileAlterationObserver，
 * 如果有文件的变化，则根据相关的文件比较器，判断文件时新增，还是删除，还是更改。（默认为1000毫秒执行一次扫描）
 *
 */
public class FileListener extends FileAlterationListenerAdaptor {

    /**
     * @description 启动监听
     */
    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println(new Date() + " :启动监听器。");
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println(new Date() + " :有新文件夹生成：" + directory.getName());
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println(new Date() + " :有文件夹内容发生变化：" + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println(new Date() + " :有文件夹被删除：" + directory.getName());
    }

    /**
     * @description 文件创建
     */
    @Override
    public void onFileCreate(File file) {
        System.out.println(new Date() + " :有新文件生成：" + file.getName());
    }

    /**
     * @description 文件内容发生变化
     */
    @Override
    public void onFileChange(File file) {
        System.out.println(new Date() + " :有文件被修改：" + file.getName());
    }

    /**
     * @description 文件被删除
     */
    @Override
    public void onFileDelete(File file) {
        System.out.println(new Date() + " :有文件被删除：" + file.getName());
    }

    /**
     * @description 监听停止
     */
    @Override
    public void onStop(FileAlterationObserver observer) {
        System.out.println(new Date() + " : 停止监听器。");
    }
}
