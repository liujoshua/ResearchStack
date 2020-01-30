package com.medaptivehealth.researchstack.backbone.sample_app;

import android.app.Application;

import com.medaptivehealth.researchstack.backbone.StorageAccess;
import com.medaptivehealth.researchstack.backbone.storage.database.AppDatabase;
import com.medaptivehealth.researchstack.backbone.storage.database.sqlite.DatabaseHelper;
import com.medaptivehealth.researchstack.backbone.storage.file.EncryptionProvider;
import com.medaptivehealth.researchstack.backbone.storage.file.FileAccess;
import com.medaptivehealth.researchstack.backbone.storage.file.PinCodeConfig;
import com.medaptivehealth.researchstack.backbone.storage.file.SimpleFileAccess;
import com.medaptivehealth.researchstack.backbone.storage.file.UnencryptedProvider;


public class MedaptiveBackboneApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Customize your pin code preferences
        PinCodeConfig pinCodeConfig = new PinCodeConfig(); // default pin config (4-digit, 1 min lockout)

        // Customize encryption preferences
        EncryptionProvider encryptionProvider = new UnencryptedProvider(); // No pin, no encryption

        // If you have special file handling needs, implement FileAccess
        FileAccess fileAccess = new SimpleFileAccess();

        // If you have your own custom database, implement AppDatabase
        AppDatabase database = new DatabaseHelper(this,
                DatabaseHelper.DEFAULT_NAME,
                null,
                DatabaseHelper.DEFAULT_VERSION);

//        AndroidThreeTen.init(this);
        StorageAccess.getInstance().init(pinCodeConfig, encryptionProvider, fileAccess, database);
    }
}
