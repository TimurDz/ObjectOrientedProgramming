package PacMan.Model.IA;

import PacMan.Model.Case;
import PacMan.Model.Couloir;
import PacMan.Model.Direction;

import java.util.ArrayList;
import java.util.Stack;

public class AStar {

    private Case[][] plateau;
    private Node startNode;
    private Node goalNode;
    private ArrayList<Node> path;


    public AStar(Case[][] plateau, Node startNode, Node goalNode) {
        this.plateau = plateau;
        this.startNode = startNode;
        this.goalNode = goalNode;

        this.path = new ArrayList<>();
    }

    public ArrayList<Node> performAStar() {
        Stack<Node> openList = new Stack<>();
        ArrayList<Node> closedList = new ArrayList<>();
        ArrayList<Node> possibleMoves;

        openList.push(this.startNode);

        boolean found = false;

        Node currNode = null;

        while(!openList.empty() && !found) {  // While there is a Node remaining or the goal isn't found

            currNode = openList.pop();

            if(!closedList.contains(currNode)) {

                if(this.goalNode.equals(currNode)) {
                    found = true;
                } else {

                    possibleMoves = getPossibleMoves(currNode);
                    for(Node successor : possibleMoves) {

                        openList.push(successor);
                        successor.setPredecessor(currNode);

                    }

                    openList.sort((n1, n2) -> {
                        if(n1.distance(goalNode) > n2.distance(goalNode)) {
                            return -1;
                        } else {
                            return 1;
                        }
                    });

                }

            }

            closedList.add(currNode);
        }

        // Getting the path
        if(this.goalNode.equals(currNode)) {

            while(!this.startNode.equals(currNode)) {
                this.path.add(currNode);
                currNode = currNode.getPredecessor();
            }

        } else {
            this.path.add(startNode);
        }

        return this.path;
    }

    private ArrayList<Node> getPossibleMoves(Node currNode) {
        int x = currNode.getX();
        int y = currNode.getY();
        ArrayList<Node> moves = new ArrayList<>();

        if(this.plateau[x - 1][y] instanceof Couloir)
            moves.add(new Node(x - 1, y, Direction.LEFT));

        if(this.plateau[x][y - 1] instanceof Couloir)
            moves.add(new Node(x, y - 1, Direction.UP));

        if(this.plateau[x + 1][y] instanceof Couloir)
            moves.add(new Node(x + 1, y, Direction.RIGHT));

        if(this.plateau[x][y + 1] instanceof Couloir)
            moves.add(new Node(x, y + 1, Direction.DOWN));

        return moves;
    }
}
