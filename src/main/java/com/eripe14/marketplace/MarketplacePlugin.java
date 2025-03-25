package com.eripe14.marketplace;

import com.eripe14.marketplace.config.ConfigManager;
import com.eripe14.marketplace.config.implementation.MessageConfig;
import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.discord.DiscordWebhookConfig;
import com.eripe14.marketplace.discord.DiscordWebhookService;
import com.eripe14.marketplace.discord.DiscordWebhookSettings;
import com.eripe14.marketplace.inventory.ConfirmInventory;
import com.eripe14.marketplace.inventory.queue.InventoryQueueController;
import com.eripe14.marketplace.inventory.queue.InventoryQueueService;
import com.eripe14.marketplace.marketplace.MarketplaceCommand;
import com.eripe14.marketplace.marketplace.MarketplaceInventory;
import com.eripe14.marketplace.marketplace.MarketplaceViewFactory;
import com.eripe14.marketplace.marketplace.offer.OfferRepository;
import com.eripe14.marketplace.marketplace.offer.OfferService;
import com.eripe14.marketplace.marketplace.offer.OfferCommand;
import com.eripe14.marketplace.notice.NoticeService;
import com.eripe14.marketplace.notice.adventure.LegacyColorProcessor;
import com.eripe14.marketplace.persistance.DatabaseManager;
import com.eripe14.marketplace.scheduler.BukkitSchedulerImpl;
import com.eripe14.marketplace.scheduler.Scheduler;
import com.eripe14.marketplace.transaction.TransactionCommand;
import com.eripe14.marketplace.transaction.TransactionRepository;
import com.eripe14.marketplace.transaction.TransactionService;
import com.eripe14.marketplace.user.UserController;
import com.eripe14.marketplace.user.UserRepository;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import eu.okaeri.tasker.core.Tasker;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import xyz.xenondevs.invui.InvUI;

import java.io.IOException;
import java.util.stream.Stream;

public class MarketplacePlugin extends JavaPlugin {

    private Scheduler scheduler;
    private Tasker tasker;

    private AudienceProvider audienceProvider;
    private MiniMessage miniMessage;
    private NoticeService noticeService;

    private ConfigManager configManager;
    private PluginConfig pluginConfig;
    private MessageConfig messageConfig;

    private DatabaseManager databaseManager;
    private DocumentPersistence documentPersistence;

    private UserRepository userRepository;

    private OfferRepository offerRepository;
    private OfferService offerService;

    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    private InventoryQueueService inventoryQueueService;

    private DiscordWebhookConfig discordWebhookConfig;
    private DiscordWebhookSettings discordWebhookSettings;
    private DiscordWebhookService discordWebhookService;

    private ConfirmInventory confirmInventory;

    private MarketplaceViewFactory marketplaceViewFactory;
    private MarketplaceInventory marketplaceInventory;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        Server server = this.getServer();

        this.scheduler = new BukkitSchedulerImpl(this);
        this.tasker = BukkitTasker.newPool(this);

        InvUI.getInstance().setPlugin(this);

        this.messageConfig = new MessageConfig();

        this.audienceProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();
        this.noticeService = new NoticeService(this.messageConfig, this.audienceProvider, this.miniMessage);

        this.configManager = new ConfigManager(this.noticeService.getNoticeRegistry());
        this.pluginConfig = this.configManager.load(PluginConfig.class, this.getDataFolder(), "config.yml");
        this.messageConfig = this.configManager.load(MessageConfig.class, this.getDataFolder(), "messages.yml");

        this.databaseManager = new DatabaseManager(this, this.pluginConfig);
        this.documentPersistence = this.databaseManager.connect();

        PersistenceCollection userCollection = PersistenceCollection.of(UserRepository.class);
        this.documentPersistence.registerCollection(userCollection);
        this.userRepository = RepositoryDeclaration.of(UserRepository.class)
                .newProxy(this.documentPersistence, userCollection, this.getClass().getClassLoader());

        PersistenceCollection offerCollection = PersistenceCollection.of(OfferRepository.class);
        this.documentPersistence.registerCollection(offerCollection);
        this.offerRepository = RepositoryDeclaration.of(OfferRepository.class)
                .newProxy(this.documentPersistence, offerCollection, this.getClass().getClassLoader());
        this.offerService = new OfferService(this.offerRepository, this.tasker);

        PersistenceCollection transactionCollection = PersistenceCollection.of(TransactionRepository.class);
        this.documentPersistence.registerCollection(transactionCollection);
        this.transactionRepository = RepositoryDeclaration.of(TransactionRepository.class)
                .newProxy(this.documentPersistence, transactionCollection, this.getClass().getClassLoader());
        this.transactionService = new TransactionService(this.transactionRepository);

        this.inventoryQueueService = new InventoryQueueService();

        this.discordWebhookConfig = this.configManager.load(DiscordWebhookConfig.class, this.getDataFolder(), "discord.yml");
        this.discordWebhookSettings = this.discordWebhookConfig;
        this.discordWebhookService = new DiscordWebhookService(this.discordWebhookSettings);

        this.confirmInventory = new ConfirmInventory(this.scheduler, this.pluginConfig.confirmInventoryConfig);

        this.marketplaceViewFactory = new MarketplaceViewFactory(
                this.confirmInventory,
                this.inventoryQueueService,
                this.offerService,
                this.transactionService,
                this.discordWebhookService,
                this.pluginConfig,
                this.noticeService
        );
        this.marketplaceInventory = new MarketplaceInventory(
                this.inventoryQueueService,
                this.marketplaceViewFactory,
                this.pluginConfig
        );

        this.registerCommands();
        this.registerListeners();
    }

    private void registerCommands() {
        this.liteCommands = LiteBukkitFactory.builder("marketplace", this)
                .commands(
                        new OfferCommand(this.offerService, this.noticeService),
                        new MarketplaceCommand(this.marketplaceInventory),
                        new TransactionCommand(this.transactionService, this.noticeService, this.pluginConfig)
                )
                .build();
    }

    private void registerListeners() {
        Stream.of(
                new UserController(this.userRepository),
                new InventoryQueueController(this.inventoryQueueService)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        try {
            this.liteCommands.unregister();
            this.documentPersistence.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}