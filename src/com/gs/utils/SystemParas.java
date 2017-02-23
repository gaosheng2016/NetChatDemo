package com.gs.utils;

/**
 * 系统参数配置工具类
 * 
 * @author sheng
 *
 */
public class SystemParas {
	// 初始化配置文件读取类
	public static ReadConfigUtil configUtil = new ReadConfigUtil("application.properties");

	// 读取出各配置项，以备任何该项目中的类使用
	public static boolean is_node_master = Boolean.parseBoolean(configUtil.getValue("node_master"));
	public static String nick_name = configUtil.getValue("nick_name");
	public static int max_connection_client_number = Integer.parseInt(configUtil.getValue("max_connection_client_number"));
	public static int server_socket_port = Integer.parseInt(configUtil.getValue("server_socket_port"));
	public static int server_socket_wait_accept_max_pool = Integer
			.parseInt(configUtil.getValue("server_socket_wait_accept_max_pool"));
	public static String server_socket_ip = configUtil.getValue("server_socket_ip");

	public static void main(String[] args) {
		System.out.println(configUtil.getValue("node_master"));
		System.out.println(configUtil.getValue("nick_name"));
		System.out.println(configUtil.getValue("max_connection_client_number"));
		System.out.println(configUtil.getValue("server_socket_port"));
		System.out.println(configUtil.getValue("server_socket_wait_accept_max_pool"));
		System.out.println(configUtil.getValue("server_socket_ip"));
	}
}
