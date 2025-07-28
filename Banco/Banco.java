import java.util.Scanner;

// Classe para representar a Conta Bancaria
class ContaBancaria {
    private String numeroConta;
    private String nomeTitular;
    private double saldo;
    private double limiteChequeEspecial;
    private double valorUsadoChequeEspecial;
    private boolean usandoChequeEspecial;

    // Construtor
    public ContaBancaria(String numeroConta, String nomeTitular, double depositoInicial) {
        this.numeroConta = numeroConta;
        this.nomeTitular = nomeTitular;
        this.saldo = depositoInicial;
        this.valorUsadoChequeEspecial = 0.0;
        this.usandoChequeEspecial = false;
        definirLimiteChequeEspecial(depositoInicial);
        System.out.println("Conta criada com sucesso para " + nomeTitular + "!");
        System.out.printf("Saldo inicial: R$%.2f%n", this.saldo);
        System.out.printf("Limite de Cheque Especial: R$%.2f%n", this.limiteChequeEspecial);
    }

    // Método privado para definir o limite do cheque especial
    private void definirLimiteChequeEspecial(double depositoInicial) {
        if (depositoInicial <= 500.00) {
            this.limiteChequeEspecial = 50.00;
        } else {
            this.limiteChequeEspecial = depositoInicial * 0.50;
        }
    }

    // Consultar Saldo
    public void consultarSaldo() {
        System.out.printf("Saldo atual: R$%.2f%n", this.saldo);
        if (usandoChequeEspecial) {
            System.out.printf("Valor usado do Cheque Especial: R$%.2f%n", this.valorUsadoChequeEspecial);
            System.out.printf("Limite disponível do Cheque Especial: R$%.2f%n", (this.limiteChequeEspecial - this.valorUsadoChequeEspecial));
        }
    }

    // Consultar Cheque Especial
    public void consultarChequeEspecial() {
        System.out.printf("Seu limite total de Cheque Especial é: R$%.2f%n", this.limiteChequeEspecial);
        if (usandoChequeEspecial) {
            System.out.printf("Você está usando o Cheque Especial. Valor utilizado: R$%.2f%n", this.valorUsadoChequeEspecial);
            System.out.printf("Limite disponível no Cheque Especial: R$%.2f%n", (this.limiteChequeEspecial - this.valorUsadoChequeEspecial));
        } else {
            System.out.println("Você não está utilizando o Cheque Especial no momento.");
            System.out.printf("Limite total disponível no Cheque Especial: R$%.2f%n", this.limiteChequeEspecial);
        }
    }

    // Depositar Dinheiro
    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de depósito inválido. Digite um valor positivo.");
            return;
        }

        double valorUsadoAntesDoDeposito = this.valorUsadoChequeEspecial; // Guarda o valor antes para calcular a taxa

        this.saldo += valor;
        System.out.printf("Depósito de R$%.2f realizado com sucesso!%n", valor);

        // Lógica de cobertura do cheque especial e cobrança de taxa
        if (usandoChequeEspecial) {
            if (this.saldo >= valorUsadoAntesDoDeposito) { // Se o depósito cobre todo o cheque especial
                this.saldo -= valorUsadoAntesDoDeposito; // Paga o cheque especial com o saldo
                this.usandoChequeEspecial = false;
                this.valorUsadoChequeEspecial = 0.0;
                System.out.println("Cheque Especial totalmente coberto!");

                // Aplica a taxa de 20% sobre o valor que havia sido usado do cheque especial
                double taxa = valorUsadoAntesDoDeposito * 0.20;
                if (taxa > 0.0) { // Garante que só cobra se realmente usou e cobriu
                    this.saldo -= taxa;
                    System.out.printf("Taxa de 20%% (R$%.2f) do valor usado do Cheque Especial aplicada.%n", taxa);
                }
            } else { // O depósito cobre apenas parte do cheque especial
                this.valorUsadoChequeEspecial -= this.saldo; // Reduz o valor do cheque especial pelo saldo depositado
                this.saldo = 0.0; // Zera o saldo, pois foi todo usado para cobrir o cheque especial
                System.out.printf("R$%.2f do depósito foi usado para cobrir parte do Cheque Especial.%n", valor);
                System.out.printf("Ainda há R$%.2f pendente no Cheque Especial.%n", this.valorUsadoChequeEspecial);
            }
        }
        consultarSaldo();
    }


    // Sacar Dinheiro
    public void sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque inválido. Digite um valor positivo.");
            return;
        }

        double saldoDisponivelTotal = this.saldo + this.limiteChequeEspecial - this.valorUsadoChequeEspecial;

        if (valor > saldoDisponivelTotal) {
            System.out.println("Saldo insuficiente para realizar o saque. Saldo disponível (incluindo cheque especial): R$" + String.format("%.2f", saldoDisponivelTotal));
            return;
        }

        if (this.saldo >= valor) {
            this.saldo -= valor;
            System.out.printf("Saque de R$%.2f realizado com sucesso!%n", valor);
        } else {
            // Usando cheque especial
            double valorRestante = valor - this.saldo;
            this.saldo = 0; // Saldo da conta se esgotou
            this.valorUsadoChequeEspecial += valorRestante;
            this.usandoChequeEspecial = true;
            System.out.printf("Saque de R$%.2f realizado. Você está usando o Cheque Especial.%n", valor);
        }
        consultarSaldo();
    }

    // Pagar Boleto
    public void pagarBoleto(double valorBoleto) {
        if (valorBoleto <= 0) {
            System.out.println("Valor do boleto inválido. Digite um valor positivo.");
            return;
        }

        double saldoDisponivelTotal = this.saldo + this.limiteChequeEspecial - this.valorUsadoChequeEspecial;

        if (valorBoleto > saldoDisponivelTotal) {
            System.out.println("Saldo insuficiente para pagar o boleto. Saldo disponível (incluindo cheque especial): R$" + String.format("%.2f", saldoDisponivelTotal));
            return;
        }

        if (this.saldo >= valorBoleto) {
            this.saldo -= valorBoleto;
            System.out.printf("Boleto de R$%.2f pago com sucesso!%n", valorBoleto);
        } else {
            // Usando cheque especial para pagar boleto
            double valorRestante = valorBoleto - this.saldo;
            this.saldo = 0; // Saldo da conta se esgotou
            this.valorUsadoChequeEspecial += valorRestante;
            this.usandoChequeEspecial = true;
            System.out.printf("Boleto de R$%.2f pago. Você está usando o Cheque Especial.%n", valorBoleto);
        }
        consultarSaldo();
    }

    // Verificar se a conta está usando cheque especial
    public void verificarUsoChequeEspecial() {
        if (usandoChequeEspecial) {
            System.out.println("Você está usando o Cheque Especial. Valor utilizado: R$" + String.format("%.2f", this.valorUsadoChequeEspecial));
        } else {
            System.out.println("Você não está usando o Cheque Especial no momento.");
        }
    }
}

