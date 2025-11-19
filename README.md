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

## 1. Personagem Controller (Gerenciamento Principal)

Esta seção lista os *endpoints* para operações de CRUD de personagens, manipulação de *status* e gestão de inventário.

| Método | URL | Descrição | Exemplo de Body (JSON) |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/personagens` | Cria um novo personagem. | `{"nome": "Kratos_God", "raca": "HUMANO", "classe": "GUERREIRO"}` |
| **`GET`** | `/personagens` | Lista todos os personagens. | *N/A* |
| **`GET`** | `/personagens/1000` | Busca um personagem pelo ID. | *N/A* |
| **`PUT`** | `/personagens/1000` | Atualiza os atributos editáveis de um personagem. | `{"nome": "Lord_Chaos_Renascido", "nivel": 181, "experiencia": 15500000, "pontosVidaMax": 5000, ...}` |
| **`DELETE`**| `/personagens/1008` | Deleta um personagem pelo ID. | *N/A* |
| **`POST`** | `/personagens/1000/ganhar-xp?xp=50000` | Adiciona XP ao personagem especificado. | *N/A* |
| **`POST`** | `/personagens/1000/receber-dano?dano=200` | Reduz os pontos de vida do personagem. | *N/A* |
| **`POST`** | `/personagens/1002/usar-mana?mana=500` | Consome pontos de mana do personagem. | *N/A* |
| **`POST`** | `/personagens/1000/inventario/adicionar` | Adiciona uma quantidade de um item ao inventário. | `{"itemId": 1009, "quantidade": 10}` |
| **`POST`** | `/personagens/1000/inventario/remover` | Remove uma quantidade de um item do inventário. | `{"itemId": 1009, "quantidade": 1}` |
| **`POST`** | `/personagens/1007/equipar/1002` | Equipa um item (1002) que está no inventário do personagem (1007). | *N/A* |
| **`POST`** | `/personagens/1000/desequipar/MAO_PRINCIPAL` | Desequipa o item do *slot* especificado. | *N/A* |
| **`POST`** | `/personagens/1000/logar` | Executa rotinas de verificação de *login*. | *N/A* |

---

## 2. Item Controller (Catálogo de Itens)

Endpoints para operações de CRUD de itens no catálogo do jogo.

| Método | URL | Descrição | Exemplo de Body (JSON) | Observações |
| :--- | :--- | :--- | :--- | :--- |
| **`POST`** | `/itens?nome=Adaga das Sombras&tipo=ARMA&slotEquipamento=MAO_PRINCIPAL` | Cria um novo item. | `{"ATAQUE_FISICO": 45, "PRECISAO": 100}` | Dados básicos via Query Params; Atributos via Body. |
| **`GET`** | `/itens` | Lista todos os itens. | *N/A* | *N/A* |
| **`GET`** | `/itens/1001` | Busca um item pelo ID. | *N/A* | *N/A* |
| **`PUT`** | `/itens/1001` | Atualiza o item especificado. | `{"nome": "Blade do Mithril +20 (Buffada)", "atributos": {"ATAQUE_FISICO": 300}}` | *N/A* |
| **`DELETE`**| `/itens/1012` | Deleta um item pelo ID. | *N/A* | *N/A* |

---

## 3. Guilda Controller (Organização Social)

Endpoints para gerenciamento de guildas e interações sociais entre personagens.

| Método | URL | Descrição | Exemplo de Body (JSON) | Observações |
| :--- | :--- | :--- | :--- | :--- |
| **`POST`** | `/guildas` | Cria uma nova guilda. | `{"nome": "Aliança Arcana", "descricao": "Apenas para magos de elite", "liderId": 1006}` | *N/A* |
| **`GET`** | `/guildas/1000` | Busca uma guilda pelo ID. | *N/A* | *N/A* |
| **`POST`** | `/guildas/convidar` | Envia um convite de guilda. | `{"convidanteId": 1003, "convidadoId": 1009}` | *N/A* |
| **`POST`** | `/guildas/aceitar-convite` | Personagem aceita o convite. | `{"personagemId": 1005, "guildaId": 1000}` | *N/A* |
| **`POST`** | `/guildas/recusar-convite` | Personagem recusa o convite. | `{"personagemId": 1007, "guildaId": 1000}` | *N/A* |
| **`POST`** | `/guildas/expulsar?guildaId=1000&expulsadorId=1000&expulsoId=1002` | Expulsa um membro da guilda. | *N/A* | IDs via Query Params. |
| **`POST`** | `/guildas/transferir-lideranca` | Transfere a liderança da guilda para outro membro. | `{"guildaId": 1001, "antigoLiderId": 1003, "novoLiderId": 1004}` | *N/A* |
| **`PUT`** | `/guildas/1000/descricao?alteradorId=1000&novaDescricao=Guilda Dominante do Server` | Altera a descrição da guilda. | *N/A* | ID do alterador e nova descrição via Query Params. |
| **`POST`** | `/guildas/1000/promover?promotorId=1000&promovidoId=1005&novoCargo=OFICIAL` | Promove/Demove um membro para um novo cargo. | *N/A* | IDs e cargo via Query Params. |

