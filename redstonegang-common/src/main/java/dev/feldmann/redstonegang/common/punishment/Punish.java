package dev.feldmann.redstonegang.common.punishment;

public enum Punish
{
    BAN("ban"),
    MUTE("mute");

    private String name;

    Punish(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public enum Reason
    {
        BAN("ban"),
        UNBAN("unban"),
        MUTE("mute"),
        UNMUTE("unmute");

        private String name;

        Reason(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }
    }
}


