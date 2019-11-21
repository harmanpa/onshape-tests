/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.cae.onshape.tests;

import com.google.common.io.ByteStreams;
import com.onshape.api.Onshape;
import com.onshape.api.desktop.OnshapeDesktop;
import com.onshape.api.exceptions.OnshapeException;
import com.onshape.api.types.InputStreamWithHeaders;
import com.onshape.api.types.OnshapeDocument;
import com.onshape.api.types.WVM;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author peter
 */
public class DownloadStreamTest {

    @Test
    public void test() throws OnshapeException, IOException {
        OnshapeDocument document = new OnshapeDocument("https://cad.onshape.com/documents/2104e2091052e34e5e6acc36/w/07e5b2a6bd21e25a5dc62956/e/9bcdf1e7dce10ce7d34d9546");
        Onshape o = new Onshape();
        OnshapeDesktop od = new OnshapeDesktop(System.getenv("ONSHAPE_CLIENTID"), System.getenv("ONSHAPE_CLIENTSECRET"));
        try {
            od.setupClient(o);
        } catch (OnshapeException ex) {
            Assertions.fail("Failed to set OAuth credentials", ex);
            return;
        }
        InputStreamWithHeaders stream = o.partStudios().exportParasolid().callToStream(document.getDocumentId(), WVM.Workspace, document.getWorkspaceId(), document.getElementId());
        stream.getHeaders().keySet().forEach((header) -> System.out.println(header + " = " + stream.getHeaderString(header)));
        int length = Integer.parseInt(stream.getHeaderString("Content-Length"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        long compressedLength = ByteStreams.copy(stream.getInputStream(), baos);
        int actualLength = o.partStudios().exportParasolid().call(document.getDocumentId(), WVM.Workspace, document.getWorkspaceId(), document.getElementId()).getFile().getData().length;
        System.out.println("Header says " + length + ", compressed is" + compressedLength + ", download is " + actualLength);
        
        
    }
}
