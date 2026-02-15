import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicaJuego {
    private List<Personaje> personajes;
    private Personaje personjeOculto;
    private Random random;

    public LogicaJuego(){
        this.personajes = new ArrayList<>();
        this.random = new Random();
        inicializarPersonajes();
        seleccionarPersonajeOculto();
    }

    private void inicializarPersonajes() {
        personajes.add(new Personaje("Harry Potter", "Imagenes/harryPotter.jpg", "hombre", "Castaño", "Verde", true, false, false));
        personajes.add(new Personaje("Bellatrix Lestrange", "Imagenes/bellatrixLestrange.jpg", "mujer", "Negro", "Negro", false, false, false));
        personajes.add(new Personaje("Ron Weasly", "Imagenes/ronWeasly.png", "hombre", "Pelirrojo", "Azul", false, false, false));
        personajes.add(new Personaje("Luna Lovegood", "Imagenes/lunaLovegood.jpg", "mujer", "Rubio", "Azul", false, false, false));
        personajes.add(new Personaje("Albus Dumbledore", "Imagenes/albusDumbledore.jpeg", "hombre", "Blanco", "Azul", true, true, true));
        personajes.add(new Personaje("Cho Chang", "Imagenes/choChang.png", "mujer", "Negro", "Marrón", false, false, false));
        personajes.add(new Personaje("Draco Malfoy", "Imagenes/dracoMalfoy.jpeg", "hombre", "Rubio", "Gris", false, false, false));
        personajes.add(new Personaje("Lord Voldemort", "Imagenes/Lordvoldemort.jpg", "hombre", "Calvo", "Rojo", false, false, false));
        personajes.add(new Personaje("Hermione Granger", "Imagenes/hermioneGranger.jpg", "mujer", "Castaño", "Marrón", false, false, false));
        personajes.add(new Personaje("Dobby", "Imagenes/dobby.jpg", "hombre", "Calvo", "Verde", false, false, false));
        personajes.add(new Personaje("Rubeus Hagrid", "Imagenes/RubeusHagrid.jpg", "hombre", "Negro", "Negro", false, false, true));
        personajes.add(new Personaje("Neville Longbottom", "Imagenes/nevilleLongbottom.jpg", "hombre", "Castaño", "Marrón", false, false, false));
        personajes.add(new Personaje("Ginny Weasly", "Imagenes/ginnyWeasly.jpeg", "mujer", "Pelirrojo", "Marrón", false, false, false));
        personajes.add(new Personaje("Fleur Delacour", "Imagenes/fleurDelacour.png", "mujer", "Rubio", "Azul", false, true, false));
        personajes.add(new Personaje("Severus Snape", "Imagenes/severusSnape.jpeg", "hombre", "Negro", "Negro", false, false, false));
        personajes.add(new Personaje("Molly Weasley", "Imagenes/mollyWeasly.jpg", "mujer", "Pelirrojo", "Marrón", false, false, false));
        personajes.add(new Personaje("Minerva McGonagall", "Imagenes/minervaMcGonagall.jpeg", "mujer", "Gris", "Verde", true, true, false));
        personajes.add(new Personaje("Nymphadora Tonks", "Imagenes/tonks.jpg", "mujer", "Castaño", "Marrón", false, false, false));
        personajes.add(new Personaje("Sirius Black", "Imagenes/siriusBlack.jpg", "hombre", "Negro", "Gris", false, false, true));
        personajes.add(new Personaje("Remus Lupin", "Imagenes/remusLupin.jpeg", "hombre", "Castaño", "Marrón", false, false, true));
    }

    public void seleccionarPersonajeOculto(){
        int indice = random.nextInt(personajes.size());
        this.personjeOculto = personajes.get(indice);
        System.out.println("DEBUG: El personaje oculto es " + personjeOculto.getNombre());
    }

    public List<Personaje> getPersonajes(){
        return personajes;
    }

    public Personaje getPersonjeOculto(){
        return personjeOculto;
    }
}
