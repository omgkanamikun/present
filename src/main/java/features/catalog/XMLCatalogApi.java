package features.catalog;

import org.xml.sax.XMLReader;

import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.catalog.CatalogResolver;
import javax.xml.parsers.SAXParserFactory;
import java.net.URI;

public class XMLCatalogApi {
    public static void main(String[] args) throws Exception {
        CatalogFeatures f = CatalogFeatures.builder().with(CatalogFeatures.Feature.RESOLVE, "continue").build();
        System.setProperty(CatalogFeatures.Feature.RESOLVE.getPropertyName(), "continue");

        CatalogResolver cr = CatalogManager.catalogResolver(CatalogFeatures.defaults(), URI.create("file:/sample.xml"));
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);

        XMLReader reader = factory.newSAXParser().getXMLReader();
        reader.setEntityResolver(cr);

    }
}
