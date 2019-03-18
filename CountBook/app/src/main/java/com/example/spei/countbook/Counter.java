package com.example.spei.countbook;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Counter {
    private String name;
    private Date date;
    private int initialValue;
    private int currentValue;
    private String comment;


    public Counter(String name, int value) {
        this.name = name;
        this.date = new Date();
        this.initialValue = value;
        this.currentValue = value;
    }


    public Counter(String name, int value, String comment) {
        this.name = name;
        this.date = new Date();
        this.initialValue = value;
        this.currentValue = value;
        this.comment = comment;
    }


    public String getName() {
        return this.name;
    }


    public String getDate() {
        SimpleDateFormat properFormat = new SimpleDateFormat("yyyy-MM-dd");
        return properFormat.format(this.date);
    }

    public void setDate() {
        this.date = new Date();
    }


    public int getCurrentValue() {
        return this.currentValue;
    }


    public int getInitialValue() {
        return this.initialValue;
    }


    public void changeCount(int increment) {
        if (this.currentValue == 0 && increment<0) {
            this.currentValue = 0;
        }
        else {
            this.currentValue = this.currentValue + increment;
            this.date = new Date();
        }
    }


    public void resetCount() {
        this.currentValue = this.initialValue;
        this.date = new Date();
    }

    public String getComment() {
        return this.comment;
    }

    public void setName(String newName) {
        this.name = newName;
    }


    public void setInitialValue(int newInitial) {
        this.initialValue = newInitial;
    }


    public void setCurrentValue(int newCurrent) {
        this.currentValue = newCurrent;
    }

    public void setComment(String newComment) {
        this.comment = newComment;
    }

    @Override
    public String toString() {
        SimpleDateFormat properFormat = new SimpleDateFormat("yyyy-MM-dd");
        return name+" | Current Value: "+currentValue+" | Initial Value: "+initialValue+" \n"+properFormat.format(date);
    }

}