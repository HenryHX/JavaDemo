import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by Administrator
 * 2019-03-21
 */

public class FdfsTest {

    @Test
    public void testUpload() throws Exception {

        ClientGlobal.init("client.conf");

        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();

        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        String[] strings = storageClient.upload_file("D:\\JavaWorkSpace\\JavaDemo\\FdfsDemo\\target\\test-classes\\test.jpg", "jpg", null);

        for (String string : strings) {
            System.out.println(string);
        }
    }

}
