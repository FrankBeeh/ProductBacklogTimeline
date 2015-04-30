package de.frankbeeh.productbacklogtimeline.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Strings;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class ProductBacklogItemData {

    private final String id;
    private final String title;
    private final String description;
    private Double estimate;
    private final State state;
    private final String sprint;
    private final String rank;
    private final String plannedRelease;

    public ProductBacklogItemData(String id, String title, String description, Double estimate, String state, String sprint, String rank, String plannedRelease) {
        this(id, title, description, estimate, State.valueOf(state), sprint, rank, plannedRelease);
    }

    public ProductBacklogItemData(String id, String title, String description, Double estimate, State state, String sprint, String rank, String plannedRelease) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimate = estimate;
        this.state = state;
        this.sprint = sprint;
        this.rank = rank;
        this.plannedRelease = plannedRelease;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getEstimate() {
        return estimate;
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public State getState() {
        return state;
    }

    public String getSprint() {
        return sprint;
    }

    public String getRank() {
        return rank;
    }

    public String getPlannedRelease() {
        return plannedRelease;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    public String getHash() {
        final HashFunction hashFunction = Hashing.sha1();
        final Hasher hasher = hashFunction.newHasher().putUnencodedChars(Strings.nullToEmpty(id)).putUnencodedChars(Strings.nullToEmpty(title)).putUnencodedChars(Strings.nullToEmpty(description));
        if (estimate != null) {
            hasher.putDouble(estimate);
        }
        if (state != null) {
            hasher.putUnencodedChars(state.toString());
        }
        hasher.putUnencodedChars(Strings.nullToEmpty(sprint)).putUnencodedChars(Strings.nullToEmpty(rank)).putUnencodedChars(Strings.nullToEmpty(plannedRelease));
        return hasher.hash().toString();
    }
}