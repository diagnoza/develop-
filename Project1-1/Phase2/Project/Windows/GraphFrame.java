package Windows;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*; 

public class GraphFrame{
    public static int colorIndex = 0;
    public static int[] colorArray = new int[20];
    public static final JButton[] colors = new JButton[21];
    public static GraphDisplay componentGraph;
    public static final RandomOrder test = new RandomOrder(SecondFrame.z,SecondFrame.y);
    public static boolean[][] test2 = test.createGraph();
    public static JFrame graphWindow;
    public static void createWindow(){
        graphWindow = new JFrame("Display graph");
        graphWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphWindow.setSize(1000,600);
        
        componentGraph = new GraphDisplay();
        JPanel graphPanel = new JPanel(new BorderLayout());

        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setPreferredSize(new Dimension(200,600));

        JPanel centralMenuPanel = new JPanel();
        centralMenuPanel.setPreferredSize(new Dimension(600,600));

        JPanel rightMenuPanel = new JPanel();
        rightMenuPanel.setPreferredSize(new Dimension(200,600));

        JButton testButton2 = new JButton("I'm a test too");

        GridLayout colorPickerLayout = new GridLayout(4,5);
        colorPickerLayout.setHgap(5);
        colorPickerLayout.setVgap(5);
        JPanel colorPickerPanel = new JPanel(colorPickerLayout);
        
        JLabel colorPickerLabel = new JLabel("Colors:");
        colorPickerLabel.setFont(colorPickerLabel.getFont().deriveFont(24.0f));
        leftMenuPanel.add(colorPickerLabel);

        Dimension buttonSize = new Dimension(30,30);

        for(int i=0;i<21;i++){
            colors[i] = new JButton();
            colors[i].setOpaque(true);
            colors[i].setBorderPainted(false);
            colors[i].setPreferredSize(buttonSize);
            colors[i].putClientProperty("index", i);
        }
        
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
        for(int i=1;i<21;i++)
            colorPickerPanel.add(colors[i]);
        leftMenuPanel.add(colorPickerPanel);

        JLabel currentColorLabel = new JLabel("Current color");
        currentColorLabel.setFont(colorPickerLabel.getFont().deriveFont(24.0f));
        JLabel currentColor = new JLabel();
        currentColor.setBackground(Color.BLACK);
        currentColor.setForeground(Color.BLACK);
        currentColor.setOpaque(true);
        currentColor.setPreferredSize(new Dimension(50, 50));
        leftMenuPanel.add(currentColorLabel);
        leftMenuPanel.add(currentColor);

        rightMenuPanel.add(testButton2);

        graphPanel.add(leftMenuPanel,BorderLayout.LINE_START);
                
        graphPanel.add(componentGraph,BorderLayout.CENTER);

        graphPanel.add(rightMenuPanel,BorderLayout.LINE_END);

        graphWindow.add(graphPanel);
        graphWindow.setResizable(false);
        graphWindow.setVisible(true);

        class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                JButton bl = (JButton) e.getSource();
                currentColor.setBackground(bl.getBackground());
                currentColor.setForeground(bl.getBackground());
                colorIndex = (int) bl.getClientProperty("index");
            }
        }
        for(int i=1;i<21;i++)
            colors[i].addActionListener(new ButtonListener());
    }
}