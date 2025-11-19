<documentação-api>
    <titulo>API Avalon - Backend</titulo>
    <subtitulo>Documentação dos Endpoints e Arquitetura de Status</subtitulo>
    
    <seção id="arquitetura-status">
        <titulo>Padrão Strategy para Cálculo de Status Base</titulo>
        <descrição>
            O cálculo dos atributos base de um personagem (HP, Mana, Ataques, Defesas, Precisão e Destreza) é implementado utilizando o padrão **Strategy**.
            Este padrão desacopla o algoritmo de cálculo do objeto Personagem, permitindo que cada Raça ou Classe (Estratégia) defina sua própria curva de crescimento baseada no Nível.
        </descrição>

        <subseção id="interface-estrategia">
            <titulo>Interface EstrategiaStatus</titulo>
            <código-java>
                <![CDATA[
public interface EstrategiaStatus {
    int getAtaqueMagicoBase(int nivel);
    int getAtaqueFisicoBase(int nivel);
    int getDefesaMagicaBase(int nivel);
    int getDefesaFisicaBase(int nivel);
    int getPrecisaoBase(int nivel);
    int getDestrezaBase(int nivel);
    int getPontosVidaBase(int nivel);
    int getPontosManaBase(int nivel);
}
                ]]>
            </código-java>
        </subseção>

        <subseção id="exemplos-formulas">
            <titulo>Fórmulas de Cálculo (Exemplos)</titulo>
            <tabela>
                <colunas>
                    <coluna>Estratégia</coluna>
                    <coluna>HP Base (getPontosVidaBase)</coluna>
                    <coluna>Mana Base (getPontosManaBase)</coluna>
                    <coluna>ATK Físico Base</coluna>
                    <coluna>ATK Mágico Base</coluna>
                </colunas>
                <linhas>
                    <linha>
                        <celula>Guerreiro</celula>
                        <celula>$150 + (nivel \times 15)$</celula>
                        <celula>$20 + (nivel \times 2)$</celula>
                        <celula>$15 + (nivel \times 4)$</celula>
                        <celula>$2 + (nivel \times 1)$</celula>
                    </linha>
                    <linha>
                        <celula>Mago</celula>
                        <celula>$70 + (nivel \times 6)$</celula>
                        <celula>$200 + (nivel \times 20)$</celula>
                        <celula>$5 + (nivel \times 1)$</celula>
                        <celula>$12 + (nivel \times 4)$</celula>
                    </linha>
                    <linha>
                        <celula>Humano</celula>
                        <celula>$100 + (nivel \times 10)$</celula>
                        <celula>$50 + (nivel \times 5)$</celula>
                        <celula>$10 + (nivel \times 3)$</celula>
                        <celula>$5 + (nivel \times 2)$</celula>
                    </linha>
                </linhas>
            </tabela>
        </subseção>
    </seção>

    <seção id="documentacao-endpoints">
        <titulo>Endpoints da API (Testes Postman)</titulo>
        <base-url>http://localhost:8080/api</base-url>

        <controller nome="Personagem Controller">
            <endpoint>
                <nome>1. Criar Personagem</nome>
                <metodo>POST</metodo>
                <url>/personagens</url>
                <body-json>
                    <![CDATA[{"nome": "Kratos_God", "raca": "HUMANO", "classe": "GUERREIRO"}]]>
                </body-json>
            </endpoint>
            <endpoint>
                <nome>2. Listar Todos</nome>
                <metodo>GET</metodo>
                <url>/personagens</url>
            </endpoint>
            <endpoint>
                <nome>4. Atualizar Personagem</nome>
                <metodo>PUT</metodo>
                <url>/personagens/1000</url>
                <body-json>
                    <![CDATA[{"nome": "Lord_Chaos_Renascido", "nivel": 181, "experiencia": 15500000, "pontosVidaAtual": 5000, "pontosVidaMax": 5000}]]>
                </body-json>
            </endpoint>
            <endpoint>
                <nome>6. Ganhar XP</nome>
                <metodo>POST</metodo>
                <url>/personagens/1000/ganhar-xp?xp=50000</url>
                <obs>XP via Query Parameter.</obs>
            </endpoint>
            <endpoint>
                <nome>9. Adicionar Item ao Inventário</nome>
                <metodo>POST</metodo>
                <url>/personagens/1000/inventario/adicionar</url>
                <body-json>
                    <![CDATA[{"itemId": 1009, "quantidade": 10}]]>
                </body-json>
            </endpoint>
            <endpoint>
                <nome>11. Equipar Item</nome>
                <metodo>POST</metodo>
                <url>/personagens/1007/equipar/1002</url>
                <obs>Equipa o Item 1002 no Personagem 1007.</obs>
            </endpoint>
            <endpoint>
                <nome>12. Desequipar Item</nome>
                <metodo>POST</metodo>
                <url>/personagens/1000/desequipar/MAO_PRINCIPAL</url>
                <obs>O slot é passado como path variable.</obs>
            </endpoint>
            </controller>

        <controller nome="Item Controller">
            <endpoint>
                <nome>1. Criar Item</nome>
                <metodo>POST</metodo>
                <url>/itens?nome=Adaga das Sombras&tipo=ARMA&slotEquipamento=MAO_PRINCIPAL</url>
                <body-json>
                    <![CDATA[{"ATAQUE_FISICO": 45, "PRECISAO": 100}]]>
                </body-json>
                <obs>Dados principais via Query Parameters. Atributos via Body.</obs>
            </endpoint>
            <endpoint>
                <nome>4. Atualizar Item</nome>
                <metodo>PUT</metodo>
                <url>/itens/1001</url>
                <body-json>
                    <![CDATA[{"nome": "Blade do Mithril +20 (Buffada)", "atributos": {"ATAQUE_FISICO": 300}}]]>
                </body-json>
            </endpoint>
            <endpoint>
                <nome>5. Deletar Item</nome>
                <metodo>DELETE</metodo>
                <url>/itens/1012</url>
            </endpoint>
            </controller>

        <controller nome="Guilda Controller">
            <endpoint>
                <nome>1. Criar Guilda</nome>
                <metodo>POST</metodo>
                <url>/guildas</url>
                <body-json>
                    <![CDATA[{"nome": "Aliança Arcana", "descricao": "Apenas para magos de elite", "liderId": 1006}]]>
                </body-json>
            </endpoint>
            <endpoint>
                <nome>3. Convidar Personagem</nome>
                <metodo>POST</metodo>
                <url>/guildas/convidar</url>
                <body-json>
                    <![CDATA[{"convidanteId": 1003, "convidadoId": 1009}]]>
                </body-json>
            </endpoint>
            <endpoint>
                <nome>6. Expulsar Membro</nome>
                <metodo>POST</metodo>
                <url>/guildas/expulsar?guildaId=1000&expulsadorId=1000&expulsoId=1002</url>
                <obs>Todos os IDs via Query Parameters.</obs>
            </endpoint>
            <endpoint>
                <nome>7. Transferir Liderança</nome>
                <metodo>POST</metodo>
                <url>/guildas/transferir-lideranca</url>
                <body-json>
                    <![CDATA[{"guildaId": 1001, "antigoLiderId": 1003, "novoLiderId": 1004}]]>
                </body-json>
            </endpoint>
            <endpoint>
                <nome>9. Promover Membro</nome>
                <metodo>POST</metodo>
                <url>/guildas/1000/promover?promotorId=1000&promovidoId=1005&novoCargo=OFICIAL</url>
                <obs>GuildaId como Path Variable. IDs e Cargo via Query Parameters.</obs>
            </endpoint>
            </controller>
    </seção>

</documentação-api>
