public class Octant {

    private double xmin, xmax, ymin, ymax, zmin, zmax;

    public Octant(double xmin, double xmax, double ymin, double ymax, double zmin, double zmax){
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.zmin = zmin;
        this.zmax = zmax;
    }

    public double getLength(){
        return Math.abs(xmax - xmin);
    }

    public boolean contains(Vector3 v){
        return false;
    }

    //equals methode, der schaut, ob zwei octanten gleich sind


}
