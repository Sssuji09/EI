package com.smartoffice.command;

import com.smartoffice.manager.OfficeManager;

public class OccupancyUpdateCommand implements Command {
    private OfficeManager manager;
    private int roomId;
    private int occupants;

    public OccupancyUpdateCommand(OfficeManager mgr, int id, int count) {
        this.manager = mgr;
        this.roomId = id;
        this.occupants = count;
    }

    public void execute() {
        manager.addOccupant(roomId, occupants);
    }
}
