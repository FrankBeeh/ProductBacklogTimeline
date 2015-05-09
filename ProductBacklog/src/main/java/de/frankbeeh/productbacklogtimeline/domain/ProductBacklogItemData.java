package de.frankbeeh.productbacklogtimeline.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Strings;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * Responsibility:
 * <ul>
 * <li>Contain a representation of the data of a {@link ProductBacklogItem} that is stored in the data base.
 * </ul>
 */
public class ProductBacklogItemData {

    private final String id;
    private final String title;
    private final String description;
    private Double estimate;
    private final State state;
    private final String jiraSprint;
    private final String jiraRank;
    private final String plannedRelease;

    public ProductBacklogItemData(String id, String title, String description, Double estimate, String state, String jiraSprint, String jiraRank, String plannedRelease) {
        this(id, title, description, estimate, State.valueOf(state), jiraSprint, jiraRank, plannedRelease);
    }

    public ProductBacklogItemData(String id, String title, String description, Double estimate, State state, String jiraSprint, String jiraRank, String plannedRelease) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimate = estimate;
        this.state = state;
        this.jiraSprint = jiraSprint;
        this.jiraRank = jiraRank;
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

    public String getJiraSprint() {
        return jiraSprint;
    }

    public String getJiraRank() {
        return jiraRank;
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
        final Hasher hasher = hashFunction.newHasher().putUnencodedChars(Strings.nullToEmpty(id)).putUnencodedChars(nullToDefault(title)).putUnencodedChars(nullToDefault(description));
        if (estimate != null) {
            hasher.putDouble(estimate);
        }
        if (state != null) {
            hasher.putUnencodedChars(state.toString());
        }
        hasher.putUnencodedChars(nullToDefault(jiraSprint)).putUnencodedChars(nullToDefault(jiraRank)).putUnencodedChars(nullToDefault(plannedRelease));
        return hasher.hash().toString();
    }

    private String nullToDefault(String string) {
        if (string == null) {
            return "-";
        }
        return string;
    }
}