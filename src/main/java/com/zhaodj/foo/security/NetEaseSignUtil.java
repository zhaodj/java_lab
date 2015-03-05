package com.zhaodj.foo.security;

/**
 * NeteaseSignUtil
 * Version 1.0
 * Copyright 2006-2007.
 * All Rights Reserved.
 * <p>本程序是个签名的工具类。
 * 用于各个产品和网银系统传递数据时使用。
 * 包含的功能包括产生需要的公私钥；产生签名和验证签名的过程。
 * @author Shaoqing Fu
 * 
 */

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class NetEaseSignUtil {
	
	private static String priKey_Hex_Str = "30820276020100300d06092a864886f70d0101010500048202603082025c020100028181009f6b8f0a2bedfa83167ed04a1f1f1bb66b9e396eea8000a5e28976e2b88d3cccc3bd6f86ded6f427de52ab59a1c35b67167694f6bde8cd8a0d2ac1528bc34b7d58612c8a576ced1bb61f6dcfc3a394f73f9bec417c0ffd124fe3479ad077197fbc5679bd8af3648839eca13ce7aaaf238bb715d727a6252580b635e08f1d3ab502030100010281807ebfbba870a8f3460a3aa54c695608688f43eb91fe2beb57f8b726a8b7e6c37262265967b800db7a5f4f07216a75b254520dd1fe239bd6df6dfc925c18f71b2ab9e41a3e10d5972b748bd016bd50b71330d98a74498f8facde664698f90ba279d0e8ba8402d55f7e069706b05dd9c203e78bea8bfac05d729eb1e61b3a14b339024100d3b83b7ce4b5434d897f01c20a57afcd0bc7e66137737dadf8963b80594fd71d97cdaf16ef1d9d14b6a606269fb532df03f79fbb82dd208981726f5e4a7ec66b024100c0c3232cbd30fa02c8e7a88a5f01c3d5c95e4f35918a70e2f0bffa822c0f23769809a9feb6841a4edc67457999d45f3e34533258f8d38cf62e3b276b1b610b5f024100bd8e1114bcdfdd262a985fc68ee3fb2e26020fadb2abe036467e9b3f4ab744ede5dfed872706e88087171622b692d0bd031c4d12e11381f21ac78e1669b41f89024018c63bb41950bd21347f099b57209bf1d322aadd80aa215e1981230a2ded782a5ada0b1e308903f494c1351daed6abb30bf89164fbfdfc84fb6620e7d42a01d302405e6735a4677f44ef4fad64d0e47356ebbece8194c42f8b3ad53789a35dace0446d3aa8c6aa91e5c8bac2966823fe77ed5e5281046b90f288772f29f194d2abc6";
	private static String pubKey_Hex_Str = "30819f300d06092a864886f70d010101050003818d00308189028181009f6b8f0a2bedfa83167ed04a1f1f1bb66b9e396eea8000a5e28976e2b88d3cccc3bd6f86ded6f427de52ab59a1c35b67167694f6bde8cd8a0d2ac1528bc34b7d58612c8a576ced1bb61f6dcfc3a394f73f9bec417c0ffd124fe3479ad077197fbc5679bd8af3648839eca13ce7aaaf238bb715d727a6252580b635e08f1d3ab50203010001";

	public static void main(String[] args)
	{
		try
		{
			//产生公私钥
			NetEaseSignUtil.genRSAKeyPair();

			//签名验签
			String srcToVeryfy = URLEncoder.encode("urstestc@163.com1赵国强赵国强20101227105117201012271131591203.86.63.98203.86.63.9854416","UTF-8");
			//System.out.println("srcToVeryfy=="+srcToVeryfy);
			//String sign = "6a8def299fd13dcaa82a7a1abc6c969ed2bdce283b47965afd5cb20caa08fbe8c71d076f73960f35164483021246e4878323e990911ea6ead62393d148cbbffbe4317de64fa4c8029ed3bf8a46babd7d05e4271d5a710cdd21a3a3db6cd8e42d62498a5ea50c0fe9b5bd1dfcf72ae44822a3454f8f92e190980d18108be10bdc";
			//	System.out.println("签名为：" + sign);
			
			String sign = NetEaseSignUtil.generateSHA1withRSASigature(priKey_Hex_Str, srcToVeryfy);
			System.out.println("原始串：" + srcToVeryfy);
			System.out.println("签名：" + sign);
			boolean result = NetEaseSignUtil.verifySHA1withRSASigature(pubKey_Hex_Str, sign, srcToVeryfy);
			System.out.println("验证签名结果为：" + result);
		
			//加密
			String srcToEncode = "123_~8*Hell赵刚媉";
			String encoded = NetEaseSignUtil.encode(srcToEncode, pubKey_Hex_Str);
			//解密
			NetEaseSignUtil.decode(encoded, priKey_Hex_Str);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 本方法使用SHA1withRSA签名算法产生签名
	 * @param String priKey 签名时使用的私钥(16进制编码)
	 * @param String src	签名的原字符串
	 * @return String 		签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
	 */
	public static String generateSHA1withRSASigature(String priKey, String src)
	{
		try
		{

			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pribyte = hexStrToBytes(priKey.trim());

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");

			RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes());

			byte[] signature = sigEng.sign();
			return bytesToHexStr(signature);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//LogMan.log("[NeteaseSignUtil][generateSHA1withRSASigature]"+e);
			return null;
		}
	}

	/**
	 * 本方法使用SHA1withRSA签名算法验证签名
	 * @param String pubKey 验证签名时使用的公钥(16进制编码)
	 * @param String sign 	签名结果(16进制编码)
	 * @param String src	签名的原字符串
	 * @return String 		签名的返回结果(16进制编码)
	 */
	public static boolean verifySHA1withRSASigature(String pubKey, String sign, String src)
	{
		try
		{
			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pubbyte = hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);

			sigEng.initVerify(rsaPubKey);
			sigEng.update(src.getBytes());

			byte[] sign1 = hexStrToBytes(sign);
			return sigEng.verify(sign1);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			//LogMan.log("[NeteaseSignUtil][verifySHA1withRSASigature]"+e);
			return false;
		}
	}

	/**
	 * 本方法用于产生1024位RSA公私钥对。
	 * 
	 */
	public static void genRSAKeyPair()
	{
		KeyPairGenerator rsaKeyGen = null;
		KeyPair rsaKeyPair = null;
		try
		{
			System.out.println("Generating a pair of RSA key ... ");
			rsaKeyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			random.setSeed(("" + System.currentTimeMillis() * Math.random() * Math.random()).getBytes());
			rsaKeyGen.initialize(1024, random);
			rsaKeyPair = rsaKeyGen.genKeyPair();
			PublicKey rsaPublic = rsaKeyPair.getPublic();
			PrivateKey rsaPrivate = rsaKeyPair.getPrivate();
			System.out.println("公钥：" + bytesToHexStr(rsaPublic.getEncoded()));
			System.out.println("私钥：" + bytesToHexStr(rsaPrivate.getEncoded()));
			System.out.println("1024-bit RSA key GENERATED.");
		}
		catch (Exception e)
		{
			System.out.println("genRSAKeyPair：" + e);
		}
	}

	/**
	 * 公钥加密
	 * @param srcToEncode
	 * @param pubKey
	 * @return
	 */
	public static String encode(String srcToEncode, String pubKey)
	{

		try
		{
			System.out.println("需要加密的明文：" + srcToEncode);
			byte[] pubbyte = hexStrToBytes(pubKey.trim());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);

			BigInteger e = rsaPubKey.getPublicExponent();
			BigInteger n = rsaPubKey.getModulus();
			System.out.println("加密的E：" + e);
			System.out.println("加密的n：" + n);
			//获得明文 ming
			byte[] plainText = srcToEncode.getBytes("UTF-8");
			BigInteger ming = new BigInteger(plainText);
			//计算密文 coded
			BigInteger coded = ming.modPow(e, n);
			System.out.println("密文：" + coded);
			return coded.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 私钥解密
	 * @param encodedSrc
	 * @param prikey
	 * @return
	 */
	public static String decode(String encodedSrc,String priKey)
	{
		try
		{
			byte[] pribyte = hexStrToBytes(priKey.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
			
			//获得私钥参数
			BigInteger n = privateKey.getModulus();
			BigInteger d = privateKey.getPrivateExponent();
			System.out.println("解密n为："+n);
			System.out.println("解密d为："+d);
			//密文
			BigInteger coded = new BigInteger(encodedSrc);
			BigInteger m =coded.modPow(d, n);
			//打印解密结果
			byte[] result = m.toByteArray();
			String str = new String(result,"UTF-8");
			System.out.println("明文解密结果为："+str);
			
			return str;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//LogMan.log("[NeteaseSignUtil][generateSHA1withRSASigature]"+e);
			return null;
		}
	}
	
	/**
	 * 将字节数组转换为16进制字符串的形式.
	 */
	private static final String bytesToHexStr(byte[] bcd)
	{
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++)
		{
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * 将16进制字符串还原为字节数组.
	 */
	private static final byte[] hexStrToBytes(String s)
	{
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

	private static final char[] bcdLookup =
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

}
