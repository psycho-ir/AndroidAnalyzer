package com.sarabadani.android.analyzer.model;

import android.provider.CallLog;

import java.util.ArrayList;
import java.util.List;

public class AggregatedCall extends Call {

    private Double percentage = 0d;

    private int numberOffCalls = 0;
    private int numberOfMissedCalls = 0;
    private int numberOfIncomingCalls = 0;
    private int numberOfOutgoingCalls = 0;

    public List<Call> getAllCalls() {
        return allCalls;
    }

    private final List<Call> allCalls;


    public AggregatedCall(Call call) {
        super(call.getNumber(), call.getName(), call.getDuration(), call.getDate(), call.getCallType());
        this.allCalls = new ArrayList<Call>();
        this.increaseNumberOfCalls(call);
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }


    public int getNumberOffCalls() {
        return numberOffCalls;
    }

    public void increaseNumberOfCalls(Call c) {
        this.allCalls.add(c);
        this.numberOffCalls++;
        switch (c.getCallType()) {
            case CallLog.Calls.INCOMING_TYPE:
                this.numberOfIncomingCalls++;
                break;
            case CallLog.Calls.MISSED_TYPE:
                this.numberOfMissedCalls++;
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                this.numberOfOutgoingCalls++;
                break;
            default:
                break;
        }
    }

    public int getNumberOfMissedCalls() {
        return numberOfMissedCalls;
    }

    public int getNumberOfIncomingCalls() {
        return numberOfIncomingCalls;
    }

    public int getNumberOfOutgoingCalls() {
        return numberOfOutgoingCalls;
    }
}
