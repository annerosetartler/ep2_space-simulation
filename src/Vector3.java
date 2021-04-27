import java.awt.*;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(){}

    // Returns the sum of this vector and vector 'v'.
    public Vector3 plus(Vector3 v) {
        return new Vector3(x + v.x,y + v.y,z + v.z);
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {
        return new Vector3(x*d,y*d,z*d);
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {
        return this.plus(v.times(-1));
    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {
        Vector3 distV = this.minus(v);
        return Math.sqrt(distV.x*distV.x + distV.y*distV.y + distV.z*distV.z);
    }

    // Returns the length (norm) of this vector.
    public double length() {
        return this.distanceTo(new Vector3());
    }

    // Normalizes this vector: changes the length of this vector such that it becomes 1.
    // The direction and orientation of the vector is not affected.
    public void normalize() {
        double length = this.length();
        x /= length;
        y /= length;
        z /= length;
    }

    // Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
    // in the existing StdDraw canvas. The z-coordinate is not used.
    public void drawAsDot(double radius, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x,y,radius);
    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {
        return "[" + x + "," + y + "," + z + "]";
    }
}
