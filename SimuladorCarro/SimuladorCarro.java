import java.util.Scanner;

// Classe para representar o Carro
class Carro {
    private boolean ligado;
    private int velocidadeAtual; // em km/h
    private int marchaAtual; // 0 para ponto morto, 1 a 6 para as marchas

    // Construtor: O carro começa desligado, em ponto morto e velocidade 0
    public Carro() {
        this.ligado = false;
        this.velocidadeAtual = 0;
        this.marchaAtual = 0; // Ponto morto
        System.out.println("Carro criado. Atualmente desligado, em ponto morto e velocidade 0 km/h.");
    }

    // Ligar o carro
    public void ligar() {
        if (!ligado) {
            ligado = true;
            System.out.println("Carro ligado!");
        } else {
            System.out.println("O carro já está ligado.");
        }
    }

    // Desligar o carro
    public void desligar() {
        if (ligado) {
            if (marchaAtual == 0 && velocidadeAtual == 0) {
                ligado = false;
                System.out.println("Carro desligado.");
            } else {
                System.out.println("Para desligar, o carro deve estar em ponto morto (marcha 0) e com velocidade 0 km/h.");
                System.out.println("Velocidade atual: " + velocidadeAtual + " km/h, Marcha atual: " + marchaAtual);
            }
        } else {
            System.out.println("O carro já está desligado.");
        }
    }

    // Acelerar
    public void acelerar() {
        if (!ligado) {
            System.out.println("Carro desligado. Não é possível acelerar.");
            return;
        }

        if (velocidadeAtual >= 120) {
            System.out.println("Velocidade máxima atingida (120 km/h).");
            return;
        }

        // Verifica o limite de velocidade para a marcha atual
        if (!podeAcelerarNaMarchaAtual(velocidadeAtual + 1)) {
            System.out.println("Não é possível acelerar para " + (velocidadeAtual + 1) + " km/h nesta marcha (" + marchaAtual + "ª). Troque a marcha para cima.");
            return;
        }

        if (marchaAtual == 0) {
            System.out.println("Carro em ponto morto (marcha 0). Não é possível acelerar. Troque a marcha para cima.");
            return;
        }

        velocidadeAtual += 1;
        System.out.println("Acelerando... Velocidade atual: " + velocidadeAtual + " km/h. Marcha: " + marchaAtual + "ª");
    }

    // Diminuir velocidade
    public void diminuirVelocidade() {
        if (!ligado) {
            System.out.println("Carro desligado. Não é possível diminuir velocidade.");
            return;
        }

        if (velocidadeAtual <= 0) {
            System.out.println("O carro já está parado (0 km/h).");
            return;
        }

        // Verifica o limite de velocidade para a marcha atual ao diminuir
        if (!podeDiminuirNaMarchaAtual(velocidadeAtual - 1)) {
             System.out.println("Para diminuir para " + (velocidadeAtual - 1) + " km/h, é recomendado reduzir a marcha. Marcha atual: " + marchaAtual + "ª");
        }

        velocidadeAtual -= 1;
        System.out.println("Diminuindo velocidade... Velocidade atual: " + velocidadeAtual + " km/h. Marcha: " + marchaAtual + "ª");

        // Regra implícita: se a velocidade cair para 0, a marcha deve voltar para 0
        if (velocidadeAtual == 0 && marchaAtual != 0) {
            marchaAtual = 0;
            System.out.println("Velocidade atingiu 0 km/h. Carro em ponto morto (marcha 0).");
        }
    }

    // Virar para esquerda/direita
    public void virar(String direcao) {
        if (!ligado) {
            System.out.println("Carro desligado. Não é possível virar.");
            return;
        }

        if (velocidadeAtual >= 1 && velocidadeAtual <= 40) {
            System.out.println("Virando para a " + direcao + ".");
        } else {
            System.out.println("Não é possível virar. A velocidade deve estar entre 1 km/h e 40 km/h.");
            System.out.println("Velocidade atual: " + velocidadeAtual + " km/h.");
        }
    }

