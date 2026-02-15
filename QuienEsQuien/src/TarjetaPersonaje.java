import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class TarjetaPersonaje extends JPanel {

    private Personaje personaje;
    private boolean descartado;
    private float opacidad;
    private Image imagenPersonaje;

    private static final Color COLOR_FONDO = new Color(10, 20, 50);
    private static final Color COLOR_DORADO = new Color(255, 215, 0);
    private static final Color COLOR_BORDE = new Color(255, 215, 0, 180);
    private static final Color COLOR_NOMBRE_BG = new Color(10, 20, 50, 200);
    private static final Color COLOR_DESCARTADO_OVERLAY = new Color(10, 20, 50, 160);

    private static final int ANCHO_CARTA = 155;
    private static final int ALTO_CARTA = 200;
    private static final int TAMANO_IMAGEN = 120;

    public TarjetaPersonaje(Personaje personaje) {
        this.personaje = personaje;
        this.descartado = false;
        this.opacidad = 1.0f;

        setPreferredSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));
        setMinimumSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));
        setOpaque(false);
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

        g2d.setColor(COLOR_FONDO);
        g2d.fillRoundRect(1, 1, w - 2, h - 2, 16, 16);

        g2d.setColor(COLOR_BORDE);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawRoundRect(1, 1, w - 3, h - 3, 16, 16);

        g2d.setColor(new Color(255, 215, 0, 60));
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawRoundRect(5, 5, w - 11, h - 11, 12, 12);

        int imgX = (w - TAMANO_IMAGEN) / 2;
        int imgY = 14;

        if (imagenPersonaje != null) {
            Shape clipAnterior = g2d.getClip();
            g2d.setClip(new java.awt.geom.RoundRectangle2D.Float(
                    imgX, imgY, TAMANO_IMAGEN, TAMANO_IMAGEN, 10, 10));
            g2d.drawImage(imagenPersonaje, imgX, imgY, TAMANO_IMAGEN, TAMANO_IMAGEN, this);
            g2d.setClip(clipAnterior);

            g2d.setColor(new Color(255, 215, 0, 100));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(imgX, imgY, TAMANO_IMAGEN, TAMANO_IMAGEN, 10, 10);
        } else {
            g2d.setColor(new Color(30, 40, 70));
            g2d.fillRoundRect(imgX, imgY, TAMANO_IMAGEN, TAMANO_IMAGEN, 10, 10);
            g2d.setColor(COLOR_DORADO);
            g2d.setFont(new Font("Serif", Font.ITALIC, 11));
            g2d.drawString("Sin imagen", imgX + 25, imgY + 65);
        }

        int nombreY = imgY + TAMANO_IMAGEN + 8;
        g2d.setColor(COLOR_NOMBRE_BG);
        g2d.fillRoundRect(8, nombreY, w - 16, h - nombreY - 8, 8, 8);

        g2d.setColor(COLOR_DORADO);
        g2d.setFont(new Font("Serif", Font.BOLD, 13));
        FontMetrics fm = g2d.getFontMetrics();
        String nombre = personaje.getNombre();
        int textoX = (w - fm.stringWidth(nombre)) / 2;
        int textoY = nombreY + ((h - nombreY - 8) + fm.getAscent()) / 2 - 2;
        g2d.drawString(nombre, textoX, textoY);

        if (descartado) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.setColor(COLOR_DESCARTADO_OVERLAY);
            g2d.fillRoundRect(1, 1, w - 2, h - 2, 16, 16);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g2d.setColor(new Color(180, 180, 180));
            g2d.setFont(new Font("Serif", Font.ITALIC, 11));
            FontMetrics fm2 = g2d.getFontMetrics();
            String desc = "Descartado";
            g2d.drawString(desc, (w - fm2.stringWidth(desc)) / 2, h / 2);
        }

        g2d.dispose();
    }
}
