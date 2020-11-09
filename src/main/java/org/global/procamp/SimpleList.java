package org.global.procamp;

import java.util.*;

public class SimpleList<T> implements List<T> {

    private final static int DEFAULT_CAPACITY = 10;
    private final static Object[] EMPTY_ARRAY = {};

    private int size = 0;
    private int capacity = 0;
    private Object[] elementArray = {};


    public SimpleList() {

    }

    public SimpleList(Collection<? extends T> collection) {
        if (collection != null) {
            Object[] array = collection.toArray();
            if (array.length != 0) {
                size = array.length;
                capacity = array.length;
                elementArray = Arrays.copyOf(array, size, Object[].class);
            }
        }
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isNotEmpty() {
        return size != 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementArray[i] == null)
                    return true;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementArray[i]))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementArray, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        initIfNeed();
        ensureCapacity();

        elementArray[size++] = t;
        return true;
    }

    @Override
    public T remove(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException();

        Object removeElement = elementArray[index];
        int numberMoveElements = size - index - 1;

        if (numberMoveElements > 0)
            System.arraycopy(elementArray, index + 1, elementArray, index, numberMoveElements);

        elementArray[--size] = null;
        return (T) removeElement;
    }

    @Override
    public boolean remove(Object o) {
        if (o != null) {
            for (int index = 0; index < size; index++)
                if (o.equals(elementArray[index])) {
                    remove(index);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        final Object[] addingArray = c.toArray();
        int addingSize = addingArray.length;
        if (addingSize == 0)
            return false;
        ensureCapacity(addingArray.length + size);
        System.arraycopy(addingArray, 0, elementArray, size, addingSize);
        size += addingSize;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        final Object[] addingArray = c.toArray();
        int addingSize = addingArray.length;
        if (addingSize == 0)
            return false;
        ensureCapacity(addingArray.length + size);

        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(elementArray, index, elementArray, index + addingSize, numMoved);

        System.arraycopy(addingArray, 0, elementArray, index, addingSize);
        size += addingSize;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null || c.isEmpty())
            return false;
        int w = 0;
        for (int r = 0; r < size; r++) {
            if (!c.contains(elementArray[r]))
                elementArray[w++] = elementArray[r];
        }
        for (int i = w; i < size; i++)
            elementArray[i] = null;
        boolean isRemoved = w != size;
        size = w;
        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null || c.isEmpty())
            return false;
        int w = 0;
        for (int r = 0; r < size; r++) {
            if (c.contains(elementArray[r]))
                elementArray[w++] = elementArray[r];
        }
        for (int i = w; i < size; i++)
            elementArray[i] = null;
        boolean isRemoved = w != size;
        size = w;
        return isRemoved;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementArray[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elementArray[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T oldValue = (T) elementArray[index];
        elementArray[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elementArray[index] = element;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementArray[i] == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementArray[i]))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementArray[i] == null)
                    return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementArray[i]))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +") > toIndex(" + toIndex + ")");

        SimpleList<T> subList = new SimpleList<>();
        subList.elementArray = Arrays.copyOfRange(elementArray, fromIndex, toIndex);
        subList.size = subList.elementArray.length;
        subList.capacity = subList.size;
        return subList;
    }

    private void initIfNeed() {
        if (elementArray == EMPTY_ARRAY && capacity == 0) {
            elementArray = new Object[capacity = DEFAULT_CAPACITY];
        }
    }

    private void ensureCapacity() {
        if (capacity == size) {
            elementArray = Arrays.copyOf(elementArray, capacity = capacity + DEFAULT_CAPACITY);
        }
    }

    private void ensureCapacity(int newSize) {
        if (capacity <= newSize) {
            elementArray = Arrays.copyOf(elementArray, capacity = newSize + DEFAULT_CAPACITY);
        }
    }


    private class SimpleIterator implements Iterator<T> {

        int cursor = 0;
        int returned = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            if (cursor >= size)
                throw new NoSuchElementException();
            Object[] elementData = SimpleList.this.elementArray;
            if (cursor >= elementData.length)
                throw new ConcurrentModificationException();
            return (T) elementData[returned = cursor++];
        }

        @Override
        public void remove() {
            if (returned < 0)
                throw new IllegalStateException();

            try {
                SimpleList.this.remove(returned);

                cursor = returned;
                returned = -1;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

