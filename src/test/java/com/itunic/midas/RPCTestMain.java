package com.itunic.midas;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.itunic.midas.rmi.RMI;

public class RPCTestMain {
	static AtomicInteger ins = new AtomicInteger();

	public static void main(String[] args) {

		// RPCServer ps = new RPCServer(1111);

		ThreadPoolExecutor ex = new ThreadPoolExecutor(8, 8, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		long start = System.currentTimeMillis();
		int len = 10000;
		for (int i = 0; i < len; i++) {
			ex.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					RPCTest t = RMI.rpc(RPCTest.class);
					ins.addAndGet(1);
					int is =ins.get();
					System.out.println(t.hello111(String.valueOf(is)) + "---" + is);
				}
			});
			/*
			 * RPCTest t = RMI.rpc(RPCTest.class); ins.addAndGet(1);
			 * System.out.println(t.hello111(String.valueOf(ins.get()))+"---"+
			 * ins.get());
			 */

		}

		while (true) {
			if (ins.get() == len) {
				long end = System.currentTimeMillis();
				long result = end - start;
				System.out.printf("并发%s数 请求数 %s，最终消耗时间:%s \n", 4,len,result / 1000);
				break;
			}
		}

	}
}
