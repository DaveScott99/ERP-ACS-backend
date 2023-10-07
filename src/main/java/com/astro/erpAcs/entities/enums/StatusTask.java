package com.astro.erpAcs.entities.enums;

public enum StatusTask {

	NOT_STARTED(1),
	IN_PROGRESS(2), 
	COMPLETED(3);
	
	private int code;
	
	private StatusTask(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static StatusTask valueOf(int code) {
		
		for (StatusTask value : StatusTask.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid StatusTask code");
		
	}
}
