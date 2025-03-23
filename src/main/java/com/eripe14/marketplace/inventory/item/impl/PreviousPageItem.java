package com.eripe14.marketplace.inventory.item.impl;

import com.eripe14.marketplace.inventory.item.InventoryItem;
import com.eripe14.marketplace.inventory.item.ItemTransformer;
import com.eternalcode.multification.shared.Formatter;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class PreviousPageItem extends PageItem {

    private final Formatter formatter;
    private final InventoryItem item;

    public PreviousPageItem(InventoryItem item) {
        super(false);
        this.item = item;
        this.formatter = new Formatter();
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> pagedGui) {
        this.formatter.register("{page}", pagedGui.getCurrentPage());
        this.formatter.register("{pages}", pagedGui.getPageAmount());

        return ItemTransformer.transformToItemBuilder(this.item, this.formatter);
    }
}