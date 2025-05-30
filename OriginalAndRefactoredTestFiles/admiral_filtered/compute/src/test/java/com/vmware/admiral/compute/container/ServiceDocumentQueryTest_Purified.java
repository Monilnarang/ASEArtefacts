package com.vmware.admiral.compute.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.common.util.QueryUtil;
import com.vmware.admiral.common.util.ServiceDocumentQuery;
import com.vmware.admiral.common.util.ServiceDocumentQuery.ServiceDocumentQueryElementResult;
import com.vmware.admiral.common.util.ServiceUtils;
import com.vmware.admiral.compute.container.ContainerDescriptionService.ContainerDescription;
import com.vmware.xenon.common.Service;
import com.vmware.xenon.common.ServiceDocument;
import com.vmware.xenon.common.UriUtils;
import com.vmware.xenon.common.Utils;
import com.vmware.xenon.services.common.QueryTask;
import com.vmware.xenon.services.common.QueryTask.Query;
import com.vmware.xenon.services.common.QueryTask.QuerySpecification;
import com.vmware.xenon.services.common.ServiceUriPaths;

public class ServiceDocumentQueryTest_Purified extends ComputeBaseTest {

    ServiceDocumentQuery<ContainerDescription> query;

    List<ContainerDescription> descs;

    private String image1 = "image1";

    private String image2 = "image2";

    @Before
    public void setUp() throws Throwable {
        query = new ServiceDocumentQuery<>(host, ContainerDescription.class);
        descs = new ArrayList<>();
        waitForServiceAvailability(ContainerDescriptionService.FACTORY_LINK);
    }

    private List<ContainerDescription> queryDocument(String documentSelfLink) throws Throwable {
        host.testStart(1);
        query.queryDocument(documentSelfLink, handler(true));
        host.testWait();
        return descs;
    }

    private List<ContainerDescription> queryDocumentUpdatedSince(long documentSinceUpdateTimeMicros, String documentSelfLink) throws Throwable {
        host.testStart(1);
        query.queryUpdatedDocumentSince(documentSinceUpdateTimeMicros, documentSelfLink, handler(true));
        host.testWait();
        return descs;
    }

    private List<ContainerDescription> queryUpdatedSince(long timeInMicros) throws Throwable {
        host.testStart(1);
        query.queryUpdatedSince(timeInMicros, handler(false));
        host.testWait();
        return descs;
    }

    private Consumer<ServiceDocumentQueryElementResult<ContainerDescription>> handler(boolean singleResult) {
        return handler(singleResult, null, null);
    }

    private Consumer<ServiceDocumentQueryElementResult<ContainerDescription>> handler(boolean singleResult, final AtomicReference<QueryTask> q, final String link) {
        descs.clear();
        return (r) -> {
            if (r.hasException()) {
                host.failIteration(r.getException());
                return;
            }
            if (q != null && q.get() == null) {
                try {
                    QueryTask queryTask = getDocumentNoWait(QueryTask.class, link);
                    q.set(queryTask);
                } catch (Throwable ignore) {
                }
            }
            if (r.hasResult()) {
                descs.add(r.getResult());
                if (singleResult) {
                    host.completeIteration();
                }
            } else {
                host.completeIteration();
            }
        };
    }

    @Test
    public void testQueryUpdatedSinceWithDeleteAndCreate_1_testMerged_1() throws Throwable {
        long startTime = Utils.getNowMicrosUtc();
        descs = queryUpdatedSince(startTime);
        assertEquals(1, descs.size());
        assertEquals(image2, descs.get(0).image);
        assertTrue(ServiceDocument.isDeleted(descs.get(0)));
    }

    @Test
    public void testQueryUpdatedSinceWithDeleteAndCreate_7() throws Throwable {
        ContainerDescription desc = new ContainerDescription();
        desc = doPost(desc, ContainerDescriptionService.FACTORY_LINK);
        ContainerDescription updatedDesc = new ContainerDescription();
        updatedDesc = doPost(updatedDesc, ContainerDescriptionService.FACTORY_LINK);
        delete(desc.documentSelfLink);
        desc = new ContainerDescription();
        desc = doPost(desc, ContainerDescriptionService.FACTORY_LINK);
        assertEquals(image1, desc.image);
    }
}
