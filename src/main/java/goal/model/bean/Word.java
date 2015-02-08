package goal.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GOAL_WORD")
public class Word {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="WORD_ID")
	private long id;
	@Column(name="USER_CAPTION", length=100, nullable=false)
	private String caption;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}

	
}
