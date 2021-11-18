package PacMan.Model.Parser;

import PacMan.Model.*;


import java.io.*;
import java.util.ArrayList;

public class Parser {


    private Jeu jeu;
    private BufferedReader br;
    private InputStreamReader isr;
    private ArrayList<Entite> entites;


    public Parser(Jeu jeu) throws FileNotFoundException {
        this.jeu = jeu;
        this.entites = new ArrayList<>();
        this.isr = new InputStreamReader(System.class.getResourceAsStream("/plateaux/defaultPlateau.txt"));
        this.br = new BufferedReader(this.isr);
    }

    public Parser(String s) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(s);

        this.br = new BufferedReader(new InputStreamReader(fis));
    }


    /**
     *  Function which reads the file to create the array of Case for the Plateau
     * @return Case[][] the array of Case which represent the Plateau
     * @throws IOException
     */
    public Case[][] createPlateau() throws IOException {
        Case[][] plateau = new Case[Jeu.LONGUEUR][Jeu.LARGEUR];
        String[] colors =  { "red", "blue", "orange", "pink"};
        int indexColors = 0;

        int ligne = 0;
        int colonne = 0;

        while(!this.br.ready()) {}

        String ligneString = this.br.readLine();
        while(ligneString != null) {
            for(char c : ligneString.toCharArray()) {

                switch(c) {
                    case ' ':
                        plateau[ligne][colonne] = new Case();
                        break;

                    case 'M':
                        plateau[ligne][colonne] = new Mur();
                        break;

                    case 'p':
                        plateau[ligne][colonne] = new Couloir(true, false);
                        break;

                    case 'P':
                        plateau[ligne][colonne] = new Couloir(false, true);
                        break;

                    case '_':
                        plateau[ligne][colonne] = new Couloir(false,false);
                        break;

                    case 'J':
                        this.entites.add(new Pacman(ligne, colonne, this.jeu));
                        plateau[ligne][colonne] = new Couloir(false,false);
                        break;

                    case 'F':
                        plateau[ligne][colonne] = new Couloir(false,false);
                        if(indexColors == 0) {

                            this.entites.add(new Fantome(ligne, colonne, this.jeu, colors[indexColors]));

                        } else if(indexColors == 1) {
                            this.entites.add(new Fantome(ligne, colonne, this.jeu, colors[indexColors], 10000));
                        } else {
                            this.entites.add(new Fantome(ligne, colonne, this.jeu, colors[indexColors], 15000));
                        }
                        indexColors++;
                        break;

                    case 'R':
                        this.entites.add(new Fantome(ligne, colonne, this.jeu, colors[indexColors], 5000));
                        plateau[ligne][colonne] = new Couloir(false, false, true);
                        indexColors++;
                        break;

                    default:
                        throw new IOException("Caractère non reconnu");
                }

                ligne++;

            }

            ligne = 0;
            colonne++;
            ligneString = this.br.readLine();
        }

        this.br.close();
        this.isr.close();
        return plateau;
    }


    /**
     *  Function which creates the array which stores the Entites position
     * @return Entite[][] the array which stores the Entites position
     */
    public Entite[][] createEntite() {
        Entite[][] tabEntite = new Entite[Jeu.LONGUEUR][Jeu.LARGEUR];

        for(Entite e : this.entites) {
            tabEntite[e.getPosX()][e.getPosY()] = e;
        }

        return tabEntite;
    }
}
