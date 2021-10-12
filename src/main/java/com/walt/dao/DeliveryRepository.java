package com.walt.dao;

import com.walt.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Delivery findDeliveriesByDriverAndDeliveryTimeBetween(Driver driver,Date from,Date to);

    Integer countByDriver(Driver driver);

    @Query("SELECT SUM(d.distance) FROM Delivery d WHERE d.driver = :driver")
    Double getTotalDistanceByDriver(Driver driver);

}


