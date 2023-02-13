package com.razkuuuuuuu.touragency.entities.bean;

import com.razkuuuuuuu.touragency.entities.entity.TourLocalisationInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TourCreationProperties implements Serializable {
    private boolean citySelected;
    private boolean hotelSelected;
    private int cityId;
    private int hotelId;
    private List<TourLocalisationInfo> localisationList;
    private String departureTakeoffDate;
    private String departureTakeoffTime;
    private String returnTakeoffDate;
    private String returnTakeoffTime;
    private int discountAmount;
    private int discountPer;
    private int discountMax;
    private String tourType;
    private int ticketCount;
    private BigDecimal ticketPrice;

    public boolean isCitySelected() {
        return citySelected;
    }

    public void setCitySelected(boolean citySelected) {
        this.citySelected = citySelected;
    }

    public boolean isHotelSelected() {
        return hotelSelected;
    }

    public void setHotelSelected(boolean hotelSelected) {
        this.hotelSelected = hotelSelected;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public List<TourLocalisationInfo> getLocalisationList() {
        return localisationList;
    }

    public void setLocalisationList(List<TourLocalisationInfo> localisationList) {
        this.localisationList = localisationList;
    }

    public void setDepartureTakeoffDate(String departureTakeoffDate) {
        this.departureTakeoffDate = departureTakeoffDate;
    }

    public String getDepartureTakeoffDate() {
        return departureTakeoffDate;
    }

    public void setDepartureTakeoffTime(String departureTakeoffTime) {
        this.departureTakeoffTime = departureTakeoffTime;
    }

    public String getDepartureTakeoffTime() {
        return departureTakeoffTime;
    }

    public void setReturnTakeoffDate(String returnTakeoffDate) {
        this.returnTakeoffDate = returnTakeoffDate;
    }

    public String getReturnTakeoffDate() {
        return returnTakeoffDate;
    }

    public void setReturnTakeoffTime(String returnTakeoffTime) {
        this.returnTakeoffTime = returnTakeoffTime;
    }

    public String getReturnTakeoffTime() {
        return returnTakeoffTime;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountMax(int discountMax) {
        this.discountMax = discountMax;
    }

    public int getDiscountMax() {
        return discountMax;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getTourType() {
        return tourType;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
