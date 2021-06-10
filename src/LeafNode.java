// represents a node without children and containing a body
public class LeafNode implements TreeNode {

    private Body body;
    private InnerNode parent;
    private Octant octant;
    private int childID;//to know which child this leaf is to its parent

    public LeafNode(Octant o, Body b, InnerNode p, int i){
        octant = o;
        body = b;
        parent = p;
        childID = i;
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

    @Override
    public int getDepth() {
        return 1;
    }

    //adds body b by storing the body in this leaf node, then adding a inner node at the place of this LeafNode and
    //subsequently adding the saved body and body b into the tree again
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

    //returns a BodyIterator of the dynamic type LeafIterator
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

        // returns true if next() has not been used yet
        @Override
        public boolean hasNext() {
            return flag;
        }

        // returns the current body and closes iterator by setting flag = false
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
