package com.itunic.midas;

import com.itunic.midas.rmi.RMI;

public class RPCTestMain {
	public static void main(String[] args) {
		
		for (int i = 0; i < 100; i++) {
			RPCTest t = RMI.rpc(RPCTest.class);
			System.out.println(t.hello111("222"));
		}
		//RPCServer ps = new RPCServer(1111);
	}
}
