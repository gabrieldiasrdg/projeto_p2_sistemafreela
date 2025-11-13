import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String raiz="Freela/";
        String raizShow = raiz + "Shows/";

        char opcao;
        Scanner sc=new Scanner(System.in);

        do {
            menuPrincipal();
            opcao=sc.next().charAt(0);
            switch (opcao) {
                case '1':
                    cadastrarShow(raizShow, sc);
                    break;
                case '2':
                    String funcaoAtualizar = "atualizar";
                    listarShows(raizShow, funcaoAtualizar, sc);
                    break;
                case '3':
                    String funcaoExcluir = "excluir";
                    listarShows(raizShow,  funcaoExcluir, sc);
                    break;
                case '4':
                    String funcaoVisualizar = "visualizar";
                    listarShows(raizShow,  funcaoVisualizar, sc);
                    break;
                case '5':
                    iniciarResetar(raiz, raizShow);
                    break;
                case '6':
                    System.out.println("Saindo...");
            }
        }while(opcao!='6');


    }
    private static void cadastrarShow(String raizShow, Scanner sc) {
        Show s = new Show();
        s.dataEvento = new Data();
        s.enderecoEvento = new Endereco();
        s.horarioInicial = new Hora();
        s.horarioFinal = new Hora();
        s.instrumentos = new Instrumentos();

        //DATA
        int anoAtual = LocalDate.now().getYear();
        boolean dataValida = false;
        System.out.println("Insira a data do evento no formato **DD MM AAAA** (Ex: 10 11 2025): ");
        do {
            try {
                System.out.println("R= ");
                int dia = sc.nextInt();
                int mes = sc.nextInt();
                int ano = sc.nextInt();
                LocalDate dataEvento = LocalDate.of(ano, mes, dia);

                if (dataEvento.getYear() < anoAtual) { //Não deixa programar show pra um ano anterior do ano de criação
                    System.out.print("\n--------------------\n");
                    System.out.print("Erro: O ano deve ser igual ou superior a " + anoAtual + ".");
                    System.out.print("\n--------------------\n");
                } else if (dataEvento.isBefore(LocalDate.now())) { //Não deixa programar show pra uma data anterior da data de criação
                    System.out.print("\n--------------------\n");
                    System.out.print("Erro: A data não pode ser anterior à data de hoje.");
                    System.out.print("\n--------------------\n");
                } else {
                    s.dataEvento.dia = dia;
                    s.dataEvento.mes = mes;
                    s.dataEvento.ano = ano;
                    dataValida = true;
                }
            } catch(InputMismatchException e) { //Captura um possível dado incorreto inserido pelo usuário
                System.out.print("\n--------------------\n");
                System.out.print("Erro: Digite apenas números inteiros.");
                System.out.print("\n--------------------\n");
                sc.nextLine(); // Limpa o buffer após o erro
            } catch (java.time.DateTimeException e) { // Captura erros como "30 de Fevereiro" ou "Mês 13" (Erros de data)
                System.out.print("\n--------------------\n");
                System.out.print("Erro: Data inválida (Ex: dia não existe no mês, mês inválido, etc.).");
                System.out.print("\n--------------------\n");
                sc.nextLine(); // Limpa o buffer
            }
        } while (!dataValida);

        System.out.println();

        //HORÁRIOS EVENTO
        boolean horarioValido = false;
        do {
            try {
                System.out.println("Insira o horário de INÍCIO do evento em **HH MM** (Ex: 12 00)): ");
                int horaInicial = sc.nextInt();
                int minutoInicial = sc.nextInt();
                System.out.println("Insira o horário de FINAL do evento em **HH MM** (Ex: 15 00)): ");
                int horaFinal = sc.nextInt();
                int minutoFinal = sc.nextInt();

                LocalTime inicio = LocalTime.of(horaInicial, minutoInicial);
                LocalTime fim = LocalTime.of(horaFinal, minutoFinal);

                if(fim.isBefore(inicio) || fim.equals(inicio)) { //Erros lógicos entre os horários
                    System.out.print("\n--------------------\n");
                    System.out.print("Erro: O horário de término deve ser DEPOIS do horário de início.");
                    System.out.print("\n--------------------\n");
                } else {
                    s.horarioInicial.hora = horaInicial;
                    s.horarioInicial.minuto = minutoInicial;
                    s.horarioFinal.hora = horaFinal;
                    s.horarioFinal.minuto = minutoFinal;
                    horarioValido = true;
                    sc.nextLine(); //limpar o buffer
                }
            } catch (InputMismatchException e) { //Captura um possível dado incorreto inserido pelo usuário
                System.out.print("\n--------------------\n");
                System.out.print("Erro: Digite apenas números inteiros.");
                System.out.print("\n--------------------\n");
                sc.nextLine(); // Limpa o buffer
            } catch  (java.time.DateTimeException e) { //Captura erros de formato/lógica no horário
                System.out.print("\n--------------------\n");
                System.out.print("Erro de Horário: Horas devem ser entre 0 e 23, minutos entre 0 e 59.");
                System.out.print("\n--------------------\n");
            }
        } while(!horarioValido);

        System.out.println();

        //ENDEREÇO
        boolean enderecoValido = false;
        System.out.println("- Informações sobre o local onde ocorrerá o evento -");
        do {
            try {
                System.out.print("Cidade: ");
                s.enderecoEvento.cidade = sc.nextLine().trim();
                while (s.enderecoEvento.cidade.isEmpty()) { //Se estiver vazio
                    System.out.print("Cidade não pode ser vazia. Insira novamente: ");
                    s.enderecoEvento.cidade = sc.nextLine().trim();
                }

                System.out.print("Bairro: ");
                s.enderecoEvento.bairro = sc.nextLine().trim();
                while (s.enderecoEvento.bairro.isEmpty()) {//Se estiver vazio
                    System.out.print("Bairro não pode ser vazio. Insira novamente: ");
                    s.enderecoEvento.bairro = sc.nextLine().trim();
                }

                System.out.print("Logradouro: ");
                s.enderecoEvento.logradouro = sc.nextLine().trim();
                while (s.enderecoEvento.logradouro.isEmpty()) {//Se estiver vazio
                    System.out.print("Logradouro não pode ser vazio. Insira novamente: ");
                    s.enderecoEvento.logradouro = sc.nextLine().trim();
                }

                System.out.print("Número: ");
                s.enderecoEvento.numero = sc.nextInt();
                sc.nextLine(); // limpar buffer

                System.out.print("Complemento (opcional): ");
                s.enderecoEvento.complemento = sc.nextLine();
                enderecoValido = true;
            }catch (InputMismatchException e) { //Captura um possível dado incorreto inserido pelo usuário
                System.out.print("\n--------------------\n");
                System.out.print("Erro: Digite apenas números inteiros na parte de 'NÚMERO'.");
                System.out.print("\n--------------------\n");
                sc.nextLine(); // Limpa o buffer
            }
        }while (!enderecoValido);

        System.out.println();

        //INSTRUMENTOS
        double[] salvaCache = new double[8]; //Pra usar o mesmo cachê caso repita o instrumento

        System.out.println("Insira a quantidade de instrumentistas a serem contratados(Limite de 8 intrumentos): ");
        do {
        s.instrumentos.quantidadeInstrumentos = sc.nextInt();
        } while (s.instrumentos.quantidadeInstrumentos<1 || s.instrumentos.quantidadeInstrumentos>8);

        int op = 0;

        for (int i = 0; i < s.instrumentos.quantidadeInstrumentos; i++) { //Vai rodar até completar a quantidade de instrumentos solicitado
            System.out.printf("Insira %dº instrumento requerido na lista: \n", i+1);
            do {
                try {
                    menuInstrumentos(); //Lista os instrumentos disponíveis
                    op = sc.nextInt();
                    sc.nextLine(); // Limpa o buffer
                }catch (InputMismatchException e) {
                    System.out.print("\n--------------------\n");
                    System.out.print("Erro: Digite apenas números inteiros.");
                    System.out.print("\n--------------------\n");
                    sc.nextLine(); // Limpa o buffer
                }
            } while (op < 1 || op > 8);

            int idx = op - 1;
            boolean cacheValido = false; // Valor do cachê

            while (!cacheValido) {
                if (salvaCache[idx] > 0) {// Se o instrumento já foi usado, reaproveita o cache
                    s.instrumentos.valorCache[idx] = salvaCache[idx];
                    cacheValido = true;
                } else { // Novo instrumento pergunta cache
                    System.out.printf("Insira o valor do cachê para o(a) %s: R$ ", s.instrumentos.instrumentosMusicais[idx]);
                    s.instrumentos.valorCache[idx] = sc.nextDouble();
                    if (s.instrumentos.valorCache[idx] < 150) {
                        System.out.println("\n!! O cachê mínimo é R$150,00 !!\n");
                    } else {
                        cacheValido = true;
                    }
                }
            }

            salvaCache[idx] = s.instrumentos.valorCache[idx]; // Armazena o cache para uso futuro
            s.instrumentos.numeroDeInstrumentos[idx]++;// Incrementa quantidade daquele instrumento específico

        }

        System.out.println();

        //INFORMAÇÕES ADICIONAIS
        System.out.println("INSIRA AS INFORMAÇÕES ADICIONAIS SOBRE O SHOW(OPCIONAL): ");
        sc.nextLine(); // limpar o buffer
        s.infoAdicionais = sc.nextLine();

        System.out.println();

        //CRIANDO ARQUIVO
        s.id = gerarID(s.dataEvento.ano, s.dataEvento.mes, s.dataEvento.dia, s.horarioInicial.hora, s.horarioInicial.minuto);
        if (criarArquivo(raizShow, s)) {
            System.out.println("Show cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao gravar o arquivo do show.");
        }
    }

    private static void atualizarShow(String raizShow, File[] arquivos, Scanner sc) {
        int opShow = -1;

        do { //Escolha do show
            System.out.print("Digite o número do show que deseja atualizar: ");
            opShow = sc.nextInt() - 1;
            sc.nextLine(); // limpar buffer
            if (opShow < 0 || opShow >= arquivos.length) {
                System.out.println("Número inválido! Tente novamente.\n");
            }
        } while (opShow < 0 || opShow >= arquivos.length);

        imprimirArquivo(raizShow, arquivos[opShow].getName());      //Mostra o conteúdo atual

        String op;//Confirma atualização

        do {
            System.out.print("Deseja realmente atualizar este show? (S/N): ");
            op = sc.nextLine().toUpperCase();
        } while (!op.equals("S") && !op.equals("N"));

        if (op.equals("N")) {
            System.out.println("Atualização cancelada.\n");
            return;
        }

        if (arquivos[opShow].delete()) { //Apaga o arquivo antigo
            System.out.println("Arquivo antigo removido com sucesso!");
        } else {
            System.out.println("Erro ao remover o arquivo antigo.\n");
            return;
        }

        //Cadastra novamente (gera novo ID e arquivo atualizado)
        System.out.println("\n--- INSIRA OS NOVOS DADOS DO SHOW ---\n");
        cadastrarShow(raizShow, sc);

        System.out.println("Show atualizado com sucesso!\n");
    }


    private static void excluirShow(File[] arquivos, Scanner sc){
        String op = "";
        int opShow = 0;

        System.out.println("Digite o número do show que você deseja excluir: ");
        do {
            opShow = sc.nextInt() - 1;
            sc.nextLine(); // limpar o buffer
        }while (opShow < 0 || opShow >= arquivos.length);

        System.out.printf("Tem certeza que deseja excluir o show '%s'? (S/N)\n", arquivos[opShow].getName());
        op = lerSN(sc);
        if (op.equals("S")) {
            if (arquivos[opShow].delete()) {
                System.out.println("Show excluído com sucesso!\n");
            } else {
                System.out.println("Erro ao tentar excluir o show.\n");
            }
        } else {
            System.out.println("Exclusão cancelada.\n");
        }
    }

    private static void listarShows(String raizShow, String funcao, Scanner sc) {
        boolean existe;
        File dir = new File(raizShow);
        String op = "";

        existe = existeArquivo(dir);

        if (!existe) {
            System.out.println("Nenhum show cadastrado ainda!");
        } else {
            File[] arquivos = dir.listFiles();
            listarArquivos(arquivos);
            if (funcao.equals("visualizar")) {
                visualizarShows(raizShow, arquivos, sc);
            } else if (funcao.equals("excluir")) {
                excluirShow(arquivos, sc);
            } else if (funcao.equals("atualizar")) {
                atualizarShow(raizShow, arquivos, sc);
            }

            System.out.println("Voltando ao menu principal...\n");

        }
    }

    private static String visualizarShows(String raizShow, File[] arquivos, Scanner sc) {
        String op = "";
        int opShow = -1; // Inicializa com valor inválido
        System.out.println("Deseja visualizar algum show? (S/N)");
        sc.nextLine(); //LIMPA BUFFER
        op = lerSN(sc);
        if (op.equals("S")) {
            do {
                System.out.println("Digite o número do show que deseja visualizar (1 a " + arquivos.length + "): ");
                try { // Tenta ler o inteiro com tratamento de erro
                    opShow = sc.nextInt() - 1;
                    sc.nextLine(); // Limpar o buffer
                } catch (java.util.InputMismatchException e) {
                    System.out.println("ERRO: Entrada inválida. Digite apenas números.");
                    sc.nextLine(); // Limpa a linha inteira, incluindo o buffer
                    opShow = -1; // Força a repetição do loop
                }
                if (opShow < 0 || opShow >= arquivos.length) {
                    System.out.println("Número fora do intervalo válido.");
                }
            } while (opShow < 0 || opShow >= arquivos.length);
            imprimirArquivo(raizShow, arquivos[opShow].getName());
            return op;
        } else {
            return op;
        }
    }

    private static void listarArquivos(File[] arquivos) {
        System.out.println("Shows cadastrados: ");
        for (int i = 0; i < arquivos.length; i++) {
            System.out.printf("%d) %s\n", i + 1, arquivos[i].getName());
        }
    }

    private static boolean existeArquivo(File dir) {
        return dir.exists() && dir.listFiles().length > 0;
    }

    private static void imprimirArquivo(String raizShow, String nomeArquivo) {
        try {
            System.out.println("\n-------------------------");
            System.out.println("Show: " + nomeArquivo);
            Scanner leitor = new Scanner(new File(raizShow+nomeArquivo));
            while (leitor.hasNextLine()) {
                System.out.println(leitor.nextLine());
            }
            leitor.close();
            System.out.println("\n-------------------------");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + nomeArquivo);
        }
    }

    private static boolean criarArquivo(String raizShow, Show s) {
        File f = new File(raizShow + s.id + ".txt");
        if (f.exists()) {
            System.out.println("Já existe um show cadastrado nesse horário!");
            return false;
        }
        try {
            PrintWriter pw = new PrintWriter(raizShow + s.id + ".txt");
            pw.append("\n=== DETALHES DO SHOW ===\n");
            pw.append("Id: " + s.id + "\n");
            pw.append("Data do evento: "+s.dataEvento.dia+"/"+s.dataEvento.mes+"/"+s.dataEvento.ano+"\n");
            pw.append("Carga horária de show (Início/Fim): "+s.horarioInicial.hora+"h"+s.horarioInicial.minuto+"min"+" - "+s.horarioFinal.hora+"h"+s.horarioFinal.minuto+"min"+"\n");
            pw.append("Informações do endereço onde ocorrerá o evento: \n");
            pw.append("- Cidade: "+s.enderecoEvento.cidade+"\n");
            pw.append("- Endereço: "+s.enderecoEvento.bairro+", "+s.enderecoEvento.logradouro+", Nº "+s.enderecoEvento.numero+"\n");
            pw.append("- Complemento: "+s.enderecoEvento.complemento+"\n");
            pw.append("Instrumentos requeridos: "+"\n");
            for(int i = 0; i < s.instrumentos.numeroDeInstrumentos.length; i++) {
                if(s.instrumentos.numeroDeInstrumentos[i]>0) {
                    if (s.instrumentos.numeroDeInstrumentos[i]>1){
                        for (int j = 0; j < s.instrumentos.numeroDeInstrumentos[i] ; j++) {
                            pw.append(String.format("- %s %d: Pendente | R$%.2f%n", s.instrumentos.instrumentosMusicais[i], j+1, s.instrumentos.valorCache[i]));
                        }
                    } else {
                        pw.append(String.format("- %s: Pendente | R$%.2f%n", s.instrumentos.instrumentosMusicais[i], s.instrumentos.valorCache[i]));
                    }
                }
            }
            pw.append("Informações adicionais: "+ s.infoAdicionais+"\n");
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String lerSN(Scanner sc) {
        String op;
        do {
            System.out.print("R= ");
            op = sc.nextLine().toUpperCase();
        } while (!op.equals("S") && !op.equals("N"));
        return op;
    }

    private static String gerarID(int ano, int mes, int dia, int horaInicio, int minutoInicio) {
        String id = String.format("%04d%02d%02d_%02d%02d", ano, mes, dia, horaInicio, minutoInicio);
        return id;
    }

    private static void iniciarResetar(String raiz, String raizShow) {
        File dir = new File(raiz);
        if(!dir.exists()){ //cria a pasta Freela, se não existir
            dir.mkdir();
        }
        dir = new File(raizShow);
        if(!dir.exists()) { //cria a pasta Shows, se não existir
            dir.mkdir();
        }
        else {
            apagarArquivos(dir);
        }

    }

    private static void apagarArquivos(File dir) {
        String[] arquivos = dir.list();
        for (String arquivo : arquivos) {
            File f = new File(dir, arquivo);
            f.delete();
        }
    }

    private static void menuInstrumentos() {
        System.out.println(	"\n-----------------------"
                + "\n1) Guitarra"
                + "\n2) Violão"
                + "\n3) Baixo"
                + "\n4) Bateria"
                + "\n5) Cajon"
                + "\n6) Teclado"
                + "\n7) Percussão"
                + "\n8) Sanfona"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }

    private static void menuPrincipal() {
        System.out.println(	"\n-----------------------"
                + "\n1) Cadastrar novo show"
                + "\n2) Atualizar show"
                + "\n3) Excluir show"
                + "\n4) Visualizar shows pendentes"
                + "\n5) Iniciar/Resetar"
                + "\n6) Sair"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }
}