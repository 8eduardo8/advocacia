package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "nota", schema = "advocacia", catalog = "")
public class NotaEntity {
    private Long id;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private Date dataExclusao;
    private ProcessoUsuarioEntity processoUsuarioByProcessoUsuarioId;
    private NotaTextoEntity notaTextoByNotaTextoId;
    private NotaDocumentoEntity notaDocumentoByNotaDocumentoId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "data_cadastro", nullable = true)
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Basic
    @Column(name = "data_atualizacao", nullable = true)
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Basic
    @Column(name = "data_exclusao", nullable = true)
    public Date getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaEntity that = (NotaEntity) o;
        return id == that.id &&
                Objects.equals(dataCadastro, that.dataCadastro) &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
                Objects.equals(dataExclusao, that.dataExclusao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCadastro, dataAtualizacao, dataExclusao);
    }

    @ManyToOne
    @JoinColumn(name = "processo_usuario_id", referencedColumnName = "id", nullable = false)
    public ProcessoUsuarioEntity getProcessoUsuarioByProcessoUsuarioId() {
        return processoUsuarioByProcessoUsuarioId;
    }

    public void setProcessoUsuarioByProcessoUsuarioId(ProcessoUsuarioEntity processoUsuarioByProcessoUsuarioId) {
        this.processoUsuarioByProcessoUsuarioId = processoUsuarioByProcessoUsuarioId;
    }

    @ManyToOne
    @JoinColumn(name = "nota_texto_id", referencedColumnName = "id", nullable = false)
    public NotaTextoEntity getNotaTextoByNotaTextoId() {
        return notaTextoByNotaTextoId;
    }

    public void setNotaTextoByNotaTextoId(NotaTextoEntity notaTextoByNotaTextoId) {
        this.notaTextoByNotaTextoId = notaTextoByNotaTextoId;
    }

    @ManyToOne
    @JoinColumn(name = "nota_documento_id", referencedColumnName = "id", nullable = false)
    public NotaDocumentoEntity getNotaDocumentoByNotaDocumentoId() {
        return notaDocumentoByNotaDocumentoId;
    }

    public void setNotaDocumentoByNotaDocumentoId(NotaDocumentoEntity notaDocumentoByNotaDocumentoId) {
        this.notaDocumentoByNotaDocumentoId = notaDocumentoByNotaDocumentoId;
    }
}
