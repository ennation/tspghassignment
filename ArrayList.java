package cs2321;

import java.util.Arrays;
import java.util.Iterator;

import net.datastructures.List;

public class ArrayList< E > implements List< E > {
    private static final int DEFAULT_CAPACITY = 16;
    private int size = 0;
    private E[] data = null;

    public ArrayList() {
        this( DEFAULT_CAPACITY );
    }

    public ArrayList( int capacity ) {
        data = ( E[] ) ( new Object[ capacity ] );
    }

    /**
     * Getter for size
     *
     * @return the size of the ArrayList
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return ( size == 0 );
    }

    @Override
    public E get( int i ) throws IndexOutOfBoundsException {
        return data[ i ];
    }

    /**
     * Changes the value of an existing element
     *
     * @param i the index of the element to replace
     * @param e the new element to be stored
     *
     * @return the previously-held value
     *
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E set( int i, E e ) throws IndexOutOfBoundsException {
        checkIndex( i, size );
        E temp = data[ i ];
        data[ i ] = e;
        return temp;
    }

    /**
     * Adds a value at the given index of the array
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     *
     * @throws IndexOutOfBoundsException if the passed index is out of bounds
     */
    @Override
    public void add( int i, E e ) throws IndexOutOfBoundsException {

        // Method checkIndex will check if index i is within range [0.
        // .size-1], and throw exception if not
        checkIndex( i, size + 1 );

        // Set element from [i .. size-1] to be the next one on its right.
        // In other words, all the elements starting from index i+1 to index
        // size-1 have to shift to its left.
        for ( int j = size - 1; j >= i; j-- )
            data[ j + 1 ] = data[ j ];

        // Assign new element to index i
        data[ i ] = e;

        // Increment the tracked size
        size++;
    }

    /**
     * Removes and returns the value at the passed index
     *
     * @param i the index of the element to be removed
     *
     * @return the value previously held at index i
     *
     * @throws IndexOutOfBoundsException if the passed index is out of bounds
     */
    @Override
    public E remove( int i ) throws IndexOutOfBoundsException {

        // Method checkIndex will check if index i is within range [0.
        // .size-1], and throw exception if not
        checkIndex( i, size );

        // Save the data at index i before it is overwritten
        E temp = data[ i ];

        // Set element from [i .. size-2] to be the next one on its right.
        // In other words, all the elements starting from index i+1 to index
        // size-1 have to shift to its left.
        for ( int k = i; k <= size - 2; k++ ) {
            data[ k ] = data[ k + 1 ];
        }

        //reduce the tracked size
        size--;

        // Return the previously-assigned value
        return temp;
    }

    /**
     * Returns an iterator for the array
     */
    @Override
    public Iterator< E > iterator() {
        return new ArrayIterator();
    }

    /**
     * Adds a value to the first index, and shifts all the elements up one
     */
    public void addFirst( E e ) {
        // TODO shift elements up
        add( 0, e );
    }

    /**
     * Adds a value to the last current index
     */
    public void addLast( E e ) {
        checkIndex( size, size + 1 );
        data[ size ] = e;
        size++;
    }

    /**
     * Removes and returns the value of the first element. All elements are then
     * shifted down
     *
     * @return the value of the first element
     *
     * @throws IndexOutOfBoundsException if the passed index is out of bounds
     */
    public E removeFirst() throws IndexOutOfBoundsException {
        return remove( 0 );
    }

    /**
     * Removes and returns the value of the last element.
     *
     * @return the value of the last element
     *
     * @throws IndexOutOfBoundsException if the passed index is out of bounds
     */
    public E removeLast() throws IndexOutOfBoundsException {
        E temp = data[ size - 1 ];
        data[ size - 1 ] = null;
        size--;
        return temp;
    }

    // Return the capacity of array, not the number of elements.
    // Notes: The initial capacity is 16. When the array is full, the array
    // should be doubled.

    /**
     * Getter for array.length
     *
     * @return the size of the ArrayList's internal array
     */
    public int capacity() {
        return data.length;
    }

    /**
     * Helper method
     * Checks if the index is in the valid range
     *
     * @param index index to be checked
     * @param max one higher than the index can be at
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    private void checkIndex( int index, int max )
            throws IndexOutOfBoundsException {
        // If current size == data.length, then resize the array to 2x the size
        if ( size == data.length ) {
            data = Arrays.copyOf( data, 2 * data.length );
        }
        if ( index < 0 || index >= max ) {
            throw new IndexOutOfBoundsException( "Index out of bounds at " + index );
        }
    }

    // MyIterator Class
    private class ArrayIterator implements Iterator< E > {
        private int nextIndex = 0;

        /**
         * Goes through the arrayList and sees if there is a next element
         *
         * @return true if i is less than the size of the array. False otherwise
         */
        public boolean hasNext() {
            return nextIndex < size;
        }

        /**
         * Goes through the arrayList and returns the elements
         *
         * @return data and increments i for the next iteration
         */
        public E next() {
            if ( nextIndex == size )
                return null;
            return data[ nextIndex++ ];
        }

    }// END MyIterator class
} // END ArrayList class
