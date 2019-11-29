package Server;

import java.io.*;
import java.util.ArrayList;

/**
 * classe responsavel pelas operacoes do servidor, tendo seus metodos chamados na classe connect
 */
public class ServerControl {
    private static ArrayList<Client> clientArrayList = new ArrayList<Client>(); //lista de clientes conectados no servidor
    private static File[] files; //lista dos arquivos disponiveis no servidor

    /**
     * pega a lista de clientes conectados ao servidor
     * @return retorna a array list
     */
    public static ArrayList<Client> getClientArrayList() {
        return clientArrayList;
    }

    /**
     * adiciona um cliente a lista de clientes conectados ao servidor
     * @param client cliente a ser adicionado a lista
     */
    public static void addClient(Client client){
        clientArrayList.add(client);
    }

    /**
     * metodo responsavel pela abertura de um novo arquivo para edicao no servidor
     * @param name nome que sera dado ao novo arquivo
     * @param client cliente que esta solicitando a abertura do novo arquivo
     * @return retorna 1 se a operacao obter sucesso, -1 se houver um erro durante a operacao
     * e -2 se ja houver um arquivo no servidor com o nome passado por parametro
     * @throws IOException
     */
    public static int newFile(String name, Client client) throws IOException {
        File file = new File(name+".txt");
        if(!file.exists()){
            try {
                file.createNewFile(); //cria um novo arquivo
                FileServer fileServer = new FileServer(); //cria um novo FileServer
                fileServer.addClient(client); //adiciona o cliente a lista de clientes conectados a esse arquivo
                fileServer.setName(name+".txt"); //adiciona ao nome do arquivo o sufixo ".txt"
                client.setFileServer(fileServer);
                return 1;
            }
            catch (IOException e){
                return -1;
            }
        }
        else{
            return -2;
        }
    }

    /**
     * verifica se o ja ha no servidor um arquivo com o nome que o metodo recebe por parametro
     * @param name nome do arquivo que deseja-se verificar
     * @return retorna 1 se o arquivo nao existe no servidor e -1 se ja existe
     */
    public static int verificaArquivo(String name){
        File file = new File(name); //abre um arquivo com o nome passado por parametro
        if(file.exists()) //verifica se o arquivo ja existia
            return -1;
        return 1;
    }


    /**
     * le um arquivo de texto do servidor e retorna o seu conteudo
     * @param file arquvo que deseja-se ler
     * @return retorna uma string contendo o texto do arquivo lido ou "-1" em caso de erro
     * @throws IOException
     */
    private static String leArquivo(File file) throws IOException {
        try {
            String aux = "", texto = ""; //strings para auxiliar a passagem do texto do arquivo para a area de texto da interface
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            texto = bufferedReader.readLine(); //a string sl recebe o conteudo da primeira linha do buffer

            while ((aux = bufferedReader.readLine()) != null) { //s1 le uma linha do buffer por vez
                texto = texto + "\n" + aux; //a linha lida por s1 é acrescentada à string sl com uma quebra de linha
            }

            if(texto == null) //se nao houver nada no arquivo, o metodo retorna a string vazia ""
                texto = "";

            return texto;
        } catch (IOException e) {
            return "-1";
        }
    }

