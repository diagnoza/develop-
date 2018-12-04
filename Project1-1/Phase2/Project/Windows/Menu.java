/**COMPILE WITH "javac -classpath . *.java" **/

package Windows;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.border.*;

public class Menu extends MouseAdapter {
	public static boolean pageOne = true;
	public static boolean pageTwo = false;
	
	public static void createWindow() {
		/*Creating the window*/
		JFrame mainWindow = new JFrame("Main Menu");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*FIRST PAGE*/
		JLabel Title = new JLabel(); //TITLE
		Title.setText("Game Title");
		Title.setFont(Title.getFont().deriveFont(96.0f));
		Title.setForeground(Color.yellow);
		Title.setVisible(pageOne);
		Title.setBounds(240,25,2000,100);
		
		JButton Bt = new JButton(); //PLAY-BUTTON
		Bt.setBounds(380,200,200,100); //setBounds(x,y,width,height)
		Bt.setVisible(pageOne);
		Bt.setText("Play");
		Bt.setBackground(Color.green);
		Bt.setOpaque(true);
		Bt.setBorderPainted(false);
		Bt.setFont(Bt.getFont().deriveFont(36.0f));
		
		JButton Bt2 = new JButton(); //QUIT-BUTTON
		Bt2.setBounds(380,350,200,100); //setBounds(x,y,width,height)
		Bt2.setVisible(pageOne);
		Bt2.setText("Quit");
		Bt2.setBackground(Color.blue);
		Bt2.setOpaque(true);
		Bt2.setBorderPainted(false);
		Bt2.setFont(Bt2.getFont().deriveFont(36.0f));
		Bt2.addActionListener(new ActionListener() {
				@Override //Overrides the super-class method
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
		});
		
		JLabel background = new JLabel(new ImageIcon("Windows/Backgrounds/NC2.jpg"));
		background.setVisible(true);
		mainWindow.setSize(959,719);
		mainWindow.setSize(960,720);
		background.setLayout(new FlowLayout());
		
		/*SECOND PAGE*/
		JLabel Title2 = new JLabel(); //TITLE2
		Title2.setText("Select gamemode");
		Title2.setFont(Title2.getFont().deriveFont(96.0f));
		Title2.setForeground(Color.yellow);
		Title2.setVisible(pageTwo);
		Title2.setBounds(80,10,2000,120);
		
		JLabel Info1 = new JLabel(); //EXPLANATION1
		Info1.setText(" ");
		Info1.setFont(Info1.getFont().deriveFont(18.0f));
		Info1.setForeground(Color.white);
		Info1.setVisible(pageTwo);
		Info1.setBounds(550,180,360,100);
			
		JLabel Info2 = new JLabel(); //EXPLANATION2
		Info2.setText(" ");
		Info2.setFont(Info2.getFont().deriveFont(18.0f));
		Info2.setForeground(Color.white);
		Info2.setVisible(pageTwo);
		Info2.setBounds(550,180,360,100);
		
		JLabel Info3 = new JLabel(); //EXPLANATION3
		Info3.setText(" ");
		Info3.setFont(Info3.getFont().deriveFont(18.0f));
		Info3.setForeground(Color.white);
		Info3.setVisible(pageTwo);
		Info3.setBounds(550,166,360,150);
		
		JButton Ct = new JButton(); //GAMEMODE1
		Ct.setBounds(100,200,300,100); //setBounds(x,y,width,height)
		Ct.setVisible(pageTwo);
		Ct.setText("To the bitter end");
		Ct.setBackground(Color.red);
		Ct.setOpaque(true);
		Ct.setBorder(new LineBorder(Color.BLACK));
		Ct.setFont(Ct.getFont().deriveFont(32.0f));
		Ct.addActionListener(new ActionListener() {
				@Override //Overrides the super-class method
				public void actionPerformed(ActionEvent e) {
					//Add actions here
				}
		});
		
		JButton Ct2 = new JButton(); //GAMEMODE2
		Ct2.setBounds(100,350,300,100); //setBounds(x,y,width,height)
		Ct2.setVisible(pageTwo);
		Ct2.setText("Best upper bound");
		Ct2.setBackground(Color.red);
		Ct2.setOpaque(true);
		Ct2.setBorder(new LineBorder(Color.BLACK));
		Ct2.setFont(Ct2.getFont().deriveFont(32.0f));
		Ct2.addActionListener(new ActionListener() {
				@Override //Overrides the super-class method
				public void actionPerformed(ActionEvent e) {
					//Add actions here
				}
		});
		
		JButton Ct3 = new JButton(); //GAMEMODE3
		Ct3.setBounds(100,500,300,100); //setBounds(x,y,width,height)
		Ct3.setVisible(pageTwo);
		Ct3.setText("Random number");
		Ct3.setBackground(Color.red);
		Ct3.setOpaque(true);
		Ct3.setBorder(new LineBorder(Color.BLACK));
		Ct3.setFont(Ct3.getFont().deriveFont(32.0f));
		Ct3.addActionListener(new ActionListener() {
				@Override //Overrides the super-class method
				public void actionPerformed(ActionEvent e) {
					//Add actions here
				}
		});
		
		JButton Ct4 = new JButton(); //BACK-BUTTON
		Ct4.setBounds(814,614,140,70); //setBounds(x,y,width,height)
		Ct4.setVisible(pageTwo);
		Ct4.setOpaque(true);
		Ct4.setText("Back");
		Ct4.setFont(Ct4.getFont().deriveFont(36.0f));
		
		JButton Ct5 = new JButton(); //INFO-BUTTON1
		Ct5.setBounds(400,200,100,100); //setBounds(x,y,width,height)
		Ct5.setVisible(pageTwo);
		Ct5.setText("?");
		Ct5.setBackground(Color.green);
		Ct5.setOpaque(true);
		Ct5.setEnabled(false);
		Ct5.setFont(Ct5.getFont().deriveFont(36.0f));
		
		JButton Ct6= new JButton(); //INFO-BUTTON2
		Ct6.setBounds(400,350,100,100); //setBounds(x,y,width,height)
		Ct6.setVisible(pageTwo);
		Ct6.setText("?");
		Ct6.setBackground(Color.green);
		Ct6.setOpaque(true);
		Ct6.setEnabled(false);
		Ct6.setFont(Ct6.getFont().deriveFont(36.0f));
		
		JButton Ct7= new JButton(); //INFO-BUTTON3
		Ct7.setBounds(400,500,100,100); //setBounds(x,y,width,height)
		Ct7.setVisible(pageTwo);
		Ct7.setText("?");
		Ct7.setBackground(Color.green);
		Ct7.setOpaque(true);
		Ct7.setEnabled(false);
		Ct7.setFont(Ct7.getFont().deriveFont(36.0f));
		
		/*Changing pages*/
		Bt.addMouseListener(new MouseListener() { //PLAY BUTTON
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
					pageOne = false;
					pageTwo = true;
					visi(Title,Bt,Bt2,Ct,Ct2,Ct3,Ct4,Ct5,Ct6,Ct7,Title2,Info1,Info2,Info3);
				}
		});
		Ct4.addActionListener(new ActionListener() { //BACK-BUTTON
				@Override //Overrides the super-class method
				public void actionPerformed(ActionEvent e) {
					pageOne = true;
					pageTwo = false;
					visi(Title,Bt,Bt2,Ct,Ct2,Ct3,Ct4,Ct5,Ct6,Ct7,Title2,Info1,Info2,Info3);
				}
		});
		
