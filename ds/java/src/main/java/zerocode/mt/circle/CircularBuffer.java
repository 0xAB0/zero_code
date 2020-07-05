package zerocode.mt.circle;

public interface CircularBuffer<T> {

    /*
     * Return an instance of a writer
     */
    Writer<T> getWriter();

    /*
     * Returns a new reader
     */
    Reader<T> getReader();

    interface Writer<T> {
        /*
         * Post a value to the circular buffer.
         */
        void post(T value);
    }


    interface Reader<T> {
        /**
         * Return the next value from the circular buffer
         *
         * @Returns T the next value or null if no value.
         * @Throws BufferWrappedException if the producer has wrapped.
         */
        T poll() throws BufferWrappedException;
    }
}
