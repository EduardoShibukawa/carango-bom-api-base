package br.com.caelum.carangobom.repositories.specifications;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.dtos.CarFilterRequest;

class CarFilterSpecificationTest {

	@Mock
	public Root<Car> rootMock;

	@Mock
	public CriteriaQuery<?> queryMock;

	@Mock
	public CriteriaBuilder criteriaBuilderMock;

	@BeforeEach
	void setUp() {
		openMocks(this);
	}

	@Test
	void whenFilterIsEmtpySpecificationShouldBeEmpty() {
		CarFilterSpecification specification = new CarFilterSpecification(new CarFilterRequest());

		specification.toPredicate(rootMock, queryMock, criteriaBuilderMock);

		verify(criteriaBuilderMock).and(new ArrayList<Predicate>().toArray(new Predicate[0]));
	}

	@Test
	void whenFilterBlankModelThenSpecificationShouldBeEmpty() {
		CarFilterSpecification specification = new CarFilterSpecification(
				new CarFilterRequest(" ", 0L, null, null));

		specification.toPredicate(rootMock, queryMock, criteriaBuilderMock);

		verify(criteriaBuilderMock)

				.and(new ArrayList<Predicate>().toArray(new Predicate[0]));
	}

	@Test
	void whenFilterOnlyNameSpecificationShouldFilterOnlyName() {
		CarFilterSpecification specification = new CarFilterSpecification(
				new CarFilterRequest("KA", 0L, null, null));

		specification.toPredicate(rootMock, queryMock, criteriaBuilderMock);

		verify(criteriaBuilderMock).and(any());
		verify(criteriaBuilderMock).like(any(), eq("%KA%"));
	}

	@Test
	void whenFilterSpecificationShouldFilter() {
		CarFilterSpecification specification = new CarFilterSpecification(
				new CarFilterRequest("KA", 1L, BigDecimal.ONE, BigDecimal.TEN));


		when(rootMock.get(anyString())).thenReturn(mock(PathMock.class));

		specification.toPredicate(rootMock, queryMock, criteriaBuilderMock);

		verify(criteriaBuilderMock).and(any());
		verify(criteriaBuilderMock).like(any(), eq("%KA%"));
		verify(criteriaBuilderMock).equal(any(), eq(1L));
		verify(criteriaBuilderMock).greaterThanOrEqualTo(any(), eq(BigDecimal.ONE));
		verify(criteriaBuilderMock).lessThanOrEqualTo(any(), eq(BigDecimal.TEN));
	}

	private interface PathMock extends Path<Object> {
	};
}
