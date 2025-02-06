package com.project.planner;

import com.project.planner.model.EquipmentType;
import com.project.planner.model.MovableEquipment;
import com.project.planner.model.Room;
import com.project.planner.repository.MovableEquipmentRepository;
import com.project.planner.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class PlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlannerApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(RoomRepository roomRepository, MovableEquipmentRepository movableEquipmentRepository) {

		return args -> {

			roomRepository.save(new Room("E1001", 23, Set.of()));
			roomRepository.save(new Room("E1002", 10, Set.of(EquipmentType.SCREEN)));
			roomRepository.save(new Room("E1003", 8, Set.of(EquipmentType.SPEAKERPHONE)));
			roomRepository.save(new Room("E1004", 4, Set.of(EquipmentType.WHITEBOARD)));
			roomRepository.save(new Room("E2001", 4, Set.of()));
			roomRepository.save(new Room("E2002", 15, Set.of(EquipmentType.SCREEN, EquipmentType.WEBCAM)));
			roomRepository.save(new Room("E2003", 7, Set.of()));
			roomRepository.save(new Room("E2004", 9, Set.of(EquipmentType.WHITEBOARD)));
			roomRepository.save(new Room("E3001", 13, Set.of(EquipmentType.SCREEN, EquipmentType.WEBCAM, EquipmentType.SPEAKERPHONE)));
			roomRepository.save(new Room("E3002", 8, Set.of()));
			roomRepository.save(new Room("E3003", 9, Set.of(EquipmentType.SCREEN, EquipmentType.WHITEBOARD)));
			roomRepository.save(new Room("E3004", 4, Set.of()));

			movableEquipmentRepository.save(new MovableEquipment(EquipmentType.SCREEN, 5));
			movableEquipmentRepository.save(new MovableEquipment(EquipmentType.SPEAKERPHONE, 4));
			movableEquipmentRepository.save(new MovableEquipment(EquipmentType.WHITEBOARD, 2));
			movableEquipmentRepository.save(new MovableEquipment(EquipmentType.WEBCAM, 4));

		};
	}
}
