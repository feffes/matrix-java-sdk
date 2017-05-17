package com.github.cypher.gui.root.roomcollection.room.chat;

import com.github.cypher.DebugLogger;
import com.github.cypher.Settings;
import com.github.cypher.ToggleEvent;
import com.github.cypher.model.Client;
import com.github.cypher.model.Room;
import com.github.cypher.model.SdkException;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.inject.Inject;

public class ChatPresenter {

	@Inject
	private Client client;

	@Inject
	private Settings settings;

	@Inject
	private EventBus eventBus;

	@FXML
	private ListView eventListView;

	@FXML
	private TextArea messageBox;

	@FXML
	private void initialize() {
		eventBus.register(this);
		messageBox.setDisable(client.getSelectedRoom() == null);
	}

	@Subscribe
	private void selectedRoomChanged(Room e){
		Platform.runLater(() -> {
			messageBox.setDisable(e == null);
		});
	}

	@FXML
	private void showRoomSettings() {
		eventBus.post(ToggleEvent.SHOW_ROOM_SETTINGS);
	}

	@FXML
	private void onMessageBoxKeyPressed(KeyEvent event) {
		if(KeyCode.ENTER.equals(event.getCode())) {

			if (((settings.getControlEnterToSendMessage() && event.isControlDown())
			 || (!settings.getControlEnterToSendMessage() && !event.isShiftDown()))
			 &&  !messageBox.getText().isEmpty()) {

				Room room = client.getSelectedRoom();
				if(room != null) {
					try {
						room.sendMessage(messageBox.getText());
					} catch(SdkException e) {
						if(DebugLogger.ENABLED) {
							DebugLogger.log("SdkException when trying to send a message: " + e);
						}
					}
				}
				messageBox.clear();

			} else if(event.isShiftDown()) {
				messageBox.insertText(
						messageBox.getCaretPosition(),
						"\n"
				);
			}
		}
	}
}
