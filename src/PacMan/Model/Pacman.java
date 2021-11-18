package PacMan.Model;

public class Pacman extends Entite {

    public static final long TIME_SUPER = 12000;
    public static final long WAIT_TIME = 250;

    private static int KILLING_SPREE = 1;

    public long timeSuperRemaining;
    public boolean isSuper;

    public Pacman(int posX, int posY, Jeu jeu) {
        super(posX, posY, jeu);
        this.currDirection = Direction.RIGHT;
        this.waitTime = this.WAIT_TIME;

        this.isSuper = false;
    }

    @Override
    protected void realiserAction() throws InterruptedException {

        int nextX = this.posX;
        int nextY = this.posY;

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

        synchronized (this) {
            Case[][] plateau = this.jeu.plateau;
            Entite[][] tabEntite = this.jeu.tabEntite;

            if(plateau[nextX][nextY] instanceof Couloir) {
                Couloir nextPosition = (Couloir) plateau[nextX][nextY];

                tabEntite[this.posX][this.posY] = null;

                if(tabEntite[nextX][nextY] instanceof Fantome) {
                    Fantome fantome = (Fantome) tabEntite[nextX][nextY];

                    if(!this.isSuper)
                        this.isAlive = false;

                    else if(this.isSuper && fantome.isFear) {
                        fantome.isDead = true;
                        tabEntite[nextX][nextY] = this;
                        this.KILLING_SPREE++;
                        this.jeu.score += this.KILLING_SPREE * 200;
                    }

                }

                else
                    tabEntite[nextX][nextY] = this;

                if(nextPosition.pacGomme) {

                    nextPosition.pacGomme = false;
                    this.jeu.score += 10;

                } else if(nextPosition.superPacGomme) {

                    nextPosition.superPacGomme = false;
                    this.isSuper = true;
                    this.timeSuperRemaining = this.TIME_SUPER;

                    for(int i = 0; i < this.jeu.tabGhosts.length; i++) {
                        this.jeu.tabGhosts[i].isFear = true;
                        this.jeu.tabGhosts[i].waitTime = Fantome.FEAR_WAIT_TIME;
                    }

                    this.jeu.score += 50;

                }

                this.posX = nextX;
                this.posY = nextY;

            }
        }

    }

    @Override
    protected void decreaseTimeSuperRemaining() {
        this.timeSuperRemaining -= this.waitTime * 2;

        if(this.timeSuperRemaining == 0) {
            this.isSuper = false;
            this.KILLING_SPREE = 1;

            for(int i = 0; i < this.jeu.tabGhosts.length; i++) {
                this.jeu.tabGhosts[i].isFear = false;
                this.jeu.tabGhosts[i].waitTime = Fantome.WAIT_TIME;
            }
        }
    }
}
