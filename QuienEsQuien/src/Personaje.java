public class Personaje {
    private String nombre;
    private String nombreImagen;
    private String genero;
    private String colorPelo;
    private String colorOjos;
    private boolean tieneGafas;
    private boolean tieneSombrero;
    private boolean tieneBarba;

    public Personaje(String nombre, String nombreImagen, String genero, String colorPelo, String colorOjos, boolean tieneGafas, boolean tieneSombrero, boolean tieneBarba) {
        this.nombre = nombre;
        this.nombreImagen = nombreImagen;
        this.genero = genero;
        this.colorPelo = colorPelo;
        this.colorOjos = colorOjos;
        this.tieneGafas = tieneGafas;
        this.tieneSombrero = tieneSombrero;
        this.tieneBarba = tieneBarba;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public String getGenero() {
        return genero;
    }

    public String getColorPelo() {
        return colorPelo;
    }

    public String getColorOjos() {
        return colorOjos;
    }

    public boolean isTieneGafas() {
        return tieneGafas;
    }

    public boolean isTieneSombrero() {
        return tieneSombrero;
    }

    public boolean isTieneBarba() {
        return tieneBarba;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' +
                ", nombreImagen='" + nombreImagen + '\'' +
                ", genero='" + genero + '\'' +
                ", colorPelo='" + colorPelo + '\'' +
                ", colorOjos='" + colorOjos + '\'' +
                ", tieneGafas=" + tieneGafas +
                ", tieneSombrero=" + tieneSombrero +
                ", tieneBarba=" + tieneBarba +
                '}';
    }
}
