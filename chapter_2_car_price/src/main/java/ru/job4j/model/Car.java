package ru.job4j.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "car_year", nullable = false)
    private int year;

    @Column(name = "make", nullable = false, length = 100)
    private String make;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "modification")
    private String modification;
    //привод
    @Column(name = "drive_train", length = 100)
    private String driveTrain;

    @Column(name = "transmission", length = 100)
    private String transmission;

    @Column(name = "engine", length = 100)
    private String engine;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "doors")
    private int doors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id", nullable = false)
    @JsonManagedReference
    private CarColor color;

    @Column(name = "vin_number")
    private String vinNumber;

    public Car() {
    }

    public Car(int year, String make, String model, String modification, String driveTrain,
               String transmission, String engine, int mileage, int doors, CarColor color, String vinNumber) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.modification = modification;
        this.driveTrain = driveTrain;
        this.transmission = transmission;
        this.engine = engine;
        this.mileage = mileage;
        this.doors = doors;
        this.color = color;
        this.vinNumber = vinNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    public String getDriveTrain() {
        return driveTrain;
    }

    public void setDriveTrain(String drivetrain) {
        this.driveTrain = drivetrain;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public CarColor getColor() {
        return color;
    }

    public void setColor(CarColor color) {
        this.color = color;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return year == car.year
                && Objects.equals(make, car.make)
                && Objects.equals(model, car.model)
                && Objects.equals(vinNumber, car.vinNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, make, model, vinNumber);
    }
}
