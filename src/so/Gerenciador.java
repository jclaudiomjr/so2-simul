/**  Programa simulador de um SO.
 *   Trabalho de SO II.
 */


package so;

/** 
 * Import necessário para funcionamento da classe.
 * ArrayList: para utilizar Arraylist de processos criados e fila de prontos.
 */
import java.util.ArrayList;

/**
 * Classe Gerenciador - Responsável pela gerencia dos processos.
 * @author JUNIOR
 */
public class Gerenciador 
{   
    private  Integer gera_pid; /** seq_proc é usada no método geraPID() para gerar um novo PID de numero sequencial. */
    private  Memoria memoria; /** Instância que controla-rá futuramente os processos na memória. */    
    private  Host host1, host2; /** Instâncias para simular duas máquinas. */    
    private  Integer disponibilidade; /** Usada para verificar a disponibilidade dos cores dos Hosts. */
    private  ArrayList<Processo> fila_aptos; /** Armazena processos prontos para executar. */
    private  ArrayList<Processo> processos_criados; /** Armazena todos os processos criados. */
    private String escalonador; /** Tipo do escalonador */
    private int quantum; /** Quantum de tempo */
    
    /** Construtor da classe Gerenciador, o contrutor já irá instanciar também uma memória e dois Hosts. */
    public Gerenciador()  
    {        
        
        this.disponibilidade = null;
        this.gera_pid = 0;
        this.memoria = new Memoria();
        this.host1 = new Host();
        this.host2 = new Host();
        this.processos_criados = new ArrayList<>();
        this.fila_aptos = new ArrayList<>();
        
    }

