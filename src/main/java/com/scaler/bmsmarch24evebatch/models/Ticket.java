package com.scaler.bmsmarch24evebatch.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Ticket extends BaseModel{
    private double totalAmount;
    @ManyToOne
    private Show show;
    @ManyToOne
    private User user;

    @ManyToMany
    private List<Seat> seats;

    @Enumerated(value = EnumType.ORDINAL)
    private TicketStatus ticketStatus;


}
