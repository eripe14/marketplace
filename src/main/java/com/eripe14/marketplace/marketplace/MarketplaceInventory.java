package com.eripe14.marketplace.marketplace;

import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.inventory.queue.InventoryQueueService;
import com.eripe14.marketplace.notice.adventure.LegacyColorProcessor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.window.Window;

import java.util.List;

public class MarketplaceInventory {

    private final MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    private final InventoryQueueService inventoryQueueService;
    private final MarketplaceViewFactory viewFactory;
    private final PluginConfig pluginConfig;
    private final PagedGui pagedGui;

    public MarketplaceInventory(
            InventoryQueueService inventoryQueueService,
            MarketplaceViewFactory viewFactory,
            PluginConfig pluginConfig
    ) {
        this.inventoryQueueService = inventoryQueueService;
        this.viewFactory = viewFactory;
        this.pluginConfig = pluginConfig;

        this.pagedGui = this.prepareGui();
    }

    public void openInventory(Player player) {
        this.refreshGui();

        Window window = Window.single()
                .setViewer(player)
                .setTitle(new AdventureComponentWrapper(this.miniMessage.deserialize(this.pluginConfig.marketPlaceInventory.title)))
                .setGui(this.pagedGui)
                .build();

        window.open();
        this.inventoryQueueService.addWindow(player, window);
    }

    private void refreshGui() {
        this.pagedGui.setContent(this.getContent());
    }

    private PagedGui prepareGui() {
        System.out.println("size: " + this.getContent().size());

        return this.viewFactory.getOffersView()
                .setContent(this.getContent())
                .build();
    }

    private List<Item> getContent() {
        return this.viewFactory.getGuiContent(event -> this.refreshGui());
    }
}