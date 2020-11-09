package org.global.procamp;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;

public class SimpleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SimpleList<String> array;
    private String[] defaultStringArray = {"elem0", "elem1", "elem2", "elem3", "elem4"};

    @Before
    public void setUp() {
        array = new SimpleList<>();
        for (String obj : defaultStringArray) {
            array.add(obj);
        }
    }

    @After
    public void tearDown() {
        array = null;
    }



    @Test
    public void addTest() {

        Assert.assertThat(array, hasItems("elem0", "elem1", "elem2", "elem3", "elem4"));
        Assert.assertThat(array, not(hasItem("elem5")));
    }

    @Test
    public void capacityTest() {
        for (int i = 5; i < 25; i++) {
            array.add("elem" + i);
        }

        Assert.assertThat(array, hasItems("elem0", "elem1", "elem2", "elem3", "elem4","elem5", "elem10", "elem14", "elem21"));
        Assert.assertEquals(25, array.size());
    }


    @Test
    public void removeIndexTest() {
        Assert.assertThat(array, hasItem("elem0"));
        array.remove(0);
        Assert.assertThat(array, not(hasItem("elem0")));
        Assert.assertEquals(4, array.size());

        Assert.assertThat(array, hasItem("elem4"));
        array.remove(3);
        Assert.assertThat(array, not(hasItem("elem4")));
        Assert.assertEquals(3, array.size());
    }

    @Test
    public void removeObjectTest() {
        Assert.assertThat(array, hasItem("elem0"));
        Assert.assertTrue(array.remove("elem0"));
        Assert.assertThat(array, not(hasItem("elem0")));
        Assert.assertEquals(4, array.size());

        Assert.assertThat(array, hasItem("elem4"));
        Assert.assertTrue(array.remove("elem4"));
        Assert.assertThat(array, not(hasItem("elem4")));
        Assert.assertEquals(3, array.size());

        Assert.assertFalse(array.remove("elem9"));
    }

    @Test
    public void getTest() {
        Assert.assertEquals("elem0", array.get(0));
        Assert.assertEquals("elem1", array.get(1));
        Assert.assertEquals("elem4", array.get(4));
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Index: 7, Size: 5");
        array.get(7);
    }

    @Test
    public void clearTest() {
        Assert.assertEquals(5, array.size());
        Assert.assertThat(array, hasItems(defaultStringArray));
        array.clear();
        Assert.assertEquals(0, array.size());
        Assert.assertThat(array, not(hasItems(defaultStringArray)));
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(5, array.size());
        array.add("elem5");
        Assert.assertEquals(6, array.size());
        array.remove(0);
        Assert.assertEquals(5, array.size());
    }

    @Test
    public void isEmptyTest() {
        Assert.assertFalse(array.isEmpty());
        array.clear();
        Assert.assertTrue(array.isEmpty());
    }

    @Test
    public void isNotEmptyTest() {
        Assert.assertTrue(array.isNotEmpty());
        array.clear();
        Assert.assertFalse(array.isNotEmpty());
    }

    @Test
    public void toArrayTest() {
        Assert.assertTrue(array.toArray() instanceof Object[]);
        Assert.assertThat(array.toArray(), instanceOf(Object[].class));
        Assert.assertTrue(array.toArray().getClass().isArray());
        Assert.assertArrayEquals(defaultStringArray, array.toArray());
    }

    @Test
    public void addAllTest() {
        Assert.assertTrue(array.addAll(Arrays.asList(defaultStringArray)));
        Assert.assertEquals(10, array.size());
        Assert.assertTrue(array.addAll(Arrays.asList("elem7", "elem8", "elem9")));
        Assert.assertEquals(13, array.size());
        Assert.assertThat(array, hasItems("elem9", "elem8", "elem7"));

        SimpleList<String> newArray = new SimpleList<>();
        Assert.assertTrue(newArray.addAll(Arrays.asList(defaultStringArray)));
        Assert.assertThat(array, hasItems(defaultStringArray));
        Assert.assertEquals(5, newArray.size());
    }

    @Test
    public void addAllIndexTest() {
        Assert.assertTrue(array.addAll(2, Arrays.asList(defaultStringArray)));
        Assert.assertEquals(10, array.size());
        Assert.assertEquals("elem1", array.get(1));
        Assert.assertEquals("elem0", array.get(2));
        Assert.assertEquals("elem1", array.get(3));
        Assert.assertEquals("elem4", array.get(6));
        Assert.assertEquals("elem2", array.get(7));
        Assert.assertEquals("elem3", array.get(8));
    }

    @Test
    public void containsTest() {
        Assert.assertTrue(array.contains("elem1"));

        Assert.assertFalse(array.contains(null));
        Assert.assertTrue(array.add(null));
        Assert.assertTrue(array.contains(null));

        Assert.assertFalse(array.contains("elem9"));
        Assert.assertTrue(array.add("elem9"));
        Assert.assertTrue(array.contains("elem9"));
    }

    @Test
    public void containsAllTest() {
        Assert.assertTrue(array.containsAll(Arrays.asList(defaultStringArray)));

        Assert.assertTrue(array.containsAll(Arrays.asList("elem2", "elem3")));
        Assert.assertFalse(array.containsAll(Arrays.asList("elem0", "elem9", "elem1")));
        Assert.assertFalse(array.containsAll(Arrays.asList("elem9", "elem8")));

        Assert.assertTrue(array.add(null));
        Assert.assertTrue(array.containsAll(Arrays.asList("elem4", null)));
    }

    @Test
    public void addIndexTest() {
        array.add(3, "elem9");
        Assert.assertEquals("elem9", array.get(3));
        Assert.assertThat(array, not(hasItem("elem3")));
        Assert.assertThat(array, hasItem("elem9"));
    }

    @Test
    public void setTest() {
        Assert.assertEquals("elem3", array.set(3, "elem9"));
        Assert.assertThat(array, not(hasItem("elem3")));
        Assert.assertThat(array, hasItem("elem9"));
        Assert.assertEquals("elem9", array.get(3));
    }

    @Test
    public void removeAllTest() {
        Assert.assertFalse(array.removeAll(Arrays.asList("elem6", "elem7")));
        Assert.assertEquals(5, array.size());

        Assert.assertTrue(array.removeAll(Arrays.asList(defaultStringArray)));
        Assert.assertEquals(0, array.size());

        Assert.assertTrue(array.addAll(Arrays.asList(defaultStringArray)));
        Assert.assertTrue(array.removeAll(Arrays.asList("elem2", "elem4")));
        Assert.assertEquals(3, array.size());
        Assert.assertThat(array, hasItems("elem0", "elem1", "elem3"));
        Assert.assertThat(array, not(hasItems("elem2", "elem4")));
    }

    @Test
    public void retainAllTest() {
        Assert.assertFalse(array.retainAll(Arrays.asList(defaultStringArray)));
        Assert.assertEquals(5, array.size());
        Assert.assertThat(array, hasItems(defaultStringArray));

        Assert.assertTrue(array.retainAll(Arrays.asList("elem6", "elem7")));
        Assert.assertEquals(0, array.size());

        Assert.assertTrue(array.addAll(Arrays.asList(defaultStringArray)));

        Assert.assertTrue(array.retainAll(Arrays.asList("elem2", "elem4")));
        Assert.assertEquals(2, array.size());
        Assert.assertThat(array, hasItems("elem2", "elem4"));
        Assert.assertThat(array, not(hasItems("elem0", "elem1", "elem3")));
    }

    @Test
    public void indexOfTest() {
        Assert.assertEquals(-1, array.indexOf("elem8"));
        Assert.assertEquals(0, array.indexOf("elem0"));
        Assert.assertEquals(3, array.indexOf("elem3"));
    }

    @Test
    public void lastIndexOfTest() {
        Assert.assertEquals(-1, array.lastIndexOf("elem8"));
        Assert.assertEquals(3, array.lastIndexOf("elem3"));
        array.add("elem3");
        Assert.assertEquals(5, array.lastIndexOf("elem3"));
        array.addAll(Arrays.asList("elem1", "elem5", "elem3"));
        Assert.assertEquals(8, array.lastIndexOf("elem3"));
    }

    @Test
    public void subListTest1() {
        Assert.assertThat(array.subList(2, 4), not(IsEmptyCollection.empty()));
        Assert.assertThat(array.subList(2, 4), not(hasItems("elem0", "elem1", "elem4")));
        Assert.assertThat(array.subList(2, 4), hasItems("elem2", "elem3"));
        Assert.assertThat(array.subList(2, 5), hasItems("elem2", "elem3", "elem4"));
    }


    @Test
    public void subListTest2() {
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("fromIndex = -1");
        array.subList(-1, 4);
    }

    @Test
    public void subListTest3() {
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("toIndex = 6");
        array.subList(2, 6);
    }

    @Test
    public void subListTest4() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("fromIndex(4) > toIndex(2)");
        array.subList(4, 2);
    }

    @Test
    public void iteratorTest1() {
        Iterator<String> iterator = array.iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("elem0", iterator.next());
        Assert.assertEquals("elem1", iterator.next());
        Assert.assertThat(array, hasItem("elem1"));
        iterator.remove();
        Assert.assertThat(array, not(hasItem("elem1")));
        Assert.assertEquals("elem2", iterator.next());
        Assert.assertEquals("elem3", iterator.next());
        Assert.assertEquals("elem4", iterator.next());
        Assert.assertFalse(iterator.hasNext());
        Assert.assertThat(array, hasItem("elem4"));
        iterator.remove();
        Assert.assertThat(array, not(hasItem("elem1")));
        Assert.assertEquals(3, array.size());
    }

    @Test
    public void iteratorTest2() {
        Iterator<String> iterator = array.iterator();

        thrown.expect(IllegalStateException.class);
        iterator.remove();
    }

    @Test
    public void iteratorTest3() {
        Iterator<String> iterator = array.iterator();

        iterator.next();
        iterator.remove();

        thrown.expect(IllegalStateException.class);
        iterator.remove();
    }

    @Test
    public void iteratorTest4() {
        Iterator<String> iterator = array.iterator();

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();

        thrown.expect(NoSuchElementException.class);
        iterator.next();
    }

    @Test
    public void constructorTest() {
        SimpleList<String> consArray = new SimpleList<>(new ArrayList<>(Arrays.asList(defaultStringArray)));

        Assert.assertThat(consArray, hasItems(defaultStringArray));
        Assert.assertThat(consArray.size(), is(5));

        consArray.add("elem7");
        consArray.add("elem8");
        Assert.assertThat(consArray.size(), is(7));

    }
}
