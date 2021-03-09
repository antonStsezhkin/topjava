package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
	public static final int USER_ID = START_SEQ + 1;
	public static final int MEAL_ID = START_SEQ + 2;

	public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.MARCH, 23, 8, 0) , "Завтрак", 500 );
	public static final Meal meal1 = new Meal(MEAL_ID+1, LocalDateTime.of(2020, Month.MARCH, 23, 13, 0) , "Обед", 1000 );
	public static final Meal meal2 = new Meal(MEAL_ID+2, LocalDateTime.of(2020, Month.MARCH, 23, 20, 0) , "Ужин", 500 );

	public static void assertMatch(Meal actual, Meal expected){
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
	public static void assertMatch(Iterable<Meal> actual, Meal... expected){
		assertMatch(actual, Arrays.asList(expected));
	}
	public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	public static Meal getUpdated(){
		Meal updated = new Meal(meal);
		updated.setCalories(800);
		updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0));
		updated.setDescription("updated");
		return updated;
	}
}
