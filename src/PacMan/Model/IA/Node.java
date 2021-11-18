package PacMan.Model.IA;

import PacMan.Model.Direction;

public class Node {
    private int x;
    private int y;

    private Node predecessor;

    private Direction direction;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        this.predecessor = null;
        this.direction = Direction.NOT_A_DIRECTION;
    }


    public Node(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;

        this.predecessor = null;
        this.direction = direction;
    }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public Node getPredecessor() { return this.predecessor; }

    public Direction getDirection() { return this.direction; }


    public void setPredecessor(Node predecessor) { this.predecessor = predecessor; }


    public double distance(Node goal) {
        //return Math.sqrt((this.x - goal.x) ^ 2 + (this.y - goal.y) ^ 2);
        return Math.hypot(goal.x - this.x, goal.y - this.y);
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof Node) {
            Node n = (Node) o;
            return (this.x == n.x && this.y == n.y);
        }

        return false;
    }

    @Override
    public String toString() {
        return "\nX : " + this.x + "\nY : " + this.y;
    }
}
