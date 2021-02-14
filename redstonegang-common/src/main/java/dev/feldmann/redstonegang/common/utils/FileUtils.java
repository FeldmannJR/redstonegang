package dev.feldmann.redstonegang.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {



    public static String getFolder() {
        String path = null;
        try {
            path = new File(".").getCanonicalPath();
            path = path.substring(path.lastIndexOf(File.separator) + 1);
            return path;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation, true);
        }
    }

    public static void copyDirectory(File sourceFolder, File destinationFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdir();
            }
            String files[] = sourceFolder.list();
            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                copyDirectory(srcFile, destFile);
            }
        } else {
            copyFile(sourceFolder, destinationFolder, true);
        }
    }

    public static void copyFile(File origem, File destino, boolean overwrite) throws IOException {
        if (destino.exists() && !overwrite) {
            System.err.println(destino.getName() + " já existe, ignorando...");
        } else {
            if (destino.exists()) {
                destino.delete();
            }
            FileInputStream fisOrigem = new FileInputStream(origem);
            FileOutputStream fisDestino = new FileOutputStream(destino);
            FileChannel fcOrigem = fisOrigem.getChannel();
            FileChannel fcDestino = fisDestino.getChannel();
            fcOrigem.transferTo(0L, fcOrigem.size(), fcDestino);
            fcOrigem.close();
            fcDestino.close();
            fisDestino.close();
            fisOrigem.close();
        }
    }

    public static String execCmd(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static boolean deleteDirectory(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            String[] files = dir.list();
            int i = 0;

            for (int len = files.length; i < len; ++i) {
                File f = new File(dir, files[i]);
                if (f.isDirectory()) {
                    deleteDirectory(f);
                } else {
                    f.delete();
                }
            }

            return dir.delete();
        } else {
            return false;
        }
    }
    
    public static int getPid(){
        String pidname = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(pidname.split("@")[0]);
    }

    public static void setConfig(String key,String value) {

        //Pega o diretório padrão das configs dando o comando, assim só precisa mudar em ulugar :P
        try {
            String dir = FileUtils.execCmd("sv dir").replace("\n", "");
            if(dir==null){
                return;
            }
            File f = new File(dir + "" + FileUtils.getFolder() + ".sh");
            if(!f.exists()){
                return;
            }
            List<String> fileContent1 = new ArrayList<>(Files.readAllLines(f.toPath(), StandardCharsets.UTF_8));
            boolean add= false;
            for (int i = 0; i < fileContent1.size(); i++) {
                if (fileContent1.get(i).startsWith(key+"=")) {
                    fileContent1.set(i, key+"=" + value);
                    add = true;
                    break;
                }
            }
            if(!add){
                fileContent1.add(key+"="+value);
            }
            Files.write(f.toPath(), fileContent1, StandardCharsets.UTF_8);;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    }
