package com.jw.call;

import java.util.concurrent.Callable;

/**
 * 
 *
 */
public class WePayCallable implements Callable<WePayModel> {

	private WePayModel wePayModel;

	public WePayCallable(WePayModel wePayModel) {
		this.wePayModel = wePayModel;
	}

	@Override
	public WePayModel call() throws Exception {
		return wePayModel;
	}

	public WePayModel getWePayModel() {
		return wePayModel;
	}

	public void setWePayModel(WePayModel wePayModel) {
		this.wePayModel = wePayModel;
	}
}
