package goal.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(PreferenceID.class)
@Table(name="GOAL_PREF")
public class Preference {

	@Id
	@ManyToOne
	private User user;
	@Id
	@ManyToOne
	private Word word;
	@Column(name="PREF_VALUE", nullable=false)
	private float value;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
}
