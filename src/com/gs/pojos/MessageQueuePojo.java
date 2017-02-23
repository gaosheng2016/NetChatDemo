package com.gs.pojos;

import java.util.LinkedList;

/**
 * 消息队列Pojo类
 * 
 * @author sheng
 *
 */
public class MessageQueuePojo {
	public MessageQueuePojo() {
		this.messageList = new LinkedList<String>();
	}

	private LinkedList<String> messageList;

	public LinkedList<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(LinkedList<String> messageList) {
		this.messageList = messageList;
	}

	public void addMessage(String oneMessage) {
		synchronized (this) {
			this.messageList.add(oneMessage);
			this.notifyAll();
		}
	}

	public String popMessage() {
		String message = null;
		synchronized (this) {
			message = this.messageList.poll();
			while (message == null) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				message = this.messageList.poll();
			}
			return message;
		}
	}
}
