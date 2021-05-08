import java.awt.*;

public class Simulation {
    // gravitational constant
    public static final double G = 1.2;
            //6.6743e-11;

    private static final int numberOfBodies = 10000;
    private static final double windowSize = 500;

    //T
    public static final double T = 1;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static Body[] bodyArray = new Body[numberOfBodies];


    public static void main(String[] args) {

        Octree test = new Octree("test1",-windowSize,windowSize,-windowSize,windowSize,-windowSize,windowSize);

        //setup schleife
        for (int i = 0; i < bodyArray.length; i++) {
            bodyArray[i] = new Body("b" + i, (Math.random()*(3-1))+1, (Math.random()*(2-1))+1, new Vector3(windowSize), new Vector3(), StdDraw.WHITE );
        }

        setUp();



        double seconds = 0;

        while (true){
            seconds++;
            test.reset();



            //tree aufbauen
            for (int i = 0; i < bodyArray.length; i++) {
                test.add(bodyArray[i]);
            }

            //kräfte berechnen für alle bodies
            for (int i = 0; i < bodyArray.length; i++) {
                test.calculate(bodyArray[i]);
            }

            //bodies bewegen
            for (int i = 0; i < bodyArray.length; i++) {
                bodyArray[i].move();
            }

            //alle paar sekunden bodies zeichnen inkl. StdDraw.clear(); Body.draw(); und StdDraw.show(); /////bodyArray[i].draw();

            StdDraw.clear(StdDraw.BLACK);
            for (int i = 0; i < bodyArray.length; i++) {
                bodyArray[i].draw();
            }
            StdDraw.show();
        }




        //array traversieren und für jeden Body berechnungsmethode aufrufen:
        // test.calculate(body);

    }

    public static void setUp(){
        StdDraw.setCanvasSize((int)Math.ceil(windowSize*2), (int)Math.ceil(windowSize*2));
        StdDraw.setXscale(-(int)Math.ceil(windowSize), (int)Math.ceil(windowSize));
        StdDraw.setYscale(-(int)Math.ceil(windowSize), (int)Math.ceil(windowSize));
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.black);
    }

}
