public class Octree {

    private TreeNode root;

    //adds a body to the octree
    public boolean add(Body b){
        /*if(!octant.contains(b)){
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
         */


        if(!octant.contains(b)){
            return true;
        }
        if(root == null){
            root = new LeaveNode(b,octant);
            return true;
        }
        boolean bool = root.add(b);
        if (!bool){
            Body recovery = root.getBody();
            root = new InnerNode();
            root.add(recovery);

        }
        root.add(b);
        return true;
    }

}
