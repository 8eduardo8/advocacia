package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "processo", schema = "advocacia", catalog = "")
public class ProcessoEntity {
    private Long id;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private Date dataExclusao;
    private String numero;
    private String area;
    private String comarca;
    private int situacao;
    private Date dataInicio;
    private Collection<ProcessoUsuarioEntity> processoUsuariosById = new ArrayList<>();

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
    @Column(name = "data_cadastro", nullable = false)
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

    @Basic
    @Column(name = "numero", nullable = false, length = 25)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "area", nullable = true, length = 20)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "situacao", nullable = false)
    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    @Basic
    @Column(name = "data_inicio", nullable = false)
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessoEntity that = (ProcessoEntity) o;
        return id == that.id &&
                situacao == that.situacao &&
                Objects.equals(dataCadastro, that.dataCadastro) &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
                Objects.equals(dataExclusao, that.dataExclusao) &&
                Objects.equals(numero, that.numero) &&
                Objects.equals(area, that.area) &&
                Objects.equals(dataInicio, that.dataInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCadastro, dataAtualizacao, dataExclusao, numero, area, situacao, dataInicio);
    }

    @OneToMany(mappedBy = "processoByProcessoId", cascade = CascadeType.ALL)
    public Collection<ProcessoUsuarioEntity> getProcessoUsuariosById() {
        return processoUsuariosById;
    }

    public void setProcessoUsuariosById(Collection<ProcessoUsuarioEntity> processoUsuariosById) {
        this.processoUsuariosById = processoUsuariosById;
    }

    @Basic
    @Column(name = "comarca", nullable = true, length = 45)
    public String getComarca() {
        return comarca;
    }

    public void setComarca(String comarca) {
        this.comarca = comarca;
    }
}
