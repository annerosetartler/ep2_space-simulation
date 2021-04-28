public class InnerNode implements TreeNode {

    private Body body;
    private TreeNode nwTop, noTop, soTop, swTop, nwBottom, noBottom, soBottom, swBottom;
    private Octant octant;

    public InnerNode(){}

    public boolean add(Body b){
        //if nwTop.contains? {
        //if (nwTop == null){ nwTop = new LeafNode(b, nwTopOctant}
        //else{ nwTop.add(b);
        //}
        //.....

        //else nur return true?
        return false;
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

    //Methode, die hilft zu wissen, in welchem Octant des Parent wir sind


}
