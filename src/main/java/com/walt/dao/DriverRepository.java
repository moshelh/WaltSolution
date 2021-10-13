package com.walt.dao;

import com.walt.model.City;
import com.walt.model.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DriverRepository extends CrudRepository<Driver,Long> {
    List<Driver> findAllDriversByCity(City city);

    Driver findByName(String name);

    @Query(value = "select d from Driver d left join Delivery de on d.id = de.driver.id where (de.deliveryTime IS NULL " +
            "OR de.deliveryTime not between :from and :to) and d.city = :city")
    List<Driver> getAvailableDriver(City city, Date from, Date to);

    List<Driver> findAll();
}
