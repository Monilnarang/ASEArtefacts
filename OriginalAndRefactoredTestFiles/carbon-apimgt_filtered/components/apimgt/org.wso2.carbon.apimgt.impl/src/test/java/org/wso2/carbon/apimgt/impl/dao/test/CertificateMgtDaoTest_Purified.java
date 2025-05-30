package org.wso2.carbon.apimgt.impl.dao.test;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.APIManagerDatabaseException;
import org.wso2.carbon.apimgt.api.dto.CertificateMetadataDTO;
import org.wso2.carbon.apimgt.api.dto.ClientCertificateDTO;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.APIManagerConfiguration;
import org.wso2.carbon.apimgt.impl.APIManagerConfigurationServiceImpl;
import org.wso2.carbon.apimgt.impl.certificatemgt.exceptions.CertificateAliasExistsException;
import org.wso2.carbon.apimgt.impl.certificatemgt.exceptions.CertificateManagementException;
import org.wso2.carbon.apimgt.impl.certificatemgt.exceptions.EndpointForCertificateExistsException;
import org.wso2.carbon.apimgt.impl.dao.CertificateMgtDAO;
import org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;
import org.wso2.carbon.base.MultitenantConstants;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class CertificateMgtDaoTest_Purified {

    private static CertificateMgtDAO certificateMgtDAO;

    private static String TEST_ALIAS = "test alias";

    private static String TEST_ALIAS_2 = "test alias 2";

    private static String TEST_ENDPOINT = "https://test-end-point.com";

    private static String TEST_ENDPOINT_2 = "https://test-end-point2.com";

    private static int TENANT_ID = MultitenantConstants.SUPER_TENANT_ID;

    private static final int TENANT_2 = 1001;

    private static final String certificate = "MIIDPTCCAiWgAwIBAgIETWBSTzANBgkqhkiG9w0BAQsFADBOMQswCQYDVQQGEwJsazELMAkGA1UECBMCbGsxCz" + "AJBgNVBAcTAmxrMQswCQYDVQQKEwJsazELMAkGA1UECxMCbGsxCzAJBgNVBAMTAmxrMCAXDTE4MDEy" + "NTExNDY1NloYDzMwMTcwNTI4MTE0NjU2WjBOMQswCQYDVQQGEwJsazELMAkGA1UECBMCbGsxCzAJBg" + "NVBAcTAmxrMQswCQYDVQQKEwJsazELMAkGA1UECxMCbGsxCzAJBgNVBAMTAmxrMIIBIjANBgkqhkiG" + "9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxLw0sVn/HP3i/5Ghp9vy0OnCs0LEJUAvjndi/Gq+ZRw7HLCVvZ" + "kZc896Kdn2k/9zdmtUptAmXswttCt6cFMIMbeMi2qeCbmPM+WXgm0Ngw+XbBL4qsyvCfnGp7d2i+Qz" + "7x1rm6cb4WGScTdRHXC9EsUGEvotmn2w8g4ksZx/1bR1D/2IZ5BL4G/4kfVcOnPXXXq2IwjVzVUWrc" + "q+fZxAo2iJ2VzGh8vfyNj9Z97Q5ey+Nreqw5HAiPjBcnD8TrbKYfn6tQTTVg8AaY97SXC/AwSvtgvD" + "PMTNNbE5c4JLo+/CeL5d6e6/qsolFpDJUfKES4Gp8MTDlwA3YF8/r0OrHQIDAQABoyEwHzAdBgNVHQ" + "4EFgQU5ZqqRPSTyT8ESAE3keTFMDQqG7owDQYJKoZIhvcNAQELBQADggEBAAL/i00VjPx9BtcUYMN6" + "hJX5cVhbvUBNzuWy+FVk3m3FfRgjXdrWhIRHXVslo/NOoxznd5TGD0GYiBuPtPEG+wYzNgpEbdKrcs" + "M1+YkZVvoon8rItY2vTC57uch/EulrKIeNiYeLxtKNgXpvvAYC0HPtKB/aiC7Vc0gH0JVNrJNah9Db" + "d7HmgeAeiDPvUpZWSvuJPg81G/rC1Gu9yFuiR8HjzcTDRVMepkefA3IpHwYvoQGjeNC/GFGAH/9jih" + "rqw8anwwPALocNSvzwB148w/viIOaopfrmMqBlBWAwUf2wYCU6W3rhhg7H6Zf2cTweLe4v57GVlOWt" + "YOXlgJzeUuc=";

    private APIIdentifier apiIdentifier = new APIIdentifier("CERTIFICATE", "CERTAPI", "1.0.0");

    @Before
    public void setUp() throws APIManagerDatabaseException, APIManagementException, SQLException, XMLStreamException, IOException, NamingException {
        String dbConfigPath = System.getProperty("APIManagerDBConfigurationPath");
        APIManagerConfiguration config = new APIManagerConfiguration();
        initializeDatabase(dbConfigPath);
        config.load(dbConfigPath);
        ServiceReferenceHolder.getInstance().setAPIManagerConfigurationService(new APIManagerConfigurationServiceImpl(config));
        APIMgtDBUtil.initialize();
        certificateMgtDAO = CertificateMgtDAO.getInstance();
    }

    private static void initializeDatabase(String configFilePath) throws IOException, XMLStreamException, NamingException {
        InputStream in;
        in = FileUtils.openInputStream(new File(configFilePath));
        StAXOMBuilder builder = new StAXOMBuilder(in);
        String dataSource = builder.getDocumentElement().getFirstChildWithName(new QName("DataSourceName")).getText();
        OMElement databaseElement = builder.getDocumentElement().getFirstChildWithName(new QName("Database"));
        String databaseURL = databaseElement.getFirstChildWithName(new QName("URL")).getText();
        String databaseUser = databaseElement.getFirstChildWithName(new QName("Username")).getText();
        String databasePass = databaseElement.getFirstChildWithName(new QName("Password")).getText();
        String databaseDriver = databaseElement.getFirstChildWithName(new QName("Driver")).getText();
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(databaseDriver);
        basicDataSource.setUrl(databaseURL);
        basicDataSource.setUsername(databaseUser);
        basicDataSource.setPassword(databasePass);
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        try {
            InitialContext.doLookup("java:/comp/env/jdbc/WSO2AM_DB");
        } catch (NamingException e) {
            InitialContext ic = new InitialContext();
            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");
            ic.bind("java:/comp/env/jdbc/WSO2AM_DB", basicDataSource);
        }
    }

    private boolean addClientCertificate() throws CertificateManagementException {
        return certificateMgtDAO.addClientCertificate(certificate, apiIdentifier, "test", "Gold", APIConstants.API_KEY_TYPE_PRODUCTION, TENANT_ID, "org1");
    }

    private boolean deleteClientCertificate() throws CertificateManagementException {
        return certificateMgtDAO.deleteClientCertificate(apiIdentifier, "test", APIConstants.API_KEY_TYPE_PRODUCTION, TENANT_ID);
    }

    @Test
    public void testGetClientCertificateCount_1() throws CertificateManagementException {
        Assert.assertEquals("The expected client certificate count does not match with the retrieved count", 1, certificateMgtDAO.getClientCertificateCount(TENANT_ID, APIConstants.API_KEY_TYPE_PRODUCTION));
    }

    @Test
    public void testGetClientCertificateCount_2() throws CertificateManagementException {
        Assert.assertEquals("The expected client certificate count does not match with the retrieved count", 0, certificateMgtDAO.getClientCertificateCount(TENANT_ID, APIConstants.API_KEY_TYPE_PRODUCTION));
    }

    @Test
    public void testCheckWhetherAliasExist_1() throws CertificateManagementException {
        Assert.assertFalse("The non-existing alias was detected as exist", certificateMgtDAO.checkWhetherAliasExist(APIConstants.API_KEY_TYPE_PRODUCTION, "test", MultitenantConstants.SUPER_TENANT_ID));
    }

    @Test
    public void testCheckWhetherAliasExist_2() throws CertificateManagementException {
        Assert.assertTrue("The existing alias was detected as notexist", certificateMgtDAO.checkWhetherAliasExist(APIConstants.API_KEY_TYPE_PRODUCTION, "test", MultitenantConstants.SUPER_TENANT_ID));
    }
}
