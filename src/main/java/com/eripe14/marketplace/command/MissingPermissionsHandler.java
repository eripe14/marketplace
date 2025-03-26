package com.eripe14.marketplace.command;

import com.eripe14.marketplace.notice.NoticeService;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MissingPermissionsHandler implements dev.rollczi.litecommands.permission.MissingPermissionsHandler<CommandSender> {

    private final NoticeService noticeService;

    public MissingPermissionsHandler(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        CommandSender commandSender = invocation.sender();

        if (!(commandSender instanceof Player player)) {
            return;
        }

        UUID uniqueId = player.getUniqueId();
        this.noticeService.create()
                .notice(messages -> messages.noPermission)
                .player(uniqueId)
                .send();
    }

}