import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.border.*;

public class GraphFrame{
    //sets the index of the JButton colors[] array to 0
    public static int colorIndex = 0;
    
    //initializes the colorArray that keeps track of how the vertices are colored
    public static int[] colorArray = new int[20];

    //initializes the coloring buttons array
    public static final JButton[] colors = new JButton[21];

    //defines a JComponent of type GraphDisplay
    public static GraphDisplay componentGraph;

    //creates a new random graph
    public static RandomOrder test;

    //creates a new connection array for the created graph
    public static boolean[][] test2;

    //defines a new JFrame to be accessible by GraphDisplay 
    public static JFrame graphWindow;

    //initializes the array of displayable vertices
    public static Ellipse2D[] verticesGraphically;
    
    public static ColEdge[] e;

    public static int chromaticNumber;

    public static int colorsCounter;

    public static JLabel showChromatic;

    public static JButton Hint2 = new JButton("Hint2");
    
    public static JLabel timeLabel;

    //Creating a variable for the time-limit
    private static javax.swing.Timer timer;
	public static int i = 0; //start time
	
    public static void createWindow(){
    	resetWindow();
    	showChromatic.setVisible(false);
        graphWindow = new JFrame("Display graph");
        graphWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphWindow.setSize(1000,600);
        
        //initializes new graph
        componentGraph = new GraphDisplay();
        //initializes main panel
        JPanel graphPanel = new JPanel(new BorderLayout());

        //adds left part of the panel (color pickers etc)
        JPanel leftMenuPanel = new JPanel(new GridLayout(5,1));
        leftMenuPanel.setPreferredSize(new Dimension(180,600));

        //adds central part of the panel (displaying graph)
        JPanel centralMenuPanel = new JPanel();
        centralMenuPanel.setPreferredSize(new Dimension(600,600));

        //adds right part of the panel (TBD)
        JPanel rightMenuPanel = new JPanel(new FlowLayout());
        rightMenuPanel.setPreferredSize(new Dimension(220,600));
        
        //testButton2 was just for testing the right side of the panel
        JButton Hint1 = new JButton("Hint1");
        Hint1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChromatic.setVisible(true);
            }
        });

        Hint2 = new JButton("Hint2");
        Hint2.setEnabled(false);
        Hint2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphFrame.graphWindow.repaint();
                GraphFrame.graphWindow.revalidate();
            }
        });
        JButton Hint3 = new JButton("Hint3");
        JButton Done = new JButton("Done");
        Done.setPreferredSize(new Dimension(180,80));
        Done.setBackground(new Color(60,180,75));
        Done.setForeground(Color.WHITE);
        Done.setOpaque(true);
        Done.setBorderPainted(false);
        Done.setFont(Done.getFont().deriveFont(28.0f));
        Done.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e) {
				Done.setBackground(new Color(79,187,93));
			}
			public void mouseExited(MouseEvent e) {
				Done.setBackground(new Color(60,180,75));
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				System.out.println(colorsCounter);
				System.out.println(chromaticNumber);
				System.out.println(GraphDisplay.isRed);
				if (colorsCounter == chromaticNumber && !GraphDisplay.isRed){
					timer.stop();
                   		 JOptionPane.showMessageDialog(null,
                    	        "Done indeed!\n" +
                                    "It took you " + timeLabel.getText() + " seconds.",
                                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
						graphWindow.dispose();
						graphWindow = null;
						Menu.createWindow();
				}
				else JOptionPane.showMessageDialog(null,
                        "whoa dud, \n" +
                                "you not done yet, keep playin this awesome game",
                        "Error!", JOptionPane.WARNING_MESSAGE);
			}
        });

        //creates the GridLayout to put color changing buttons in
        GridLayout colorPickerLayout = new GridLayout(4,5);
        
        //set the distances between buttons
        colorPickerLayout.setHgap(5);
        colorPickerLayout.setVgap(5);

        //initializes the colorPickerPanel using the colorPickerLayout
        JPanel colorPickerPanel = new JPanel(colorPickerLayout);
        colorPickerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        
        JLabel colorPickerLabel = new JLabel("Colors:");
        colorPickerLabel.setHorizontalAlignment(JLabel.CENTER);
        colorPickerLabel.setFont(colorPickerLabel.getFont().deriveFont(24.0f));
        leftMenuPanel.add(colorPickerLabel);

        //adds unified definition of button size for all colorPickerPanel buttons
        Dimension buttonSize = new Dimension(30,30);

        //initializes all buttons and sets their size & opaqueness to add colors
        for(int i=0;i<21;i++){
            colors[i] = new JButton();
            colors[i].setOpaque(true);
            colors[i].setBorderPainted(true);
            colors[i].setPreferredSize(buttonSize);
            colors[i].putClientProperty("index", i);
            colors[i].setBorder(new LineBorder(Color.BLACK));
        }
        
        /*those lines set the background of the buttons.
        They are also used later when referring from GraphDisplay using colorArray and colorIndex*/
        colors[0].setBackground(Color.BLACK);
        colors[1].setBackground(new Color(230,25,75));
        colors[2].setBackground(new Color(60,180,75));
        colors[3].setBackground(new Color(255,225,25));
        colors[4].setBackground(new Color(0,130,200));
        colors[5].setBackground(new Color(245,130,48));
        colors[6].setBackground(new Color(145,30,180));
        colors[7].setBackground(new Color(70,240,240));
        colors[8].setBackground(new Color(240,50,230));
        colors[9].setBackground(new Color(210,245,60));
        colors[10].setBackground(new Color(250,190,190));
        colors[11].setBackground(new Color(0,128,128));
        colors[12].setBackground(new Color(230,190,255));
        colors[13].setBackground(new Color(170,110,40));
        colors[14].setBackground(new Color(255,250,200));
        colors[15].setBackground(new Color(128,0,0));
        colors[16].setBackground(new Color(170,255,195));
        colors[17].setBackground(new Color(128,128,0));
        colors[18].setBackground(new Color(255,215,180));
        colors[19].setBackground(new Color(0,0,128));
        colors[20].setBackground(new Color(128,128,0));

        //loop that adds all colors from the colorpicker to the colorPickerPanel
        for(int i=1;i<21;i++)
            colorPickerPanel.add(colors[i]);

        //adds colorPickerPanel to the leftMenuPanel, below the "Colors" label
        leftMenuPanel.add(colorPickerPanel);

        //sets the text and font size of the label above the currently selected color
        JLabel currentColorLabel = new JLabel("Current color:");
        currentColorLabel.setHorizontalAlignment(JLabel.CENTER);
        currentColorLabel.setFont(colorPickerLabel.getFont().deriveFont(24.0f));
        
        JPanel currentColorPanel = new JPanel();
        currentColorPanel.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        //adds the label that displays the currently selected color
        JLabel currentColor = new JLabel();
        currentColor.setBackground(Color.BLACK);
        currentColor.setForeground(Color.BLACK);
        currentColor.setOpaque(true);
        currentColor.setBorder(new LineBorder(Color.BLACK));
        currentColorPanel.add(currentColor);

        //sets the size of the label displaying current color
        currentColor.setPreferredSize(new Dimension(70, 70));
        currentColor.setMaximumSize(new Dimension(70,70));
        currentColor.setMinimumSize(new Dimension(70,70));

        //those two lines add the label for the currently selected color and the currently selected color
        leftMenuPanel.add(currentColorLabel);
        leftMenuPanel.add(currentColorPanel);
        
        timeLabel = new JLabel("Time used: 0");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setFont(new Font("Tahoma",Font.BOLD,23));
        
        timer = new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent e){
				timeLabel.setText("Time used: " + Integer.toString(i));
				i++;
				if(i<0){
					JOptionPane.showMessageDialog(null,"TIME'S UP!!Try Again","Game End",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
		timer.start();
        
        leftMenuPanel.add(timeLabel);
        rightMenuPanel.add(Hint1);
        rightMenuPanel.add(Hint2);
        rightMenuPanel.add(Hint3);
        rightMenuPanel.add(showChromatic);
        rightMenuPanel.add(Done);

        JButton resignButton = new JButton("Resign");
        resignButton.setPreferredSize(new Dimension(180,80));
        resignButton.setBackground(new Color(52,98,216));
        resignButton.setForeground(Color.WHITE);
        resignButton.setOpaque(true);
        resignButton.setBorderPainted(false);
        resignButton.setFont(resignButton.getFont().deriveFont(28.0f));
        resignButton.addMouseListener(new MouseListener(){
            @Override
            public void mouseEntered(MouseEvent e) {
				resignButton.setBackground(new Color(52,114,216));
			}
			public void mouseExited(MouseEvent e) {
				resignButton.setBackground(new Color(52,98,216));
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				SecondFrame.createWindow(SecondFrame.GM);
				graphWindow.dispose();
			}
        });
        rightMenuPanel.add(resignButton,BorderLayout.SOUTH);

        graphPanel.add(leftMenuPanel,BorderLayout.LINE_START);
                
        graphPanel.add(componentGraph,BorderLayout.CENTER);

        graphPanel.add(rightMenuPanel,BorderLayout.LINE_END);

        graphWindow.add(graphPanel);
        graphWindow.setLocation(250,150);
        graphWindow.setResizable(false);
        graphWindow.setVisible(true);

        /*class that defines ButtonListener:
        -checking what currently clicked button is
        - setting the background of the label displaying currently selected button to the currently selected color
        - setting the foreground of the label displaying currently selected button to the currently selected color
        - setting the colorIndex to the index of the JButton colors[] array, to use later in GraphDisplay to color nodes
        */
        class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                JButton bl = (JButton) e.getSource();
                currentColor.setBackground(bl.getBackground());
                currentColor.setForeground(bl.getBackground());
                colorIndex = (int) bl.getClientProperty("index");
            }
        }
        //adds ActionListeners to all buttons from the panel allowing color selection
        for(int i=1;i<21;i++)
            colors[i].addActionListener(new ButtonListener());
    }
    public static void resetWindow(){
        componentGraph = null;
        colorArray = new int[20];
        test = new RandomOrder(SecondFrame.z, SecondFrame.y);
        test2 = test.createGraph();
        verticesGraphically = new Ellipse2D[test.getVertices()];
        e = Main.transform(SecondFrame.y, SecondFrame.z, test2);
        chromaticNumber = CalculateChromatic.getChromatic(SecondFrame.y, SecondFrame.z, e);
        showChromatic = new JLabel("Chromatic number = " + chromaticNumber);
        colorsCounter = 0;
        colorIndex=0;

    }
}
