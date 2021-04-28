public class LeaveNode implements TreeNode {

    private Body body;
    private InnerNode parent;
    private Octant octant;

    public boolean add(Body b){
        if (parent == null){
            return false;
        }
        Body recovery = body;
        parent.octant() = new InnerNode();
        parent.add(recovery);
        parent.add(b);
        return true;
    }

    public Vector3 getCenter(){
        return body.getMassCenter();
    }
}
