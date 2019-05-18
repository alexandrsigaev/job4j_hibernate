package ru.job4j.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_item")
public class CarItem {

    @Id
    @Column(name = "car_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person", nullable = false)
    @JsonIgnore
    private Person person;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "address_locality", nullable = false)
    private String addressLocality;

    @Column(name = "description")
    private String description;

    @Column(name = "create_item", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;

    @OneToMany(mappedBy = "carItem", fetch = FetchType.EAGER)
    @JsonProperty("images")
    @JsonBackReference
    private List<CarImage> carImage;

    @OneToOne
    @JoinColumn(name = "id_car", nullable = false)
    private Car car;


    @Column(name = "sold", nullable = false)
    private boolean sold;

    public CarItem() {
    }

    public CarItem(Person person, int price, String addressLocality, String description,
                   LocalDateTime created, Car car, boolean sold) {
        this.person = person;
        this.price = price;
        this.addressLocality = addressLocality;
        this.description = description;
        this.created = created;
        this.car = car;
        this.sold = sold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddressLocality() {
        return addressLocality;
    }

    public void setAddressLocality(String addressLocality) {
        this.addressLocality = addressLocality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<CarImage> getCarImage() {
        return carImage;
    }

    public void setCarImage(List<CarImage> carImage) {
        this.carImage = carImage;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarItem carItem = (CarItem) o;
        return id == carItem.id
                && Objects.equals(person, carItem.person)
                && Objects.equals(created, carItem.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, created);
    }

}
