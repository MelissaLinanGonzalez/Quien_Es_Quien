import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaJuego extends JFrame {

    private JPanel panelPrincipal;
    private JPanel panelCabecera;
    private JPanel panelTablero;
    private JPanel panelControles;
    private JLabel lblTitulo;
    private JButton btnAyuda;
    private JButton btnReiniciar;
    private JComboBox<String> comboPreguntas;
    private JButton btnPreguntar;
    private JLabel lblMensaje;
    private JScrollPane scrollPaneTablero;

    private LogicaJuego logica;
    private List<TarjetaPersonaje> listaTarjetas;

    private static final Color AZUL_OSCURO = new Color(10, 20, 50);
    private static final Color AZUL_PANEL = new Color(14, 26, 60);
    private static final Color DORADO = new Color(255, 215, 0);
    private static final Color DORADO_SUAVE = new Color(255, 215, 0, 140);
    private static final Color VERDE_SI = new Color(50, 205, 50);
    private static final Color ROJO_NO = new Color(255, 70, 70);

    public VentanaJuego() {
        setTitle("¿Quién es Quién? — Edición Harry Potter ⚡");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 820);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);

        setContentPane(panelPrincipal);

        if (scrollPaneTablero != null) {
            scrollPaneTablero.getVerticalScrollBar().setUnitIncrement(16);
        }

        logica = new LogicaJuego();
        listaTarjetas = new ArrayList<>();

        aplicarTema();
        configurarControles();
        cargarTablero();

        setVisible(true);
    }

    private void aplicarTema() {
        panelPrincipal.setBackground(AZUL_OSCURO);
        panelCabecera.setBackground(AZUL_OSCURO);
        panelTablero.setBackground(AZUL_PANEL);
        panelControles.setBackground(AZUL_OSCURO);

        lblTitulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
        lblTitulo.setForeground(DORADO);
        lblTitulo.setText("⚡ ¿Quién es Quién? — Harry Potter ⚡");

        estilizarBoton(btnAyuda, "  ?  ", new Font("Serif", Font.BOLD, 20));
        btnAyuda.setToolTipText("Instrucciones del juego");

        estilizarBoton(btnReiniciar, " ⏳ Girar el Tiempo ", new Font("Serif", Font.BOLD, 14));
        btnReiniciar.setToolTipText("Reiniciar la partida");

        comboPreguntas.setBackground(new Color(20, 30, 65));
        comboPreguntas.setForeground(DORADO);
        comboPreguntas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboPreguntas.setBorder(BorderFactory.createLineBorder(DORADO_SUAVE, 1));

        estilizarBoton(btnPreguntar, " Preguntar ", new Font("Serif", Font.BOLD, 15));

        lblMensaje.setFont(new Font("Serif", Font.BOLD, 18));
        lblMensaje.setForeground(DORADO);
        lblMensaje.setText("Elige una pregunta y haz clic en Preguntar...");

        panelCabecera.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, DORADO_SUAVE));
        panelControles.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, DORADO_SUAVE));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void estilizarBoton(JButton boton, String texto, Font fuente) {
        boton.setText(texto);
        boton.setFont(fuente);
        boton.setForeground(DORADO);
        boton.setBackground(new Color(20, 30, 65));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DORADO_SUAVE, 2),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(35, 50, 90));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(20, 30, 65));
            }
        });
    }

    private void configurarControles() {
        // Preguntas disponibles
        String[] preguntas = {
                "¿Es Hombre?", "¿Es Mujer?",
                "¿Tiene Gafas?", "¿Tiene Sombrero/Gorro?", "¿Tiene Barba?",
                "¿Tiene el pelo Rubio?", "¿Tiene el pelo Castaño?",
                "¿Tiene el pelo Pelirrojo?", "¿Tiene el pelo Negro?",
                "¿Tiene el pelo Blanco/Gris?",
                "¿Tiene los ojos Marrones?",
                "¿Tiene los ojos Azules?",
                "¿Tiene los ojos Verdes?",
                "¿Tiene los ojos Grises/Negros?"
        };
        comboPreguntas.setModel(new DefaultComboBoxModel<>(preguntas));

        btnPreguntar.addActionListener(e -> procesarPregunta());

        btnAyuda.addActionListener(e -> mostrarAyuda());

        btnReiniciar.addActionListener(e -> reiniciarPartida());
    }

    private void cargarTablero() {
        panelTablero.setLayout(new GridLayout(0, 4, 18, 18));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelTablero.removeAll();
        listaTarjetas.clear();

        for (Personaje p : logica.getPersonajes()) {
            TarjetaPersonaje tarjeta = new TarjetaPersonaje(p);

            tarjeta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!tarjeta.isDescartado()) {
                        intentarAdivinar(tarjeta.getPersonaje());
                    }
                }
            });

            listaTarjetas.add(tarjeta);
            panelTablero.add(tarjeta);
        }

        panelTablero.revalidate();
        panelTablero.repaint();
    }

    private void procesarPregunta() {
        String pregunta = (String) comboPreguntas.getSelectedItem();
        if (pregunta == null)
            return;

        Personaje oculto = logica.getPersonjeOculto();
        if (oculto == null)
            return;

        boolean respuesta = evaluarPregunta(pregunta, oculto);

        if (respuesta) {
            lblMensaje.setText("✦ SÍ ✦");
            lblMensaje.setForeground(VERDE_SI);
        } else {
            lblMensaje.setText("✦ NO ✦");
            lblMensaje.setForeground(ROJO_NO);
        }

        actualizarTablero(pregunta, respuesta);
    }

    private boolean evaluarPregunta(String pregunta, Personaje p) {
        switch (pregunta) {
            case "¿Es Hombre?":
                return p.getGenero().equalsIgnoreCase("Hombre");
            case "¿Es Mujer?":
                return p.getGenero().equalsIgnoreCase("Mujer");
            case "¿Tiene Gafas?":
                return p.isTieneGafas();
            case "¿Tiene Sombrero/Gorro?":
                return p.isTieneSombrero();
            case "¿Tiene Barba?":
                return p.isTieneBarba();
            case "¿Tiene el pelo Rubio?":
                return p.getColorPelo().toLowerCase().contains("rubio");
            case "¿Tiene el pelo Castaño?":
                return p.getColorPelo().toLowerCase().contains("castaño");
            case "¿Tiene el pelo Pelirrojo?":
                return p.getColorPelo().toLowerCase().contains("pelirrojo");
            case "¿Tiene el pelo Negro?":
                return p.getColorPelo().toLowerCase().contains("negro");
            case "¿Tiene el pelo Blanco/Gris?":
                return p.getColorPelo().toLowerCase().contains("blanco")
                        || p.getColorPelo().toLowerCase().contains("gris")
                        || p.getColorPelo().toLowerCase().contains("plateado");
            case "¿Tiene los ojos Marrones?":
                return p.getColorOjos().equalsIgnoreCase("Marrón");
            case "¿Tiene los ojos Azules?":
                return p.getColorOjos().equalsIgnoreCase("Azul");
            case "¿Tiene los ojos Verdes?":
                return p.getColorOjos().equalsIgnoreCase("Verde");
            case "¿Tiene los ojos Grises/Negros?":
                return p.getColorOjos().equalsIgnoreCase("Gris") || p.getColorOjos().equalsIgnoreCase("Negro");
            default:
                return false;
        }
    }

    private void actualizarTablero(String pregunta, boolean afirmativa) {
        int vivos = 0;
        String nombreUltimo = "";

        for (TarjetaPersonaje tarjeta : listaTarjetas) {
            if (tarjeta.isDescartado())
                continue;

            Personaje p = tarjeta.getPersonaje();
            boolean cumple = evaluarPregunta(pregunta, p);

            if ((afirmativa && !cumple) || (!afirmativa && cumple)) {
                tarjeta.setDescartado(true);
            }

            if (!tarjeta.isDescartado()) {
                vivos++;
                nombreUltimo = p.getNombre();
            }
        }

        if (vivos == 1) {
            mostrarVictoria(nombreUltimo);
        }
    }

    private void intentarAdivinar(Personaje elegido) {
        Personaje oculto = logica.getPersonjeOculto();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Crees que el personaje oculto es " + elegido.getNombre() + "?",
                "⚡ Adivinanza Mágica ⚡",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (elegido.getNombre().equals(oculto.getNombre())) {
                mostrarVictoria(elegido.getNombre());
            } else {
                mostrarDerrota(elegido.getNombre(), oculto.getNombre());
            }
        }
    }

    private void mostrarVictoria(String nombre) {
        JOptionPane.showMessageDialog(
                this,
                "🏆 ¡VICTORIA! 🏆\n\n"
                        + "El personaje oculto era: " + nombre + "\n\n"
                        + "¡Has demostrado ser un verdadero mago!",
                "⚡ ¡Felicidades! ⚡",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarDerrota(String elegido, String correcto) {
        JOptionPane.showMessageDialog(
                this,
                "💀 ¡DERROTA! 💀\n\n"
                        + "Elegiste: " + elegido + "\n"
                        + "El personaje oculto era: " + correcto + "\n\n"
                        + "El Sombrero Seleccionador se ha equivocado esta vez...",
                "⚡ Has fallado ⚡",
                JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAyuda() {
        JDialog dialogo = new JDialog(this, "⚡ Instrucciones — ¿Quién es Quién? ⚡", true);
        dialogo.setSize(520, 450);
        dialogo.setLocationRelativeTo(this);
        dialogo.getContentPane().setBackground(AZUL_OSCURO);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setBackground(AZUL_OSCURO);
        texto.setForeground(DORADO);
        texto.setFont(new Font("Serif", Font.PLAIN, 15));
        texto.setMargin(new Insets(20, 20, 20, 20));
        texto.setText(
                "═══════════════════════════════════════\n"
                        + "     ⚡ INSTRUCCIONES DEL JUEGO ⚡\n"
                        + "═══════════════════════════════════════\n\n"
                        + "Bienvenido al juego de ¿Quién es Quién?\n"
                        + "Edición Hogwarts.\n\n"
                        + "🎯 OBJETIVO:\n"
                        + "Adivina cuál es el personaje oculto que\n"
                        + "ha sido seleccionado al azar.\n\n"
                        + "📜 CÓMO JUGAR:\n"
                        + "1. Selecciona una pregunta del desplegable.\n"
                        + "2. Pulsa «Preguntar» para obtener la\n"
                        + "   respuesta (SÍ o NO).\n"
                        + "3. Los personajes que no coinciden se\n"
                        + "   volverán semi-transparentes (efecto\n"
                        + "   fantasma), como si fueran espectros.\n"
                        + "4. Cuando creas saber quién es, haz clic\n"
                        + "   directamente en su carta para adivinar.\n\n"
                        + "⏳ GIRAR EL TIEMPO:\n"
                        + "Pulsa este botón para reiniciar la partida\n"
                        + "con un nuevo personaje oculto.\n\n"
                        + "¡Buena suerte, joven mago! ⚡");

        JScrollPane scroll = new JScrollPane(texto);
        scroll.setBorder(BorderFactory.createLineBorder(DORADO_SUAVE, 2));
        scroll.getViewport().setBackground(AZUL_OSCURO);

        JButton btnCerrar = new JButton("Cerrar");
        estilizarBoton(btnCerrar, " Cerrar ", new Font("Serif", Font.BOLD, 14));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(AZUL_OSCURO);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        panelBoton.add(btnCerrar);

        dialogo.add(scroll, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void reiniciarPartida() {
        logica.seleccionarPersonajeOculto();

        for (TarjetaPersonaje tarjeta : listaTarjetas) {
            tarjeta.resetear();
        }

        lblMensaje.setText("Elige una pregunta y haz clic en Preguntar...");
        lblMensaje.setForeground(DORADO);
        comboPreguntas.setSelectedIndex(0);

        panelTablero.revalidate();
        panelTablero.repaint();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(0, 0));
        panelCabecera = new JPanel();
        panelCabecera.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);
        lblTitulo = new JLabel();
        lblTitulo.setText("Label");
        panelCabecera.add(lblTitulo);
        btnAyuda = new JButton();
        btnAyuda.setText("?");
        panelCabecera.add(btnAyuda);
        btnReiniciar = new JButton();
        btnReiniciar.setText("Reiniciar");
        panelCabecera.add(btnReiniciar);
        panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelPrincipal.add(panelControles, BorderLayout.SOUTH);
        comboPreguntas = new JComboBox();
        panelControles.add(comboPreguntas);
        btnPreguntar = new JButton();
        btnPreguntar.setText("Preguntas");
        panelControles.add(btnPreguntar);
        lblMensaje = new JLabel();
        lblMensaje.setText("");
        panelControles.add(lblMensaje);
        scrollPaneTablero = new JScrollPane();
        panelPrincipal.add(scrollPaneTablero, BorderLayout.CENTER);
        panelTablero = new JPanel();
        panelTablero.setLayout(new BorderLayout(0, 0));
        scrollPaneTablero.setViewportView(panelTablero);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelPrincipal;
    }

}
