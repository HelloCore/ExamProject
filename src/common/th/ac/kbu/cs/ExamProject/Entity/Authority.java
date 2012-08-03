package th.ac.kbu.cs.ExamProject.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TYPE")
public class Authority implements Serializable {

	private static final long serialVersionUID = -9207463823741433366L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "TYPE")
	private Integer type;
	
	@Column(name = "AUTHORITY",length=45)
	private String authority;

	@Column(name = "TYPE_DESC",length=50)
	private String typeDesc;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
}
