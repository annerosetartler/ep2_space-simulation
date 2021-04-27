public class Octree {

    private TreeNode root;

    //adds a body to the octree
    public void add(Body b){
        if(!octant.contains(b)){
            return;
        }
        if(root == null){
            root = new LeaveNode(b,octant);
        }else if(root.getClass() == LeaveNode.class){
            Body temp = root.getBody();
            root = new InnerNode(); //Gewicht plus Schwerpunkt von b und temp
            root.add(temp);
            root.add(b);
        }else{
            root.add(b);
        }
    }

}
