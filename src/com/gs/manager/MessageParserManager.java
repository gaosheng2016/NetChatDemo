package com.gs.manager;

import com.gs.iface.parser.IMessageParser;
import com.gs.impl.parser.MessageParserImpl;

/**
 * 消息解析器
 * 
 * @author sheng
 *
 */
public class MessageParserManager {
	private IMessageParser iMessageParser;

	public MessageParserManager() {
		this.iMessageParser = new MessageParserImpl();
	}

	public String parser(String content) {
		return this.iMessageParser.parser(content);
	}
}
