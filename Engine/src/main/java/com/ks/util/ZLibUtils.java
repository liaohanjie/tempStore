package com.ks.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * ZLib压缩工具
 * @author ks
 */
public abstract class ZLibUtils {
	/**
	 * 压缩
	 * @param data 数据
	 * @param off 开始位置
	 * @param len 长度
	 * @return 压缩后的数据
	 */
	public static byte[] compress(byte[] data,int off,int len){
		byte[] output = new byte[0];

		Deflater compresser = new Deflater();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			compresser.reset();
			compresser.setInput(data,off,len);
			compresser.finish();
			byte[] buf = new byte[256];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			compresser.end();
		}
		return output;
	}
	
	/**
	 * 压缩
	 * 
	 * @param data
	 *            待压缩数据
	 * @return byte[] 压缩后的数据
	 */
	public static byte[] compress(byte[] data) {
		return compress(data, 0, data.length);
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
			throw new RuntimeException(e);
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			decompresser.end();
		}

		return output;
	}
}

