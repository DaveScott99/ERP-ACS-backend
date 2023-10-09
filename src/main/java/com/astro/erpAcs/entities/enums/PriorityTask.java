package com.astro.erpAcs.entities.enums;

public enum PriorityTask {

	LOW(1),
	MEDIUM(2), 
	HIGH(3);
	
	private int code;
	
	private PriorityTask(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static PriorityTask valueOf(int code) {
		
		for (PriorityTask value : PriorityTask.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Invalid PriorityTask code");
		
	}
	
}
