package dev.feldmann.redstonegang.common.api.maps;

import dev.feldmann.redstonegang.common.api.RedstoneGangWebAPI;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.common.api.maps.responses.UploadMapResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsAPI {

    private RedstoneGangWebAPI api;

    public MapsAPI(RedstoneGangWebAPI api) {
        this.api = api;
    }

    public Response<MapResponse> find(int id) {
        return api.builder("maps", "id", "" + id).get(MapResponse.class);
    }

    public MapResponse find(String game, String name) {
        Response<MapResponse> response = api.builder("maps", "find")
                .data("game", game)
                .data("name", name)
                .get(MapResponse.class);
        if (response.hasFailed()) {
            return null;
        }
        return response.collect();
    }

    public Response<MapResponse[]> list() {
        return api.builder("maps", "list").get(MapResponse[].class);
    }

    public Response<MapResponse[]> list(String game) {
        return api.builder("maps", "list", game).get(MapResponse[].class);
    }

    public Response<MapResponse> findOrCreate(String name, String game) {
        return api.builder("maps", "create")
                .data("name", name)
                .data("game", game)
                .post(MapResponse.class);
    }

    public List<String> gameList() {
        Response<String[]> games = api.builder("maps", "games")
                .get(String[].class);
        if (games.hasFailed()) {
            return new ArrayList<>();
        }
        return Arrays.asList(games.collect());
    }


    public boolean upload(int mapId, File f, boolean isValid) {
        Response<UploadMapResponse> resp = api.builder("maps", "upload")
                .data("id", "" + mapId)
                .data("valid", isValid + "")
                .file("map", f).post(UploadMapResponse.class);
        return !resp.hasFailed() && resp.collect().success;
    }

    public File download(String pathFile, int mapid) {
        return api.builder("maps", "download", mapid + "").getFile(pathFile);
    }


}
