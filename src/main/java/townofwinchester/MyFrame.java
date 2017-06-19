/*
 * MyFrame Class
 */

package townofwinchester;

import java.awt.*;
import javax.swing.*;
import java.lang.*;
//Following Imports are for Reading image files

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;
import org.apache.logging.log4j.*;

/*
 * GUI. Creates the user interface that the player will interact with.
 * @author Andy Ark
 * @author David Mao
 * @author Omar Grant
 * @author Roman 
 * @author James Spina
 * @author Tim Dalton
 *
 * TODO:
 * Replace villager with a role variable and timer with an actual timer, and make all the inputs work
 *
 */

public class MyFrame extends javax.swing.JFrame{

    // these are the components we need.
    private final JSplitPane splitPane; // split the window in top and bottom
    private final JPanel topPanel;      // container panel for the top
    private final JPanel bottomPanel;   // container panel for the bottom
    private final JScrollPane scrollPane;   // makes the text scrollable
    private final JTextArea textArea;   // the text
    private final JPanel inputPanel;    // under the text a container for all the input elements
    private final JTextField textField; // a textField for the text the user inputs
    private final JButton button;       // and a "send" button
    private final JLabel role;          // role of player
    private final JLabel counter;       //amount of time left
    private final int people = 7;       //add # of people here?
    private final java.util.List<Path> imagePaths;  // List of all character images
    private BufferedImage image;    // image variable used to hold images that will be drawn
    private GameTimer timer = new GameTimer(); //keeps track of night and day

    public MyFrame() {
        imagePaths = getPaths("/images");// read paths to image files

        splitPane = new JSplitPane();

        topPanel = new JPanel();         //top component
        bottomPanel = new JPanel();      //bottom component

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

        timer.startDayNightCountdown(60);
        counter = new JLabel("Time Left: " + timer.getDaySeconds() + " seconds", SwingConstants.TRAILING);
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
/*    
    public void readImage(int i){                                                                       //reads the image from files
        try{                                                                                            //try catch block necessary for reading images
        ClassLoader classLoader = getClass().getClassLoader();                                          //idk what this code does
        File f = new File(classLoader.getResource("images/" + characterImages.get(i)).getFile());       //pulls an image from characterImages list at location i
        image = ImageIO.read(f);                                                                        //reads the previously pulled file
        image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);    //constructor for BufferedImage class that sets image to the proper size and type


        }catch(IOException e){   //catches the excpetion
             System.out.println("Error: " + e);
        }
    }
*/    
    public void paintImage(Graphics g){
        g.drawImage(image, 50, 50, null);               //Numbers temporary until I figure out how to access c.gridx/y for the temp Pictures
    }

    public static void main(String args[])throws IOException{  //IOException is for reading images
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new MyFrame().setVisible(true);
            }
        });
    }

    /**
     * Read all file paths from directory and return them as a {@link java.util.List}.
     * Precondition: directory is not null, not empty, and must start with '/'.
     *
     * @param directory directory within resources directory
     * @return {@link java.util.List} of {@link Path}s.
     * @see <a href="https://stackoverflow.com/questions/1429172/how-do-i-list-the-files-inside-a-jar-file/39974405#39974405" target="_blank">https://stackoverflow.com/questions/1429172/</a>
     */
    private java.util.List<Path> getPaths(String directory) {
        assert directory != null && directory.length() > 0 && directory.charAt(0) == '/'
            : String.format("\"%s\" is null, or empty, or doesn't start with '/'", directory);
        LogManager.getLogger(TownOfWinchester.SHORT)
            .info("getPaths(\"{}\") =", directory);
        final java.util.List<Path> paths = new ArrayList<Path>();
        try {
            URI uri = MyFrame.class.getResource(directory).toURI();
            try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap()) : null)) {
                Path myPath = Paths.get(uri);
                Files.walkFileTree(myPath, new SimpleFileVisitor<Path>() { 
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        paths.add(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            System.exit(5); // I/O error
        }
        LogManager.getLogger(TownOfWinchester.SHORT)
            .info("{}.size() ({} files)", paths, paths.size());
        return paths;
    }
}