import java.awt.*;

public class Body {
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private Color color; // for drawing the body.
    private Vector3 force;

    public Body(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color){
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
    }

    public Body(double mass, double radius, Vector3 position, Vector3 currentMovement){
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = StdDraw.WHITE;
    }

    public void setForce(Vector3 f){
        force = f;
    }

    // Returns the name of the body
    public String getName(){
        return name;
    }

    //Returns 1 as there is only one body
    public int numberOfBodies(){
        return 1;
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
        double force = Simulation.G * mass * body.mass/(r * r);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force)
    // Hint: see simulation loop in Simulation.java to find out how this is done
    public void move(Vector3 force) {
        Vector3 lastPos = position;
        // F = m*a -> a = F/m
        position = currentMovement.plus(position.plus(force.times(1/mass)));
        currentMovement = position.minus(lastPos);
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
    // (use a conversion based on the logarithm as in 'Simulation.java').
    // Hint: use the method drawAsDot implemented in Vector3 for this
    public void draw() {
        position.drawAsDot(1e9*Math.log10(radius),color);
    }
}
