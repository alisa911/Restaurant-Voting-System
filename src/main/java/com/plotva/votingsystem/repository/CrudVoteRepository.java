package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    Vote getVoteByIdAndUser_id(int id, int userId);

    List<Vote> getAllByUser_id(int userId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id=:id AND v.date=:date")
    int getCount(@Param("id") int restaurantId, @Param("date") LocalDate date);

    int deleteVoteByIdAndUser_id(int id, int userId);
}
