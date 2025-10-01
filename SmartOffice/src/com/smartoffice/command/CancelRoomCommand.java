package com.smartoffice.command;

import com.smartoffice.manager.OfficeManager;

public class CancelRoomCommand implements Command {
    private OfficeManager manager;
    private int roomId;

    public CancelRoomCommand(OfficeManager mgr, int id) {
        this.manager = mgr;
        this.roomId = id;
    }

    public void execute() {
        manager.cancelRoom(roomId);
    }
}
