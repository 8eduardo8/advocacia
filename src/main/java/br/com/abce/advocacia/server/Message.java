package br.com.abce.advocacia.server;

public class Message {

    private Long idNota;
    private String idProcesso;
    private String idUsuario;
    private String nomeUsuario;
    private String content;
    private String file;
    private String data;

    private String nomeFile;
    private String formatoFile;

    private int tipoNota; // 1 - Mensagem; 2 - Andamento Processo; 3 - Documento do Processo; 4 - Servidor;

    public Message() {
        super();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(String idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getFormatoFile() {
        return formatoFile;
    }

    public void setFormatoFile(String formatoFile) {
        this.formatoFile = formatoFile;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
        this.idNota = idNota;
    }

    public int getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(int tipoNota) {
        this.tipoNota = tipoNota;
    }
}
