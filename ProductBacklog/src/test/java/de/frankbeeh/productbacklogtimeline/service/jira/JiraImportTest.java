package de.frankbeeh.productbacklogtimeline.service.jira;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItem;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class JiraImportTest {
    @Test
    public void importProductBacklogItems() throws Exception {
        final JiraImporter jiraImporter = new JiraImporter();
        final String jiraBaseUrl = "https://jira.atlassian.com";
        final String jql = "key in (TST-65595,TST-67742) ORDER BY key ASC";
        final List<ProductBacklogItem> actualProductBacklogItems = jiraImporter.importProductBacklogItems(jiraBaseUrl, jql);
        final List<ProductBacklogItem> expectedProductBacklogItems = Arrays.asList(new ProductBacklogItem("TST-65595", "Title 1", "Description 1", 22d, State.Done, "Mobile Wins Sprint 1",
                "2|i0573j:", "Dev Version,Test Version 1"), new ProductBacklogItem("TST-67742", "Title 2", "Description 2", 13d, (State) null, null, "2|i075yn:", "A version"));
        assertEquals(expectedProductBacklogItems.toString(), actualProductBacklogItems .toString());
    }
}
