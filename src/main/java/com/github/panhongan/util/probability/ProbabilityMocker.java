package com.github.panhongan.util.probability;

public class ProbabilityMocker {

	private int percent = 0; // (0-100)

	private int step = 0;

	private int count = 0;

	public ProbabilityMocker(int percent) throws Exception {
		if (percent <= 0 || percent > 100) {
			throw new Exception("invalid percentage, should be at (0-100)");
		}

		this.setPercent(percent);
	}

	public void setPercent(int percent) {
		this.percent = percent;
		this.step = 100 / percent;
	}

	public int getPercent() {
		return percent;
	}
	
	public int getStep() {
		return step;
	}

	public boolean hit() {
		if (++count == step) {
			count = 0;
			return true;
		}

		return false;
	}

}

