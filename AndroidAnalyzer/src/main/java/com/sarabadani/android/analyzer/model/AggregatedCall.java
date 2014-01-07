package com.sarabadani.android.analyzer.model;

public class AggregatedCall extends Call {

	private Double percentage = 0d;

	private int numberOffCalls = 0;

	public AggregatedCall(Call call) {
		super(call.getNumber(), call.getName(), call.getDuration(), call.getDate());
		this.numberOffCalls = 1;
		// TODO Auto-generated constructor stub
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public void increaseNumberOfCalls() {
		this.numberOffCalls++;
	}

	public int getNumberOffCalls() {
		return numberOffCalls;
	}

}
