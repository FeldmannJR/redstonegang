package dev.feldmann.redstonegang.wire;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.errors.ExceptionsDB;
import dev.feldmann.redstonegang.common.utils.ExceptionUtils;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class WireLogFilter extends AbstractFilter {

    Gson gson;

    public WireLogFilter() {
        this.gson = new Gson();
    }

    public void filterMessage(LogEvent record) {
        if (record.getLevel() == Level.ERROR) {
            Throwable thrown = record.getThrown();
            if (thrown != null) {
                try {
                    if (RedstoneGang.instance() != null) {
                        String game = null;
                        if (Wire.instance != null && Wire.instance.game() != null && Wire.instance.game().getServer() != null) {
                            game = Wire.instance.game().getServer().getGames().name();
                        }
                        RedstoneGang.instance().databases().exceptions().addException(game, thrown);
                    }
                } catch (Exception ex) {

                }
            }
        }

    }

    @Override
    public Result filter(LogEvent logEvent) {
        filterMessage(logEvent);
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String s, Object... objects) {
        return super.filter(logger, level, marker, s, objects);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object o, Throwable throwable) {
        return super.filter(logger, level, marker, o, throwable);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        return super.filter(logger, level, marker, message, throwable);
    }
}
