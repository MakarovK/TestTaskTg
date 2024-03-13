package com.tasktg.repository;

import com.tasktg.entity.MinesweeperGamePojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MinesweeperGameRepository extends JpaRepository<MinesweeperGamePojo, UUID> {
}
