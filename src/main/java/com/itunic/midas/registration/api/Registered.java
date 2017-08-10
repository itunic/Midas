package com.itunic.midas.registration.api;

import com.itunic.midas.registration.vo.ConnectionVo;

public interface Registered {
	/**
	 * 
	 * 注册
	 * 
	 * @author yinbin
	 * @Date 2017年8月10日 上午10:54:52
	 * @version 1.0.0
	 * @param vo
	 */
	public void registered(ConnectionVo vo);

	/**
	 * 
	 * 重新注册
	 * @author yinbin
	 * @Date 2017年8月10日 上午10:56:39
	 * @version 1.0.0
	 * @param vo
	 */
	public void reRegistered(ConnectionVo vo);
}
