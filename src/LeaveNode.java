public class LeaveNode implements TreeNode {

    private Body body;
    private TreeNode parent;
    private Octant octant;

    public void add(Body b){
        parent.octant = new InnerNode();
    }

    public Vector3 getCenter(){
        return body.getMassCenter();
    }
}
