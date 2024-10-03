package cs2321;

import net.datastructures.Stack;

public class DLLStack< E > implements Stack< E > {
    // Member variables
    DoublyLinkedList< E > dll = new DoublyLinkedList<>();

    @Override
    public int size() {
        return dll.size();
    }

    @Override
    public boolean isEmpty() {
        return dll.isEmpty();
    }

    @Override
    public void push( E e ) {
        // TODO make sure head of the list is where I wanna be coming from
        dll.addFirst( e );
    }

    @Override
    public E top() {
        // TODO make sure head of the list is where I wanna be coming from
        if ( this.isEmpty() )
            return null;
        else
            return dll.get( dll.first() );
    }

    @Override
    public E pop() {
        // TODO make sure head of the list is where I wanna be coming from
        if ( this.isEmpty() )
            return null;
        else
            return dll.removeFirst();
    }

}
