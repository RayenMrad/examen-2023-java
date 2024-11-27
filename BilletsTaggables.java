import java.time.LocalDate;
import java.util.Arrays;

public class BilletsTaggables extends Billet implements Taggable {
    protected String LTags[];
    protected int nbTags;
    
    public BilletsTaggables(LocalDate datePub, String auteur, int capacite){
        super(datePub,auteur);
        this.LTags = new String[capacite];
        this.nbTags = 0;
    }

    @Override
    public String toString() {
        return "BilletsTaggables [datePub=" + datePub + ", LTags=" + Arrays.toString(LTags) + ", nbTags=" + nbTags
                + ", auteur=" + auteur + "]";
    }

    @Override
    public void ajoutTag(String tag) {
        int pos = rechercheTag(tag);
        if(nbTags==LTags.length){
            System.out.println("impossible d'ajouter un autre tag la capacité est max");
            return;
        }
        if(pos!=-1){
            System.out.println("ce tag est deja existe");
            return;
        }else{
            LTags[nbTags]=tag;
            nbTags++;
        }
    }
    
    

    @Override
    public void supprimeTag(String tag){
           int pos = rechercheTag(tag);
        if (pos == -1) {
            System.out.println("Tag introuvable.");
            return;
        }
        for (int i = pos; i < nbTags - 1; i++) {
            LTags[i] = LTags[i + 1]; // Décale les éléments après le tag supprimé
        }
        LTags[nbTags - 1] = null; // Supprime le dernier tag
        nbTags--;
    }


    @Override
    public int nombreTags() {
        return LTags.length;
    }

    @Override
    public int rechercheTag(String tag) {
        int position=-1;
        for (int i = 0; i < nbTags ; i++) {
            if (LTags[i].equals(tag)) {
                return i;
            }
        };
        return position;
    }
    
}
