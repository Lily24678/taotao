package com.itheima.fastDfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;

/**
 * @Description: 图片上传测试
 * @author nq nianqiang@itcast.cn
 */
public class FastDFSTest {

	@Test
	public void testFastDFS() throws Exception {
		// 1、加载配置文件，配置文件中的内容就是tracker服务的地址。
		// 配置文件内容：tracker_server=192.168.25.133:22122
		String conf = "D:/develop/work/workspace-taotao/taotao-manager-web/src/main/resources/conf/fast_dfs.conf";
		ClientGlobal.init(conf);
		// 2、创建一个TrackerClient对象。直接new一个。
		TrackerClient client = new TrackerClient();
		// 3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
		TrackerServer trackerServer = client.getConnection();
		// 4、创建一个StorageServer的引用，值为null
		StorageServer storageServer = null;
		// 5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 6、使用StorageClient对象上传图片。 
		String[] strings = storageClient.upload_appender_file("C:/Users/nianqiang/Desktop/a.png", "png", null);
		// 7、返回数组。包含组名和图片的路径。
		for (String string : strings) {
			System.out.println(string);
		}

	}
	/**
	 * @Description: 工具类测试
	 * @param
	 * @return void
	 */
	@Test
	public void uploadPicture() throws Exception {
		String conf = "D:/develop/work/workspace-taotao/taotao-manager-web/src/main/resources/conf/fast_dfs.conf";
		FastDFSClient client = new FastDFSClient(conf);
		String file = client.uploadFile("C:/Users/nianqiang/Desktop/a.png", "png");
		System.out.println(file);
	}

//	public static void main(String[] args) throws Exception{
//		FdfsConnectionPool pool = new FdfsConnectionPool();
//		List<String> trackers = new ArrayList<>();
//		trackers.add("192.168.25.133:22122");
//		
//		TrackerConnectionManager tcm = new TrackerConnectionManager(pool, trackers);
//		TrackerClient trackerClient = new DefaultTrackerClient(tcm);
//		
//		ConnectionManager cm = new ConnectionManager(pool);
//		FastFileStorageClient storageClient = new DefaultFastFileStorageClient(trackerClient, cm);
		// 参数1：文件的byte数组，参数二：文件的扩展名
//		File file = new File("‪C:/Users/nianqiang/Desktop/a.png");
//		storageClient.uploadFile(FileUtils.readFileToByteArray(file), "png");
//	}

}
