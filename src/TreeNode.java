public interface TreeNode {
    boolean add(Body b);

    Body getBody();

    Vector3 calculate(Body body);

    //Methode, die hilft zu wissen, in welchem Octant des Parent wir sind
}
