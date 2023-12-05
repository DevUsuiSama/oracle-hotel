package com.hotel.challenge.utils;

import java.awt.event.MouseEvent;

import com.hotel.challenge.interfaces.MouseInterface;

public final class MouseUtil {

    public static void pressed(MouseInterface mouseInterface, MouseEvent event) {
        mouseInterface.setXMouse(event.getX());
        mouseInterface.setYMouse(event.getY());
    }

    public static void dragged(MouseInterface mouseInterface, MouseEvent event) {
        mouseInterface.setLocation(
                event.getXOnScreen() - mouseInterface.getXMouse(),
                event.getYOnScreen() - mouseInterface.getYMouse());
    }

}
