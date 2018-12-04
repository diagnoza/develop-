/**COMPILE WITH "javac -classpath . *.java" **/

package Windows;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.border.*;

public class SecondFrame {
	private static javax.swing.Timer timer;
	public static int GM = 0; //Which gamemode
	public static int z = 10; //Number of vertices
	public static int y = 30; //Number of edges
	public static int x = 0; //Timer variable
	public static void createWindow(int gm){
		GM = gm;
		
		//Creating the window
		JFrame SecondWindow = new JFrame("Game Title");
		SecondWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*LABELS*/
		JLabel Title = new JLabel(); //TITLE
		Title.setText("Pre-game settings");
		Title.setFont(Title.getFont().deriveFont(96.0f));
		Title.setForeground(Color.green);
		Title.setVisible(true);
		Title.setBounds(60,0,2000,150);
		
		JLabel Node = new JLabel(); //Asking for nodes
		Node.setText("Number of nodes:");
		Node.setFont(Node.getFont().deriveFont(24.0f));
		Node.setForeground(Color.white);
		Node.setVisible(true);
		Node.setBounds(185,186,2000,100);
		
		JLabel Node1 = new JLabel(); //Asking for edges
		Node1.setText("Number of edges:");
		Node1.setFont(Node1.getFont().deriveFont(24.0f));
		Node1.setForeground(Color.white);
		Node1.setVisible(true);
		Node1.setBounds(185,336,2000,100);
		
		/*TEXT FIELDS*/
		JTextField f1 = new JTextField(1); //TEXTFIELD1
		f1.setBounds(400,200,75,75);
		f1.setBackground(Color.white);
		f1.setText(number(z));
		f1.setEditable(false);
		f1.setFont(f1.getFont().deriveFont(36.0f));
		f1.setVisible(true);
		
		JTextField f2 = new JTextField(1); //TEXTFIELD2
		f2.setBounds(400,350,75,75);
		f2.setBackground(Color.white);
		f2.setText(number(30));
		f2.setEditable(false);
		f2.setFont(f2.getFont().deriveFont(36.0f));
		f2.setVisible(true);
		
		/*Background*/
		JLabel background = new JLabel(new ImageIcon("Windows/Backgrounds/NC3.jpg"));
		background.setVisible(true);
		SecondWindow.setSize(959,719);
		SecondWindow.setSize(960,720);
		background.setLayout(new FlowLayout());
		
		/*BUTTONS*/
		JButton D = new JButton(); //Higher node 
		D.setBounds(475,200,50,37); //setBounds(x,y,width,height)
		D.setVisible(true);
		D.setText("<html>&#8963;</html>");
		D.setFont(D.getFont().deriveFont(16.0f));
		
		JButton D1 = new JButton(); //Lower node
		D1.setBounds(475,238,50,37); //setBounds(x,y,width,height)
		D1.setVisible(true);
		D1.setText("<html>&#8964;</html>");
		D1.setFont(D1.getFont().deriveFont(16.0f));
		
		JButton D3 = new JButton(); //Higher edge
		D3.setBounds(475,350,50,37); //setBounds(x,y,width,height)
		D3.setVisible(true);
		D3.setText("<html>&#8963;</html>");
		D3.setFont(D3.getFont().deriveFont(16.0f));
		
		JButton D4 = new JButton(); //Lower edge
		D4.setBounds(475,388,50,37); //setBounds(x,y,width,height)
		D4.setVisible(true);
		D4.setText("<html>&#8964;</html>");
		D4.setFont(D4.getFont().deriveFont(16.0f));
		
		JButton D5 = new JButton(); //CONTINUE-BUTTON
		D5.setBounds(313,488,300,100); //setBounds(x,y,width,height)
		D5.setVisible(true);
		D5.setOpaque(true);
		D5.setBackground(Color.green);
		D5.setBorder(new LineBorder(Color.BLACK));
		D5.setText("Continue");
		D5.setFont(D5.getFont().deriveFont(16.0f));
		
		JButton Dt4 = new JButton(); //BACK-BUTTON
		Dt4.setBounds(814,614,140,70); //setBounds(x,y,width,height)
		Dt4.setVisible(true);
		Dt4.setText("Back");
		Dt4.setFont(Dt4.getFont().deriveFont(36.0f));
		
		timer = new javax.swing.Timer(100, getButtonAction(f1,f2));
		
		/*Button-Clicks*/
		D.addMouseListener(new MouseListener() { //1st HIGHER NODE BUTTON
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
				mouseReleased(e);
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
				if (y<z-1) { //If-statement that increases the number of edges if this is too low compared to the number of nodes
					y = z-1;
					f2.setText(number(y));
				}
			}
			public void mousePressed(MouseEvent e) {
				x = 1;
				if (y<z-1) { //If-statement that increases the number of edges if this is too low compared to the number of nodes
					y = z-1;
					f2.setText(number(y));
				}
				else {
					timer.start();
				}
			}
			public void mouseClicked(MouseEvent e) {
				if (z<20 && z>-1){ //If-statement so that number of nodes does not exceed 20
					z++;
					f1.setText(number(z));
				}
				if (y<z-1) { //If-statement that increases the number of edges if this is too low compared to the number of nodes
					y = z-1;
					f2.setText(number(y));
				}
			}
		});
		
