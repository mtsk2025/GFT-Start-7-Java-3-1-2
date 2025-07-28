package PetShop_Banho;
import java.util.Scanner;

// Classe para representar a Máquina de Banho
class MaquinaDeBanho {
    private String petNaMaquina; // Nome do pet, null se não houver pet
    private double nivelAgua; // Litros
    private double nivelShampoo; // Litros
    private boolean suja; // Indica se precisa de limpeza após um banho incompleto

    private final double CAPACIDADE_MAX_AGUA = 30.0;
    private final double CAPACIDADE_MAX_SHAMPOO = 10.0;
    private final double CONSUMO_AGUA_BANHO = 10.0;
    private final double CONSUMO_SHAMPOO_BANHO = 2.0;
    private final double CONSUMO_AGUA_LIMPEZA = 3.0;
    private final double CONSUMO_SHAMPOO_LIMPEZA = 1.0;
    private final double ABASTECIMENTO_PADRAO = 2.0; // 2 litros por vez

    // Construtor
    public MaquinaDeBanho() {
        this.petNaMaquina = null;
        this.nivelAgua = 0.0;
        this.nivelShampoo = 0.0;
        this.suja = false;
        System.out.println("Máquina de Banho inicializada. Sem pet, vazia e limpa.");
    }

    // Colocar pet na máquina
    public void colocarPet(String nomePet) {
        if (petNaMaquina != null) {
            System.out.println("Já existe um pet na máquina: " + petNaMaquina + ". Remova-o primeiro.");
            return;
        }
        if (suja) {
            System.out.println("A máquina está suja e precisa ser limpa antes de colocar um novo pet.");
            return;
        }
        this.petNaMaquina = nomePet;
        System.out.println(nomePet + " foi colocado na máquina de banho.");
    }

    // Retirar pet da máquina
    public void retirarPet() {
        if (petNaMaquina == null) {
            System.out.println("Não há pet na máquina para ser retirado.");
            return;
        }
        System.out.println(petNaMaquina + " foi retirado da máquina.");
        // Se o pet foi retirado sem banho, a máquina fica suja
        if (suja) { // Indica que um banho foi iniciado mas não finalizado
             System.out.println("AVISO: O pet foi retirado sem terminar o banho. A máquina está suja e precisa de limpeza.");
        }
        this.petNaMaquina = null; // Remove o pet
    }


    // Dar banho no pet
    public void darBanho() {
        if (petNaMaquina == null) {
            System.out.println("Não há pet na máquina para dar banho.");
            return;
        }
        if (suja) {
            System.out.println("A máquina está suja. Limpe-a antes de iniciar um novo banho.");
            return;
        }
        if (nivelAgua < CONSUMO_AGUA_BANHO) {
            System.out.printf("Água insuficiente para o banho. Nível atual: %.2f L. Necessário: %.2f L.%n", nivelAgua, CONSUMO_AGUA_BANHO);
            return;
        }
        if (nivelShampoo < CONSUMO_SHAMPOO_BANHO) {
            System.out.printf("Shampoo insuficiente para o banho. Nível atual: %.2f L. Necessário: %.2f L.%n", nivelShampoo, CONSUMO_SHAMPOO_BANHO);
            return;
        }

        System.out.println("Iniciando banho em " + petNaMaquina + "...");
        nivelAgua -= CONSUMO_AGUA_BANHO;
        nivelShampoo -= CONSUMO_SHAMPOO_BANHO;
        System.out.println("Banho de " + petNaMaquina + " concluído! Pet limpo.");
        suja = true; // Máquina fica suja após cada banho (limpo ou não) para simular necessidade de limpeza
        // No caso de banho concluído, a sujeira é devido ao uso, não por interrupção
        // O próximo pet só poderá entrar após a limpeza.
        System.out.println("Por favor, retire o pet da máquina e limpe-a para o próximo uso.");
    }

    // Abastecer com água
    public void abastecerAgua() {
        if (nivelAgua + ABASTECIMENTO_PADRAO > CAPACIDADE_MAX_AGUA) {
            System.out.printf("Capacidade máxima de água atingida. Nível atual: %.2f L. Não é possível adicionar mais %.2f L.%n", nivelAgua, ABASTECIMENTO_PADRAO);
            return;
        }
        nivelAgua += ABASTECIMENTO_PADRAO;
        System.out.printf("Água abastecida (+%.2f L). Nível atual de água: %.2f L.%n", ABASTECIMENTO_PADRAO, nivelAgua);
    }

