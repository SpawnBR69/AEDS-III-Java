import java.util.Scanner;

public class Main {

    public static void imprimeMenuG(){
        System.out.println("_________________________________________");
        System.out.println("|1. Criar grafo vazio.                  |");
        System.out.println("|2. Importar grafo de arquivo.          |");
        System.out.println("_________________________________________");
    }

    public static void imprimeMenu(){
        System.out.println("_________________________________________");
        System.out.println("|1. Imprimir grafo.                     |");
        System.out.println("|2. Consultar se um vértice é adjacente.|");
        System.out.println("|3. Inserir Aresta.                     |");
        System.out.println("|4. Remover Aresta.                     |");
        System.out.println("|5. Editar coordenadas do vértice.      |");
        System.out.println("|6. Editar nome do vértice.             |");
        System.out.println("|7. Consultar primeiro adjacente.       |");
        System.out.println("|8. Consultar próximo adjacente.        |");
        System.out.println("|9. Consultar lista de adjacentes.      |");
        System.out.println("|10. Desenhar Grafo.                    |");
        System.out.println("|11. Busca em Largura.                  |");
        System.out.println("|12. Busca em Profundidade.             |");
        System.out.println("|13. Gera Árvore Mínima.                |");
        System.out.println("|14. Encontrar menor Caminho.           |");
        System.out.println("|15. Sair.                              |");
        System.out.println("_________________________________________");
    }

    public static void main(String[] args){
        int escolha = 0;
        int tamanho;
        int origem;
        int destino;
        int peso;
        int x,y;
        char escolhaC;
        String nome;
        Scanner e = new Scanner(System.in);
        Grafo grafo = new Grafo();
        imprimeMenuG();
        escolha = e.nextInt();
        if(escolha == 1){
            System.out.println("Digite o tamanho do grafo desejado: ");
            tamanho = e.nextInt();
            System.out.println("O grafo é direcionado?(1 = Sim/2 = Não)");
            int direcionado = e.nextInt();
            boolean dire;
            if(direcionado == 1)
                dire = true;
            else
                dire = false;
            grafo.criaGrafoVazio(tamanho,dire);
        }else{
            grafo.lerArquivo("src/grafo.txt");
        }
        while(escolha != -1){
            imprimeMenu();
            System.out.println("Digite sua escolha: ");
            escolha = e.nextInt();
            switch (escolha){
                case 1:
                    grafo.imprimeGrafo();
                    break;
                case 2:
                    System.out.println("Digite o vértice de origem: ");
                    origem = e.nextInt();
                    System.out.println("Digite o vértice de destino: ");
                    destino = e.nextInt();
                    int adjacencia = grafo.consultaAdj(origem,destino);
                    if(adjacencia != -1){
                        System.out.println("É adjacente e o peso da aresta é: "+adjacencia);
                    }else{
                        System.out.println("Não são adjacentes!");
                    }
                    break;
                case 3:
                    System.out.println("Digite o vértice de origem: ");
                    origem = e.nextInt();
                    System.out.println("Digite o vértice de destino: ");
                    destino = e.nextInt();
                    System.out.println("Digite o peso da aresta: ");
                    peso = e.nextInt();
                    grafo.insereAresta(origem,destino,peso);
                    break;
                case 4:
                    System.out.println("Digite o vértice de origem: ");
                    origem = e.nextInt();
                    System.out.println("Digite o vértice de destino: ");
                    destino = e.nextInt();
                    grafo.removeAresta(origem,destino);
                    break;
                case 5:
                    System.out.println("Digite o vértice: ");
                    origem = e.nextInt();
                    System.out.println("Digite a coordenada X: ");
                    x = e.nextInt();
                    System.out.println("Digite a coordenada Y: ");
                    y = e.nextInt();
                    grafo.alteraCoord(origem,x,y);
                    break;
                case 6:
                    System.out.println("Digite o vértice: ");
                    origem = e.nextInt();
                    System.out.println("Digite o novo nome do vértice: ");
                    e.skip("\n");
                    nome = e.nextLine();
                    grafo.setMatNomes(origem,nome);
                    break;
                case 7:
                    System.out.println("Digite o vértice: ");
                    origem = e.nextInt();
                    if(grafo.primeiroAdj(origem) != -1){
                        System.out.println("A primeira adjacência deste vértice é: " + grafo.primeiroAdj(origem));
                    }else{
                        System.out.println("Esse vértice não possui adjacências!");
                    }
                    break;
                case 8:
                    System.out.println("Digite o vértice de origem: ");
                    origem = e.nextInt();
                    System.out.println("Digite o vértice de destino: ");
                    destino = e.nextInt();
                    if(grafo.proxAdj(origem,destino) != -1){
                        System.out.println("O próximo adjacente desse vértice é: " + grafo.proxAdj(origem,destino));
                    }else{
                        System.out.println("Esse vértice não possui próxima adjacência!");
                    }
                    break;
                case 9:
                    System.out.println("Digite o vértice: ");
                    origem = e.nextInt();
                    grafo.listaAdj(origem);
                    break;
                case 10:
                    Janela janela  = new Janela(grafo);
                    janela.repaint();
                    break;
                case 11:
                    grafo.buscaEmLargura();
                    break;
                case 12:
                    grafo.buscaEmProfundidade();
                    break;
                case 13:
                    grafo.arvoreMinima();
                    break;
                case 14:
                    grafo.printNomes();
                    System.out.println("Digite o nome da origem: ");
                    e.skip("\n");
                    nome = e.nextLine();
                    origem = grafo.getMatNomes(nome);
                    if(origem == -1) {
                        System.out.println("Origem Inexistente");
                        break;
                    }
                    System.out.println("Digite o nome do destino: ");
                    nome = e.nextLine();
                    destino = grafo.getMatNomes(nome);
                    if(destino == -1) {
                        System.out.println("Destino Inexistente");
                        break;
                    }
                    grafo.menorCaminho(origem,destino);
                    break;
                default:
                    System.out.println("Deseja sair da aplicação?(s/n)");
                    e.nextLine();
                    escolhaC = e.nextLine().charAt(0);
                    if(escolhaC != 's'){
                        escolha = 0;
                    }else{
                        escolha = -1;
                    }
                    break;
            }
        }
        grafo.exportaGrafo();
    }
}
