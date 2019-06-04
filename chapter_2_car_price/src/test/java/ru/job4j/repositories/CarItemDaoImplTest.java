package ru.job4j.repositories;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.model.Car;
import ru.job4j.model.CarColor;
import ru.job4j.model.CarItem;
import ru.job4j.model.Person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CarItemDaoImplTest {

    private final CarItemDao<CarItem, Integer> carItemDao = CarItemDaoImpl.getInstance();
    private final PersonDao<Person, Integer> personDao = PersonDaoImpl.getInstance();

    private CarColor testColor;
    private Person personOne;
    private Person personTwo;


    @Before
    public void setUp() throws Exception {
        testColor = HibernateUtil.tx(session -> {
            CarColor color = new CarColor("blackberry");
            Optional<CarColor> candidate = session.createQuery("from CarColor where colorName =: color", CarColor.class)
                    .setParameter("color", color.getColorName())
                    .uniqueResultOptional();

            if (candidate.isPresent()) {
                color = candidate.get();
            } else {
                session.save(color);
            }
            return color;
        });

        Optional<Person> candidateOne = personDao.findByLogin("personOneCarItem");

        if (candidateOne.isEmpty()) {
            personOne = new Person("personOneCarItem", "pwrd", "Name", "email@123.com",
                    "1234567890", LocalDate.now());
            personDao.persist(personOne);
        } else {
            personOne = candidateOne.get();
        }
    }

    @Test
    public void persist() {
        Car testCar = new Car(2011, "Toyota1", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItem = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCar, false);

        this.carItemDao.persist(carItem);

        assertThat(carItem, is(carItemDao.findById(carItem.getId())));
    }

    @Test
    public void update() {
        Car testCar = new Car(2011, "Toyota2", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItem = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCar, false);

        this.carItemDao.persist(carItem);

        assertThat(carItem, is(carItemDao.findById(carItem.getId())));



    }


    @Test
    public void delete() {
        Car testCar = new Car(2011, "Toyota3", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItem = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCar, false);

        this.carItemDao.persist(carItem);

        this.carItemDao.delete(carItem);
        assertNull(this.carItemDao.findById(carItem.getId()));
    }

    @Test
    public void findAll() {
        Car testCarOne = new Car(2011, "Toyota4", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemOne = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarOne, false);

        this.carItemDao.persist(carItemOne);


        Car testCarTwo = new Car(2011, "Toyota5", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemTwo = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarTwo, false);

        this.carItemDao.persist(carItemTwo);

        List<CarItem> items = this.carItemDao.findAll();
        assertTrue(items.contains(carItemOne) && items.contains(carItemTwo));

    }

    @Test
    public void findOpenItems() {
        Car testCarOne = new Car(2011, "Toyota6", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemOne = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarOne, false);

        this.carItemDao.persist(carItemOne);


        Car testCarTwo = new Car(2011, "Toyota7", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemTwo = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarTwo, true);

        this.carItemDao.persist(carItemTwo);

        List<CarItem> openItems = this.carItemDao.findOpenItems();
        assertTrue(openItems.contains(carItemOne));
        assertFalse(openItems.contains(carItemTwo));
    }

    @Test
    public void findCloseItems() {
        Car testCarOne = new Car(2011, "Toyota8", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemOne = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarOne, false);

        this.carItemDao.persist(carItemOne);


        Car testCarTwo = new Car(2011, "Toyota9", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemTwo = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarTwo, true);

        this.carItemDao.persist(carItemTwo);

        List<CarItem> openItems = this.carItemDao.findCloseItems();
        assertTrue(openItems.contains(carItemTwo));
        assertFalse(openItems.contains(carItemOne));
    }

    @Test
    public void findAllUserItem() {
        Car testCarOne = new Car(2011, "Toyota10", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemOne = new CarItem(personOne, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarOne, false);

        this.carItemDao.persist(carItemOne);

        personTwo = new Person("findAllUserCatItem2", "pwrd", "Name", "email@123.com",
                "1234567890", LocalDate.now());
        personDao.persist(personTwo);


        Car testCarTwo = new Car(2011, "Toyota11", "Camry", "universal", "AWD",
                "Automatic", "2.5", 2500000, 3, testColor,
                "08236567IUN655");
        CarItem carItemTwo = new CarItem(personTwo, 2000, "MSK",
                "Good", LocalDateTime.now(), testCarTwo, true);

        this.carItemDao.persist(carItemTwo);


    }


}