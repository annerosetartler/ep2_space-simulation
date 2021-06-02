// guarantees the existence of an iterator in all classes that implement this interface (for dynamic binding)
public interface BodyIterable extends Iterable<Body> {

    // Returns an iterator over elements of type 'Body'.
    BodyIterator iterator();

}