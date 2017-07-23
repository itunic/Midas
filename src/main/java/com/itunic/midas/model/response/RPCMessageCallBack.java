package com.itunic.midas.model.response;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 *  RPC消息发起并回调实现类
 * @ClassName RPCMessageCallBack
 * @author yinbin
 * @Date 2017年7月21日 下午2:56:41
 * @version 1.0.0
 */
public class RPCMessageCallBack extends AbstractMessageCallBack<RPCResponseMessageModel> {
	private RPCResponseMessageModel response = null;
	private Lock lock = new ReentrantLock();
	private Condition over = lock.newCondition();

	@Override
	public Object start() {
		try {
			lock.lock();
			/**
			 * 判断是否有返回值，在线程超过休眠时间则返回null，如果被唤醒，则代表有返回值。
			 */
			if (null == response)
				over.await(10 * 1000, TimeUnit.MILLISECONDS);
			return response.getResult();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void finish(RPCResponseMessageModel response) {
		try {
			lock.lock();
			this.response = response;
			over.signal();// 唤醒休眠线程
		} finally {
			lock.unlock();
		}
	}

}
