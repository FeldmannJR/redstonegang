package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.menus.edit;

//
//public class MenuEditBeneficiosVip extends Menu {
//
//    public MenuEditBeneficiosVip(ShopVIP vip, ShopAddon addon) {
//        super("Editando Vip", 6);
//        for (String b : vip.beneficios) {
//            addNext(new Button(ItemBuilder.item(Material.PAPER).name("§f" + b).build()) {
//                @Override
//                public void click(Player player, Menu menu, ClickType clickType) {
//                    vip.beneficios.remove(b);
//                    addon.save(vip);
//                    new MenuEditBeneficiosVip(vip, addon).open(player);
//
//                }
//            });
//
//        }
//        if (vip.beneficios.size() < 45) {
//            add(49, new Button(ItemBuilder.item(Material.EMERALD).name("§e§lAdicionar").build()) {
//                @Override
//                public void click(Player player, Menu menu, ClickType clickType) {
//                    addon.api(ChatInputAPI.class).getInput(player, "Beneficio", (p, s) -> {
//                        if (s != null) {
//                            vip.beneficios.add(s);
//                            addon.save(vip);
//                        }
//                        new MenuEditBeneficiosVip(vip, addon).open(player);
//
//                    });
//
//                }
//            });
//        }
//    }
//}
