package townofwinchester;

import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import org.apache.logging.log4j.*;

/*
 * GUI. Does stuff.
 *
 * @author David Mao
 * @author James Spina
 * @author Roman Wicky
 *
 * TODO:
 * Replace villager with a role variable and timer with an actual timer, and make all the inputs work
 *
 */

public class MyFrame extends javax.swing.JFrame{

    // these are the components we need.
    private final JSplitPane splitPane;  // split the window in top and bottom
    private final JPanel topPanel;       // container panel for the top
    private final JPanel bottomPanel;    // container panel for the bottom
    private final JScrollPane scrollPane; // makes the text scrollable
    private final JTextArea textArea;     // the text
    private final JPanel inputPanel;      // under the text a container for all the input elements
    private final JTextField textField;   // a textField for the text the user inputs
    private final JButton button;         // and a "send" button
    private final JLabel role;           //role of player
    private final JLabel counter;        //amount of time left
    private final int people = 7;         //add # of people here?
    private final ArrayList<String> characterImages;       // List of all character images

    public MyFrame(){
        characterImages = new ArrayList<String>();
      
        splitPane = new JSplitPane();

        topPanel = new JPanel();         //top component
        bottomPanel = new JPanel();      //bottom component
        
        ClassLoader classLoader = getClass().getClassLoader();
        final File folder = new File(classLoader.getResource("Images").getFile());
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
            LogManager.getLogger(TownOfWinchester.SHORT).info(file.getFileName());

        for (File file : listOfFiles) {
            if (file.isFile()) {
                characterImages.add(file.getPath());
            }
        }
        
        //text area
        scrollPane = new JScrollPane();
        textArea = new JTextArea();

        //input components
        inputPanel = new JPanel();
        textField = new JTextField();
        button = new JButton("send");

        //window
        setPreferredSize(new Dimension(Math.max(800, 120*people + 100), 600));     //initial window is 800x600 pixels, though width expands as needed
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(splitPane);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(200);                    //initial position of the divider is 200 (window is 600 pixels high)
        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);

        //topPanel
        GridBagLayout testLayout = new GridBagLayout();
        topPanel.setLayout(testLayout);
        GridBagConstraints c = new GridBagConstraints();
        topPanel.setBackground(Color.WHITE);

        for(int i = 0; i < people; i++){                      //makes buttons
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i;
            c.gridy = 1;
            JButton a = new JButton("Person " + (i+1));
            a.setPreferredSize(new Dimension(120, 40));
            topPanel.add(a, c);
        }

        for(int j = 0; j < people; j++){                      //makes pictures **TEMP**
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = j;
            c.gridy = 2;
            JLabel b = new JLabel("       Picture " + (j+1));
            b.setPreferredSize(new Dimension(100, 40));
            topPanel.add(b, c);
        }

        role = new JLabel("Villager", SwingConstants.LEADING);
        role.setFont(new Font("Inconsolata", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 50; //increase height of the title
        c.weightx = 0;
        c.gridwidth = 0;
        c.gridx = 0;
        c.gridy = 0;
        topPanel.add(role, c);

        counter = new JLabel("Counter - 10:00", SwingConstants.TRAILING);
        counter.setFont(new Font("Inconsolata", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 50; //increase height of the title
        c.weightx = 0;
        c.gridwidth = 0;
        c.gridx = 0;
        c.gridy = 0;
        topPanel.add(counter, c);

        //bottomPanel
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); //bottomPanel is arranged vertically

        bottomPanel.add(scrollPane);
        scrollPane.setViewportView(textArea);
        bottomPanel.add(inputPanel);

        //max size of the inputPanel (so it doesn't get too big when the user resizes the window)
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));     //max height: 100    max width: a big ass number
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        inputPanel.add(textField);        //left is the textField
        inputPanel.add(button);           //right is the "send" button

        pack();   //makes sure every layout and size we just defined gets applied before the stuff becomes visible
    }

    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new MyFrame().setVisible(true);
            }
        });
    }
    
}