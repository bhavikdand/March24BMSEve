package com.scaler.bmsmarch24evebatch.repositories;

import com.scaler.bmsmarch24evebatch.models.Show;
import com.scaler.bmsmarch24evebatch.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Integer> {

    List<ShowSeatType> findAllByShowId(int showId);
}
