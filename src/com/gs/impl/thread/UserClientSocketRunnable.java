package com.gs.impl.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.gs.manager.MessageParserManager;
import com.gs.utils.StaticValue;

public class UserClientSocketRunnable implements Runnable{
	private Socket clientSocket;
	private String nickName;
	private BufferedWriter bufferWriter;
	private BufferedReader bufferReader;
	private BufferedReader consoleBufferReader;
	private MessageParserManager messageParserManager;
	private boolean isRunning = true;
	
	public UserClientSocketRunnable(String nickName, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.nickName = nickName;
		this.messageParserManager = new MessageParserManager();
		this.isRunning = true;
		
		try {
			this.bufferReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream(), StaticValue.default_encoding));
			this.consoleBufferReader = new BufferedReader(new InputStreamReader(System.in, StaticValue.default_encoding));
			this.bufferWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream(), StaticValue.default_encoding));
			
			//开启从服务器端读取消息线程
			ReadSocketServerRunnable ReadSocketServerRunnable = new ReadSocketServerRunnable(this.bufferReader);
			new Thread(ReadSocketServerRunnable).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String temp_line = null;
		while(isRunning) {
			try {
				temp_line = this.consoleBufferReader.readLine();
				this.bufferWriter.write(temp_line + StaticValue.separate_next_line);
				this.bufferWriter.flush();
				System.out.println("client to server----" + temp_line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ReadSocketServerRunnable implements Runnable {
		
		private BufferedReader bufferReader;
		private boolean isRunning = true;
		
		public ReadSocketServerRunnable(BufferedReader bufferReader) {
			this.bufferReader = bufferReader;
			this.isRunning = true;
		}

		@Override
		public void run() {
			String temp_line = null;
			while (isRunning) {
				try {
					temp_line = this.bufferReader.readLine();
					System.out.println("server to client----" + temp_line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}