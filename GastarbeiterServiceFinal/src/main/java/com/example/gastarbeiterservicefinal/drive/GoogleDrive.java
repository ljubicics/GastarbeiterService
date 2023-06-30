package com.example.gastarbeiterservicefinal.drive;

import com.example.gastarbeiterservicefinal.drive.model.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.*;

public class GoogleDrive {
    private Drive service;
    private String posaoID = null;
    private String prihodiID = null;
    private String troskoviID = null;
    private ArrayList<Prihodi> prihodiList = new ArrayList<>();
    private ArrayList<Posao> posloviList = new ArrayList<>();
    private ArrayList<Troskovi> troskoviList = new ArrayList<>();
    private ObservableList<Posao> observablePosloviList;
    private ObservableList<Prihodi> observablePrihodiList;
    private ObservableList<Troskovi> observableTroskoviList;
    Type typeMyTypePosao = new TypeToken<ArrayList<Posao>>(){}.getType();
    Type typeMyTypePrihodi = new TypeToken<ArrayList<Prihodi>>(){}.getType();
    Type typeMyTypeTroskovi = new TypeToken<ArrayList<Troskovi>>(){}.getType();
    Gson gson = new Gson();

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GoogleDrive.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public static Drive getDriveService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }
    public void initService() {
        this.service = null;
        try {
            this.service = getDriveService();
        }catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    // Dodati da prima iz formi u fx-u polja koja ce biti prosledjena ovde
    // Izmeniti da ako nema poslovi fajla, onda kreira novi fajl (prvo pokretanje programa)
    public void addPosao(Posao p) {
        this.loadData();
        if(posaoID != null && prihodiID != null) {
            try {
                // Ovde treba da ide konstruktor koji ce da nastane od stvari povucenih sa fronta
                service.files().delete(posaoID).execute();
                posloviList.add(p);
                updatePrihodiList(p);
                writeToFile("poslovi");
                service.files().delete(prihodiID).execute();
                writeToFile("prihodi");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { // Ako jesu null tj. ne postoje onda se kreiraju kada se stisne dugme
            try {
                // Privremeno inace treba da se uzme iz forme
                //Posao p = new Posao("Kijent",100, LocalDate.now().toString(), "caocao", 117.5, NacinPlacanja.PAYPAL, 60, Valuta.DINAR);
                posloviList.add(p);
                if(prihodiList.size() > 0) {
                    Prihodi prihodi = prihodiList.get(prihodiList.size() - 1);
                    if(prihodi != null) {
                        if(prihodi.getGodina().equals(p.getPrihodi().getGodina()) && prihodi.getMesec().equals(p.getPrihodi().getMesec())) {
                            prihodi.setMihailo(prihodi.getMihailo() + p.getPrihodi().getMihailo());
                            prihodi.setZdravko(prihodi.getZdravko() + p.getPrihodi().getZdravko());
                            prihodi.setKasa(prihodi.getKasa() + p.getPrihodi().getKasa());
                            prihodiList.set(prihodiList.size() - 1, prihodi);
                        }
                    }
                } else {
                    prihodiList.add(p.getPrihodi());
                }
                writeToFile("poslovi");
                writeToFile("prihodi");
                writeToFile("troskovi");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addTrosak(Troskovi t) {
        this.loadData();
        if(troskoviID != null) {
            try {
                for(int i = 0; i < prihodiList.size(); i++) {
                    if(Objects.equals(prihodiList.get(i).getGodina(), t.getGodina()) && Objects.equals(prihodiList.get(i).getMesec(), t.getMesec())) {
                        Prihodi p = prihodiList.get(i);
                        p.setKasa(prihodiList.get(i).getKasa() - t.getSuma());
                        prihodiList.set(i, p);
                        System.out.println(p);
                    }
                }
                service.files().delete(prihodiID).execute();
                service.files().delete(troskoviID).execute();
                troskoviList.add(t);
                System.out.println(troskoviList.size());
                writeToFile("troskovi");
                writeToFile("prihodi");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadData() {
        try {
            FileList fileList = service.files().list().execute();
            List<File> files = fileList.getFiles();
            for(File f : files) {
                System.out.println(f.getName());
                if(f.getName().equals("poslovi")) {
                    posaoID = f.getId();
                } else if (f.getName().equals("prihodi")) {
                    prihodiID = f.getId();
                } else if (f.getName().equals("troskovi")) {
                    troskoviID = f.getId();
                }
            }
            if(prihodiID != null && posaoID != null && troskoviID != null) {
                OutputStream outputStream = new ByteArrayOutputStream();
                OutputStream outputStream2 = new ByteArrayOutputStream();
                OutputStream outputStream3 = new ByteArrayOutputStream();
                service.files().get(posaoID)
                        .executeMediaAndDownloadTo(outputStream);
                String stringFromPoslovi = outputStream.toString();
                System.out.println(stringFromPoslovi);
                posloviList = gson.fromJson(stringFromPoslovi, typeMyTypePosao);
                service.files().get(prihodiID)
                        .executeMediaAndDownloadTo(outputStream2);
                String stringFromPrihodi = outputStream2.toString();
                prihodiList = gson.fromJson(stringFromPrihodi, typeMyTypePrihodi);
                service.files().get(troskoviID)
                        .executeMediaAndDownloadTo(outputStream3);
                String stringFromTroskovi = outputStream3.toString();
                troskoviList = gson.fromJson(stringFromTroskovi, typeMyTypeTroskovi);
                observablePosloviList = FXCollections.observableList(posloviList);
                observablePrihodiList = FXCollections.observableList(prihodiList);
                observableTroskoviList = FXCollections.observableList(troskoviList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            java.io.File file = new java.io.File("tokens");
            file.delete();
        }
    }

    public void writeToFile(String name) throws IOException {
        java.io.File file = new java.io.File(name);
        File fileMetaData = new File();
        fileMetaData.setName(name);
        String json;
        if(name.equals("poslovi")) {
            json = gson.toJson(posloviList);
        } else if(name.equals("prihodi")){
            json = gson.toJson(prihodiList);
        } else {
            json = gson.toJson(troskoviList);
        }
        PrintWriter pw = new PrintWriter(file);
        pw.write(json);
        pw.flush();
        pw.close();
        FileContent fileContent = new FileContent("text/plain", file);
        service.files().create(fileMetaData, fileContent).setFields("id").execute();
    }

    //Vrsi update prihoda, tako sto povecava poslednje prihode za novi posao i dodaje ih u listu
    public void updatePrihodiList(Posao p) {
        // Ne hvata dobro
        Prihodi prihodi = null;
        for(int i = 0; i < prihodiList.size(); i++) {
            if(prihodiList.get(i).getGodina().equals(p.getGodina()) && prihodiList.get(i).getMesec().equals(p.getMesec())) {
                prihodi = prihodiList.get(i);
            }
        }
        if (prihodi != null) {
            prihodi.setMihailo(prihodi.getMihailo() + p.getPrihodi().getMihailo());
            prihodi.setZdravko(prihodi.getZdravko() + p.getPrihodi().getZdravko());
            prihodi.setKasa(prihodi.getKasa() + p.getPrihodi().getKasa());
            prihodiList.set(prihodiList.size() - 1, prihodi);
        } else {
            prihodiList.add(new Prihodi(p.getGodina(), p.getMesec(), p.getPrihodi().getMihailo(), p.getPrihodi().getZdravko(), p.getPrihodi().getKasa()));
        }
    }
    public ArrayList<Prihodi> getPrihodiList() {
        return prihodiList;
    }

    public ArrayList<Posao> getPosloviList() {
        return posloviList;
    }

    public Drive getService() {
        return service;
    }

    public void setService(Drive service) {
        this.service = service;
    }

    public String getPosaoID() {
        return posaoID;
    }

    public void setPosaoID(String posaoID) {
        this.posaoID = posaoID;
    }

    public String getPrihodiID() {
        return prihodiID;
    }

    public void setPrihodiID(String prihodiID) {
        this.prihodiID = prihodiID;
    }

    public void setPrihodiList(ArrayList<Prihodi> prihodiList) {
        this.prihodiList = prihodiList;
    }

    public void setPosloviList(ArrayList<Posao> posloviList) {
        this.posloviList = posloviList;
    }

    public ObservableList<Posao> getObservablePosloviList() {
        return observablePosloviList;
    }

    public void setObservablePosloviList(ObservableList<Posao> observablePosloviList) {
        this.observablePosloviList = observablePosloviList;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public ObservableList<Prihodi> getObservablePrihodiList() {
        return observablePrihodiList;
    }

    public void setObservablePrihodiList(ObservableList<Prihodi> observablePrihodiList) {
        this.observablePrihodiList = observablePrihodiList;
    }

    public Type getTypeMyTypePosao() {
        return typeMyTypePosao;
    }

    public ObservableList<Troskovi> getObservableTroskoviList() {
        return observableTroskoviList;
    }

    public void setObservableTroskoviList(ObservableList<Troskovi> observableTroskoviList) {
        this.observableTroskoviList = observableTroskoviList;
    }

    public void setTypeMyTypePosao(Type typeMyTypePosao) {
        this.typeMyTypePosao = typeMyTypePosao;
    }

    public Type getTypeMyTypePrihodi() {
        return typeMyTypePrihodi;
    }

    public void setTypeMyTypePrihodi(Type typeMyTypePrihodi) {
        this.typeMyTypePrihodi = typeMyTypePrihodi;
    }
}
