package goal.model.bean;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class Result implements Comparable<Result> {
	
	private String title;
	private String summarize;
	private String location;
	@Transient
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
	
	public boolean sameDocumentAs(Result otherDoc){
		if(this.getClass().equals(otherDoc.getClass()))
			if(this.location.equals(otherDoc.location))
				return true;
		return false;
	}
	
}
