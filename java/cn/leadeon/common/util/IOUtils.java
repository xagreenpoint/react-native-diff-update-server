package cn.leadeon.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * 
 * IO 工具集合
 * @author linxuhcao
 * @version [版本号, 2016年11月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IOUtils {

	/**
	 * InputStream关闭
	 * 
	 * @param in
	 * @see [类、类#方法、类#成员]
	 */
	public static void close(InputStream in) {
		if (null != in) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * OutputStream 关闭
	 * 
	 * @param out
	 * @see [类、类#方法、类#成员]
	 */
	public static void close(OutputStream out) {
		if (null != out) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reader 关闭
	 * 
	 * @param reader
	 * @see [类、类#方法、类#成员]
	 */
	public static void close(Reader reader) {
		if (null != reader) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writer 关闭
	 * 
	 * @param writer
	 * @see [类、类#方法、类#成员]
	 */
	public static void close(Writer writer) {
		if (null != writer) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
