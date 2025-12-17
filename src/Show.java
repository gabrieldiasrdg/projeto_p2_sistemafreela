public class Show {
    String id;
    Data dataEvento;
    Endereco enderecoEvento;
    Hora horarioInicial;
    Hora horarioFinal;
    Instrumentos instrumentos;
    String infoAdicionais;

    public static String gerarID(int ano, int mes, int dia, int horaInicio, int minutoInicio) {
        String id = String.format("%04d%02d%02d_%02d%02d", ano, mes, dia, horaInicio, minutoInicio);
        return id;
    }
}
