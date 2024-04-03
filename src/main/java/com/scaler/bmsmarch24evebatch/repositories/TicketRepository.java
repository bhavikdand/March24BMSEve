package com.scaler.bmsmarch24evebatch.repositories;

import com.scaler.bmsmarch24evebatch.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {


}
