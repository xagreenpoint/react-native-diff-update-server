package cn.leadeon.common.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

import ecuop.security.Base64;



/**
 * RSA加密算法工具类
 * 
 * @author linxuchao
 */
public class RSAUtil {

	private final static Logger LOG = Logger.getLogger(RSAUtil.class.getName());

	// 私钥指数
	private static final String PRIVATE_KEY_EXPONENT = "98534771646870147834544192034956025369";

	// 模
	private static final String KEY_MODULUS = "172053215288437611692003855080317127571";

	// 公钥指数
	private static final String PUBLIC_KEY_EXPONENT = "65537";

	private static final String RSA = "RSA";

	/**
	 * 生成密钥对方法 需要更换密钥时使用
	 * 
	 * @return KeyPair
	 * @throws Exception
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA,
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			// 大小
			final int KEY_SIZE = 128;
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			return keyPair;
		} catch (Exception e) {
			LOG.error("生成密钥对出现异常！--->>>", e);
			throw new RuntimeException("生成密钥对出现异常！", e);
		}
	}

	/**
	 * 获取公钥信息 加密时使用
	 * 
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey() {
		try {
			KeyFactory keyFac = null;
			keyFac = KeyFactory.getInstance(RSA,
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
					KEY_MODULUS), new BigInteger(PUBLIC_KEY_EXPONENT));
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (Exception e) {
			LOG.error("获取公钥出现异常！--->>>", e);
			throw new RuntimeException("获取公钥出现异常！", e);
		}
	}

	/**
	 * 获取私钥信息解密时使用
	 * 
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey() {
		try {
			KeyFactory keyFac = null;
			keyFac = KeyFactory.getInstance(RSA,
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(
					new BigInteger(KEY_MODULUS), new BigInteger(
							PRIVATE_KEY_EXPONENT));
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (Exception e) {
			LOG.error("获取私钥出现异常！--->>>", e);
			throw new RuntimeException("获取私钥出现异常！", e);

		}

	}

	/**
	 * 加密方法
	 * 
	 * @param dataStr
	 *            待加密字符串
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String dataStr) {
		try {
			byte[] data = dataStr.getBytes();
			Cipher cipher = Cipher.getInstance(RSA,
					new org.bouncycastle.jce.provider.BouncyCastleProvider());

			cipher.init(Cipher.ENCRYPT_MODE, generateRSAPublicKey());

			// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			int blockSize = cipher.getBlockSize();

			// 加密块大小为127byte,加密后为128个byte;因此共有2个加密块，
			// 第一个127byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小

			int leavedSize = data.length % blockSize;

			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;

			byte[] raw = new byte[outputSize * blocksSize];

			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize) {

					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				} else {
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				}
				i++;
			}
			return Base64.encode(raw);
		} catch (Exception e) {
			LOG.error("密码加密出现异常！--->>>", e);
			throw new RuntimeException("密码加密出现异常！", e);
		}
	}

	/**
	 * 解密方法
	 * 
	 * @param rawStr
	 *            待解密字符串
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static String decrypt(String rawStr) {
		try {
			byte[] raw = Base64.decode(rawStr);
			Cipher cipher = Cipher.getInstance(RSA,
					new org.bouncycastle.jce.provider.BouncyCastleProvider());

			cipher.init(cipher.DECRYPT_MODE, generateRSAPrivateKey());

			int blockSize = cipher.getBlockSize();

			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);

			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return new String(bout.toByteArray());
		} catch (Exception e) {
			LOG.error("密码解密出现异常！--->>>", e);
			throw new RuntimeException("密码解密出现异常！", e);
		}
	}

	/**
	 * 解析JS加密，将JS中传来的密文转换唱Base64适用的字符串 仅对JSP页面使用
	 * 
	 * @param paramStr
	 * @return Java方法可以解析的密文
	 * @throws Exception
	 */
	public static String decryptStr(String paramStr) {
		StringBuffer sb = new StringBuffer();
		try {
			byte[] raw = new BigInteger(paramStr, 16).toByteArray();
			Cipher cipher = Cipher.getInstance(RSA,
					new org.bouncycastle.jce.provider.BouncyCastleProvider());

			cipher.init(Cipher.DECRYPT_MODE, generateRSAPrivateKey());

			int blockSize = cipher.getBlockSize();

			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);

			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			byte[] de_result = bout.toByteArray();
			sb.append(new String(de_result));
			// 返回解密的字符串
			return encrypt(sb.reverse().toString());
		} catch (Exception e) {
			LOG.error("JSP页面解密出现错误！--->>>", e);
			throw new RuntimeException("JSP页面解密出现错误！", e);
		}
	}

	/**
	 * 内部测试方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String srcRes = RSAUtil.decrypt("ZMoxMFJcab+MtapxnLPGKQ==");
		System.out.println(RSAUtil.encrypt("updatePackage"));
		System.out.println(srcRes);
	}

}
