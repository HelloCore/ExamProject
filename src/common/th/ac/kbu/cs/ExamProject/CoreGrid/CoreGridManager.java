package th.ac.kbu.cs.ExamProject.CoreGrid;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import th.ac.kbu.cs.ExamProject.Service.BasicEntityService;
import th.ac.kbu.cs.ExamProject.Service.BasicFinderService;
import th.ac.kbu.cs.ExamProject.Service.StudentTeacherService;
import th.ac.kbu.cs.ExamProject.Util.BeanUtils;

@Configurable
public abstract class CoreGridManager<T> {
	
	@Autowired
	protected BasicFinderService basicFinderService;
	
	@Autowired
	protected BasicEntityService basicEntityService;
	
	@Autowired
	protected StudentTeacherService studentTeacherService;
	
	public void save(final T domain) {
		Object entity = this.toEntity(domain);
		basicEntityService.saveOrUpdate(entity);
	}
	
	public void delete(final T domain){
		Object entity = this.toEntityDelete(domain);
		basicEntityService.update(entity);
	}
	
	public abstract Object toEntityDelete(final T domain);
	public abstract Object toEntity(final T domain);
	
	public CoreGrid<HashMap<String,Object>> search(final T domain,String username){
		DetachedCriteria criteria = initCriteria(domain);
		
		ProjectionList projectionList = Projections.projectionList();
		this.setProjectionList(projectionList,domain);
		
		criteria.setProjection(projectionList);
		
		this.applyCriteria(criteria,domain,username);
		
		this.addOrder(criteria);
		criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		Integer limit = this.getRows();
		Integer start = (this.getPage() -1) * this.getRows();
		List<HashMap<String,Object>> records = basicFinderService.findByCriteria(criteria,start,limit);
		CoreGrid<HashMap<String,Object>> gridData = new CoreGrid<HashMap<String,Object>>();
		gridData.setRecords(records);
		Integer totalRecord = this.getTotal(domain,username);
		gridData.setTotalRecords(totalRecord);
		gridData.setPage(this.getPage());
		Double totalPages = Math.ceil(totalRecord.doubleValue() / this.getRows().doubleValue());
		gridData.setTotalPages(totalPages.intValue());
		
		return gridData;
	}

	protected Integer getTotal(final T domain,String username){
		DetachedCriteria criteria = initCriteria(domain);
		ProjectionList projectionList = Projections.projectionList();
		
		this.setProjectionCount(projectionList, domain);
		
		criteria.setProjection(projectionList);
		applyCriteria(criteria,domain,username);
		return BeanUtils.toInteger(basicFinderService.findUniqueByCriteria(criteria));
	}
	
	protected void setProjectionCount(ProjectionList projectionList,final T domain){
		projectionList.add(Projections.rowCount());
	}
	
	protected abstract void setProjectionList(ProjectionList projectionList,final T domain);
	
	protected abstract DetachedCriteria initCriteria(final T domain);
	
	protected abstract void addOrder(DetachedCriteria criteria);
	
	protected abstract void applyCriteria(DetachedCriteria criteria,final T domain,String username);
	
	private Integer rows;
	private Integer page;
	private String orderBy;
	private String order;
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
