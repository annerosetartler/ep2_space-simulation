public class Iterator implements java.util.Iterator<Body> {
    TreeNode node;

    public Iterator(TreeNode node){
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Body next() {
        return node.getBody();
    }
}
