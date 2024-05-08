package entities.objects;

public enum ObjectTypes {
    HELMET (0),
    ARMOR (1),
    SHIELD (2),
    JEWELERY (3),
    SWORD (4),
    AXE (5),
    SPELLBOOK (6),
    CONSUMABLE (7),
    PICKUP (8);

    final int type;
    ObjectTypes(int type) {
        this.type = type;
    }
}
