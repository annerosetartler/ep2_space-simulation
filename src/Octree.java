public class Octree implements BodyIterable{

    private String name;
    private TreeNode root;
    private Octant octant;

    public Octree(String n,double xmin, double xmax, double ymin, double ymax, double zmin, double zmax){
        name = n;
        octant = new Octant(xmin,xmax,ymin,ymax,zmin,zmax);
    }

    // adds a body to the octree if the position is still available
    // otherwise false is returned
    public boolean add(Body b){
        if(!octant.contains(b.getMassCenter())){
            return false;
        }
        if(root == null){
            root = new LeafNode(octant,b,null,-1);
            return true;
        }
        boolean bool = root.add(b);
        if (!bool){
            Body recovery = root.getBody();
            root = new InnerNode(octant,null);
            root.add(recovery);
            root.add(b);
        }
        return true;
    }

    // Calculates all forces on body and subsequently sets the force in body
    public void calculate(Body body){
        if (root == null || body == null){
            return;
        }
        body.setForce(root.calculate(body));
    }

    public int getDepth(){
        if(root == null){
            return 0;
        }else{
            return root.getDepth();
        }
    }

    // generates an iterator for iterating over the leaves of the octree
    @Override
    public BodyIterator iterator() {
        if(root == null){
            return null;
        }else{
           return root.iterator();
        }
    }
}
