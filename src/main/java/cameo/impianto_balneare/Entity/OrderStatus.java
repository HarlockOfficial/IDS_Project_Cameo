package cameo.impianto_balneare.Entity;

public enum OrderStatus {
    ORDERED,
    IN_PROGRESS,
    COMPLETED,
    DELIVERED,
    PAID;

    public OrderStatus next() {
        return this.ordinal() < OrderStatus.values().length - 1 ?
                OrderStatus.values()[this.ordinal() + 1] :
                OrderStatus.values()[OrderStatus.values().length - 1];
    }
}
