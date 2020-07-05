package zerocode.mt.circle;

import java.lang.reflect.Array;

public final class MTCircularBuffer<T> implements CircularBuffer<T> {

    private final Object[] buffer;

    private final long capacity;
    private volatile long writePointer;

    private final MTBufferWriter<T> writer;

    public MTCircularBuffer(Class<T> type, int capacity) {
        // Need to align this to a power of two.

        this.capacity = upper_power_of_two(capacity);
        buffer = new Object[upper_power_of_two(capacity)];

        writer = new MTBufferWriter<>();
    }

    /**
     * This implementation is optimised for a single writer and multiple readers
     *  if you call this twice, you will get the same writer back.
     *
     * @return Writer of type T
     */
    @Override
    public Writer<T> getWriter() {
        return writer;
    }

    /**
     * Returns a new reader on each invocation, Each invocation returns a new reader
     *  initialised at the current write pointer.
     * @return Reader of type T
     */

    @Override
    public Reader<T> getReader() {
        return new MTBufferReader<>();
    }

    private class MTBufferWriter<T> implements Writer<T> {

        @Override
        public void post(T value) {
            final int idx = (int)(writePointer&capacity);
            buffer[idx] = value;
            writePointer++;
        }
    }

    private class MTBufferReader<T> implements Reader<T> {
        // Always init at writePointer
        private long readPtr = writePointer;

        @Override
        public T poll() throws BufferWrappedException {
            if(readPtr == writePointer) {
                return null;
            }

            // If the gap is > than capacity we've wrapped.
            if( writePointer - readPtr >= capacity) {
                throw new BufferWrappedException();
            }

            final int arrayIdx = (int)(readPtr&capacity);
            final T value = (T)buffer[arrayIdx];
            readPtr++;

            return value;
        }
    }


    /*unsigned long upper_power_of_two(unsigned long v)
{
    v--;
    v |= v >> 1;
    v |= v >> 2;
    v |= v >> 4;
    v |= v >> 8;
    v |= v >> 16;
    v++;
    return v;

}*/
    private static int upper_power_of_two(int in) {
        in--;
        in|=in>>1;
        in|=in>>2;
        in|=in>>4;
        in|=in>>8;
        in|=in>>16;
        in++;
        return in;
    }
}
