package com.scaler.bmsmarch24evebatch.services;

import com.scaler.bmsmarch24evebatch.exceptions.InvalidRequestException;
import com.scaler.bmsmarch24evebatch.exceptions.UnavailableSeatsException;
import com.scaler.bmsmarch24evebatch.models.*;
import com.scaler.bmsmarch24evebatch.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService{

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;
    private ShowSeatRepository showSeatRepository;
    private TicketRepository ticketRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ShowRepository showRepository, SeatRepository seatRepository, ShowSeatRepository showSeatRepository, TicketRepository ticketRepository, ShowSeatTypeRepository showSeatTypeRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    @Override
    public Ticket bookTicket(List<Integer> seatIds, int showId, int userId) throws InvalidRequestException, UnavailableSeatsException {
        /*
        validate if the user is a valid user
        validate if the show is present in the db
        validate if the show can be booked now? startime + 10 mins is allowed
        validate if the seats are valid
        now fetch available show seats from db
        if avl seat count == # of seats in the request
            update the seat status to blocked
        else
            throw exeception saying few or all seats are unavailable
         */
        Optional<User> userById = this.userRepository.findUserById(userId);
        if(userById.isEmpty()){
            throw  new InvalidRequestException("User is invalid");
        }
        User user = userById.get();

        Show show = this.showRepository.findById(showId).orElseThrow(() -> new InvalidRequestException("Show is invalid"));

        long currentDateTime = new Date().getTime();
        if(show.getStartTime().getTime() + (10 * 60L) < currentDateTime){
            throw new InvalidRequestException("This show cannot be booked");
        }

        List<Seat> seats = seatRepository.findAllByIdIn(seatIds);
        if(seats.size() != seatIds.size()){
            throw new InvalidRequestException("Seats are invalid");
        }
        // TODO:  Check if the seats belong to the screen on which the show is going to be run
        blockSeatForUser(user, show, seatIds);

        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShowId(showId);
        Map<SeatType, Double> pricingMap = new HashMap<>();
        for(ShowSeatType seatType: showSeatTypes){
            pricingMap.put(seatType.getSeatType(), seatType.getAmount());
        }
        double totalAmount = 0;
        for(Seat seat: seats){
            totalAmount += pricingMap.get(seat.getSeatType());
        }

        // TODO: Apply strategy pattern to compute convenience fee and add it to the total amount

        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.UNPAID);
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeats(seats);
        ticket.setTotalAmount(totalAmount);

        return this.ticketRepository.save(ticket);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void blockSeatForUser(User user, Show show, List<Integer> seatIds) throws UnavailableSeatsException {
        List<ShowSeat> showSeats = this.showSeatRepository.findAllByShowIdAndSeatIdsInAndSeatStatus_Available(show.getId(), seatIds);
        if(showSeats.size() != seatIds.size()) {
            throw new UnavailableSeatsException("Some or all seats are unavailable");
        }
        showSeats.stream().forEach(ss -> {
            ss.setSeatStatus(SeatStatus.BLOCKED);
            ss.setUser(user);
        });
        //if the objects that we are trying to store have ids (it means that these
        // objects are already present in the DB), hence saveAll will fire an update
        // else saveAll leads to an insert query
        showSeatRepository.saveAll(showSeats);

    }
}
