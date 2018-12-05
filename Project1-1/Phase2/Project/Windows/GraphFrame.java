
package Windows;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*; 

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
    public static final RandomOrder test = new RandomOrder(SecondFrame.z,SecondFrame.y);

    //creates a new connection array for the created graph
    public static boolean[][] test2 = test.createGraph();

    //defines a new JFrame to be accessible by GraphDisplay 
    public static JFrame graphWindow;

    //initializes the array of displayable vertices
    public static Ellipse2D[] verticesGraphically = new Ellipse2D[GraphFrame.test.vertices];

    //Creating a variable for the time-limit
    private static javax.swing.Timer timer;
	public static int i = 179; //start time
	
    public static void createWindow(){
        graphWindow = new JFrame("Display graph");
        graphWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphWindow.setSize(1000,600);
        
        //initializes new graph
        componentGraph = new GraphDisplay();
        //initializes main panel
        JPanel graphPanel = new JPanel(new BorderLayout());

        //adds left part of the panel (color pickers etc)
        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setPreferredSize(new Dimension(200,600));

        //adds central part of the panel (displaying graph)
        JPanel centralMenuPanel = new JPanel();
        centralMenuPanel.setPreferredSize(new Dimension(600,600));

        //adds right part of the panel (TBD)
        JPanel rightMenuPanel = new JPanel();
        rightMenuPanel.setPreferredSize(new Dimension(200,600));

        //testButton2 was just for testing the right side of the panel
        JButton testButton2 = new JButton("I'm a test too");

        //creates the GridLayout to put color changing buttons in
        GridLayout colorPickerLayout = new GridLayout(4,5);
        
        //set the distances between buttons
        colorPickerLayout.setHgap(5);
        colorPickerLayout.setVgap(5);

        //initializes the colorPickerPanel using the colorPickrLayout
        JPanel colorPickerPanel = new JPanel(colorPickerLayout);
        
        JLabel colorPickerLabel = new JLabel("Colors:");
        colorPickerLabel.setFont(colorPickerLabel.getFont().deriveFont(24.0f));
        leftMenuPanel.add(colorPickerLabel);

        //adds unified definition of button size for all colorPickerPanel buttons
        Dimension buttonSize = new Dimension(30,30);

        //initializes all buttons and sets their size & opaqueness to add colors
        for(int i=0;i<21;i++){
            colors[i] = new JButton();
            colors[i].setOpaque(true);
            colors[i].setBorderPainted(false);
            colors[i].setPreferredSize(buttonSize);
            colors[i].putClientProperty("index", i);
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
        JLabel currentColorLabel = new JLabel("Current color");
        currentColorLabel.setFont(colorPickerLabel.getFont().deriveFont(24.0f));

        //adds the label that displays the currently selected color
        JLabel currentColor = new JLabel();
        currentColor.setBackground(Color.BLACK);
        currentColor.setForeground(Color.BLACK);
        currentColor.setOpaque(true);

        //sets the size of the label displaying current color
        currentColor.setPreferredSize(new Dimension(50, 50));

        //those two lines add the label for the currently selected color and the currently selected color
        leftMenuPanel.add(currentColorLabel);
        leftMenuPanel.add(currentColor);
        
        JLabel timeLabel = new JLabel("180");
		timeLabel.setFont(new Font("Tahoma",Font.BOLD,16));
        
        timer = new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent e){
				timeLabel.setText(Integer.toString(i));
				i--;
				if(i<0){
					JOptionPane.showMessageDialog(null,"TIME UP!!Try Again","Game End",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
		timer.start();
		
		leftMenuPanel.add(timeLabel);

        //adds the test button to the right panel, because nothing else to display there yet
        rightMenuPanel.add(testButton2);

        graphPanel.add(leftMenuPanel,BorderLayout.LINE_START);
                
        graphPanel.add(componentGraph,BorderLayout.CENTER);

        graphPanel.add(rightMenuPanel,BorderLayout.LINE_END);

        graphWindow.add(graphPanel);
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
}