public class Banco {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContaBancaria minhaConta = null; // A conta será criada no menu

        int opcao;
        do {
            System.out.println("\n--- Menu Principal ---");
            if (minhaConta == null) {
                System.out.println("1. Criar Nova Conta");
            } else {
                System.out.println("1. (Conta já criada)");
            }
            System.out.println("2. Consultar Saldo");
            System.out.println("3. Consultar Cheque Especial");
            System.out.println("4. Depositar Dinheiro");
            System.out.println("5. Sacar Dinheiro");
            System.out.println("6. Pagar Boleto");
            System.out.println("7. Verificar Uso do Cheque Especial");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            // Validação de entrada para a opção
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // consome a entrada inválida
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após nextInt()

            // Impede que outras opções sejam escolhidas antes de criar a conta
            if (minhaConta == null && opcao != 1 && opcao != 0) {
                System.out.println("Por favor, crie uma conta primeiro (Opção 1).");
                continue; // Volta para o início do loop
            }

            switch (opcao) {
                case 1:
                    if (minhaConta != null) {
                        System.out.println("Uma conta já foi criada.");
                        break;
                    }
                    System.out.print("Digite o número da conta: ");
                    String numConta = scanner.nextLine();
                    System.out.print("Digite o nome do titular: ");
                    String nomeTitular = scanner.nextLine();
                    System.out.print("Digite o valor do depósito inicial: R$");
                    double depositoInicial = -1; // Inicializa com valor inválido
                    while (depositoInicial < 0) {
                        if (scanner.hasNextDouble()) {
                            depositoInicial = scanner.nextDouble();
                            if (depositoInicial < 0) {
                                System.out.println("Valor não pode ser negativo. Digite um valor válido: R$");
                            }
                        } else {
                            System.out.println("Entrada inválida. Digite um número para o depósito inicial: R$");
                            scanner.next(); // Consumir a entrada inválida
                        }
                    }
                    scanner.nextLine(); // Consumir a quebra de linha após nextDouble()
                    minhaConta = new ContaBancaria(numConta, nomeTitular, depositoInicial);
                    break;
                case 2:
                    minhaConta.consultarSaldo();
                    break;
                case 3:
                    minhaConta.consultarChequeEspecial();
                    break;
                case 4:
                    System.out.print("Digite o valor a depositar: R$");
                    double valorDeposito = -1;
                     while (valorDeposito < 0) {
                        if (scanner.hasNextDouble()) {
                            valorDeposito = scanner.nextDouble();
                            if (valorDeposito < 0) {
                                System.out.println("Valor não pode ser negativo. Digite um valor válido: R$");
                            }
                        } else {
                            System.out.println("Entrada inválida. Digite um número para o depósito: R$");
                            scanner.next(); // Consumir a entrada inválida
                        }
                    }
                    minhaConta.depositar(valorDeposito);
                    break;
                case 5:
                    System.out.print("Digite o valor a sacar: R$");
                    double valorSaque = -1;
                    while (valorSaque < 0) {
                        if (scanner.hasNextDouble()) {
                            valorSaque = scanner.nextDouble();
                             if (valorSaque < 0) {
                                System.out.println("Valor não pode ser negativo. Digite um valor válido: R$");
                            }
                        } else {
                            System.out.println("Entrada inválida. Digite um número para o saque: R$");
                            scanner.next(); // Consumir a entrada inválida
                        }
                    }
                    minhaConta.sacar(valorSaque);
                    break;
                case 6:
                    System.out.print("Digite o valor do boleto a pagar: R$");
                    double valorBoleto = -1;
                    while (valorBoleto < 0) {
                        if (scanner.hasNextDouble()) {
                            valorBoleto = scanner.nextDouble();
                             if (valorBoleto < 0) {
                                System.out.println("Valor não pode ser negativo. Digite um valor válido: R$");
                            }
                        } else {
                            System.out.println("Entrada inválida. Digite um número para o boleto: R$");
                            scanner.next(); // Consumir a entrada inválida
                        }
                    }
                    minhaConta.pagarBoleto(valorBoleto);
                    break;
                case 7:
                    minhaConta.verificarUsoChequeEspecial();
                    break;
                case 0:
                    System.out.println("Saindo do sistema bancário. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}