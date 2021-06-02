// represents an octant in 3D space
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

    //returns length of this Octant
    public double getLength(){
        return Math.abs(xmax - xmin);
    }

    //checks if the coordinates represented by Vector3 v are inside this Octant
    public boolean contains(Vector3 v){
        if (v == null){
            return false;
        }else{
            return v.isInOctant(xmin,xmax,ymin,ymax,zmin,zmax);
        }
    }

    //generates all childOctants with the corresponding coordinates
    public Octant[] childOctants(){
        Octant[] octants = new Octant[8];
        double middle = (xmin + xmax)/2.0;
        octants[0] = new Octant(xmin,middle,middle,ymax,middle,zmax);//nwTop
        octants[1] = new Octant(middle,xmax,middle,ymax,middle,zmax);//noTop
        octants[2] = new Octant(xmin,middle,ymin,middle,middle,zmax);//swTop
        octants[3] = new Octant(middle,xmax,ymin,middle,middle,zmax);//soTop
        octants[4] = new Octant(xmin,middle,middle,ymax,zmin,middle);//nwBottom
        octants[5] = new Octant(middle,xmax,middle,ymax,zmin,middle);//noBottom
        octants[6] = new Octant(xmin,middle,ymin,middle,zmin,middle);//swBottom
        octants[7] = new Octant(middle,xmax,ymin,middle,zmin,middle);//soBottom
        return octants;
    }

    // returns a readable representation of the information of this Octant:
    //Octant: xmin, xmax, ymin, ymax, zmin, zmax
    public String toString(){
        return "Octant: " + xmin +", "+ xmax +", "+  ymin +", "+  ymax +", "+  zmin +", "+ zmax;
    }
}
