-----

# ⚔️ Avalon API - Backend Documentation 🛡️

A **Avalon API** é o *backend* do sistema de gerenciamento de dados de um projeto de RPG/MMORPG, responsável por Personagens, Itens e Guildas. Esta documentação abrange a arquitetura central e a referência completa dos *endpoints* da API.

-----

## 📐 Padrão de Arquitetura: Status Strategy

O cálculo dos atributos base (HP, Mana, Ataques, Defesas, etc.) de um personagem é determinado por sua Raça/Classe, que define uma curva de crescimento específica.

Para garantir flexibilidade e manutenibilidade, o projeto implementa o padrão de *design* **Strategy**. Cada Raça ou Classe é mapeada para uma estratégia de cálculo que implementa a interface `EstrategiaStatus`.

### Interface Principal (`EstrategiaStatus`)

Esta interface define o contrato para todas as lógicas de cálculo de atributos base, recebendo o `nivel` como parâmetro:

```java
public interface EstrategiaStatus {
    int getAtaqueMagicoBase(int nivel);
    int getAtaqueFisicoBase(int nivel);
    int getDefesaMagicaBase(int nivel);
    // ... outros getters para DefesaFisica, Precisao, Destreza
    int getPontosVidaBase(int nivel);
    int getPontosManaBase(int nivel);
}
```

### Exemplos de Fórmulas de Status Base

Abaixo estão alguns exemplos de como as estratégias concretas definem os *status* de um personagem.

| Estratégia | Pontos de Vida (HP Max) | Pontos de Mana (MP Max) | Ataque Físico |
| :--- | :--- | :--- | :--- |
| **Guerreiro** | $150 + (Nível \times 15)$ | $20 + (Nível \times 2)$ | $15 + (Nível \times 4)$ |
| **Mago** | $70 + (Nível \times 6)$ | $200 + (Nível \times 20)$ | $5 + (Nível \times 1)$ |
| **Humano** | $100 + (Nível \times 10)$ | $50 + (Nível \times 5)$ | $10 + (Nível \times 3)$ |

-----

## 🌐 Referência de Endpoints

A URL base para todos os acessos é **`http://localhost:8080/api`**.

### 1\. Personagem Controller

Endpoints para CRUD de Personagem e manipulação de *status* e inventário.

| Método | URL | Descrição | Exemplo de Body |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/personagens` | **Cria** um novo personagem. | `{"nome": "Kratos_God", "raca": "HUMANO", "classe": "GUERREIRO"}` |
| **`GET`** | `/personagens` | **Lista** todos os personagens. | *N/A* |
| **`GET`** | `/personagens/{id}` | **Busca** um personagem específico. | *N/A* |
| **`PUT`** | `/personagens/1000` | **Atualiza** dados principais. | `{"nome": "Lord_Chaos_Renascido", "nivel": 181, "pontosVidaMax": 5000, ...}` |
| **`DELETE`** | `/personagens/1008` | **Deleta** um personagem. | *N/A* |
| **`POST`** | `/personagens/1000/ganhar-xp?xp=50000` | Adiciona XP ao personagem. | *N/A* |
| **`POST`** | `/personagens/1000/inventario/adicionar` | Adiciona item ao inventário. | `{"itemId": 1009, "quantidade": 10}` |
| **`POST`** | `/personagens/1007/equipar/1002` | **Equipa** item (1002) no personagem (1007). | *N/A* |
| **`POST`** | `/personagens/1000/desequipar/MAO_PRINCIPAL` | **Desequipa** item de um *slot*. | *N/A* |

### 2\. Item Controller

Endpoints para CRUD de Itens e gestão de atributos.

| Método | URL | Descrição | Exemplo de Body / Observações |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/itens?nome=Adaga das Sombras&tipo=ARMA&slotEquipamento=MAO_PRINCIPAL` | **Cria** um novo item. | **Body:** `{"ATAQUE_FISICO": 45, "PRECISAO": 100}` (Atributos no Body, Nome/Tipo/Slot na URL) |
| **`GET`** | `/itens` | **Lista** todos os itens. | *N/A* |
| **`GET`** | `/itens/1001` | **Busca** um item específico. | *N/A* |
| **`PUT`** | `/itens/1001` | **Atualiza** dados e atributos do item. | `{"nome": "Blade do Mithril +20 (Buffada)", "atributos": {"ATAQUE_FISICO": 300}}` |
| **`DELETE`** | `/itens/1012` | **Deleta** um item. | *N/A* |

### 3\. Guilda Controller

Endpoints para gerenciamento de Guildas e interações entre membros (convites, cargos, etc.).

| Método | URL | Descrição | Exemplo de Body / Observações |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/guildas` | **Cria** uma nova guilda. | `{"nome": "Aliança Arcana", "liderId": 1006}` |
| **`GET`** | `/guildas/1000` | **Busca** uma guilda específica. | *N/A* |
| **`POST`** | `/guildas/convidar` | Envia um convite de guilda. | `{"convidanteId": 1003, "convidadoId": 1009}` |
| **`POST`** | `/guildas/aceitar-convite` | Personagem aceita o convite. | `{"personagemId": 1005, "guildaId": 1000}` |
| **`POST`** | `/guildas/expulsar?guildaId=1000&expulsadorId=1000&expulsoId=1002` | **Expulsa** um membro. | IDs da guilda, expulsador e expulso via *Query Parameters*. |
| **`POST`** | `/guildas/transferir-lideranca` | **Transfere** a liderança da guilda. | `{"guildaId": 1001, "antigoLiderId": 1003, "novoLiderId": 1004}` |
| **`PUT`** | `/guildas/1000/descricao?alteradorId=1000&novaDescricao=Guilda Dominante do Server` | **Muda** a descrição. | Nova descrição e alterador ID via *Query Parameters*. |
| **`POST`** | `/guildas/1000/promover?promotorId=1000&promovidoId=1005&novoCargo=OFICIAL` | **Promove** um membro a um novo cargo. | IDs e cargo via *Query Parameters*. |

-----

Este formato deve ser renderizado corretamente no GitHub ou em qualquer visualizador Markdown, oferecendo a clareza e o detalhe que você precisa.

Se precisar de qualquer ajuste ou de uma seção de "Como Começar" (Instalação/Configuração), é só me dizer\!
