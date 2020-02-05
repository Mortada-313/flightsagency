package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Flight {
    private StringProperty destiantion,companyName,type,departureDate,cost,tripTime;

    public Flight(String destiantion, String companyName, String type, String departureDate, String cost, String tripTime) {
        this.destiantion =new SimpleStringProperty( destiantion);
        this.companyName = new SimpleStringProperty(companyName);
        this.type = new SimpleStringProperty(type);
        this.departureDate = new SimpleStringProperty(departureDate);
        this.cost = new SimpleStringProperty(cost);
        this.tripTime = new SimpleStringProperty(tripTime);
    }

    public String getDestiantion() {
        return destiantion.get();
    }

    public StringProperty destiantionProperty() {
        return destiantion;
    }

    public void setDestiantion(String destiantion) {
        this.destiantion.set(destiantion);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDepartureDate() {
        return departureDate.get();
    }

    public StringProperty departureDateProperty() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate.set(departureDate);
    }



    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public String getTripTime() {
        return tripTime.get();
    }

    public StringProperty tripTimeProperty() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime.set(tripTime);
    }
}
