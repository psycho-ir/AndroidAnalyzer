package com.sarabadani.android.analyzer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AggregatedCalls extends ArrayList<AggregatedCall> {
    private static AggregatedCalls instance = new AggregatedCalls();

    public static AggregatedCalls getInstance(){
        return instance;
    }

    public AggregatedCalls(){
        super();
    }
	public Boolean has(String number) {
		for (Call c : this) {
			if (c.getNumber().equals(number)) {
				return true;
			}
		}
		return false;
	}

	public AggregatedCall find(String number) {
		for (AggregatedCall c : this) {
			if (c.getNumber().equals(number)) {
				return c;
			}
		}
		return null;
	}

	public void aggregate(List<Call> calls) {
		for (Call c : calls) {
			if (this.has(c.getNumber())) {
				AggregatedCall aggregatedCall = this.find(c.getNumber());
				aggregatedCall.increaseDuration(c.getDuration());
				aggregatedCall.increaseNumberOfCalls(c);
			} else {
				this.add(new AggregatedCall(c));
			}
		}
		calculatePercentage();
		Collections.sort(this, new Comparator<AggregatedCall>() {

			@Override
			public int compare(AggregatedCall lhs, AggregatedCall rhs) {
				 if (lhs.getDuration() == rhs.getDuration()) return 0;
	                if (lhs.getDuration() < rhs.getDuration()) return 1;
	                else return -1;
			}
		});

	}

	private void calculatePercentage() {
		int sum = calculateTotalDuration();
		for (AggregatedCall call : this) {

			call.setPercentage((double) Math.round((((double) call.getDuration() / sum) * 100)));
		}
	}

	public int calculateTotalDuration() {
		int sum = 0;
		for (Call c : this) {
			sum += c.getDuration();
		}

		return sum;
	}

    public int getNumberOfIncommingCalls(){
        int sum = 0;
        for (AggregatedCall c : this) {
            sum += c.getNumberOfIncomingCalls();
        }

        return sum;
    }

    public int getNumberOfOutgoingCalls(){
        int sum = 0;
        for (AggregatedCall c : this) {
            sum += c.getNumberOfOutgoingCalls();
        }

        return sum;
    }

    public int getNumberOfMissedCalls(){
        int sum = 0;
        for (AggregatedCall c : this) {
            sum += c.getNumberOfMissedCalls();
        }
        return sum;
    }
  }
