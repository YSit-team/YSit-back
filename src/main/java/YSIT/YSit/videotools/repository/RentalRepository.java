package YSIT.YSit.videotools.repository;

import YSIT.YSit.videotools.RentalStatus;
import YSIT.YSit.videotools.entity.Rental;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class RentalRepository {
    private final EntityManager em;

    public String save(Rental rental) {
        em.persist(rental);
        return rental.getId();
    }

    public Rental findOne(String rentalId) {
        return em.find(Rental.class, rentalId);
    }

    public List<Rental> findAll() {
        return em.createQuery("select r from Rental r order by r.status asc", Rental.class)
                .getResultList();
    }
    public List<Rental> findRequestById(String id) {
        return em.createQuery("select r from Rental r where r.uploader like :id order by r.status asc", Rental.class)
                .setParameter("id", id)
                .getResultList();
    }

    public String deleteById(String id) {
        Rental deleteRental = em.find(Rental.class, id);
        try {
            em.remove(deleteRental);
        } catch (IllegalStateException e) {
            return "Fail";
        }
        return "Complete";
    }
}
