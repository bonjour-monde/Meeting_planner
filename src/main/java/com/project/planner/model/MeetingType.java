package com.project.planner.model;

import java.util.Set;
import java.util.stream.Collectors;

public enum MeetingType {
    VC, SPEC, RS, RC;

    private static final Set<EquipmentType> NECESSARY_EQUIPMENT_VC = Set.of(EquipmentType.SCREEN, EquipmentType.SPEAKERPHONE, EquipmentType.WEBCAM);
    private static final Set<EquipmentType> NECESSARY_EQUIPMENT_RS = Set.of(EquipmentType.WHITEBOARD);
    private static final Set<EquipmentType> NECESSARY_EQUIPMENT_RC = Set.of(EquipmentType.SCREEN, EquipmentType.SPEAKERPHONE, EquipmentType.WHITEBOARD);

    private static final int NECESSARY_CAPACITY_SPEC = 3;

    public boolean isMeetingPossible(Set<EquipmentType> equipments, int capacity) {
        return switch (this) {
            case VC ->  equipments.containsAll(NECESSARY_EQUIPMENT_VC);
            case RS ->  equipments.containsAll(NECESSARY_EQUIPMENT_RS);
            case RC ->  equipments.containsAll(NECESSARY_EQUIPMENT_RC);
            case SPEC ->  capacity >= NECESSARY_CAPACITY_SPEC;
        };
    }

    public Set<EquipmentType> getNecessaryEquipments() {
        return switch (this) {
            case VC ->  NECESSARY_EQUIPMENT_VC;
            case RS ->  NECESSARY_EQUIPMENT_RS;
            case RC ->  NECESSARY_EQUIPMENT_RC;
            case SPEC ->  Set.of();
        };
    }
}
