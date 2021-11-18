package PacMan.Model;

public class Couloir extends Case {

    public boolean pacGomme;
    public boolean superPacGomme;
    public boolean respawn;

    public Couloir(boolean pacGomme, boolean superPacGomme) {
        this.pacGomme = pacGomme;
        this.superPacGomme = superPacGomme;
        this.respawn = false;
    }

    public Couloir(boolean pacGomme, boolean superPacGomme, boolean respawn) {
        this.pacGomme = pacGomme;
        this.superPacGomme = superPacGomme;
        this.respawn = respawn;
    }
}
