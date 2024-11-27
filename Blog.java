import java.time.LocalDate;

public class Blog {

    private String titre;
    private Publiable[] LBillets;
    private int nbBillets;
    private int capacite;

    public Blog(String titre, int capacite) {
        this.titre = titre;
        this.capacite = capacite;
        this.LBillets = new Publiable[capacite];
        this.nbBillets = 0;
    }

    public void post(Publiable billet) throws InvalidURLException {
        if (billet instanceof Video) {
            Video video = (Video) billet;
            if (!video.getUrl().startsWith("https")) {
                throw new InvalidURLException("L'URL de la vidéo doit utiliser le protocole HTTPS.");
            }
        }

        if (nbBillets < capacite) {
            LBillets[nbBillets] = billet;
            nbBillets++;
        } else {
            System.out.println("Capacité maximale atteinte, impossible d'ajouter un billet.");
        }
    }

    public int getNombreBilletsTaggables() {
        int s = 0;
        for (int i = 0; i < nbBillets; i++) {
            if (LBillets[i] instanceof Taggable) {
                s++;
            }
        }
        return s;
    }

    public Publiable[] RechercheBilletsTaggablesParAuteur(String auteur) {
        Publiable[] result = new Publiable[nbBillets];
        int index = 0;

        for (int i = 0; i < nbBillets; i++) {
            if (LBillets[i] instanceof Taggable && LBillets[i].getAuteur().equals(auteur)) {
                result[index] = LBillets[i];
                index++;
            }
        }

        return result;
    }

    public Publiable getPlusRécentBillet() {
        if (nbBillets == 0) return null;

        Publiable plusRecent = LBillets[0];
        for (int i = 1; i < nbBillets; i++) {
            if (LBillets[i].getDatePublication().isAfter(plusRecent.getDatePublication())) {
                plusRecent = LBillets[i];
            }
        }
        return plusRecent;
    }

    public Publiable[] RechercheBilletsParContenu(String[] mots) {
        Publiable[] result = new Publiable[nbBillets];
        int index = 0;

        for (int i = 0; i < nbBillets; i++) {
            if (LBillets[i] instanceof Message) {
                Message message = (Message) LBillets[i];
                boolean contientTousLesMots = true;

                for (String mot : mots) {
                    if (message.getContenu().indexOf(mot) == -1) {
                        contientTousLesMots = false;
                        break;
                    }
                }

                if (contientTousLesMots) {
                    result[index] = message;
                    index++;
                }
            }
        }

      return result ;
    }

    public static void main(String[] args) {
        try {
            Blog blog = new Blog("Tech Blog", 10);

            blog.post(new Message(LocalDate.of(2024, 11, 25), "Auteur1", "Bienvenue sur mon blog de technologie"));
            blog.post(new Message(LocalDate.of(2024, 11, 26), "Auteur2", "Découvrez les dernières tendances en IA et en programmation"));

            BilletsTaggables billetTaggable1 = new BilletsTaggables(LocalDate.now(), "Auteur3", 5);
            billetTaggable1.ajoutTag("tech");
            billetTaggable1.ajoutTag("programming");
            blog.post(billetTaggable1);

            BilletsTaggables billetTaggable2 = new BilletsTaggables(LocalDate.of(2024, 11, 28), "Auteur1", 3);
            billetTaggable2.ajoutTag("AI");
            billetTaggable2.ajoutTag("AI2");
            blog.post(billetTaggable2);

            blog.post(new Video(LocalDate.now(), "Auteur4", 5, "https://securevideo.com/video"));

            try {
                blog.post(new Video(LocalDate.now(), "Auteur5", 5, "http://insecurevideo.com/fail"));
            } catch (InvalidURLException e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            System.out.println("Tous les billets publiés :");
            for (int i = 0; i < blog.getNombreBilletsTaggables(); i++) {
                System.out.println(blog.getPlusRécentBillet());
            }

            System.out.println("\nRecherche de billets taggables rédigés par Auteur3 :");
            Publiable[] billetsAuteur3 = blog.RechercheBilletsTaggablesParAuteur("Auteur3");
            for (Publiable billet : billetsAuteur3) {
                if (billet != null) {
                    System.out.println(billet);
                }
            }

            String[] motsRecherche = {"IA", "programmation"};
            System.out.println("\nRecherche de billets contenant tous les mots :");
            Publiable[] billetsContenantMots = blog.RechercheBilletsParContenu(motsRecherche);
            for (Publiable billet : billetsContenantMots) {
                if (billet != null) {
                    System.out.println(billet);
                }
            }

            System.out.println("\nLe billet le plus récent :");
            System.out.println(blog.getPlusRécentBillet());

        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
        }
    }
}
