package com.github.cypher.model;

import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;

public interface RoomCollection {
	void addRoom(Room room);
	Image getImage();
	ObjectProperty<Image> getImageProperty();
}
