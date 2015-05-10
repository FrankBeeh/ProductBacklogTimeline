package de.frankbeeh.productbacklogtimeline.service.database.mapper;

import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.RELEASE;
import static de.frankbeeh.productbacklogtimeline.service.database.generated.Tables.RELEASES;

import java.sql.Connection;
import java.time.LocalDateTime;

import org.jooq.Record2;
import org.jooq.Result;

import de.frankbeeh.productbacklogtimeline.domain.Release;
import de.frankbeeh.productbacklogtimeline.domain.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.service.DateConverter;
import de.frankbeeh.productbacklogtimeline.service.criteria.ReleaseCriteriaFactory;

public class ReleasesMapper extends BaseMapper {

    public ReleasesMapper(Connection connection) {
        super(connection);
    }

    public void insert(LocalDateTime productTimestampId, ReleaseForecast releaseForecast) {
        for (Release release : releaseForecast.getReleases()) {
            insertRelease(release);
            insertRelation(productTimestampId, release);
        }
    }

    private void insertRelation(LocalDateTime productTimestampId, Release release) {
        getDslContext().insertInto(RELEASES, RELEASES.PT_ID, RELEASES.RELEASE_HASH).values(DateConverter.getTimestamp(productTimestampId), release.getHash()).execute();
    }

    public ReleaseForecast get(LocalDateTime productTimestampId) {
        final ReleaseForecast releaseForecast = new ReleaseForecast();
        Result<Record2<String, String>> result = getDslContext().select(RELEASE.NAME, RELEASE.CRITERIA).from(
                RELEASES.join(RELEASE).on(RELEASES.RELEASE_HASH.eq(RELEASE.HASH))).where(RELEASES.PT_ID.eq(DateConverter.getTimestamp(productTimestampId))).fetch();
        for (Record2<String, String> record : result) {
            Release release = new Release(record.getValue(RELEASE.NAME), ReleaseCriteriaFactory.getReleaseCriteria(record.getValue(RELEASE.CRITERIA)));
            releaseForecast.addRelease(release);
        }
        return releaseForecast;
    }
    
    private void insertRelease(Release release) {
        // WORKAROUND because onDuplicateKeyIgnore() is not implemented for SQLite
        if (itemNotYetInserted(release)) {
            getDslContext().insertInto(RELEASE, RELEASE.HASH, RELEASE.NAME, RELEASE.CRITERIA).values(release.getHash(), release.getName(), release.getCriteria().toString()).execute();
        }
    }
    
    private boolean itemNotYetInserted(Release release) {
        return getDslContext().select(RELEASE.HASH).from(RELEASE).where(RELEASE.HASH.eq(release.getHash())).fetchOne() == null;
    }
}