		/*INFO ABOUT GAMEMODES*/
		Ct5.addMouseListener(new MouseListener() { //INFO-BUTTON1
				@Override //Overrides the super-class method
				public void mouseEntered(MouseEvent e) {
					Info1.setText("<html>TO THE BITTER END: <br>Find the Chromatic Number of the graph.</html>");
				}
				public void mouseExited(MouseEvent e) {
					Info1.setText(" ");
				}
				public void mouseReleased(MouseEvent e) {
				}
				public void mousePressed(MouseEvent e) {
				}
				public void mouseClicked(MouseEvent e) {
				}
		});
		
		Ct6.addMouseListener(new MouseListener() { //INFO-BUTTON2
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
				Info2.setText("<html>BEST UPPER BOUND IN A FIXED TIME FRAME: <br>Please color the graph in the given time.</html>");
			}
			public void mouseExited(MouseEvent e) {
				Info2.setText(" ");
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});

		Ct7.addMouseListener(new MouseListener() { //INFO-BUTTON3
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
				Info3.setText("<html>RANDOM ORDER: <br>Please color a random graph.<br>Once the color of the vertices is chosen, you can't change the color it again.</html>");
			}
			public void mouseExited(MouseEvent e) {
				Info3.setText(" ");
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		/*GAMEMODES*/
		Ct.addMouseListener(new MouseListener() { //GAMEMODE1 BUTTON
				@Override //Overrides the super-class method
				public void mouseEntered(MouseEvent e) {
					Ct.setBackground(Color.orange);
				}
				public void mouseExited(MouseEvent e) {
					Ct.setBackground(Color.red);
				}
				public void mouseReleased(MouseEvent e) {
				}
				public void mousePressed(MouseEvent e) {
				}
				public void mouseClicked(MouseEvent e) {
					SecondFrame.createWindow(1);
					mainWindow.dispose();
				}
		});
		Ct2.addMouseListener(new MouseListener() { //GAMEMODE2 BUTTON
				@Override //Overrides the super-class method
				public void mouseEntered(MouseEvent e) {
					Ct2.setBackground(Color.orange);
				}
				public void mouseExited(MouseEvent e) {
					Ct2.setBackground(Color.red);
				}
				public void mouseReleased(MouseEvent e) {
				}
				public void mousePressed(MouseEvent e) {
				}
				public void mouseClicked(MouseEvent e) {
					SecondFrame.createWindow(2);
					mainWindow.dispose();
				}
		});
		Ct3.addMouseListener(new MouseListener() { //GAMEMODE3 BUTTON
				@Override //Overrides the super-class method
				public void mouseEntered(MouseEvent e) {
					Ct3.setBackground(Color.orange);
				}
				public void mouseExited(MouseEvent e) {
					Ct3.setBackground(Color.red);
				}
				public void mouseReleased(MouseEvent e) {
				}
				public void mousePressed(MouseEvent e) {
				}
				public void mouseClicked(MouseEvent e) {
					SecondFrame.createWindow(3);
					mainWindow.dispose();
				}
		});
		
		/*Displaying the window*/         
		mainWindow.setLayout(new BorderLayout()); //Give the window a borderlayout)
												   //Dividing the window in 5 region(North,east,south,west,center)
		mainWindow.setResizable(false); //Window can't be resized
		/**mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);  //fullscreen */
		mainWindow.setBounds(240,60,960,720);
		
		/*Adding the objects to the window*/
		mainWindow.add(Title); //Adding the titles
		mainWindow.add(Title2);
		mainWindow.add(Bt); //Adding the buttons
		mainWindow.add(Bt2);
		mainWindow.add(Ct);
		mainWindow.add(Ct2);
		mainWindow.add(Ct3);
		mainWindow.add(Ct4);
		mainWindow.add(Ct5);
		mainWindow.add(Ct6);
		mainWindow.add(Ct7);
		mainWindow.add(Info1);
		mainWindow.add(Info2);
		mainWindow.add(Info3);
		mainWindow.add(background); //Adding the background
		mainWindow.setVisible(true);  //visibility of the window
		
		
	}
	public static void visi(JLabel Title,JButton Bt,JButton Bt2,JButton Ct,
		JButton Ct2,JButton Ct3,JButton Ct4,JButton Ct5,JButton Ct6,JButton Ct7,
		JLabel Title2,JLabel Info1,JLabel Info2,JLabel Info3){
		Title.setVisible(pageOne);
		Bt.setVisible(pageOne);
		Bt2.setVisible(pageOne);
		Ct.setVisible(pageTwo);
		Ct2.setVisible(pageTwo);
		Ct3.setVisible(pageTwo);
		Ct4.setVisible(pageTwo);
		Ct5.setVisible(pageTwo);
		Ct6.setVisible(pageTwo);
		Ct7.setVisible(pageTwo);
		Title2.setVisible(pageTwo);
		Info1.setVisible(pageTwo);
		Info2.setVisible(pageTwo);
		Info3.setVisible(pageTwo);
	}
}
