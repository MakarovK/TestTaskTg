package com.tasktg.repository;

import com.tasktg.entity.MinesweeperFieldPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MinesweeperFieldRepository extends JpaRepository<MinesweeperFieldPojo, UUID> {
}
