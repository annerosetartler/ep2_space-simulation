import java.awt.*;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    private static int numberOfBodies = 10000; //changeable by Scanner input in main
    private static final double windowSize = 500;
    private static int minMass = 100;
    private static Random random = new Random();
    private static final double spreader = 0.8;//a value between 0 and 1 (not 0)
    private static final Color[] colorArray = {StdDraw.WHITE, StdDraw.CYAN, StdDraw.GREEN, StdDraw.PRINCETON_ORANGE, StdDraw.BOOK_LIGHT_BLUE, StdDraw.BOOK_RED};
    private static int currentTextColor = 0;
    private static int ticker = 0;
    public static double T = 0; //changeable by Scanner input in main

    public static Octree octree;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        readNumberOfBodies(input);

        readTInput(input);

        printOutput();

        octree = new Octree("starShow",-windowSize,windowSize,-windowSize,windowSize,-windowSize,windowSize);

        //there are always 4 bodies in the simulation plus the ones the user added through the scanner
        setUp();


        while (true){
            try{
                System.out.println(octree.getDepth());

                calcForces();

                moveBodies();

                drawSky();

                buildTree();

                ticker++;
            }catch(NullPointerException e){
                System.out.println("Hope you enjoyed the show! Good night! :)");
                break;
            }
        }
    }

    // changes numberOfBodies according to input of user using a Scanner
    private static void readNumberOfBodies(Scanner input){
        System.out.println("Please enter the number of bodies as integer: ");
        boolean flag = true;
        while (flag){
            if(input.hasNextInt()){
                numberOfBodies = input.nextInt();
                if(numberOfBodies >= 0 && numberOfBodies <= 11000){
                    flag = false;
                    break;
                }else{
                    System.out.println("Please choose a number from 0 to 11000.");
                }
            }else {
                String typedIn = input.next();
                System.out.println("Wrong Input type: " + typedIn);
            }
        }
    }

    // changes T according to input of user using a Scanner
    private static void readTInput(Scanner input){
        System.out.println("Please enter T as double: ");
        boolean flag = true;
        while (flag){
            if(input.hasNext()){
                if (input.hasNextDouble()) {
                    T = input.nextDouble();
                    if(T >= 0.0 && T <= 1.0){
                        flag = false;
                        input.close();
                        break;
                    }else{
                        System.out.println("T has to be in [0,1].");
                    }
                } else {
                    String typedIn = input.next();
                    System.out.println("Wrong Input type: " + typedIn);
                }
            }
        }
    }

    // prints out a cute star slogan, the currently used T and number of overall bodies used in the simulation
    private static void printOutput(){
        System.out.println(" ****************************************\n" + starSlogan() + "\n ****************************************\n");
        int numBodies = numberOfBodies + 4;
        System.out.println("number of bodies: " + numBodies);
        System.out.println("T: " + T);
    }

    //sets up StdDraw window and star starting points
    public static void setUp(){
        StdDraw.setCanvasSize((int)Math.ceil(windowSize*2), (int)Math.ceil(windowSize*2));
        StdDraw.setXscale(-windowSize,windowSize);
        StdDraw.setYscale(-windowSize,windowSize);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.black);

        createStars();
        createBigStars();
    }

    //creates numberOfBodies stars
    public static void createStars(){
        for (int i = 0; i < numberOfBodies; i++) {
            double randomNumber = Math.random();
            octree.add(new Body((1e4 * randomNumber * windowSize + minMass), randomNumber * (1.0/700) * windowSize, randomizeClusters(), new Vector3())); // no start movement right now
        }
    }

    //adds big stars to make movement more interesting/less even
    //always part of the simulation
    public static void createBigStars(){
        octree.add(new Body("AlphaCentauris",1e10  * windowSize,(1.0/300) * windowSize,new Vector3(0,(windowSize/3),0),new Vector3(-(windowSize/1000),-(windowSize/1000),0),StdDraw.WHITE));
        octree.add(new Body("FancyStarName",1e10 * windowSize,(1.0/300) * windowSize,new Vector3(-(windowSize/3),-(windowSize/5),0),new Vector3((windowSize/1000),-(windowSize/1000),0),StdDraw.PINK));
        octree.add(new Body("BigNebula",1e10 * windowSize,(1.0/300) * windowSize,new Vector3((windowSize/3),-(windowSize/5),0),new Vector3(-(windowSize/1000),(windowSize/1000),0),StdDraw.YELLOW));
        octree.add(new Body("DerGroßeWagen",1e10 * windowSize,(1.0/300) * windowSize,new Vector3(-(windowSize-(windowSize/10)),-(windowSize-(windowSize/10)),0),new Vector3((windowSize/2000),(windowSize/2000),0),StdDraw.ORANGE));
    }

    //helps to make star starting formations less uniform by partitioning in two forms of coordinate-generation
    private static Vector3 randomizeClusters(){
        double random = Math.random();
        if ((int)(random*100) <= 60 ){
            return generateMultiClusterPosition();
        }
        else return generateOneClusterPosition();
    }

    //generates star-accumulations
    private static Vector3 generateMultiClusterPosition(){
        double xPeak = (random.nextInt(3)-1) * windowSize/2.0;
        double yPeak = (random.nextInt(3)-1) * windowSize/2.0;
        double zPeak = (random.nextInt(3)-1) * windowSize/2.0;
        double x = random.nextGaussian() * (windowSize/2) * spreader;
        double y = random.nextGaussian() * (windowSize/2) * spreader;
        double z = random.nextGaussian() * (windowSize/2) * spreader;
        return new Vector3(x+xPeak, y+yPeak,z+zPeak);
    }

    //generates more star-positions in the inner part of the window
    private static Vector3 generateOneClusterPosition(){
        double x = random.nextGaussian() * windowSize * spreader;
        double y = random.nextGaussian() * windowSize * spreader;
        double z = random.nextGaussian() * windowSize * spreader;
        return new Vector3(x, y,z);
    }

    //calculates forces for each body according to barnes hut algorithm
    public static void calcForces(){
        for (Body body : octree) {
            octree.calculate(body);
        }
    }

    //moves the bodies according to the newly calculated movement-vector
    public static void moveBodies(){
        for (Body body : octree) {
            body.move();
        }
    }

    //adds all bodies with updated movement in the octree
    public static void buildTree(){
        Octree octNew = new Octree("nextRound",-windowSize,windowSize,-windowSize,windowSize,-windowSize,windowSize);
        for (Body body : octree) {
            octNew.add(body);
        }
        octree = octNew;
    }

    //draws the new sky-formation
    public static void drawSky(){
        StdDraw.clear(StdDraw.BLACK);
        for (Body body: octree) {
            body.draw();
        }
        if(ticker % 40 == 0){
            currentTextColor++;
            currentTextColor %= colorArray.length;
        }
        StdDraw.setPenColor(colorArray[currentTextColor]);
        Font font = new Font("arial",Font.ITALIC,20);
        StdDraw.setFont(font);
        StdDraw.text(-180,400,"SHOOT FOR THE MOON...YOU MIGHT DRAW() THE SKY");
        StdDraw.show();
    }

    // randomly chooses one of 10 cute star slogans
    public static String starSlogan(){
        int rando = random.nextInt(10);
        String slogan = "";
        switch (rando){
            case 0:
                slogan = "Stars can't shine without darkness.";
                break;
            case 1:
                slogan = "This night is sparkling...don't you let it go";
                break;
            case 2:
                slogan = "Look up and get lost...";
                break;
            case 3:
                slogan = "Nothing lasts forever but the earth and the sky.";
                break;
            case 4:
                slogan = "Maybe we belong among the stars.";
                break;
            case 5:
                slogan = "Mars is waiting to be reached...";
                break;
            case 6:
                slogan = "Stars are not always visible, but they are always there.";
                break;
            case 7:
                slogan = "Reach for the stars...you might become one.";
                break;
            case 8:
                slogan = "Let's dance beneath the stars and forget the world...";
                break;
            case 9:
                slogan = "The great kings of the past look down on us from the stars.";
                break;
            default:
                slogan = "All systems are go!";
        }
        return slogan;
    }

}
