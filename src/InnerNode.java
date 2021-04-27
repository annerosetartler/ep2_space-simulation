public class InnerNode implements TreeNode {

    private Body body;
    private TreeNode nwTop, noTop, soTop, swTop, nwBottom, noBottom, soBottom, swBottom;
    private Octant octant;

    public InnerNode(){}

    public void add(Body b){
        //if nwTop.contains? nwTop.add;
    }

    public Vector3 getCenter(){
        return null;
    }

    public double getMass(){
        return 0.0;
    }

    public double getLength(){
        return octant.getLength();
    }
}
