package com.smartoffice.command;

import com.smartoffice.manager.OfficeManager;

public class BookRoomCommand implements Command {
    private OfficeManager manager;
    private int roomId;

    public BookRoomCommand(OfficeManager mgr, int id) {
        this.manager = mgr;
        this.roomId = id;
    }

    public void execute() {
        manager.blockRoom(roomId, "09:00", 60);  
    }
}
