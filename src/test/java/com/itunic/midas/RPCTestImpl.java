package com.itunic.midas;


import com.itunic.midas.io.core.annotation.RPCService;

@RPCService(RPCTest.class)
public class RPCTestImpl implements RPCTest {

	@Override
	public String hello111(String abc) {
		System.out.println("Netty RPC SUCCESS");
		return "port:20880 HAHAHAH-~!"+abc;
	}

}
