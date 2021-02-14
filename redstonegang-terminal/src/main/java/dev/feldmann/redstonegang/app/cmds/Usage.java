package dev.feldmann.redstonegang.app.cmds;

public class Usage extends Comando {
    public Usage() {
        super("usage", "Vê o uso da JVM!");
    }

    @Override
    public void exec(String[] args) {
        int mb = 1024*1024;

        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();

        out("§e##### Heap utilization statistics [MB] #####");
        out("§eUsed Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
        out("§eFree Memory:" + runtime.freeMemory() / mb);
        out("§eTotal Memory:" + runtime.totalMemory() / mb);
        out("§eMax Memory:" + runtime.maxMemory() / mb);
    }
}
