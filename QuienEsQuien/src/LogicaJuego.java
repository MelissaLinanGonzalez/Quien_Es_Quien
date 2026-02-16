import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicaJuego {

    private List<Personaje> personajes;
    private List<Personaje> personajesActivos;
    private Personaje personajeSecreto;
    private int intentosRestantes;
    // LÍMITE DE VIDAS
    private static final int MAX_INTENTOS = 3;

    public LogicaJuego() {
        personajes = new ArrayList<>();
        inicializarPersonajes();
        reiniciarPartida();
    }

    private void inicializarPersonajes() {
        // Carga de personajes (Asegúrate de que las rutas y nombres coincidan con tus archivos)
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
        personajes.add(new Personaje("Nymphadora Tonks", "Imagenes/tonks.jpg", "mujer", "Pelirrojo", "Marrón", false, false, false));
        personajes.add(new Personaje("Sirius Black", "Imagenes/siriusBlack.jpg", "hombre", "Negro", "Gris", false, false, true));
        personajes.add(new Personaje("Remus Lupin", "Imagenes/remusLupin.jpeg", "hombre", "Castaño", "Marrón", false, false, true));
    }

    public void reiniciarPartida() {
        personajesActivos = new ArrayList<>(personajes);
        Random rand = new Random();
        personajeSecreto = personajes.get(rand.nextInt(personajes.size()));
        intentosRestantes = MAX_INTENTOS;
        System.out.println("Secreto (Debug): " + personajeSecreto.getNombre());
    }

    // --- LÓGICA DE VIDAS ---
    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public void restarIntento() {
        if (intentosRestantes > 0) {
            intentosRestantes--;
        }
    }

    public boolean quedanIntentos() {
        return intentosRestantes > 0;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public boolean esPersonajeSecreto(Personaje p) {
        return p.getNombre().equals(personajeSecreto.getNombre());
    }

    public Personaje getPersonajeSecreto() {
        return personajeSecreto;
    }
}