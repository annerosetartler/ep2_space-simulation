public class LeafNode implements TreeNode {

    private Body body;
    private InnerNode parent;
    private Octant octant;
    private int childID;//to know which child this leaf is to its parent, ersparen uns so noch einen Vergleich in Octant
    private double length;

    public LeafNode(Octant o, Body b, InnerNode p, int i){
        octant = o;
        body = b;
        parent = p;
        childID = i;
        length = o.getLength();
    }

    public Vector3 getMassCenter(){
        return body.getMassCenter();
    }

    public double getMass(){
        return body.getMass();
    }

    //returns this body
    public Body getBody(){
        return body;
    }

    //calculates and returns the gravitational force on body excerted by this.body
    @Override
    public Vector3 calculate(Body body) {
        return body.gravitationalForce(this.body);
    }

    //adds body b by storing the body in this leave node, then adding a inner node at the place of this LeafNode and
    //subsiquently adding the saved body and body b into the tree again
    public boolean add(Body b){
        if (parent == null){
            return false;
        }
        Body recovery = body;
        parent.setChild(childID,new InnerNode(octant,parent));
        parent.getChild(childID).add(recovery);
        parent.getChild(childID).add(b);
        return true;
    }

    //returns the name of this Body
    public String toString(){
        return body.toString();
    }

    @Override
    public BodyIterator iterator() {
        return new LeafIterator(body);
    }

    private class LeafIterator implements BodyIterator{
        private Body b;
        private boolean flag;

        public LeafIterator(Body body) {
            b = body;
            flag = true;
        }

        @Override
        public boolean hasNext() {
            return flag;
        }

        public Body next(){
            if(flag){
                flag = false;
                return b;
            }else{
                return null;
            }
        }
    }
}
