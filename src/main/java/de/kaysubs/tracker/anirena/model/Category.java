package de.kaysubs.tracker.anirena.model;

public enum Category {
    RAW(1),
    ANIME(2),
    HENTAI(3),
    DRAMA(4),
    DVD(5),
    HENTAI_GAME(6),
    MANGA(7),
    MUSIC(8),
    AMV(9),
    NON_ENGLISH(10),
    OTHER(11);

    public static Category fromId(int id) {
        for(Category category : values())
            if(category.getId() == id)
                return category;

        throw new IllegalArgumentException("No Category with id " + id);
    }

    private final int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
