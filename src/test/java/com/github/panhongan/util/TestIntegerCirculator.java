package com.github.panhongan.util;

import com.github.panhongan.util.IntegerCirculator;

public class TestIntegerCirculator {
	public static void main(String[] args) {
		int queue_index = 0;
		int inner_queue_num = 3;
		
		long begin_time = System.currentTimeMillis();
		for (long i = 0; i < 100000000 + 4; ++i) {
			queue_index = (++queue_index % inner_queue_num);
		}
		long end_time = System.currentTimeMillis();
		System.out.println("divide cost time :" + (end_time - begin_time));
		System.out.println(queue_index);

		
		queue_index = 0;
		try {
			IntegerCirculator cir = new IntegerCirculator(inner_queue_num);

			begin_time = System.currentTimeMillis();
			for (long i = 0; i < 100000000 + 4; ++i) {
				cir.increment();
				if (++queue_index == inner_queue_num) {
					queue_index = 0;
				}
			}
			
			end_time = System.currentTimeMillis();
			System.out.println("add cost time :" + (end_time - begin_time));
			System.out.println(cir.get() + ", " + queue_index);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
