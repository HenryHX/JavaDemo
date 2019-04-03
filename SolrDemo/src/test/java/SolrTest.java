import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator
 * 2019-03-26
 */

public class SolrTest {
    String baseUrl = "http://localhost:8080/solr";

    @Test
    public void testIndexManager() {
        try {
            SolrServer solrServer = new HttpSolrServer(baseUrl);

            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", "0000001");
            document.addField("exchange_id", "SHZ");
            document.addField("instrument_name", "自定义证券");
            document.addField("price_tick", 0.59f);

            solrServer.add(document);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteDocumentByid() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer(baseUrl);

        solrServer.deleteById("0000001");

        solrServer.commit();
    }

    @Test
    public void deleteDocumentByQuery() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer(baseUrl);

        solrServer.deleteByQuery("*:*");

        solrServer.commit();
    }

    @Test
    public void queryIndex() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer(baseUrl);

        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.set("df", "instrument_keywords");
        query.setFilterQueries("id : [000000 TO 000013]");
        query.setSort("id", SolrQuery.ORDER.desc);

        query.setStart(0);
        query.setRows(3);

        query.setHighlight(true);
        query.addHighlightField("instrument_name");
        query.setHighlightSimplePre("<span style=\"color:red\">");
        query.setHighlightSimplePost("</span>");

        query.setFields("id", "instrument_name");

        QueryResponse response = solrServer.query(query);
        SolrDocumentList resultList = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        System.out.println("===========一共记录数:" + resultList.getNumFound());
        for (SolrDocument doc : resultList) {
            System.out.println("++++++++++++++++++++++++");
            System.out.println(doc.get("id"));
            System.out.println(doc.get("instrument_name"));
            System.out.println(doc.get("exchange_id"));
            System.out.println(doc.get("price_tick"));

            List<String> strings = highlighting.get(doc.get("id")).get("instrument_name");

            if (strings != null && strings.size() > 0) {
                System.out.println(strings.toArray()[0]);
            }
        }
    }


}
