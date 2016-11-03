package so;
/**
 *
 * @author JUNIOR
 */
public class Host {
    /** o core1 armazenará o processo designado a ele. */
    private Processo core1;
    /** o core2 armazenará o processo designado a ele. */
    private Processo core2;

    /** Construtor dos Hosts, já setando os cores para null pois não terão nenhum processo executando (obviamente). */
    public Host() 
    {   
        this.core1 = null;
        this.core2 = null;
    }    

    /** Método retorna o número do core que está disponível, ou se nenhum core está disponível.
     * @return int numero do core disponível. Sendo 1 para core1, 2 para core2 e 0 para nenhum core disponível.
     */
    public int getCore_Disp()
    {
        /** Caso retorne 1, o processo criado sera executado pelo core1 do host que esta sendo testado. */
        if ( core1 == null )
            return 1;
        /** Caso retorne 2, o processo criado sera executado pelo core2 do host que esta sendo testado. */
        else if ( core2 == null)
            return 2;
        /** Caso retorne 0, nenhum core está disponível no host que está sendo testado. */
        else
            return 0;
    }

    /** core1 recebe o processo que será executado por ele.
     * @param core1 - Processo que será recebido pelo core1.
     */
    public void setCore1(Processo core1) 
    {
        this.core1 = core1;
    }

    /** core2 recebe o processo que será executado por ele.
     * @param core2 - Processo que será recebido pelo core2.
     */
    public void setCore2(Processo core2) 
    {
        this.core2 = core2;
    }
    
    /**
     * getter para obter o processo que está ocupando o core1.
     * @return core1 - processo que está ocupando o core1.
     */
    public Processo getCore1()
    {
        return core1;
    }
    /**
     * getter para obter o processo que está ocupando o core2.
     * @return core2 - processo que está ocupando o core2.
     */
    public Processo getCore2()
    {
        return core2;
    }
    
}
