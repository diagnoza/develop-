/**COMPILE WITH "javac -classpath . *.java" **/

package Windows;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

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
		
		/*TEXT FIELDS*/
		JTextField f1 = new JTextField(1); //TEXTFIELD1
		f1.setBounds(400,200,75,75);
		f1.setBackground(Color.white);
		f1.setText(number(z));
		f1.setEditable(false);
		f1.setFont(f1.getFont().deriveFont(36.0f));
		f1.setVisible(true);
		
		JTextField f2 = new JTextField(1); //TEXTFIELD1
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
		JButton D = new JButton(); //
		D.setBounds(475,200,50,37); //setBounds(x,y,width,height)
		D.setVisible(true);
		D.setText("<html>&#8963;</html>");
		D.setFont(D.getFont().deriveFont(16.0f));
		
		JButton D1 = new JButton(); //
		D1.setBounds(475,238,50,37); //setBounds(x,y,width,height)
		D1.setVisible(true);
		D1.setText("<html>&#8964;</html>");
		D1.setFont(D1.getFont().deriveFont(16.0f));
		
		JButton D3 = new JButton(); //
		D3.setBounds(475,350,50,37); //setBounds(x,y,width,height)
		D3.setVisible(true);
		D3.setText("<html>&#8963;</html>");
		D3.setFont(D3.getFont().deriveFont(16.0f));
		
		JButton D4 = new JButton(); //
		D4.setBounds(475,388,50,37); //setBounds(x,y,width,height)
		D4.setVisible(true);
		D4.setText("<html>&#8964;</html>");
		D4.setFont(D4.getFont().deriveFont(16.0f));
		
		JButton D5 = new JButton(); //
		D5.setBounds(313,488,300,100); //setBounds(x,y,width,height)
		D5.setVisible(true);
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
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
			}
			public void mousePressed(MouseEvent e) {
				x = 1;
				timer.start();
			}
			public void mouseClicked(MouseEvent e) {
				if (z<20 && z>-1){ //If-statement so that number of nodes does not exceed 20
					z++;
					f1.setText(number(z));
				}
			}
		});
		
		D1.addMouseListener(new MouseListener() { //1st LOWER NODE BUTTON
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
			}
			public void mousePressed(MouseEvent e) {
				x = 2;
				timer.start();
			}
			public void mouseClicked(MouseEvent e) {
				if (z<21 && z>0){ //If-statement so that number of nodes does not exceed 0
					z--;
					f1.setText(number(z));
				}
				if(2*y>z*(z-1)) {
					y=z*(z-1)/2;
					f2.setText(number(y));
				}
			}
		});
		
		D3.addMouseListener(new MouseListener() { //2nd HIGHER NODE BUTTON
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
			}
			public void mousePressed(MouseEvent e) {
				x = 3;
				timer.start();
			}
			public void mouseClicked(MouseEvent e) {
				if (y<60 && y>-1){ //If-statement so that number of nodes does not exceed 20
					y++;
					f2.setText(number(y));
				}
			}
		});
		
		D4.addMouseListener(new MouseListener() { //2nd LOWER NODE BUTTON
			@Override //Overrides the super-class method
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
				timer.stop();
			}
			public void mousePressed(MouseEvent e) {
				x = 4;
				timer.start();
			}
			public void mouseClicked(MouseEvent e) {
				if (y<61 && y>0){ //If-statement so that number of nodes does not exceed 0
					y--;
					f2.setText(number(y));
				}
			}
		});

		D5.addMouseListener(new MouseListener(){
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
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
		
		SecondWindow.setLayout(new BorderLayout());
		SecondWindow.setResizable(false); //Window can't be resized
		SecondWindow.setVisible(true);  //visibility of the window
		SecondWindow.setBounds(240,60,960,720);
		
		SecondWindow.add(f1);
		SecondWindow.add(D);
		SecondWindow.add(D1);
		SecondWindow.add(f2);
		SecondWindow.add(D3);
		SecondWindow.add(D4);
		SecondWindow.add(D5);
		SecondWindow.add(Dt4);
		SecondWindow.add(background);
	}
	private static ActionListener getButtonAction(JTextField f1, JTextField f2) {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(x==1){
            		if (z<20 && z>-1){ //If-statement so that number of nodes does not exceed 20
            			z++;
            			f1.setText(number(z));
            		}
				}
				if(x==2){
					if (z<21 && z>0){ //If-statement so that number of nodes does not exceed 0
						z--;
						f1.setText(number(z));
					}
				}
				if(x==3){
					if (y<60 && y>-1){ //If-statement so that number of nodes does not exceed 20
						y++;
						f2.setText(number(y));
					}
				}
				if(x==4){
					if (y<61 && y>0){ //If-statement so that number of nodes does not exceed 0
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