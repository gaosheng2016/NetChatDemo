package com.gs.impl.thread;

import java.io.IOException;
import java.util.List;

import com.gs.manager.MessageQueueManager;
import com.gs.utils.StaticValue;

/**
 * 守护线程，负责打印统计信息，报告信息等
 * 
 * @author sheng
 *
 */
public class DaemonThread implements Runnable {

	private List<ServerClientSocketRunnable> serverClientList;
	// 待向所有客户端发送消息的消息队列管理器
	private MessageQueueManager messageQueueManager;
	private boolean isRunning = true;

	public DaemonThread(List<ServerClientSocketRunnable> serverClientList, MessageQueueManager messageQueueManager) {
		this.serverClientList = serverClientList;
		this.messageQueueManager = messageQueueManager;
		this.isRunning = true;
	}

	@Override
	public void run() {
		String message = null;
		while (isRunning) {
			message = messageQueueManager.getOneMessage();
			for (ServerClientSocketRunnable serverClientSocketRunnable : serverClientList) {
				serverClientSocketRunnable.writeToUserClient(message + StaticValue.separate_next_line);
			}
//			System.out.println("demon by server to client---" + message);
		}
	}

}
