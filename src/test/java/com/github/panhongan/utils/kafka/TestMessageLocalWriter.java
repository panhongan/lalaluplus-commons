package com.github.panhongan.utils.kafka;

/**
 * lalalu plus
 */
public class TestMessageLocalWriter {

	public static void main(String[] args) {
		MessageLocalWriter local_writer = new MessageLocalWriter("./data", 10);

		for (int i = 0; i < 120; ++i) {
			local_writer.processMessage("aa", 1, new Integer(i).toString());
			try {
				Thread.sleep(1 * 1000);
			} catch (Exception e) {

			}
		}
	}

}
