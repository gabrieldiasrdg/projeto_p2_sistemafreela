import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String raiz="Freela/";
        String raizShow = raiz + "Shows/";
        String raizBanda = raiz + "Bandas/";
        String raizMusico = raiz + "Musicos/";

        char opcao;
        Scanner sc=new Scanner(System.in);

        do {
            menuPrincipal();
            opcao = sc.next().charAt(0);

            switch (opcao) {

                case '1': // Cadastrar Banda/Artista
                    cadastrarBanda(raizBanda, sc);
                    break;
                case '2': // Cadastrar Show
                    cadastrarShow(raizShow, sc);
                    break;
                case '3': // Cadastrar Músico
                    cadastrarMusico(raizMusico, sc);
                    break;
                case '4': // Registrar Músico em algum show
                    String registrarMusico = "registrar";
                    listarShows(raizShow, raizMusico, registrarMusico, sc);
                    break;
                case '5': // Excluir Show/Músico/Banda
                   menuExclusao(raizShow, raizBanda, raizMusico, sc);
                    break;
                case '6': // Listar Shows
                    String vizaulizarShows = "visualizar";
                    listarShows(raizShow, raizBanda, vizaulizarShows, sc);
                    break;
                case '7': // Listar Músicos
                    listarMusicos(raizMusico, sc);
                    break;
                case '8': // Listar Bandas
                    listarBandas(raizBanda, sc);
                    break;
                case '9': // Iniciar/Resetar
                    iniciarResetar(raiz, raizShow, raizBanda, raizMusico);
                    break;

                case '0': // Sair
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

        } while (opcao != '0');
    }

    private static void listarBandas(String raizBanda, Scanner sc) {
        File dir = new File(raizBanda);
        File[] arquivos = dir.listFiles();

        if (!existeArquivo(dir)) {
            System.out.println("Não há bandas cadastrados!");
        } else {
            int op = 0;
            System.out.println("Selecione a banda que você deseja vizualizar: ");
            listarArquivos(arquivos);//Lista as bandas
            do {
                System.out.println("Opção: ");
                op = sc.nextInt() - 1;
                if (op < 0 || op > arquivos.length - 1) {
                    System.out.println("Opção inválida!");
                }
            } while (op < 0 || op > arquivos.length - 1);
            String nomeArquivo = arquivos[op].getName();
            imprimirArquivo(raizBanda, nomeArquivo);
        }
    }

    private static void listarMusicos(String raizMusico, Scanner sc) {
        File dir = new File(raizMusico);
        File[] arquivos = dir.listFiles();

        if (!existeArquivo(dir)) {
            System.out.println("Não há músicos cadastrados!");
        } else {
            int op = 0;
            System.out.println("Selecione a músico que você deseja vizualizar: ");
            listarArquivos(arquivos); //Lista os músicos
            do {
                System.out.println("Opção: ");
                op = sc.nextInt() - 1;
                if (op < 0 || op > arquivos.length - 1) {
                    System.out.println("Opção inválida!");
                }
            } while (op < 0 || op > arquivos.length - 1);
            String nomeArquivo = arquivos[op].getName();
            imprimirArquivo(raizMusico, nomeArquivo);
        }
    }

    private static void menuExclusao(String raizShow, String raizBanda, String raizMusico, Scanner sc) {
        int op = 0;
        String funcao = "";
        do {
            System.out.println(	"\n-----------------------"
                    + "\n1) Excluir Show"
                    + "\n2) Excluir Músico"
                    + "\n3) Excluir Banda"
                    + "\n-----------------------");
            System.out.println("Opção: ");
            op = sc.nextInt();
            if (op < 1 || op > 3){
                System.out.println("Opção inválida!");
            }
        } while (op < 1 || op > 3);

        if(op == 1) {
            funcao = "excluirShow";
            listarShows(raizShow, raizMusico, funcao, sc);
        } else if (op == 2) {
            File dir = new File(raizMusico);
            File[] arquivos = dir.listFiles();

            if (!existeArquivo(dir)) {
                System.out.println("Não há músicos cadastrados!");
            } else {
                int opMusico = 0;
                System.out.println("Selecione o músico que você deseja excluir: ");
                listarArquivos(arquivos);//Lista os musicos
                do {
                    System.out.println("Opção: ");
                    opMusico = sc.nextInt() - 1;
                    if (opMusico < 0 || opMusico > arquivos.length - 1) {
                        System.out.println("Opção inválida!");
                    }
                } while (opMusico < 0 || opMusico > arquivos.length - 1);
                excluirMusicoBanda(arquivos, opMusico, sc);
            }
        } else if (op == 3) {
            File dir = new File(raizBanda);
            File[] arquivos = dir.listFiles();

            if (!existeArquivo(dir)) {
                System.out.println("Não há bandas cadastrados!");
            } else {
                int opBanda = 0;
                System.out.println("Selecione a banda que você deseja excluir: ");
                listarArquivos(arquivos);//Lista as bandas
                do {
                    System.out.println("Opção: ");
                    opBanda = sc.nextInt() - 1;
                    if (opBanda < 0 || opBanda > arquivos.length - 1) {
                        System.out.println("Opção inválida!");
                    }
                } while (opBanda < 0 || opBanda > arquivos.length - 1);
                excluirMusicoBanda(arquivos, opBanda, sc);
            }
        }

    }

    private static void excluirMusicoBanda(File[] arquivos, int opMusico, Scanner sc) {
        System.out.printf("Tem certeza que deseja excluir '%s'? (S/N)\n", arquivos[opMusico].getName());
        sc.nextLine();
        String op = lerSN(sc);
        if (op.equals("S")) {
            if (arquivos[opMusico].delete()) {
                System.out.println("Músico excluído com sucesso!\n");
            } else {
                System.out.println("Erro ao tentar excluir o músico.\n");
            }
        } else {
            System.out.println("Exclusão cancelada.\n");
        }
    }


    private static void cadastrarBanda(String raizBanda, Scanner sc) {
        sc.nextLine(); //Limpar buffer
        Banda b = new Banda();

        //Dados da banda
        System.out.println("Insira o nome da banda: ");
        b.nome = sc.nextLine();
        do {
            System.out.println("Insira o cnpj da banda: ");
            b.cnpj = sc.nextLine();
            b.cnpj = b.cnpj.replaceAll("\\D", "");//Remove tudo que não for número
            if (b.cnpj.length() != 14) {
                System.out.println("ERRO! O CNPJ deve conter 14 digitos inteiros!");
            }
        } while (b.cnpj.length() != 14);

        //Formatar CNPJ e gerar ID
        b.cnpj = Banda.formatarCnpj(b.cnpj);
        b.id = Banda.gerarIDBanda(b.nome, b.cnpj);

        // salvar arquivo
        if (salvarBanda(raizBanda, b)) {
            System.out.println("Banda cadastrada com sucesso!");
        } else {
            System.out.println("Erro ao gravar o arquivo do banda.");
        }
    }

    private static void cadastrarMusico(String raizMusico, Scanner sc) {
        sc.nextLine(); //Limpar buffer
        Musico m = new Musico();

        //Dados do musico

        //NOME
        System.out.println("Insira o nome do músico: ");
        m.nome = sc.nextLine();

        //Quantidade de instrumentos que o músico toca
        boolean ehInteiro = false;//Validar entrada
        do {
            try {
                do {
                    System.out.println("Insira a quantidade de instrumentos que "+m.nome+" toca: ");
                    m.nInstrumentosToca = sc.nextInt();
                    ehInteiro = true;
                    if (m.nInstrumentosToca > 8 || m.nInstrumentosToca < 1) {
                        System.out.println("ERRO: Insira um valor válido! (Entre 1 a 8)");
                    }
                } while (m.nInstrumentosToca > 8 || m.nInstrumentosToca <1);
            } catch (InputMismatchException e) {
                System.out.print("\n--------------------\n");
                System.out.print("Erro: Digite apenas números inteiros.");
                System.out.print("\n--------------------\n");
                sc.nextLine(); // Limpa o buffer após o erro
            }
        }while (!ehInteiro);

        System.out.println();

        //Atribuir os instrumentos ao músico
        Instrumentos inst =  new Instrumentos();
        m.instrumentoDoMusico = new String[m.nInstrumentosToca];
        int op = 0;
        boolean concluido = false;
        do {
            boolean[] jaEscolhido = new boolean[8];
            try {
                for (int i = 0; i < m.nInstrumentosToca; i++) {
                    boolean escolhidoValido = false;
                    while (!escolhidoValido) {
                        System.out.printf("Insira o %dº instrumento que %s toca:%n", i + 1, m.nome);
                        menuInstrumentos();
                        op = sc.nextInt();
                        if (op < 1 || op > 8) {
                            System.out.println("ERRO: Digite um valor entre 1 e 8!");
                            continue; //repete o loop do zero
                        }
                        if (jaEscolhido[op - 1]) {
                            System.out.println("Instrumento já selecionado! Escolha outro.");
                            continue; //repete o loop do zero
                        }
                        // Se chegou aqui: instrumento válido e ainda não escolhido
                        m.instrumentoDoMusico[i] = inst.instrumentoRequeridos[op - 1];
                        jaEscolhido[op - 1] = true;
                        escolhidoValido = true;
                    }
                }
                concluido = true;
            } catch (InputMismatchException e) {
                System.out.print("\n--------------------\n");
                System.out.print("Erro: Digite apenas números inteiros.");
                System.out.print("\n--------------------\n");
                sc.nextLine(); // Limpa o buffer após o erro
            }
        } while (!concluido);

        //Inserir o número de registro
        String registroParaTeste = "";
        concluido = false;
        do {
            try {
                do {
                    System.out.printf("Insira o número de registro de %s: %n", m.nome);
                    m.registro = sc.nextInt();
                    registroParaTeste = String.valueOf(m.registro);
                    if (Musico.existeRegistro(registroParaTeste, raizMusico)) {
                        System.out.println("Este número de registro já existe! Insira um outro!");
                    }
                    if (m.registro < 0) {
                        System.out.println("O registro não pode ser negativo.");
                    }
                } while (Musico.existeRegistro(registroParaTeste, raizMusico) || m.registro < 0);
                concluido = true;
            } catch (InputMismatchException e) {
                System.out.print("\n--------------------\n");
                System.out.print("Erro: Digite apenas números inteiros.");
                System.out.print("\n--------------------\n");
                sc.nextLine(); //limpa buffer
            }
        } while (!concluido);

        //Gerar ID
        String nomeSemEspaco = m.nome.trim().replaceAll("\\s+", "_");
        m.id = Musico.gerarIDMusico(nomeSemEspaco, m.registro);

        //salvar arquivo
        if (salvarMusico(raizMusico, m)) {
            System.out.println("Músico cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao gravar o arquivo do músico.");
        }
    }

    private static void cadastrarShow(String raizShow, Scanner sc) {
        File pasta = new File("Freela/Bandas/");
        if (pasta.listFiles().length == 0) {
            System.out.println("Você precisa ter bandas cadastradas antes de tentar cadastrar algum show!");
            return;
        }
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
        s.instrumentos.quantidadeInstrumentosRequeridos = sc.nextInt();
        } while (s.instrumentos.quantidadeInstrumentosRequeridos<1 || s.instrumentos.quantidadeInstrumentosRequeridos>8);

        s.instrumentos.vagaPendente = new boolean[s.instrumentos.quantidadeInstrumentosRequeridos]; //SHOW NASCE COM VAGAS PENDENTES
        s.instrumentos.statusVaga   = new String[s.instrumentos.quantidadeInstrumentosRequeridos]; //SHOW NASCE COM VAGAS PENDENTES

        for (int i = 0; i < s.instrumentos.quantidadeInstrumentosRequeridos; i++) {
            s.instrumentos.vagaPendente[i] = true;
            s.instrumentos.statusVaga[i] = "PENDENTE";
        }

        int op = 0;

        for (int i = 0; i < s.instrumentos.quantidadeInstrumentosRequeridos; i++) { //Vai rodar até completar a quantidade de instrumentos solicitado
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
                    System.out.printf("Insira o valor do cachê para o(a) %s: R$ ", s.instrumentos.instrumentoRequeridos[idx]);
                    s.instrumentos.valorCache[idx] = sc.nextDouble();
                    sc.nextLine(); // limpa o ENTER
                    if (s.instrumentos.valorCache[idx] < 150) {
                        System.out.println("\n!! O cachê mínimo é R$150,00 !!\n");
                    } else {
                        cacheValido = true;
                    }
                }
            }

            salvaCache[idx] = s.instrumentos.valorCache[idx]; // Armazena o cache para uso futuro
            s.instrumentos.contInstrumentos[idx]++;// Incrementa quantidade daquele instrumento específico

        }

        System.out.println();

        //INFORMAÇÕES ADICIONAIS
        System.out.println("INSIRA AS INFORMAÇÕES ADICIONAIS SOBRE O SHOW(OPCIONAL): ");
        s.infoAdicionais = sc.nextLine();

        System.out.println();

        //CRIANDO ARQUIVO
        s.id = Show.gerarID(s.dataEvento.ano, s.dataEvento.mes, s.dataEvento.dia, s.horarioInicial.hora, s.horarioInicial.minuto);
        if (salvarShow(raizShow, s)) {
            System.out.println("Show cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao gravar o arquivo do show.");
        }
    }

    private static void registrarMusico(String raizShow, String raizMusico, File arquivo, Scanner sc) {

        //Mostrar show e reconstruir o objeto
        String nomeArquivo = arquivo.getName();
        imprimirArquivo(raizShow, nomeArquivo);

        Show s = recuperarInfoShow(raizShow, nomeArquivo);

        //se por algum motivo não estiver inicializado
        if (s.instrumentos.statusVaga == null || s.instrumentos.vagaPendente == null) {
            s.instrumentos.statusVaga = new String[s.instrumentos.quantidadeInstrumentosRequeridos];
            s.instrumentos.vagaPendente = new boolean[s.instrumentos.quantidadeInstrumentosRequeridos];
            for (int i = 0; i < s.instrumentos.quantidadeInstrumentosRequeridos; i++) {
                s.instrumentos.statusVaga[i] = "PENDENTE";
                s.instrumentos.vagaPendente[i] = true;
            }
        }

        //Montar as opções (somente vagas pendentes) e mapear opção -> slot real
        int opcaoExibida = 0; //é o índice REAL da vaga no array do show.
        int slotGlobal = 0; //é o índice REAL da vaga no array do show.

        String[] salvaInstrumentoOpcao = new String[s.instrumentos.quantidadeInstrumentosRequeridos]; //instrumento mostrado naquela opção
        Double[] salvaCacheOpcao = new Double[s.instrumentos.quantidadeInstrumentosRequeridos]; //salva o cache
        int[] salvaSlotRealOpcao = new int[s.instrumentos.quantidadeInstrumentosRequeridos]; //qual o slot real no array do show

        System.out.println("\nEscolha a vaga pendente que deseja preencher:");

        for (int tipo = 0; tipo < s.instrumentos.instrumentoRequeridos.length; tipo++) {
            int qtd = s.instrumentos.contInstrumentos[tipo];
            for (int j = 0; j < qtd; j++) {
                if (slotGlobal >= s.instrumentos.quantidadeInstrumentosRequeridos) break;
                if (s.instrumentos.vagaPendente[slotGlobal]) {
                    double cache = (s.instrumentos.valorCache[tipo] != null) ? s.instrumentos.valorCache[tipo] : 0.0;
                    System.out.printf("%d) %s %d: PENDENTE | R$%.2f%n", (opcaoExibida + 1), s.instrumentos.instrumentoRequeridos[tipo], (j + 1), cache);
                    salvaInstrumentoOpcao[opcaoExibida] = s.instrumentos.instrumentoRequeridos[tipo];
                    salvaCacheOpcao[opcaoExibida] = cache;
                    salvaSlotRealOpcao[opcaoExibida] = slotGlobal;
                    opcaoExibida++;
                }
                slotGlobal++;
            }
        }

        if (opcaoExibida == 0) {
            System.out.println("Nenhuma vaga pendente neste show. Tudo já foi preenchido.");
            return;
        }

        //Escolher a vaga (pela opção exibida)
        int escolhaVaga = -1;
        do {
            try {
                System.out.print("R= ");
                escolhaVaga = sc.nextInt() - 1;

                if (escolhaVaga < 0 || escolhaVaga >= opcaoExibida) {
                    System.out.println("Insira um número válido (entre 1 e " + opcaoExibida + ").");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: digite apenas números inteiros.");
                sc.nextLine();
                escolhaVaga = -1;
            }
        } while (escolhaVaga < 0 || escolhaVaga >= opcaoExibida);

        int slotSelecionado = salvaSlotRealOpcao[escolhaVaga];
        String instrumentoEscolhido = salvaInstrumentoOpcao[escolhaVaga];

        //Filtrar músicos que tocam o instrumento escolhido e escolher um
        String[] musicosFiltrados = new String[50];

        System.out.println("\nSelecione o músico que deseja atribuir:");
        int contRetorno = filtrarMusicosPorInstrumento(sc, raizMusico, s, instrumentoEscolhido, musicosFiltrados);

        //Função retorna cont começando em 1. Então a quantidade real é cont-1.
        int qtdMusicos = contRetorno - 1;

        if (qtdMusicos <= 0) {
            System.out.println("Nenhum músico registrado toca este instrumento.");
            return;
        }

        int escolhaMusico = -1;
        do {
            try {
                System.out.print("R= ");
                escolhaMusico = sc.nextInt() - 1;

                if (escolhaMusico < 0 || escolhaMusico >= qtdMusicos) {
                    System.out.println("Insira um número válido (entre 1 e " + qtdMusicos + ").");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: digite apenas números inteiros.");
                sc.nextLine();
                escolhaMusico = -1;
            }
        } while (escolhaMusico < 0 || escolhaMusico >= qtdMusicos);

        String musicoEscolhido = musicosFiltrados[escolhaMusico];

        //Atualiza o estado interno da vaga (PENDENTE -> ID do músico)
        s.instrumentos.statusVaga[slotSelecionado] = musicoEscolhido;
        s.instrumentos.vagaPendente[slotSelecionado] = false;

        //Reescrever o arquivo do show preservando o status de cada vaga
        try {
            PrintWriter pw = new PrintWriter(raizShow + s.id + ".txt");

            pw.append("\n=== DETALHES DO SHOW ===\n");
            pw.append("Id: " + s.id + "\n");
            pw.append("Data do evento: " + s.dataEvento.dia + "/" + s.dataEvento.mes + "/" + s.dataEvento.ano + "\n");

            pw.append(String.format("Carga horária de show (Início/Fim): %02dh%02dmin - %02dh%02dmin%n",
                    s.horarioInicial.hora,
                    s.horarioInicial.minuto,
                    s.horarioFinal.hora,
                    s.horarioFinal.minuto));

            pw.append("Informações do endereço onde ocorrerá o evento: \n");
            pw.append("- Cidade: " + s.enderecoEvento.cidade + "\n");
            pw.append("- Endereço: " + s.enderecoEvento.bairro + ", " + s.enderecoEvento.logradouro + ", Nº " + s.enderecoEvento.numero + "\n");
            pw.append("- Complemento: " + s.enderecoEvento.complemento + "\n");

            pw.append("Instrumentos requeridos: " + s.instrumentos.quantidadeInstrumentosRequeridos + "\n");

            int slot = 0;
            for (int tipo = 0; tipo < s.instrumentos.instrumentoRequeridos.length; tipo++) {
                int qtd = s.instrumentos.contInstrumentos[tipo];
                for (int j = 0; j < qtd; j++) {
                    if (slot >= s.instrumentos.quantidadeInstrumentosRequeridos) break;
                    double cache = (s.instrumentos.valorCache[tipo] != null) ? s.instrumentos.valorCache[tipo] : 0.0;
                    String status = s.instrumentos.statusVaga[slot]; //"PENDENTE" ou "ID_DO_MUSICO"
                    pw.append(String.format("- %s %d: %s | R$%.2f%n", s.instrumentos.instrumentoRequeridos[tipo], (j + 1), status, cache));
                    slot++;
                }
            }

            pw.append("Informações adicionais: " + s.infoAdicionais + "\n");
            pw.close();

            System.out.println("\nMúsico registrado com sucesso!");

        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo do show não encontrado.");
        }
    }

    private static Show recuperarInfoShow(String raizShow, String nomeArquivo){
        Show s = new Show();
        try {
            BufferedReader br=new BufferedReader(new FileReader(raizShow+nomeArquivo));

            br.readLine();
            br.readLine(); //Pula "=== DETALHES DO SHOW ==="

            //ID
            String linha = br.readLine();
            s.id = linha.substring(linha.indexOf(":") + 2);

            //DATA
            linha = br.readLine();
            String data = linha.substring(linha.indexOf(":") + 2);
            String[] partesData = data.split("/");
            s.dataEvento = new Data();
            s.dataEvento.dia = Integer.parseInt(partesData[0]);
            s.dataEvento.mes = Integer.parseInt(partesData[1]);
            s.dataEvento.ano = Integer.parseInt(partesData[2]);

            //HORÁRIO
            linha = br.readLine();
            String horarios = linha.substring(linha.indexOf(":") + 2);
            String[] partesHor = horarios.split("-");
            String inicio = partesHor[0].trim();
            String fim = partesHor[1].trim();
            s.horarioInicial = new Hora();
            s.horarioInicial.hora = Integer.parseInt(inicio.substring(0, inicio.indexOf("h")));
            s.horarioInicial.minuto = Integer.parseInt(inicio.substring(inicio.indexOf("h") + 1, inicio.indexOf("min")));
            s.horarioFinal = new Hora();
            s.horarioFinal.hora = Integer.parseInt(fim.substring(0, fim.indexOf("h")));
            s.horarioFinal.minuto = Integer.parseInt(fim.substring(fim.indexOf("h") + 1, fim.indexOf("min")));

            br.readLine(); //"Informações do endereço onde ocorrerá o evento:"

            //ENDEREÇO
            s.enderecoEvento = new Endereco();
            linha = br.readLine(); //CIDADE
            s.enderecoEvento.cidade = linha.substring(linha.indexOf(":") + 2).trim();
            linha = br.readLine(); //ENDEREÇO
            String endSemLabel = linha.substring(linha.indexOf(":") + 2).trim();
            int primeiraVirgula = endSemLabel.indexOf(",");
            int ultimaVirgula   = endSemLabel.lastIndexOf(",");
            s.enderecoEvento.bairro = endSemLabel.substring(0, primeiraVirgula).trim();
            s.enderecoEvento.logradouro = endSemLabel.substring(primeiraVirgula + 1, ultimaVirgula).trim();
            String numStr = endSemLabel.substring(endSemLabel.indexOf("Nº") + 2).trim(); //
            s.enderecoEvento.numero = Integer.parseInt(numStr);
            linha = br.readLine(); //COMPLEMENTO
            s.enderecoEvento.complemento = linha.substring(linha.indexOf(":") + 2).trim();

            //CAPTURAR QUANTIDADE TOTAL DE INSTRUMENTOS
            linha = br.readLine();
            int numeroDeInstrumentos = Integer.parseInt(linha.substring(linha.indexOf(":") + 1).trim());
            s.instrumentos = new Instrumentos();
            s.instrumentos.quantidadeInstrumentosRequeridos = numeroDeInstrumentos;
            s.instrumentos.vagaPendente = new boolean[numeroDeInstrumentos];
            s.instrumentos.statusVaga   = new String[numeroDeInstrumentos];


            //INSTRUMENTOS
            // INSTRUMENTOS
            int lidas = 0;
            while (lidas < numeroDeInstrumentos && (linha = br.readLine()) != null) {
                linha = linha.trim();        //pula linhas vazias
                if (linha.isEmpty()) continue; //pula cabeçalho repetido
                if (linha.startsWith("Instrumentos requeridos:")) continue;
                if (!linha.startsWith("-") || !linha.contains(":") || !linha.contains("|") || !linha.contains("R$")) { //garante que é uma linha de vaga
                    continue; // ignora qualquer linha fora do padrão
                }

                //status entre ": " e "|"
                String status = linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf("|")).trim();
                s.instrumentos.statusVaga[lidas] = status;
                s.instrumentos.vagaPendente[lidas] = status.equalsIgnoreCase("PENDENTE");

                //pega instrumento
                String antesDoDoisPontos = linha.substring(linha.indexOf("-") + 2, linha.indexOf(":")).trim();
                String instrumentoAtual = antesDoDoisPontos.substring(0, antesDoDoisPontos.lastIndexOf(" ")).trim();

                //pega cache
                int posR = linha.indexOf("R$");
                String cacheStr = linha.substring(posR + 2).trim().replace(".", "").replace(",", ".");
                double valorCache = Double.parseDouble(cacheStr);

                // atualiza contadores e cache por tipo
                for (int j = 0; j < s.instrumentos.instrumentoRequeridos.length; j++) {
                    if (s.instrumentos.instrumentoRequeridos[j].equalsIgnoreCase(instrumentoAtual)) {
                        s.instrumentos.contInstrumentos[j]++;

                        if (s.instrumentos.valorCache[j] == null || s.instrumentos.valorCache[j] == 0) {
                            s.instrumentos.valorCache[j] = valorCache;
                        }
                        break;
                    }
                }
                lidas++;
            }

            for (int i = 0; i < numeroDeInstrumentos; i++) {
                if (s.instrumentos.statusVaga[i] == null) {
                    s.instrumentos.statusVaga[i] = "PENDENTE";
                    s.instrumentos.vagaPendente[i] = true;
                }
            }

            //INFORMAÇÕES ADICIONAIS
            linha =  br.readLine();
            s.infoAdicionais = linha.substring(linha.indexOf(":") + 2).trim();

            //FINALIZA
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    private static int filtrarMusicosPorInstrumento(Scanner sc, String raizMusico, Show s,
                                                    String instrumentoDesejado, String[] musicosFiltrados) {
        File dir = new File(raizMusico);

        if (!existeArquivo(dir)) {
            System.out.println("Nenhum músico cadastrado ainda!");
            return 1; // mantém sua lógica (cont começa em 1)
        }

        File[] arquivos = dir.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum músico cadastrado ainda!");
            return 1;
        }

        int cont = 1;

        for (int i = 0; i < arquivos.length; i++) {
            File arq = arquivos[i];
            if (!arq.isFile() || !arq.getName().toLowerCase().endsWith(".txt")) continue;
            try (BufferedReader br = new BufferedReader(new FileReader(arq))) {
                String id = arq.getName();
                id = id.substring(0, id.lastIndexOf(".")); // remove .txt
                if (musicoTocaInstrumento(br, instrumentoDesejado)) {
                    System.out.println(cont + " - " + arq.getName());
                    if (cont - 1 < musicosFiltrados.length) {
                        musicosFiltrados[cont - 1] = id;
                    }
                    cont++;
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + arq.getName());
            }
        }

        return cont; // mantém seu padrão (quantidade real = cont - 1)
    }

    private static boolean musicoTocaInstrumento(BufferedReader br, String instrumentoDesejado) {
        try {
            // pula linha em branco inicial
            br.readLine();

            // pula "=== DETALHES DO MÚSICO ==="
            br.readLine();

            // pula "Id:"
            br.readLine();

            // pula "Nome do Músico: ..."
            br.readLine();

            // lê "Instrumentos de competência do músico: X"
            String linha = br.readLine();
            if (linha == null) return false;
            int n = Integer.parseInt(linha.substring(linha.indexOf(":") + 1).trim());
            for (int i = 0; i < n; i++) {
                linha = br.readLine();
                if (linha == null) return false; // arquivo acabou antes do esperado
                if (linha.trim().equalsIgnoreCase(instrumentoDesejado)) {
                    return true;
                }
            }

        } catch (IOException | NumberFormatException e) {
            // Se o arquivo estiver quebrado/fora do padrão, apenas considera que não toca.
            return false;
        }

        return false;
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

    private static void listarShows(String raizShow, String raizMusico, String funcao, Scanner sc) {
        boolean existe;
        File dir = new File(raizShow);
        File dirMusicos =  new File(raizMusico);
        int op = 0;

        existe = existeArquivo(dir);
        if (!existe) {
            System.out.println("Nenhum show cadastrado ainda!");
        } else {
            File[] arquivos = dir.listFiles();
            System.out.println("Shows cadastrados: ");
            listarArquivos(arquivos);
            if (funcao.equals("visualizar")) {
                visualizarShows(raizShow, arquivos, sc);
            } else if (funcao.equals("excluirShow")) {
                excluirShow(arquivos, sc);
            } else if (funcao.equals("registrar")) {
                if(!existeArquivo(dirMusicos)) {
                    System.out.println("---------------------");
                    System.out.println("Você precisa registrar músicos antes de atribuí-los aos shows!");
                    System.out.println("---------------------");
                    return;
                } else {
                    do {
                        System.out.println("Insira o número correspondente ao show você deseja registrar o músico: ");
                        op = sc.nextInt() - 1;
                    } while (op < 0 || op >= arquivos.length);
                    registrarMusico(raizShow, raizMusico, arquivos[op], sc);
                }
                return;
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
        for (int i = 0; i < arquivos.length; i++) {
            System.out.printf("%d) %s\n", i + 1, arquivos[i].getName());
        }
    }

    private static boolean existeArquivo(File dir) {
        return dir.exists() && dir.listFiles().length > 0;
    }

    private static void imprimirArquivo(String raizArquivo, String nomeArquivo) {
        try {
            System.out.println("\n-------------------------");
            System.out.println(nomeArquivo);
            Scanner leitor = new Scanner(new File(raizArquivo+nomeArquivo));
            while (leitor.hasNextLine()) {
                System.out.println(leitor.nextLine());
            }
            leitor.close();
            System.out.println("\n-------------------------");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + nomeArquivo);
        }
    }

    private static boolean salvarBanda(String raizBanda, Banda b) {
        File pasta = new File(raizBanda);
        if (!pasta.exists()) {
            pasta.mkdirs(); //cria diretórios necessários
        }

        File f = new File(pasta, b.id + ".txt");

        if (f.exists()) { //TESTA SE O NOME INTEIRO DA BANDA JA EXISTE
            System.out.println("Essa banda ja está cadastrada!");
            return false;
        }

        File[] arquivos = pasta.listFiles(); //VETOR DAS BANDAS QUE JÁ EXISTEM
        for (int i = 0; i < arquivos.length; i++) { //Percorre todas as bandas
            File arquivosExistentes = arquivos[i]; //Captura cada arquivo das bandas
            String nome = arquivosExistentes.getName(); //Captura o nome do arquivo(id da banda) existente
            String parteDoId = nome.substring(0, nome.indexOf("_")); //Captura o NOME da banda
            if(parteDoId.equals(b.nome)) { //Testa se é igual a atual
                System.out.println("Já foi cadastrada uma banda com este nome!");
                return false;
            }
            parteDoId = nome.substring(nome.indexOf("_") + 1, nome.lastIndexOf(".")); //Captura o CNPJ da banda existente
            if(parteDoId.equals(b.cnpj)) { //Testa se é igual a atual
                System.out.println("Este CNPJ já foi cadastrado em uma outra banda!!");
                return false;
            }
        }

        try { //Se passou pelos testes grava tudo no arquivo
            PrintWriter pw = new PrintWriter(f);
            pw.append("\n=== DETALHES DA BANDA ===\n");
            pw.append("Id: " + b.id + "\n");
            pw.append("Nome da banda: "+b.nome+"\n");
            pw.append("CNPJ: "+b.cnpj+"\n");
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo não encontrado.");
            return false;
        }
    }

    private static boolean salvarMusico(String raizMusico, Musico m) {
        File pasta = new File(raizMusico);
        if (!pasta.exists()) {
            pasta.mkdirs(); //cria diretórios necessários
        }

        File f = new File(pasta, m.id + ".txt");

        try {
            PrintWriter pw = new PrintWriter(f);
            pw.append("\n=== DETALHES DO MÚSICO ===\n");
            pw.append("Id: " + m.id + "\n");
            pw.append("Nome do Músico: "+m.nome+"\n");
            pw.append("Instrumentos de competência do músico: "+m.nInstrumentosToca+"\n");
            for (int i = 0; i < m.nInstrumentosToca; i++) {
                pw.append(m.instrumentoDoMusico[i]+"\n");
            }
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo não encontrado.");
            return false;
        }
    }

    private static boolean salvarShow(String raizArquivo, Show s) {
            File f = new File(raizArquivo + s.id + ".txt");
            if (f.exists()) {
                System.out.println("Já existe um show cadastrado nesse horário!");
                return false;
            }
            try {
                PrintWriter pw = new PrintWriter(raizArquivo + s.id + ".txt");
                pw.append("\n=== DETALHES DO SHOW ===\n");
                pw.append("Id: " + s.id + "\n");
                pw.append("Data do evento: "+s.dataEvento.dia+"/"+s.dataEvento.mes+"/"+s.dataEvento.ano+"\n");
                pw.append(String.format("Carga horária de show (Início/Fim): %02dh%02dmin - %02dh%02dmin\n",
                        s.horarioInicial.hora,
                        s.horarioInicial.minuto,
                        s.horarioFinal.hora,
                        s.horarioFinal.minuto));
                pw.append("Informações do endereço onde ocorrerá o evento: \n");
                pw.append("- Cidade: "+s.enderecoEvento.cidade+"\n");
                pw.append("- Endereço: "+s.enderecoEvento.bairro+", "+s.enderecoEvento.logradouro+", Nº "+s.enderecoEvento.numero+"\n");
                pw.append("- Complemento: "+s.enderecoEvento.complemento+"\n");
                pw.append("Instrumentos requeridos: "+s.instrumentos.quantidadeInstrumentosRequeridos+"\n");
                int slot = 0;
                for (int tipo = 0; tipo < s.instrumentos.instrumentoRequeridos.length; tipo++) {
                    if (s.instrumentos.contInstrumentos[tipo] > 0) {
                        for (int j = 0; j < s.instrumentos.contInstrumentos[tipo]; j++) {
                            String status = "PENDENTE";
                            if (s.instrumentos.statusVaga != null && slot < s.instrumentos.statusVaga.length) {
                                status = s.instrumentos.statusVaga[slot];
                            }
                            double cache = (s.instrumentos.valorCache[tipo] != null) ? s.instrumentos.valorCache[tipo] : 0.0;
                            pw.append(String.format("- %s %d: %s | R$%.2f%n",
                                    s.instrumentos.instrumentoRequeridos[tipo],
                                    j + 1,
                                    status,
                                    cache));
                            slot++;
                        }
                    }
                }
                pw.append("Informações adicionais: "+ s.infoAdicionais+"\n");
                pw.close();
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("Erro: arquivo não encontrado.");
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


    private static void iniciarResetar(String raiz, String raizShow, String raizBanda, String raizMusico) {
        File dir = new File(raiz);
        if(!dir.exists()){ //cria a pasta Freela, se não existir
            dir.mkdir();
        } else {
            apagarArquivos(dir);
        }
        dir = new File(raizShow);
        if(!dir.exists()) { //cria a pasta Shows, se não existir
            dir.mkdir();
        } else {
            apagarArquivos(dir);
        }
        dir = new File(raizBanda);
        if(!dir.exists()) { //cria a pasta Banda, se não existir
            dir.mkdir();
        } else {
            apagarArquivos(dir);
        }
        dir = new File(raizMusico);
        if(!dir.exists()) { //cria a pasta Musico, se não existir
            dir.mkdir();
        } else {
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
        System.out.println(
                "\n----------------------------" +
                        "\n        MENU PRINCIPAL" +
                        "\n----------------------------" +
                        "\n1) Cadastrar Banda/Artista" +
                        "\n2) Cadastrar Show" +
                        "\n3) Cadastrar Músico" +
                        "\n4) Registrar Músico em algum show" +
                        "\n5) Excluir Show/Músico/Banda" +
                        "\n6) Listar Shows Pendentes" +
                        "\n7) Listar Músicos" +
                        "\n8) Listar Bandas" +
                        "\n9) Iniciar/Resetar" +
                        "\n0) Sair" +
                        "\n----------------------------");
        System.out.print("Opção: ");
    }
}