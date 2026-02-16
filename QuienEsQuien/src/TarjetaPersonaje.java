import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class TarjetaPersonaje extends JPanel {

    private Personaje personaje;
    private boolean descartado;
    private float opacidad;
    private Image imagenPersonaje;

    // Ajuste de colores: se ha reducido el canal alfa (opacidad) para que sean traslúcidos
    private static final Color COLOR_FONDO = new Color(10, 20, 50, 160); // Más traslúcido
    private static final Color COLOR_DORADO = new Color(255, 215, 0);
    private static final Color COLOR_BORDE = new Color(255, 215, 0, 140);
    private static final Color COLOR_NOMBRE_BG = new Color(10, 20, 50, 120); // Más traslúcido
    private static final Color COLOR_DESCARTADO_OVERLAY = new Color(10, 20, 50, 180);

    // Dimensiones más cuadradas: se ha reducido el ancho para ajustarse más a la imagen
    private static final int ANCHO_CARTA = 140; // Antes 155
    private static final int ALTO_CARTA = 185;  // Antes 200
    private static final int TAMANO_IMAGEN = 120;

    public TarjetaPersonaje(Personaje personaje) {
        this.personaje = personaje;
        this.descartado = false;
        this.opacidad = 1.0f;

        setPreferredSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));
        setMinimumSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));
        setOpaque(false); // Importante para que se vea el fondo a través del panel
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setToolTipText("Clic para adivinar: " + personaje.getNombre());

        cargarImagen();
    }

    private void cargarImagen() {
        try {
            ImageIcon icon = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource("/" + personaje.getNombreImagen()))
            );
            imagenPersonaje = icon.getImage().getScaledInstance(
                    TAMANO_IMAGEN, TAMANO_IMAGEN, Image.SCALE_SMOOTH
            );
        } catch (Exception e) {
            imagenPersonaje = null;
            System.err.println("No se pudo cargar imagen: " + personaje.getNombreImagen());
        }
    }

    public void setDescartado(boolean descartado) {
        this.descartado = descartado;
        this.opacidad = descartado ? 0.22f : 1.0f;
        repaint();
    }

    public boolean isDescartado() {
        return descartado;
    }

    public void resetear() {
        this.descartado = false;
        this.opacidad = 1.0f;
        repaint();
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidad));

        int w = getWidth();
        int h = getHeight();

        // Fondo traslúcido
        g2d.setColor(COLOR_FONDO);
        g2d.fillRoundRect(1, 1, w - 2, h - 2, 16, 16);

        g2d.setColor(COLOR_BORDE);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawRoundRect(1, 1, w - 3, h - 3, 16, 16);

        int imgX = (w - TAMANO_IMAGEN) / 2;
        int imgY = 10; // Espacio superior reducido

        if (imagenPersonaje != null) {
            Shape clipAnterior = g2d.getClip();
            g2d.setClip(new java.awt.geom.RoundRectangle2D.Float(
                    imgX, imgY, TAMANO_IMAGEN, TAMANO_IMAGEN, 10, 10));
            g2d.drawImage(imagenPersonaje, imgX, imgY, TAMANO_IMAGEN, TAMANO_IMAGEN, this);
            g2d.setClip(clipAnterior);

            g2d.setColor(new Color(255, 215, 0, 80));
            g2d.setStroke(new BasicStroke(1.2f));
            g2d.drawRoundRect(imgX, imgY, TAMANO_IMAGEN, TAMANO_IMAGEN, 10, 10);
        }

        // Nombre del personaje más ajustado
        int nombreY = imgY + TAMANO_IMAGEN + 6;
        g2d.setColor(COLOR_NOMBRE_BG);
        g2d.fillRoundRect(8, nombreY, w - 16, h - nombreY - 8, 8, 8);

        g2d.setColor(COLOR_DORADO);
        g2d.setFont(new Font("Serif", Font.BOLD, 12)); // Fuente ligeramente más pequeña
        FontMetrics fm = g2d.getFontMetrics();
        String nombre = personaje.getNombre();
        int textoX = (w - fm.stringWidth(nombre)) / 2;
        int textoY = nombreY + ((h - nombreY - 8) + fm.getAscent()) / 2 - 2;
        g2d.drawString(nombre, textoX, textoY);

        if (descartado) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2d.setColor(COLOR_DESCARTADO_OVERLAY);
            g2d.fillRoundRect(1, 1, w - 2, h - 2, 16, 16);
        }

        g2d.dispose();
    }
}