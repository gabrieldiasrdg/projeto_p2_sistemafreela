public class Show {
    String id;
    Data dataEvento;
    Endereco enderecoEvento;
    Hora horarioInicial;
    Hora horarioFinal;
    String infoAdicionais;

    Banda banda; //quem foi contratada pro show
    Instrumentos  instrumentos; //o que o show precisa
    Integrantes[] integrantes; //músicos que foram atribuídos

    public static String gerarID(int ano, int mes, int dia, int horaInicio, int minutoInicio) {
        String id = String.format("%04d%02d%02d_%02d%02d", ano, mes, dia, horaInicio, minutoInicio);
        return id;
    }
}
