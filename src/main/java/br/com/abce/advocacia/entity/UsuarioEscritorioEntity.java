package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "usuario_escritorio", schema = "advocacia", catalog = "")
public class UsuarioEscritorioEntity {
    private int id;
    private Date dataAtualizacao;
    private Date dataCadastrol;
    private Date dataExclusao;
//    private int usuarioId;
//    private int escritorioId;
    private UsuarioEntity usuarioByUsuarioId;
    private EscritorioEntity escritorioByEscritorioId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "data_cadastrol", nullable = true)
    public Date getDataCadastrol() {
        return dataCadastrol;
    }

    public void setDataCadastrol(Date dataCadastrol) {
        this.dataCadastrol = dataCadastrol;
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
//    @Column(name = "usuario_id", nullable = false)
//    public int getUsuarioId() {
//        return usuarioId;
//    }
//
//    public void setUsuarioId(int usuarioId) {
//        this.usuarioId = usuarioId;
//    }
//
//    @Basic
//    @Column(name = "escritorio_id", nullable = false)
//    public int getEscritorioId() {
//        return escritorioId;
//    }
//
//    public void setEscritorioId(int escritorioId) {
//        this.escritorioId = escritorioId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEscritorioEntity that = (UsuarioEscritorioEntity) o;
        return id == that.id &&
//                usuarioId == that.usuarioId &&
//                escritorioId == that.escritorioId &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
                Objects.equals(dataCadastrol, that.dataCadastrol) &&
                Objects.equals(dataExclusao, that.dataExclusao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataAtualizacao, dataCadastrol, dataExclusao);
//        , usuarioId, escritorioId);
    }

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    public UsuarioEntity getUsuarioByUsuarioId() {
        return usuarioByUsuarioId;
    }

    public void setUsuarioByUsuarioId(UsuarioEntity usuarioByUsuarioId) {
        this.usuarioByUsuarioId = usuarioByUsuarioId;
    }

    @ManyToOne
    @JoinColumn(name = "escritorio_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public EscritorioEntity getEscritorioByEscritorioId() {
        return escritorioByEscritorioId;
    }

    public void setEscritorioByEscritorioId(EscritorioEntity escritorioByEscritorioId) {
        this.escritorioByEscritorioId = escritorioByEscritorioId;
    }
}
