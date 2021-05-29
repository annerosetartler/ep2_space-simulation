public interface TreeNode extends BodyIterable{

    // Adds a body in the Octree
    boolean add(Body b);

    // Returns the body of this TreeNode
    Body getBody();

    // Calculates forces excerted on body
    Vector3 calculate(Body body);

}
