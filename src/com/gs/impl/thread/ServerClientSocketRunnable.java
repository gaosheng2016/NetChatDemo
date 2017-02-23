package com.gs.impl.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.gs.manager.MessageParserManager;
import com.gs.manager.MessageQueueManager;
import com.gs.utils.StaticValue;

/**
 * server client runnable
 * 
 * @author sheng
 *
 */
public class ServerClientSocketRunnable implements Runnable {
	private Socket clientSocket;
	private String nickName;
	private BufferedWriter bufferWriter;

	public BufferedWriter getBufferWriter() {
		return bufferWriter;
	}

	public void setBufferWriter(BufferedWriter bufferWriter) {
		this.bufferWriter = bufferWriter;
	}

	public BufferedReader getBufferReader() {
		return bufferReader;
	}

	public void setBufferReader(BufferedReader bufferReader) {
		this.bufferReader = bufferReader;
	}

	private BufferedReader bufferReader;
	private MessageParserManager messageParserManager;
	private boolean isRunning = true;
	private MessageQueueManager messageQueueManager;

	public ServerClientSocketRunnable(String nickName, Socket clientSocket, MessageQueueManager messageQueueManager) {
		this.clientSocket = clientSocket;
		this.nickName = nickName;
		this.messageParserManager = new MessageParserManager();
		this.isRunning = true;
		this.messageQueueManager = messageQueueManager;

		try {
			this.bufferReader = new BufferedReader(
					new InputStreamReader(this.clientSocket.getInputStream(), StaticValue.default_encoding));
			this.bufferWriter = new BufferedWriter(
					new OutputStreamWriter(this.clientSocket.getOutputStream(), StaticValue.default_encoding));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 封装的由server client向user client发送消息的方法
	public void writeToUserClient(String message) {
		try {
			this.bufferWriter.write(message);
			this.bufferWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String temp_line = null;
		while (isRunning) {
			try {
				temp_line = this.bufferReader.readLine();
				messageQueueManager.addOneMessage(temp_line);

				System.out.println("server from client message---" + temp_line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