    /** Gera um pid de forma sequancial, para cada processo.
     * @return Integer - PID
     */
    public Integer geraPID()
    {
        gera_pid++;
        
        return gera_pid;
    }
    
    
    /** Metodo que verifica se o tempo de execução de cada processo em execução não excedeu seu tempo
     * 
     * @param h1core1 - core1 do host1
     * @param h1core2 - core2 do host1
     * @param h2core1 - core1 do host2
     * @param h2core2 - core2 do host2
     */
    public void verificaExecucao(Processo h1core1, Processo h1core2, Processo h2core1, Processo h2core2)
    {
        /** separa_string_tempo_atual receberá, os valores retornado de gettime(), de forma separada por ":". */
        String separa_string_tempo_atual[] = So.getTime().split(":");
        Integer tempo_atual[] = new Integer[3];
        
        tempo_atual[0] = Integer.parseInt(separa_string_tempo_atual[0]);
        tempo_atual[1] = Integer.parseInt(separa_string_tempo_atual[1]);
        tempo_atual[2] = Integer.parseInt(separa_string_tempo_atual[2]);        
        
        /** Inicio da verificação dos processos do host1 core1. */
        if ( host1.getCore1() != null )
        {
            if ( tempo_atual[0] > h1core1.getTempo_saida(0) )
            {                
                h1core1.setStatus("finalizado");
                So.insereDadosArquivo(h1core1.getTempo_saida(0)+":"+h1core1.getTempo_saida(1)+":"+h1core1.getTempo_saida(2)+": processo de nome:"+h1core1.getNome()+" foi finalizado. %n");
                if ( ! fila_aptos.isEmpty() )
                {                       
                    fila_aptos.get(0).setTempo_entrada_execucao(h1core1.getTempo_saida(0)+":"+h1core1.getTempo_saida(1)+":"+h1core1.getTempo_saida(2));
                    fila_aptos.get(0).setTempo_saida(calculaTempoSaida(fila_aptos.get(0).getTempo_entrada_execucao(), fila_aptos.get(0).getTempo_execucao()));
                    host1.setCore1(null);                        
                    host1.setCore1(fila_aptos.get(0));                       
                    fila_aptos.remove(0);
                    So.insereDadosArquivo(tempo_atual[0]+":"+tempo_atual[1]+":"+tempo_atual[2]+": processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no Host1 no Core1.%n");
                }
            }
            else if ( tempo_atual[0] == h1core1.getTempo_saida(0) )
            {               
                if ( tempo_atual[1] > h1core1.getTempo_saida(1) )  
                {
                    h1core1.setStatus("finalizado");
                    So.insereDadosArquivo(h1core1.getTempo_saida(0)+":"+h1core1.getTempo_saida(1)+":"+h1core1.getTempo_saida(2)+": processo de nome:"+h1core1.getNome()+" foi finalizado. %n");
                    if ( ! fila_aptos.isEmpty() )
                    {                       
                        fila_aptos.get(0).setTempo_entrada_execucao(h1core1.getTempo_saida(0)+":"+h1core1.getTempo_saida(1)+":"+h1core1.getTempo_saida(2));
                        fila_aptos.get(0).setTempo_saida(calculaTempoSaida(fila_aptos.get(0).getTempo_entrada_execucao(), fila_aptos.get(0).getTempo_execucao()));
                        host1.setCore1(null);                        
                        host1.setCore1(fila_aptos.get(0));                       
                        fila_aptos.remove(0);
                        So.insereDadosArquivo(tempo_atual[0]+":"+tempo_atual[1]+":"+tempo_atual[2]+": processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no Host1 no Core1.%n");
                    }
                }
                else if ( tempo_atual[1] == h1core1.getTempo_saida(1))
                {
                    if ( tempo_atual[2] >= h1core1.getTempo_saida(2) )
                    {
                        h1core1.setStatus("finalizado");
                        So.insereDadosArquivo(h1core1.getTempo_saida(0)+":"+h1core1.getTempo_saida(1)+":"+h1core1.getTempo_saida(2)+": processo de nome:"+h1core1.getNome()+" foi finalizado. %n");
                    
                        if ( ! fila_aptos.isEmpty() )
                        {                       
                            fila_aptos.get(0).setTempo_entrada_execucao(h1core1.getTempo_saida(0)+":"+h1core1.getTempo_saida(1)+":"+h1core1.getTempo_saida(2));
                            fila_aptos.get(0).setTempo_saida(calculaTempoSaida(fila_aptos.get(0).getTempo_entrada_execucao(), fila_aptos.get(0).getTempo_execucao()));
                            host1.setCore1(null);                        
                            host1.setCore1(fila_aptos.get(0));                       
                            fila_aptos.remove(0);
                            So.insereDadosArquivo(tempo_atual[0]+":"+tempo_atual[1]+":"+tempo_atual[2]+": processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no Host1 no Core1.%n");
                        }
                    }
                }
            }            
        }
        /** Inicio da verificação dos processos do host1 core2. */
        if ( host1.getCore2() != null )
        {
            if ( tempo_atual[1] >= h1core2.getTempo_saida(1) )  
            {
                if ( tempo_atual[2] >= h1core2.getTempo_saida(2) )
                {
                    h1core2.setStatus("finalizado");
                    So.insereDadosArquivo(h1core2.getTempo_saida(0)+":"+h1core2.getTempo_saida(1)+":"+h1core2.getTempo_saida(2)+": processo de nome:"+h1core2.getNome()+" foi finalizado. %n");
                    
                    if ( ! fila_aptos.isEmpty() )
                    {
                        fila_aptos.get(0).setTempo_entrada_execucao(h1core2.getTempo_saida(0)+":"+h1core2.getTempo_saida(1)+":"+h1core2.getTempo_saida(2));
                        fila_aptos.get(0).setTempo_saida(calculaTempoSaida(fila_aptos.get(0).getTempo_entrada_execucao(), fila_aptos.get(0).getTempo_execucao()));
                        host1.setCore2(null);
                        host1.setCore2(fila_aptos.get(0));                        
                        fila_aptos.remove(0);
                        So.insereDadosArquivo(tempo_atual[0]+":"+tempo_atual[1]+":"+tempo_atual[2]+" processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no Host1 no Core2.%n");
                    }
                }
            }
        }
        /** Inicio da verificação dos processos do host2 core1. */
        if ( host2.getCore1() != null )
        {
            if ( tempo_atual[1] >= h2core1.getTempo_saida(1) )  
            {
                if ( tempo_atual[2] >= h2core1.getTempo_saida(2) )
                {
                    h2core1.setStatus("finalizado"); 
                    So.insereDadosArquivo(h2core1.getTempo_saida(0)+":"+h2core1.getTempo_saida(1)+":"+h2core1.getTempo_saida(2)+": processo de nome:"+h2core1.getNome()+" foi finalizado. %n");
                    
         
                    if ( ! fila_aptos.isEmpty() )
                    {
                        fila_aptos.get(0).setTempo_entrada_execucao(h2core1.getTempo_saida(0)+":"+h2core1.getTempo_saida(1)+":"+h2core1.getTempo_saida(2));
                        fila_aptos.get(0).setTempo_saida(calculaTempoSaida(fila_aptos.get(0).getTempo_entrada_execucao(), fila_aptos.get(0).getTempo_execucao()));
                        host2.setCore1(null);
                        host2.setCore1(fila_aptos.get(0));                        
                        fila_aptos.remove(0);
                        So.insereDadosArquivo(tempo_atual[0]+":"+tempo_atual[1]+":"+tempo_atual[2]+" processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no Host2 no Core1.%n");
                    }
                }
            }
        }
        /** Inicio da verificação dos processos do host2 core2. */
        if ( host2.getCore2() != null )
        {
            if ( tempo_atual[1] >= h2core2.getTempo_saida(1) )  
            {
                if ( tempo_atual[2] >= h2core2.getTempo_saida(2) )
                {
                    h2core2.setStatus("finalizado");
                    So.insereDadosArquivo(h2core2.getTempo_saida(0)+":"+h2core2.getTempo_saida(1)+":"+h2core2.getTempo_saida(2)+": processo de nome:"+h2core2.getNome()+" foi finalizado. %n");
                    

                    if ( ! fila_aptos.isEmpty() )
                    {
                        fila_aptos.get(0).setTempo_entrada_execucao(h2core2.getTempo_saida(0)+":"+h2core2.getTempo_saida(1)+":"+h2core2.getTempo_saida(2));
                        fila_aptos.get(0).setTempo_saida(calculaTempoSaida(fila_aptos.get(0).getTempo_entrada_execucao(), fila_aptos.get(0).getTempo_execucao()));
                        host2.setCore2(null);
                        host2.setCore2(fila_aptos.get(0));                        
                        fila_aptos.remove(0);
                        So.insereDadosArquivo(tempo_atual[0]+":"+tempo_atual[1]+":"+tempo_atual[2]+" processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no Host2 no Core2.%n");
                    }
                }
            }
        }
    }
    
