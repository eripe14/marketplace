package com.eripe14.marketplace.transaction;

import com.eripe14.marketplace.config.implementation.PluginConfig;
import com.eripe14.marketplace.notice.NoticeService;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Command(name = "transactions")
public class TransactionCommand {

    private final TransactionService transactionService;
    private final NoticeService noticeService;
    private final PluginConfig pluginConfig;

    public TransactionCommand(
            TransactionService transactionService,
            NoticeService noticeService,
            PluginConfig pluginConfig
    ) {
        this.transactionService = transactionService;
        this.noticeService = noticeService;
        this.pluginConfig = pluginConfig;
    }

    @Execute
    @Permission("marketplace.transactions")
    void execute(@Context Player player) {
        UUID uniqueId = player.getUniqueId();
        Formatter formatter = new Formatter();


        List<Transaction> transactions = this.transactionService.getTransactionsByBuyer(player);
        if (transactions.isEmpty()) {
            this.noticeService.create()
                    .notice(messages -> messages.noTransactions)
                    .player(uniqueId)
                    .send();
            return;
        }

        this.noticeService.create()
                .notice(messages -> messages.transactionsHeader)
                .player(uniqueId)
                .send();

        for (Transaction transaction : transactions) {
            formatter.register(
                    "{item}",
                    transaction.getItem().getType().name().toLowerCase().replace("_", " ")
            );
            formatter.register("{price}", transaction.getPrice());
            formatter.register("{seller}", Bukkit.getOfflinePlayer(transaction.getSellerUuid()).getName());
            formatter.register("{date}", this.formatInstant(transaction.getTransactionDate()));
            formatter.register("{source}", transaction.getTransactionSource().getName());

            this.noticeService.create()
                    .notice(messages -> messages.transactionEntry)
                    .player(uniqueId)
                    .formatter(formatter)
                    .send();
        }
    }

    private String formatInstant(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return DateTimeFormatter.ofPattern(this.pluginConfig.marketPlaceConfig.timeFormat).format(zonedDateTime);
    }

}