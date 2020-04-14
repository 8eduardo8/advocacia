package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "processo_usuario", schema = "advocacia", catalog = "")
public class ProcessoUsuarioEntity {
    private Long id;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private Date dataExclusao;
//    private Long processoId;
//    private Long usuarioId;
    private Collection<NotaEntity> notasById;
    private ProcessoEntity processoByProcessoId;
    private UsuarioEntity usuarioByUsuarioId;

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

//    @Basic
//    @Column(name = "processo_id", nullable = false)
//    public Long getProcessoId() {
//        return processoId;
//    }
//
//    public void setProcessoId(Long processoId) {
//        this.processoId = processoId;
//    }
//
//    @Basic
//    @Column(name = "usuario_id", nullable = false)
//    public Long getUsuarioId() {
//        return usuarioId;
//    }
//
//    public void setUsuarioId(Long usuarioId) {
//        this.usuarioId = usuarioId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoUsuarioEntity that = (ProcessoUsuarioEntity) o;
        return id == that.id &&
//                processoId == that.processoId &&
//                usuarioId == that.usuarioId &&
                Objects.equals(dataCadastro, that.dataCadastro) &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
                Objects.equals(dataExclusao, that.dataExclusao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCadastro, dataAtualizacao, dataExclusao);
//        , processoId, usuarioId);
    }

    @OneToMany(mappedBy = "processoUsuarioByProcessoUsuarioId")
    public Collection<NotaEntity> getNotasById() {
        return notasById;
    }

    public void setNotasById(Collection<NotaEntity> notasById) {
        this.notasById = notasById;
    }

    @ManyToOne
    @JoinColumn(name = "processo_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public ProcessoEntity getProcessoByProcessoId() {
        return processoByProcessoId;
    }

    public void setProcessoByProcessoId(ProcessoEntity processoByProcessoId) {
        this.processoByProcessoId = processoByProcessoId;
    }

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public UsuarioEntity getUsuarioByUsuarioId() {
        return usuarioByUsuarioId;
    }

    public void setUsuarioByUsuarioId(UsuarioEntity usuarioByUsuarioId) {
        this.usuarioByUsuarioId = usuarioByUsuarioId;
    }
}
