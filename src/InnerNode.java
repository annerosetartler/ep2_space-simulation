public class InnerNode implements TreeNode {

    private TreeNode[] child = new TreeNode[8];//nwTop, noTop, soTop, swTop, nwBottom, noBottom, soBottom, swBottom;
    private Octant octant;
    private InnerNode parent;
    private double mass;
    private Vector3 MassCenter;
    private double length;

    public InnerNode(Octant c, InnerNode p) {
        octant = c;
        parent = p;
        length = c.getLength();
        mass = 0.0;
        MassCenter = new Vector3();
    }

    public InnerNode(Octant c, InnerNode p, double m, Vector3 v) {
        octant = c;
        parent = p;
        length = c.getLength();
        mass = m;
        MassCenter = v;
    }

    public Vector3 getMassCenter() {
        return MassCenter;
    }

    public double getMass() {
        return mass;
    }

    public double getLength() {
        return octant.getLength();
    }

    public Body getBody() {
        return null;
    }

    //calculates force excerted on body by this InnerNode
    @Override
    public Vector3 calculate(Body body) {

        if ((MassCenter.distanceTo(body.getMassCenter())) / length > (Simulation.T == 0 ? 0 : 1 / Simulation.T)) {
            return body.gravitationalForce(MassCenter, mass);
        }

        Vector3 calculateVector = new Vector3();

        for (int i = 0; i < child.length; i++) {
            if (child[i] != null) {
                calculateVector = calculateVector.plus(child[i].calculate(body));
            }
        }

        return calculateVector;
    }

    public InnerNode parent() {
        return parent;
    }

    //sets child at index i
    public void setChild(int i, InnerNode innerN) {
        child[i] = innerN;
    }

    //returns child at index i
    public TreeNode getChild(int i) {
        return child[i];
    }

    //adds body b to this InnerNode (or one of its children)
    public boolean add(Body b) {
        if (b == null) {
            return false;
        }
        Octant[] oct = octant.childOctants();
        Vector3 position = b.getMassCenter();
        double m = b.getMass();
        MassCenter = MassCenter.times(mass);
        mass += m;
        MassCenter = (MassCenter.plus(position.times(m))).times(1 / mass);

        boolean flag = true;
        for (int i = 0; i < oct.length && flag; i++) {
            if (oct[i].contains(position)) {
                if (child[i] == null) {
                    child[i] = new LeafNode(oct[i], b, this, i);
                    return true;
                } else {
                    child[i].add(b);
                    flag = false;
                }
            }
        }
        return true;
    }

    // Returns a readable representation of the information of this InnerNode's Octant:
    //Octant: xmin, xmax, ymin, ymax, zmin, zmax
    public String toString(){
        return octant.toString();
    }


    @Override
    public BodyIterator iterator() {
        return new InnerIterator(this);
    }

    private class InnerIterator implements BodyIterator{
        private InnerNode node;
        private BodyIterator[] iter;
        private int currentNodeIndex = 0;

        public InnerIterator(InnerNode t) {
            node = t;
            iter = new BodyIterator[8];
            for (int i = 0; i < 8; i++) {
                TreeNode child = node.getChild(i);
                if (child != null) {
                    iter[i] = child.iterator();
                }
            }
            if (iter[currentNodeIndex] == null){
                setNextNodeIndex();
            }
        }

        public void setNextNodeIndex(){
            while (currentNodeIndex < 8 && iter[currentNodeIndex] == null){
                currentNodeIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            return currentNodeIndex < 8;
        }

        public Body next(){
            Body body = iter[currentNodeIndex].next();
            if (!iter[currentNodeIndex].hasNext()){
                currentNodeIndex++;
                setNextNodeIndex();
            }
            return body;
        }
    }
}
