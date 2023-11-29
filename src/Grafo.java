import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.*;

public class Grafo {

    private int matGrafo[][];

    private int matCoords[][];

    private String matNomes[];

    private int vertices;

    private int arestas;

    private boolean direcionado = false;

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getArestas() {
        return arestas;
    }

    public void setArestas(int arestas) {
        this.arestas = arestas;
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void setDirecionado(boolean direcionado) {
        this.direcionado = direcionado;
    }

    public void setMatNomes(int vertice, String nome){
        matNomes[vertice] = nome;
    }

    public void printNomes(){
        for(String i : matNomes){
            System.out.println(i);
        }
    }

    public int getMatNomes(String nome){
        for(int i = 0; i < vertices; i ++){
            if(matNomes[i].equals(nome)){
                return i;
            }
        }
        return -1;
    }

    public void lerArquivo(String nomeArquivo){
        try {
            File arquivoGrafo = new File(nomeArquivo);
            Scanner e = new Scanner(arquivoGrafo);
            String direcionado = e.nextLine();
            if(direcionado.equals("nao")){
                this.direcionado = false;
                System.out.println("Não direcionado");
            }else{
                this.direcionado = true;
                System.out.println("Direcionado");
            }
            vertices = e.nextInt();
            matGrafo = new int[vertices][vertices];
            for(int i = 0; i<vertices;i++){
                for (int j = 0; j<vertices; j++){
                    matGrafo[i][j] = -1;
                }
            }
            matCoords = new int[2][vertices];
            matNomes = new String[vertices];
            for(int i = 0; i<vertices;i++){
                e.nextInt();
                matCoords[0][i] = e.nextInt();
                matCoords[1][i] = e.nextInt();
                e.skip(" ");
                matNomes[i] = e.nextLine();
            }
            arestas = e.nextInt();
            for(int i = 0; i<arestas;i++){
                int origem = e.nextInt();
                int destino = e.nextInt();
                if(this.direcionado) {
                    matGrafo[origem][destino] = e.nextInt();

                }else{
                    int peso = e.nextInt();
                    matGrafo[origem][destino] = peso;
                    matGrafo[destino][origem] = peso;
                }
            }
            e.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERRO!");
            e.printStackTrace();
        }
    }

    public void exportaGrafo(){
        try {
            File arquivo = new File("src/grafo.txt");
            arquivo.createNewFile();
        } catch(IOException e) {
            System.out.println("Falha");
        }

        try {
            FileWriter arquivo = new FileWriter("src/grafo.txt");
            if(direcionado){
                arquivo.write("sim\n");
            }else{
                arquivo.write("nao\n");
            }
            arquivo.write(vertices + "\n");
            for(int i = 0; i < vertices; i++){
                arquivo.write(i+" "+matCoords[0][i]+" "+matCoords[1][i]+" "+matNomes[i]+"\n");
            }
            arquivo.write(arestas+"\n");
            if(direcionado) {
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        if (matGrafo[i][j] == -1) {
                            continue;
                        } else {
                            arquivo.write(i + " " + j + " " + matGrafo[i][j] + "\n");
                        }
                    }
                }
            }else{
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        if (matGrafo[i][j] == -1 || j>i) {
                            continue;
                        } else {
                            arquivo.write(i + " " + j + " " + matGrafo[i][j] + "\n");
                        }
                    }
                }
            }
            arquivo.close();
        } catch(IOException e) {
            System.out.println("Falha");
        }
    }

    public void criaGrafoVazio(int tamanho, boolean direcionado){
        Scanner e = new Scanner(System.in);
        vertices = tamanho;
        matGrafo =  new int[vertices][vertices];
        matCoords = new int[2][vertices];
        matNomes = new String[vertices];
        arestas =  0;
        this.direcionado = direcionado;
        for(int i = 0; i < vertices; i++){
            System.out.println("Digite a coordenada x do vértice "+i+": ");
            matCoords[0][i] = e.nextInt();
            System.out.println("Digite a coordenada y do vértice "+i+": ");
            matCoords[1][i] = e.nextInt();
            System.out.println("Digite o nome do vértice "+i+": ");
            e.skip("\n");
            matNomes[i] = e.nextLine();
            for(int j = 0; j < vertices; j++){
                matGrafo[i][j] = -1;
            }
        }
    }

    public void imprimeGrafo(){
        for(int i = 0; i < vertices; i++){
            for (int j = 0; j < vertices; j++){
                if(matGrafo[i][j] == -1){
                    System.out.print("X ");
                }else{
                    System.out.print(matGrafo[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int consultaAdj(int origem,int destino){
        return matGrafo[origem][destino];
    }

    public void removeAresta(int origem, int destino){
        if(origem >= vertices || destino >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        if(direcionado){
            matGrafo[origem][destino] = -1;
        }else{
            matGrafo[origem][destino] = -1;
            matGrafo[destino][origem] = -1;
        }
        arestas--;
    }

    public void insereAresta(int origem, int destino, int peso){
        if(origem >= vertices || destino >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        if(direcionado){
            matGrafo[origem][destino] = peso;
        }else{
            matGrafo[origem][destino] = peso;
            matGrafo[destino][origem] = peso;
        }
        arestas++;
    }

    public void alteraCoord(int vertice, int x, int y){
        if(vertice >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        matCoords[0][vertice] = x;
        matCoords[1][vertice] = y;
    }

    public int primeiroAdj(int vertice){
        if(vertice >= vertices){
            System.out.println("Vértice inexistente!");
            return -1;
        }
        for(int i = 0; i<vertices;i++){
            if(matGrafo[vertice][i] != -1){
                return i;
            }else{
                continue;
            }
        }
        return -1;
    }

    public int proxAdj(int origem,int destino){
        if(origem >= vertices || destino >= vertices){
            System.out.println("Vértice inexistente!");
            return -1;
        }
        for(int i = destino+1;i < vertices;i++){
            if(matGrafo[origem][i] != -1){
                return i;
            }else{
                continue;
            }
        }
        return -1;
    }

    public void listaAdj(int vertice){
        if(vertice >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        boolean achou = false;
        System.out.print("Adjacências: ");
        for(int i = 0; i < vertices ; i++){
            if(matGrafo[vertice][i] != -1){
                System.out.print(i + "\t");
                achou = true;
            }else{
                continue;
            }
        }
        if(!achou){
            System.out.print("Nenhuma adjacência encontrada!");
        }
        System.out.println();
    }

    public void buscaEmLargura(){
        int antecessor[] = new int[vertices];
        char cor[] = new char[vertices];
        int distancia[] = new int[vertices];
        int j = 0;
        for(int i = 0; i < vertices; i++){
            antecessor[i] = -1;
            cor[i] = 'B';
            distancia[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < vertices; i++){
            if(cor[i] == 'B'){
                visitaEmLargura(i,antecessor,cor,distancia);
            }
        }
        for(int i = 0; i < vertices; i++){
            System.out.println("Distancia vértice "+i+": "+distancia[i]);
        }
    }

    public void visitaEmLargura(int vertice, int antecessor[], char cor[], int distancia[]){
        int adjascente;
        int prox;
        cor[vertice] = 'C';
        distancia[vertice] = 0;
        Queue<Integer> fila = new LinkedList();
        fila.add(vertice);
        while(!fila.isEmpty()) {
            prox = fila.poll();
            adjascente = primeiroAdj(prox);
            while (adjascente != -1) {
                if (cor[adjascente] == 'B') {
                    fila.add(adjascente);
                    cor[adjascente] = 'C';
                    antecessor[adjascente] = prox;
                    distancia[adjascente] = distancia[prox] + 1;
                    adjascente = proxAdj(prox, adjascente);
                    System.out.println(adjascente);
                }else{
                    adjascente = proxAdj(prox, adjascente);
                }
            }
            cor[prox] = 'P';
        }
    }

    public void buscaEmProfundidade(){
        int antecessor[] = new int[vertices];
        char cor[] = new char[vertices];
        int descoberta[] = new int[vertices];
        int termino[] = new int[vertices];
        Integer tempo = 0;
        for(int i = 0; i < vertices; i++){
            descoberta[i] = -1;
            cor[i] = 'B';
        }
        for(int i = 0; i < vertices; i++){
            if(cor[i] == 'B'){
                tempo = visitaEmProfundidade(tempo, i, antecessor, cor, descoberta, termino);
            }
        }
        for(int i = 0; i < vertices; i++){
            System.out.println("Tempo de descoberta do vértice "+i+": "+descoberta[i]);
            System.out.println("Tempo de término do vértice "+i+": "+termino[i]);
        }
    }

    public int visitaEmProfundidade(Integer tempo, int vertice, int antecessor[], char cor[], int descoberta[], int termino[]){
        int adjascente;
        cor[vertice] = 'C';
        tempo++;
        descoberta[vertice] = tempo;
        adjascente = primeiroAdj(vertice);
        while(adjascente != -1){
            antecessor[adjascente] = vertice;
            if(cor[adjascente] == 'B'){
                tempo = visitaEmProfundidade(tempo,adjascente,antecessor,cor,descoberta,termino);
            }
            adjascente = proxAdj(vertice,adjascente);
        }
        cor[vertice] = 'P';
        tempo++;
        termino[vertice] = tempo;
        return tempo;
    }

    public void arvoreMinima(){
        boolean visitado[] = new boolean[vertices];
        int soma=0;
        Queue<Par> pares = new PriorityQueue<>(new ComparaPares());
        for(int i = 0; i < vertices; i ++){
            if(matGrafo[0][i] != -1){
                pares.add(new Par(matGrafo[0][i],i,0));
            }
        }
        visitado[0] = true;
        while(!pares.isEmpty()) {
            Par par = pares.poll();
            if (!visitado[par.destino]) {
                soma+=par.peso;
                System.out.println("O vértice "+par.destino+" entrou na arvore minima com uma aresta de peso "+par.peso);
                for (int i = 0; i < vertices; i++) {
                    if(matGrafo[par.destino][i] != -1){
                        pares.add(new Par(matGrafo[par.destino][i],i,par.destino));
                    }
                }
                visitado[par.destino] = true;

            }
        }
        System.out.println("O peso total da arvore minima é "+soma);
    }

    class ComparaPares implements Comparator<Par>{
        @Override
        public int compare(Par p1, Par p2){
            if(p1.peso < p2.peso){
                return -1;
            }if(p1.peso > p2.peso){
                return 1;
            }
            return 0;
        }
    }

    public void menorCaminho(int origem, int destino){
        Queue<Par> pares = new LinkedList<>();
        boolean visitado[] =  new boolean[vertices];
        int distancia[] = new int[vertices];
        int antecessor[] =  new int[vertices];
        for(int i = 0; i < vertices; i++){
            distancia[i] = Integer.MAX_VALUE;
            antecessor[i]=-1;
        }
        visitado[origem] = true;
        distancia[origem] = 0;
        for(int i = 0; i < vertices; i++){
            if(matGrafo[origem][i] != -1){
                pares.add(new Par(matGrafo[origem][i],i,origem));
            }
        }
        while(!pares.isEmpty()){
            Par par = pares.poll();
            if(!visitado[par.destino]){
                for (int i = 0; i < vertices; i++) {
                    if(matGrafo[par.destino][i] != -1){
                        pares.add(new Par(matGrafo[par.destino][i],i,par.destino));
                    }
                }
                visitado[par.destino] = true;
            }
            if(distancia[par.destino] > par.peso + distancia[par.origem]){
                antecessor[par.destino] = par.origem;
                distancia[par.destino] = par.peso + distancia[par.origem];
            }
        }
        int prox = destino;
        Stack<Integer> caminho = new Stack<>();
        System.out.print("O menor caminho de "+origem+" até "+destino+" é: ");
        do{
            caminho.add(prox);
            prox = antecessor[prox];
        }while(prox != -1);
        while(!caminho.isEmpty()){
            System.out.print(caminho.pop()+" ");
        }
        System.out.println();
    }

    public void desehnhaGrafo(Graphics g){
        Random r = new Random();
        for(int i = 0; i < vertices; i++){
            g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
            g.fillOval(matCoords[0][i]*Janela.escala,matCoords[1][i]*Janela.escala,20,20);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i),matCoords[0][i]*Janela.escala+10,matCoords[1][i]*Janela.escala+10);
        }
    }

    public void desenhaAresta(Graphics g){
        g.setColor(Color.BLACK);
        if(!direcionado) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (matGrafo[i][j] == -1 || j > i) {
                        continue;
                    }
                    g.drawLine(matCoords[0][i]*Janela.escala+10,matCoords[1][i]*Janela.escala+10,matCoords[0][j]*Janela.escala+10,matCoords[1][j]*Janela.escala+10);
                    g.drawString(String.valueOf(matGrafo[i][j]),((matCoords[0][i]+matCoords[0][j])/2)*5,((matCoords[1][i]+matCoords[1][j])/2)*5);
                }
            }
        }else{
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (matGrafo[i][j] == -1) {
                        continue;
                    }
                    drawArrow(g,matCoords[0][i]*Janela.escala+10,matCoords[1][i]*Janela.escala+10,matCoords[0][j]*Janela.escala+10,matCoords[1][j]*Janela.escala+10,20,10);
                    g.drawString(String.valueOf(matGrafo[i][j]),(matCoords[0][i]+matCoords[0][j])/2,(matCoords[1][i]+matCoords[1][j])/2);
                }
            }
        }
    }

    public void drawArrow(Graphics g, int x0, int y0, int x1, int y1, int headLength, int headAngle) {
        double offs = headAngle * Math.PI / 180.0;
        double angle = Math.atan2(y0 - y1, x0 - x1);
        int[] xs = { x1 + (int) (headLength * Math.cos(angle + offs)), x1,
                x1 + (int) (headLength * Math.cos(angle - offs)) };
        int[] ys = { y1 + (int) (headLength * Math.sin(angle + offs)), y1,
                y1 + (int) (headLength * Math.sin(angle - offs)) };
        g.drawLine(x0, y0, x1, y1);
        g.drawPolyline(xs, ys, 3);
    }
}
