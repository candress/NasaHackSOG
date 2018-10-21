package Game;

/**
 * Code adapted from the book. This class is the backbone of the entire program.  It initializes a 1D array of integers
 * which act as disjoint sets (every index being initialized at -1. Then it establishes a parent-child relationship
 * between indices and their values to other indices, acting as pointers to a parent node.  This builds up a data
 * structure which resembles a forest, and combines certain sets.
 *
 * @author Cameron Andress
 * @version 1.0 (09/02/2018)
 */
class DisJSets {

    private int [ ] s; // array to store the pointers/sizes

    /**
     * This constructor simply accepts a size parameter and initializes the array with -1 values
     *
     * @param numElements   integer representing the size of the array (number of disjoint sets)
     */
    DisJSets(int numElements){
        s = new int [numElements];
        for( int i = 0; i < s.length; i++){
            s[i] = -1;
        }
    }

    /**
     * Union two disjoint sets. For simplicity, we assume root1 and root2 are distinct and represent set names.
     * Code has been modified from book to be union-by-size, not by height.
     * This method will update the pointer of a certain root depending on its size.  The larger set will
     * devour the smaller set while adding its size.
     *
     * @param root1 the root of set 1.
     * @param root2 the root of set 2.
     */
    void union( int root1, int root2 ){
        if( s[ root2 ] < s[ root1 ] ) { // root2 is deeper.  Root 2 takes their combined size
            s[root2] = s[root2] + s[root1];
            s[root1] = root2; // Make root2 new root
        }else{ // if size is same or root 1 deeper, merge root 2 into root 1 and root 1 takes their combined their size
            s[root1] = s[root1] + s[root2];
            s[root2] = root1; // Make root1 new root
            }
    }

    /**
     * Perform a find. Error checks omitted for simplicity.
     * This method will recursively search for the root (as denoted by a negative value).  When
     * it finds that root, it will return it and set all subsequent pointers to the root as
     * it unwraps the recursion.
     *
     * @param x the element being searched for.
     * @return the set containing x.
     */
    int find( int x ){
        if( s[ x ] < 0 )
            return x; // returns the value x once the value inside "s[]" at x (x's size) is < 0
        else
            return s[x] = find(s[x]); // first sets "s[]" at x to be the result of find(s[x]), then returns the root
    }

    /**
     * This method simply returns the size of the provided set.
     *
     * @param root  the root (or array location) to provide the size for
     * @return      an integer value for the size of the root
     */
    public int getSetSize(int root) {
        return s[root];
    }

}
