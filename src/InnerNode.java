public class InnerNode implements TreeNode {

    private TreeNode[] child = new TreeNode[8];//nwTop, noTop, soTop, swTop, nwBottom, noBottom, soBottom, swBottom;
    private Octant octant;
    private InnerNode parent;
    private double mass;
    private Vector3 MassCenter;
    private double length;

    public InnerNode(Octant c, InnerNode p){
        octant = c;
        parent = p;
        length = c.getLength();
        mass = 0.0;
        MassCenter = new Vector3();
    }

    public InnerNode(Octant c, InnerNode p,double m,Vector3 v){
        octant = c;
        parent = p;
        length = c.getLength();
        mass = m;
        MassCenter = v;
    }

    public Vector3 getMassCenter(){
        return MassCenter;
    }

    public double getMass(){
        return mass;
    }

    public double getLength(){
        return octant.getLength();
    }

    public Body getBody(){
        return null;
    }

    @Override
    public Vector3 calculate(Body body) {

        if ((MassCenter.distanceTo(body.getMassCenter()))/length > (Simulation.T == 0 ? 0 : 1/Simulation.T)) {
            //entweder gib body.gravitationalForce(this inner node);
            return body.gravitationalForce(MassCenter, mass); //oder plus?
        }

        Vector3 calculateVector = new Vector3();
            //oder gib jeweils body.... für jedes kind (mit + berechnen)

        for (int i = 0; i < child.length; i++) {
            if (child[i] != null) {
                calculateVector = calculateVector.plus(child[i].calculate(body));
            }
        }

        return calculateVector;
    }

    public InnerNode parent(){
        return parent;
    }

    public void setChild(int i,InnerNode innerN){
        child[i] = innerN;
    }

    public TreeNode getChild(int i){
        return child[i];
    }

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
        /*
        if(oct[0].contains(position)){
            if(child[0] == null){
                child[0] = new LeafNode(oct[0],b,this,0);
                return true;
            }else{
                child[0].add(b);
            }
        }else if(oct[1].contains(position)){
            if(child[1] == null){
                child[1] = new LeafNode(oct[1],b,this,1);
                return true;
            }else{
                child[1].add(b);
            }
        }else if(oct[2].contains(position)){
            if(child[2] == null){
                child[2] = new LeafNode(oct[2],b,this,2);
                return true;
            }else{
                child[2].add(b);
            }
        }else if(oct[3].contains(position)){
            if(child[3] == null){
                child[3] = new LeafNode(oct[3],b,this,3);
                return true;
            }else{
                child[3].add(b);
            }
        }else if(oct[4].contains(position)){
            if(child[4] == null){
                child[4] = new LeafNode(oct[4],b,this,4);
                return true;
            }else{
                child[4].add(b);
            }
        }else if(oct[5].contains(position)){
            if(child[5] == null){
                child[5] = new LeafNode(oct[5],b,this,5);
                return true;
            }else{
                child[5].add(b);
            }
        }else if(oct[6].contains(position)){
            if(child[6] == null){
                child[6] = new LeafNode(oct[6],b,this,6);
                return true;
            }else{
                child[6].add(b);
            }
        }else if(oct[7].contains(position)){
            if(child[7] == null){
                child[7] = new LeafNode(oct[7],b,this,7);
                return true;
            }else{
                child[7].add(b);
            }
        }else{
            return true;
        }
        return true;
    }
*/
    public String toString(){
        return octant.toString();
    }



}
