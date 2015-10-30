package goal.model.bean;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GOAL_DATAWAREHOUSE")
@AttributeOverrides({
	@AttributeOverride(name="title", column=@Column(name="DATAWAREHOUSE_TITLE", length=200, nullable=false)),
	@AttributeOverride(name="summarize", column=@Column(name="DATAWAREHOUSE_SUMMARIZE", nullable=false)),
	@AttributeOverride(name="location", column=@Column(name="DATAWAREHOUSE_LOCATION", length=200, nullable=false))
})
public class DwhResult extends Result {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DATAWAREHOUSE_ID")
	private long id;
	
}
