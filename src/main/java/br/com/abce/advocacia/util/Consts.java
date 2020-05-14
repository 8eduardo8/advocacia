package br.com.abce.advocacia.util;

public class Consts {

    public static final int REGISTRO_ATIVO = 0;
    public static final int REGISTRO_INATIVO = 1;

    public static final int PERFIL_CLIENTE = 1;
    public static final int PERFIL_ADVOGADO = 2;
    public static final int PERFIL_SECRETARIA = 3;
    public static final int PERFIL_ADMINISTRADOR = 4;

    public static final int TIPO_ANDAMENTO_PROCESSO = 2;
    public static final int TIPO_MENSAGEM = 1;

    public static final Integer NAO_RECUPERACAO_SENHA = 0;
    public static final Integer RECUPERACAO_SENHA = 1;


    public static final String NAO_POSSIVEL_DADOS_USUARIO = "Não foi possível obter o dados do usuário.";
    public static final String NAO_POSSIVEL_DADOS_PROCESSO = "Não foi possível obter o dados do processo.";
    public static final String OPERACO_REALIZADA_SUCESSO = "Operação realizada com sucesso.";

    public static final String ID_PROCESSO_NAO_INFORMADO = "Id do processo não informado.";
    public static final String ID_USUARIO_NAO_INFORMADO = "Id do usuário não informado.";
    public static final String PROCESSO_NAO_ENCONTRADO = "Processo não encontrado.";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado.";


    public static final int PAGE_SIZE_DEFAULT = 6;
    public static final int PAGINA_UM = 1;

    public static final String URL_SISTEMA = "http://localhost:8080/advocacia/login.xhtml";


    public static final String ASSUNTO_SENHA_PROVISORIA = "Senha provisória";
    public static final String CORPO_EMAIL_SENHA_PROVISORIA = "Olá %s,\n" +
            "\n" +
            "Uma senha provisória foi gerada para você acessar o sistema.\n" +
            "\n" +
            "\tsenha: %s\n" +
            "\n" +
            "\n" +
            "Segue o link para acesso ao sistema:\n" +
            "\n" +
            "\t%s" +
            "\n" +
            "\n" +
            "Favor não responder, e-mail automático.";

    public static final String ASSUNTO_EMAIL_NOVO_USUARIO= "Criação de novo usuário";
    public static final String CORPO_EMAIL_NOVO_USUARIO  = "Olá %s,\n" +
            "\n" +
            "O seu usuário foi criado para acesso ao sistema. Segue abaixo a seus dados de acesso:\n" +
            "\n" +
            "\tusuário: %s\n" +
            "\tsenha: %s\n" +
            "\n" +
            "\n" +
            "Segue o link para acesso ao sistema:\n" +
            "\n" +
            "\t%s" +
            "\n" +
            "\n" +
            "Favor não responder, e-mail automático.";

    public static final String ASSUNTO_TROCA_SENHA = "Alteração de senha de usuário";
    public static final String CORPO_EMAIL_TROCA_SENHA = "Olá %s,\n" +
            "\n" +
            "Estamos apenas lhe informando que a sua senha foi alterada recentemente no sistema.\n" +
            "\n" +
            "Favor não responder, e-mail automático.";
}
