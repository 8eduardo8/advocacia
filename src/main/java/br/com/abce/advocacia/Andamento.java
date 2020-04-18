package br.com.abce.advocacia;

public enum Andamento {

    CONVOCACAO_DEPOIMENTO("Convocação para depoimento"),
    CONCLUSOS_JULGAMENTO("Conclusos para julgamento"),
    CONCLUSOS_DESPACHO("Conclusos para despacho"),
    RECEBIDOS_AUTOS("Recebidos os Autos"),
    RECEBIDOS_AUTOS_PUBLICACAO("Recebidos os autos expedir publicação"),
    EXPEDICAO_CERTIDAO("Expedição de Certidão");

    private String descricao;

    Andamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
