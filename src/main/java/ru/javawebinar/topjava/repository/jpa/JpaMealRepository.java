package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public Meal save(Meal meal, int userId) {
		meal.setUser(em.getReference(User.class, userId));
		if (meal.isNew()) {
			em.persist(meal);
			return meal;
		} else if(get(meal.getId(), userId) == null){
		return null;
	}
		else {
			return em.merge(meal);
		}
	}

	@Override
	@Transactional
	public boolean delete(int id, int userId) {
		return em.createNamedQuery(Meal.DELETE)
				.setParameter("id", id)
				.setParameter("userId", userId)
				.executeUpdate() != 0;
	}

	@Override
	public Meal get(int id, int userId) {
		Meal meal = em.find(Meal.class, id);
		return (meal != null && meal.getUser().getId() == userId)? meal : null;
	}

	@Override
	public List<Meal> getAll(int userId) {
		return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
				.setParameter("userId", userId)
				.getResultList();
	}

	@Override
	public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
		return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
				.setParameter("start", startDateTime)
				.setParameter("end", endDateTime)
				.setParameter("userId", userId)
				.getResultList();
	}
}
