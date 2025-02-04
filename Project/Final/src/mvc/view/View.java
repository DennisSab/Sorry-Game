package mvc.view;

import mvc.model.card.Card;
import mvc.model.pawn.Pawn;
import mvc.model.player.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;
import java.util.Scanner;
import mvc.model.turn.Turn;

public class View extends JFrame {

    private JButton FoldButton;
    private JButton DrawButton;
    private JLabel LastPlayedCard;
    private JMenuBar menuBar;

    private JLabel[] positions;

    private JLabel[] redHomeSquares;
    private JLabel[] yellowHomeSquare;
    private JLabel redHome;
    private JLabel redStart;
    private JLabel yellowHome;
    private JLabel yellowStart;

    private JLayeredPaneExtension basic_panel;

    private JTextArea infobox;

    private ClassLoader cldr;

    private JLabel []pawn;

    int pos[]=new int[4];

    int posRed[]=new int[2];
    int posYellow[]=new int[2];

    boolean IsInHomeRedSquares1=false;
    boolean IsInHomeRedSquares2=false;
    boolean IsInHomeYellowSquares1=false;

    boolean IsInHomeYellowSquares2=false;

    boolean DrawCard8=false;
    boolean DrawCard12=false;

    boolean p0fin=false;
    boolean p1fin=false;

    boolean p2fin=false;
    boolean p3fin=false;

