package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "nota_documento", schema = "advocacia", catalog = "")
public class NotaDocumentoEntity {
    private Long id;
    private String nome;
    private String descricao;
    private byte[] arquivo;
    private String formarto;
    private NotaEntity notaEntity;

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
    @Column(name = "nome", nullable = true, length = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "descricao", nullable = true, length = 100)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Basic
    @Column(name = "arquivo", nullable = true)
    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    @Basic
    @Column(name = "formarto", nullable = true, length = 3)
    public String getFormarto() {
        return formarto;
    }

    public void setFormarto(String formarto) {
        this.formarto = formarto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaDocumentoEntity that = (NotaDocumentoEntity) o;
        return id == that.id &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(descricao, that.descricao) &&
                Arrays.equals(arquivo, that.arquivo) &&
                Objects.equals(formarto, that.formarto);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nome, descricao, formarto);
        result = 31 * result + Arrays.hashCode(arquivo);
        return result;
    }

    @OneToOne(mappedBy = "notaDocumentoByNotaDocumentoId", cascade = CascadeType.PERSIST)
    public NotaEntity getNotaEntity() {
        return notaEntity;
    }

    public void setNotaEntity(NotaEntity notaEntity) {
        this.notaEntity = notaEntity;
    }
}
