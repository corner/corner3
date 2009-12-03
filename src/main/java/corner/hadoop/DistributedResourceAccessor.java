package corner.hadoop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 针对分布式文件进行处理
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.2
 */
public interface DistributedResourceAccessor {
	/**
	 * 从Hadoop中得到文件
	 * 
	 * @param filePath
	 *            要得到的文件路径
	 * @param out
	 *            输出IO流
	 * @throws IOException
	 *             发生IO操作异常
	 */
	public abstract void getFile(String filePath, OutputStream out)
			throws IOException;

	/**
	 * 把文件上传到Hadoop服务器中
	 * 
	 * @param filePath
	 *            文件路径
	 * @param is
	 *            IO输入流
	 * @return 是否上传成功
	 * @throws IOException
	 *             发生IO操作异常
	 */
	public abstract void putFile(String filePath, InputStream is)
			throws IOException;

	/**
	 * 将文件从hdfs中删除
	 * 
	 * @param filePath
	 * @return true,删除成功;false,删除失败
	 * @throws IOException
	 *             发生IO操作异常
	 * @since 0.0.2
	 */
	public abstract boolean deleteFile(String filePath) throws IOException;

	/**
	 * 获取文件的存储协议
	 * 
	 * @return
	 * @since 0.0.2
	 */
	public String getProtocol();

	/**
	 * 获取文件的修改时间
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @since 0.0.2
	 */
	public long getFileMTTime(String filePath) throws IOException;

	/**
	 * 获取path下面的文件列表
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @since 0.0.2
	 */
	public List<FileDesc> list(String path) throws IOException;

	/**
	 * 
	 * @param dirPath
	 * @return
	 * @since 0.0.2
	 */
	public boolean mkdirs(String dirPath) throws IOException;

	/**
	 * 检查文件是否存在
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @since 0.0.2
	 */
	public boolean isFileExist(String filePath) throws IOException;

	/**
	 * 取得文件的描述信息
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @since 0.0.2
	 */
	public FileDesc getFileDesc(String filePath) throws IOException;
}