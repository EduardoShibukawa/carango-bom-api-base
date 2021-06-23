package br.com.caelum.carangobom.repositories.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.dtos.CarFilterRequest;

public class CarFilterSpecification implements Specification<Car> {

	private static final long serialVersionUID = 7971569800945349613L;

	private final transient CarFilterRequest filter;
	private final transient List<Predicate> predicates;

	public CarFilterSpecification(CarFilterRequest filter) {
		this.filter = filter;
		this.predicates = new ArrayList<>();
	}

	@Override
	public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		filterModel(root, criteriaBuilder);
		
		filterBrand(root, criteriaBuilder);
		
		filterValues(root, criteriaBuilder);
	
		
		return criteriaBuilder
			.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	private void filterValues(Root<Car> root, CriteriaBuilder criteriaBuilder) {
		if (!Objects.isNull(filter.getMinValue())) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("value"), filter.getMinValue()));
		}
		
		if (!Objects.isNull(filter.getMaxValue())) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("value"), filter.getMaxValue()));	
		}
	}

	private void filterBrand(Root<Car> root, CriteriaBuilder criteriaBuilder) {
		if (!Objects.isNull(filter.getIdBrand())
				&& filter.getIdBrand() > 0L) {
			predicates.add(criteriaBuilder.equal(root.get("brand").get("id"), filter.getIdBrand()));		
		}
	}

	private void filterModel(Root<Car> root, CriteriaBuilder criteriaBuilder) {
		if (!Objects.isNull(filter.getModel())
				&& !filter.getModel().isBlank()) {
			predicates.add(criteriaBuilder.like(root.get("model"), "%" + filter.getModel() + "%"));		
		}
	}

}
