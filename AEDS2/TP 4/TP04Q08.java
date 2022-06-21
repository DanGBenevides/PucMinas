import java.util.Scanner;

class No {
    public int elemento;
    public No esq, dir;

    public No(int elemento) {
        this(elemento, null, null);
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    //In
    public void caminharCentral() {
        caminharCentral(raiz);
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq);
            MyIO.print(i.elemento + " ");
            caminharCentral(i.dir);
        }
    }

    //Pre
    public void caminharPre() {
        caminharPre(raiz);
    }

    private void caminharPre(No i) {
        if (i != null) {
            MyIO.print(i.elemento + " ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    //Pos
    public void caminharPos() {
        caminharPos(raiz);
    }
    
    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq);
            caminharPos(i.dir);
            MyIO.print(i.elemento + " ");
        }
    }

    //Inserir
    public void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(int x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        }
        else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        }
        else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        }
        else {
            throw new Exception("Erro ao inserir!");
        }
        return i;
    }
}



class TP04Q08 {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        int c = 0;
        int n = 0;
        int valor = 0;
        ArvoreBinaria arvore;

        c = Integer.parseInt(sc.next().trim());
        for (int i = 1; i <= c; i++) {
            arvore = new ArvoreBinaria();
            n = Integer.parseInt(sc.next().trim());
            
            for (int j = 0; j < n; j++) {
                valor = Integer.parseInt(sc.next().trim());
                arvore.inserir(valor);
            }

            MyIO.println("Case " + i + ":");
            MyIO.print("Pre.: ");
            arvore.caminharPre();
            MyIO.println(" ");
            MyIO.print("In..: ");
            arvore.caminharCentral();
            MyIO.println(" ");
            MyIO.print("Post: ");
            arvore.caminharPos();

            if (i != c) {
                MyIO.println("\n");
            }
            n = 0;
        }
        sc.close();
    }
}