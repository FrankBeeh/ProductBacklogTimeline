package de.frankbeeh.productbacklogtimeline.service.jira;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.domain.Version;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.service.importer.DataFromCsvImporter;

/**
 * Responsibility:
 * <ul>
 * <li>Imports {@link ProductBacklogItem}s from JIRA.
 * </ul>
 */
public class JiraImporter {

    public List<ProductBacklogItem> importProductBacklogItems(final String jiraBaseUrl, final String username, final String password, final String jql) throws URISyntaxException {
        final JerseyJiraRestClientFactory f = new JerseyJiraRestClientFactory();
        JiraRestClient jc;
        if (username == null) {
            jc = f.create(new URI(jiraBaseUrl), new AnonymousAuthenticationHandler());
        } else {
            jc = f.createWithBasicHttpAuthentication(new URI(jiraBaseUrl), username, password);
        }

        final SearchResult r = jc.getSearchClient().searchJql(jql, 10, 0, null);

        final List<ProductBacklogItem> actualProductBacklogItems = new ArrayList<ProductBacklogItem>();
        final Iterator<BasicIssue> it = r.getIssues().iterator();
        while (it.hasNext()) {
            final Issue issue = jc.getIssueClient().getIssue(((BasicIssue) it.next()).getKey(), null);
            actualProductBacklogItems.add(createProductBacklogItemData(issue));
        }
        return actualProductBacklogItems;
    }

    private ProductBacklogItem createProductBacklogItemData(Issue issue) {
        return new ProductBacklogItem(issue.getKey(), issue.getSummary(), issue.getDescription(), (Double) issue.getFieldByName("Story Points").getValue(),
                DataFromCsvImporter.parseState(issue.getResolution() != null ? issue.getResolution().getName() : null), parseSprints(issue), issue.getFieldByName("Rank").getValue().toString(),
                parseFixVersions(issue.getFixVersions()));
    }

    private String parseFixVersions(Iterable<Version> fixVersions) {
        final Iterator<Version> iterator = fixVersions.iterator();
        final StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().getName()).append(",");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private String parseSprints(Issue issue) {
        final Object value2 = issue.getFieldByName("Sprint").getValue();
        if (value2 == null) {
            return null;
        }
        final String value = value2.toString();
        final Pattern compile = Pattern.compile(".+?name=(.+?),.+");
        final Matcher matcher = compile.matcher(value);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

}
