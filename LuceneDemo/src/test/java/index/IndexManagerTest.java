package index;

import analyzer.IKAnalyzer4Lucene7;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator
 * 2019-03-19
 */

public class IndexManagerTest {

    @Test
    public void testIndexWriter() throws Exception {

        File targetDir = new File("D:\\JavaWorkSpace\\LuceneDic");
        FileUtils.cleanDirectory(targetDir);

        File dir = new File("D:\\JavaWorkSpace\\LuceneText");
        List<Document> docList = new ArrayList<Document>();

        for (File file : dir.listFiles()) {
            String fileName = file.getName();
            String fileContent = FileUtils.readFileToString(file, "UTF-8");
            Long fileSize = FileUtils.sizeOf(file);

            TextField fileNameField = new TextField("fileName", fileName, Field.Store.YES);
            TextField fileContentField = new TextField("fileContent", fileContent, Field.Store.YES);
            LongPoint fileSizeField = new LongPoint("fileSize", fileSize);

            Document doc = new Document();
            doc.add(fileContentField);
            doc.add(fileNameField);
            doc.add(fileSizeField);

            docList.add(doc);
        }


        Analyzer analyzer = new IKAnalyzer4Lucene7();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        Directory directory = FSDirectory.open(Paths.get("D:\\JavaWorkSpace\\LuceneDic"));
        IndexWriter indexWriter = new IndexWriter(directory, config);

        for (Document document : docList) {
            indexWriter.addDocument(document);
        }

        indexWriter.close();
    }

    @Test
    public void testIndexReader() throws Exception {
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get("D:\\JavaWorkSpace\\LuceneDic")));
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Analyzer analyzer = new IKAnalyzer4Lucene7();
        QueryParser queryParser = new QueryParser("fileContent", analyzer);
        Query query = queryParser.parse("fileContent:apache");

        TopDocs topDocs = indexSearcher.search(query, 10);

        System.out.println("totoal hit========" + topDocs.totalHits);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);

            System.out.println("doc id =======" + id + ";score=========" + scoreDoc.score);

            System.out.println(doc.getFields());
            System.out.println(doc.getField("fileContent"));
            System.out.println(doc.get("fileContent"));
        }

        indexReader.close();
    }


    @Test
    public void testUpdateDoc() throws Exception {
        Analyzer analyzer = new IKAnalyzer4Lucene7();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        Directory directory = FSDirectory.open(Paths.get("D:\\JavaWorkSpace\\LuceneDic"));
        IndexWriter indexWriter = new IndexWriter(directory, config);

        Document doc = new Document();

        doc.add(new TextField("fileName", "张三说的确实在理", Field.Store.YES));
        doc.add(new TextField("fileContent", "厉害了我的国一经播出，受到各方好评，强烈激发了国人的爱国之情、自豪感！", Field.Store.YES));
        doc.add(new LongPoint("fileSize", 156l));


        indexWriter.updateDocument(new Term("fileContent", "复仇者联盟"), doc);

        indexWriter.close();
    }


    @Test
    public void testDelDoc() throws Exception {
        Query query = new TermQuery(new Term("fileContent", "爱国"));

        Analyzer analyzer = new IKAnalyzer4Lucene7();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        Directory directory = FSDirectory.open(Paths.get("D:\\JavaWorkSpace\\LuceneDic"));
        IndexWriter indexWriter = new IndexWriter(directory, config);


        indexWriter.deleteDocuments(query);
        indexWriter.close();

    }

    @Test
    public void testBooleanQuery() throws Exception {
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get("D:\\JavaWorkSpace\\LuceneDic")));
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        BooleanQuery.Builder builder = new BooleanQuery.Builder();


        Query query1 = new TermQuery(new Term("fileContent", "复仇"));
        Query query2 = new TermQuery(new Term("fileName", "apache"));

        builder.add(query1, BooleanClause.Occur.MUST);
        builder.add(query2, BooleanClause.Occur.MUST);

        BooleanQuery query = builder.build();
        TopDocs topDocs = indexSearcher.search(query, 10);

        System.out.println("totoal hit========" + topDocs.totalHits);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);

            System.out.println("doc id =======" + id + ";score=========" + scoreDoc.score);

            System.out.println(doc.getFields());
            System.out.println(doc.getField("fileContent"));
            System.out.println(doc.get("fileContent"));
        }

        indexReader.close();
    }

    @Test
    public void testAllQuery() throws Exception {
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get("D:\\JavaWorkSpace\\LuceneDic")));
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Query query = new MatchAllDocsQuery();

        TopDocs topDocs = indexSearcher.search(query, 2);

        System.out.println("totoal hit========" + topDocs.totalHits);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);

            System.out.println("doc id =======" + id + ";score=========" + scoreDoc.score);

            System.out.println(doc.getFields());
            System.out.println(doc.getField("fileContent"));
            System.out.println(doc.get("fileContent"));
        }

        indexReader.close();
    }


    @Test
    public void testMultiFieldQuery() throws Exception {
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get("D:\\JavaWorkSpace\\LuceneDic")));
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Analyzer analyzer = new IKAnalyzer4Lucene7();
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"fileContent", "fileName"}, analyzer);
        Query query = queryParser.parse("apache");

        TopDocs topDocs = indexSearcher.search(query, 2);

        System.out.println("totoal hit========" + topDocs.totalHits);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);

            System.out.println("doc id =======" + id + ";score=========" + scoreDoc.score);

            System.out.println(doc.getFields());
            System.out.println(doc.getField("fileContent"));
            System.out.println(doc.get("fileContent"));
        }

        indexReader.close();
    }
}
