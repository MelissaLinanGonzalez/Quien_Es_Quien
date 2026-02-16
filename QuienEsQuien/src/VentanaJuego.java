import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaJuego extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelCabecera;
    private JLabel lblTitulo;
    private JPanel panelTablero;
    private JPanel panelControles;
    private JComboBox<String> comboPreguntas;
    private JButton btnPreguntar;
    private JLabel lblMensaje;
    private JButton btnReiniciar;
    private JButton btnAyuda;
    private JScrollPane scrollPaneTablero;

    // Etiqueta para las vidas
    private JLabel lblIntentos;

    private LogicaJuego logica;
    private List<TarjetaPersonaje> listaTarjetas;

    // Constantes de color
    private static final Color DORADO = new Color(255, 215, 0);
    private static final Color DORADO_SUAVE = new Color(255, 223, 100);
    private static final Color AZUL_OSCURO = new Color(10, 20, 40);

    private static final Color VERDE_EXITO = new Color(50, 255, 50);
    private static final Color ROJO_ERROR = new Color(255, 50, 50);

    public VentanaJuego() {
        logica = new LogicaJuego();
        listaTarjetas = new ArrayList<>();

        $$$setupUI$$$();

        setTitle("¿Quién es Quién? - Harry Potter Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);

        if (panelPrincipal == null) {
            createUIComponents();
            setContentPane(panelPrincipal);
        } else {
            setContentPane(panelPrincipal);
        }

        configurarControles();
        aplicarTema();
        cargarTablero();

        btnPreguntar.addActionListener(e -> realizarPregunta());
        btnReiniciar.addActionListener(e -> reiniciarJuego());
        btnAyuda.addActionListener(e -> mostrarAyuda());

        actualizarIntentosUI();

        if (scrollPaneTablero != null) {
            scrollPaneTablero.getVerticalScrollBar().setUnitIncrement(16);
        }

        setVisible(true);
    }

    private void createUIComponents() {
        panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                var url = getClass().getResource("/Imagenes/fondo.png");
                if (url != null) {
                    Image img = new ImageIcon(url).getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(AZUL_OSCURO);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
    }

    private void configurarControles() {
        String[] preguntas = {
                "¿Es Hombre?", "¿Es Mujer?",
                "¿Tiene Gafas?", "¿Tiene Sombrero/Gorro?", "¿Tiene Barba?",
                "¿Tiene el pelo Rubio?", "¿Tiene el pelo Castaño?",
                "¿Tiene el pelo Pelirrojo?", "¿Tiene el pelo Negro?",
                "¿Tiene el pelo Blanco/Gris?", "Calvo",
                "¿Tiene los ojos Marrones?",
                "¿Tiene los ojos Azules?",
                "¿Tiene los ojos Verdes?",
                "¿Tiene los ojos Grises/Negros?"
        };
        comboPreguntas.setModel(new DefaultComboBoxModel<>(preguntas));

        panelControles.removeAll();

        lblIntentos = new JLabel("Vidas: 3");
        lblIntentos.setFont(new Font("Serif", Font.BOLD, 20));
        lblIntentos.setForeground(new Color(255, 100, 100));

        lblMensaje.setText("");
        lblMensaje.setFont(new Font("Serif", Font.BOLD, 24));
        lblMensaje.setPreferredSize(new Dimension(80, 30));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);

        panelControles.add(comboPreguntas);
        panelControles.add(btnPreguntar);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(lblMensaje);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(lblIntentos);

        panelControles.revalidate();
        panelControles.repaint();
    }

    private void aplicarTema() {
        panelPrincipal.setOpaque(false);

        // Barras opacas para evitar errores visuales
        panelCabecera.setOpaque(true);
        panelCabecera.setBackground(AZUL_OSCURO);
        panelControles.setOpaque(true);
        panelControles.setBackground(AZUL_OSCURO);

        panelTablero.setOpaque(false);
        if (scrollPaneTablero != null) {
            scrollPaneTablero.setOpaque(false);
            scrollPaneTablero.getViewport().setOpaque(false);
            scrollPaneTablero.setBorder(null);
            scrollPaneTablero.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        }

        lblTitulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
        lblTitulo.setForeground(DORADO);
        lblTitulo.setText("⚡ ¿Quién es Quién? — Harry Potter ⚡");

        estilizarBoton(btnAyuda, "  ?  ", new Font("Serif", Font.BOLD, 20));
        btnAyuda.setToolTipText("Instrucciones");

        estilizarBoton(btnReiniciar, " ⏳ Girar el Tiempo ", new Font("Serif", Font.BOLD, 14));
        btnReiniciar.setToolTipText("Reiniciar partida");

        comboPreguntas.setBackground(new Color(20, 30, 65));
        comboPreguntas.setForeground(DORADO);
        comboPreguntas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboPreguntas.setBorder(BorderFactory.createLineBorder(DORADO_SUAVE, 1));

        estilizarBoton(btnPreguntar, " Preguntar ", new Font("Serif", Font.BOLD, 15));

        panelCabecera.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, DORADO_SUAVE));
        panelControles.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, DORADO_SUAVE));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void estilizarBoton(JButton btn, String texto, Font fuente) {
        btn.setText(texto);
        btn.setFont(fuente);
        btn.setBackground(AZUL_OSCURO);
        btn.setForeground(DORADO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DORADO, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }

            public void mouseExited(MouseEvent evt) {
                btn.setForeground(DORADO);
                btn.setBorder(BorderFactory.createLineBorder(DORADO, 2));
            }
        });
    }

    private void cargarTablero() {
        panelTablero.setLayout(new GridLayout(0, 4, 15, 15));
        panelTablero.removeAll();
        listaTarjetas.clear();

        for (Personaje p : logica.getPersonajes()) {
            TarjetaPersonaje tarjeta = new TarjetaPersonaje(p);

            // --- LÓGICA DE CLIC CON VIDAS ---
            tarjeta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (tarjeta.isDescartado()) return;

                    if (logica.esPersonajeSecreto(p)) {
                        // CASO ACIERTO
                        JOptionPane.showMessageDialog(VentanaJuego.this,
                                "¡CORRECTO! ⚡\nHas descubierto a " + p.getNombre() + ".",
                                "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
                        reiniciarJuego();
                    } else {
                        // CASO FALLO
                        logica.restarIntento();
                        actualizarIntentosUI();

                        if (!logica.quedanIntentos()) {
                            // CASO GAME OVER
                            JOptionPane.showMessageDialog(VentanaJuego.this,
                                    "¡HAS PERDIDO! 💀\nTe has quedado sin intentos.\nEl personaje secreto era: " +
                                            logica.getPersonajeSecreto().getNombre(),
                                    "Derrota", JOptionPane.ERROR_MESSAGE);
                            reiniciarJuego();
                        } else {
                            // CASO PIERDE VIDA
                            JOptionPane.showMessageDialog(VentanaJuego.this,
                                    "¡Incorrecto! Ese no es el personaje.\nPierdes una vida.",
                                    "Fallo", JOptionPane.WARNING_MESSAGE);
                            tarjeta.setDescartado(true);
                        }
                    }
                }
            });

            listaTarjetas.add(tarjeta);
            panelTablero.add(tarjeta);
        }

        panelTablero.revalidate();
        panelTablero.repaint();
    }

    private void actualizarIntentosUI() {
        if (lblIntentos != null) {
            lblIntentos.setText("Vidas: " + "⚡".repeat(logica.getIntentosRestantes()));
            if (logica.getIntentosRestantes() == 1) {
                lblIntentos.setForeground(Color.RED);
            } else {
                lblIntentos.setForeground(new Color(255, 100, 100));
            }
        }
    }

    private void realizarPregunta() {
        String pregunta = (String) comboPreguntas.getSelectedItem();
        if (pregunta == null) return;

        Personaje secreto = logica.getPersonajeSecreto();
        boolean respuesta = evaluarPregunta(pregunta, secreto);

        if (respuesta) {
            lblMensaje.setText("SÍ");
            lblMensaje.setForeground(VERDE_EXITO);
        } else {
            lblMensaje.setText("NO");
            lblMensaje.setForeground(ROJO_ERROR);
        }

        for (TarjetaPersonaje tarjeta : listaTarjetas) {
            if (tarjeta.isDescartado()) continue;

            boolean cumplePropiedad = evaluarPregunta(pregunta, tarjeta.getPersonaje());

            if (respuesta && !cumplePropiedad) tarjeta.setDescartado(true);
            else if (!respuesta && cumplePropiedad) tarjeta.setDescartado(true);
        }
    }

    private boolean evaluarPregunta(String pregunta, Personaje p) {
        switch (pregunta) {
            case "¿Es Hombre?":
                return p.getGenero().equalsIgnoreCase("hombre");
            case "¿Es Mujer?":
                return p.getGenero().equalsIgnoreCase("mujer");
            case "¿Tiene Gafas?":
                return p.isTieneGafas();
            case "¿Tiene Sombrero/Gorro?":
                return p.isTieneSombrero();
            case "¿Tiene Barba?":
                return p.isTieneBarba();
            case "¿Tiene el pelo Rubio?":
                return p.getColorPelo().equalsIgnoreCase("Rubio");
            case "¿Tiene el pelo Castaño?":
                return p.getColorPelo().equalsIgnoreCase("Castaño");
            case "¿Tiene el pelo Pelirrojo?":
                return p.getColorPelo().equalsIgnoreCase("Pelirrojo");
            case "¿Tiene el pelo Negro?":
                return p.getColorPelo().equalsIgnoreCase("Negro");
            case "¿Tiene el pelo Blanco/Gris?":
                return p.getColorPelo().equalsIgnoreCase("Blanco") || p.getColorPelo().equalsIgnoreCase("Gris");
            case "Calvo":
                return p.getColorPelo().equalsIgnoreCase("Calvo");
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

    private void reiniciarJuego() {
        logica.reiniciarPartida();
        for (TarjetaPersonaje t : listaTarjetas) {
            t.resetear();
        }
        lblMensaje.setText("...");
        lblMensaje.setForeground(Color.WHITE);
        actualizarIntentosUI();
    }

    private void mostrarAyuda() {
        // 1. Colores en formato Hexadecimal para el HTML (Coinciden con tus constantes)
        String hexAzul = "#0A1428";   // Tu AZUL_OSCURO (10, 20, 40)
        String hexDorado = "#FFD700"; // Tu DORADO
        String hexVerde = "#32FF32";  // Verde brillante
        String hexRojo = "#FF4500";   // Rojo anaranjado brillante

        // 2. Diseño de la Ayuda en HTML/CSS
        String contenidoAyuda = "<html>" +
                "<body style='font-family: Serif; font-size: 14px; background-color: " + hexAzul + "; color: " + hexDorado + "; margin: 10px;'>" +

                "<h1 style='text-align: center; border-bottom: 2px solid " + hexDorado + "; padding-bottom: 5px;'>⚡ Guía del Mago ⚡</h1>" +

                "<p><b>1. EL MISTERIO</b><br>" +
                "La máquina ha seleccionado un <i>Personaje Secreto</i> al azar.<br>" +
                "Tu misión es usar tu magia deductiva para descubrir quién es.</p>" +

                "<hr style='color: " + hexDorado + ";'>" +

                "<p><b>2. LANZAR HECHIZOS (Preguntas)</b><br>" +
                "Usa el menú desplegable inferior para filtrar sospechosos. Esto es seguro y <b>no gasta vidas</b>.</p>" +
                "<ul>" +
                "<li>Si la respuesta es <b style='color:" + hexVerde + ";'>SÍ</b>: Se oscurecen los que NO lo cumplen.</li>" +
                "<li>Si la respuesta es <b style='color:" + hexRojo + ";'>NO</b>: Se oscurecen los que SÍ lo cumplen.</li>" +
                "</ul>" +

                "<hr style='color: " + hexDorado + ";'>" +

                "<p><b>3. ADIVINAR EL PERSONAJE (¡Riesgo!)</b><br>" +
                "Cuando creas saber la respuesta, <b>haz clic sobre la carta</b> del personaje.</p>" +

                "<div style='border: 1px solid " + hexRojo + "; padding: 5px; background-color: #152030;'>" +
                "<p style='text-align: center; color: " + hexRojo + "; margin-top: 0;'><b>⚠️ PELIGRO MORTAL ⚠️</b></p>" +
                "Tienes un límite de <b>3 VIDAS (⚡⚡⚡)</b>." +
                "<ul style='margin-bottom: 0;'>" +
                "<li><b>Fallo = -1 Vida.</b></li>" +
                "<li>Si pierdes las 3 vidas... <b>¡GAME OVER!</b><br>El juego termina y se revela el secreto.</li>" +
                "</ul>" +
                "</div>" +

                "<br><p style='text-align: center; font-style: italic;'>¿Estás listo para el reto?</p>" +
                "</body></html>";

        // 3. Crear el componente visual para mostrar el HTML
        JEditorPane editorPane = new JEditorPane("text/html", contenidoAyuda);
        editorPane.setEditable(false); // Para que solo sea lectura
        editorPane.setBackground(new Color(10, 20, 40)); // Fondo AZUL_OSCURO sólido del componente
        editorPane.setCaretPosition(0); // Asegura que el scroll empiece arriba

        // 4. Meterlo dentro de un ScrollPane (Barra de desplazamiento)
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setPreferredSize(new Dimension(450, 400)); // Tamaño de la ventana
        scrollPane.setBorder(BorderFactory.createLineBorder(DORADO, 2)); // Borde dorado exterior

        // 5. Eliminar el borde feo del JEditorPane interno
        editorPane.setBorder(BorderFactory.createEmptyBorder());

        // 6. Mostrar el diálogo personalizado
        // Usamos PLAIN_MESSAGE para que no salga el icono por defecto al lado, ya que tenemos nuestro diseño
        JOptionPane.showMessageDialog(this, scrollPane, "Instrucciones", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
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