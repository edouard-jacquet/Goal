package goal.model.bean;

public class Result implements Comparable<Result> {
	
	private String title;
	private String summarize;
	private String location;
	private double score;
	
	public int compareTo(Result result) {
		if(this.getScore() == result.getScore()) {
			return 0;
		}
		else if(this.getScore() < result.getScore()) {
			return -1;
		}
		else if(this.getScore() > result.getScore()) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummarize() {
		return summarize;
	}
	public void setSummarize(String summarize) {
		this.summarize = summarize;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
