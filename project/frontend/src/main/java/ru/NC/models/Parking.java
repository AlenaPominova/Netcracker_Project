package ru.NC.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PARKING")
public class Parking implements Serializable {

    private Long id;
    private Double longitude;
    private Double latitude;
    private String address;
    private Double price;
    private Timestamp openTime;
    private Timestamp closeTime;
    private Integer freeSpots;
    private User owner;

    public Parking(){}

    public Parking(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "PARKING_ID", unique = true, nullable = false, precision = 5, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LONGITUDE", unique = false, nullable = false, precision = 5, scale = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "LATITUDE", unique = false, nullable = false, precision = 5, scale = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "ADDRESS", unique = true, nullable = false, precision = 5, scale = 0)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "PRICE", unique = true, nullable = false, precision = 5, scale = 0)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "OPEN_TIME", unique = true, nullable = true, precision = 5, scale = 0)
    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    @Column(name = "CLOSE_TIME", unique = true, nullable = true, precision = 5, scale = 0)
    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }

    @Column(name = "FREE_SPOTS", unique = true, nullable = false, precision = 5, scale = 0)
    public Integer getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(Integer freeSpots) {
        this.freeSpots = freeSpots;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
                ", owner={" + owner +
                '}';
    }
}
