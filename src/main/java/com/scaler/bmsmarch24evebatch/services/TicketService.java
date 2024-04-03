package com.scaler.bmsmarch24evebatch.services;

import com.scaler.bmsmarch24evebatch.exceptions.InvalidRequestException;
import com.scaler.bmsmarch24evebatch.exceptions.UnavailableSeatsException;
import com.scaler.bmsmarch24evebatch.models.Show;
import com.scaler.bmsmarch24evebatch.models.Ticket;

import java.util.List;

public interface TicketService {

    public Ticket bookTicket(List<Integer> seatIds, int showId, int userId) throws InvalidRequestException, UnavailableSeatsException;
}
