package YSIT.YSit.videotools.repository;

import YSIT.YSit.videotools.RentalStatus;
import YSIT.YSit.videotools.entity.Rental;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RentalRepository {
    private final EntityManager em;

    public String save(Rental rental) {
        em.persist(rental);
        return rental.getId();
    }

    public Rental findOne(String rentalId) {
        return em.find(Rental.class, rentalId);
    }
    public List<Rental> findRequestsByWait() {
        return em.createQuery("select r from Rental r where r.status like :status order by r.status asc", Rental.class)
                .setParameter("status", RentalStatus.wait)
                .getResultList();
    }
    public List<Rental> findRequestsAll() {
        return em.createQuery("select r from Rental r order by r.status asc", Rental.class)
                .getResultList();
    }
}