		D1.addMouseListener(new MouseListener() { //1st LOWER NODE BUTTON
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
				mouseReleased(e);
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
				if(2*y>z*(z-1)) { //If-statement that changes to max number of edges when lowering number of nodes
					if (z==3){ //Exception that makes edges 2 instead of 3
						y = 2;	
					}
					else {
						y = (z*(z-1)/2);
					}
					f2.setText(number(y));
				}
			}
			public void mousePressed(MouseEvent e) {
				x = 2;
				if(2*y>z*(z-1)) { //If-statement that changes to max number of edges when lowering number of nodes
					if (z==3){ //Exception that makes edges 2 instead of 3
						y = 2;	
					}
					else {
						y = (z*(z-1)/2);
					}
					f2.setText(number(y));
				}
				else {
					timer.start();
				}
			}
			public void mouseClicked(MouseEvent e) {
				if (z<21 && z>2){ //If-statement so that number of nodes does not exceed 2
					z--;
					f1.setText(number(z));
				}
				if(2*y>z*(z-1)) { //If-statement that changes to max number of edges when lowering number of nodes
					if (z==3){ //Exception that makes edges 2 instead of 3
						y = 2;	
					}
					else {
						y = (z*(z-1)/2);
					}
					f2.setText(number(y));
				}
			}
		});
		
		D3.addMouseListener(new MouseListener() { //2nd HIGHER NODE BUTTON
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
				mouseReleased(e);
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
				if (y>=z*(z-1)/2) { //If-statement thats displays a warning when the number of edges is too high
					if (z==3){ //Exception that makes edges 2 instead of 3
						y = 2;	
					}
					else {
						y = (z*(z-1)/2);
					}
					f2.setText(number(y));
				}
			}
			public void mousePressed(MouseEvent e) {
				x = 3;
				if (y>=z*(z-1)/2) { //If-statement thats displays a warning when the number of edges is too high
					if (z==3){ //Exception that makes edges 2 instead of 3
						y = 2;	
					}
					else {
						y = (z*(z-1)/2);
					}
					f2.setText(number(y));
				}
				else {
					timer.start();
				}
			}
			public void mouseClicked(MouseEvent e) {
				if (y<190 && y>-1 && y<z*(z-1)/2){ //If-statement so that number of edges does not exceed 190
					y++;
					f2.setText(number(y));
				}
				if (y>=z*(z-1)/2) { //If-statement thats displays a warning when the number of edges is too high
					if (z==3){ //Exception that makes edges 2 instead of 3
						y = 2;	
					}
					else {
						y = (z*(z-1)/2);
					}
					f2.setText(number(y));
				}
			}
		});
		
		D4.addMouseListener(new MouseListener() { //2nd LOWER NODE BUTTON
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
				mouseReleased(e);
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
				if (y<z-1) { //If-statement that increases the number of edges if this is too low compared to the number of nodes
					y = z-1;
					f2.setText(number(y));
				}
			}
			public void mousePressed(MouseEvent e) {
				x = 4;
				if (y<z-1) { //If-statement that increases the number of edges if this is too low compared to the number of nodes
					y = z-1;
					f2.setText(number(y));
				}
				else {
					timer.start();
				}
			}
			public void mouseClicked(MouseEvent e) {
				if (y<191 && y>2){ //If-statement so that number of edges does not exceed 2
					y--;
					f2.setText(number(y));
				}
				if (y<z-1) { //If-statement that increases the number of edges if this is too low compared to the number of nodes
					y = z-1;
					f2.setText(number(y));
				}
			}
		});

		/*Continue button*/
		D5.addMouseListener(new MouseListener(){ //CONTINUE BUTTON CLICK
			@Override
			public void mouseEntered(MouseEvent e) {
				D5.setBackground(new Color(60,180,75));
			}
			public void mouseExited(MouseEvent e) {
				D5.setBackground(Color.green);
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				GraphFrame.createWindow();
				SecondWindow.dispose();
			}
		});
		
		/*Back button*/
		Dt4.addMouseListener(new MouseListener() { //BACK BUTTON CLICK
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				Menu.createWindow();
				SecondWindow.dispose();
			}
		});
		
		/*Displaying the window*/
		SecondWindow.setLayout(new BorderLayout());
		SecondWindow.setResizable(false); //Window can't be resized
		SecondWindow.setBounds(240,60,960,720);
		
		/*Adding objects to the window*/
		SecondWindow.add(f1);
		SecondWindow.add(D);
		SecondWindow.add(D1);
		SecondWindow.add(f2);
		SecondWindow.add(D3);
		SecondWindow.add(D4);
		SecondWindow.add(D5);
		SecondWindow.add(Dt4);
		SecondWindow.add(Title);
		SecondWindow.add(Node);
		SecondWindow.add(Node1);
		SecondWindow.add(background);
		
		SecondWindow.setVisible(true);  //visibility of the window
	}
	private static ActionListener getButtonAction(JTextField f1, JTextField f2) {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(x==1){
            		if (z<20 && z>1){ //If-statement so that number of nodes does not exceed 20
            			z++;
            			f1.setText(number(z));
            		}
				}
				if(x==2){
					if (z<21 && z>2){ //If-statement so that number of nodes does not exceed 2
						z--;
						f1.setText(number(z));
					}
				}
				if(x==3){
					if (y<190 && y>1){ //If-statement so that number of edges does not exceed 190
						y++;
						f2.setText(number(y));
					}
				}
				if(x==4){
					if (y<191 && y>2){ //If-statement so that number of edges does not exceed 2
						y--;
						f2.setText(number(y));
					}
				}
            }
        };
        return action;
    }
	public static String number(int x){
		String sequence = Integer.toString(x);
		return sequence;
	}
}