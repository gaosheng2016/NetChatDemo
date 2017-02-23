package com.gs.controler;

import java.net.ServerSocket;
import java.net.Socket;

import com.gs.impl.thread.SocketServerRunnableImpl;
import com.gs.impl.thread.UserClientSocketRunnable;
import com.gs.utils.SystemParas;

/**
 * 系统启动器
 * 
 * @author sheng
 *
 */
public class SystemControler {
	public static void main(String[] args) throws Exception {
		if (SystemParas.is_node_master) {// 说明是服务节点
			ServerSocket serverSocket = new ServerSocket(SystemParas.server_socket_port,
					SystemParas.server_socket_wait_accept_max_pool);
			SocketServerRunnableImpl serverSocketRunnable = new SocketServerRunnableImpl(SystemParas.nick_name,
					serverSocket);

			Thread serverThread = new Thread(serverSocketRunnable);
			serverThread.start();

			System.out.println("socket server have started");
		} else {// 说明是socket client节点
			Socket clientSocket = new Socket(SystemParas.server_socket_ip, SystemParas.server_socket_port);

			UserClientSocketRunnable userClientSocketRunnable = new UserClientSocketRunnable(SystemParas.nick_name,
					clientSocket);

			Thread clientThread = new Thread(userClientSocketRunnable);
			clientThread.start();

			System.out.println("user socket client have started");
		}

	}
}
