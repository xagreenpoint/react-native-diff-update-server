package cn.leadeon.common.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.security.MessageDigest;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件操作工具
 * 
 * @author linxuchao
 * @version [版本号, 2017-10-19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileTools {

	private static final int BUFFEREDSIZE = 1024;
	
	public static String tempPathName;
	
	static {
        SysConfiguration cfg = new SysConfiguration();
        // 获取可删除临时存储目录
        tempPathName = cfg.getValue("WEB_TEMP");
    }

	/**
	 * 截取附件名称
	 * 
	 * @param fileName
	 *            /test/test.txt 截取成 test.txt
	 * @return
	 */
	public static String sbuFileName(String fileName) {
		String sbuFileName = "";
		if (!"".equals(fileName)) {
			sbuFileName = fileName.substring(fileName.lastIndexOf("/") + 1,
					fileName.length());
		}
		return sbuFileName;
	}

	/**
	 * 截取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String subFileExtName(String fileName) {
		String extName = "";
		if (!"".equals(fileName)) {
			extName = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
		}
		return extName;
	}

	/**
	 * 创建目录
	 * 
	 * @param dirName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static File makeDir(String dirName) {
		File file = new File(dirName);
		try {
			if (!file.exists()) {
				if (!file.exists()) {
					file.mkdirs();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建目录【" + dirName + "】失败!");
		}
		return file;
	}

	/**
	 * 文件转换
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(File f) throws IOException {
		if (!f.exists()) {
			throw new FileNotFoundException(f.getName());
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOUtils.close(in);
			IOUtils.close(bos);
		}
	}

	static char hexdigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * md5文件生成摘要
	 * 
	 * @param file
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String MD5File(File file) {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			// 32位加密
			byte[] b = md.digest();
			return byteToHexString(b);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			IOUtils.close(fis);
		}
	}

	/**
	 * @param tmp
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		// 表示转换结果中对应的字符位置
		int k = 0;
		for (int i = 0; i < 16; i++) {
			// 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			// 取第 i 个字节
			byte byte0 = tmp[i];
			// 取字节中高 4 位的数字转换,
			str[k++] = hexdigits[byte0 >>> 4 & 0xf];
			// >>> 为逻辑右移，将符号位一起右移
			// 取字节中低 4 位的数字转换
			str[k++] = hexdigits[byte0 & 0xf];
		}
		// 换后的结果转换为字符串
		s = new String(str);
		return s;
	}

	/**
	 * 解压zip压缩包
	 * 
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void UnZIP(File file, String tempPath) throws IOException {
		// 创建压缩文件对象
		ZipFile zf = new ZipFile(file);
		// 获取压缩文件中的文件枚举
		Enumeration<ZipEntry> en = zf.getEntries();
		int length = 0;
		byte[] b = new byte[2048];

		// 提取压缩文件夹中的所有压缩实例对象
		while (en.hasMoreElements()) {
			ZipEntry ze = en.nextElement();
			// logger.debug("压缩文件夹中的内容："+ze.getName());
			// logger.debug("是否是文件夹："+ze.isDirectory());
			// 创建解压缩后的文件实例对象
			File f = new File(tempPath
					+ File.separator
					+ file.getName().substring(0,
							file.getName().lastIndexOf(".")) + File.separator
					+ ze.getName());
			// logger.debug("解压后的内容："+f.getPath());
			// logger.debug("是否是文件夹："+f.isDirectory());
			// 如果当前压缩文件中的实例对象是文件夹就在解压缩后的文件夹中创建该文件夹
			if (ze.isDirectory()) {
				f.mkdirs();
			} else {
				// 如果当前解压缩文件的父级文件夹没有创建的话，则创建好父级文件夹
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}

				// 将当前文件的内容写入解压后的文件夹中。
				OutputStream outputStream = new FileOutputStream(f);
				InputStream inputStream = zf.getInputStream(ze);
				while ((length = inputStream.read(b)) > 0) {
					outputStream.write(b, 0, length);
				}

				inputStream.close();
				outputStream.close();
			}
		}
		zf.close();
	}

	/**
	 * 压缩zip格式的压缩文件
	 * 
	 * @param inputFilename
	 *            压缩的文件或文件夹及详细路径
	 * @param zipFilename
	 *            输出文件名称及详细路径
	 * @throws IOException
	 */
	public synchronized void zip(String inputFilename, String zipFilename)
			throws IOException {
		zip(new File(inputFilename), zipFilename);
	}

	/**
	 * 压缩zip格式的压缩文件
	 * 
	 * @param inputFile
	 *            需压缩文件
	 * @param zipFilename
	 *            输出文件及详细路径
	 * @throws IOException
	 */
	public synchronized void zip(File inputFile, String zipFilename)
			throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFilename));
		try {
			zip(inputFile, out, "");
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
	}

	/**
	 * 压缩zip格式的压缩文件
	 * 
	 * @param inputFile
	 *            需压缩文件
	 * @param out
	 *            输出压缩文件
	 * @param base
	 *            结束标识
	 * @throws IOException
	 */
	private synchronized void zip(File inputFile, ZipOutputStream out,
			String base) throws IOException {
		if (inputFile.isDirectory()) {
			File[] inputFiles = inputFile.listFiles();
			if(base.length()>0){
				out.putNextEntry(new ZipEntry(base + "/"));
			}
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < inputFiles.length; i++) {
				zip(inputFiles[i], out, base + inputFiles[i].getName());
			}
		} else {
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base));
			} else {
				out.putNextEntry(new ZipEntry(inputFile.getName()));
			}
			FileInputStream in = new FileInputStream(inputFile);
			try {
				int c;
				byte[] by = new byte[FileTools.BUFFEREDSIZE];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				in.close();
			}
		}
	}
	
	public static String getTempPath(HttpServletRequest request){
		  // 获取程序部署目录
        String path = request.getSession().getServletContext().getRealPath("/");
        // 截取临时文件目录
        String tempPath = path.substring(0, path.indexOf("webapps")) + "webapps"
                + File.separator + tempPathName + File.separator;
        File file = new File(tempPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return tempPath;
	}
	
	public static void doDeleteEmptyDir(String dir) {
        (new File(dir)).delete();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
