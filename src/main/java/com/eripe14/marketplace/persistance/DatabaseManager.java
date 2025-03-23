package com.eripe14.marketplace.persistance;

import com.eripe14.marketplace.MarketplacePlugin;
import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.RepositoryDeclaration;

public class DatabaseManager {

    private final MarketplacePlugin plugin;
    private final PluginConfig pluginConfig;

    public DatabaseManager(MarketplacePlugin plugin, PluginConfig pluginConfig) {
        this.plugin = plugin;
        this.pluginConfig = pluginConfig;
    }

    public DocumentPersistence connect() {
        ConnectionString mongoUri = new ConnectionString(this.pluginConfig.storage.mongoUri);
        MongoClient mongoClient = MongoClients.create(mongoUri);

        if (mongoUri.getDatabase() == null) {
            throw new IllegalArgumentException("Database name is not provided in the connection string");
        }

        PersistencePath basePath = PersistencePath.of(this.pluginConfig.storage.prefix);

        return new DocumentPersistence(
                new MongoPersistence(basePath, mongoClient, mongoUri.getDatabase()),
                JsonSimpleConfigurer::new,
                new SerdesBukkit()
        );
    }

    public <T extends DocumentRepository> T registerRepository(
            Class<T> clazz,
            DocumentPersistence persistence
    ) {
        PersistenceCollection collection = PersistenceCollection.of(clazz);
        persistence.registerCollection(collection);

        return RepositoryDeclaration.of(clazz).newProxy(persistence, collection, this.plugin.getClass().getClassLoader());
    }

}