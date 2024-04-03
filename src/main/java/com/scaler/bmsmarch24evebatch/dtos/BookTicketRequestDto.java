package com.scaler.bmsmarch24evebatch.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BookTicketRequestDto {
    private List<Integer> seatIds;
    private int showId;
    private int userId;
}
