package main.util;

import java.io.PrintWriter;
import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.controller.OnlineController;

public class Arduino {
	
	public static ObservableList<String> getPortList(){

		SerialPort[] portNames = SerialPort.getCommPorts();
		ObservableList<String> portList = null;

		if (portNames.length == 0) {

		}
		else if (portNames.length == 1) {
			portList = FXCollections.observableArrayList(portNames[0].getSystemPortName());
		}
		else if (portNames.length == 2) {
			portList = FXCollections.observableArrayList(portNames[0].getSystemPortName(), portNames[1].getSystemPortName());
		}
		else if (portNames.length == 3) {
			portList = FXCollections.observableArrayList(portNames[0].getSystemPortName(), portNames[1].getSystemPortName(), portNames[2].getSystemPortName());
		}
		else if (portNames.length == 4) {
			portList = FXCollections.observableArrayList(portNames[0].getSystemPortName(), portNames[1].getSystemPortName(), portNames[2].getSystemPortName(),portNames[3].getSystemPortName());
		}

		return portList;
	}

	public static void sendData(double n){
		SerialPort chosenPort = OnlineController.getChosenPort();
		if (chosenPort != null) {
			PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
			//System.out.println(n);
			int helo = (int)n;
			String heloo = (Integer.toString(helo) + "a");
			System.out.println("Arduinonak kiküldött jel:" + heloo);
			output.print(heloo);
			output.flush();
		}
	}	
}
