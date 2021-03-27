package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;


@ContextConfiguration({
		"classpath:spring/spring-app.xml",
		"classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
	static {
		// Only for postgres driver logging
		// It uses java.util.logging and logged via jul-to-slf4j bridge
		SLF4JBridgeHandler.install();
	}

	@Autowired
	private MealService service;

	@Test
	public void get(){
		Meal meal = service.get(MEAL_ID, ADMIN_ID);
		assertMatch(meal, MealTestData.meal);
	}

	@Test
	public void getAll(){
		List<Meal>meals = service.getAll(USER_ID);
		assertMatch(meals, meal,meal1,meal2);
	}

	@Test
	public void update(){
		Meal updated = getUpdated();
		service.update(updated, ADMIN_ID);
		assertMatch(service.get(MEAL_ID, ADMIN_ID),getUpdated());
	}

	@Test
	public void delete(){
		service.delete(MEAL_ID, ADMIN_ID);
		assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
	}

	@Test
	public void deleteNotFound(){
		assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN_ID));
	}

	@Test
	public void getNotFound(){
		assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ADMIN_ID));
	}

	@Test
	public void duplicateUserAndTime(){
		LocalDateTime time = LocalDateTime.of(2020, 3, 23, 8, 0);
		assertThrows(DataAccessException.class, () ->
			service.create(new Meal(time, "duplicate", 1000), ADMIN_ID)
		);
	}

	@Test
	public void create(){
		Meal created = service.create(getNew(), USER_ID);
		Integer newId = created.getId();
		Meal test = getNew();
		test.setId(newId);
		assertMatch(created, test);
		assertMatch(service.get(newId, USER_ID), test);
	}
}
