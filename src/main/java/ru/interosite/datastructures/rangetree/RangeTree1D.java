package ru.interosite.datastructures.rangetree;

import com.google.common.base.Preconditions;
import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * Created by cyrusmith@.
 */
public class RangeTree1D {

    public static class TreeNode {
        private int rank;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int rank) {
            this(rank, null, null);
        }

        public TreeNode(int rank, TreeNode left, TreeNode right) {
            Preconditions
                .checkState((left == null && right == null) || (left != null && right != null));
            this.rank = rank;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof TreeNode) {
                TreeNode that = (TreeNode) obj;
                return rank == that.rank && Objects.equals(left, that.left) && Objects
                    .equals(right, that.right);
            }
            return super.equals(obj);
        }

        @Override public int hashCode() {
            return Objects.hash(rank, left, right);
        }

        @Override public String toString() {
            return "r:" + rank + ", l: " + Objects.toString(left) + ", r:" + Objects
                .toString(right);
        }
    }

    public static TreeNode create(List<Integer> items) {
        if (items.size() == 0) {
            return null;
        }
        List<Queue<TreeNode>> queues = new ArrayList<>();
        queues.add(new ArrayDeque<>());
        queues.add(new ArrayDeque<>());
        int level = (int) Math.ceil(Math.log(items.size()) / Math.log(2) + 1);
        Queue<TreeNode> queue = queues.get(level % 2);
        TreeNode root = null;
        for (Integer item : items) {
            queue.add(root = new TreeNode(item));
        }
        while (level > 0) {
            Queue<TreeNode> queue1 = queues.get(level % 2);
            Queue<TreeNode> queue2 = queues.get((level + 1) % 2);
            while (queue1.size() >= 2) {
                TreeNode left = queue1.poll();
                TreeNode right = queue1.poll();
                TreeNode nextRight = left;
                int rank = 0;
                while (nextRight != null) {
                    rank = nextRight.rank;
                    nextRight = nextRight.right;
                }
                queue2.add(root = new TreeNode(rank, left, right));
            }
            if (!queue1.isEmpty()) {
                queue2.add(queue1.poll());
            }
            Preconditions.checkState(queue1.isEmpty());
            level--;
        }
        return root;
    }

    public static List<Integer> rangeQuery(TreeNode root, int start, int end) {
        List<TreeNode> pathStart = new ArrayList<>();
        List<TreeNode> pathEnd = new ArrayList<>();
        TreeNode next = root;
        while (next != null) {
            pathStart.add(next);
            if (start > next.rank) {
                next = next.right;
            } else {
                next = next.left;
            }
        }

        next = root;
        while (next != null) {
            pathEnd.add(next);
            if (end > next.rank) {
                next = next.right;
            } else {
                next = next.left;
            }
        }

        int common = 0;
        // Looking for common node.
        while (pathStart.get(common) == pathEnd.get(common)) {
            common++;
        }
        common--;

        List<Integer> result = new ArrayList<>();

        TreeNode prev = pathStart.remove(pathStart.size() - 1);
        if (prev.rank >= start) {
            result.add(prev.rank);
        }
        while (pathStart.size() - common - 1 > 0) {
            next = pathStart.remove(pathStart.size() - 1);
            if (next.left == prev) {
                Queue<TreeNode> rangeQueue = new ArrayDeque<>();
                rangeQueue.add(next.right);
                while (!rangeQueue.isEmpty()) {
                    TreeNode node = rangeQueue.poll();
                    if (node.isLeaf()) {
                        result.add(node.rank);
                    } else {
                        rangeQueue.add(node.left);
                        rangeQueue.add(node.right);
                    }
                }
            }
            prev = next;
        }

        prev = pathEnd.remove(pathEnd.size() - 1);
        Integer lastElement = prev.rank <= end ? prev.rank : null;
        while (pathEnd.size() - common - 1 > 0) {
            next = pathEnd.remove(pathEnd.size() - 1);
            if (next.right == prev) {
                Queue<TreeNode> rangeQueue = new ArrayDeque<>();
                rangeQueue.add(next.left);
                while (!rangeQueue.isEmpty()) {
                    TreeNode node = rangeQueue.poll();
                    if (node.isLeaf()) {
                        result.add(node.rank);
                    } else {
                        rangeQueue.add(node.left);
                        rangeQueue.add(node.right);
                    }
                }
            }
            prev = next;
        }
        if (lastElement != null) {
            result.add(lastElement);
        }
        return result;
    }
}