    /**
     * <b>constructor</b>:Creates a new window
     */
    public View() {
        cldr = this.getClass().getClassLoader();
        this.setResizable(true);
        this.setTitle("Sorry Game!");
        this.setPreferredSize(new Dimension(1126, 844));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * <b>constructor</b>:initializes pawns and background images of the table
     */
    public void initComponents() {

        posRed[0]=0;
        posRed[1]=0;
        posYellow[0]=0;
        posYellow[1]=0;

        positions = new JLabel[64];
        //add the background
        ClassLoader cldr = this.getClass().getClassLoader();
        URL imageURL = cldr.getResource("mvc/images/background.png");
        if (imageURL == null) {
            System.out.println("Image not found");
        }
        Image image = new ImageIcon(imageURL).getImage();
        basic_panel = new JLayeredPaneExtension(image);

        //add the info box
        infobox = new JTextArea();
        infobox.setBackground(Color.WHITE);
        infobox.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        infobox.setBounds(830, 500, 250, 150);
        infobox.setOpaque(true);
        //set a larger font size
        Font largerFont = new Font(infobox.getFont().getName(), Font.BOLD, 22);
        infobox.setFont(largerFont);
        infobox.setText("Info Box");
        basic_panel.add(infobox);
        basic_panel.repaint();

        init_buttons();
        init_squares();

        //add Sorry logo
        URL imageSorry = cldr.getResource("mvc/images/sorryImage.png");
        if (imageSorry != null) {
            ImageIcon imageInMiddle = new ImageIcon(imageSorry);
            JLabel imageLabel = new JLabel(imageInMiddle);

            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            imageLabel.setBounds(230, 330, 340, 100);

            // Add the image label directly to the custom desktop pane
            basic_panel.add(imageLabel);
        } else {
            System.err.println("Image sorry was not found!");
        }


        //add home and start squares for red
        URL imageHomeRED =cldr.getResource("mvc/images/slides/redspiti.png");
        if(imageHomeRED!=null){
            Image imageIcon1=new ImageIcon(imageHomeRED).getImage();
            redHome=new JLabel();
            redHome.setIcon(new ImageIcon(imageIcon1));
            redHome.setBounds(70,300,125,123);

            // Add the image label directly to the custom desktop pane
            basic_panel.add(redHome,0);
        }else{
            System.err.println("Image sorry was not found!");
        }

        URL imageStartRED =cldr.getResource("mvc/images/slides/redspitiStart.png");
        if(imageStartRED!=null){
            Image imageIcon1=new ImageIcon(imageStartRED).getImage();
            redStart=new JLabel();
            redStart.setIcon(new ImageIcon(imageIcon1));
            redStart.setBounds(170,50,125,123);

            // Add the image label directly to the custom desktop pane
            basic_panel.add(redStart,0);
        }else{
            System.err.println("Image sorry was not found!");
        }


        //add home and start squares for yellow
        URL imageHomeYellow =cldr.getResource("mvc/images/slides/yellowspiti.png");
        if(imageHomeYellow!=null){
            Image imageIcon1=new ImageIcon(imageHomeYellow).getImage();
            yellowHome=new JLabel();
            yellowHome.setIcon(new ImageIcon(imageIcon1));
            yellowHome.setBounds(610,378,125,123);

            // Add the image label directly to the custom desktop pane
            basic_panel.add(yellowHome,0);
        }else{
            System.err.println("Image sorry was not found!");
        }

        URL imageStartYellow =cldr.getResource("mvc/images/slides/yellowspitiStart.png");
        if(imageStartYellow!=null){
            Image imageIcon1=new ImageIcon(imageStartYellow).getImage();
            yellowStart=new JLabel();
            yellowStart.setIcon(new ImageIcon(imageIcon1));
            yellowStart.setBounds(500,626,125,123);

            // Add the image label directly to the custom desktop pane
            basic_panel.add(yellowStart,0);
        }else{
            System.err.println("Image sorry was not found!");
        }


        init_pawns();


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(basic_panel, GroupLayout.PREFERRED_SIZE, 1140, GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(basic_panel, GroupLayout.PREFERRED_SIZE, 900, GroupLayout.PREFERRED_SIZE));
        pack();
        basic_panel.repaint();
    }


    /**
     * <b>constructor</b>:creates the windows for the game to be played
     */
    public void init_squares() {

        yellowHomeSquare =new JLabel[5];
        redHomeSquares =new JLabel[5];

        //red side
        for (int i = 0; i < 15; i++) {
            URL imageSquare = cldr.getResource("mvc/images/slides/whiteSlide.png");
            if (imageSquare == null) {
                System.out.println("photo not found");
            }
            ImageIcon imageIcon = new ImageIcon(imageSquare);
            positions[i] = new JLabel(imageIcon);
            setLayout(new FlowLayout());

            add(positions[i]); // Add the JLabel to the container

            // Adjust the position based on your requirements
            int x = (i * 50);
            int y = 0;

            if (i == 1 || i == 9) {
                URL image = cldr.getResource("mvc/images/slides/redSlideStart.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            } else if (i == 2 || i == 3 || i == 10 || i == 11 || i == 12) {
                URL image = cldr.getResource("mvc/images/slides/redSlideMedium.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);

                if (i == 2) {
                    //add the red home squares
                    URL imageR = cldr.getResource("mvc/images/slides/redHomeSquare.png");
                    for (int j = 0; j < 5; j++) {
                        ImageIcon imageIcon11 = new ImageIcon(imageR);
                        redHomeSquares[j] = new JLabel(imageIcon11);
                        setLayout(new FlowLayout());
                        add(redHomeSquares[j]);
                        redHomeSquares[j].setBounds(x, y + ((j+1) * 50), 50, 50);
                        redHomeSquares[j].setOpaque(true);
                        basic_panel.add(redHomeSquares[j], 0);
                    }
                }
            } else if (i == 4 || i == 13) {
                URL image = cldr.getResource("mvc/images/slides/redSlideEnd.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);

            } else {
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            }
        }

        //add blue side
        for (int i = 15; i < 30; i++) {
            URL imageSquare = cldr.getResource("mvc/images/slides/whiteSlide.png");
            if (imageSquare == null) {
                System.out.println("photo not found");
            }
            ImageIcon imageIcon = new ImageIcon(imageSquare);
            positions[i] = new JLabel(imageIcon);
            setLayout(new FlowLayout());

            add(positions[i]); // Add the JLabel to the container

            // Adjust the position based on your requirements
            int x = 750;
            int y = ((i - 15) * 50);

            if (i == 16 || i == 24) {
                URL image = cldr.getResource("mvc/images/slides/blueSlideStart.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            } else if (i == 17 || i == 18 || i ==25 || i == 26 || i == 27) {
                URL image = cldr.getResource("mvc/images/slides/blueSlideMedium.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            } else if (i == 19 || i == 28) {
                URL image = cldr.getResource("mvc/images/slides/blueSlideEnd.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);

            } else {
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            }
        }

        //add yellow side
        for (int i = 30; i < 46; i++) {

            URL imageSquare = cldr.getResource("mvc/images/slides/whiteSlide.png");
            if (imageSquare == null) {
                System.out.println("photo not found");
            }
            ImageIcon imageIcon = new ImageIcon(imageSquare);
            positions[i] = new JLabel(imageIcon);
            setLayout(new FlowLayout());

            add(positions[i]); // Add the JLabel to the container

            int x = 750 - ((i-30) * 50);
            int y = 750;

            if (i == 31 || i == 39) {
                URL image = cldr.getResource("mvc/images/slides/yellowSlideStart.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            } else if (i == 32 || i == 33 || i == 40 || i == 41 || i == 42) {
                URL image = cldr.getResource("mvc/images/slides/yellowSlideMedium.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
                if (i == 32) {
                    //add the red home squares
                    URL imageR = cldr.getResource("mvc/images/slides/yellowHomeSquare.png");
                    for (int j = 0; j < 5; j++) {
                        ImageIcon imageIcon11 = new ImageIcon(imageR);
                        yellowHomeSquare[j] = new JLabel(imageIcon11);
                        setLayout(new FlowLayout());
                        add(yellowHomeSquare[j]);
                        yellowHomeSquare[j].setBounds(x, y - ((j+1) * 50), 50, 50);
                        yellowHomeSquare[j].setOpaque(true);
                        basic_panel.add(yellowHomeSquare[j], 0);
                    }
                }
            } else if (i == 34 || i == 43) {
                URL image = cldr.getResource("mvc/images/slides/yellowSlideEnd.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);

            } else {
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            }

        }

        //add green side
        for (int i = 45; i < 60; i++) {

            URL imageSquare = cldr.getResource("mvc/images/slides/whiteSlide.png");
            if (imageSquare == null) {
                System.out.println("photo not found");
            }
            ImageIcon imageIcon = new ImageIcon(imageSquare);
            positions[i] = new JLabel(imageIcon);
            setLayout(new FlowLayout());

            add(positions[i]); // Add the JLabel to the container

            int x = 0;
            int y = 750 - ((i-45) * 50);

            if (i == 46 || i == 54) {
                URL image = cldr.getResource("mvc/images/slides/greenSlideStart.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            } else if (i == 47 || i == 48 || i == 55 || i == 56 || i == 57) {
                URL image = cldr.getResource("mvc/images/slides/greenSlideMedium.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            } else if (i == 49 || i == 58) {
                URL image = cldr.getResource("mvc/images/slides/greenSlideEnd.png");
                ImageIcon imageIcon1 = new ImageIcon(image);
                positions[i] = new JLabel(imageIcon1);
                setLayout(new FlowLayout());
                add(positions[i]);
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);

            } else {
                positions[i].setBounds(x, y, 50, 50); // Set position and size
                positions[i].setOpaque(true);
                basic_panel.add(positions[i], 0);
            }

        }

    }

    public JButton getFoldButton(){
        return FoldButton;
    }

    public JButton getDrawButton(){
        return DrawButton;
    }


    /**
     * <b>constructor</b>:initializes the buttons of the game
     */
    public void init_buttons(){
        URL imageURL=cldr.getResource("mvc/images/cards/backCard.png");
        Image image=new ImageIcon(imageURL).getImage();
        DrawButton =new JButton();
        DrawButton.setName("Draw Button");
        DrawButton.setBounds(830,150,120,182);
        DrawButton.setIcon(new ImageIcon(image));
        basic_panel.add(DrawButton,0);

        //add current card label
        LastPlayedCard=new JLabel();
        LastPlayedCard.setBounds(980,150,120,182);
        LastPlayedCard.setBackground(Color.WHITE);
        basic_panel.add(LastPlayedCard,0);

        //add fold button
        FoldButton =new JButton("Fold Button");
        FoldButton.setBackground(Color.RED);
        FoldButton.setBounds(830,400,250,75);
        Font buttonFont=new Font(FoldButton.getFont().getName(),Font.BOLD,22);
        FoldButton.setFont(buttonFont);
        basic_panel.add(FoldButton,0);

        //add the text receive for cards
        JLabel labelReceive=new JLabel("Receive Card");
        labelReceive.setBounds(830,300,150,100);
        Font labelFont = new Font(labelReceive.getFont().getName(), Font.BOLD, 16);
        labelReceive.setFont(labelFont);

        basic_panel.add(labelReceive);

        //add the text current for cards
        JLabel labelCurrent=new JLabel("Current Card");
        labelCurrent.setBounds(980,300,150,100);
        Font labelFont2 = new Font(labelCurrent.getFont().getName(), Font.BOLD, 16);
        labelCurrent.setFont(labelFont2);

        basic_panel.add(labelCurrent);
        basic_panel.repaint();

    }

    /**
     * method to update current card correctly
     * @param imagePath
     */
    public void updateCurrentCard(String imagePath){

        URL imageURL=cldr.getResource(imagePath);
        ImageIcon icon=new ImageIcon(imageURL);

        LastPlayedCard.setIcon(icon);
        LastPlayedCard.setBounds(980,150,120,182);
        basic_panel.add(LastPlayedCard,0);
        basic_panel.repaint();

    };


    /**
     * Method that implements the logic of the game
     * (Mistakes might be found in the use of cards 3,7)
     * @param player
     * @param value
     */
    public void updatePawn(Player player, int value){


        //for each player
        if(player.getID()==1){

            //for each card
            if(value==1) {

                if(posRed[0]==-1 && posRed[1]==-1){
                    System.out.println("GAME FINISHED");
                    showWinningMessage("PLAYER 1 WON!");
                }

                if(pos[0]==-1 && pos[1]==-1){
                    System.out.println("Player 1 choose pawn to move from the START:1 or 2");
                    Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
                    int pawnNumber= sc.nextInt();
                    while (pawnNumber!=1 && pawnNumber!=2){
                        System.out.println("Please type in the correct value 1 or 2!");
                        pawnNumber= sc.nextInt();
                    }

                    if (pawnNumber==1){
                        pawn[0].setBounds(positions[4].getX(),positions[4].getY(),50,50);
                        pos[0]=4;
                    }else {
                        pawn[1].setBounds(positions[4].getX(),positions[4].getY(),50,50);
                        pos[1]=4;
                    }
                }else if(pos[1]!=-1 && pos[0]==-1){
                    System.out.println("Type 1:Player 1 move pawn1 from start\nType 2:Player 1 move pawn2 one step");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){
                        pawn[0].setBounds(positions[4].getX(),positions[4].getY(),50,50);
                        pos[0]=4;
                    }else{
                        if(posRed[1]!=0 && posRed[1]!=-1 && IsInHomeRedSquares2){
                            posRed[1]=posRed[1]+1;
                            if(posRed[1]==6){
                                pawn[1].setBounds(135,325,50,50);
                                posRed[1]=-1;
                                p1fin=true;
                            }else {
                                pawn[1].setBounds(redHomeSquares[posRed[1]-1].getX(),redHomeSquares[posRed[1]-1].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }
                        }else if(!IsInHomeRedSquares2){
                            pos[1]=pos[1]+1;
                            if (pos[1]==3){
                                posRed[1]=1;
                                pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else{
                                if(pos[1]>59){
                                    pos[1]=pos[1]-60;
                                }
                                pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }
                    }

                }else if(pos[1] == -1){
                    System.out.println("Type 1:Player 1 move pawn1 one step\nType 2:Player 1 move pawn2 from the start");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){

                        if(posRed[0]!=0 && posRed[0]!=1 && IsInHomeRedSquares1){
                            posRed[0]=posRed[0]+1;
                            if(posRed[0]==6){
                                pawn[0].setBounds(80,325,50,50);
                                posRed[0]=-1;
                                p0fin=true;
                            }else{
                                pawn[0].setBounds(redHomeSquares[posRed[0]-1].getX(),redHomeSquares[posRed[0]-1].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }
                        }else if(!IsInHomeRedSquares1){
                            pos[0]=pos[0]+1;
                            if(pos[0]==3){
                                posRed[0]=1;
                                pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else{
                                if(pos[0]>59){
                                    pos[0]=pos[0]-60;
                                }
                                pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }
                    }else if(!IsInHomeRedSquares2 && posRed[1]!=-1){
                        pawn[1].setBounds(positions[4].getX(), positions[4].getY(), 50, 50);
                        pos[1] = 4;
                    }
                }else {
                    System.out.println("Player 1 choose pawn to move 1 step!");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1 && posRed[0]!=1){

                        if(posRed[0] != -1 && IsInHomeRedSquares1){
                            posRed[0]=posRed[0]+1;
                            if(posRed[0]==6){
                                pawn[0].setBounds(80,325,50,50);
                                posRed[0]=-1;
                                p0fin=true;
                            }else {
                                pawn[0].setBounds(redHomeSquares[posRed[0]-1].getX(),redHomeSquares[posRed[0]-1].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }
                        }else if(!IsInHomeRedSquares1){
                            pos[0]=pos[0]+1;
                            if(pos[0]==3){
                                posRed[0]=1;
                                pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else{
                                if(pos[0]>59){
                                    pos[0]=pos[0]-60;
                                }
                                pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }

                    }else if(posRed[1]!=-1){
                        if(posRed[1] != 0){
                            posRed[1]=posRed[1]+1;
                            if(posRed[1]==6 && IsInHomeRedSquares2){
                                pawn[1].setBounds(135,325,50,50);
                                posRed[1]=-1;
                                p1fin=true;
                            }else {
                                pawn[1].setBounds(redHomeSquares[posRed[1]-1].getX(),redHomeSquares[posRed[1]-1].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }
                        }else if(!IsInHomeRedSquares2){
                            pos[1]=pos[1]+1;
                            if (pos[1]==3){
                                posRed[1]=1;
                                pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else{
                                if(pos[1]>59){
                                    pos[1]=pos[1]-60;
                                }
                                pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }
                    }else{
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 1");
                    }

                }
            }
            else if(value==2){
                if(pos[0]==-1 && pos[1]==-1){
                    System.out.println("Player 1 choose pawn to move from the START:1 or 2");
                    Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
                    int pawnNumber= sc.nextInt();
                    while (pawnNumber!=1 && pawnNumber!=2){
                        System.out.println("Please type in the correct value 1 or 2!");
                        pawnNumber= sc.nextInt();
                    }

                    if (pawnNumber==1){
                        pawn[0].setBounds(positions[4].getX(),positions[4].getY(),50,50);
                        pos[0]=4;
                    }else {
                        pawn[1].setBounds(positions[4].getX(),positions[4].getY(),50,50);
                        pos[1]=4;
                    }
                }else if(pos[0]==-1 && posRed[1]!=-1){
                    System.out.println("Choose Action\nType 1:Player 1 move pawn1 from start\nType 2:Player 1 move pawn2 two steps");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){
                        pawn[0].setBounds(positions[4].getX(),positions[4].getY(),50,50);
                        pos[0]=4;
                    }else{

                        if(posRed[1]!=0 && posRed[1]!=-1 && IsInHomeRedSquares2){
                            posRed[1]=posRed[1]+2;
                            if (posRed[1]==6){
                                pawn[1].setBounds(135,325,50,50);
                                posRed[1]=-1;
                                p1fin=true;
                            }else if(posRed[1]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posRed[1]=posRed[1]-2;
                            }else{
                                pawn[1].setBounds(redHomeSquares[posRed[1]-1].getX(),redHomeSquares[posRed[1]-1].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }
                        }else if(!IsInHomeRedSquares2){
                            pos[1]=pos[1]+2;
                            if(pos[1]==3 ){
                                posRed[1]=1;
                                pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            } else if (pos[1]==4) {
                                posRed[1]=2;
                                pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else{
                                if(pos[1]>59){
                                    pos[1]=pos[1]-60;
                                }
                                pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }

                    }

                }else if(pos[1]==-1 && posRed[0]!=-1){
                    System.out.println("Choose Action\nType 1:Player 1 move pawn1 two steps\nType 2:Player 1 move pawn2 from the start");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){

                        if(posRed[0]!=0 && posRed[0]!=-1 && IsInHomeRedSquares1){
                            posRed[0]=posRed[0]+2;
                            if (posRed[0]==6){
                                pawn[0].setBounds(80,325,50,50);
                                posRed[0]=-1;
                                p0fin=true;
                            }else if(posRed[0]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posRed[0]=posRed[0]-2;
                            }else{
                                pawn[0].setBounds(redHomeSquares[posRed[0]-1].getX(),redHomeSquares[posRed[0]-1].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }
                        }else if(!IsInHomeRedSquares1){
                            pos[0]=pos[0]+2;
                            if(pos[0]==3){
                                posRed[0]=1;
                                pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else if(pos[0]==4){
                                posRed[0]=2;
                                pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else{
                                if(pos[0]>59){
                                    pos[0]=pos[0]-60;
                                }
                                pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }
                    }else {
                        pawn[1].setBounds(positions[4].getX(), positions[4].getY(), 50, 50);
                        pos[1] = 4;
                    }
                }else{
                    System.out.println("Player 1 choose pawn to move 2 steps!");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){

                        if(posRed[0]!=0 && posRed[0]!=-1 && IsInHomeRedSquares1){
                            posRed[0]=posRed[0]+2;
                            if (posRed[0]==6){
                                pawn[0].setBounds(80,325,50,50);
                                posRed[0]=-1;
                                p0fin=true;
                            }else if(posRed[0]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posRed[0]=posRed[0]-2;
                            }else{
                                pawn[0].setBounds(redHomeSquares[posRed[0]-1].getX(),redHomeSquares[posRed[0]-1].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }
                        }else if(!IsInHomeRedSquares1){
                            pos[0]=pos[0]+2;
                            if(pos[0]==3){
                                posRed[0]=1;
                                pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else if(pos[0]==4){
                                posRed[0]=2;
                                pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else {
                                if(pos[0]>59){
                                    pos[0]=pos[0]-60;
                                }
                                pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }
                    }else {

                        if(posRed[1]!=0 && posRed[1]!=-1 && IsInHomeRedSquares2){
                            posRed[1]=posRed[1]+2;
                            if (posRed[1]==6){
                                pawn[1].setBounds(135,325,50,50);
                                posRed[1]=-1;
                                p1fin=true;
                            }else if(posRed[1]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posRed[1]=posRed[1]-2;
                            }else{
                                pawn[1].setBounds(redHomeSquares[posRed[1]-1].getX(),redHomeSquares[posRed[1]-1].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }
                        }else if(!IsInHomeRedSquares2){
                            pos[1] = pos[1] + 2;
                            if(pos[1]==3){
                                posRed[1]=1;
                                pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else if(pos[1]==4){
                                posRed[1]=2;
                                pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else{
                                if(pos[1]>59){
                                    pos[1]=pos[1]-60;
                                }
                                pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                SlidesCheck(1);
                            }
                        }
                    }
                }
            }
            else if(value==3){

                    if(pos[0]!=-1 && pos[1]!=-1 && !IsInHomeRedSquares2 && !IsInHomeRedSquares1 && !p0fin && !p1fin){
                        //check if moves are possible
                        pos[0]=pos[0]+3;
                        pos[1]=pos[1]+3;


                        if(pos[0]>59){
                            pos[0]=pos[0]-60;
                        }

                        if(pos[0]==3){
                            posRed[0]=1;
                            pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares1=true;
                        }else if(pos[0]==4){
                            posRed[0]=2;
                            pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares1=true;
                        }else if(pos[0]==5){
                            posRed[0]=3;
                            pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares1=true;
                        }else{
                            pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                            SlidesCheck(1);
                        }

                        if(pos[1]>59){
                            pos[1]=pos[1]-60;
                        }

                        if(pos[1]==3){
                            posRed[1]=1;
                            pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares2=true;
                        }else if(pos[1]==4){
                            posRed[1]=2;
                            pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares2=true;
                        }else if(pos[1]==5){
                            posRed[1]=3;
                            pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares2=true;
                        }else {
                            pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                            SlidesCheck(1);
                        }
                    }
                    else if((pos[0]!=-1 && !IsInHomeRedSquares1)&& (IsInHomeYellowSquares2 || p1fin)){
                        pos[0]=pos[0]+3;
                        if(pos[0]>59){
                            pos[0]=pos[0]-60;
                        }

                        if(pos[0]==3){
                            posRed[0]=1;
                            pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares1=true;
                        }else if(pos[0]==4){
                            posRed[0]=2;
                            pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares1=true;
                        }else if(pos[0]==5){
                            posRed[0]=3;
                            pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares1=true;
                        }else{
                            pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                            SlidesCheck(1);
                        }

                    }
                    else if((pos[1]!=-1 && !IsInHomeRedSquares2) && (IsInHomeYellowSquares1 || p0fin)){
                        pos[1]=pos[1]+3;
                        if(pos[1]>59){
                            pos[1]=pos[1]-60;
                        }

                        if(pos[1]==3){
                            posRed[1]=1;
                            pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares2=true;
                        }else if(pos[1]==4){
                            posRed[1]=2;
                            pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares2=true;
                        }else if(pos[1]==5){
                            posRed[1]=3;
                            pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares2=true;
                        }else {
                            pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                            SlidesCheck(1);
                        }
                    }
                    else if(IsInHomeRedSquares1 && !p0fin){
                        posRed[0]=posRed[0]+3;
                        if(posRed[0]==6){
                            posRed[0]=-1;
                            pawn[0].setBounds(80,325,50,50);
                            p0fin=true;
                        }else if(posRed[0]<6){
                            pawn[0].setBounds(redHomeSquares[posRed[0]-1].getX(),redHomeSquares[posRed[0]-1].getY(),50,50);
                        }else {
                            posRed[0]=posRed[0]-3;
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                        }

                        if (IsInHomeRedSquares2 && posRed[1]!=1){
                            posRed[1]=posRed[1]+3;
                            if(posRed[1]==6){
                                posRed[1]=-1;
                                pawn[1].setBounds(135,325,50,50);
                                p1fin=true;
                            }else if(posRed[1]<6){
                                pawn[1].setBounds(redHomeSquares[posRed[1]-1].getX(),redHomeSquares[posRed[1]-1].getY(),50,50);
                            }else {
                                posRed[1]=posRed[1]-3;
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                            }
                        }else if(posRed[1]==-1){
                            showWinningMessage("PLAYER 1 red WON!!!");
                        }else if(pos[1]!=-1 && !IsInHomeRedSquares2){
                            pos[1]=pos[1]+3;
                            if (pos[1]>59){
                                pos[1]=pos[1]-60;
                            }
                            pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                            SlidesCheck(1);
                        }
                    }
                    else if(IsInHomeRedSquares2 && !p1fin){
                        posRed[1]=posRed[1]+3;
                        if(posRed[1]==6){
                            posRed[1]=-1;
                            pawn[1].setBounds(135,325,50,50);
                            p1fin=true;
                        }else if(posRed[1]<6){
                            pawn[1].setBounds(redHomeSquares[posRed[1]-1].getX(),redHomeSquares[posRed[1]-1].getY(),50,50);
                        }else {
                            posRed[1]=posRed[1]-3;
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                        }

                        if (IsInHomeRedSquares2 && posRed[0]!=1){
                            posRed[0]=posRed[0]+3;
                            if(posRed[0]==6){
                                posRed[0]=-1;
                                pawn[0].setBounds(80,325,50,50);
                                p0fin=true;
                            }else if(posRed[0]<6){
                                pawn[0].setBounds(redHomeSquares[posRed[0]-1].getX(),redHomeSquares[posRed[0]-1].getY(),50,50);
                            }else {
                                posRed[0]=posRed[0]-3;
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                            }
                        }else if(posRed[0]==-1){
                            showWinningMessage("PLAYER 1 red WON!!!");
                        }else if(pos[0]!=-1 && !IsInHomeRedSquares2){
                            pos[0]=pos[0]+3;
                            if (pos[0]>59){
                                pos[0]=pos[0]-60;
                            }
                            pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                            SlidesCheck(1);
                        }
                    }
            }
            else if(value==-4){
                if(pos[0]!=-1 && pos[1]!=-1){
                    System.out.println("Player 1 choose pawn 1 or 2 to move backwards 4 steps!");
                    Scanner scanner3=new Scanner(System.in);
                    int pawnN=scanner3.nextInt();
                    if(pawnN==1 && !IsInHomeRedSquares1){
                        pos[0]=pos[0]-4;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                        pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                        SlidesCheck(1);
                    }else if (pawnN==2 && !IsInHomeRedSquares2){
                        pos[1]=pos[1]-4;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                        pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                        SlidesCheck(1);
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM CARD4");
                    }
                }else if(pos[0]!=-1 && !IsInHomeRedSquares1){
                    pos[0]=pos[0]-4;
                    if(pos[0]<0){
                        pos[0]=60+pos[0];
                    }
                    pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                    SlidesCheck(1);
                }else if(pos[1]!=-1 && !IsInHomeRedSquares2){
                    pos[1]=pos[1]-4;
                    if(pos[1]<0){
                        pos[1]=60+pos[1];
                    }
                    pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                    SlidesCheck(1);
                }else {
                    System.out.println("MOVE CANNOT BE DONE FROM CARD4");
                }
            }
            else if(value==5){
                if(pos[0]!=-1 && pos[1]!=-1 && !IsInHomeRedSquares2 && !IsInHomeRedSquares1){
                    //check if moves are possible
                    pos[0]=pos[0]+5;
                    pos[1]=pos[1]+5;

                    if(pos[0]>59){
                        pos[0]=pos[0]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[0]==3){
                        posRed[0]=1;
                        pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==4){
                        posRed[0]=2;
                        pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==5){
                        posRed[0]=3;
                        pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==6){
                        posRed[0]=4;
                        pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==7){
                        posRed[0]=5;
                        pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(posRed[0]==0){
                        pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                        SlidesCheck(1);
                    }


                    if(pos[1]>59){
                        pos[1]=pos[1]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[1]==3){
                        posRed[1]=1;
                        pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==4){
                        posRed[1]=2;
                        pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==5){
                        posRed[1]=3;
                        pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==6){
                        posRed[1]=4;
                        pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==7){
                        posRed[1]=5;
                        pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(posRed[1]==0){
                        pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                        SlidesCheck(1);
                    }

                }else if((pos[0]!=-1 && !IsInHomeRedSquares1)&& (IsInHomeRedSquares2 || p1fin)){
                    pos[0]=pos[0]+5;
                    if(pos[0]>59){
                        pos[0]=pos[0]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[0]==3){
                        posRed[0]=1;
                        pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==4){
                        posRed[0]=2;
                        pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==5){
                        posRed[0]=3;
                        pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==6){
                        posRed[0]=4;
                        pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==7){
                        posRed[0]=5;
                        pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(posRed[0]==0){
                        pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                        SlidesCheck(1);
                    }
                }else if((pos[1]!=-1 && !IsInHomeRedSquares2)&& (IsInHomeRedSquares1 || p0fin)){
                    pos[1]=pos[1]+5;
                    if(pos[1]>59){
                        pos[1]=pos[1]-60;
                    }

                    if(pos[1]==3){
                        posRed[1]=1;
                        pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==4){
                        posRed[1]=2;
                        pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==5){
                        posRed[1]=3;
                        pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==6){
                        posRed[1]=4;
                        pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==7){
                        posRed[1]=5;
                        pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(posRed[1]==0){
                        pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                        SlidesCheck(1);
                    }
                }else if(posRed[1]==1){
                    posRed[1]=-1;
                    pawn[1].setBounds(135,325,50,50);
                    p1fin=true;
                    IsInHomeRedSquares2=true;
                }else if(posRed[0]==1){
                    posRed[0]=-1;
                    pawn[0].setBounds(80,325,50,50);
                    p0fin=true;
                    IsInHomeRedSquares1=true;
                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD5");
                }
            }
            else if(value==6){
                SorryCardDraw(1);
            }
            else if(value==7){
                if(pos[0]==-1 && pos[1]==-1){
                    System.out.println("MOVE CANNOT BE DONE FROM CARD7");
                }else if(pos[0]!=-1 && pos[1]!=-1 && !IsInHomeRedSquares1 && !IsInHomeRedSquares2){
                    System.out.println("Player 1 choose the move of your pawns");
                    System.out.println("Type 1 for:\npawn1 7 steps and pawn2 0 steps");
                    System.out.println("Type 2 for:\npawn1 6 steps and pawn2 1 steps");
                    System.out.println("Type 3 for:\npawn1 5 steps and pawn2 2 steps");
                    System.out.println("Type 4 for:\npawn1 4 steps and pawn2 3 steps");
                    System.out.println("Type 5 for:\npawn1 3 steps and pawn2 4 steps");
                    System.out.println("Type 6 for:\npawn1 2 steps and pawn2 5 steps");
                    System.out.println("Type 7 for:\npawn1 1 steps and pawn2 6 steps");
                    System.out.println("Type 8 for:\npawn1 0 steps and pawn2 7 steps");
                    Scanner scanner=new Scanner(System.in);
                    int type=scanner.nextInt();
                    switch (type){
                        case 1:
                            pos[0]=pos[0]+7;
                            break;
                        case 2:
                            pos[0]=pos[0]+6;
                            pos[1]=pos[1]+1;
                            break;
                        case 3:
                            pos[0]=pos[0]+5;
                            pos[1]=pos[1]+2;
                            break;
                        case 4:
                            pos[0]=pos[0]+4;
                            pos[1]=pos[1]+3;
                            break;
                        case 5:
                            pos[0]=pos[0]+3;
                            pos[1]=pos[1]+4;
                            break;
                        case 6:
                            pos[0]=pos[0]+2;
                            pos[1]=pos[1]+5;
                            break;
                        case 7:
                            pos[0]=pos[0]+1;
                            pos[1]=pos[1]+6;
                            break;
                        case 8:
                            pos[1]=pos[1]+7;
                            break;
                    }
                    if(pos[0]>59){
                        pos[0]=pos[0]-60;
                    }

                    //check for if pawn is inserted at the home squares
                    if(pos[0]==3){
                        posRed[0]=1;
                        pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==4){
                        posRed[0]=2;
                        pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==5){
                        posRed[0]=3;
                        pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==6){
                        posRed[0]=4;
                        pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==7){
                        posRed[0]=5;
                        pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==8){
                        posRed[0]=-1;
                        pawn[0].setBounds(80,325,50,50);
                        IsInHomeRedSquares1=true;
                        p0fin=true;
                    }else if(pos[0]==9){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 7");
                        //get pawn back to its correct position
                        pos[0]=pos[0]-7;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else{
                        pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                        SlidesCheck(1);
                    }


                    if(pos[1]>59){
                        pos[1]=pos[1]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[1]==3){
                        posRed[1]=1;
                        pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==4){
                        posRed[1]=2;
                        pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==5){
                        posRed[1]=3;
                        pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==6){
                        posRed[1]=4;
                        pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==7){
                        posRed[1]=5;
                        pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==8){
                        posRed[1]=-1;
                        pawn[1].setBounds(135,325,50,50);
                        IsInHomeRedSquares2=true;
                        p1fin=true;
                    }else if(pos[1]==9){
                        System.out.println("MOVE CANNOT BE DONE FROM CARD 7");
                        pos[1]=pos[1]-7;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else{
                        pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                        SlidesCheck(1);
                    }

                }else if((pos[1]!=-1 && !IsInHomeRedSquares2)&& (IsInHomeRedSquares1 || p0fin)){
                    pos[1]=pos[1]+7;
                    if(pos[1]>59){
                        pos[1]=pos[1]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[1]==3){
                        posRed[1]=1;
                        pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==4){
                        posRed[1]=2;
                        pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==5){
                        posRed[1]=3;
                        pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==6){
                        posRed[1]=4;
                        pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==7){
                        posRed[1]=5;
                        pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==8){
                        posRed[1]=-1;
                        pawn[1].setBounds(135,325,50,50);
                        IsInHomeRedSquares2=true;
                        p1fin=true;
                    }else if(pos[1]==9){
                        System.out.println("MOVE CANNOT BE DONE FROM CARD 7");
                        pos[1]=pos[1]-7;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else{
                        pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                        SlidesCheck(1);
                    }
                }else if((pos[0]!=-1 && !IsInHomeRedSquares1)&& (IsInHomeRedSquares2 || p1fin)){
                    pos[0]=pos[0]+7;
                    if(pos[0]>59){
                        pos[0]=pos[0]-60;
                    }

                    //check for if pawn is inserted at the home squares
                    if(pos[0]==3){
                        posRed[0]=1;
                        pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==4){
                        posRed[0]=2;
                        pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==5){
                        posRed[0]=3;
                        pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==6){
                        posRed[0]=4;
                        pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==7){
                        posRed[0]=5;
                        pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==8){
                        posRed[0]=-1;
                        pawn[0].setBounds(80,325,50,50);
                        IsInHomeRedSquares1=true;
                        p0fin=true;
                    }else if(pos[0]==9){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 7");
                        //get pawn back to its correct position
                        pos[0]=pos[0]-7;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else{
                        pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                        SlidesCheck(1);
                    }
                }
            }
            else if(value==8){
                if(pos[0]==-1 && pos[1]==-1){
                    //draw new cards
                    System.out.println("Player 1 draw a new card");
                    setDrawCard8(true);
                }else if((pos[0]!=-1 && !IsInHomeRedSquares1) && (IsInHomeRedSquares2 || p1fin)){
                    System.out.println("Player 1 choose:\nMove pawn1 8 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[0]=pos[0]+8;
                        if(pos[0]>59){
                            pos[0]=pos[0]-60;
                        }

                        if(pos[0]==3){
                            posRed[0]=1;
                            pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==4){
                            posRed[0]=2;
                            pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==5){
                            posRed[0]=3;
                            pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==6){
                            posRed[0]=4;
                            pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==7){
                            posRed[0]=5;
                            pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==8){
                            posRed[0]=-1;
                            pawn[0].setBounds(80,325,50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                            p0fin=true;
                        }else if(pos[0]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[0]=pos[0]-8;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard8(false);
                        }else if(pos[0]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[0]=pos[0]-8;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                            SlidesCheck(1);
                            setDrawCard8(false);
                        }

                    }else {
                        //draw card
                        System.out.println("Player 1 draw a new card");
                        setDrawCard8(true);
                    }
                }else if((pos[1]!=-1 && !IsInHomeRedSquares2) && (IsInHomeRedSquares1 || p0fin)){
                    System.out.println("Player 1 choose:\nMove pawn2 8 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[1]=pos[1]+8;

                        if(pos[1]>59){
                            pos[1]=pos[1]-60;
                        }

                        if(pos[1]==3){
                            posRed[1]=1;
                            pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==4){
                            posRed[1]=2;
                            pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==5){
                            posRed[1]=3;
                            pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==6){
                            posRed[1]=4;
                            pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==7){
                            posRed[1]=5;
                            pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==8){
                            posRed[1]=-1;
                            pawn[1].setBounds(135,325,50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                            p1fin=true;
                        }else if(pos[1]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[1]=pos[1]-8;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard8(false);
                        }else if(pos[1]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[1]=pos[1]-8;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                            SlidesCheck(1);
                            setDrawCard8(false);
                        }
                    }else{
                        //draw card
                        System.out.println("Player 1 draw a new card");
                        setDrawCard8(true);
                    }
                }else if(!IsInHomeRedSquares1 && !IsInHomeRedSquares2 && !p1fin && !p0fin){
                    System.out.println("Player 1 choose:\nMove pawn1 8 steps or move pawn2 8 steps or Draw a card.Type 1 or 2 or 3");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if(p==1 && !IsInHomeRedSquares1){
                        pos[0]=pos[0]+8;
                        if(pos[0]>59){
                            pos[0]=pos[0]-60;
                        }
                        if(pos[0]==3){
                            posRed[0]=1;
                            pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==4){
                            posRed[0]=2;
                            pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==5){
                            posRed[0]=3;
                            pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==6){
                            posRed[0]=4;
                            pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==7){
                            posRed[0]=5;
                            pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[0]==8){
                            posRed[0]=-1;
                            pawn[0].setBounds(80,325,50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard8(false);
                            p0fin=true;
                        }else if(pos[0]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[0]=pos[0]-8;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard8(false);
                        }else if(pos[0]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[0]=pos[0]-8;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                            SlidesCheck(1);
                            setDrawCard8(false);
                        }
                    }else if(p==2 && !IsInHomeRedSquares2){
                        pos[1]=pos[1]+8;
                        if(pos[1]>59){
                            pos[1]=pos[1]-60;
                        }

                        if(pos[1]==3){
                            posRed[1]=1;
                            pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==4){
                            posRed[1]=2;
                            pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==5){
                            posRed[1]=3;
                            pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==6){
                            posRed[1]=4;
                            pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==7){
                            posRed[1]=5;
                            pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[1]==8){
                            posRed[1]=-1;
                            pawn[1].setBounds(135,325,50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard8(false);
                            p1fin=true;
                        }else if(pos[1]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[1]=pos[1]-8;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard8(false);
                        }else if(pos[1]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[1]=pos[1]-8;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                            SlidesCheck(1);
                            setDrawCard8(false);
                        }
                    }else if(p==3){
                        System.out.println("Player 1 draw a new card");
                        setDrawCard8(true);
                    }
                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                }
            }
            else if(value==10){
                if(pos[0]==-1 && pos[1]==-1){
                    System.out.println("NO MOVE CAN BE DONE WITH CARD10");
                }else if((pos[0]!=-1 && !IsInHomeRedSquares1)&& (IsInHomeRedSquares2 || p1fin)){
                    System.out.println("Player 1 choose:");
                    System.out.println("Move pawn1 10 steps or move pawn1 1 step back.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int d=scanner.nextInt();
                    if(d==1){
                        pos[0]=pos[0]+10;
                    }else if (d==2){
                        pos[0]=pos[0]-1;
                    }
                    if(pos[0]>59){
                        pos[0]=pos[0]-60;
                    }

                    if(pos[0]<0){
                        pos[0]=60+pos[0];
                    }

                    //check if pawn needs to be inserted at the red home squares
                    if(pos[0]==3){
                        posRed[0]=1;
                        pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==4){
                        posRed[0]=2;
                        pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==5){
                        posRed[0]=3;
                        pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==6){
                        posRed[0]=4;
                        pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==7){
                        posRed[0]=5;
                        pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==8){
                        posRed[0]=-1;
                        pawn[0].setBounds(80,325,50,50);
                        IsInHomeRedSquares1=true;
                        p0fin=true;
                    }else if(pos[0]==9){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else if(pos[0]==10){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else if(pos[0]==11){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else if(pos[0]==12){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else {
                        pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                        SlidesCheck(1);
                    }

                }else if((pos[1]!=-1 && !IsInHomeRedSquares2)&& (IsInHomeRedSquares1 || p0fin)){
                    System.out.println("Player 1 choose:");
                    System.out.println("Move pawn2 10 steps or move pawn2 1 step back.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int d=scanner.nextInt();
                    if(d==1){
                        pos[1]=pos[1]+10;
                    }else if (d==2){
                        pos[1]=pos[1]-1;
                    }
                    if(pos[1]>59){
                        pos[1]=pos[1]-60;
                    }
                    if(pos[1]<0){
                        pos[1]=60+pos[1];
                    }

                    //check if pawn needs to be inserted at the red home squares
                    if(pos[1]==3){
                        posRed[1]=1;
                        pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==4){
                        posRed[1]=2;
                        pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==5){
                        posRed[1]=3;
                        pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==6){
                        posRed[1]=4;
                        pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==7){
                        posRed[1]=5;
                        pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==8){
                        posRed[1]=-1;
                        pawn[1].setBounds(135,325,50,50);
                        IsInHomeRedSquares2=true;
                        p1fin=true;
                    }else if(pos[1]==9){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else if(pos[1]==10){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else if(pos[1]==11){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else if(pos[1]==12){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else {
                        pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                        SlidesCheck(1);
                    }
                }else if(!IsInHomeRedSquares2 && !IsInHomeRedSquares1 && pos[0]!=-1 && pos[1]!=-1){
                    System.out.println("Player 1 choose:");
                    System.out.println("Type 1 ,move pawn1 10 steps");
                    System.out.println("Type 2 ,move pawn2 10 steps");
                    System.out.println("Type 3 ,move pawn1 1 step back");
                    System.out.println("Type 4 ,move pawn2 1 step back");
                    Scanner scanner=new Scanner(System.in);
                    int d=scanner.nextInt();
                    switch (d){
                        case 1:
                            pos[0]=pos[0]+10;
                            break;
                        case 2:
                            pos[1]=pos[1]+10;
                            break;
                        case 3:
                            pos[0]=pos[0]-1;
                            break;
                        case 4:
                            pos[1]=pos[1]-1;
                            break;
                    }
                    if(pos[0]>59){
                        pos[0]=pos[0]-60;
                    }
                    if(pos[1]>59){
                        pos[1]=pos[1]-60;
                    }
                    if(pos[0]<0){
                        pos[0]=60+pos[0];
                    }
                    if(pos[0]==3){
                        posRed[0]=1;
                        pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==4){
                        posRed[0]=2;
                        pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==5){
                        posRed[0]=3;
                        pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==6){
                        posRed[0]=4;
                        pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==7){
                        posRed[0]=5;
                        pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares1=true;
                    }else if(pos[0]==8){
                        posRed[0]=-1;
                        pawn[0].setBounds(80,325,50,50);
                        IsInHomeRedSquares1=true;
                        p0fin=true;
                    }else if(pos[0]==9){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else if(pos[0]==10){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else if(pos[0]==11){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else if(pos[0]==12){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[0]=pos[0]-10;
                        if(pos[0]<0){
                            pos[0]=60+pos[0];
                        }
                    }else {
                        pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                        SlidesCheck(1);
                    }

                    if(pos[1]<0){
                        pos[1]=60+pos[1];
                    }

                    if(pos[1]==3){
                        posRed[1]=1;
                        pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==4){
                        posRed[1]=2;
                        pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==5){
                        posRed[1]=3;
                        pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==6){
                        posRed[1]=4;
                        pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==7){
                        posRed[1]=5;
                        pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                        IsInHomeRedSquares2=true;
                    }else if(pos[1]==8){
                        posRed[1]=-1;
                        pawn[1].setBounds(135,325,50,50);
                        IsInHomeRedSquares2=true;
                        p1fin=true;
                    }else if(pos[1]==9){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else if(pos[1]==10){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else if(pos[1]==11){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else if(pos[1]==12){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[1]=pos[1]-10;
                        if(pos[1]<0){
                            pos[1]=60+pos[1];
                        }
                    }else {
                        pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                        SlidesCheck(1);
                    }

                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                }
            }
            else if(value==11) {
                if (pos[0] == -1 && pos[1] == -1) {
                    System.out.println("MOVE CANNOT BE DONE FROM CARD11");
                } else if(!IsInHomeRedSquares2 || !IsInHomeRedSquares1 ){

                    System.out.println("Player 1 choose");
                    System.out.println("Type 1:move any pawn 11 steps");
                    System.out.println("Type 2:switch any of yours pawns with any of your opponents pawns");
                    Scanner scanner1 = new Scanner(System.in);
                    int dec = scanner1.nextInt();

                    if (dec == 1) {
                        if (pos[0] == -1 && pos[1] == -1) {
                            System.out.println("NO MOVE CAN BE DONE WITH CARD11");
                        } else if ((pos[0]!= -1 && !IsInHomeRedSquares1) && (IsInHomeRedSquares2 || p1fin)) {
                            pos[0] = pos[0] + 11;
                            if (pos[0] > 59) {
                                pos[0] = pos[0] - 60;
                            }
                            //check if pawn needs to be inserted at the red home squares
                            if(pos[0]==3){
                                posRed[0]=1;
                                pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else if(pos[0]==4){
                                posRed[0]=2;
                                pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else if(pos[0]==5){
                                posRed[0]=3;
                                pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else if(pos[0]==6){
                                posRed[0]=4;
                                pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else if(pos[0]==7){
                                posRed[0]=5;
                                pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                                IsInHomeRedSquares1=true;
                            }else if(pos[0]==8){
                                posRed[0]=-1;
                                pawn[0].setBounds(80,325,50,50);
                                IsInHomeRedSquares1=true;
                                p0fin=true;
                            }else if(pos[0]==9){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[0]=pos[0]-11;
                                if(pos[0]<0){
                                    pos[0]=60+pos[0];
                                }
                            }else if(pos[0]==10){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[0]=pos[0]-11;
                                if(pos[0]<0){
                                    pos[0]=60+pos[0];
                                }
                            }else if(pos[0]==11){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[0]=pos[0]-11;
                                if(pos[0]<0){
                                    pos[0]=60+pos[0];
                                }
                            }else if(pos[0]==12){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[0]=pos[0]-11;
                                if(pos[0]<0){
                                    pos[0]=60+pos[0];
                                }
                            }else if(pos[0]==13){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[0]=pos[0]-11;
                                if(pos[0]<0){
                                    pos[0]=60+pos[0];
                                }
                            }else{
                                pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                SlidesCheck(1);
                            }
                        } else if ((pos[1] != -1 && !IsInHomeRedSquares2 )&& (IsInHomeRedSquares1 || p0fin)) {
                            pos[1] = pos[1] + 11;
                            if (pos[1] > 59) {
                                pos[1] = pos[1] - 60;
                            }

                            //check if pawn needs to be inserted at the red home squares
                            if(pos[1]==3){
                                posRed[1]=1;
                                pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else if(pos[1]==4){
                                posRed[1]=2;
                                pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else if(pos[1]==5){
                                posRed[1]=3;
                                pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else if(pos[1]==6){
                                posRed[1]=4;
                                pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else if(pos[1]==7){
                                posRed[1]=5;
                                pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                                IsInHomeRedSquares2=true;
                            }else if(pos[1]==8){
                                posRed[1]=-1;
                                pawn[1].setBounds(135,325,50,50);
                                IsInHomeRedSquares2=true;
                                p1fin=true;
                            }else if(pos[1]==9){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[1]=pos[1]-11;
                                if(pos[1]<0){
                                    pos[1]=60+pos[1];
                                }
                            }else if(pos[1]==10){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[1]=pos[1]-11;
                                if(pos[1]<0){
                                    pos[1]=60+pos[1];
                                }
                            }else if(pos[1]==11){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[1]=pos[1]-11;
                                if(pos[1]<0){
                                    pos[1]=60+pos[1];
                                }
                            }else if(pos[1]==12){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[1]=pos[1]-11;
                                if(pos[1]<0){
                                    pos[1]=60+pos[1];
                                }
                            }else if(pos[1]==13){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[1]=pos[1]-11;
                                if(pos[1]<0){
                                    pos[1]=60+pos[1];
                                }
                            }else{
                                pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                SlidesCheck(1);
                            }

                        } else if(!IsInHomeRedSquares1 && !IsInHomeRedSquares2 && pos[0]!=-1 && pos[1]!=-1){
                            System.out.println("Player 1 choose");
                            System.out.println("Type 1:move pawn1 11 steps");
                            System.out.println("Type 2:move pawn2 11 steps");
                            Scanner scan = new Scanner(System.in);
                            int t = scan.nextInt();
                            if (t == 1) {
                                pos[0] = pos[0] + 11;
                                if (pos[0] > 59) {
                                    pos[0] = pos[0] - 60;
                                }

                                //check if pawn needs to be inserted at the red home squares
                                if(pos[0]==3){
                                    posRed[0]=1;
                                    pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                    IsInHomeRedSquares1=true;
                                }else if(pos[0]==4){
                                    posRed[0]=2;
                                    pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                    IsInHomeRedSquares1=true;
                                }else if(pos[0]==5){
                                    posRed[0]=3;
                                    pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                                    IsInHomeRedSquares1=true;
                                }else if(pos[0]==6){
                                    posRed[0]=4;
                                    pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                                    IsInHomeRedSquares1=true;
                                }else if(pos[0]==7){
                                    posRed[0]=5;
                                    pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                                    IsInHomeRedSquares1=true;
                                }else if(pos[0]==8){
                                    posRed[0]=-1;
                                    pawn[0].setBounds(80,325,50,50);
                                    IsInHomeRedSquares1=true;
                                    p0fin=true;
                                }else if(pos[0]==9){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[0]=pos[0]-11;
                                    if(pos[0]<0){
                                        pos[0]=60+pos[0];
                                    }
                                }else if(pos[0]==10){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[0]=pos[0]-11;
                                    if(pos[0]<0){
                                        pos[0]=60+pos[0];
                                    }
                                }else if(pos[0]==11){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[0]=pos[0]-11;
                                    if(pos[0]<0){
                                        pos[0]=60+pos[0];
                                    }
                                }else if(pos[0]==12){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[0]=pos[0]-11;
                                    if(pos[0]<0){
                                        pos[0]=60+pos[0];
                                    }
                                }else if(pos[0]==13){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[0]=pos[0]-11;
                                    if(pos[0]<0){
                                        pos[0]=60+pos[0];
                                    }
                                }else{
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(1);
                                }

                            } else if (t == 2) {
                                pos[1] = pos[1] + 11;
                                if (pos[1] > 59) {
                                    pos[1] = pos[1] - 60;
                                }
                                //check if pawn needs to be inserted at the red home squares
                                if(pos[1]==3){
                                    posRed[1]=1;
                                    pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                                    IsInHomeRedSquares2=true;
                                }else if(pos[1]==4){
                                    posRed[1]=2;
                                    pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                                    IsInHomeRedSquares2=true;
                                }else if(pos[1]==5){
                                    posRed[1]=3;
                                    pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                                    IsInHomeRedSquares2=true;
                                }else if(pos[1]==6){
                                    posRed[1]=4;
                                    pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                                    IsInHomeRedSquares2=true;
                                }else if(pos[1]==7){
                                    posRed[1]=5;
                                    pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                                    IsInHomeRedSquares2=true;
                                }else if(pos[1]==8){
                                    posRed[1]=-1;
                                    pawn[1].setBounds(135,325,50,50);
                                    IsInHomeRedSquares2=true;
                                    p1fin=true;
                                }else if(pos[1]==9){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[1]=pos[1]-11;
                                    if(pos[1]<0){
                                        pos[1]=60+pos[1];
                                    }
                                }else if(pos[1]==10){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[1]=pos[1]-11;
                                    if(pos[1]<0){
                                        pos[1]=60+pos[1];
                                    }
                                }else if(pos[1]==11){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[1]=pos[1]-11;
                                    if(pos[1]<0){
                                        pos[1]=60+pos[1];
                                    }
                                }else if(pos[1]==12){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[1]=pos[1]-11;
                                    if(pos[1]<0){
                                        pos[1]=60+pos[1];
                                    }
                                }else if(pos[1]==13){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[1]=pos[1]-11;
                                    if(pos[1]<0){
                                        pos[1]=60+pos[1];
                                    }
                                }else{
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(1);
                                }
                            }
                        }else{
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                        }
                    } else if (dec == 2) {
                        if (pos[0] == -1 && pos[1] == -1) {
                            System.out.println("NO SWITCH CAN BE DONE WITH CARD11\nPLEASE FOLD TO CONTINUE");
                        } else if ((pos[0] != -1 && !IsInHomeRedSquares1) && (IsInHomeRedSquares2 ||p1fin)) {
                            if (pos[2] == -1 && pos[3] == -1) {
                                System.out.println("NO SWITCH CAN BE DONE WITH CARD11\nPLEASE FOLD TO CONTINUE");
                            } else {
                                int p2 = -1;
                                int p3 = -1;
                                for (int i = 0; i < 60; i++) {
                                    if (pos[2] == i) {
                                        p2 = i;
                                    }
                                    if (pos[3] == i) {
                                        p3 = i;
                                    }
                                }
                                if (p2 != -1 && p3 != -1) {
                                    System.out.println("Player 1 choose opponent pawn to switch positions!1 or 2");
                                    Scanner sc = new Scanner(System.in);
                                    int r = sc.nextInt();
                                    if (r == 1) {
                                        int temp = pos[0];
                                        pos[0] = pos[2];
                                        pos[2] = temp;
                                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                        SlidesCheck(1);
                                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                        SlidesCheck(1);
                                    } else if (r == 2) {
                                        int temp = pos[0];
                                        pos[0] = pos[3];
                                        pos[3] = temp;
                                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                        SlidesCheck(1);
                                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                        SlidesCheck(1);
                                    }
                                } else if (p2 != -1) {
                                    int temp = pos[0];
                                    pos[0] = pos[2];
                                    pos[2] = temp;
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(1);
                                } else if (p3 != -1) {
                                    int temp = pos[0];
                                    pos[0] = pos[3];
                                    pos[3] = temp;
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(1);
                                }
                            }
                        } else if ((pos[1] != -1 && !IsInHomeRedSquares2)&& (IsInHomeRedSquares1 || p0fin)) {
                            if (pos[2] == -1 && pos[3] == -1) {
                                System.out.println("NO SWITCH CAN BE DONE WITH CARD11");
                            } else {
                                int p2 = -1;
                                int p3 = -1;
                                for (int i = 0; i < 60; i++) {
                                    if (pos[2] == i) {
                                        p2 = i;
                                    }
                                    if (pos[3] == i) {
                                        p3 = i;
                                    }
                                }
                                if (p2 != -1 && p3 != -1) {
                                    System.out.println("Player 1 choose opponent pawn to switch positions!1 or 2");
                                    Scanner sc = new Scanner(System.in);
                                    int r = sc.nextInt();
                                    if (r == 1) {
                                        int temp = pos[1];
                                        pos[1] = pos[2];
                                        pos[2] = temp;
                                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                        SlidesCheck(1);
                                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                        SlidesCheck(1);
                                    } else if (r == 2) {
                                        int temp = pos[1];
                                        pos[1] = pos[3];
                                        pos[3] = temp;
                                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                        SlidesCheck(1);
                                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                        SlidesCheck(1);
                                    }
                                } else if (p2 != -1) {
                                    int temp = pos[1];
                                    pos[1] = pos[2];
                                    pos[2] = temp;
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(1);
                                } else if (p3 != -1) {
                                    int temp = pos[1];
                                    pos[1] = pos[3];
                                    pos[3] = temp;
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(1);
                                }
                            }
                        } else if(!IsInHomeRedSquares1 && !IsInHomeRedSquares2 && pos[0]!=-1 && pos[1]!=-1){
                            System.out.println("Player 1 choose to replace:");
                            System.out.println("Type 1:replace pawn1 with opponent's pawn1");
                            System.out.println("Type 2:replace pawn1 with opponent's pawn2");
                            System.out.println("Type 3:replace pawn2 with opponent's pawn1");
                            System.out.println("Type 4:replace pawn2 with opponent's pawn2");
                            Scanner scanner2 = new Scanner(System.in);
                            int type = scanner2.nextInt();

                            if (pos[2] == -1 && (type == 1 || type == 3)) {
                                type = 5;
                            } else if (pos[3] == -1 && (type == 2 || type == 4)) {
                                type = 5;
                            }


                            switch (type) {
                                case 1:
                                    int temp = pos[0];
                                    pos[0] = pos[2];
                                    pos[2] = temp;
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    break;
                                case 2:
                                    int temp1 = pos[0];
                                    pos[0] = pos[3];
                                    pos[3] = temp1;
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    break;
                                case 3:
                                    int temp2 = pos[1];
                                    pos[1] = pos[2];
                                    pos[2] = temp2;
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    break;
                                case 4:
                                    int temp3 = pos[1];
                                    pos[1] = pos[3];
                                    pos[3] = temp3;
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(1);
                                    break;
                                case 5:
                                    System.out.println("THIS SWITCH CANNOT BE DONE!");
                                    System.out.println("FOLD TO CONTINUE");
                                    break;
                            }
                        }else{
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                        }
                    }
                }
            }
            else if(value==12){
                if(pos[0]==-1 && pos[1]==-1){
                    //draw new cards
                    setDrawCard12(true);
                }else if((pos[0]!=-1 && !IsInHomeRedSquares1)&& (IsInHomeRedSquares2 || p1fin)){
                    System.out.println("Player 1 choose:\nMove pawn1 12 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[0]=pos[0]+12;
                        if(pos[0]>59){
                            pos[0]=pos[0]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[0]==3){
                            posRed[0]=1;
                            pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==4){
                            posRed[0]=2;
                            pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==5){
                            posRed[0]=3;
                            pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==6){
                            posRed[0]=4;
                            pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==7){
                            posRed[0]=5;
                            pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==8){
                            posRed[0]=-1;
                            pawn[0].setBounds(80,325,50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                            p0fin=true;
                        }else if(pos[0]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-11;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==11){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==12){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==13){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==14){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                            SlidesCheck(1);
                            setDrawCard12(false);
                        }

                    }else {
                        //draw card
                        setDrawCard12(true);
                    }
                }else if((pos[1]!=-1 && !IsInHomeRedSquares2)&& (IsInHomeRedSquares1 || p0fin)){
                    System.out.println("Player 1 choose:\nMove pawn2 12 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[1]=pos[1]+12;
                        if(pos[1]>59){
                            pos[1]=pos[1]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[1]==3){
                            posRed[1]=1;
                            pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==4){
                            posRed[1]=2;
                            pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==5){
                            posRed[1]=3;
                            pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==6){
                            posRed[1]=4;
                            pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==7){
                            posRed[1]=5;
                            pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==8){
                            posRed[1]=-1;
                            pawn[1].setBounds(135,325,50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                            p1fin=true;
                        }else if(pos[1]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==11){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==12){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==13){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==14){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                            SlidesCheck(1);
                            setDrawCard12(false);
                        }

                    }else{
                        //draw card
                        setDrawCard12(true);
                    }
                }else if(!IsInHomeRedSquares1 && !IsInHomeRedSquares2 && pos[0]!=-1 && pos[1]!=-1){
                    System.out.println("Player 1 choose:\nMove pawn1 12 steps or move pawn2 12 steps or Draw a new Card.\nType 1 or 2 or 3");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if(p==1){
                        pos[0]=pos[0]+12;
                        if(pos[0]>59){
                            pos[0]=pos[0]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[0]==3){
                            posRed[0]=1;
                            pawn[0].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==4){
                            posRed[0]=2;
                            pawn[0].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==5){
                            posRed[0]=3;
                            pawn[0].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==6){
                            posRed[0]=4;
                            pawn[0].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==7){
                            posRed[0]=5;
                            pawn[0].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[0]==8){
                            posRed[0]=-1;
                            pawn[0].setBounds(80,325,50,50);
                            IsInHomeRedSquares1=true;
                            setDrawCard12(false);
                            p0fin=true;
                        }else if(pos[0]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-11;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==11){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==12){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==13){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else if(pos[0]==14){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[0]=pos[0]-12;
                            if(pos[0]<0){
                                pos[0]=60+pos[0];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                            SlidesCheck(1);
                            setDrawCard12(false);
                        }

                    }else if(p==2){
                        pos[1]=pos[1]+12;
                        if(pos[1]>59){
                            pos[1]=pos[1]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[1]==3){
                            posRed[1]=1;
                            pawn[1].setBounds(redHomeSquares[0].getX(),redHomeSquares[0].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==4){
                            posRed[1]=2;
                            pawn[1].setBounds(redHomeSquares[1].getX(),redHomeSquares[1].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==5){
                            posRed[1]=3;
                            pawn[1].setBounds(redHomeSquares[2].getX(),redHomeSquares[2].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==6){
                            posRed[1]=4;
                            pawn[1].setBounds(redHomeSquares[3].getX(),redHomeSquares[3].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==7){
                            posRed[1]=5;
                            pawn[1].setBounds(redHomeSquares[4].getX(),redHomeSquares[4].getY(),50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[1]==8){
                            posRed[1]=-1;
                            pawn[1].setBounds(135,325,50,50);
                            IsInHomeRedSquares2=true;
                            setDrawCard12(false);
                            p1fin=true;
                        }else if(pos[1]==9){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==11){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==12){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==13){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else if(pos[1]==14){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[1]=pos[1]-12;
                            if(pos[1]<0){
                                pos[1]=60+pos[1];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                            SlidesCheck(1);
                            setDrawCard12(false);
                        }
                    }else if(p==3){
                        System.out.println("Player 1 draw a new card");
                        setDrawCard12(true);
                    }
                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                }
            }

        }else if(player.getID()==2){

            if(value==1){


                if(posYellow[0]==-1 && posYellow[1]==-1){
                    System.out.println("GAME FINISHED");
                    showWinningMessage("PLAYER 2 WON!");
                }

                if(pos[2]==-1 && pos[3]==-1){
                    System.out.println("Player 2 choose pawn to move from the START:1 or 2");
                    Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
                    int pawnNumber= sc.nextInt();
                    while (pawnNumber!=1 && pawnNumber!=2){
                        System.out.println("Please type in the correct value 1 or 2!");
                        pawnNumber= sc.nextInt();
                    }

                    if (pawnNumber==1){
                        pawn[2].setBounds(positions[34].getX(),positions[34].getY(),50,50);
                        pos[2]=34;
                    }else {
                        pawn[3].setBounds(positions[34].getX(),positions[34].getY(),50,50);
                        pos[3]=34;
                    }
                }else if(pos[3]!=-1 && pos[2]==-1){
                    System.out.println("Type 1:Player 2 move pawn1 from start\nType 2:Player 2 move pawn2 one step");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){
                        pawn[2].setBounds(positions[34].getX(),positions[34].getY(),50,50);
                        pos[2]=34;
                    }else{
                        if(posYellow[1]!=0 && posYellow[1]!=-1 && IsInHomeYellowSquares2){
                            posYellow[1]=posYellow[1]+1;
                            if(posYellow[1]==6){
                                pawn[3].setBounds(670,340,50,50);
                                posYellow[1]=-1;
                                p3fin=true;
                            }else {
                                pawn[3].setBounds(yellowHomeSquare[posYellow[1]-1].getX(),yellowHomeSquare[posYellow[1]-1].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }
                        }else if(!IsInHomeYellowSquares2){
                            pos[3]=pos[3]+1;
                            if (pos[3]==33){
                                posYellow[1]=1;
                                pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else{
                                if(pos[3]>59){
                                    pos[3]=pos[3]-60;
                                }
                                pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                                SlidesCheck(2);
                            }
                        }
                    }

                }else if(pos[3] == -1){
                    System.out.println("Type 1:Player 2 move pawn1 one step\nType 2:Player 2 move pawn2 from the start");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){

                        if(posYellow[0]!=0 && posYellow[0]!=1 && IsInHomeYellowSquares1){
                            posYellow[0]=posYellow[0]+1;
                            if(posYellow[0]==6){
                                pawn[2].setBounds(620,340,50,50);
                                posYellow[0]=-1;
                                p2fin=true;
                            }else{
                                pawn[2].setBounds(yellowHomeSquare[posYellow[0]-1].getX(),yellowHomeSquare[posYellow[0]-1].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }
                        }else if(!IsInHomeYellowSquares1){
                            pos[2]=pos[2]+1;
                            if(pos[2]==33){
                                posYellow[0]=1;
                                pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else{
                                if(pos[2]>59){
                                    pos[2]=pos[2]-60;
                                }
                                pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                                SlidesCheck(2);
                            }
                        }
                    }else if(!IsInHomeYellowSquares2 && posYellow[1]!=-1){
                        pawn[3].setBounds(positions[34].getX(), positions[34].getY(), 50, 50);
                        pos[3] = 34;
                    }
                }else {
                    System.out.println("Player 2 choose pawn to move 1 step!");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1 && posYellow[0]!=1){

                        if(posYellow[0] != -1 && IsInHomeYellowSquares1){
                            posYellow[0]=posYellow[0]+1;
                            if(posYellow[0]==6){
                                pawn[2].setBounds(620,340,50,50);
                                posYellow[0]=-1;
                                p2fin=true;
                            }else {
                                pawn[2].setBounds(yellowHomeSquare[posYellow[0]-1].getX(),yellowHomeSquare[posYellow[0]-1].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }
                        }else if(!IsInHomeYellowSquares1){
                            pos[2]=pos[2]+1;
                            if(pos[2]==33){
                                posYellow[0]=1;
                                pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else{
                                if(pos[2]>59){
                                    pos[2]=pos[2]-60;
                                }
                                pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                                SlidesCheck(2);
                            }
                        }

                    }else if(posYellow[1]!=-1){
                        if(posYellow[1] != 0){
                            posYellow[1]=posYellow[1]+1;
                            if(posYellow[1]==6 && IsInHomeYellowSquares2){
                                pawn[3].setBounds(670,340,50,50);
                                posYellow[1]=-1;
                                p3fin=true;
                            }else {
                                pawn[3].setBounds(yellowHomeSquare[posYellow[1]-1].getX(),yellowHomeSquare[posYellow[1]-1].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }
                        }else if(!IsInHomeYellowSquares2){
                            pos[3]=pos[3]+1;
                            if (pos[3]==33){
                                posYellow[1]=1;
                                pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else{
                                if(pos[3]>59){
                                    pos[3]=pos[3]-60;
                                }
                                pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                                SlidesCheck(2);
                            }
                        }
                    }else{
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 1");
                    }

                }
            }
            else if(value==2){
                if(pos[2]==-1 && pos[3]==-1){
                    System.out.println("Player 2 choose pawn to move from the START:1 or 2");
                    Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
                    int pawnNumber= sc.nextInt();
                    while (pawnNumber!=1 && pawnNumber!=2){
                        System.out.println("Please type in the correct value 1 or 2!");
                        pawnNumber= sc.nextInt();
                    }

                    if (pawnNumber==1){
                        pawn[2].setBounds(positions[34].getX(),positions[34].getY(),50,50);
                        pos[2]=34;
                    }else {
                        pawn[3].setBounds(positions[34].getX(),positions[34].getY(),50,50);
                        pos[3]=34;
                    }
                }else if(pos[2]==-1 && posYellow[1]!=-1){
                    System.out.println("Choose Action\nType 1:Player 2 move pawn1 from start\nType 2:Player 2 move pawn2 two steps");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){
                        pawn[2].setBounds(positions[34].getX(),positions[34].getY(),50,50);
                        pos[2]=34;
                    }else{

                        if(posYellow[1]!=0 && posYellow[1]!=-1 && IsInHomeYellowSquares2){
                            posYellow[1]=posYellow[1]+2;
                            if (posYellow[1]==6){
                                pawn[3].setBounds(670,340,50,50);
                                posYellow[1]=-1;
                                p3fin=true;
                            }else if(posYellow[1]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posYellow[1]=posYellow[1]-2;
                            }else{
                                pawn[3].setBounds(yellowHomeSquare[posYellow[1]-1].getX(),yellowHomeSquare[posYellow[1]-1].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }
                        }else if(!IsInHomeYellowSquares2){
                            pos[3]=pos[3]+2;
                            if(pos[3]==33){
                                posYellow[1]=1;
                                pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            } else if (pos[3]==34) {
                                posYellow[1]=2;
                                pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else{
                                if(pos[3]>59){
                                    pos[3]=pos[3]-60;
                                }
                                pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                                SlidesCheck(1);
                            }
                        }

                    }

                }else if(pos[3]==-1 && posYellow[0]!=-1){
                    System.out.println("Choose Action\nType 1:Player 2 move pawn1 two steps\nType 2:Player 2 move pawn2 from the start");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){

                        if(posYellow[0]!=0 && posYellow[0]!=-1 && IsInHomeYellowSquares1){
                            posYellow[0]=posYellow[0]+2;
                            if (posYellow[0]==6){
                                pawn[2].setBounds(620,340,50,50);
                                posYellow[0]=-1;
                                p2fin=true;
                            }else if(posYellow[0]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posYellow[0]=posYellow[0]-2;
                            }else{
                                pawn[2].setBounds(yellowHomeSquare[posYellow[0]-1].getX(),yellowHomeSquare[posYellow[0]-1].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }
                        }else if(!IsInHomeYellowSquares1){
                            pos[2]=pos[2]+2;
                            if(pos[2]==33){
                                posYellow[0]=1;
                                pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else if(pos[2]==34){
                                posYellow[0]=2;
                                pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else{
                                if(pos[2]>59){
                                    pos[2]=pos[2]-60;
                                }
                                pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                                SlidesCheck(2);
                            }
                        }
                    }else {
                        pawn[3].setBounds(positions[34].getX(), positions[34].getY(), 50, 50);
                        pos[3] = 34;
                    }
                }else{
                    System.out.println("Player 2 choose pawn to move 2 steps!");
                    Scanner scanner=new Scanner(System.in);
                    int pawnN=scanner.nextInt();
                    if(pawnN==1){

                        if(posYellow[0]!=0 && posYellow[0]!=-1 && IsInHomeYellowSquares1){
                            posYellow[0]=posYellow[0]+2;
                            if (posYellow[0]==6){
                                pawn[2].setBounds(620,340,50,50);
                                posYellow[0]=-1;
                                p2fin=true;
                            }else if(posYellow[0]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posYellow[0]=posYellow[0]-2;
                            }else{
                                pawn[2].setBounds(yellowHomeSquare[posYellow[0]-1].getX(),yellowHomeSquare[posYellow[0]-1].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }
                        }else if(!IsInHomeYellowSquares1){
                            pos[2]=pos[2]+2;
                            if(pos[2]==33){
                                posYellow[0]=1;
                                pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else if(pos[2]==34){
                                posYellow[0]=2;
                                pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else {
                                if(pos[2]>59){
                                    pos[2]=pos[2]-60;
                                }
                                pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                                SlidesCheck(2);
                            }
                        }
                    }else {

                        if(posYellow[1]!=0 && posYellow[1]!=-1 && IsInHomeYellowSquares2){
                            posYellow[1]=posYellow[1]+2;
                            if (posYellow[1]==6){
                                pawn[3].setBounds(670,340,50,50);
                                posYellow[1]=-1;
                                p3fin=true;
                            }else if(posYellow[1]==7){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD2");
                                posYellow[1]=posYellow[1]-2;
                            }else{
                                pawn[3].setBounds(yellowHomeSquare[posYellow[1]-1].getX(),yellowHomeSquare[posYellow[1]-1].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }
                        }else if(!IsInHomeYellowSquares2){
                            pos[3] = pos[3] + 2;
                            if(pos[3]==33){
                                posYellow[1]=1;
                                pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else if(pos[3]==34){
                                posYellow[1]=2;
                                pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else{
                                if(pos[3]>59){
                                    pos[3]=pos[3]-60;
                                }
                                pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                SlidesCheck(2);
                            }
                        }
                    }
                }
            }
            else if(value==3){
                if(pos[2]!=-1 && pos[3]!=-1 && !IsInHomeYellowSquares1 && !IsInHomeYellowSquares2 && !p2fin && !p3fin){
                    //check if moves are possible
                    pos[2]=pos[2]+3;
                    pos[3]=pos[3]+3;


                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }

                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else{
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }

                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }

                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else {
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }
                else if((pos[2]!=-1 && !IsInHomeYellowSquares1) && (IsInHomeYellowSquares2 || p3fin)){
                    pos[2]=pos[2]+3;
                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }

                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else{
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }

                }
                else if((pos[3]!=-1 && !IsInHomeYellowSquares2) && (IsInHomeYellowSquares1 || p2fin)){
                    pos[3]=pos[3]+3;
                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }

                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else {
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }
                else if(IsInHomeYellowSquares1 && !p2fin){
                    posYellow[0]=posYellow[0]+3;
                    if(posYellow[0]==6){
                        posYellow[0]=-1;
                        pawn[2].setBounds(620,340,50,50);
                        p2fin=true;
                    }else if(posYellow[0]<6){
                        pawn[2].setBounds(yellowHomeSquare[posYellow[0]-1].getX(),yellowHomeSquare[posYellow[0]-1].getY(),50,50);
                    }else {
                        posYellow[0]=posYellow[0]-3;
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                    }

                    if (IsInHomeYellowSquares2 && posYellow[1]!=1){
                        posYellow[1]=posYellow[1]+3;
                        if(posYellow[1]==6){
                            posYellow[1]=-1;
                            pawn[3].setBounds(670,340,50,50);
                            p3fin=true;
                        }else if(posYellow[1]<6){
                            pawn[3].setBounds(yellowHomeSquare[posYellow[1]-1].getX(),yellowHomeSquare[posYellow[1]-1].getY(),50,50);
                        }else {
                            posYellow[1]=posYellow[1]-3;
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                        }
                    }else if(posYellow[1]==-1){
                        showWinningMessage("PLAYER 2 red WON!!!");
                    }else if(pos[3]!=-1 && !IsInHomeRedSquares2){
                        pos[3]=pos[3]+3;
                        if (pos[3]>59){
                            pos[3]=pos[3]-60;
                        }
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }
                else if(IsInHomeYellowSquares2 && !p3fin){
                    posYellow[1]=posYellow[1]+3;
                    if(posYellow[1]==6){
                        posYellow[1]=-1;
                        pawn[3].setBounds(670,340,50,50);
                        p3fin=true;
                    }else if(posYellow[1]<6){
                        pawn[3].setBounds(yellowHomeSquare[posYellow[1]-1].getX(),yellowHomeSquare[posYellow[1]-1].getY(),50,50);
                    }else {
                        posYellow[1]=posYellow[1]-3;
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                    }

                    if (IsInHomeYellowSquares2 && posYellow[0]!=1){
                        posYellow[0]=posYellow[0]+3;
                        if(posYellow[0]==6){
                            posYellow[0]=-1;
                            pawn[2].setBounds(620,340,50,50);
                            p2fin=true;
                        }else if(posYellow[0]<6){
                            pawn[2].setBounds(yellowHomeSquare[posYellow[0]-1].getX(),yellowHomeSquare[posYellow[0]-1].getY(),50,50);
                        }else {
                            posYellow[0]=posYellow[0]-3;
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 3");
                        }
                    }else if(posYellow[0]==-1){
                        showWinningMessage("PLAYER 2 red WON!!!");
                    }else if(pos[2]!=-1 && !IsInHomeYellowSquares2){
                        pos[2]=pos[2]+3;
                        if (pos[2]>59){
                            pos[2]=pos[2]-60;
                        }
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(1);
                    }
                }
            }
            else if(value==-4){

                if(pos[2]!=-1 && pos[3]!=-1){
                    System.out.println("Player 2 choose pawn 1 or 2 to move backwards 4 steps!");
                    Scanner scanner3=new Scanner(System.in);
                    int pawnN=scanner3.nextInt();
                    if(pawnN==1 && !IsInHomeYellowSquares1){
                        pos[2]=pos[2]-4;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }else if (pawnN==2 && !IsInHomeYellowSquares2){
                        pos[3]=pos[3]-4;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM CARD4");
                    }
                }else if(pos[2]!=-1 && !IsInHomeYellowSquares1){
                    pos[2]=pos[2]-4;
                    if(pos[2]<0){
                        pos[2]=60+pos[2];
                    }
                    pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                    SlidesCheck(2);
                }else if(pos[3]!=-1 && !IsInHomeYellowSquares2){
                    pos[3]=pos[3]-4;
                    if(pos[3]<0){
                        pos[3]=60+pos[3];
                    }
                    pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                    SlidesCheck(2);
                }else {
                    System.out.println("MOVE CANNOT BE DONE FROM CARD4");
                }
            }
            else if(value==5){
                if(pos[2]!=-1 && pos[3]!=-1 && !IsInHomeYellowSquares2 && !IsInHomeYellowSquares1){
                    //check if moves are possible
                    pos[2]=pos[2]+5;
                    pos[3]=pos[3]+5;

                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==36){
                        posYellow[0]=4;
                        pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==37){
                        posYellow[0]=5;
                        pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(posYellow[0]==0){
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }


                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==36){
                        posYellow[1]=4;
                        pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==37){
                        posYellow[1]=5;
                        pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(posYellow[1]==0){
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }

                }else if((pos[2]!=-1 && !IsInHomeRedSquares1) && (IsInHomeRedSquares2 || p3fin)){
                    pos[2]=pos[2]+5;
                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==36){
                        posYellow[0]=4;
                        pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==37){
                        posYellow[0]=5;
                        pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(posYellow[0]==0){
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }else if((pos[3]!=-1 && !IsInHomeYellowSquares2) && (IsInHomeYellowSquares1 || p2fin)){
                    pos[3]=pos[3]+5;

                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }
                    //check for if pawn is inserted at the home squares
                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==36){
                        posYellow[1]=4;
                        pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==37){
                        posYellow[1]=5;
                        pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(posYellow[1]==0){
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }else if(posYellow[1]==1){
                    posYellow[1]=-1;
                    pawn[3].setBounds(670,340,50,50);
                    p3fin=true;
                    IsInHomeYellowSquares2=true;
                }else if(posYellow[0]==1){
                    posYellow[0]=-1;
                    pawn[2].setBounds(620,340,50,50);
                    p2fin=true;
                    IsInHomeYellowSquares1=true;
                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD5");
                }
            }
            else if(value==6){
                SorryCardDraw(2);
            }
            else if(value==7){
                if(pos[2]==-1 && pos[3]==-1){
                    System.out.println("MOVE CANNOT BE DONE FROM CARD7");
                }else if(pos[2]!=-1 && pos[3]!=-1 && !IsInHomeYellowSquares1 && !IsInHomeYellowSquares2){
                    System.out.println("Player 2 choose the move of your pawns");
                    System.out.println("Type 1 for:\npawn1 7 steps and pawn2 0 steps");
                    System.out.println("Type 2 for:\npawn1 6 steps and pawn2 1 steps");
                    System.out.println("Type 3 for:\npawn1 5 steps and pawn2 2 steps");
                    System.out.println("Type 4 for:\npawn1 4 steps and pawn2 3 steps");
                    System.out.println("Type 5 for:\npawn1 3 steps and pawn2 4 steps");
                    System.out.println("Type 6 for:\npawn1 2 steps and pawn2 5 steps");
                    System.out.println("Type 7 for:\npawn1 1 steps and pawn2 6 steps");
                    System.out.println("Type 8 for:\npawn1 0 steps and pawn2 7 steps");
                    Scanner scanner=new Scanner(System.in);
                    int type=scanner.nextInt();
                    switch (type){
                        case 1:
                            pos[2]=pos[2]+7;
                            break;
                        case 2:
                            pos[2]=pos[2]+6;
                            pos[3]=pos[3]+1;
                            break;
                        case 3:
                            pos[2]=pos[3]+5;
                            pos[3]=pos[3]+2;
                            break;
                        case 4:
                            pos[2]=pos[2]+4;
                            pos[3]=pos[3]+3;
                            break;
                        case 5:
                            pos[2]=pos[2]+3;
                            pos[3]=pos[3]+4;
                            break;
                        case 6:
                            pos[2]=pos[2]+2;
                            pos[3]=pos[3]+5;
                            break;
                        case 7:
                            pos[2]=pos[2]+1;
                            pos[3]=pos[3]+6;
                            break;
                        case 8:
                            pos[3]=pos[3]+7;
                            break;
                    }
                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }

                    //check for if pawn is inserted at the home squares
                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==36){
                        posYellow[0]=4;
                        pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==37){
                        posYellow[0]=5;
                        pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==38){
                        posYellow[0]=-1;
                        pawn[2].setBounds(620,340,50,50);
                        IsInHomeYellowSquares1=true;
                        p2fin=true;
                    }else if(pos[2]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 7");
                        //get pawn back to its correct position
                        pos[2]=pos[2]-7;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else{
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }


                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }

                    //check for if pawn is inserted at the home squares
                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[2]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==36){
                        posYellow[1]=4;
                        pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==37){
                        posYellow[1]=5;
                        pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==38){
                        posYellow[1]=-1;
                        pawn[3].setBounds(670,340,50,50);
                        IsInHomeYellowSquares2=true;
                        p3fin=true;
                    }else if(pos[3]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 7");
                        //get pawn back to its correct position
                        pos[3]=pos[3]-7;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else{
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }

                }else if((pos[3]!=-1 && !IsInHomeRedSquares2) && (IsInHomeRedSquares1 || p2fin)){
                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }

                    //check for if pawn is inserted at the home squares
                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==36){
                        posYellow[1]=4;
                        pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==37){
                        posYellow[1]=5;
                        pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==38){
                        posYellow[1]=-1;
                        pawn[3].setBounds(670,340,50,50);
                        IsInHomeYellowSquares2=true;
                        p3fin=true;
                    }else if(pos[3]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 7");
                        //get pawn back to its correct position
                        pos[3]=pos[3]-7;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else{
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }else if((pos[2]!=-1 && !IsInHomeYellowSquares1) && (IsInHomeYellowSquares2 || p3fin)){
                    pos[2]=pos[2]+7;
                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }

                    //check for if pawn is inserted at the home squares
                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==36){
                        posYellow[0]=4;
                        pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==37){
                        posYellow[0]=5;
                        pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==38){
                        posYellow[0]=-1;
                        pawn[2].setBounds(620,340,50,50);
                        IsInHomeYellowSquares1=true;
                        p2fin=true;
                    }else if(pos[2]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 7");
                        //get pawn back to its correct position
                        pos[2]=pos[2]-7;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else{
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }
            }
            else if(value==8){
                if(pos[2]==-1 && pos[3]==-1){
                    //draw new cards
                    System.out.println("Player 2 draw a new card");
                    setDrawCard8(true);
                }else if((pos[2]!=-1 && !IsInHomeYellowSquares1) && (IsInHomeYellowSquares2 || p3fin)){
                    System.out.println("Player 2 choose:\nMove pawn1 8 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[2]=pos[2]+8;
                        if(pos[2]>59){
                            pos[2]=pos[2]-60;
                        }

                        if(pos[2]==33){
                            posYellow[0]=1;
                            pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==34){
                            posYellow[0]=2;
                            pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==35){
                            posYellow[0]=3;
                            pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==36){
                            posYellow[0]=4;
                            pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==37){
                            posYellow[0]=5;
                            pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==38){
                            posYellow[0]=-1;
                            pawn[2].setBounds(620,340,50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                            p2fin=true;
                        }else if(pos[2]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[2]=pos[2]-8;
                            if(pos[2]<0){
                                pos[2]=60+pos[0];
                            }
                            setDrawCard8(false);
                        }else if(pos[2]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[2]=pos[2]-8;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                            SlidesCheck(2);
                            setDrawCard8(false);
                        }

                    }else {
                        //draw card
                        System.out.println("Player 2 draw a new card");
                        setDrawCard8(true);
                    }
                }else if((pos[3]!=-1 && !IsInHomeYellowSquares2)&& (IsInHomeYellowSquares1 || p2fin)){
                    System.out.println("Player 2 choose:\nMove pawn2 8 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[3]=pos[3]+8;

                        if(pos[3]>59){
                            pos[3]=pos[3]-60;
                        }

                        if(pos[3]==33){
                            posYellow[1]=1;
                            pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==34){
                            posYellow[1]=2;
                            pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==35){
                            posYellow[1]=3;
                            pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==36){
                            posYellow[1]=4;
                            pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==37){
                            posYellow[1]=5;
                            pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==38){
                            posYellow[1]=-1;
                            pawn[3].setBounds(670,340,50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                            p3fin=true;
                        }else if(pos[3]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[3]=pos[3]-8;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard8(false);
                        }else if(pos[3]==40){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[3]=pos[3]-8;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                            SlidesCheck(2);
                            setDrawCard8(false);
                        }
                    }else{
                        //draw card
                        System.out.println("Player 2 draw a new card");
                        setDrawCard8(true);
                    }
                }else if(!IsInHomeYellowSquares1 && !IsInHomeYellowSquares2 && pos[2]!=-1 && pos[1]!=-1){
                    System.out.println("Player 2 choose:\nMove pawn1 8 steps or move pawn2 8 steps or Draw a card.Type 1 or 2 or 3");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if(p==1 && !IsInHomeYellowSquares1){
                        pos[2]=pos[2]+8;
                        if(pos[2]>59){
                            pos[2]=pos[2]-60;
                        }

                        if(pos[2]==33){
                            posYellow[0]=1;
                            pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==34){
                            posYellow[0]=2;
                            pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==35){
                            posYellow[0]=3;
                            pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==36){
                            posYellow[0]=4;
                            pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==37){
                            posYellow[0]=5;
                            pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                        }else if(pos[2]==38){
                            posYellow[0]=-1;
                            pawn[2].setBounds(620,340,50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard8(false);
                            p2fin=true;
                        }else if(pos[2]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[2]=pos[2]-8;
                            if(pos[2]<0){
                                pos[2]=60+pos[0];
                            }
                            setDrawCard8(false);
                        }else if(pos[2]==10){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[2]=pos[2]-8;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                            SlidesCheck(2);
                            setDrawCard8(false);
                        }
                    }else if(p==2 && !IsInHomeYellowSquares2){
                        pos[3]=pos[3]+8;

                        if(pos[3]>59){
                            pos[3]=pos[3]-60;
                        }

                        if(pos[3]==33){
                            posYellow[1]=1;
                            pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==34){
                            posYellow[1]=2;
                            pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==35){
                            posYellow[1]=3;
                            pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==36){
                            posYellow[1]=4;
                            pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==37){
                            posYellow[1]=5;
                            pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                        }else if(pos[3]==38){
                            posYellow[1]=-1;
                            pawn[3].setBounds(670,340,50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard8(false);
                            p3fin=true;
                        }else if(pos[3]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[3]=pos[3]-8;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard8(false);
                        }else if(pos[3]==40){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                            pos[3]=pos[3]-8;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard8(false);
                        }else {
                            pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                            SlidesCheck(2);
                            setDrawCard8(false);
                        }
                    }else if(p==3){
                        System.out.println("Player 2 draw a new card");
                        setDrawCard8(true);
                    }
                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD 8");
                }
            }
            else if(value==10){
                if(pos[2]==-1 && pos[3]==-1){
                    System.out.println("NO MOVE CAN BE DONE WITH CARD10");
                }else if((pos[2]!=-1 && !IsInHomeYellowSquares1) && (IsInHomeYellowSquares2 || p3fin)){
                    System.out.println("Player 2 choose:");
                    System.out.println("Move pawn1 10 steps or move pawn1 1 step back.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int d=scanner.nextInt();
                    if(d==1){
                        pos[2]=pos[2]+10;
                    }else if (d==2){
                        pos[2]=pos[2]-1;
                    }
                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }

                    if(pos[2]<0){
                        pos[2]=60+pos[2];
                    }

                    //check if pawn needs to be inserted at the red home squares
                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==36){
                        posYellow[0]=4;
                        pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==37){
                        posYellow[0]=5;
                        pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==38){
                        posYellow[0]=-1;
                        pawn[2].setBounds(620,340,50,50);
                        IsInHomeYellowSquares1=true;
                        p2fin=true;
                    }else if(pos[2]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else if(pos[2]==40){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else if(pos[2]==41){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else if(pos[2]==42){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else {
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }

                }else if((pos[3]!=-1 && !IsInHomeYellowSquares2) && (IsInHomeYellowSquares1 || p2fin)){
                    System.out.println("Player 2 choose:");
                    System.out.println("Move pawn2 10 steps or move pawn2 1 step back.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int d=scanner.nextInt();
                    if(d==1){
                        pos[3]=pos[3]+10;
                    }else if (d==2){
                        pos[3]=pos[3]-1;
                    }
                    if(pos[3]>59){
                        pos[1]=pos[3]-60;
                    }
                    if(pos[3]<0){
                        pos[3]=60+pos[3];
                    }

                    //check if pawn needs to be inserted at the red home squares
                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==36){
                        posYellow[1]=4;
                        pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==37){
                        posYellow[1]=5;
                        pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==38){
                        posYellow[1]=-1;
                        pawn[3].setBounds(670,340,50,50);
                        IsInHomeYellowSquares2=true;
                        p3fin=true;
                    }else if(pos[3]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else if(pos[3]==40){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else if(pos[3]==41){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else if(pos[3]==42){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else {
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }
                }else if(!IsInHomeYellowSquares1 && !IsInHomeYellowSquares2 && pos[2]!=-1 && pos[3]!=-1){
                    System.out.println("Player 2 choose:");
                    System.out.println("Type 1 ,move pawn1 10 steps");
                    System.out.println("Type 2 ,move pawn2 10 steps");
                    System.out.println("Type 3 ,move pawn1 1 step back");
                    System.out.println("Type 4 ,move pawn2 1 step back");
                    Scanner scanner=new Scanner(System.in);
                    int d=scanner.nextInt();
                    switch (d){
                        case 1:
                            pos[2]=pos[2]+10;
                            break;
                        case 2:
                            pos[3]=pos[3]+10;
                            break;
                        case 3:
                            pos[2]=pos[2]-1;
                            break;
                        case 4:
                            pos[3]=pos[3]-1;
                            break;
                    }
                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }
                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }
                    if(pos[2]<0){
                        pos[2]=60+pos[2];
                    }
                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==36){
                        posYellow[0]=4;
                        pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==37){
                        posYellow[0]=5;
                        pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                    }else if(pos[2]==38){
                        posYellow[0]=-1;
                        pawn[2].setBounds(620,340,50,50);
                        IsInHomeYellowSquares1=true;
                        p2fin=true;
                    }else if(pos[2]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else if(pos[2]==40){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else if(pos[2]==41){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else if(pos[2]==42){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[2]=pos[2]-10;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                    }else {
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                    }

                    if(pos[3]<0){
                        pos[3]=60+pos[3];
                    }

                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==36){
                        posYellow[1]=4;
                        pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==37){
                        posYellow[1]=5;
                        pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                    }else if(pos[3]==38){
                        posYellow[1]=-1;
                        pawn[3].setBounds(670,340,50,50);
                        IsInHomeYellowSquares2=true;
                        p3fin=true;
                    }else if(pos[3]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else if(pos[3]==40){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else if(pos[3]==41){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else if(pos[3]==42){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                        pos[3]=pos[3]-10;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                    }else {
                        pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                        SlidesCheck(2);
                    }

                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD 10");
                }
            }
            else if(value==11){
                if (pos[2] == -1 && pos[3] == -1) {
                    System.out.println("MOVE CANNOT BE DONE FROM CARD11");
                } else if(!IsInHomeYellowSquares2 || !IsInHomeYellowSquares1){

                    System.out.println("Player 2 choose");
                    System.out.println("Type 1:move any pawn 11 steps");
                    System.out.println("Type 2:switch any of yours pawns with any of your opponents pawns");
                    Scanner scanner1 = new Scanner(System.in);
                    int dec = scanner1.nextInt();

                    if (dec == 1) {
                        if (pos[2] == -1 && pos[3] == -1) {
                            System.out.println("NO MOVE CAN BE DONE WITH CARD11");
                        } else if ((pos[2] != -1 && !IsInHomeYellowSquares1) && (IsInHomeYellowSquares2 || p3fin)) {
                            pos[2] = pos[2] + 11;
                            if (pos[2] > 59) {
                                pos[2] = pos[2] - 60;
                            }
                            //check if pawn needs to be inserted at the red home squares
                            if(pos[2]==33){
                                posYellow[0]=1;
                                pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else if(pos[2]==34){
                                posYellow[0]=2;
                                pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else if(pos[2]==35){
                                posYellow[0]=3;
                                pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else if(pos[2]==36){
                                posYellow[0]=4;
                                pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else if(pos[2]==37){
                                posYellow[0]=5;
                                pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                                IsInHomeYellowSquares1=true;
                            }else if(pos[2]==38){
                                posYellow[0]=-1;
                                pawn[2].setBounds(620,340,50,50);
                                IsInHomeYellowSquares1=true;
                                p2fin=true;
                            }else if(pos[2]==39){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[2]=pos[2]-11;
                                if(pos[2]<0){
                                    pos[2]=60+pos[2];
                                }
                            }else if(pos[2]==40){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[2]=pos[2]-11;
                                if(pos[2]<0){
                                    pos[2]=60+pos[2];
                                }
                            }else if(pos[2]==41){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[2]=pos[2]-11;
                                if(pos[2]<0){
                                    pos[2]=60+pos[2];
                                }
                            }else if(pos[2]==42){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[2]=pos[2]-11;
                                if(pos[2]<0){
                                    pos[2]=60+pos[2];
                                }
                            }else if(pos[2]==43){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[2]=pos[2]-11;
                                if(pos[2]<0){
                                    pos[2]=60+pos[2];
                                }
                            }else{
                                pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                SlidesCheck(2);
                            }
                        } else if ((pos[3] != -1 && !IsInHomeYellowSquares2) && (IsInHomeYellowSquares1 || p2fin )) {
                            pos[3] = pos[3] + 11;
                            if (pos[3] > 59) {
                                pos[3] = pos[3] - 60;
                            }

                            //check if pawn needs to be inserted at the red home squares
                            if(pos[3]==33){
                                posYellow[1]=1;
                                pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else if(pos[3]==34){
                                posYellow[1]=2;
                                pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else if(pos[3]==35){
                                posYellow[1]=3;
                                pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else if(pos[3]==36){
                                posYellow[1]=4;
                                pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else if(pos[3]==37){
                                posYellow[1]=5;
                                pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                                IsInHomeYellowSquares2=true;
                            }else if(pos[3]==38){
                                posYellow[1]=-1;
                                pawn[1].setBounds(670,340,50,50);
                                IsInHomeYellowSquares2=true;
                                p3fin=true;
                            }else if(pos[3]==39){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[3]=pos[3]-11;
                                if(pos[3]<0){
                                    pos[3]=60+pos[3];
                                }
                            }else if(pos[3]==40){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[3]=pos[3]-11;
                                if(pos[3]<0){
                                    pos[3]=60+pos[3];
                                }
                            }else if(pos[3]==41){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[3]=pos[3]-11;
                                if(pos[3]<0){
                                    pos[3]=60+pos[3];
                                }
                            }else if(pos[3]==42){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[3]=pos[3]-11;
                                if(pos[3]<0){
                                    pos[3]=60+pos[3];
                                }
                            }else if(pos[3]==43){
                                System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                pos[3]=pos[3]-11;
                                if(pos[3]<0){
                                    pos[3]=60+pos[3];
                                }
                            }else{
                                pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                SlidesCheck(2);
                            }

                        } else if(!IsInHomeYellowSquares1 && !IsInHomeYellowSquares2 && pos[2]!=-1 && pos[3]!=-1){
                            System.out.println("Player 2 choose");
                            System.out.println("Type 1:move pawn1 11 steps");
                            System.out.println("Type 2:move pawn2 11 steps");
                            Scanner scan = new Scanner(System.in);
                            int t = scan.nextInt();
                            if (t == 1) {
                                pos[2] = pos[2] + 11;
                                if (pos[2] > 59) {
                                    pos[2] = pos[2] - 60;
                                }
                                //check if pawn needs to be inserted at the red home squares
                                if(pos[2]==33){
                                    posYellow[0]=1;
                                    pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                    IsInHomeYellowSquares1=true;
                                }else if(pos[2]==34){
                                    posYellow[0]=2;
                                    pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                    IsInHomeYellowSquares1=true;
                                }else if(pos[2]==35){
                                    posYellow[0]=3;
                                    pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                                    IsInHomeYellowSquares1=true;
                                }else if(pos[2]==36){
                                    posYellow[0]=4;
                                    pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                                    IsInHomeYellowSquares1=true;
                                }else if(pos[2]==37){
                                    posYellow[0]=5;
                                    pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                                    IsInHomeYellowSquares1=true;
                                }else if(pos[2]==38){
                                    posYellow[0]=-1;
                                    pawn[2].setBounds(620,340,50,50);
                                    IsInHomeYellowSquares1=true;
                                    p2fin=true;
                                }else if(pos[2]==39){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[2]=pos[2]-11;
                                    if(pos[2]<0){
                                        pos[2]=60+pos[2];
                                    }
                                }else if(pos[2]==40){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[2]=pos[2]-11;
                                    if(pos[2]<0){
                                        pos[2]=60+pos[2];
                                    }
                                }else if(pos[2]==41){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[2]=pos[2]-11;
                                    if(pos[2]<0){
                                        pos[2]=60+pos[2];
                                    }
                                }else if(pos[2]==42){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[2]=pos[2]-11;
                                    if(pos[2]<0){
                                        pos[2]=60+pos[2];
                                    }
                                }else if(pos[2]==43){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[2]=pos[2]-11;
                                    if(pos[2]<0){
                                        pos[2]=60+pos[2];
                                    }
                                }else{
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(2);
                                }

                            } else if (t == 2) {
                                pos[3] = pos[3] + 11;
                                if (pos[3] > 59) {
                                    pos[3] = pos[3] - 60;
                                }

                                //check if pawn needs to be inserted at the red home squares
                                if(pos[3]==33){
                                    posYellow[1]=1;
                                    pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                                    IsInHomeYellowSquares2=true;
                                }else if(pos[3]==34){
                                    posYellow[1]=2;
                                    pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                                    IsInHomeYellowSquares2=true;
                                }else if(pos[3]==35){
                                    posYellow[1]=3;
                                    pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                                    IsInHomeYellowSquares2=true;
                                }else if(pos[3]==36){
                                    posYellow[1]=4;
                                    pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                                    IsInHomeYellowSquares2=true;
                                }else if(pos[3]==37){
                                    posYellow[1]=5;
                                    pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                                    IsInHomeYellowSquares2=true;
                                }else if(pos[3]==38){
                                    posYellow[1]=-1;
                                    pawn[1].setBounds(670,340,50,50);
                                    IsInHomeYellowSquares2=true;
                                    p3fin=true;
                                }else if(pos[3]==39){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[3]=pos[3]-11;
                                    if(pos[3]<0){
                                        pos[3]=60+pos[3];
                                    }
                                }else if(pos[3]==40){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[3]=pos[3]-11;
                                    if(pos[3]<0){
                                        pos[3]=60+pos[3];
                                    }
                                }else if(pos[3]==41){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[3]=pos[3]-11;
                                    if(pos[3]<0){
                                        pos[3]=60+pos[3];
                                    }
                                }else if(pos[3]==42){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[3]=pos[3]-11;
                                    if(pos[3]<0){
                                        pos[3]=60+pos[3];
                                    }
                                }else if(pos[3]==43){
                                    System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                                    pos[3]=pos[3]-11;
                                    if(pos[3]<0){
                                        pos[3]=60+pos[3];
                                    }
                                }else{
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(2);
                                }
                            }
                        }else{
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                        }
                    } else if (dec == 2) {
                        if (pos[2] == -1 && pos[3] == -1) {
                            System.out.println("NO SWITCH CAN BE DONE WITH CARD11\nPLEASE FOLD TO CONTINUE");
                        } else if ((pos[2] != -1 && !IsInHomeYellowSquares1)&& (IsInHomeYellowSquares2 || p3fin) ) {
                            if (pos[0] == -1 && pos[1] == -1) {
                                System.out.println("NO SWITCH CAN BE DONE WITH CARD11\nPLEASE FOLD TO CONTINUE");
                            } else {
                                int p2 = -1;
                                int p3 = -1;
                                for (int i = 0; i < 60; i++) {
                                    if (pos[0] == i) {
                                        p2 = i;
                                    }
                                    if (pos[1] == i) {
                                        p3 = i;
                                    }
                                }
                                if (p2 != -1 && p3 != -1) {
                                    System.out.println("Player 2 choose opponent pawn to switch positions!1 or 2");
                                    Scanner sc = new Scanner(System.in);
                                    int r = sc.nextInt();
                                    if (r == 1) {
                                        int temp = pos[2];
                                        pos[2] = pos[0];
                                        pos[0] = temp;
                                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[0]].getY(), 50, 50);
                                        SlidesCheck(2);
                                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                        SlidesCheck(2);
                                    } else if (r == 2) {
                                        int temp = pos[2];
                                        pos[2] = pos[1];
                                        pos[1] = temp;
                                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                        SlidesCheck(2);
                                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                        SlidesCheck(2);
                                    }
                                } else if (p2 != -1) {
                                    int temp = pos[2];
                                    pos[2] = pos[0];
                                    pos[0] = temp;
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(2);
                                } else if (p3 != -1) {
                                    int temp = pos[2];
                                    pos[2] = pos[1];
                                    pos[1] = temp;
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(2);
                                }
                            }
                        } else if ((pos[3] != -1 && !IsInHomeYellowSquares2) && (IsInHomeYellowSquares1 || p2fin)) {
                            if (pos[0] == -1 && pos[1] == -1) {
                                System.out.println("NO SWITCH CAN BE DONE WITH CARD11");
                            } else {
                                int p2 = -1;
                                int p3 = -1;
                                for (int i = 0; i < 60; i++) {
                                    if (pos[0] == i) {
                                        p2 = i;
                                    }
                                    if (pos[1] == i) {
                                        p3 = i;
                                    }
                                }
                                if (p2 != -1 && p3 != -1) {
                                    System.out.println("Player 2 choose opponent pawn to switch positions!1 or 2");
                                    Scanner sc = new Scanner(System.in);
                                    int r = sc.nextInt();
                                    if (r == 1) {
                                        int temp = pos[3];
                                        pos[3] = pos[0];
                                        pos[0] = temp;
                                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                        SlidesCheck(2);
                                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                        SlidesCheck(2);
                                    } else if (r == 2) {
                                        int temp = pos[3];
                                        pos[3] = pos[1];
                                        pos[1] = temp;
                                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                        SlidesCheck(2);
                                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                        SlidesCheck(2);
                                    }
                                } else if (p2 != -1) {
                                    int temp = pos[3];
                                    pos[3] = pos[0];
                                    pos[0] = temp;
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(2);
                                } else if (p3 != -1) {
                                    int temp = pos[3];
                                    pos[3] = pos[1];
                                    pos[1] = temp;
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(2);
                                }
                            }
                        } else if(!IsInHomeYellowSquares1 && !IsInHomeYellowSquares2 && pos[2]!=-1 && pos[3]!=-1){
                            System.out.println("Player 2 choose to replace:");
                            System.out.println("Type 1:replace pawn1 with opponent's pawn1");
                            System.out.println("Type 2:replace pawn1 with opponent's pawn2");
                            System.out.println("Type 3:replace pawn2 with opponent's pawn1");
                            System.out.println("Type 4:replace pawn2 with opponent's pawn2");
                            Scanner scanner2 = new Scanner(System.in);
                            int type = scanner2.nextInt();

                            if (pos[0] == -1 && (type == 1 || type == 3)) {
                                type = 5;
                            } else if (pos[1] == -1 && (type == 2 || type == 4)) {
                                type = 5;
                            }


                            switch (type) {
                                case 1:
                                    int temp = pos[2];
                                    pos[2] = pos[0];
                                    pos[0] = temp;
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    break;
                                case 2:
                                    int temp1 = pos[2];
                                    pos[2] = pos[1];
                                    pos[1] = temp1;
                                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    break;
                                case 3:
                                    int temp2 = pos[3];
                                    pos[3] = pos[0];
                                    pos[0] = temp2;
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    break;
                                case 4:
                                    int temp3 = pos[3];
                                    pos[3] = pos[1];
                                    pos[1] = temp3;
                                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                                    SlidesCheck(2);
                                    break;
                                case 5:
                                    System.out.println("THIS SWITCH CANNOT BE DONE!");
                                    System.out.println("FOLD TO CONTINUE");
                                    break;
                            }
                        }else{
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 11");
                        }
                    }
                }
            }
            else if(value==12){

                if(pos[2]==-1 && pos[3]==-1){
                    //draw new cards
                    setDrawCard12(true);
                }else if(pos[3]==-1 && !IsInHomeYellowSquares1){
                    System.out.println("Player 2 choose:\nMove pawn1 12 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[2]=pos[2]+12;
                        if(pos[2]>59){
                            pos[2]=pos[2]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[2]==33){
                            posYellow[0]=1;
                            pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==34){
                            posYellow[0]=2;
                            pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==35){
                            posYellow[0]=3;
                            pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==36){
                            posYellow[0]=4;
                            pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==37){
                            posYellow[0]=5;
                            pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==38){
                            posYellow[0]=-1;
                            pawn[2].setBounds(620,340,50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                            p2fin=true;
                        }else if(pos[2]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==40){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==41){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==42){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==43){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==44){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                            SlidesCheck(2);
                            setDrawCard12(false);
                        }

                    }else {
                        //draw card
                        setDrawCard12(true);
                    }
                }else if(pos[2]==-1 && !IsInHomeYellowSquares2){
                    System.out.println("Player 2 choose:\nMove pawn2 12 steps or draw new card.Type 1 or 2");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if (p==1){
                        pos[3]=pos[3]+12;
                        if(pos[3]>59){
                            pos[3]=pos[3]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[3]==33){
                            posYellow[1]=1;
                            pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==34){
                            posYellow[1]=2;
                            pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[2]==35){
                            posYellow[1]=3;
                            pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==36){
                            posYellow[1]=4;
                            pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==37){
                            posYellow[1]=5;
                            pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==38){
                            posYellow[1]=-1;
                            pawn[3].setBounds(670,340,50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                            p3fin=true;
                        }else if(pos[3]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==40){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==41){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==42){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==43){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==44){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                            SlidesCheck(2);
                            setDrawCard12(false);
                        }
                    }else{
                        //draw card
                        setDrawCard12(true);
                    }
                }else if(!IsInHomeYellowSquares1 && !IsInHomeYellowSquares2){
                    System.out.println("Player 2 choose:\nMove pawn1 12 steps or move pawn2 12 steps or Draw a new Card.\nType 1 or 2 or 3");
                    Scanner scanner=new Scanner(System.in);
                    int p=scanner.nextInt();
                    if(p==1){
                        pos[2]=pos[2]+12;
                        if(pos[2]>59){
                            pos[2]=pos[2]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[2]==33){
                            posYellow[0]=1;
                            pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==34){
                            posYellow[0]=2;
                            pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==35){
                            posYellow[0]=3;
                            pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==36){
                            posYellow[0]=4;
                            pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==37){
                            posYellow[0]=5;
                            pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                        }else if(pos[2]==38){
                            posYellow[0]=-1;
                            pawn[2].setBounds(620,340,50,50);
                            IsInHomeYellowSquares1=true;
                            setDrawCard12(false);
                            p2fin=true;
                        }else if(pos[2]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==40){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==41){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==42){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==43){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else if(pos[2]==44){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[2]=pos[2]-12;
                            if(pos[2]<0){
                                pos[2]=60+pos[2];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                            SlidesCheck(2);
                            setDrawCard12(false);
                        }

                    }else if(p==2){
                        pos[3]=pos[3]+12;
                        if(pos[3]>59){
                            pos[3]=pos[3]-60;
                        }

                        //check if pawn needs to be inserted at the red home squares
                        if(pos[3]==33){
                            posYellow[1]=1;
                            pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==34){
                            posYellow[1]=2;
                            pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[2]==35){
                            posYellow[1]=3;
                            pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==36){
                            posYellow[1]=4;
                            pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==37){
                            posYellow[1]=5;
                            pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                        }else if(pos[3]==38){
                            posYellow[1]=-1;
                            pawn[3].setBounds(670,340,50,50);
                            IsInHomeYellowSquares2=true;
                            setDrawCard12(false);
                            p3fin=true;
                        }else if(pos[3]==39){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==40){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==41){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==42){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==43){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else if(pos[3]==44){
                            System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                            pos[3]=pos[3]-12;
                            if(pos[3]<0){
                                pos[3]=60+pos[3];
                            }
                            setDrawCard12(false);
                        }else{
                            pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                            SlidesCheck(2);
                            setDrawCard12(false);
                        }
                    }else if(p==3){
                        System.out.println("Player 2 draw a new card");
                        setDrawCard12(true);
                    }
                }else if((pos[2]!=-1 && !IsInHomeYellowSquares1) && (IsInHomeYellowSquares2 || p3fin)){
                    pos[2]=pos[2]+12;
                    if(pos[2]>59){
                        pos[2]=pos[2]-60;
                    }

                    //check if pawn needs to be inserted at the red home squares
                    if(pos[2]==33){
                        posYellow[0]=1;
                        pawn[2].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                        setDrawCard12(false);
                    }else if(pos[2]==34){
                        posYellow[0]=2;
                        pawn[2].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                        setDrawCard12(false);
                    }else if(pos[2]==35){
                        posYellow[0]=3;
                        pawn[2].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                        setDrawCard12(false);
                    }else if(pos[2]==36){
                        posYellow[0]=4;
                        pawn[2].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                        setDrawCard12(false);
                    }else if(pos[2]==37){
                        posYellow[0]=5;
                        pawn[2].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares1=true;
                        setDrawCard12(false);
                    }else if(pos[2]==38){
                        posYellow[0]=-1;
                        pawn[2].setBounds(620,340,50,50);
                        IsInHomeYellowSquares1=true;
                        setDrawCard12(false);
                        p2fin=true;
                    }else if(pos[2]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[2]=pos[2]-12;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                        setDrawCard12(false);
                    }else if(pos[2]==40){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[2]=pos[2]-12;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                        setDrawCard12(false);
                    }else if(pos[2]==41){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[2]=pos[2]-12;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                        setDrawCard12(false);
                    }else if(pos[2]==42){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[2]=pos[2]-12;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                        setDrawCard12(false);
                    }else if(pos[2]==43){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[2]=pos[2]-12;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                        setDrawCard12(false);
                    }else if(pos[2]==44){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[2]=pos[2]-12;
                        if(pos[2]<0){
                            pos[2]=60+pos[2];
                        }
                        setDrawCard12(false);
                    }else{
                        pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                        SlidesCheck(2);
                        setDrawCard12(false);
                    }
                }else if((pos[3]!=-1 && !IsInHomeYellowSquares2) && (IsInHomeYellowSquares1 || p2fin)){
                    pos[3]=pos[3]+12;
                    if(pos[3]>59){
                        pos[3]=pos[3]-60;
                    }

                    //check if pawn needs to be inserted at the red home squares
                    if(pos[3]==33){
                        posYellow[1]=1;
                        pawn[3].setBounds(yellowHomeSquare[0].getX(),yellowHomeSquare[0].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                        setDrawCard12(false);
                    }else if(pos[3]==34){
                        posYellow[1]=2;
                        pawn[3].setBounds(yellowHomeSquare[1].getX(),yellowHomeSquare[1].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                        setDrawCard12(false);
                    }else if(pos[2]==35){
                        posYellow[1]=3;
                        pawn[3].setBounds(yellowHomeSquare[2].getX(),yellowHomeSquare[2].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                        setDrawCard12(false);
                    }else if(pos[3]==36){
                        posYellow[1]=4;
                        pawn[3].setBounds(yellowHomeSquare[3].getX(),yellowHomeSquare[3].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                        setDrawCard12(false);
                    }else if(pos[3]==37){
                        posYellow[1]=5;
                        pawn[3].setBounds(yellowHomeSquare[4].getX(),yellowHomeSquare[4].getY(),50,50);
                        IsInHomeYellowSquares2=true;
                        setDrawCard12(false);
                    }else if(pos[3]==38){
                        posYellow[1]=-1;
                        pawn[3].setBounds(670,340,50,50);
                        IsInHomeYellowSquares2=true;
                        setDrawCard12(false);
                        p3fin=true;
                    }else if(pos[3]==39){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[3]=pos[3]-12;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                        setDrawCard12(false);
                    }else if(pos[3]==40){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[3]=pos[3]-12;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                        setDrawCard12(false);
                    }else if(pos[3]==41){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[3]=pos[3]-12;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                        setDrawCard12(false);
                    }else if(pos[3]==42){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[3]=pos[3]-12;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                        setDrawCard12(false);
                    }else if(pos[3]==43){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[3]=pos[3]-12;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                        setDrawCard12(false);
                    }else if(pos[3]==44){
                        System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                        pos[3]=pos[3]-12;
                        if(pos[3]<0){
                            pos[3]=60+pos[3];
                        }
                        setDrawCard12(false);
                    }else{
                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                        SlidesCheck(2);
                        setDrawCard12(false);
                    }
                }else{
                    System.out.println("MOVE CANNOT BE DONE WITH CARD 12");
                }
            }
        }
        basic_panel.repaint();
    }


    /**
     * method to set if we need to draw a new card,after drawing card 8
     * @param drawCard8
     */
    public void setDrawCard8(boolean drawCard8) {
        DrawCard8 = drawCard8;
    }

    /**
     * method to get the info that we need to draw a new card,after drawing card 8
     * @return
     */
    public boolean isDrawCard8() {
        return DrawCard8;
    }

    /**
     * method to set if we need to draw a new card,after drawing card 12
     * @param drawCard12
     */
    public void setDrawCard12(boolean drawCard12){
        DrawCard12=drawCard12;
    }


    /**
     * method to get the info that we need to draw a new card,after drawing card 12
     * @return
     */
    public boolean isDrawCard12() {
        return DrawCard12;
    }

    /**
     * method to see if the game is finished
     */
    public void gameFIN(){
        if(posRed[0]==-1 && posRed[1]==-1){
            showWinningMessage("PLAYER 1 WON!");
        }else if(posYellow[0]==-1 && posYellow[1]==-1){
            showWinningMessage("PLAYER 2 WON");
        }
    }

    /**
     * method that calls other method that check the slide cases
     * @param p
     */
    public void SlidesCheck(int p){
        SlidesCheckerBlue(p);
        SlidesCheckerYellow(p);
        SlidesCheckerRed(p);
        SlidesCheckerGreen(p);
    }

    /**
     * check green slide cases
     * @param p
     */
    public void SlidesCheckerGreen(int p){
        if (p==1){
            //check if pawns landed on green slide start(first one)
            if(pos[0]==46|| pos[1]==46){
                //check opp pawn is the slide
                if(pos[2]==47 || pos[2]==48 || pos[2]==49){
                    pawn[2].setBounds(510,690,50,50);
                    pos[2]=-1;
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                } else if (pos[3]==47 || pos[3]==48 || pos[3]==49){
                    pawn[3].setBounds(560,690,50,50);
                    pos[3]=-1;
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                }
                //get pawn to the end
                if(pos[0]==46){
                    pos[0]=49;
                    pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                }else if(pos[1]==46){
                    pos[1]=49;
                    pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                }

            }//check if pawns landed on green slide start(second one)
            else if (pos[0]==54 || pos[1]==54){
                //check opp pawn is in the slide
                if(pos[2]==55 || pos[2]==56 || pos[2]==57 || pos[2]==58){
                    pawn[2].setBounds(510,690,50,50);
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                    pos[2]=-1;
                }else if(pos[3]==55 || pos[3]==56 || pos[3]==57 || pos[3]==58){
                    pawn[3].setBounds(560,690,50,50);
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                    pos[3]=-1;
                }
                //get pawn to the end
                if(pos[0]==54){
                    pos[0]=58;
                    pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                }else if(pos[1]==54){
                    pos[1]=58;
                    pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                }
            }
            basic_panel.repaint();
        }else if (p==2){

            if(pos[2]==46 || pos[3]==46){

                //check opp pawn is the slide
                if(pos[0]==47 || pos[0]==48 || pos[0]==49){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                } else if (pos[1]==47 || pos[1]==48 || pos[1]==49) {
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }
                //get pawn to the end
                if(pos[2]==46){
                    pos[2]=49;
                    pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                }else if(pos[3]==46){
                    pos[3]=49;
                    pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                }
            }else if (pos[2]==54 || pos[3]==54){
                //check opp pawn is in the slide
                if(pos[0]==55 || pos[0]==56 || pos[0]==57 || pos[0]==58){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                }else if(pos[1]==55 || pos[1]==56 || pos[1]==57 || pos[1]==58){
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }
                //get pawn to the end
                if(pos[2]==54){
                    pos[2]=58;
                    pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                }else if(pos[3]==54){
                    pos[3]=58;
                    pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                }
            }
            basic_panel.repaint();

        }
    }

    /**
     * check blue slide cases
     * @param p
     */
    public void SlidesCheckerBlue(int p){
        if (p==1){
            //check if pawns landed on blue slide start(first one)
            if(pos[0]==16 || pos[1]==16){
                //check opp pawn is the slide
                if(pos[2]==17 || pos[2]==18 || pos[2]==19){
                    pawn[2].setBounds(510,690,50,50);
                    pos[2]=-1;
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                } else if (pos[3]==17 || pos[3]==18 || pos[3]==19){
                    pawn[3].setBounds(560,690,50,50);
                    pos[3]=-1;
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                }
                //get pawn to the end
                if(pos[0]==16){
                    pos[0]=19;
                    pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                }else if(pos[1]==16){
                    pos[1]=19;
                    pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                }

            }//check if pawns landed on blue slide start(second one)
            else if (pos[0]==24 || pos[1]==24){
                //check opp pawn is in the slide
                if(pos[2]==25 || pos[2]==26 || pos[2]==27 || pos[2]==28){
                    pawn[2].setBounds(510,690,50,50);
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                    pos[2]=-1;
                }else if(pos[3]==25 || pos[3]==26 || pos[3]==27 || pos[3]==28){
                    pawn[3].setBounds(560,690,50,50);
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                    pos[3]=-1;
                }
                //get pawn to the end
                if(pos[0]==24){
                    pos[0]=28;
                    pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                }else if(pos[1]==24){
                    pos[1]=28;
                    pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                }
            }
            basic_panel.repaint();
        }else if (p==2){

            if(pos[2]==16 || pos[3]==16){

                //check opp pawn is the slide
                if(pos[0]==17 || pos[0]==18 || pos[0]==19){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                } else if (pos[1]==17 || pos[1]==18 || pos[1]==19) {
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }
                //get pawn to the end
                if(pos[2]==16){
                    pos[2]=19;
                    pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                }else if(pos[3]==16){
                    pos[3]=19;
                    pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                }
            }else if (pos[2]==24 || pos[3]==24){
                //check opp pawn is in the slide
                if(pos[0]==25 || pos[0]==26 || pos[0]==27 || pos[0]==28){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                }else if(pos[1]==25 || pos[1]==26 || pos[1]==27 || pos[1]==28){
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }
                //get pawn to the end
                if(pos[2]==24){
                    pos[2]=28;
                    pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                }else if (pos[3]==24){
                    pos[3]=28;
                    pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                }
            }
            basic_panel.repaint();
        }
    }

    /**
     * check yellow slide cases
     * @param p
     */
    public void SlidesCheckerYellow(int p){
        if (p==1){
            //check if pawns landed on yellow slide start(first one)
            if(pos[0]==31 || pos[1]==31){
                //check opp pawn is the slide
                if(pos[2]==32 || pos[2]==33 || pos[2]==34){
                    pawn[2].setBounds(510,690,50,50);
                    pos[2]=-1;
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                } else if (pos[3]==32 || pos[3]==33 || pos[3]==34){
                    pawn[3].setBounds(560,690,50,50);
                    pos[3]=-1;
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                }
                //get pawn to the end
                if(pos[0]==31){
                    pos[0]=34;
                    pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                }else if(pos[1]==31){
                    pos[1]=34;
                    pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                }

            }//check if pawns landed on green slide start(second one)
            else if (pos[0]==39 || pos[1]==39){
                //check opp pawn is in the slide
                if(pos[2]==40 || pos[2]==41 || pos[2]==42 || pos[2]==43){
                    pawn[2].setBounds(510,690,50,50);
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                    pos[2]=-1;
                }else if(pos[3]==40 || pos[3]==41 || pos[3]==42 || pos[3]==43){
                    pawn[3].setBounds(560,690,50,50);
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                    pos[3]=-1;
                }
                //get pawn to the end
                if(pos[0]==39){
                    pos[0]=43;
                    pawn[0].setBounds(positions[pos[0]].getX(),positions[pos[0]].getY(),50,50);
                }else if(pos[1]==39){
                    pos[1]=43;
                    pawn[1].setBounds(positions[pos[1]].getX(),positions[pos[1]].getY(),50,50);
                }
            }
            basic_panel.repaint();
        }else if (p==2){

            if(pos[2]==31 || pos[3]==31){

                //check opp pawn is the slide
                if(pos[0]==32 || pos[0]==33 || pos[0]==34){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                } else if (pos[1]==32 || pos[1]==33 || pos[1]==34) {
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }

            }else if (pos[2]==39 || pos[3]==39){
                //check opp pawn is in the slide
                if(pos[0]==40 || pos[0]==41 || pos[0]==42 || pos[0]==43){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                }else if(pos[1]==40 || pos[1]==41 || pos[1]==42 || pos[1]==43){
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }
            }
            basic_panel.repaint();
        }
    }

    /**
     * check red slide cases
     * @param p
     */
    public void SlidesCheckerRed(int p){
        if (p==1){
            //check if pawns landed on red slide start(first one)
            if(pos[0]==1 || pos[1]==1){
                //check opp pawn is the slide
                if(pos[2]==2 || pos[2]==3 || pos[2]==4){
                    pawn[2].setBounds(510,690,50,50);
                    pos[2]=-1;
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                } else if (pos[3]==2 || pos[3]==3 || pos[3]==4){
                    pawn[3].setBounds(560,690,50,50);
                    pos[3]=-1;
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                }

            }//check if pawns landed on red slide start(second one)
            else if (pos[0]==9 || pos[1]==9){
                //check opp pawn is in the slide
                if(pos[2]==10 || pos[2]==11 || pos[2]==12 || pos[2]==13){
                    pawn[2].setBounds(510,690,50,50);
                    System.out.println("Player 2 Pawn 1 goes back to START!");
                    pos[2]=-1;
                }else if(pos[3]==10 || pos[3]==11 || pos[3]==12 || pos[3]==13){
                    pawn[3].setBounds(560,690,50,50);
                    System.out.println("Player 2 Pawn 2 goes back to START!");
                    pos[3]=-1;
                }
            }
            basic_panel.repaint();
        }else if (p==2){

            if(pos[2]==1 || pos[3]==1){

                //check opp pawn is the slide
                if(pos[0]==2 || pos[0]==3 || pos[0]==4){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                } else if (pos[1]==2 || pos[1]==3 || pos[1]==4) {
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }
                //get pawn to the end
                if(pos[2]==1){
                    pos[2]=4;
                    pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                }else if(pos[3]==1){
                    pos[3]=4;
                    pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                }
            }else if (pos[2]==9 || pos[3]==9){
                //check opp pawn is in the slide
                if(pos[0]==10 || pos[0]==11 || pos[0]==12 || pos[0]==13){
                    pawn[0].setBounds(180,75,50,50);
                    System.out.println("Player 1 Pawn 1 goes back to START!");
                    pos[0]=-1;
                }else if(pos[1]==10 || pos[1]==11 || pos[1]==12 || pos[1]==13){
                    pawn[1].setBounds(235,75,50,50);
                    System.out.println("Player 1 Pawn 2 goes back to START!");
                    pos[1]=-1;
                }
                //get pawn to the end
                if(pos[2]==9){
                    pos[2]=13;
                    pawn[2].setBounds(positions[pos[2]].getX(),positions[pos[2]].getY(),50,50);
                }else if (pos[3]==9){
                    pos[3]=13;
                    pawn[3].setBounds(positions[pos[3]].getX(),positions[pos[3]].getY(),50,50);
                }
            }
            basic_panel.repaint();
        }
    }

    /**
     * method that implements the logic behind when a sorry card is drawn
     * @param p
     */
    public void SorryCardDraw(int p) {
        if (p == 1) {
            if (pos[0] == -1 && pos[1] == -1) {
                int p2 = -1;
                int p3 = -1;
                for (int i = 0; i < 60; i++) {
                    if (pos[2] == i) {
                        p2 = i;
                    }
                    if (pos[3] == i) {
                        p3 = i;
                    }
                }
                if (p2 != -1 && p3 != -1) {
                    System.out.println("Player 1 choose pawn to move from start 1 or 2");
                    Scanner scanner = new Scanner(System.in);
                    int pawnN = scanner.nextInt();
                    System.out.println("Player 1 choose now the yellow pawn to replace and send it to START!1 or 2");
                    int r = scanner.nextInt();
                    if (pawnN == 1 && !IsInHomeRedSquares1) {
                        if (r == 1 && !IsInHomeYellowSquares1) {
                            pos[0] = pos[2];
                            pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                            pos[2] = -1;
                            pawn[2].setBounds(510, 690, 50, 50);
                            System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn1");
                        } else if (r == 2 && !IsInHomeYellowSquares2) {
                            pos[0] = pos[3];
                            pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                            pos[3] = -1;
                            pawn[3].setBounds(560, 690, 50, 50);
                            System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn2");
                        }else{
                            System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                        }
                    } else if (pawnN == 2 && !IsInHomeRedSquares2) {
                        if (r == 1 && !IsInHomeYellowSquares1) {
                            pos[1] = pos[2];
                            pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                            pos[2] = -1;
                            pawn[2].setBounds(510, 690, 50, 50);
                            System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn1");
                        } else if (r == 2 && !IsInHomeYellowSquares2) {
                            pos[1] = pos[3];
                            pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                            pos[3] = -1;
                            pawn[3].setBounds(560, 690, 50, 50);
                            System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn2");
                        }else{
                            System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                        }
                    }
                } else if (p2 != -1) {
                    System.out.println("Player 1 choose pawn to move from start 1 or 2");
                    Scanner scanner = new Scanner(System.in);
                    int pawnN = scanner.nextInt();
                    if (pawnN == 1 && !IsInHomeYellowSquares1) {
                        pos[0] = pos[2];
                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                        pos[2] = -1;
                        pawn[2].setBounds(510, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn1");
                    } else if (pawnN == 2 && !IsInHomeYellowSquares1) {
                        pos[1] = pos[2];
                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                        pos[2] = -1;
                        pawn[2].setBounds(510, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn1");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else if (p3 != -1) {
                    System.out.println("Player 1 choose pawn to move from start 1 or 2");
                    Scanner scanner = new Scanner(System.in);
                    int pawnN = scanner.nextInt();
                    if (pawnN == 1 && !IsInHomeYellowSquares2) {
                        pos[0] = pos[3];
                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                        pos[3] = -1;
                        pawn[3].setBounds(560, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn2");
                    } else if (pawnN == 2 && IsInHomeYellowSquares2) {
                        pos[1] = pos[3];
                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                        pos[3] = -1;
                        pawn[3].setBounds(560, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn2");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else {
                    System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                }

            }else if (pos[0]==-1) {
                int p2 = -1;
                int p3 = -1;
                for (int i = 0; i < 60; i++) {
                    if (pos[2] == i) {
                        p2 = i;
                    }
                    if (pos[3] == i) {
                        p3 = i;
                    }
                }
                if (p2 != -1 && p3 != -1) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Player 1 choose now the yellow pawn to replace and send it to START!1 or 2");
                    int r = scanner.nextInt();
                    if (r == 1 && !IsInHomeYellowSquares1) {
                        pos[0] = pos[2];
                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                        pos[2] = -1;
                        pawn[2].setBounds(510, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn1");
                    } else if (r == 2 && !IsInHomeYellowSquares2) {
                        pos[0] = pos[3];
                        pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                        pos[3] = -1;
                        pawn[3].setBounds(560, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn2");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else if (p2 != -1 && !IsInHomeYellowSquares1) {
                    pos[0] = pos[2];
                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                    pos[2] = -1;
                    pawn[2].setBounds(510, 690, 50, 50);
                    System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn1");
                } else if (p3 != -1 && !IsInHomeYellowSquares2) {
                    pos[0] = pos[3];
                    pawn[0].setBounds(positions[pos[0]].getX(), positions[pos[0]].getY(), 50, 50);
                    pos[3] = -1;
                    pawn[3].setBounds(560, 690, 50, 50);
                    System.out.println("SORRY!!! Player 1 pawn1 replaces Player 2 pawn2");
                } else {
                    System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                }
            }else if (pos[1] == -1) {
                int p2 = -1;
                int p3 = -1;
                for (int i = 0; i < 60; i++) {
                    if (pos[2] == i) {
                        p2 = i;
                    }
                    if (pos[3] == i) {
                        p3 = i;
                    }
                }
                if (p2 != -1 && p3 != -1) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Player 1 choose now the yellow pawn to replace and send it to START!1 or 2");
                    int r = scanner.nextInt();
                    if (r == 1 && !IsInHomeYellowSquares1) {
                        pos[1] = pos[2];
                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                        pos[2] = -1;
                        pawn[2].setBounds(510, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn1");
                    } else if (r == 2 && !IsInHomeYellowSquares2) {
                        pos[1] = pos[3];
                        pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                        pos[3] = -1;
                        pawn[3].setBounds(560, 690, 50, 50);
                        System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn2");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else if (p2 != -1 && !IsInHomeYellowSquares1) {
                    pos[1] = pos[2];
                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                    pos[2] = -1;
                    pawn[2].setBounds(510, 690, 50, 50);
                    System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn1");
                } else if (p3 != -1 && !IsInHomeYellowSquares2) {
                    pos[1] = pos[3];
                    pawn[1].setBounds(positions[pos[1]].getX(), positions[pos[1]].getY(), 50, 50);
                    pos[3] = -1;
                    pawn[3].setBounds(560, 690, 50, 50);
                    System.out.println("SORRY!!! Player 1 pawn2 replaces Player 2 pawn2");
                } else {
                    System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                }
            }
        }else if(p==2){
            if (pos[2] == -1 && pos[3] == -1) {
                int p2 = -1;
                int p3 = -1;
                for (int i = 0; i < 60; i++) {
                    if (pos[0] == i) {
                        p2 = i;
                    }
                    if (pos[1] == i) {
                        p3 = i;
                    }
                }
                if (p2 != -1 && p3 != -1) {
                    System.out.println("Player 2 choose pawn to move from start 1 or 2");
                    Scanner scanner = new Scanner(System.in);
                    int pawnN = scanner.nextInt();
                    System.out.println("Player 2 choose now the red pawn to replace and send it to START!1 or 2");
                    int r = scanner.nextInt();
                    if (pawnN == 1) {
                        if (r == 1 && !IsInHomeRedSquares1) {
                            pos[2] = pos[0];
                            pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                            pos[0] = -1;
                            pawn[0].setBounds(180, 75, 50, 50);
                            System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn1");
                        } else if (r == 2 && !IsInHomeRedSquares2) {
                            pos[2] = pos[1];
                            pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                            pos[1] = -1;
                            pawn[1].setBounds(235, 75, 50, 50);
                            System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn2");
                        }else{
                            System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                        }
                    } else if (pawnN == 2) {
                        if (r == 1 && !IsInHomeRedSquares1) {
                            pos[3] = pos[0];
                            pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                            pos[0] = -1;
                            pawn[0].setBounds(180, 75, 50, 50);
                            System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn1");
                        } else if (r == 2 && !IsInHomeRedSquares2) {
                            pos[3] = pos[1];
                            pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                            pos[1] = -1;
                            pawn[1].setBounds(235, 75, 50, 50);
                            System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn2");
                        }else{
                            System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                        }
                    }
                } else if (p2 != -1) {
                    System.out.println("Player 2 choose pawn to move from start 1 or 2");
                    Scanner scanner = new Scanner(System.in);
                    int pawnN = scanner.nextInt();
                    if (pawnN == 1 && !IsInHomeRedSquares1) {
                        pos[2] = pos[0];
                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                        pos[0] = -1;
                        pawn[0].setBounds(180, 75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn1");
                    } else if (pawnN == 2 && !IsInHomeRedSquares1) {
                        pos[3] = pos[0];
                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                        pos[0] = -1;
                        pawn[0].setBounds(180, 75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn1");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else if (p3 != -1) {
                    System.out.println("Player 2 choose pawn to move from start 1 or 2");
                    Scanner scanner = new Scanner(System.in);
                    int pawnN = scanner.nextInt();
                    if (pawnN == 1 && !IsInHomeRedSquares2){
                        pos[2] = pos[1];
                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                        pos[1] = -1;
                        pawn[1].setBounds(235, 75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn2");
                    } else if (pawnN == 2 && !IsInHomeRedSquares2) {
                        pos[3] = pos[1];
                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                        pos[1] = -1;
                        pawn[1].setBounds(235, 75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn2");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else {
                    System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                }

            }else if (pos[2]==-1) {
                int p2 = -1;
                int p3 = -1;
                for (int i = 0; i < 60; i++) {
                    if (pos[0] == i) {
                        p2 = i;
                    }
                    if (pos[1] == i) {
                        p3 = i;
                    }
                }
                if (p2 != -1 && p3 != -1) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Player 2 choose now the red pawn to replace and send it to START!1 or 2");
                    int r = scanner.nextInt();
                    if (r == 1 && !IsInHomeRedSquares1) {
                        pos[2] = pos[0];
                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                        pos[0] = -1;
                        pawn[0].setBounds(180, 75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn1");
                    } else if (r == 2 && !IsInHomeRedSquares2) {
                        pos[2] = pos[1];
                        pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                        pos[1] = -1;
                        pawn[1].setBounds(235, 75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn2");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else if (p2 != -1 && !IsInHomeRedSquares1) {
                    pos[2] = pos[0];
                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                    pos[0] = -1;
                    pawn[0].setBounds(180, 75, 50, 50);
                    System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn1");
                } else if (p3 != -1 && !IsInHomeRedSquares2) {
                    pos[2] = pos[1];
                    pawn[2].setBounds(positions[pos[2]].getX(), positions[pos[2]].getY(), 50, 50);
                    pos[1] = -1;
                    pawn[1].setBounds(235, 75, 50, 50);
                    System.out.println("SORRY!!! Player 2 pawn1 replaces Player 1 pawn2");
                } else {
                    System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                }
            }else if (pos[3] == -1) {
                int p2 = -1;
                int p3 = -1;
                for (int i = 0; i < 60; i++) {
                    if (pos[0] == i) {
                        p2 = i;
                    }
                    if (pos[1] == i) {
                        p3 = i;
                    }
                }
                if (p2 != -1 && p3 != -1) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Player 2 choose now the red pawn to replace and send it to START!1 or 2");
                    int r = scanner.nextInt();
                    if (r == 1 && !IsInHomeRedSquares1) {
                        pos[3] = pos[0];
                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                        pos[0] = -1;
                        pawn[0].setBounds(180, 75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn1");
                    } else if (r == 2 && !IsInHomeRedSquares2) {
                        pos[3] = pos[1];
                        pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                        pos[1] = -1;
                        pawn[1].setBounds(235,75, 50, 50);
                        System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn2");
                    }else{
                        System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                    }
                } else if (p2 != -1 && !IsInHomeRedSquares1) {
                    pos[3] = pos[0];
                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                    pos[0] = -1;
                    pawn[0].setBounds(180, 75, 50, 50);
                    System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn1");
                } else if (p3 != -1 && !IsInHomeRedSquares2) {
                    pos[3] = pos[1];
                    pawn[3].setBounds(positions[pos[3]].getX(), positions[pos[3]].getY(), 50, 50);
                    pos[1] = -1;
                    pawn[1].setBounds(235, 75, 50, 50);
                    System.out.println("SORRY!!! Player 2 pawn2 replaces Player 1 pawn2");
                } else {
                    System.out.println("MOVE CANNOT BE DONE FROM SORRY CARD!");
                }
            }
        }
    }


    /**
     * method that initializes the pawns
     */
    public void init_pawns(){
        pawn =new JLabel[4];
        for(int i=0;i<4;i++){
            pawn[i]=new JLabel();
        }

        URL image =cldr.getResource("mvc/images/pawns/redPawn1.png");
        Image image1=new ImageIcon(image).getImage();
        pawn[0].setIcon(new ImageIcon(image1));
        pawn[0].setBounds(180,75,50,50);
        pos[0]=-1;
        basic_panel.add(pawn[0],0);

        URL image2 =cldr.getResource("mvc/images/pawns/redPawn2.png");
        Image image12=new ImageIcon(image2).getImage();
        pawn[1].setIcon(new ImageIcon(image12));
        pawn[1].setBounds(235,75,50,50);
        pos[1]=-1;
        basic_panel.add(pawn[1],0);

        URL imageY =cldr.getResource("mvc/images/pawns/yellowPawn1.png");
        Image image1Y=new ImageIcon(imageY).getImage();
        pawn[2].setIcon(new ImageIcon(image1Y));
        pawn[2].setBounds(510,690,50,50);
        pos[2]=-1;
        basic_panel.add(pawn[2],0);

        URL imageY2 =cldr.getResource("mvc/images/pawns/yellowPawn2.png");
        Image imageYe=new ImageIcon(imageY2).getImage();
        pawn[3].setIcon(new ImageIcon(imageYe));
        pawn[3].setBounds(560,690,50,50);
        pos[3]=-1;
        basic_panel.add(pawn[3],0);

    }


    public JLabel[] getPaws(){
        return pawn;
    }

    /**
     * method that updates the info box
     */
    public void updateInfobox(String message){
        infobox.setText(message);
        basic_panel.repaint();
    }

    /**
     * method that shows the winning message if any player has won
     * @param message
     */
    public void showWinningMessage(String message){
        JOptionPane.showMessageDialog(this,message);
    }

}