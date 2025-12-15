public class Instrumentos {
    int quantidadeInstrumentosRequeridos;
    String[] instrumentoRequeridos = {"guitarra", "violão", "baixo","bateria", "cajon", "teclado", "percussão", "sanfona"};
    Double[] valorCache = new Double[8];
    int[] contInstrumentos = new int[8];
    boolean[] vagaPendente;
    String[] statusVaga; //"PENDENTE" ou "ID_DO_MUSICO"
}
