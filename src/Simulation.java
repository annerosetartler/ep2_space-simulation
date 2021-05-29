import java.awt.*;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    private static int numberOfBodies = 10000;
    private static final double windowSize = 500;
    private static int minMass = 100;
    private static Random random = new Random();
    private static final double spreader = 0.8;//a value between 0 and 1 (not 0)

    //T
    public static double T = 0;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static Body[] bodyArray = new Body[numberOfBodies];
    public static Octree octree;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the number of bodies as integer: ");
        boolean flag = true;
        while (flag){
            if(input.hasNextInt()){
                numberOfBodies = input.nextInt();
                if(numberOfBodies >= 5 && numberOfBodies <= 11000){
                    flag = false;
                    break;
                }else{
                    System.out.println("Please choose a number from 5 to 11000.");
                }
            }else {
                String typedIn = input.next();
                System.out.println("Wrong Input type: " + typedIn);
            }
        }
        System.out.println("Please enter T as double: ");
        flag = true;
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
        System.out.println(starSlogan());
        System.out.println("number of bodies: " + numberOfBodies);
        System.out.println("T: " + T);
        //bodyArray = new Body[numberOfBodies];

        octree = new Octree("test1",-windowSize,windowSize,-windowSize,windowSize,-windowSize,windowSize);

        setUp();

        while (true){

            calcForces();

            moveBodies();

            drawSky();

        }

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

    //adds big stars to make movement more interesting/less even (written over some already randomly created stars in array)
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

    //moves the bodies according to the newly calculated movement-vecor
    public static void moveBodies(){
        for (Body body : octree) {
            body.move();
        }
    }

    //adds all bodies with updated movement in the octree
    public static void buildTree(Octree oct){
        for (int i = 0; i < bodyArray.length; i++) {
            oct.add(bodyArray[i]);
        }
    }

    //draws the new sky-formation
    public static void drawSky(){
        StdDraw.clear(StdDraw.BLACK);
        for (Body body: octree) {
            body.draw();
        }
        StdDraw.show();
    }

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
