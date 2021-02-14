package dev.feldmann.redstonegang.wire.game.base.addons.server.jobs.perks;

public interface SpecificAction<T, U> {

    U convert(T t);

    U[] getFilters();

    default boolean canDrop(T ent) {
        U eF = convert(ent);
        for (U u : getFilters()) {
            if (apply(eF, u)) {
                return true;
            }
        }
        return false;
    }

    default boolean apply(U entity, U filter) {
        return entity.equals(filter);
    }

}
