package com.test.http;

import java.io.Serializable;

public class ResonseEnty<T> implements Serializable{



	/**
	 * status : 0
	 * errcode : 10001
	 * msg : success
	 */

	private int status;
	private int errcode;
	private String msg;
	T    data;


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public  boolean isSuccess(){
		return   getStatus()==1;
	}

}
