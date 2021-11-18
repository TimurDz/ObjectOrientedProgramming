package PacMan.Model;

import PacMan.Model.IA.AStar;
import PacMan.Model.IA.Node;

import java.util.ArrayList;
import java.util.Random;

public class Fantome extends Entite {

    public static final long WAIT_TIME = 250;
    public static final long FEAR_WAIT_TIME = 400;
    private static final long DEAD_TIME = 4000;
    private static final int DIFFICULTY = 4; // Greater = easier; (Min = 1; Max = 9)

    public boolean isFear;
    public boolean isDead;
    public String color;

    private long timeBeforeStart;

    public Fantome(int posX, int posY, Jeu jeu, String color) {
        super(posX, posY, jeu);
        this.color = color;
        this.waitTime = this.WAIT_TIME;

        this.isFear = false;
        this.isDead = false;
        this.timeBeforeStart = 0;
    }

    public Fantome(int posX, int posY, Jeu jeu, String color, long timeBeforeStart) {
        super(posX, posY, jeu);
        this.color = color;
        this.waitTime = this.WAIT_TIME;

        this.isFear = false;
        this.isDead = false;
        this.timeBeforeStart = timeBeforeStart;
    }

    @Override
    protected void realiserAction() throws InterruptedException {

        if(this.timeBeforeStart != 0) {
            Thread.sleep(this.timeBeforeStart);
            this.timeBeforeStart = 0;
        }

        if(this.isDead) {
            this.isFear = false;
            for(int i = 0; i < Jeu.LONGUEUR; i++) {
                for(int j = 0; j < Jeu.LARGEUR; j++) {

                    if(this.jeu.plateau[i][j] instanceof Couloir) {
                        Couloir c = (Couloir) this.jeu.plateau[i][j];

                        if(c.respawn) {
                            this.posX = i;
                            this.posY = j;
                        }
                    }

                }
            }

            Thread.sleep(this.DEAD_TIME);
            this.isDead = false;
            this.waitTime = this.WAIT_TIME;
        }

        int nextX = this.posX;
        int nextY = this.posY;

        if(!this.isFear) {

            this.iaMovement();

        } else {

            this.randomMovement();

        }



        switch (this.currDirection) {
            case UP:
                nextY = this.posY - 1;
                break;

            case DOWN:
                nextY = this.posY + 1;
                break;

            case LEFT:
                nextX = this.posX - 1;
                break;

            case RIGHT:
                nextX = this.posX + 1;
                break;

            case NOT_A_DIRECTION:
                break;
        }

        if(nextX == -1)
            nextX += Jeu.LONGUEUR;

        if(nextY == -1)
            nextY += Jeu.LARGEUR;

        deplacement(nextX % Jeu.LONGUEUR, nextY % Jeu.LARGEUR);
    }

    @Override
    protected void deplacement(int nextX, int nextY) {

        synchronized(this) {

            Case[][] plateau = this.jeu.plateau;
            Entite[][] tabEntite = this.jeu.tabEntite;

            if(plateau[nextX][nextY] instanceof Couloir) {
                if(!(tabEntite[nextX][nextY] instanceof Fantome)) {
                    tabEntite[this.posX][this.posY] = null;

                    if(tabEntite[nextX][nextY] instanceof Pacman && !this.isFear) {
                        tabEntite[nextX][nextY].isAlive = false;
                    }

                    tabEntite[nextX][nextY] = this;

                    this.posX = nextX;
                    this.posY = nextY;
                }

            }

        }
    }

    @Override
    protected void decreaseTimeSuperRemaining() {}

    private void iaMovement() {

        Random rand = new Random();
        int randInt = rand.nextInt(10);

        if(randInt % this.DIFFICULTY == 0) {
            this.randomMovement();

        } else {
            AStar aStar = new AStar(this.jeu.plateau, new Node(this.posX, this.posY), new Node(this.jeu.getPacman().posX, this.jeu.getPacman().posY));
            ArrayList<Node> path = aStar.performAStar();

            this.currDirection = path.get(path.size() - 1).getDirection();

        }
    }

    private void randomMovement() {
        Random rand = new Random();
        int randDir = rand.nextInt(4);

        int tempX;
        int tempY;

        switch (randDir) {
            case 0:
                tempY = this.posY - 1;

                if(tempY == -1)
                    tempY += Jeu.LARGEUR;

                tempY = tempY % Jeu.LARGEUR;

                if(this.jeu.plateau[this.posX][tempY] instanceof Couloir)
                    this.currDirection = Direction.UP;
                break;

            case 1:
                tempY = this.posY + 1;

                if(tempY == -1)
                    tempY += Jeu.LARGEUR;

                tempY = tempY % Jeu.LARGEUR;

                if(this.jeu.plateau[this.posX][tempY] instanceof Couloir)
                    this.currDirection = Direction.DOWN;
                break;

            case 2:
                tempX = this.posX - 1;

                if(tempX == -1)
                    tempX += Jeu.LONGUEUR;

                tempX = tempX % Jeu.LONGUEUR;

                if(this.jeu.plateau[tempX][this.posY] instanceof Couloir)
                    this.currDirection = Direction.LEFT;
                break;

            case 3:
                tempX = this.posX + 1;

                if(tempX == -1)
                    tempX += Jeu.LONGUEUR;

                tempX = tempX % Jeu.LONGUEUR;

                if(this.jeu.plateau[tempX][this.posY] instanceof Couloir)
                    this.currDirection = Direction.RIGHT;
                break;
        }
    }
}
