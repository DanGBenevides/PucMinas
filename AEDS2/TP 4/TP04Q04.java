
import java.util.Date;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.File;

class Filme {
    private String Nome;
    private String Titulo;
    private Date Data;
    private int Duracao;
    private String Genero;
    private String Idioma;
    private String Situacao;
    private float Orcamento;
    private String[] Chave;

    public Filme() {
        this.Nome = "";
        this.Titulo = "";
        this.Data = new Date();
        this.Duracao = 0;
        this.Genero = "";
        this.Idioma = "";
        this.Situacao = "";
        this.Orcamento = 0;
        this.Chave = new String[0];
    }

    // Set e Get nome
    private void setNome(String nome) {
        this.Nome = nome;
    }

    public String getNome() {
        return Nome;
    }

    // Set e Get Titulo original
    private void setTitulo(String titulo) {
        this.Titulo = titulo;
    }

    public String getTitulo() {
        return Titulo;
    }

    // Set e Get Data
    private void setData(String data) {
        Date date = null;

        if (data != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.parse(data);
            } catch (ParseException e) {
            }
        }
        this.Data = date;
    }

    public static String formataData(Date date) {
        String resp = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            resp = sdf.format(date);
        } catch (Exception e) {

        }
        return resp;
    }

    public Date getData() {
        return Data;
    }

    // Set e Get Duração
    private void setDuracao(String duracao) {
        int tempo = 0, h = 0, m = 0;

        if (duracao.length() <= 2) {
            tempo = Character.getNumericValue(duracao.charAt(0));
        } else {
            for (int i = 0; i < duracao.length(); i++) {
                if (duracao.charAt(i) == 'h') {
                    h = Character.getNumericValue(duracao.charAt(i - 1));
                    h *= 60;
                }
                if (duracao.charAt(i) == 'm') {
                    m = Character.getNumericValue(duracao.charAt(i - 1));
                    if (duracao.charAt(i - 2) >= 48 && duracao.charAt(i - 2) <= 57) {
                        m = m + (Character.getNumericValue(duracao.charAt(i - 2)) * 10);
                    }
                }
            }
            tempo = h + m;
        }

        this.Duracao = tempo;
    }

    public int getDuracao() {
        return Duracao;
    }

    // Set e Get Genero
    private void setGenero(String genero) {
        this.Genero = genero;
    }

    public String getGenero() {
        return Genero;
    }

    // Set e Get Idioma
    public void setIdioma(String idioma) {
        Idioma = idioma;
    }

    public String getIdioma() {
        return Idioma;
    }

    // Set e Get Situação
    public void setSituacao(String situacao) {
        Situacao = situacao;
    }

    public String getSituacao() {
        return Situacao;
    }

    // Set e Get Orçamento
    public void setOrcamento(float orcamento) {
        Orcamento = orcamento;
    }

    public float getOrcamento() {
        return Orcamento;
    }

    // Set e Get Chave
    public void setChave(String[] chave) {
        this.Chave = chave;
    }

    public String[] getChave() {
        return Chave;
    }

    public static String formataChave(String[] chave) {
        String resp = "[";

        for (int i = 0; i < chave.length; i++) {
            resp += chave[i] + ", ";

        }
        resp = resp.replace(", null,", "");
        resp = resp.replace("null,", "");
        resp = resp.replace("null", "");
        resp = resp.trim();
        resp += "]";

        return resp;
    }

    public static String removeTags(String in) {
        String resp = "";
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == '<') {
                i++;
                while (in.charAt(i) != '>')
                    i++;
            } else {
                resp += in.charAt(i);
            }
        }
        return resp;
    }

    public void leitura(String arquivo) throws Exception {
        InputStreamReader arq = new InputStreamReader(new FileInputStream(arquivo));
        BufferedReader br = new BufferedReader(arq);
        String aux = "", resp = "";
        float orcamento = 0;
        String[] palavras_chave = new String[40];
        String[] chave_vazia = new String[0];
        int contador = 0;

        // Nome
        while (!aux.contains("h2 class")) {
            aux = br.readLine();
        }
        aux = br.readLine();
        setNome(removeTags(aux).trim());

        // Data
        while (!aux.contains("span class=\"release\"")) {
            aux = br.readLine();
        }
        aux = br.readLine();
        aux = removeTags(aux).trim();

        for (int i = 0; i < 10; i++) {
            resp += aux.charAt(i);
        }
        setData(resp);

        // Gênero
        while (!aux.contains("genres")) {
            aux = br.readLine();
        }
        br.readLine();
        aux = removeTags(br.readLine().trim());
        aux = aux.replace("&nbsp;", "");

        setGenero(aux.trim());

        // Duração
        while (!aux.contains("runtime")) {
            aux = br.readLine();
        }
        br.readLine();
        aux = br.readLine();
        setDuracao(aux.trim());

        // Título Original
        resp = "";

        while (!aux.contains("Título original") && !aux.contains("bdi>Situação")) {
            aux = br.readLine();
        }

        if (aux.contains("Situação")) {
            setTitulo(getNome());
        } else {
            aux = removeTags(aux).trim();

            for (int i = 16; i < aux.length(); i++) {
                resp += aux.charAt(i);
            }

            setTitulo(resp.trim());
        }

        // Situação
        resp = "";

        while (!aux.contains("strong><bdi>Situa")) {
            aux = br.readLine();
        }
        aux = removeTags(aux.trim());

        for (int i = 8; i < aux.length(); i++) {
            resp += aux.charAt(i);
        }
        setSituacao(resp.trim());

        // Idioma
        resp = "";

        while (!aux.contains("Idioma")) {
            aux = br.readLine();
        }
        aux = removeTags(aux.trim());

        for (int i = 15; i < aux.length(); i++) {
            resp += aux.charAt(i);
        }
        setIdioma(resp.trim());

        // Orçamento
        resp = "";

        while (!aux.contains("Orçamento")) {
            aux = br.readLine();
        }
        aux = removeTags(aux).trim();

        if (aux.contains("-")) {
            aux = aux.replace("-", "0.0");

            for (int i = 10; i < aux.length(); i++) {
                resp += aux.charAt(i);
            }
        } else {
            for (int i = 12; i < aux.length(); i++) {
                if (aux.charAt(i) == '.') {
                    i = aux.length();
                } else {
                    resp += aux.charAt(i);
                }
            }
        }

        resp = resp.replace(",", "");
        orcamento = Float.parseFloat(resp);
        setOrcamento(orcamento);

        // Palavras Chave
        resp = "";

        while (!aux.contains("Palavras-chave") && !aux.contains("Nenhuma")) {
            aux = br.readLine();
        }
        br.readLine();
        aux = br.readLine();
        if (aux.contains("Nenhuma")) {
            setChave(chave_vazia);
        } else {
            br.readLine();
            aux = br.readLine();

            while (!aux.contains("</ul>")) {
                if (aux.contains("li")) {
                    resp = removeTags(aux).trim();
                    palavras_chave[contador] = resp;

                    aux = br.readLine();

                    contador++;
                } else {
                    aux = br.readLine();
                }
            }

            setChave(palavras_chave);
        }

        arq.close();
        br.close();
    }

    public void imprimir() {
        String resp = "";

        String Nome = getNome();
        String Titulo = getTitulo();
        String Data = formataData(getData());
        String Duracao = Integer.toString(getDuracao());
        String Genero = getGenero();
        String Idioma = getIdioma();
        String Situacao = getSituacao();
        String Orcamento = Float.toString(getOrcamento());
        String Chave = formataChave(getChave());

        resp += Nome + " " + Titulo + " " + Data + " " + Duracao + " " + Genero + " " + Idioma + " " + Situacao + " "
                + Orcamento + " " + Chave;

        MyIO.println(resp);
    }
}

