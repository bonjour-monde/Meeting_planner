package com.project.planner.repository;

import com.project.planner.model.MovableEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovableEquipmentRepository extends JpaRepository<MovableEquipment, Long> {
}