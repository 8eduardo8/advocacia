package br.com.abce.advocacia;

public enum SituacaoProcesso {
    EM_ANDAMENTO("Em andamento", 0),
    ARQUIVADO("Arquivado", 1),
    SUSPENSO("Suspenso", 2),
    CONCLUIDO("Concluído", 3);

    private String descricao;
    private int codigo;

    SituacaoProcesso(String descricao, int codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public static SituacaoProcesso getSituacaoProcesso(final int codigoSituacao) {

        SituacaoProcesso situacaoProcesso = null;

        for (SituacaoProcesso situacao : values()) {
            if (situacao.getCodigo() == codigoSituacao){
                situacaoProcesso = situacao;
                break;
            }
        }

        return situacaoProcesso;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
