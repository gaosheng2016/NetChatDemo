package com.gs.manager;

import com.gs.pojos.MessageQueuePojo;

/**
 * 消息队列管理器
 * @author sheng
 *
 */
public class MessageQueueManager {
	private MessageQueuePojo messageQueuePojo;
	
	public MessageQueueManager() {
		this.messageQueuePojo = new MessageQueuePojo();
	}
	
	public String getOneMessage() {
		return this.messageQueuePojo.popMessage();
	}
	
	public void addOneMessage(String message) {
		this.messageQueuePojo.addMessage(message);
	}
}
