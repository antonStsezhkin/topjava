package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;


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
		Meal meal = service.get(MEAL_ID, USER_ID);
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
		service.update(updated, USER_ID);
		assertMatch(service.get(MEAL_ID, USER_ID),getUpdated());
	}
}
