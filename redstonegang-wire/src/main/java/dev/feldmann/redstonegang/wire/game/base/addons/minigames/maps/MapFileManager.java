package dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.api.Response;
import dev.feldmann.redstonegang.common.api.maps.MapsAPI;
import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.common.utils.FileUtils;
import dev.feldmann.redstonegang.common.utils.ZipUtils;
import dev.feldmann.redstonegang.wire.utils.TarUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapFileManager {


    public String cacheFolder = "/server/maps";
    public String uploadFolder = cacheFolder + "/uploadFromBukkit";


    public File getMap(int id) throws IOException {
        File f = getFolderForMapId(id);
        if (f.exists()) {
            return f;
        }
        return downloadMap(id);
    }

    public boolean copyMap(int id, File folder) throws IOException {
        File file = getMap(id);
        if (file == null) return false;
        FileUtils.copyDirectory(file, folder);
        return true;
    }

    public List<String> games() {
        return maps().gameList();
    }

    public boolean exists(MapResponse response) {
        Response<MapResponse> res = maps().find(response.id);
        if (res.hasFailed()) {
            return false;
        }
        return res.collect() != null;
    }

    private List<MapResponse> getMapsByGame(String game) {
        Response<MapResponse[]> response = maps().list(game);
        if (response.hasFailed()) {
            return new ArrayList<>();
        }
        return Arrays.asList(response.collect());
    }

    public File downloadMap(int id) throws IOException {
        new File(cacheFolder).mkdirs();
        File download = maps().download(cacheFolder + "/" + id + ".tar.gz", id);
        if (download == null) return null;

        File folder = getFolderForMapId(id);
        TarUtils.decompress(download, folder);
        download.delete();
        return folder;

    }

    public boolean downloadMap(int id, File folder) throws IOException {
        File file = downloadMap(id);
        if (file == null) return false;
        FileUtils.copyDirectory(file, folder);
        return true;
    }

    public void uploadFromBukkit(int id, File bukkitFolder, boolean valid) throws IOException {
        File cache = getFolderForMapId(id);
        FileUtils.deleteDirectory(cache);
        FileUtils.copyDirectory(bukkitFolder, cache);
        ArrayList<File> todelete = new ArrayList();
        for (File lixo : cache.listFiles()) {
            if (lixo.getName().equalsIgnoreCase("stats") || lixo.getName().equalsIgnoreCase("playerdata")) {
                todelete.add(lixo);
            }
        }
        for (File x : todelete) {
            if (x.isDirectory()) {
                FileUtils.deleteDirectory(x);
            }
            x.delete();
        }
        FileUtils.deleteDirectory(bukkitFolder);
        uploadMap(id, valid);
    }

    public void updateConfig(int mapId, File bukkitFolder, boolean valid) throws IOException {
        File cache = getFolderForMapId(mapId);
        if (!cache.exists()) {
            // Se não tiver cacheado o mapa manda todo ele
            uploadFromBukkit(mapId, bukkitFolder, valid);
            return;
        } else {
            File f = new File(cache, "config.yml");
            FileUtils.copyFile(new File(bukkitFolder, "config.yml"), f, true);
        }

        uploadMap(mapId, valid);
    }


    public boolean uploadMap(int id, boolean isValid) throws IOException {
        File folder = getFolderForMapId(id);
        if (folder.exists() && folder.isDirectory()) {

            String fname = uploadFolder + "/" + id + ".tar.gz";
            TarUtils.compressIgnoreParent(fname, folder);
            boolean upload = maps().upload(id, new File(fname), isValid);
            File file = new File(fname);
            if (file.exists()) {
                file.delete();
            }
            return upload;

        }
        return false;
    }

    public boolean createNewVoidWorld(MapResponse response, boolean valid) throws IOException {
        File folder = getFolderForMapId(response.id);
        InputStream fis = getClass().getResourceAsStream("/resources/voidWorld.zip");
        ZipUtils.unzip(fis, folder);
        return uploadMap(response.id, valid);

    }


    private MapsAPI maps() {
        return RedstoneGang.instance().webapi().maps();
    }

    public File getFolderForMapId(int id) {
        return new File(cacheFolder, String.valueOf(id));
    }

}
