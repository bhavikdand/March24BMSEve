package com.scaler.bmsmarch24evebatch.dtos;

import com.scaler.bmsmarch24evebatch.models.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDto {
    private Ticket ticket;
    private String errorMessage;
    private ResponseType responseType;
}
