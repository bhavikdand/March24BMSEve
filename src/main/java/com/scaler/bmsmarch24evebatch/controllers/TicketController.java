package com.scaler.bmsmarch24evebatch.controllers;

import com.scaler.bmsmarch24evebatch.dtos.BookTicketRequestDto;
import com.scaler.bmsmarch24evebatch.dtos.BookTicketResponseDto;
import com.scaler.bmsmarch24evebatch.dtos.ResponseType;
import com.scaler.bmsmarch24evebatch.exceptions.InvalidRequestException;
import com.scaler.bmsmarch24evebatch.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto){
        BookTicketResponseDto responseDto = new BookTicketResponseDto();
        try{
            validateRequest(requestDto);
            this.ticketService.bookTicket(requestDto.getSeatIds(), requestDto.getShowId(), requestDto.getUserId());

        } catch (Exception e){
            responseDto.setResponseType(ResponseType.FAILURE);
            responseDto.setErrorMessage(e.getMessage());
        }
        return responseDto;

    }

    private void validateRequest(BookTicketRequestDto requestDto) throws InvalidRequestException {
        if(requestDto.getSeatIds() == null || requestDto.getSeatIds().isEmpty()){
            throw new InvalidRequestException("Seatids should be present");
        }
        if(requestDto.getUserId() < 0){
            throw new InvalidRequestException("User id seems to be invalid");
        }
        if(requestDto.getShowId() < 0){
            throw new InvalidRequestException("Show id seems to be invalid");
        }
    }
}
