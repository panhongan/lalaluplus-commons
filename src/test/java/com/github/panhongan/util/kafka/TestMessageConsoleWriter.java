package com.github.panhongan.util.kafka;

import com.github.panhongan.util.kafka.MessageConsoleWriter;

public class TestMessageConsoleWriter {

	public static void main(String [] args) {
		MessageConsoleWriter writer = new MessageConsoleWriter();
		writer.processMessage("test", 0, "abc");
	}
}
