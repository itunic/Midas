package com.itunic.midas.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.itunic.midas.io.core.ResponseDispatcher;
import com.itunic.midas.io.core.ThreadPoolExecutorFactory;
import com.itunic.midas.io.core.annotation.RPCService;
import com.itunic.midas.spring.beans.ProtocolXMLBean;

@Service
public class ResponseApplicationContextAware implements ApplicationContextAware, InitializingBean {
	private static final ConcurrentHashMap<String, Object> serviceMap = new ConcurrentHashMap<String, Object>();
	private static ProtocolXMLBean bean = null;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		bean = ctx.getBean(ProtocolXMLBean.class);
		Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RPCService.class);
		if (MapUtils.isNotEmpty(serviceBeanMap)) {
			for (Object serviceBean : serviceBeanMap.values()) {
				String interfaceName = serviceBean.getClass().getAnnotation(RPCService.class).value().getName();
				//serviceMap.put(interfaceName, serviceBean.getClass());
				serviceMap.put(interfaceName, serviceBean);
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ResponseDispatcher dispatch = new ResponseDispatcher(bean.getPort(), bean.getThreads(),
				bean.getSerialization());
		dispatch.register(serviceMap);
		ThreadPoolExecutorFactory.getThreadPoolExecutor().submit(dispatch);
	}

}
