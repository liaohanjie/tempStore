package com.living.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


/**
 * IO处理
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月30日
 */
public class IoUtil {

	/**
	 * 读取流到 buffer 数组中
	 * @param input
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public static int read(InputStream input, byte[] buffer) throws IOException {
        int length = buffer.length;
        int remaining = length;
        
        while (remaining > 0) {
            int location = length - remaining;
            int count = input.read(buffer, location, remaining);
            if (-1 == count) {
                break;
            }
            remaining -= count;
        }
        return length - remaining;
    }
    
	/**
	 * 读取流到并返回String字符串
	 * @param input
	 * @param length
	 * @param charset
	 * @return
	 * @throws IOException
	 */
    public static String read(InputStream input, int length, Charset charset) throws IOException {
        byte[] buffer = new byte[length];
        int index = read(input, buffer);
        if (index == 0 || index == -1) {
            return "";
        }
        if (charset == null) {
            charset = Charset.forName("UTF-8");
        }
        
        return new String(buffer, charset);
    }
    
    /**
     * 读取流到并返回 utf-8 String字符串
     * @param input
     * @param length
     * @return
     * @throws IOException
     */
    public static String read(InputStream input, int length) throws IOException {
        return read(input, length, null);
    }
    
    /**
     * 读取字符串，没有读取到返回空字符串
     * @param input
     * @param charset
     * @return
     * @throws IOException
     */
    public static String read(InputStream input, Charset charset) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int size = 512;
        byte[] buffer = new byte[size];
        
        int length = 0;
        try {
            while(true) {
                int count = input.read(buffer, 0, size);
                if (-1 == count) {
                    break;
                }
                output.write(buffer, 0, count);
                length = length + count;
            }
            
            if (length == 0) {
            	return "";
            }
            
            if (charset == null) {
                charset = Charset.forName("UTF-8");
            }
            
            return new String(output.toByteArray(), charset);
        } finally {
            output.close();
        }
    }
    
    /**
     * 以 utf-8 读取字符串
     * @param input
     * @return
     * @throws IOException
     */
    public static String read(InputStream input) throws IOException {
        Charset charset = null;
        return read(input, charset);
    }
}
