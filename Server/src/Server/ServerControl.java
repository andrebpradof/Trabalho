package Server;

import java.io.*;
import java.util.ArrayList;

public class ServerControl {
    private static ArrayList<Client> clientArrayList = new ArrayList<Client>();
    private static ArrayList<FileServer> fileServerArrayList = new ArrayList<FileServer>();
    private static File[] files;

    public static ArrayList<Client> getClientArrayList() {
        return clientArrayList;
    }

    public static ArrayList<FileServer> getFileServerArrayList() {
        return fileServerArrayList;
    }

    public static void addClient(Client client){
        clientArrayList.add(client);
    }

    public static void addFile(FileServer fileServer){
        fileServerArrayList.add(fileServer);
    }

    public static int newFile(String name, Client client) throws IOException {
        File file = new File(name+".txt");
        if(!file.exists()){
            try {
                file.createNewFile();
                FileServer fileServer = new FileServer();
                fileServer.addClient(client);
                fileServer.setName(name);
                fileServerArrayList.add(fileServer);
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

    public static int verificaArquivo(String name){
        File file = new File(name);
        if(file.exists())
            return -1;
        return 1;
    }

    private static String leArquivo(File file) throws IOException {
        try {
            String aux = "", texto = ""; //strings para auxiliar a passagem do texto do arquivo para a area de texto da interface
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            texto = bufferedReader.readLine(); //a string sl recebe o conteudo da primeira linha do buffer

            while ((aux = bufferedReader.readLine()) != null) { //s1 le uma linha do buffer por vez
                texto = texto + "\n" + aux; //a linha lida por s1 é acrescentada à string sl com uma quebra de linha
            }

            return texto;
        } catch (IOException e) {
            return "-1";
        }
    }

    public static String listaArquivos(){
        File f = new File(".//");

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".txt");
            }
        };
        files = f.listFiles(filter);

        if(files.length == 0)
            return "-1";

        String lista = "0 - " + files[0].getName();
        for(int i=1; i<files.length; i++){
            lista = lista + "\n" + i + " - " + files[i].getName();
        }
        return lista;
    }

    public static String abrir(int indice,Client client) throws IOException {
        File file = files[indice];
        String texto = leArquivo(file);
        if(texto.equals("-1"))
            return "-1";

        FileServer fileServer = new FileServer();
        fileServer.setName(file.getName());
        fileServer.addClient(client);

        return texto;
    }

    public static int upload(String name, String texto, Client client) throws IOException {

        try{
            File file = new File(name);

            FileWriter fileWriter = new FileWriter(file, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(texto);

            bufferedWriter.flush();
            bufferedWriter.close();

            FileServer fileServer = new FileServer();
            fileServer.addClient(client);
            fileServer.setName(file.getName());
            fileServerArrayList.add(fileServer);
            return 1;
        }
        catch(IOException e){
            return -1;
        }
    }

}
