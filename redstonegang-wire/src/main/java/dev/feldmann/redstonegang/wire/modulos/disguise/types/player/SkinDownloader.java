package dev.feldmann.redstonegang.wire.modulos.disguise.types.player;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseModule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SkinDownloader {


    //Nós usamos um sistema customizado de skin e temos nosso próprio servidor
    //A skin é retornada da seguinte forma texture;;;;;signature
    //Caso queira usar skins direto da mojang precisa modificar este metodo para pegar da mojang(talvez no futuro faça config pra pegar da mojang)
    public static PropertyMap getTexture(String nome) {
        PropertyMap map = new PropertyMap();
        String txt = null;
        String signature = null;
        try {
            URL url = new URL(DisguiseModule.skinUrl + nome);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            String result = "";

            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();
            if (!result.isEmpty()) {
                txt = result.split(";;;;;")[0];
                signature = result.split(";;;;;")[1];

            }
            br.close();
        } catch (Exception e) {

            return null;
        }
        if (txt == null) return map;

        map.put("textures", new Property("textures", txt, signature));
        return map;
    }

}
