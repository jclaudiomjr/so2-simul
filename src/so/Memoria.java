package so;

import java.text.SimpleDateFormat;


/**
 *
 * @author JUNIOR
 */
public class Memoria 
{
    private final int tam_pag = 8;      /** Tamanho ta pagina em bytes. O tamanho 8 foi definido pelo professor **/    
    private final int qtd_pag_f = 16;       /** Numero de paginas FISICAS. Aquantidade 16 foi definido pelo professor **/   
    private final int qtd_pag_v = 32;       /** Numero de paginas VIRTUAIS. Aquantidade 32 foi definido pelo professor **/
    private int escopo_mem_f[][] = new int[qtd_pag_f][tam_pag];     /** escopo_mem_f é a matriz 16x8 que mostrará o escopo da memória FISICA. */    
    private int escopo_mem_v[][] = new int [qtd_pag_v][tam_pag];        /** escopo_mem_v é a matriz 32x8 que mostrará o escopo da memória VIRTUAL. */

    /** Construtor da memória, já setando a memória com o escopo zerado. */
    public Memoria() 
    {
        zera_escopo();        
    }
    
    /** Método para zerar escopo, usado somente no construtor da classe Memoria. */
    public void zera_escopo()
    {
        /** lin controla-rá linhas das matrizes, col controla-rá colunas das matrizes. */
        int lin, col;
        
        for ( lin = 0; lin < qtd_pag_f ; lin++ )
            for ( col = 0; col < tam_pag ; col++ )
                escopo_mem_f[lin][col] = 0;
                
        
        for ( lin = 0; lin < qtd_pag_v ; lin++ )
            for ( col = 0; col < tam_pag ; col++ )
                escopo_mem_v[lin][col] = 0;  
     }
   
    /** Metodo que desenha o escopo da memória VIRTUAL. */    
    public void escopo_v()
    {
        /** lin controla-rá linhas das matrizes, col controla-rá colunas das matrizes. */
        int lin, col;
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        
        System.out.println("ESCOPO MEMORIA VIRTUAL:");
        So.insereDadosArquivo(formato.format(So.getTempoAtual())+"%nESCOPO MEMORIA VIRTUAL:%n");
        for ( lin = 0; lin < qtd_pag_v ; lin++ )
        {
            for ( col = 0; col < tam_pag ; col++ )
            {
                if ( escopo_mem_v[lin][col] == 0 )
                {
                    System.out.print("| ");
                    So.insereDadosArquivo("| ");    
                }
                else if ( escopo_mem_v[lin][col] == 1 )
                {
                    System.out.print("|#");
                    So.insereDadosArquivo("|#");
                }
            }
            System.out.println("|");
            So.insereDadosArquivo("|%n"); 
        }
                
    }
    
    /** Metodo que desenha o escopo da memória FISICA. */
    public void escopo_f()
    {
        /** lin controla-rá linhas das matrizes, col controla-rá colunas das matrizes. */
        int lin, col;
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        
        System.out.println("ESCOPO MEMORIA FISICA:");
        So.insereDadosArquivo(formato.format(So.getTempoAtual())+" ESCOPO MEMORIA FISICA:%n");
        
        for ( lin = 0; lin < qtd_pag_f ; lin++ )
        {
            for ( col = 0; col < tam_pag ; col++ )
            {
                if ( escopo_mem_f[lin][col] == 0 )
                {
                    System.out.print("| ");
                    So.insereDadosArquivo("| ");
                }
                else if ( escopo_mem_f[lin][col] == 1 )
                {
                    System.out.print("|#");
                    So.insereDadosArquivo("|#");
                }
            }
            System.out.println("|");
            So.insereDadosArquivo("|%n");
        }
                
    }
    
    /** Método apenas retorna o tamanho das págimas (8 Bytes)
     * @return int tam_pag = tamanho da pagina
     */
    public int getTam_pag()
    {
        return (tam_pag);
    }
    
    /** Método apenas retorna a quantidade de páginas FISICAS.
     * @return qtd_pag_f = quantidade de paginas fisica.
     */
    public int getQtd_pag_f()
    {
        return (qtd_pag_f);
    }
    
    /** Método apenas retorna a quantidade de páginas VIRTUAL.
     * @return qtd_pag_v = quantidade de paginas virtual.
     */
    public int getQtd_pag_v()
    {
        return (qtd_pag_v);
    }
}

