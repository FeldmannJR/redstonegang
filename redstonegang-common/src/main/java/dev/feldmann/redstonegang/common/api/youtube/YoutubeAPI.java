package dev.feldmann.redstonegang.common.api.youtube;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class YoutubeAPI {
    private static String key = "AIzaSyCbhSFxasG2oxOixteTz0z31svE725L3Z4";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public static JsonObject readJsonFromUrl(String url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(2000);
        urlConnection.setReadTimeout(2000);
        BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), Charset.forName("UTF-8")));
        JsonParser parser = new JsonParser();
        JsonElement ob = parser.parse(new JsonReader(rd));
        if (!ob.isJsonObject()) {
            return null;
        }
        return ob.getAsJsonObject();
    }

    private static VideoInfo getVideoInfoFrom(JsonObject item) {


        JsonObject statistics = item.get("statistics").getAsJsonObject();
        JsonObject snippets = item.get("snippet").getAsJsonObject();
        String videoid = null;
        if (item.has("id")) {
            JsonElement el = item.get("id");
            if (el.isJsonPrimitive()) {
                videoid = el.getAsString();
            }
            if (el.isJsonObject()) {
                JsonObject jo = el.getAsJsonObject();
                if (jo.has("kind")) {
                    if (jo.get("kind").getAsString().equals("youtube#video")) {
                        videoid = jo.get("id").getAsString();
                    }
                }
            }
        }
        if (videoid == null) {
            return null;
        }
        String title = snippets.get("title").getAsString();
        String chanel = snippets.get("channelTitle").getAsString();
        String views = statistics.get("viewCount").getAsString();
        String likes = statistics.get("likeCount").getAsString();
        String dislike = statistics.get("dislikeCount").getAsString();

        int viewsint = -1;
        try {
            viewsint = Integer.valueOf(views);
        } catch (NumberFormatException ex) {
            return null;
        }
        return new VideoInfo(videoid, title, chanel, viewsint, Integer.valueOf(likes), Integer.valueOf(dislike));

    }

    private static String parseIdFromVideo(String url) {

        URL u = null;
        try {
            u = new URL(url);
            String query = u.getQuery();
            String[] split = query.split("&");
            for (String s : split) {
                if (s.contains("=")) {
                    String[] split1 = s.split("=");
                    if (split1[0].equalsIgnoreCase("v")) {
                        return split1[1];
                    }

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static VideoInfo getVideoFromUrl(String url) {
        String s = parseIdFromVideo(url);
        if (s == null) return null;

        return getVideoFromId(s);


    }

    public static VideoInfo getLastVideoFromChannel(String channelId) {
        //https://www.googleapis.com/youtube/v3/search?part=snippet,statics&channelId=UCmEClzCBDx-vrt0GuSKBd9g&maxResults=10&order=date&type=video&key=AIzaSyAz1cwta12RnzpI53dMACy5lKVPlP2Wn5U
        JsonObject obj = null;
        try {
            obj = readJsonFromUrl("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + channelId + "&maxResults=1&order=date&type=video&key=" + key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (obj == null) {
            return null;
        }
        JsonArray items = obj.get("items").getAsJsonArray();
        int si = items.size();
        if (si >= 1) {
            JsonObject first = items.get(0).getAsJsonObject();
            if (first.has("id")) {
                if (first.get("id").isJsonObject()) {
                    JsonObject id = first.get("id").getAsJsonObject();
                    if (id.has("kind") && id.get("kind").getAsString().equals("youtube#video")) {
                        return getVideoFromId(id.get("videoId").getAsString());
                    }
                }
            }
        }

        return null;

    }

    private static String getChannelIdFromName(String channelname) {
        JsonObject obj = null;
        try {
            obj = readJsonFromUrl("https://www.googleapis.com/youtube/v3/channels?forUsername=" + channelname + "&maxResults=1&part=id&key=" + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (obj == null) {
            return null;
        }
        JsonArray items = obj.get("items").getAsJsonArray();
        if (items.size() == 1) {
            JsonObject first = items.get(0).getAsJsonObject();
            if (first.has("id")) {
                return first.get("id").getAsString();
            }
        }
        return null;
    }

    public static VideoInfo getVideoFromId(String id) {
        JsonObject obj = null;
        try {
            obj = readJsonFromUrl("https://www.googleapis.com/youtube/v3/videos?part=statistics,snippet&id=" + id + "&key=" + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (obj == null) {
            return null;
        }
        JsonArray items = (JsonArray) obj.get("items");
        if (items.size() != 1) {
            return null;
        }
        JsonObject item = items.get(0).getAsJsonObject();
        if (item.has("kind")) {
            if (item.get("kind").getAsString().equals("youtube#video")) {
                return getVideoInfoFrom(item);
            }
        }
        return null;
    }

    public static String formatNumero(int numero) {
        String nome = "";
        if (numero >= 1000 * 1000 * 1000) {
            nome = (numero / 1000000000) + "b";
        } else if (numero >= 1000000) {
            nome = (numero / 1000000) + "m";
        } else if (numero >= 1000) {
            nome = (numero / 1000) + "k";
        } else {
            return numero + "";
        }
        return nome;
    }

    public static String getChannelId(String channelurl) {
        try {
            URL url = new URL(channelurl);
            String str = url.getPath();
            if (url.getQuery() != null) {
                str = str.replace(url.getQuery(), "");
            }


            String[] id = str.split("/");

            if (id.length != 3) {
                return null;
            }
            if (id[1].equalsIgnoreCase("channel")) {
                return id[2];
            }
            if (id[1].equalsIgnoreCase("user")) {
                return getChannelIdFromName(id[2]);
            }

            return id[id.length - 1];

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static class VideoInfo {
        String id;
        String nome;
        String channel;
        int views;
        int likes;
        int deslikes;

        URL thumb;

        public VideoInfo(String id, String nome, String channel, int views, int likes, int deslikes) {
            this.likes = likes;
            this.deslikes = deslikes;
            this.id = id;
            this.nome = nome;
            this.channel = channel;
            this.views = views;
            try {
                this.thumb = new URL("https://i.ytimg.com/vi/" + id + "/mqdefault.jpg");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        public String getChannel() {
            return channel;
        }

        public String getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getViews() {
            return formatNumero(views);
        }

        //320x180
        public URL getThumb() {
            return thumb;
        }

        public String getLikes() {
            return formatNumero(likes);
        }

        public String getDeslikes() {
            return formatNumero(deslikes);
        }

        public String getLink() {
            return "https://www.youtube.com/watch?v=" + id;

        }
    }


}
