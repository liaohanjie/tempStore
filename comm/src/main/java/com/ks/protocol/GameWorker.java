package com.ks.protocol;

import com.ks.protocol.codec.message.obj.MessageObject;

public class GameWorker implements Runnable {
	private Subpackage subpackage;
	private MessageObject obj;
	
	public GameWorker(Subpackage subpackage, MessageObject obj) {
		super();
		this.subpackage = subpackage;
		this.obj = obj;
	}

	@Override
	public void run() {
		subpackage.process(obj);
	}

}
