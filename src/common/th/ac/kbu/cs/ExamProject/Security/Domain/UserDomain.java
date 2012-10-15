package th.ac.kbu.cs.ExamProject.Security.Domain;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Entity.User;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;


@Configurable
public class UserDomain {

	@Autowired
	private BasicFinderService basicFinderService;

	public User loadUserByUsername(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class,
				"user");
		criteria.createAlias("user.authority", "authority");
		criteria.createAlias("user.prefixName", "prefixName");
		criteria.add(Restrictions.eq("user.username", username));
		return basicFinderService.findUniqueByCriteria(criteria);
	}
}
