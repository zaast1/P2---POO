README – Sistema de Gestão FitLife (Projeto POO)
Este projeto implementa um sistema básico de gerenciamento para uma academia, desenvolvido em Java como parte da disciplina de Programação Orientada a Objetos (POO). O objetivo principal é demonstrar conceitos fundamentais de POO, como herança, polimorfismo, abstração, encapsulamento, além de simular persistência de dados utilizando arquivos CSV.

Descrição Geral do Sistema
O sistema é dividido em módulos que gerenciam alunos, aulas, professores, modalidades, frequência e operações financeiras. A arquitetura foi organizada de forma a separar claramente as classes de domínio e as regras de negócio, utilizando serviços para realizar o carregamento e a persistência dos dados.

Funcionalidades Principais
1. Polimorfismo e Regras de Acesso (Planos de Academia)
   O sistema modela diferentes tipos de planos de assinatura, utilizando polimorfismo para definir regras de acesso:
   A classe abstrata Plano define o comportamento padrão e força suas subclasses a implementarem regras específicas de acesso.


As subclasses PlanoVip, PlanoMensal e PlanoAnual sobrescrevem o método temAcessoExclusivoAulas().


O PlanoVip concede acesso liberado às aulas exclusivas, enquanto os demais planos aplicam restrições.


A classe Aluno mantém internamente um objeto do tipo Plano, e os serviços chamam métodos polimórficos sem precisar saber qual plano está associado ao aluno.


2. Gestão de Domínio e Persistência em CSV
   Os dados do sistema são armazenados e carregados através de arquivos CSV, simulando a persistência de um banco de dados simples.
   As principais entidades são:
   Aluno


Professor


Modalidade


Aula


Frequencia


Transacao


A lógica de leitura e escrita desses dados é organizada nos serviços:
ServicoDeGestaoFitLife


ServicoFinanceiro


Esses serviços centralizam a lógica de negócio e garantem a separação de responsabilidades.
3. Módulo Financeiro e Relatórios com Java Streams
   O sistema registra pagamentos através da classe Transacao, que contém informações sobre valores, datas e categorias.
   A classe RelatorioFinanceiro utiliza Java Streams para:
   Calcular a receita total


Agregar valores por tipo de plano


Gerar relatórios compactos no estilo BI (Business Intelligence)


A abordagem com Streams permite consultas declarativas, concisas e eficientes.
4. Controle de Frequência
   A classe Frequencia registra entradas e saídas de alunos, associando cada registro a um aluno específico por meio de seu identificador.
   Isso permite acompanhar presença, pontualidade e movimentação interna da academia.

Estrutura de Classes (Descrição Simplificada)
A tabela abaixo apresenta um resumo textual das classes principais e seus papéis no sistema.
Classe                    | Conceitos de POO aplicados           | Responsabilidade
------------------------- | ------------------------------------ | -------------------------------
Plano (abstract)          | Abstração, herança, polimorfismo     | Modelo base para regras de planos
Aluno                     | Encapsulamento, associação           | Armazena dados do aluno e referência ao plano
ServicoDeGestaoFitLife    | SRP, serviços de negócio             | Gerencia acesso, cadastros e persistência CSV
RelatorioFinanceiro       | Java Streams                          | Gera relatórios financeiros e cálculos agregados
Modalidade, Professor     | Encapsulamento                        | Representam entidades de domínio
Aula                      | Encapsulamento                        | Relaciona modalidades e professores
Transacao                 | Encapsulamento                        | Registra operações financeiras
Frequencia                | Encapsulamento                        | Registra presença e horários


Como Executar o Projeto
Certifique-se de ter o JDK instalado e configurado.


Abra o projeto no IntelliJ IDEA.


Compile o projeto normalmente usando o menu Build ou através do Maven se estiver configurado.


Para executar o módulo de gestão:


Execute a classe:
com.fitlife.Main


Para executar o módulo financeiro:


Execute a classe:
com.fitlife.MainFinanceiro


Os dados de teste serão criados automaticamente se os arquivos CSV não existirem.
Caso existam, serão carregados dos seguintes arquivos:
professores.csv
modalidades.csv
aulas.csv
alunos.csv
transacoes.csv
frequencias.csv

