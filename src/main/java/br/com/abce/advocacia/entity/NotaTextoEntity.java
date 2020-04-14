package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "nota_texto", schema = "advocacia", catalog = "")
public class NotaTextoEntity {
    private Long id;
    private String descricao;
    private int tipo;

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
    @Column(name = "descricao", nullable = false, length = 100)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Basic
    @Column(name = "tipo", nullable = false)
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaTextoEntity that = (NotaTextoEntity) o;
        return id == that.id &&
                tipo == that.tipo &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, tipo);
    }
}
