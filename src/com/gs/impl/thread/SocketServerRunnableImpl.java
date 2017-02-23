package com.gs.impl.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import com.gs.manager.MessageQueueManager;
import com.gs.utils.StaticValue;

public class SocketServerRunnableImpl implements Runnable {
	private String nickName;
	private ServerSocket serverSocket;
	private MessageQueueManager messageQueueManager;

	private boolean isRunning = true;
	private List<ServerClientSocketRunnable> serverClientList;

	public SocketServerRunnableImpl(String nickName, ServerSocket serverSocket) {
		this.nickName = nickName;
		this.serverSocket = serverSocket;
		this.isRunning = true;
		messageQueueManager = new MessageQueueManager();
		serverClientList = new LinkedList<ServerClientSocketRunnable>();
		
		//在socket server处启动守护线程
		DaemonThread daemonThread = new DaemonThread(serverClientList, messageQueueManager);
		new Thread(daemonThread).start();
		
		//开启管理员向user client发送消息
		AdminWriteMessageRunnable adminWriteMessageRunnable = new AdminWriteMessageRunnable(messageQueueManager);
		new Thread(adminWriteMessageRunnable).start();
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	// 实现server socket的主要处理逻辑
	@Override
	public void run() {
		while (isRunning) {
			Socket client_socket = null;
			try {
				client_socket = serverSocket.accept();
				// 即server client线程
				ServerClientSocketRunnable clientSocketRunnable = new ServerClientSocketRunnable(null, client_socket,
						this.messageQueueManager);
				this.serverClientList.add(clientSocketRunnable);
				new Thread(clientSocketRunnable).start();
//				System.out.println("one client is online!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

/**
 * 管理员要写给各客户端的Runnable类
 * 
 * @author sheng
 *
 */

class AdminWriteMessageRunnable implements Runnable {
	private MessageQueueManager messageQueueManager;
	private BufferedReader bufferReader;
	private boolean isRunning = true;

	public AdminWriteMessageRunnable(MessageQueueManager messageQueueManager) {
		this.messageQueueManager = messageQueueManager;
		this.isRunning = true;

		try {
			this.bufferReader = new BufferedReader(new InputStreamReader(System.in, StaticValue.default_encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String temp_line = null;
		while (isRunning) {
			try {
				temp_line = this.bufferReader.readLine();
				this.messageQueueManager.addOneMessage(temp_line);
//				System.out.println("admin by server to client---" + temp_line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
