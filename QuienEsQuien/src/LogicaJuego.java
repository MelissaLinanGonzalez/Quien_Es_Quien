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

    private void inicializarPersonajes(){
        personajes.add(new Personaje("Harry Potter", "Imagenes/harryPotter.jpg", "hombre", "castaño oscuro", "castaños", true, false, false));
        personajes.add(new Personaje("Albus Dumbledore", "Imagenes/albusDumbledore.jpeg", "hombre", "blanco", "castaños", true, true, true));
        personajes.add(new Personaje("Draco Malfoy", "Imagenes/dracoMalfoy.jpeg", "hombre", "rubio platino", "azul grisáceo", false, false, false));
        personajes.add(new Personaje("Ginny Weasly", "Imagenes/ginnyWeasly.jpeg", "mujer", "pelirrojo", "azules", false, false, false));
        personajes.add(new Personaje("Hermione Granger", "Imagenes/hermioneGranger.jpg", "mujer", "castaño rojizo", "marrones", false, false, false));
        personajes.add(new Personaje("Minerva McGonagall", "Imagenes/minervaMcGonagall.jpeg", "mujer", "gris plateado", "azul claro", true, true, false));
        personajes.add(new Personaje("Ron Weasly", "Imagenes/ronWeasly.png", "hombre", "pelirrojo", "verdes", false, false, false));
        personajes.add(new Personaje("Rubeus Hagrid", "Imagenes/RubeusHagrid.jpg", "hombre", "negro", "marrón oscuros", false, false, true));
        personajes.add(new Personaje("Severus Snape", "Imagenes/severusSnape.jpeg", "hombre", "negro", "marrón oscuros", false, false, false));
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
