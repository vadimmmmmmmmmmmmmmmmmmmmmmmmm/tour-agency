package com.razkuuuuuuu.touragency.entities.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class Tour implements Serializable {
    private static final long serialVersionUID = 4244281329696733770L;
    private int id;
    private String title;
    private String description;
    private String enTitle;
    private String uaTitle;
    private String ruTitle;
    private String enDescription;
    private String uaDescription;
    private String ruDescription;
    private String type;
    private Date departureTakeoffDate;
    private Time departureTakeoffTime;
    private Date returnTakeoffDate;
    private Time returnTakeoffTime;
    private String imageFile;
    private BigDecimal ticketPrice;
    private int ticketCount;
    private int registeredTicketsCount;
    private int paidTicketsCount;
    private String cityName;
    private String hotelName;
    private int hotelRating;
    private boolean onFire;
    private int discountAmount;
    private int discountPerEveryTicketsCount;
    private int maxDiscount;
    private int tourRating;

    public int getTourRating() {
        return tourRating;
    }

    public void setTourRating(int tourRating) {
        this.tourRating = tourRating;
    }



    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public String getUaTitle() {
        return uaTitle;
    }

    public void setUaTitle(String uaTitle) {
        this.uaTitle = uaTitle;
    }

    public String getRuTitle() {
        return ruTitle;
    }

    public void setRuTitle(String ruTitle) {
        this.ruTitle = ruTitle;
    }

    public String getEnDescription() {
        return enDescription;
    }

    public void setEnDescription(String enDescription) {
        this.enDescription = enDescription;
    }

    public String getUaDescription() {
        return uaDescription;
    }

    public void setUaDescription(String uaDescription) {
        this.uaDescription = uaDescription;
    }

    public String getRuDescription() {
        return ruDescription;
    }

    public void setRuDescription(String ruDescription) {
        this.ruDescription = ruDescription;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public int getDiscountPerEveryTicketsCount() {
        return discountPerEveryTicketsCount;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setDiscountPerEveryTicketsCount(int discountPerEveryTicketsCount) {
        this.discountPerEveryTicketsCount = discountPerEveryTicketsCount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }



    public Tour() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDepartureTakeoffDate() {
        return departureTakeoffDate;
    }

    public void setDepartureTakeoffDate(Date departureTakeoffDate) {
        this.departureTakeoffDate = departureTakeoffDate;
    }

    public Time getDepartureTakeoffTime() {
        return departureTakeoffTime;
    }

    public void setDepartureTakeoffTime(Time departureTakeoffTime) {
        this.departureTakeoffTime = departureTakeoffTime;
    }

    public Date getReturnTakeoffDate() {
        return returnTakeoffDate;
    }

    public void setReturnTakeoffDate(Date returnTakeoffDate) {
        this.returnTakeoffDate = returnTakeoffDate;
    }

    public Time getReturnTakeoffTime() {
        return returnTakeoffTime;
    }

    public void setReturnTakeoffTime(Time returnTakeoffTime) {
        this.returnTakeoffTime = returnTakeoffTime;
    }


    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setRegisteredTicketsCount(int registeredTicketsCount) {
        this.registeredTicketsCount = registeredTicketsCount;
    }

    public int getRegisteredTicketsCount() {
        return registeredTicketsCount;
    }

    public void setPaidTicketsCount(int paidTicketsCount) {
        this.paidTicketsCount = paidTicketsCount;
    }

    public int getPaidTicketsCount() {
        return paidTicketsCount;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelRating(int hotelRating) {
        this.hotelRating = hotelRating;
    }

    public int getHotelRating() {
        return hotelRating;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public boolean getOnFire() {
        return onFire;
    }


}
