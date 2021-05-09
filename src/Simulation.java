import java.awt.*;
import java.util.Random;

public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    private static final int numberOfBodies = 10000;
    private static final double windowSize = 500;
    private static int minMass = 100;
    private static Random random = new Random();

    //T
    public static final double T = 1;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static Body[] bodyArray = new Body[numberOfBodies];


    public static void main(String[] args) {
        Octree octree = new Octree("test1",-windowSize,windowSize,-windowSize,windowSize,-windowSize,windowSize);

        setUp();


        while (true){

            octree.reset();

            buildTree(octree);

            calcForces(octree);

            moveBodies();

            drawSky();

        }

    }

    //sets up StdDraw window and star starting points
    public static void setUp(){                                 //nochmal window und window size ansehen, ob man da noch was schöner machen kann
        StdDraw.setCanvasSize((int)Math.ceil(windowSize*2), (int)Math.ceil(windowSize*2));
        StdDraw.setXscale(-(int)Math.ceil(windowSize/2), (int)Math.ceil(windowSize/2));
        StdDraw.setYscale(-(int)Math.ceil(windowSize/2), (int)Math.ceil(windowSize/2));
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.black);

        createStars();
        createBigStars();
    }

    //creates numberOfBodies stars
    public static void createStars(){
        for (int i = 0; i < bodyArray.length; i++) { //10 stars space for the "big stars" fraction
            double randomNumber = Math.random();
            bodyArray[i] = new Body((1e4 * randomNumber * windowSize + minMass), randomNumber * (1.0/900) * windowSize, randomizeClusters(), new Vector3()); // no start movement right now
        }
    }

    //adds big stars to make movement more interesting/less even (written over some already randomly created stars in array)
    public static void createBigStars(){
        //bodyArray[9991] = new Body();

        //black hole
        bodyArray[9996] = new Body("BlackHole", 1e9 * 0.9 * windowSize, (1.0/1000) * windowSize, new Vector3(+windowSize/11,-windowSize/7.2,0),
                new Vector3(), StdDraw.BLACK);

        //...
        bodyArray[9998] = new Body(1e9  * windowSize, (1.0/610) * windowSize,  new Vector3(-(windowSize/2)+(windowSize/15),-(windowSize/6),windowSize/11),
                new Vector3(0.1, -0.5,-0.1));


        bodyArray[9999] = new Body( "BigBody", 1e8 * 0.8 * windowSize, (1.0/400) * windowSize,
                new Vector3(-windowSize/8,-(windowSize/4)+(windowSize/10),-(windowSize/6)), new Vector3(), StdDraw.BOOK_RED);
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
        double xPeak = (random.nextInt(3)-1) * windowSize/4.0;
        double yPeak = (random.nextInt(3)-1) * windowSize/4.0;
        double zPeak = (random.nextInt(3)-1) * windowSize/4.0;
        double x = random.nextGaussian() * (windowSize/4) * T;
        double y = random.nextGaussian() * (windowSize/4) * T;
        double z = random.nextGaussian() * (windowSize/4) * T;
        return new Vector3(x+xPeak, y+yPeak,z+zPeak);
    }

    //generates more star-positions in the inner part of the window
    private static Vector3 generateOneClusterPosition(){
        double x = random.nextGaussian() * (windowSize/2) * T;
        double y = random.nextGaussian() * (windowSize/2) * T;
        double z = random.nextGaussian() * (windowSize/2) * T;
        return new Vector3(x, y,z);
    }

    //calculates forces for each body according to barnes hut algorithm
    public static void calcForces(Octree oct){
        for (int i = 0; i < bodyArray.length; i++) {
            oct.calculate(bodyArray[i]);
        }
    }

    //moves the bodies according to the newly calculated movement-vecor
    public static void moveBodies(){
        for (int i = 0; i < bodyArray.length; i++) {
            bodyArray[i].move();
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
        for (int i = 0; i < bodyArray.length; i++) {
            bodyArray[i].draw();
        }
        StdDraw.show();
    }

}
