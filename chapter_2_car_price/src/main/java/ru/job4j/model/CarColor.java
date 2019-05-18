package ru.job4j.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_color")
public class CarColor {

    @Id
    @Column(name = "color_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "color_name", unique = true, nullable = false)
    private String colorName;

    public CarColor() {
    }

    public CarColor(String colorName) {
        this.colorName = colorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarColor carColor = (CarColor) o;
        return Objects.equals(colorName, carColor.colorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorName);
    }
}
