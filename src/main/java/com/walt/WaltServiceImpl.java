package com.walt;

import com.walt.dao.CustomerRepository;
import com.walt.dao.DeliveryRepository;
import com.walt.dao.DriverRepository;
import com.walt.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WaltServiceImpl implements WaltService {

    @Resource
    CustomerRepository customerRepository;

    @Resource
    DriverRepository driverRepository;

    @Resource
    DeliveryRepository deliveryRepository;

    @Override
    public Delivery createOrderAndAssignDriver(Customer customer, Restaurant restaurant, Date deliveryTime) throws Exception {
        Customer customerFromDb = customerRepository.findByName(customer.getName());
        // check if customer exist, if not create one
        if (customerFromDb == null) {
            customerRepository.save(customer);
        }
        // get all drivers by city
        List<Driver> drivers = driverRepository.getAvailableDriver(customer.getCity(),deliveryTime,addHours(deliveryTime,1));

        if (drivers.isEmpty()) {
            throw new Exception("No Driver Available");
        }

        TreeMap<Integer, Driver> map = new TreeMap<>();
        // if we have more the one take the driver with minimal delivery
        for (Driver driver : drivers) {
            int deliveryCount = deliveryRepository.countByDriver(driver);
            map.put(deliveryCount, driver);
        }
        Driver driver = map.firstEntry().getValue();
        Delivery delivery = new Delivery(driver, restaurant, customer, deliveryTime);
        delivery.setDistance(calculateDistance());
        return delivery;
    }

    @Override
    public List<DriverDistance> getDriverRankReport() {
        List<Driver> drivers = driverRepository.findAll();
        return getDriverDistances(drivers);
    }

    @Override
    public List<DriverDistance> getDriverRankReportByCity(City city) {
        List<Driver> drivers = driverRepository.findAllDriversByCity(city);
        return getDriverDistances(drivers);
    }

    private List<DriverDistance> getDriverDistances(List<Driver> drivers) {
        List<DriverDistance> reports = new ArrayList<>();
        drivers.forEach(driver -> {
            Double distance = deliveryRepository.getTotalDistanceByDriver(driver);
            Report report = new Report(driver, distance == null ? 0 : distance.longValue());
            reports.add(report);

        });
        reports.sort((o1, o2) -> (int) (o2.getTotalDistance() - o1.getTotalDistance()));
        return reports;
    }

    private Double calculateDistance() {
        return (double) new Random().nextInt(21);
    }

    private Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
