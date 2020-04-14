package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "endereco", schema = "advocacia", catalog = "")
public class EnderecoEntity {
    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String cidade;
    private String bairro;
    private String uf;
    private EscritorioEntity escritoriosById;
    private UsuarioEntity usuariosById;

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
    @Column(name = "cep", nullable = true, length = 8)
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Basic
    @Column(name = "logradouro", nullable = true, length = 50)
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    @Basic
    @Column(name = "complemento", nullable = true, length = 80)
    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Basic
    @Column(name = "numero", nullable = true, length = 5)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "cidade", nullable = true, length = 45)
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Basic
    @Column(name = "uf", nullable = true, length = 2)
    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoEntity that = (EnderecoEntity) o;
        return id == that.id &&
                Objects.equals(cep, that.cep) &&
                Objects.equals(logradouro, that.logradouro) &&
                Objects.equals(complemento, that.complemento) &&
                Objects.equals(numero, that.numero) &&
                Objects.equals(cidade, that.cidade) &&
                Objects.equals(uf, that.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cep, logradouro, complemento, numero, cidade, uf);
    }

    @OneToOne(mappedBy = "enderecoByEnderecoId", cascade = CascadeType.ALL)
    public EscritorioEntity getEscritoriosById() {
        return escritoriosById;
    }

    public void setEscritoriosById(EscritorioEntity escritoriosById) {
        this.escritoriosById = escritoriosById;
    }

    @OneToOne(mappedBy = "enderecoByEnderecoId", cascade = CascadeType.ALL)
    public UsuarioEntity getUsuariosById() {
        return usuariosById;
    }

    public void setUsuariosById(UsuarioEntity usuariosById) {
        this.usuariosById = usuariosById;
    }

    @Basic
    @Column(name = "bairro", nullable = true, length = 45)
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
