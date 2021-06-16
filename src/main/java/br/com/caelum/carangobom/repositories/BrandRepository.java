package br.com.caelum.carangobom.repositories;

import br.com.caelum.carangobom.domain.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class BrandRepository {

    private EntityManager em;

    @Autowired
    public BrandRepository(EntityManager em) {
        this.em = em;
    }

    public void delete(Brand brand) {
        em.remove(brand);
    }

    public Brand save(Brand brand) {
        em.persist(brand);
        return brand;
    }

    public Optional<Brand> findById(Long id) {
        return Optional.ofNullable(em.find(Brand.class, id));
    }

    public List<Brand> findAllByOrderByNome() {
        return em.createQuery("select m from Brand m order by m.nome", Brand.class)
                .getResultList();
    }

}
