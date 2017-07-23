package com.itunic.midas.model.response;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 *  RPC��Ϣ���𲢻ص�ʵ����
 * @ClassName RPCMessageCallBack
 * @author yinbin
 * @Date 2017��7��21�� ����2:56:41
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
			 * �ж��Ƿ��з���ֵ�����̳߳�������ʱ���򷵻�null����������ѣ�������з���ֵ��
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
			over.signal();// ���������߳�
		} finally {
			lock.unlock();
		}
	}

}
