package br.com.caelum.carangobom.cars.dtos;

import br.com.caelum.carangobom.cars.repositories.specification.CarFilterSpecification;
import br.com.caelum.carangobom.cars.entities.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

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
