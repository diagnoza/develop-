package Windows;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
public class SecondWindow {
	public static void createWindow(){
		//Creating the window
		JFrame mainWindow = new JFrame("Main Menu");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainWindow.setLayout(null);
		mainWindow.setVisible(true);  //visibility of the window
		//mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);  //fullscreen
		mainWindow.setBounds(240,60,960,720);
	}
}