package com.yavirac.homies.FirebaseStorage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageService {
    private final String bucketName = "homies-2df0a.appspot.com";
    private Storage storage;
    
    // Definimos las carpetas disponibles
    public static final String CARPETA_PROVEEDOR = "proveedor";
    public static final String CARPETA_PRODUCTO = "producto";

    public FirebaseStorageService() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/homies-2df0a-firebase-adminsdk-mwqll-9f12633d25.json");

        storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .getService();
    }

    public String uploadFile(MultipartFile file, String carpeta) throws IOException {
        // Validamos que la carpeta sea válida
        if (!carpeta.equals(CARPETA_PROVEEDOR) && !carpeta.equals(CARPETA_PRODUCTO)) {
            throw new IllegalArgumentException("Carpeta no válida. Use CARPETA_PROVEEDOR o CARPETA_PRODUCTO");
        }

        String fileName = carpeta + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        
        storage.create(blobInfo, file.getBytes());
        
        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", 
            bucketName, 
            URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    // Métodos de conveniencia para cada carpeta
    public String uploadProveedorFile(MultipartFile file) throws IOException {
        return uploadFile(file, CARPETA_PROVEEDOR);
    }

    public String uploadProductoFile(MultipartFile file) throws IOException {
        return uploadFile(file, CARPETA_PRODUCTO);
    }
}