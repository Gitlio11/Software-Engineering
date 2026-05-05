package services.dblp.search;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.http.client.utils.URIBuilder;
import services.dblp.DBLPQuery;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@Getter
public class DBLPSearchQuery implements DBLPQuery {
    /**
     * Query string.
     */
    @NonNull
    private final DBLPSearchQueryString queryString;

    /**
     * Format to use, options are XML, JSON, and JSONP
     */
    @NonNull
    @Builder.Default
    private final DBLPSearchFormat format = DBLPSearchFormat.JSON;

    /**
     * Maximum number of results. DBLP caps this to 1000.
     */
    @Min(1)
    @Max(1000)
    @Builder.Default
    private final int maxResults = 30;

    /**
     * The first record number for search results. Can be used with maxResults to paginate.
     */
    @Min(0)
    @Builder.Default
    private final int minRecordNumber = 0;

    /**
     * Max number of completion terms. Performs prefix-matching such as "term" -> "terms"
     */
    @Min(0)
    @Max(1000)
    @Builder.Default
    private final int maxCompletionTerms = 10;

    /**
     * DBLP Endpoint (publication, author, venue)
     */
    @NonNull
    private final DBLPSearchEndpoint endpoint;

    @Override
    public String getAssembledQuery() {
        final URIBuilder uri = new URIBuilder();
        uri.setScheme("http");
        uri.setHost("dblp.org");
        uri.setPath(endpoint.getExtension());
        uri.addParameter("format", format.getFormatText());
        uri.addParameter("h", "" + maxResults);
        uri.addParameter("f", "" + minRecordNumber);
        uri.addParameter("c", "" + maxCompletionTerms);
        uri.addParameter("q", "" + queryString.getQuery());
        return uri.toString();
    }
}
