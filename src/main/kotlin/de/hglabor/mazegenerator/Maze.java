package de.hglabor.mazegenerator;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Stack;

//this is not mine!!!
//but I forgot where I got this from
public class Maze {
    private final int width;
    private final int height;
    private final Stack<Integer> stack = new Stack<>();
    public BitSet maze;

    //initializes the maze
    public Maze(int w, int h) {
        width = w * 2 + 1;
        height = h * 2 + 1;
        maze = new BitSet(width * height);

        int finish = (int) (Math.random() * (width / 2)) * 2 + 1;
        maze.set(width * height - 1 - finish);

        int start = (int) (Math.random() * (width / 2)) * 2 + 1;
        maze.set(start);
        maze.set(start + width);

        stack.push(start + width);
    }

    public BitSet getBitSet() {
        return maze;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //takes one step towards converting maze
    //returns true if maze is finished
    //else returns false
    public boolean generate() {
        if (stack.empty()) return true;
        int m;
        int loc = stack.peek();

        ArrayList<Integer> move = new ArrayList<>();
        if (loc - 2 * width >= 0 && !maze.get(loc - 2 * width)) move.add(-1 * width);
        if (loc + 2 * width < width * height && !maze.get(loc + 2 * width)) move.add(width);
        if (loc + 2 < width * height && (loc + 2) / width == loc / width && !maze.get(loc + 2)) move.add(1);
        if (loc - 2 >= 0 && (loc - 2) / width == loc / width && !maze.get(loc - 2)) move.add(-1);

        if (move.size() > 0) {
            m = move.get((int) (Math.random() * move.size()));
            maze.set(stack.push(loc + 2 * m));
            maze.set(loc + m);
            return false;
        }
        stack.pop();
        return stack.empty();
    }
}