    /**
     * faz uma lista dos arquivos no servidor para que o cliente possa
     * indicar pelo indice qual arquivo deseja abrir.
     * @return retorna uma string com a lista dos nomes dos arquivos no servidor ou "-1" em caso de erro
     */
    public static String listaArquivos(){
        File f = new File(".//"); //abre um arquivo

        //garante que apenas os arquivos .txt serao abertos
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".txt");
            }
        };
        files = f.listFiles(filter); //pega a lista dos arquivos que estao no diretorio

        if(files.length == 0) //se nao houverem arquivos na lista, retorna -1
            return "-1";

        //associa os nomes de arquivos da lista a indices, a partir do 0
        String lista = "0 - " + files[0].getName();
        for(int i=1; i<files.length; i++){
            lista = lista + "\n" + i + " - " + files[i].getName();
        }
        return lista;
    }

    /**
     * atualiza o numero de pessoas editando um arquivo
     * @param fileServer //arquivo cuja lista de editores deseja-se atualizar
     * @return retorna 1 se o processo ocorreu corretamente ou -1 se houve algum erro
     * @throws IOException
     */
    public static int atualizaNumEditores(FileServer fileServer) throws IOException {
        int numEdit = fileServer.getClients().size(); //pega o numero de editores do arquivo

        //atribui os clientes da list a objetos Client
        for(Client client : fileServer.getClients()){
            try {
                client.getOutputForward().writeUTF("numEdit");
                client.getOutputForward().flush();
                client.getOutputForward().writeInt(numEdit);
                client.getOutputForward().flush();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 1;
    }

    /**
     * abre e le um arquivo correspondente ao indice indicado pelo cliente
     * @param indice indice do arquivo que deseja-se abrir, indicado pelo cliente
     * @param client cliente que deseja abrir o arquivo
     * @return retorna uma string com o texto do arquivo ou "-1" caso haja um erro no processo
     * @throws IOException
     */
    public static String abrir(int indice,Client client) throws IOException {
        File file = files[indice]; //associa o arquivo de indice recebido por parametro a um file
        String texto = leArquivo(file); //le o conteudo do arquivo em file

        if(texto != null && texto.equals("-1")) //verifica se houve algum erro
            return "-1";

        //relaciona o cliente ao fileserver
        FileServer fileServer = new FileServer();
        fileServer.setName(file.getName());
        fileServer.addClient(client);
        client.setFileServer(fileServer);

        //atualizaNumEditores(fileServer);

        return texto;
    }

    /**
     * recebe o texto de um arquivo da maquina do cliente e salva um arquivo no servidor com mesmo conteudo e nome
     * @param name nome do arquivo do upload
     * @param texto conteudo do arquivo
     * @param client cliente que esta realizando o upload
     * @return retorna 1 se o processo ocorrer normalmente ou -1 caso haja algum erro
     * @throws IOException
     */
    public static int upload(String name, String texto, Client client) throws IOException {

        try{
            File file = new File(name); //abre um arquivo com o nome passado por parametro

            FileWriter fileWriter = new FileWriter(file, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(texto); //escreve o texto no arquivo aberto

            bufferedWriter.flush();
            bufferedWriter.close();

            FileServer fileServer = new FileServer();
            fileServer.addClient(client);
            fileServer.setName(file.getName());
            client.setFileServer(fileServer);
            //associa o arquivo e o cliente a um fileserver
            return 1;
        }
        catch(IOException e){
            return -1;
        }
    }

    /**
     * atualiza o arquivo para todos os editores de um arquivo quando um editor altera algo no texto
     * @param fileServer arquivo editado a ser atualizado
     * @param clientSend cliente que fez a alteracao no arquivo
     * @param texto conteudo do arquivo apos a alteracao que deve ser atualizado para todos os editores
     * @return
     * @throws IOException
     */
    public static int atualiza(FileServer fileServer, Client clientSend, String texto) throws IOException {
        for(Client client : fileServer.getClients()){ //pega a lista de clientes editando o arquivo em questao
            if(client != clientSend){
                try {
                    client.getOutputForward().writeUTF("setText");
                    client.getOutputForward().flush();
                    client.getOutputForward().writeUTF(texto);
                    //atualiza o texto para os demais clientes
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }
        return 1;
    }

    /**
     * pega o texto que o usuario digitou no text area e escreve no arquivo
     * @param texto conteudo da textArea
     * @param client cliente editando o texto
     * @return retorna 1 se o processo ocorreu normalmente e -1 se houve algum erro
     * @throws IOException
     */
    public static int recebeTexto(String texto,Client client) throws IOException {
        try {
            File file = new File(client.getFileServer().getName()); //abre o arquivo no qual sera salvo o texto
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(texto); //salva o texto da textArea no arquivo

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        //return atualiza(client.getFileServer(),client,texto);
        return 1;
    }

}
