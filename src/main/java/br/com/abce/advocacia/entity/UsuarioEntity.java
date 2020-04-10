package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "advocacia", catalog = "")
public class UsuarioEntity {
    private int id;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private Date dataExclusao;
    private String login;
    private String cpf;
    private int perfil;
    private String nome;
    private String sobreNome;
    private String sexo;
    private String email;
    private String senha;
    private Integer recuperarSenha;
    private String senhaProvisoria;
    private int situacao;
    private byte[] foto;
    private String telefoneFixo;
    private String telefoneCelular;
    private Collection<ProcessoUsuarioEntity> processoUsuariosById;
    private EnderecoEntity enderecoByEnderecoId;
    private Collection<UsuarioEscritorioEntity> usuarioEscritoriosById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Basic
    @Column(name = "login", nullable = false, length = 20)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "cpf", nullable = false, length = 15)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Basic
    @Column(name = "perfil", nullable = false)
    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    @Basic
    @Column(name = "nome", nullable = false, length = 50)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "sobre_nome", nullable = true, length = 50)
    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    @Basic
    @Column(name = "sexo", nullable = false, length = 1)
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 70)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "senha", nullable = false, length = 200)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Basic
    @Column(name = "recuperar_senha", nullable = true)
    public Integer getRecuperarSenha() {
        return recuperarSenha;
    }

    public void setRecuperarSenha(Integer recuperarSenha) {
        this.recuperarSenha = recuperarSenha;
    }

    @Basic
    @Column(name = "senha_provisoria", nullable = true, length = 200)
    public String getSenhaProvisoria() {
        return senhaProvisoria;
    }

    public void setSenhaProvisoria(String senhaProvisoria) {
        this.senhaProvisoria = senhaProvisoria;
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
    @Column(name = "foto", nullable = true)
    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Basic
    @Column(name = "telefone_fixo", nullable = true, length = 10)
    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    @Basic
    @Column(name = "telefone_celular", nullable = true, length = 11)
    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return id == that.id &&
                perfil == that.perfil &&
                situacao == that.situacao &&
                Objects.equals(dataCadastro, that.dataCadastro) &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
                Objects.equals(dataExclusao, that.dataExclusao) &&
                Objects.equals(login, that.login) &&
                Objects.equals(cpf, that.cpf) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(sobreNome, that.sobreNome) &&
                Objects.equals(sexo, that.sexo) &&
                Objects.equals(email, that.email) &&
                Objects.equals(senha, that.senha) &&
                Objects.equals(recuperarSenha, that.recuperarSenha) &&
                Objects.equals(senhaProvisoria, that.senhaProvisoria) &&
                Arrays.equals(foto, that.foto) &&
                Objects.equals(telefoneFixo, that.telefoneFixo) &&
                Objects.equals(telefoneCelular, that.telefoneCelular);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, dataCadastro, dataAtualizacao, dataExclusao, login, cpf, perfil, nome, sobreNome, sexo, email, senha, recuperarSenha, senhaProvisoria, situacao, telefoneFixo, telefoneCelular);
        result = 31 * result + Arrays.hashCode(foto);
        return result;
    }

    @OneToMany(mappedBy = "usuarioByUsuarioId", cascade = CascadeType.ALL)
    public Collection<ProcessoUsuarioEntity> getProcessoUsuariosById() {
        return processoUsuariosById;
    }

    public void setProcessoUsuariosById(Collection<ProcessoUsuarioEntity> processoUsuariosById) {
        this.processoUsuariosById = processoUsuariosById;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    public EnderecoEntity getEnderecoByEnderecoId() {
        return enderecoByEnderecoId;
    }

    public void setEnderecoByEnderecoId(EnderecoEntity enderecoByEnderecoId) {
        this.enderecoByEnderecoId = enderecoByEnderecoId;
    }

    @OneToMany(mappedBy = "usuarioByUsuarioId")
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    public Collection<UsuarioEscritorioEntity> getUsuarioEscritoriosById() {
        return usuarioEscritoriosById;
    }

    public void setUsuarioEscritoriosById(Collection<UsuarioEscritorioEntity> usuarioEscritoriosById) {
        this.usuarioEscritoriosById = usuarioEscritoriosById;
    }
}