    // Verificar velocidade
    public void verificarVelocidade() {
        System.out.println("Velocidade atual: " + velocidadeAtual + " km/h.");
        System.out.println("Marcha atual: " + marchaAtual + "ª.");
        if (ligado) {
            System.out.println("Carro ligado.");
        } else {
            System.out.println("Carro desligado.");
        }
    }

    // Trocar a marcha
    public void trocarMarcha(int novaMarcha) {
        if (!ligado) {
            System.out.println("Carro desligado. Não é possível trocar a marcha.");
            return;
        }
        if (novaMarcha < 0 || novaMarcha > 6) {
            System.out.println("Marcha inválida. As marchas válidas são de 0 a 6.");
            return;
        }

        // Não deve ser permitido pular uma marcha
        if (novaMarcha > marchaAtual + 1 || novaMarcha < marchaAtual - 1) {
            System.out.println("Não é permitido pular marchas. Tente trocar para a marcha " + (marchaAtual + 1) + " ou " + (marchaAtual - 1) + ".");
            return;
        }

        // Verifica se a velocidade está dentro do limite da nova marcha
        if (!velocidadePermitidaParaMarcha(novaMarcha, velocidadeAtual)) {
            System.out.println("Não é possível engatar a " + novaMarcha + "ª marcha com a velocidade atual (" + velocidadeAtual + " km/h). Ajuste a velocidade.");
            return;
        }

        marchaAtual = novaMarcha;
        System.out.println("Marcha trocada para: " + marchaAtual + "ª.");
    }

    // Métodos auxiliares para encapsular as regras de velocidade por marcha
    private boolean velocidadePermitidaParaMarcha(int marcha, int velocidade) {
        switch (marcha) {
            case 0: return velocidade == 0; // Ponto morto apenas com velocidade 0
            case 1: return velocidade >= 0 && velocidade <= 20;
            case 2: return velocidade >= 21 && velocidade <= 40;
            case 3: return velocidade >= 41 && velocidade <= 60;
            case 4: return velocidade >= 61 && velocidade <= 80;
            case 5: return velocidade >= 81 && velocidade <= 100;
            case 6: return velocidade >= 101 && velocidade <= 120;
            default: return false;
        }
    }

    // Método para verificar se pode acelerar e continuar na marcha atual
    private boolean podeAcelerarNaMarchaAtual(int velocidadeFutura) {
         if (marchaAtual == 0) return false; // Não acelera em ponto morto
         return velocidadePermitidaParaMarcha(marchaAtual, velocidadeFutura);
    }

    // Método para verificar se pode diminuir a velocidade e continuar na marcha atual (apenas informativo)
    private boolean podeDiminuirNaMarchaAtual(int velocidadeFutura) {
        if (velocidadeFutura < 0) return true; // Pode diminuir até 0
        return velocidadePermitidaParaMarcha(marchaAtual, velocidadeFutura);
    }
}

public class SimuladorCarro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Carro meuCarro = new Carro();
        int opcao;

        do {
            System.out.println("\n--- Simulador de Carro ---");
            System.out.println("1. Ligar Carro");
            System.out.println("2. Desligar Carro");
            System.out.println("3. Acelerar");
            System.out.println("4. Diminuir Velocidade");
            System.out.println("5. Virar para Esquerda");
            System.out.println("6. Virar para Direita");
            System.out.println("7. Verificar Velocidade e Estado");
            System.out.println("8. Trocar Marcha");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    meuCarro.ligar();
                    break;
                case 2:
                    meuCarro.desligar();
                    break;
                case 3:
                    meuCarro.acelerar();
                    break;
                case 4:
                    meuCarro.diminuirVelocidade();
                    break;
                case 5:
                    meuCarro.virar("Esquerda");
                    break;
                case 6:
                    meuCarro.virar("Direita");
                    break;
                case 7:
                    meuCarro.verificarVelocidade();
                    break;
                case 8:
                    System.out.print("Digite a nova marcha (0 a 6): ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Entrada inválida. Digite um número para a marcha.");
                        scanner.next();
                        System.out.print("Digite a nova marcha (0 a 6): ");
                    }
                    int novaMarcha = scanner.nextInt();
                    meuCarro.trocarMarcha(novaMarcha);
                    break;
                case 0:
                    System.out.println("Saindo do simulador de carro. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}