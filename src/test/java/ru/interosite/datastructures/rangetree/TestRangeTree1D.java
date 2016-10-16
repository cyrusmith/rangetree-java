package ru.interosite.datastructures.rangetree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.interosite.datastructures.rangetree.RangeTree1D.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by cyrusmith@.
 */
@RunWith(JUnit4.class) public class TestRangeTree1D {

    @Test public void shouldCreateNoNode() {
        assertNull(RangeTree1D.create(new ArrayList<>()));
    }

    @Test public void shouldCreateOneNode() {
        TreeNode node = RangeTree1D.create(Arrays.asList(1));
        assertEquals(new TreeNode(1), node);
    }

    @Test public void shouldCreateTwoNodes() {
        TreeNode node = RangeTree1D.create(Arrays.asList(1, 5));
        assertEquals(new TreeNode(1, new TreeNode(1), new TreeNode(5)), node);
    }

    @Test public void shouldCreateThreeNodes() {
        TreeNode node = RangeTree1D.create(Arrays.asList(1, 5, 7));
        assertEquals(
            new TreeNode(5, new TreeNode(1, new TreeNode(1), new TreeNode(5)), new TreeNode(7)),
            node);
    }

    @Test public void shouldQueryRangeOfSingleElement() {
        TreeNode root = RangeTree1D
            .create(Arrays.asList(1));

        List<Integer> result = RangeTree1D.rangeQuery(root, 0, 2);
        assertEquals(Arrays.asList(1), result);
    }

    @Test public void shouldQueryRangeOfThreeElements() {
        TreeNode root = RangeTree1D
            .create(Arrays.asList(1, 3, 5));

        assertEquals(Arrays.asList(1, 3, 5), RangeTree1D.rangeQuery(root, 0, 6));
        assertEquals(Arrays.asList(1, 3, 5), RangeTree1D.rangeQuery(root, 1, 5));
        assertEquals(Arrays.asList(3), RangeTree1D.rangeQuery(root, 2, 4));
        assertEquals(Arrays.asList(3, 5), RangeTree1D.rangeQuery(root, 2, 5));
        assertEquals(Arrays.asList(1, 3), RangeTree1D.rangeQuery(root, 1, 4));
    }

    @Test public void shouldQueryRangeOfFourElements() {
        TreeNode root = RangeTree1D
            .create(Arrays.asList(1, 2, 3, 4));

        List<Integer> result = RangeTree1D.rangeQuery(root, 3, 4);
        assertEquals(Arrays.asList(3, 4), result);
    }

    @Test public void shouldQueryRangeFromRoot() {
        TreeNode root = RangeTree1D
            .create(Arrays.asList(1, 3, 4, 7, 9, 12, 14, 15, 17, 20, 22, 24, 25, 27, 29, 31));

        List<Integer> result = RangeTree1D.rangeQuery(root, 2, 23);
        assertEquals(Arrays.asList(3, 4, 7, 9, 12, 14, 15, 17, 20, 22), result);
    }

    @Test public void shouldQueryRangeOnTheLeft() {
        TreeNode root = RangeTree1D
            .create(Arrays.asList(1, 3, 4, 7, 9, 12, 14, 15, 17, 20, 22, 24, 25, 27, 29, 31));

        List<Integer> result = RangeTree1D.rangeQuery(root, 1, 5);
        assertEquals(Arrays.asList(1, 3, 4), result);
    }

    @Test public void shouldQueryRangeOnTheRight() {
        TreeNode root = RangeTree1D
            .create(Arrays.asList(1, 3, 4, 7, 9, 12, 14, 15, 17, 20, 22, 24, 25, 27, 29, 31));

        List<Integer> result = RangeTree1D.rangeQuery(root, 23, 27);
        assertEquals(Arrays.asList(24, 25, 27), result);
    }

}
