package com.dreamail.mercury.receiver.attachment.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.junit.Test;

public class DeflaterCompressionTest {
	/**
	 * TODO 数据压缩比测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public final static void main(String[] args) throws Exception {
		String inputString = "blahblahblah??sdfsdfsdfsdfsdfsdfdbhkfsdjfsjdhbsdjfbsdjksbhfjsdbfjksdbfjsd";
		byte[] input = inputString.getBytes("UTF-8");
		System.out.println("数据包原始大小=" + input.length);
		// Compress the bytes 开始压缩数据,
		byte[] output = new byte[100];
		Deflater compresser = new Deflater();
		compresser.setInput(input); // 要压缩的数据包
		compresser.finish(); // 完成
		int compressedDataLength = compresser.deflate(output); // 压缩，返回的是数据包经过缩缩后的大小
		System.out.println("数据包经过缩缩后的大小=" + compressedDataLength);
		// String out = new String(output,0,compressedDataLength,"UTF-8");
		// System.out.println(out);
		// compresser.end();

		// Decompress the bytes // 开始解压
		Inflater decompresser = new Inflater();
		decompresser.setInput(output, 0, compressedDataLength);
		// 对byte[]进行解压，同时可以要解压的数据包中的某一段数据，就好像从zip中解压出某一个文件一样。
		byte[] result = new byte[100];
		int resultLength = decompresser.inflate(result); // 返回的是解压后的的数据包大小，
		decompresser.end();
		System.out.println("解压后的的数据包大小=" + resultLength);
		// Decode the bytes into a String
		String outputString = new String(result, 0, resultLength, "UTF-8");
		System.out.println(outputString);
	}

	/**
	 * 压缩
	 * 
	 * @param data
	 *            待压缩数据
	 * @return byte[] 压缩后的数据
	 */
	public static byte[] compress(byte[] data) {
		byte[] output = new byte[0];

		Deflater compresser = new Deflater();

		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		compresser.end();
		return output;
	}

	/**
	 * 解压缩
	 * 
	 * @param data
	 *            待压缩的数据
	 * @return byte[] 解压缩后的数据
	 */
	public static byte[] decompress(byte[] data) {
		byte[] output = new byte[0];

		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		decompresser.end();
		return output;
	}
	
	
	@Test 
    public final void test() {
        String inputStr = "snowolf@zlex.org;dongliang@zlex.org;zlex.dongliang@zlex.org";  
        System.err.println("输入字符串:\t" + inputStr);  
        byte[] input = inputStr.getBytes();  
        System.err.println("输入字节长度:\t" + input.length);  
 
        byte[] data = compress(input);  
        System.err.println("压缩后字节长度:\t" + data.length);  
 
        byte[] output = decompress(data);  
        System.err.println("解压缩后字节长度:\t" + output.length);  
        String outputStr = new String(output);  
        System.err.println("输出字符串:\t" + outputStr);  
        
        assertEquals(inputStr, outputStr);  
    }  
	
}
