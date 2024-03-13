package com.tasktg.main;

import com.tasktg.controller.MinesweeperGameController;
import com.tasktg.converter.MinesweeperFieldConverter;
import com.tasktg.entity.MinesweeperGamePojo;
import com.tasktg.enums.MinesweeperGameCell;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.tasktg.repository.MinesweeperFieldRepository;
import com.tasktg.repository.MinesweeperGameRepository;
import com.tasktg.service.MinesweeperGameService;

@SpringBootApplication
@CrossOrigin
@ComponentScan(basePackageClasses = {MinesweeperGameController.class,
        MinesweeperGameService.class,
        MinesweeperGameRepository.class,
        MinesweeperGamePojo.class,
        MinesweeperGameCell.class,
        MinesweeperFieldConverter.class,
        MinesweeperFieldRepository.class
})
@EntityScan(basePackageClasses = MinesweeperGamePojo.class)
@EnableJpaRepositories(basePackageClasses = MinesweeperGameRepository.class)
public class MinesweeperGame {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperGame.class, args);
    }

}
