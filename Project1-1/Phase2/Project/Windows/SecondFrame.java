package Windows;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
public class SecondFrame {
	public static void createWindow(){
		//Creating the window
		JFrame SecondWindow = new JFrame("Game Title");
		SecondWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*TEXT FIELDS*/
		JTextField f1 = new JTextField(1); /*TEXTFIELD1*/
		f1.setBounds(400,200,75,75);
		f1.setEditable(true);
		f1.setFont(f1.getFont().deriveFont(36.0f));
		f1.setVisible(true);
		
		/*BUTTONS*/
		JButton D = new JButton("\uE318"); //
		D.setBounds(475,200,50,37); //setBounds(x,y,width,height)
		D.setVisible(true);
		D.setText("<html>&#8963;</html>");
		D.setFont(D.getFont().deriveFont(16.0f));
		
		JButton D1 = new JButton("\uE318"); //
		D1.setBounds(475,238,50,37); //setBounds(x,y,width,height)
		D1.setVisible(true);
		D1.setText("<html>&#8964;</html>");
		D1.setFont(D1.getFont().deriveFont(16.0f));
		
		SecondWindow.setLayout(null);
		SecondWindow.setResizable(false); //Window can't be resized
		SecondWindow.setVisible(true);  //visibility of the window
		SecondWindow.setBounds(240,60,960,720);
		
		SecondWindow.add(f1);
		SecondWindow.add(D);
		SecondWindow.add(D1);
	}
}