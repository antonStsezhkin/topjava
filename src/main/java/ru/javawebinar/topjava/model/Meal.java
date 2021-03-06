package ru.javawebinar.topjava.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(name = "meals_unique_user_datetime_idx", columnNames = {"user_id", "date_time"})})
@NamedQueries({
		@NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id =:userId ORDER BY m.dateTime DESC"),
		@NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id =:userId AND m.dateTime >= :start AND m.dateTime < :end  ORDER BY m.dateTime DESC"),
		@NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.user.id =:userId AND m.id =:id")
})
public class Meal extends AbstractBaseEntity {
	public static final String ALL_SORTED = "Meal.getAll";
	public static final String GET_BETWEEN = "Meal.getBetween";
	public static final String DELETE = "Meal.delete";

	@Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
	@NotNull
    private LocalDateTime dateTime;

	@Column(name = "description", nullable = false)
	@NotBlank
	@Size(max=120)
    private String description;

	@Column(name = "calories", nullable = false)
	@Range(min=0, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
