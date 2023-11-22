package models;

import mg.tonymushah.dbconnection.utils.annotations.Column;
import mg.tonymushah.dbconnection.utils.annotations.Table;

@Table(name = "personne")
public class Personne {
    @Column(name = "nom")
    private String nom;
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Personne() {
    }
    public Personne(String nom) {
        this.nom = nom;
    }
    
}
