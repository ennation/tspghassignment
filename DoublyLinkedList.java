package cs2321;

import java.util.Iterator;

import net.datastructures.Position;
import net.datastructures.PositionalList;


public class DoublyLinkedList< E > implements PositionalList< E > {
    // Member variables
    private Node< E > head = null; // header sentinel
    private Node< E > tail = null; // trailer sentinel
    private int size = 0; // number of elements in the list

    public DoublyLinkedList() {
        head = new Node<>( null, null, null );
        tail = new Node<>( null, head, null );
        head.setNext( tail );
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return ( size == 0 );
    }

    @Override
    public Position< E > first() {
        return position( head.getNext() );
    }

    @Override
    public Position< E > last() {
        return position( tail.getPrev() );
    }

    @Override
    public Position< E > before( Position< E > p )
            throws IllegalArgumentException {
        Node< E > node = validate( p );
        return position( node.getPrev() );
    }

    @Override
    public Position< E > after( Position< E > p )
            throws IllegalArgumentException {
        Node< E > node = validate( p );
        return position( node.getNext() );
    }

    @Override
    public Position< E > addFirst( E e ) {
        return addBetween( e, head, head.getNext() );
    }

    @Override
    public Position< E > addLast( E e ) {
        return addBetween( e, tail.getPrev(), tail );
    }

    @Override
    public Position< E > addBefore( Position< E > p, E e )
            throws IllegalArgumentException {
        Node< E > node = validate( p );
        return addBetween( e, node.getPrev(), node );
    }

    @Override
    public Position< E > addAfter( Position< E > p, E e )
            throws IllegalArgumentException {
        Node< E > node = validate( p );
        return addBetween( e, node, node.getNext() );
    }

    @Override
    public E set( Position< E > p, E e ) throws IllegalArgumentException {
        Node< E > node = validate( p );
        E answer = node.getElement();
        node.setElement( e );
        return answer;
    }

    public E get( Position< E > p ) throws IllegalArgumentException {
        Node< E > node = validate( p );
        return node.getElement();
    }

    @Override
    public E remove( Position< E > p ) throws IllegalArgumentException {
        Node< E > node = validate( p );
        Node< E > predecessor = node.getPrev();
        Node< E > successor = node.getNext();
        predecessor.setNext( successor );
        successor.setPrev( predecessor );
        size--;
        E answer = node.getElement();
        node.setElement( null ); // help with garbage collection
        node.setNext( null ); // and convention for defunct node
        node.setPrev( null );
        return answer;
    }

    @Override
    public Iterator< E > iterator() {
        return new ElementIterator();
    }

    @Override
    public Iterable< Position< E > > positions() {
        return new PositionIterable();
    }

    public E removeFirst() throws IllegalArgumentException {
        if ( isEmpty() )
            throw new IllegalArgumentException( "Nothing to remove" );
        return remove( first() );
    }

    public E removeLast() throws IllegalArgumentException {
        if ( isEmpty() )
            throw new IllegalArgumentException( "Nothing to remove" );
        return remove( last() );
    }

    // Private helper methods

    /**
     * Private helper method
     * Validates the position and returns it as a node.
     *
     * @param p The position to be validated
     *
     * @return the Node at the given position
     */
    private Node< E > validate( Position< E > p )
            throws IllegalArgumentException {
        if ( !( p instanceof Node ) )
            throw new IllegalArgumentException( "Invalid p" );
        Node< E > node = ( Node< E > ) p; // safe cast
        if ( node.next == null ) // convention for defunct node
            throw new IllegalArgumentException( "p is no longer in the list" );
        return node;
    }

    /**
     * Private helper method
     * Returns the given node as a Position (or null, if it is a sentinel).
     *
     * @param node The node to get the position of
     *
     * @return the position of the given node
     */
    private Position< E > position( Node< E > node ) {
        if ( node == head || node == tail )
            return null; // do not expose user to the sentinels
        return node;
    }

    /**
     * Private helper method
     * Adds element e to the linked list between the given nodes.
     *
     * @param e the element to add
     * @param pred the preceding Node
     * @param succ the succeeding Node
     */
    private Position< E > addBetween( E e, Node< E > pred, Node< E > succ ) {
        // create and link a new node
        Node< E > newest = new Node<>( e, pred, succ );

        pred.setNext( newest );
        succ.setPrev( newest );
        size++;
        return newest;
    }

    // Nested Node class
    private static class Node< E > implements Position< E > {
        private E element; // The element stored in the Node
        private Node< E > prev; // The previous Node in the doubly-linked list
        private Node< E > next; // The next Node in the doubly-linked list

        // No-arg Constructor
        public Node() {

        }

        /**
         * Constructor
         *
         * @param element the element to store in this Node
         * @param prev the previous Node in the doubly-linked list
         * @param next the next Node in the doubly-linked list
         */
        public Node( E element, Node< E > prev, Node< E > next ) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        /**
         * Returns the element stored at this position.
         *
         * @return the found element
         *
         * @throws IllegalStateException if position no longer valid
         */
        public E getElement() throws IllegalStateException {
            if ( next == null )
                throw new IllegalStateException( "Position invalid" );
            return element;
        }

        public Node< E > getPrev() {
            return prev;
        }

        public Node< E > getNext() {
            return next;
        }

        public void setElement( E element ) {
            this.element = element;
        }

        public void setPrev( Node< E > prev ) {
            this.prev = prev;
        }

        public void setNext( Node< E > next ) {
            this.next = next;
        }
    } // END Node class

    // Nested PositionIterator class
    private class PositionIterator implements Iterator< Position< E > > {
        // Instance variables

        // position of the next element to report
        private Position< E > cursor = first();

        private Position< E > recent = null; // position of last reported
        // element

        /**
         * Tests whether the iterator has a next object
         *
         * @return false if the cursor is equal to null, else return true
         */
        public boolean hasNext() {
            return ( cursor != null );
        }

        /**
         * Returns the next position in the iterator
         *
         * @return the next position in the iterator
         */
        public Position< E > next() {
            if ( cursor == null )
                return null;
            recent = cursor;
            cursor = after( cursor );
            return recent;
        }

    }// END PositionIterator class

    // Nested ElementIterator class
    private class ElementIterator
            implements Iterator< E > {
        Iterator< Position< E > > posIterator = new PositionIterator();

        public boolean hasNext() { return posIterator.hasNext(); }

        public E next() {
            return posIterator.next().getElement();
        } // return element! public void remove( ) { posIterator.remove( ); }
    }

    // Nested PositionIterable class
    private class PositionIterable implements Iterable< Position< E > > {
        public Iterator< Position< E > > iterator() { return new PositionIterator(); }
    } // END PositionIterable class
}
