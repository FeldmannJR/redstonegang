package dev.feldmann.redstonegang.common.errors;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.Database;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables.EXCEPTIONS;

public class ExceptionsDB extends Database {
    @Override
    public void createTables() {
        dsl().execute("create table if not exists exceptions\n" +
                "(\n" +
                "    id       bigint auto_increment\n" +
                "        primary key,\n" +
                "    occurred timestamp default CURRENT_TIMESTAMP null,\n" +
                "    server   varchar(32)                         not null,\n" +
                "    game     varchar(32)                         null,\n" +
                "    error    longtext                            not null\n" +
                ");\n" +
                "\n");
    }

    public JsonObject convertToJson(Throwable throwable) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", throwable.getLocalizedMessage());
        jsonObject.addProperty("throwable", throwable.getClass().getSimpleName());
        JsonArray stacktrace = new JsonArray();
        for (StackTraceElement stack : throwable.getStackTrace()) {
            JsonObject j = new JsonObject();
            j.addProperty("class", stack.getClassName());
            j.addProperty("method", stack.getMethodName());
            j.addProperty("line", stack.getLineNumber());
            stacktrace.add(j);
        }
        jsonObject.add("stacktrace", stacktrace);
        if (throwable.getCause() != null) {
            jsonObject.add("cause", convertToJson(throwable.getCause()));
        }
        return jsonObject;
    }

    public void addException(String game, Throwable throwlable) {
        try {
            dsl().insertInto(EXCEPTIONS).columns(EXCEPTIONS.SERVER, EXCEPTIONS.GAME, EXCEPTIONS.ERROR)
                    .values(RedstoneGang.instance().getNomeServer(), game, convertToJson(throwlable).toString())
                    .execute();
        } catch (Exception ex) {
            // Faz nada pra n entrar em loop do caralho
        }
    }
}