    /**
     * Seta o tipo de escalonador.
     * @param escalonador - tipo de escalonador.
     */
    public void setEscalonador(String escalonador)
    {
        this.escalonador = escalonador;
    }
    
    /**
     * Retorna o tipo de escalonador.
     * @return escalonador - Tipo de escalonador.
     */
    public String getEscalonador()
    {
        return escalonador;
    }
    
    /**
     * Seta a quantidade (unidade de tempo) quantum.
     * @param quantum - quantum de tempo.
     */
    public void setQuantum(String quantum)
    {
        this.quantum = Integer.parseInt(quantum);
    }
    
    /**
     * Retorna o valor do quantum.
     * @return quantum - quantum de tempo.
     */
    public int getQuantum()
    {
        return quantum;
    }
    
    /** Método para criar processo e adicionar no arraylist de processos_criados.
     * 
     * @param nome - nome do processo.
     * @param tamanho - tamanho do processo (Bytes).
     * @param tempo_execucao - tempo de execução do processo.
     * @param tempo_criacao - tempo de criação do processo.
     */
    public void criarProcesso(String nome, String tamanho, String tempo_execucao, String tempo_criacao)
    {       
        String entrada_execucao = null;
        if ( !processos_criados.isEmpty() )
        {    
            verificaExecucao(host1.getCore1(), host1.getCore2(), host2.getCore1(), host2.getCore2());
        }         
        /** disponibilidade vai receber o valor retornado do método 'getCore_Disp()'. O método verifica qual core está disponível do HOST 1. */
        disponibilidade = host1.getCore_Disp();
        /** Início da verificação (para o host 1), se for 1 o processo irá para o core1 (do host1), se for 2 o processo vai para o core2 do host2). */
        if ( disponibilidade == 1 )
        {                            
            entrada_execucao = tempo_criacao;
            /** Cria o processo, adicionando ao arraylist de processos_criados. */
            processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), "Host 1", "executando" ));
            /** core1 do host1 recebe o processo. */
            host1.setCore1(processos_criados.get(processos_criados.size() - 1));
            So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host1 executando no Core1.%n");
        } 
        else if ( disponibilidade == 2 )
        {               
            entrada_execucao = tempo_criacao;
            /** Cria o processo, adicionando ao arraylist de processos_criados. */
            processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), "Host 1", "executando" ));
            /** core2 do host1 recebe o processo. */
            host1.setCore2(processos_criados.get(processos_criados.size() - 1));
            So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host1 executando no Core2.%n");
        } 
        /** fim da verificação para o host1 e início da verificação para o host2 caso não encontre um core disponível no host1. */
        else
        {
            /** Mesmo procedimento, agora verificando o HOST 2. */
            disponibilidade = host2.getCore_Disp();
            /** Início da verificação para host2, se for 1 o processo irá para o core1 (do host2), se for 2 irá para o core2 (do host2). */
            if ( disponibilidade == 1 )
            {                    
                entrada_execucao = tempo_criacao;
                /** Cria o processo, adicionando ao arraylist de processos_criados. */
                processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), "Host 2", "executando" ));
                /** core1 do host2 recebe o processo. */
                host2.setCore1(processos_criados.get(processos_criados.size() - 1));
                So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host2 executando no Core1.%n");
            } 
            else if ( disponibilidade == 2 )
            {
                entrada_execucao = tempo_criacao;
                /** Cria o processo, adicionando ao arraylist de processos_criados. */
                processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), "Host 2", "executando" ));
                /** core2 do host2 recebe o processo. */
                host2.setCore2(processos_criados.get(processos_criados.size() - 1));
                So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host2 executando no Core2.%n");
            }
            /** Fim da verificação para host2. Caso não ache um core disponível em nenhum host, o processo é criado no arraylist de processos_criados e também é colocado no arraylist de fila_aptos. */
            else
            {           
                /** Cria o processo, adicionando ao arraylist de processos_criados. */
                processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao, null, "?", "apto" ));
                /** O último processo criado é adicionado na fila de aptos. */
                fila_aptos.add(processos_criados.get(processos_criados.size() - 1));
                So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado e colocado na fila de aptos.%n");
            }                               
        }
        System.out.println("\t* *Processo Criado aos "+tempo_criacao+".* *");
    }

    /** Este método irá calcular o tempo de saída do processo criado.
     * 
     * @param entrada_execucao - tempo de criação do processo.
     * @param tempo_execucao - tempo de execução do processo.
     * @return tempo_saida - tempo de criação + tempo de execução.
     */
    public Integer[] calculaTempoSaida(String entrada_execucao, String tempo_execucao)
    {
        /** separa_string_tempo_criacao receberá o valor do tempo_criacao, separado por ":". */
        String separa_string_entrada_execucao[] = entrada_execucao.split(":");
        Integer tempo_saida[] = new Integer[3];
        
        /**
         * tempo_saida[0] - é refetente a hora hh.
         * tempo_saida[1] - é referente aos minutos mm.
         * tempo_saida[2] - é referente aos segundos ss.
         */       
        tempo_saida[2] = ( ( Integer.parseInt(separa_string_entrada_execucao[2]) ) + ( Integer.parseInt(tempo_execucao) ) );
        if ( tempo_saida[2] > 60 )
        {
            tempo_saida[1] = ( Integer.parseInt(separa_string_entrada_execucao[1] ) ) + 1;
            tempo_saida[2] -= 60;
            if ( tempo_saida[1] == 60 )
            {
                tempo_saida[0] = ( Integer.parseInt(separa_string_entrada_execucao[0] ) ) + 1;
                tempo_saida[1] = 0;
            }
            else
            {
                tempo_saida[0] = ( Integer.parseInt(separa_string_entrada_execucao[0] ) );
            }
        }
        else
        {
            tempo_saida[0] = Integer.parseInt(separa_string_entrada_execucao[0]);
            tempo_saida[1] = Integer.parseInt(separa_string_entrada_execucao[1]);
        }
        return tempo_saida;
    }
    
    /**
     * Método que mostrará a tabela de processos e suas informações/status.
     * Básicamente formado por printf's.
     */
    public void mostraTabelaPS()
    {
        int aux;
        
        if ( !processos_criados.isEmpty() )
        {    
            verificaExecucao(host1.getCore1(), host1.getCore2(), host2.getCore1(), host2.getCore2());
        }
        
        if ( !processos_criados.isEmpty() )
        {
            So.insereDadosArquivo(So.getTime()+" Mostra Tabela:%n");
            System.out.println("#########################################################################################################");
            System.out.println("#PID\t|NOME\t|TAMANHO\t|TEMPO CRIACAO\t|TEMPO EXEC.\t|TEMPO P/ SAÍDA\t|STATUS\t\t|HOST\t#");
            So.insereDadosArquivo("#########################################################################################################%n");
            So.insereDadosArquivo("#PID\t|NOME\t|TAMANHO\t|TEMPO CRIACAO\t|TEMPO EXEC.\t|TEMPO P/ SAÍDA\t|STATUS\t\t|HOST\t#%n");

            for ( aux = 0 ; aux < processos_criados.size() ; aux++ )
            {
                if ( processos_criados.get(aux).getStatus().equals("executando") )
                {
                    System.out.println("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                    +processos_criados.get(aux).getTempo_criacao()+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+processos_criados.get(aux).getTempo_saida(0)+":"
                    +processos_criados.get(aux).getTempo_saida(1)+":"+processos_criados.get(aux).getTempo_saida(2)+"\t|"+processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost()+"\t#");                
                    
                    So.insereDadosArquivo("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                    +processos_criados.get(aux).getTempo_criacao()+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+processos_criados.get(aux).getTempo_saida(0)+":"
                    +processos_criados.get(aux).getTempo_saida(1)+":"+processos_criados.get(aux).getTempo_saida(2)+"\t|"+processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost()+"\t#%n");                

                }
            }
            if ( !fila_aptos.isEmpty() )
            {
                for ( aux = 0 ; aux < fila_aptos.size() ; aux++ )
                {
                    System.out.println("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                    +processos_criados.get(aux).getTempo_criacao()+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+processos_criados.get(aux).getTempo_saida(0)+":"
                    +processos_criados.get(aux).getTempo_saida(1)+":"+processos_criados.get(aux).getTempo_saida(2)+"\t|"+processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost()+"\t#");                
                    
                    So.insereDadosArquivo("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                    +processos_criados.get(aux).getTempo_criacao()+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+processos_criados.get(aux).getTempo_saida(0)+":"
                    +processos_criados.get(aux).getTempo_saida(1)+":"+processos_criados.get(aux).getTempo_saida(2)+"\t|"+processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost()+"\t#%n");                

                }
            }
            System.out.println("#########################################################################################################");
            System.out.println("#\tTempo Atual: "+So.getTime()+"  #");
            System.out.println("################################");
            
            So.insereDadosArquivo("#########################################################################################################%n");
            So.insereDadosArquivo("#\tTempo Atual: "+So.getTime()+"  #%n");
            So.insereDadosArquivo("################################%n");
        }
        else
        {
            System.out.println("Não foi criado nenhum processo!");
            So.insereDadosArquivo(So.getTime()+" não foi criado nenhum processo.%n");
        }
        
    }
    
    /** Método que irá mostrar informações da memória de acordo com o parâmetro passado.
     * 
     * @param parametro - parâmetro informado para o método retorna o escopo da memória Física ou Virtual.
     */
    public void mostraMem(String parametro)
    {
        /** Se o parâmetro é "F" mostrará o escopo da memória FISICA. */
        if ( parametro.equals("F") )
        {
            memoria.escopo_f();
        } 
        /** Se o parâmetro é "V" mostrará o escopo da memória VIRTUAL. */
        else if ( parametro.equals("V") ) 
        {
            memoria.escopo_v();
        } 
        /** Se o parâmetro é "nd" mostrará somente a configuração da memória. */
        else if ( parametro.equals("nd") )
          {
                System.out.println("CONFIGURAÇÕES DE MEMORIA:");
                System.out.println("Pagina: "+memoria.getTam_pag()+" Bytes");
                System.out.println("Memória Física: "+memoria.getQtd_pag_f()+" Paginas.");
                System.out.println("Memória Virtual: "+memoria.getQtd_pag_v()+ "Paginas");
                
                So.insereDadosArquivo("CONFIGURAÇÕES DE MEMORIA:%n");
                So.insereDadosArquivo("Pagina: "+memoria.getTam_pag()+" Bytes.%n");
                So.insereDadosArquivo("Memória Física: "+memoria.getQtd_pag_f()+" Paginas.%n");
                So.insereDadosArquivo("Memória Virtual: "+memoria.getQtd_pag_v()+ "Paginas.%n");
          }
    }

    
    /**
     * Método que limpa os ArrayLists antes de usá-los (garantia).
     */
    public void limpaArraylist()
    {
        processos_criados.clear();
        fila_aptos.clear();
    }
}
