import java.awt.*;
import java.util.Random;

public class Body {
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private Color color; // for drawing the body.
    private Vector3 force; //calculated force vector
    private static final Color[] colorArray = {StdDraw.WHITE, StdDraw.CYAN, StdDraw.GREEN, StdDraw.PRINCETON_ORANGE, StdDraw.BOOK_LIGHT_BLUE, StdDraw.BOOK_RED};

    public Body(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color){
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
        this.force = new Vector3();
    }

    // constructor for anonymous stars
    public Body(double mass, double radius, Vector3 position, Vector3 currentMovement){
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = colorArray[(new Random()).nextInt(colorArray.length)];
        this.force = new Vector3();
    }

    // Sets force
    public void setForce(Vector3 f){
        force = f;
    }

    //Returns the mass of the body
    public double getMass(){
        return mass;
    }

    //Returns the gravitational center of this component
    public Vector3 getMassCenter(){
        return position;
    }

    // Returns the distance between this body and the specified 'body'.
    public double distanceTo(Body body) {
        return position.distanceTo(body.position);
    }

    //Returns a vector representing the gravitational force exerted by 'body' on this body.
    //The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the masses of the objects
    //interacting, r being the distance between the centers of the masses and G being the gravitational constant.
    //To calculate the force exerted on b1, simply multiply the normalized vector pointing from b1 to b2 with the
    //calculated force
    public Vector3 gravitationalForce(Body body) {
        Vector3 direction = body.position.minus(position);
        double r = this.distanceTo(body);
        direction.normalize();
        if (r == 0){
            return new Vector3();
        }
        double force = Simulation.G * mass * body.mass/(r * r);
        return direction.times(force);
    }

    //Calculates the gravitational force excerted on a body or a conglomerate of bodies
    // (represented by the position and mass) on this body
    public Vector3 gravitationalForce(Vector3 position, double mass){
        Vector3 direction = position.minus(this.position);
        double r = this.position.distanceTo(position);
        direction.normalize();
        if (r == 0){
            return new Vector3();
        }
        double force = Simulation.G * this.mass * mass/(r * r);
        return direction.times(force);
    }

    // Moves this body to a new position, according to its force vector
    // and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and its force)
    public void move() {
        Vector3 lastPos = position;
        // F = m*a -> a = F/m
        position = currentMovement.plus(position.plus(force.times(1/mass)));
        currentMovement = position.minus(lastPos);
    }

    // Returns a string with the name of this body
    public String toString() {
        return name;
    }

    // Draws the body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    public void draw() {
        position.drawAsDot(radius, color);
    }
}
