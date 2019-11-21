/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.onshape.tests;

import com.onshape.api.Onshape;
import com.onshape.api.desktop.OnshapeDesktop;
import com.onshape.api.exceptions.OnshapeException;
import com.onshape.api.responses.AssembliesCreateTranslationResponse;
import com.onshape.api.types.InputStreamWithHeaders;
import com.onshape.api.types.WV;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author peter
 */
public class ExportAssemblyTest {

    @Test
    public void test() throws OnshapeException, InterruptedException, ExecutionException {
        //https://cad.onshape.com/documents/9558507b2d8feaea012281be/w/8a8c9fb7f12ace4bfb9f4dad/e/a8d9da8f108b44b9fa903800
        Onshape o = new Onshape();
        OnshapeDesktop od = new OnshapeDesktop(System.getenv("ONSHAPE_CLIENTID"), System.getenv("ONSHAPE_CLIENTSECRET"));
        try {
            od.setupClient(o);
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to set OAuth credentials", ex);
            return;
        }
        // Start the translation
        Future<AssembliesCreateTranslationResponse> future = o.assemblies().createTranslation()
                .formatName("PARASOLID")
                .includeExportIds(Boolean.TRUE)
                .storeInDocument(Boolean.FALSE)
                .linkDocumentWorkspaceId("")
                .call("9558507b2d8feaea012281be", WV.Workspace, "8a8c9fb7f12ace4bfb9f4dad", "a8d9da8f108b44b9fa903800")
                .asFuture(o);

// Poll for completion
        AssembliesCreateTranslationResponse response = future.get();

        if ("DONE".equals(response.getRequestState())) {
            // Request to download the data generated
           InputStreamWithHeaders stream = o.documents().downloadExternalData().callToStream(response.getResultExternalDataIds()[0], response.getResultDocumentId());
           stream.getHeaders().forEach((key, value) -> System.out.println(key + " = " + stream.getHeaderString(key)));
            // Do something with the data
        } else {
            // Reason for failure is in response.getFailureReason()
        }
    }
}
