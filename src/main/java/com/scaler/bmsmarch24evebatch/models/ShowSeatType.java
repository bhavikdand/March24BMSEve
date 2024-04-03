package com.scaler.bmsmarch24evebatch.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class ShowSeatType extends BaseModel{
    private SeatType seatType;
    private Show show;
    private double amount;
}
