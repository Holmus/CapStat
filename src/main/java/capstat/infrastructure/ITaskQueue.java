package capstat.infrastructure;

import java.util.NoSuchElementException;

/**
 * A queue of Strings, describings tasks to perform, for example an SQL query.
 * This is a fairly specific implementation of a more general concept, tailored
 * to the needs of this project.
 *
 * The queue is ordered on a FIFO ("first in, first out") basis, meaning that
 * the element that has been in the queue the longest is the first to be
 * removed at removal. This element is called the <i>head</i> of the queue. The
 * last element - the most recently added element - is called the <i>tail</i>
 * of the queue.
 *
 * @author Christian Persson
 */
public interface ITaskQueue {

    /**
     * Adds a new String as the tail of this queue.
     */
    public void add(String task);

    /**
     * Retrieves, but does not remove, the head of this queue.
     * @return a String representing the task
     * @throws NoSuchElementException if this queue is empty
     */
    public String peek() throws NoSuchElementException;

    /**
     * Retrieves, and removes, the head of this queue.
     * @return a String representing the task
     * @throws NoSuchElementException if this queue is empty
     */
    public String pop() throws NoSuchElementException;

    /**
     * Clear all elements from this queue.
     */
    public void clear();

    /**
     * Check whether this queue contains any elements.
     * @return true if this queue contains at least one element; false otherwise
     */
    public boolean hasElements();

    /**
     * Check whether this queue contains the given String.
     * @return true if this contains the given String; false otherwise
     */
    public boolean contains(String task);
}
