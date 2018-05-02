package com.parking.server.objects;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public class Parking implements Serializable {

    private Long id;
    private String name;
    private Double longitude;
    private Double latitude;
    private String address;
    private Double price;
    private Timestamp openTime;
    private Timestamp closeTime;
    private Integer freeSpots;
    private Long owner_id;
    private Map<Long, String> attr_name;

    public Parking(){}

    public Parking(Long id) {
        this.id = id;
    }

    //@Column(name = "PARKING_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //@Column(name = "LONGITUDE")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    //@Column(name = "LATITUDE")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    //@Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //@Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    //@Column(name = "OPEN_TIME")
    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

   // @Column(name = "CLOSE_TIME")
    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }

    //@Column(name = "FREE_SPOTS")
    public Integer getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(Integer freeSpots) {
        this.freeSpots = freeSpots;
    }

    //@Column(name = "OWNER_ID")
    public Long getOwner() {
        return owner_id;
    }

    public void setOwner(Long owner_d) {
        this.owner_id = owner_id;
    }

    public Map<Long, String> getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(Map<Long, String> attr_name) {
        this.attr_name = attr_name;
    }

    //@Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return Parking.class.getSimpleName() +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", freeSpots=" + freeSpots +
                ", owner={" + owner_id +
                '}';
    }
}
