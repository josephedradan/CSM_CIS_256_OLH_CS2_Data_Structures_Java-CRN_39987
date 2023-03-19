/**
 * @author Joseph Edradan
 * <p>
 * This file is a Wrapper container for a Vertex
 */

/*
Notes there are 2 types of Wrappers
Type 1:
    Can be Marked
    Count can be Changed
    No Edge
    changeTime can be changed
    Has arrayListWrapper

Type 2:
    Cannot be Marked
    Count is 0
    Has Edge
    changeTime is 0
    No arrayListWrapper

Both:
    Reference Vertex

Note:
    Vertex index, Wrapper index in an Iterable should be the same
 */

package Standard;

public abstract class Wrapper {

    // Vertex associated with this Wrapper
    protected Vertex vertex;

    public Wrapper(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    @Override
    public String toString() {
        return String.format("Wrapper Vertex Number: %s", vertex.getIndex());
    }

}
