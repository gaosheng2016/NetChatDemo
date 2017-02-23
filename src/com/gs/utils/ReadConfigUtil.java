package com.gs.utils;

/**
 * 读取配置文件的工具类
 * @author sheng
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class ReadConfigUtil {
	private Properties config = null;

	public ReadConfigUtil(String configFile) {
		InputStream in = ReadConfigUtil.class.getClassLoader().getResourceAsStream("application.properties");
		config = new Properties();
		try {
			Reader reader = new InputStreamReader(in, StaticValue.default_encoding);
			config.load(reader);
			in.close();
		} catch (IOException e) {
			System.out.println("No application.properties defined error");
		}
	}

	// 根据key读取value
	public String getValue(String key) {
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
			return null;
		}
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * }
	 */

}