class NoAN {
    public boolean cor;
    public Filme elemento;
    public NoAN esq, dir;
    public int nivel;

    public NoAN(Filme elemento) {
        this(elemento, false, null, null);
    }

    public NoAN(Filme elemento, boolean cor) {
        this(elemento, cor, null, null);
    }

    public NoAN(Filme elemento, boolean cor, NoAN esq, NoAN dir) {
        this.cor = cor;
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class Alvinegra {
    private int comparacoes;
    private NoAN raiz;

    public Alvinegra() {
        raiz = null;
    }

    public boolean pesquisar(String x) {
        MyIO.print("raiz ");
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(String x, NoAN i) {
        boolean resp;

        if (i == null) {
            resp = false;
        } else if (x.compareTo(i.elemento.getTitulo()) == 0) {
            resp = true;
        } else if (x.compareTo(i.elemento.getTitulo()) < 0) {
            MyIO.print("esq ");
            resp = pesquisar(x, i.esq);
        } else {
            MyIO.print("dir ");
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    public void inserir(Filme elemento) throws Exception {
        // Se a arvore estiver vazia
        if (raiz == null) {
            raiz = new NoAN(elemento);
            System.out.println("Antes, zero elementos. Agora, raiz(" + raiz.elemento + ").");

            // Senao, se a arvore tiver um elemento
        } 
        else if (raiz.esq == null && raiz.dir == null) {
            if (elemento.getTitulo().compareTo(raiz.elemento.getTitulo()) < 0) {
                raiz.esq = new NoAN(elemento);
            } 
            else {
                raiz.dir = new NoAN(elemento);
            }

            // Senao, se a arvore tiver dois elementos (raiz e dir)
        } 
        else if (raiz.esq == null) {
            if (elemento.getTitulo().compareTo(raiz.elemento.getTitulo()) < 0) {
                raiz.esq = new NoAN(elemento);
            } 
            else if (elemento.getTitulo().compareTo(raiz.dir.elemento.getTitulo()) < 0) {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = elemento;
            } 
            else {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = raiz.dir.elemento;
                raiz.dir.elemento = elemento;
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, se a arvore tiver dois elementos (raiz e esq)
        } 
        else if (raiz.dir == null) {
            if (elemento.getTitulo().compareTo(raiz.elemento.getTitulo()) > 0) {
                raiz.dir = new NoAN(elemento);
            } 
            else if (elemento.getTitulo().compareTo(raiz.esq.elemento.getTitulo()) > 0) {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = elemento;
            } 
            else {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = elemento;
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, a arvore tem tres ou mais elementos
        } 
        else {
            inserir(elemento, null, null, null, raiz);
        }
        raiz.cor = false;
    }

    private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
        // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
        if (pai.cor == true) {
            // 4 tipos de reequilibrios e acoplamento
            if (pai.elemento.getTitulo().compareTo(avo.elemento.getTitulo()) > 0) { // rotacao a esquerda ou direita-esquerda
                if (i.elemento.getTitulo().compareTo(pai.elemento.getTitulo()) > 0) {
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }
            } else { // rotacao a direita ou esquerda-direita
                if (i.elemento.getTitulo().compareTo(pai.elemento.getTitulo()) < 0) {
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }
            if (bisavo == null) {
                raiz = avo;
            } else if (avo.elemento.getTitulo().compareTo(bisavo.elemento.getTitulo()) < 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }
            // reestabelecer as cores apos a rotacao
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        } // if(pai.cor == true)
    }

    /**
     * Metodo privado recursivo para inserir elemento.
     * 
     * @param elemento Elemento a ser inserido.
     * @param avo      NoAN em analise.
     * @param pai      NoAN em analise.
     * @param i        NoAN em analise.
     * @throws Exception Se o elemento existir.
     */
    private void inserir(Filme elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
        if (i == null) {
            if (elemento.getTitulo().compareTo(pai.elemento.getTitulo()) < 0) {
                i = pai.esq = new NoAN(elemento, true);
            } else {
                i = pai.dir = new NoAN(elemento, true);
            }
            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }
        } else {
            // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if (i == raiz) {
                    i.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }
            if (elemento.getTitulo().compareTo(i.elemento.getTitulo()) < 0) {
                inserir(elemento, avo, pai, i, i.esq);
            } else if (elemento.getTitulo().compareTo(i.elemento.getTitulo()) > 0) {
                inserir(elemento, avo, pai, i, i.dir);
            } else {
                throw new Exception("Erro inserir (elemento repetido)!");
            }
        }
    }

    private NoAN rotacaoDir(NoAN no) {
        //System.out.println("Rotacao DIR(" + no.elemento + ")");
        NoAN noEsq = no.esq;
        NoAN noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    private NoAN rotacaoEsq(NoAN no) {
        //System.out.println("Rotacao ESQ(" + no.elemento + ")");
        NoAN noDir = no.dir;
        NoAN noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }

    private NoAN rotacaoDirEsq(NoAN no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private NoAN rotacaoEsqDir(NoAN no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }

    public void matricula(long tempo) throws Exception {
        File file = new File("matricula_alvinegra.txt");
        FileWriter fw = new FileWriter(file);

        fw.write("753511" + "\t" + tempo + "\t" + comparacoes);
        fw.close();
    }
}

public class TP04Q04 {
    public static void main(String[] args) throws Exception {
        MyIO.setCharset("UTF-8");
        Filme filme;
        Alvinegra arvore = new Alvinegra();
        String arquivo = "";
        String path = "/tmp/filmes/";
        String pesquisa = "";
        long tempoInicial = System.currentTimeMillis();

        while (isFim(arquivo) == false) {
            arquivo = MyIO.readLine();
            if (isFim(arquivo) == false) {
                arquivo = path + arquivo;

                filme = new Filme();
                filme.leitura(arquivo);
                arvore.inserir(filme);
            }
        }

        while (isFim(pesquisa) == false) {
            pesquisa = MyIO.readLine();
            if (isFim(pesquisa) == false) {
                MyIO.println(pesquisa);
                if (arvore.pesquisar(pesquisa) == true) {
                    MyIO.println("SIM");
                } else {
                    MyIO.println("NAO");
                }
            }
        }

        long tempoFinal = System.currentTimeMillis();
        long tempo = tempoFinal - tempoInicial;
        arvore.matricula(tempo);
    }

    public static boolean isFim(String fim) {
        return (fim.length() == 3 && fim.charAt(0) == 'F' && fim.charAt(1) == 'I' && fim.charAt(2) == 'M');
    }
}
