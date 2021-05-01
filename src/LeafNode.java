public class LeafNode implements TreeNode {

    private Body body;
    private InnerNode parent;
    private Octant octant;
    private int childID;//to know which child this leaf is to its parent, ersparen uns so noch einen Vergleich in Octant

    public LeafNode(Octant o, Body b, InnerNode p, int i){
        octant = o;
        body = b;
        parent = p;
        childID = i;
    }

    public Vector3 getMassCenter(){
        return body.getMassCenter();
    }

    public double getMass(){
        return body.getMass();
    }

    public Body getBody(){
        return body;
    }

    public boolean add(Body b){
        if (parent == null){
            return false;
        }
        Body recovery = body;
        parent.setChild(childID,new InnerNode(octant,parent));
        parent.getChild(childID).add(recovery);
        parent.getChild(childID).add(b);
        parent = null;
        return true;
    }
}