    // Abastecer com shampoo
    public void abastecerShampoo() {
        if (nivelShampoo + ABASTECIMENTO_PADRAO > CAPACIDADE_MAX_SHAMPOO) {
            System.out.printf("Capacidade máxima de shampoo atingida. Nível atual: %.2f L. Não é possível adicionar mais %.2f L.%n", nivelShampoo, ABASTECIMENTO_PADRAO);
            return;
        }
        nivelShampoo += ABASTECIMENTO_PADRAO;
        System.out.printf("Shampoo abastecido (+%.2f L). Nível atual de shampoo: %.2f L.%n", ABASTECIMENTO_PADRAO, nivelShampoo);
    }

    // Verificar nível de água
    public void verificarNivelAgua() {
        System.out.printf("Nível de água: %.2f L / %.2f L (Máximo)%n", nivelAgua, CAPACIDADE_MAX_AGUA);
    }

    // Verificar nível de shampoo
    public void verificarNivelShampoo() {
        System.out.printf("Nível de shampoo: %.2f L / %.2f L (Máximo)%n", nivelShampoo, CAPACIDADE_MAX_SHAMPOO);
    }

    // Verificar se tem pet no banho
    public void verificarPetNaMaquina() {
        if (petNaMaquina != null) {
            System.out.println("Há um pet na máquina: " + petNaMaquina);
        } else {
            System.out.println("Não há pet na máquina.");
        }
    }

    // Limpar máquina
    public void limparMaquina() {
        if (!suja) {
            System.out.println("A máquina já está limpa.");
            return;
        }
        if (petNaMaquina != null) {
            System.out.println("Retire o pet da máquina antes de iniciar a limpeza.");
            return;
        }
        if (nivelAgua < CONSUMO_AGUA_LIMPEZA) {
            System.out.printf("Água insuficiente para limpar a máquina. Nível atual: %.2f L. Necessário: %.2f L.%n", nivelAgua, CONSUMO_AGUA_LIMPEZA);
            return;
        }
        if (nivelShampoo < CONSUMO_SHAMPOO_LIMPEZA) { // Shampoo para sanitizar, por exemplo
            System.out.printf("Shampoo (ou produto de limpeza) insuficiente para limpar a máquina. Nível atual: %.2f L. Necessário: %.2f L.%n", nivelShampoo, CONSUMO_SHAMPOO_LIMPEZA);
            return;
        }

        nivelAgua -= CONSUMO_AGUA_LIMPEZA;
        nivelShampoo -= CONSUMO_SHAMPOO_LIMPEZA;
        suja = false;
        System.out.println("Máquina limpa com sucesso!");
        System.out.printf("Consumo de %.2f L de água e %.2f L de shampoo para limpeza.%n", CONSUMO_AGUA_LIMPEZA, CONSUMO_SHAMPOO_LIMPEZA);
    }
}

public class PetShopBanho {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MaquinaDeBanho maquina = new MaquinaDeBanho();
        int opcao;

        do {
            System.out.println("\n--- Controle da Máquina de Banho Pet Shop ---");
            System.out.println("1. Colocar Pet na Máquina");
            System.out.println("2. Retirar Pet da Máquina");
            System.out.println("3. Dar Banho no Pet");
            System.out.println("4. Abastecer Água");
            System.out.println("5. Abastecer Shampoo");
            System.out.println("6. Verificar Nível de Água");
            System.out.println("7. Verificar Nível de Shampoo");
            System.out.println("8. Verificar se Tem Pet na Máquina");
            System.out.println("9. Limpar Máquina");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // Consumir a entrada inválida
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do pet a ser colocado: ");
                    String nomePetColocar = scanner.nextLine();
                    maquina.colocarPet(nomePetColocar);
                    break;
                case 2:
                    maquina.retirarPet();
                    break;
                case 3:
                    maquina.darBanho();
                    break;
                case 4:
                    maquina.abastecerAgua();
                    break;
                case 5:
                    maquina.abastecerShampoo();
                    break;
                case 6:
                    maquina.verificarNivelAgua();
                    break;
                case 7:
                    maquina.verificarNivelShampoo();
                    break;
                case 8:
                    maquina.verificarPetNaMaquina();
                    break;
                case 9:
                    maquina.limparMaquina();
                    break;
                case 0:
                    System.out.println("Saindo do controle da máquina de banho. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}