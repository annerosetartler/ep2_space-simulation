public class Simulation {
    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    public static void main(String[] args) {
        Body sun = new Body("Sol",1.989e30,696340e3,new Vector3(0,0,0),new Vector3(0,0,0),StdDraw.YELLOW);
        Body earth = new Body("Earth",5.972e24,6371e3,new Vector3(-5,-5,-5),new Vector3(-10308.53,-28169.38,0),StdDraw.BLUE);
        Body mercury = new Body("Mercury",3.301e23,2440e3,new Vector3(5,5,5),new Vector3(-17117.83,-46297.48,-1925.57),StdDraw.GRAY);
        Body venus = new Body("Venus",4.86747e24,6052e3,new Vector3(-1,-1,-1),new Vector3(-34446.02,-5567.47,2181.10),StdDraw.PINK);
        Body mars = new Body("Mars",6.41712e23,3390e3,new Vector3(1,1,1),new Vector3(20651.98,-10186.67,-2302.79),StdDraw.RED);
        Octree test = new Octree("test1",-10,10,-10,10,-10,10);

        test.add(sun);
        test.add(earth);
        test.add(mercury);
        test.add(venus);
        test.add(mars);

    }

}
