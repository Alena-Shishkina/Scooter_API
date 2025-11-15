package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Order {
    private Integer track;
    private String name;
    private String surname;
    private String address;
    private String metro;
    private String phoneNumber;
    private Integer rentTime;
    private String deliveryDate;
    private String[] color;
    private String comments;


    public Integer getTrack() {
        return track;
    }

    public Order setTrack(Integer track) {
        this.track = track;
        return this;
    }


    public String getName() {
        return name;
    }

    public Order setName(String name) {
        this.name = name;
        return this;
    }


    public String getSurname() {
        return surname;
    }

    public Order setSurname(String surname) {
        this.surname = surname;
        return this;
    }


    public String getAddress() {
        return address;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }


    public String getMetro() {
        return metro;
    }

    public Order setMetro(String metro) {
        this.metro = metro;
        return this;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Order setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    public Integer getRentTime() {
        return rentTime;
    }

    public Order setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
        return this;
    }


    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }


    public String[] getColor() {
        return color;
    }

    public Order setColor(String[] color) {
        this.color = color;
        return this;
    }


    public String getComments() {
        return comments;
    }

    public Order setComments(String comments) {
        this.comments = comments;
        return this;
    }


}
