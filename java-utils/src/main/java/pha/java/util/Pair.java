package pha.java.util;

public class Pair<First, Second> {

	public First first;
	
	public Second second;
	
	public Pair(First first, Second second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString() {
		return "Pair(" + first + "," + second + ")";
	}
	
}
