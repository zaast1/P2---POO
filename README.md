# 🏋️ Sistema de Gestão FitLife

> Projeto final da disciplina de **Programação Orientada a Objetos (POO)**.

O **FitLife** é um sistema de gerenciamento de academia baseado em console, desenvolvido em Java, que aplica conceitos fundamentais como Polimorfismo, Herança, Encapsulamento e Persistência de Dados. O sistema visa substituir controles manuais por uma solução automatizada e integrada.

---

## 📋 Funcionalidades

O sistema é dividido em módulos integrados acessíveis via menu principal:

### 1. Gestão Administrativa (CRUD)
* **Cadastros:** Alunos, Professores, Modalidades e Aulas.
* **Edição e Remoção:** Permite atualizar dados (como nome/idade) e excluir registros do sistema.
* **Agendamento:** Criação de grade de aulas vinculando modalidade, professor e horário.
* **Validação de Negócio:** Bloqueio de cadastro de menores de 18 anos sem autorização expressa.

### 2. Controle de Acesso e Planos (Polimorfismo)
* **Lógica VIP:** Implementação de regras de acesso baseadas no tipo de plano.
* Alunos **VIP** (Polimorfismo) têm acesso irrestrito a aulas exclusivas e reservas.
* Alunos **Básicos/Mensais** visualizam apenas a grade padrão.

### 3. Módulo Financeiro Integrado
* **Fluxo de Caixa:** Registro de pagamentos de mensalidades com data e valor.
* **Integridade:** O sistema valida se o Aluno existe na base de dados antes de processar qualquer pagamento.
* **Status:** Consulta rápida para verificar se o aluno está em dia ou pendente.

### 4. Relatórios e Persistência
* **Relatórios em PDF:** Geração de relatórios gerenciais completos (Listas de alunos, professores e modalidades) utilizando a biblioteca **iText**.
* **Persistência em CSV:** Todos os dados são salvos automaticamente em arquivos locais (`alunos.csv`, `transacoes.csv`, etc.), garantindo que as informações persistam entre as execuções.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 17+)
* **Gerenciamento:** Maven
* **Bibliotecas Externas:**
    * `iText PDF (v5.5.13.4)` - Para geração de documentos PDF.
* **Conceitos:** Java IO (Leitura/Escrita de Arquivos), Java Streams API (para relatórios financeiros e filtros), POO Completa.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
1.  Ter o **Java JDK** instalado.
2.  Uma IDE Java (IntelliJ IDEA recomendado).
3.  Conexão com internet (para o Maven baixar o iText na primeira execução).

### Passo a Passo
1.  **Clonar/Baixar:** Faça o download deste repositório.
2.  **Abrir:** Abra a pasta do projeto na sua IDE.
3.  **Dependências:**
    * O projeto utiliza **Maven**. Ao abrir, aguarde o IntelliJ/Eclipse baixar as dependências listadas no `pom.xml` (especificamente o iText).
4.  **Executar:**
    * Localize a classe principal: `src/main/java/com/fitlife/Main.java`.
    * Execute o método `main`.
5.  **Utilização:**
    * O sistema criará automaticamente os arquivos `.csv` na raiz do projeto caso eles não existam.
    * Navegue pelo menu numérico no console para acessar as funcionalidades.

---

## 👥 Autores (Equipe)

* **Pedro Henrique Rodrigues Jacques Pinheiro** - *Configuração Central, Gestão de Aulas e Lógica de Serviço.*
* **José Airton Rodrigues Galdino Júnior** - *Hierarquia de Planos, Lógica de Alunos e Regras de Validação.*
* **Gabriel do Rego Lima Menezes** - *Módulo de Recursos Físicos e Validação de Histórico.*
* **Henrique França de Souza Medeiros Maranguape** - *Módulo Financeiro e Cálculos de Receita.*
* **Marina de Lima Fonseca** - *Implementação de Relatórios PDF (iText) e Documentação.*

---

**Nota:** Projeto desenvolvido para fins avaliativos da P2 de POO - 2025.