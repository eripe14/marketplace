package com.eripe14.marketplace.marketplace.offer;

import com.eripe14.marketplace.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Command(name = "offer")
public class OfferCommand {

    private final OfferService offerService;
    private final NoticeService noticeService;

    public OfferCommand(OfferService offerService, NoticeService noticeService) {
        this.offerService = offerService;
        this.noticeService = noticeService;
    }

    @Execute
    void offer(@Context Player player, @Arg("price") double price) {
        UUID uniqueId = player.getUniqueId();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR) {
            this.noticeService.create()
                    .notice(messages -> messages.offerItemCannotBeAir)
                    .player(uniqueId)
                    .send();
            return;
        }

        this.offerService.create(uniqueId, itemStack, price);
        this.noticeService.create()
                .notice(messages -> messages.offerCreated)
                .player(uniqueId)
                .placeholder("{price}", String.valueOf(price))
                .send();
    }
}