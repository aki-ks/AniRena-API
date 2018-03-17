package de.kaysubs.tracker.anirena.model;

import java.util.Optional;
import java.util.OptionalInt;

public class SearchRequest {
    private OptionalInt offset = OptionalInt.empty();
    private Optional<String> term = Optional.empty();
    private Optional<Category> category = Optional.empty();

    public OptionalInt getOffset() {
        return offset;
    }

    public SearchRequest setOffset(Integer offset) {
        this.offset = offset == null ? OptionalInt.empty() : OptionalInt.of(offset);
        return this;
    }

    public Optional<String> getTerm() {
        return term;
    }

    public SearchRequest setTerm(String term) {
        this.term = Optional.ofNullable(term);
        return this;
    }

    public Optional<Category> getCategory() {
        return category;
    }

    public SearchRequest setCategory(Category category) {
        this.category = Optional.ofNullable(category);
        return this;
    }
}
