package th.ac.kbu.cs.ExamProject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PREFIX_NAME")
public class PrefixName {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PREFIX_NAME_ID")
	private Integer prefixNameId;

	@Column(name = "PREFIX_NAME_TH")
	private String prefixNameTh;

	@Column(name = "PREFIX_NAME_EN")
	private String prefixNameEn;

	public Integer getPrefixNameId() {
		return prefixNameId;
	}

	public void setPrefixNameId(Integer prefixNameId) {
		this.prefixNameId = prefixNameId;
	}

	public String getPrefixNameTh() {
		return prefixNameTh;
	}

	public void setPrefixNameTh(String prefixNameTh) {
		this.prefixNameTh = prefixNameTh;
	}

	public String getPrefixNameEn() {
		return prefixNameEn;
	}

	public void setPrefixNameEn(String prefixNameEn) {
		this.prefixNameEn = prefixNameEn;
	}
	
}
